import com.kenzie.app.menu.GameLoop;
import java.util.Scanner;

public class Application {

    public static void main(String[] args)  {
        String playerName;
        Scanner scn = new Scanner(System.in);
        System.out.println("Hello! Please enter your name...");
        playerName = scn.nextLine();

        GameLoop newGame = new GameLoop(playerName);
        newGame.gameStart();
    }
}

