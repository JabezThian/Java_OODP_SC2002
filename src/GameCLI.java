import java.util.Scanner;
import core.BattleEngine;

public class GameCLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BattleEngine engine = new BattleEngine();
        engine.runGame(scanner);
        scanner.close();
    }
}
