package com.kenzie.app.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.app.data.GameDTO;
import com.kenzie.app.http.CustomHttpClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GameLoop {

    private final String playerName;
    private int score;

    public GameLoop(String playerName) {
        this.playerName = playerName;
        this.score = 0;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void gameStart() {
        int numQuestions = 0;
        String guess = "";
        //I found & used Collections.shuffle() in Oregon Trail's extra credit portion
        //I like the random implementation as it removes duplicates intuitively as well
        ArrayList<Integer> questionList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            questionList.add(i);
        }
        Collections.shuffle(questionList);

        try {
            String url = "https://jservice.kenzie.academy/api/clues";
            String httpResponse;

            httpResponse = CustomHttpClient.sendGET(url);

            //Json Object mapping
            ObjectMapper objectMapper = new ObjectMapper();
            GameDTO gameObj;
            gameObj = objectMapper.readValue(httpResponse, GameDTO.class);

            Scanner scanner = new Scanner(System.in);

            System.out.println("Welcome " + getPlayerName() + " ,let's play!\n");

            do {
                for (int i = 0; i < 10; i++) {
                    System.out.println("Category: " + gameObj.getClues().get(questionList.get(i)).getCategory().getTitle());
                    System.out.println("Question: " + gameObj.getClues().get(questionList.get(i)).getQuestion());
                    guess = scanner.nextLine();
                    if(guess.equalsIgnoreCase(gameObj.getClues().get(questionList.get(i)).getAnswer())) {
                        score ++;
                        System.out.println("That's correct! Your score is now " + getScore());
                    } else {
                        System.out.println("I'm sorry, but that's incorrect. The answer was " + gameObj.getClues().get(questionList.get(i)).getAnswer());
                    }
                    System.out.println("Score: " + getScore());
                    System.out.println();
                    numQuestions++;
                }
            } while(numQuestions < 10);
            System.out.println("Thanks for playing " + getPlayerName() + "! Your final score was " + getScore());

        } catch(Exception e) {
            System.out.println("Unexpected exception: " + e);
        }
    }
}
