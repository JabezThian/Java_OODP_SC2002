package core;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BattleEngine engine = new BattleEngine();
        engine.runGame(scanner);
        scanner.close();
    }
}
