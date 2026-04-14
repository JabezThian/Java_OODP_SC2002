package ui;

import java.util.Scanner;

public class LoadingScreen {

    public void showBanner() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║       TURN-BASED COMBAT ARENA  v1.0          ║");
        System.out.println("║              SC2002 Group Project            ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println();
    }

    public void showPlayerOptions() {
        System.out.println("══════════════════ PLAYER CLASSES ══════════════════");
        System.out.printf("  %-8s │ HP:%-4d │ ATK:%-3d │ DEF:%-3d │ SPD:%-3d%n",
                "Warrior", 260, 40, 20, 30);
        System.out.println("           │ Special: Shield Bash");
        System.out.println("           │   → Deals BasicAttack dmg to 1 enemy + Stuns for 2 turns");
        System.out.println();
        System.out.printf("  %-8s │ HP:%-4d │ ATK:%-3d │ DEF:%-3d │ SPD:%-3d%n",
                "Wizard", 200, 50, 10, 20);
        System.out.println("           │ Special: Arcane Blast");
        System.out.println("           │   → Deals BasicAttack dmg to ALL enemies");
        System.out.println("           │   → Each kill grants +10 ATK until end of level");
        System.out.println("═════════════════════════════════════════════════════");
    }

    public void showEnemyInfo() {
        System.out.println("═══════════════════ ENEMY TYPES ═════════════════════");
        System.out.printf("  %-8s │ HP:%-4d │ ATK:%-3d │ DEF:%-3d │ SPD:%-3d%n",
                "Goblin", 55, 35, 15, 25);
        System.out.printf("  %-8s │ HP:%-4d │ ATK:%-3d │ DEF:%-3d │ SPD:%-3d%n",
                "Wolf", 40, 45, 5, 35);
        System.out.println("═════════════════════════════════════════════════════");
    }

    public void showItemOptions() {
        System.out.println("═══════════════════ AVAILABLE ITEMS ═════════════════");
        System.out.println("  1. Potion      → Heals 100 HP (capped at max HP)");
        System.out.println("  2. Power Stone → Free extra use of special skill (no cooldown effect)");
        System.out.println("  3. Smoke Bomb  → Enemy attacks deal 0 damage for 2 turns");
        System.out.println("  (You may pick 2 items; duplicates are allowed)");
        System.out.println("═════════════════════════════════════════════════════");
    }

    public void showDifficultyOptions() {
        System.out.println("══════════════════ DIFFICULTY LEVELS ════════════════");
        System.out.println("  1. Easy   → Initial: 3 Goblins");
        System.out.println("              No backup wave");
        System.out.println("  2. Medium → Initial: 1 Goblin + 1 Wolf");
        System.out.println("              Backup:  2 Wolves");
        System.out.println("  3. Hard   → Initial: 2 Goblins");
        System.out.println("              Backup:  1 Goblin + 2 Wolves");
        System.out.println("═════════════════════════════════════════════════════");
    }

    public String promptPlayerName(Scanner scanner) {
        showPlayerOptions();
        System.out.println();
        showEnemyInfo();
        System.out.println();

        System.out.print("Enter your hero's name: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) name = scanner.nextLine();
        name = name.trim();
        if (name.isEmpty()) name = "Hero";
        return name;
    }

    public int promptClassChoice(Scanner scanner) {
        int choice = -1;
        while (choice != 1 && choice != 2) {
            System.out.println("\nSelect your class:");
            System.out.println("  1. Warrior");
            System.out.println("  2. Wizard");
            System.out.print("Choice » ");
            try {
                choice = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                scanner.next();
            }
            if (choice != 1 && choice != 2) {
                System.out.println(">> Invalid choice. Please select 1 or 2.");
            }
        }
        return choice;
    }

    public int promptItemChoice(Scanner scanner, int pickNumber) {
        int choice = -1;
        while (choice < 1 || choice > 3) {
            System.out.printf("Pick item %d (1-3): ", pickNumber);
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

    public int promptDifficultyChoice(Scanner scanner) {
        showDifficultyOptions();
        System.out.println();

        int choice = -1;
        while (choice < 1 || choice > 3) {
            System.out.print("Select difficulty (1-3): ");
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
