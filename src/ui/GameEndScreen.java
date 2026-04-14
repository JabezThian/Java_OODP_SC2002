package ui;

import entities.Player;
import java.util.Scanner;

public class GameEndScreen {

    public void showVictory(Player player, int totalRounds) {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║                  VICTORY!                    ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println("Congratulations, you have defeated all your enemies.");
        System.out.println();
        System.out.println("──────────── Statistics ────────────");
        System.out.printf("  Remaining HP : %d / %d%n",
                player.getHealth().getValue(),
                player.getHealth().getMaxHealth());
        System.out.printf("  Total Rounds : %d%n", totalRounds);
        System.out.println("────────────────────────────────────");
    }

    public void showDefeat(Player player, int enemiesRemaining, int totalRounds) {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║                  DEFEATED                    ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println("Defeated. Don't give up, try again!");
        System.out.println();
        System.out.println("──────────── Statistics ────────────");
        System.out.printf("  Enemies Remaining  : %d%n", enemiesRemaining);
        System.out.printf("  Total Rounds Survived: %d%n", totalRounds);
        System.out.println("────────────────────────────────────");
    }

    /**
     * Shows the post-game menu and returns the player's choice:
     *   1 = replay with same settings
     *   2 = new game (return to home screen)
     *   3 = exit
     */
    public int showReplayMenu(Scanner scanner) {
        System.out.println();
        System.out.println("What would you like to do?");
        System.out.println("  1. Replay with the same settings");
        System.out.println("  2. Start a new game (return to home screen)");
        System.out.println("  3. Exit");

        int choice = -1;
        while (choice < 1 || choice > 3) {
            System.out.print("Choice » ");
            try {
                choice = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                scanner.next();
            }
            if (choice < 1 || choice > 3) {
                System.out.println(">> Invalid choice. Please select 1, 2, or 3.");
            }
        }
        return choice;
    }
}
