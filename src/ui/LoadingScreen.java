package ui;

import difficulty.EasyLevel;
import difficulty.HardLevel;
import difficulty.Level;
import difficulty.MediumLevel;
import entities.Player;
import entities.Warrior;
import entities.Wizard;
import item.Item;
import item.ItemBag;
import item.Potion;
import item.PowerStone;
import item.SmokeBomb;

import java.util.Scanner;

public class LoadingScreen {

    public void showBanner() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║       TURN-BASED COMBAT ARENA  v1.0         ║");
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

    /**
     * Shows all player and enemy info, prompts for name and class, returns the created Player.
     */
    public Player promptPlayerSetup(Scanner scanner) {
        showPlayerOptions();
        System.out.println();
        showEnemyInfo();
        System.out.println();

        System.out.print("Enter your hero's name: ");
        // If a prior nextInt() left a newline in the buffer, consume it first.
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) name = scanner.nextLine();
        name = name.trim();
        if (name.isEmpty()) name = "Hero";

        Player player = null;
        while (player == null) {
            System.out.println("\nSelect your class:");
            System.out.println("  1. Warrior");
            System.out.println("  2. Wizard");
            System.out.print("Choice » ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    player = new Warrior(name);
                    System.out.println(">> You chose Warrior!");
                    break;
                case 2:
                    player = new Wizard(name);
                    System.out.println(">> You chose Wizard!");
                    break;
                default:
                    System.out.println(">> Invalid choice. Please select 1 or 2.");
            }
        }
        return player;
    }

    /**
     * Prompts for exactly 2 item picks (duplicates allowed), populates the given ItemBag.
     */
    public void promptItemSelection(Scanner scanner, ItemBag bag) {
        showItemOptions();
        System.out.println();

        for (int pick = 1; pick <= 2; pick++) {
            Item chosen = null;
            while (chosen == null) {
                System.out.printf("Pick item %d (1-3): ", pick);
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        chosen = new Potion();
                        break;
                    case 2:
                        chosen = new PowerStone();
                        break;
                    case 3:
                        chosen = new SmokeBomb();
                        break;
                    default:
                        System.out.println(">> Invalid choice. Please select 1, 2, or 3.");
                }
            }
            bag.gainItem(chosen);
            System.out.println(">> Added: " + chosen.getName());
        }
        System.out.println(">> Items selected:");
        bag.displayItems();
    }

    /**
     * Shows difficulty options and returns the chosen Level implementation.
     */
    public Level promptDifficultyChoice(Scanner scanner) {
        showDifficultyOptions();
        System.out.println();

        Level level = null;
        while (level == null) {
            System.out.print("Select difficulty (1-3): ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    level = new EasyLevel();
                    break;
                case 2:
                    level = new MediumLevel();
                    break;
                case 3:
                    level = new HardLevel();
                    break;
                default:
                    System.out.println(">> Invalid choice. Please select 1, 2, or 3.");
            }
        }
        System.out.println(">> Difficulty: " + level.getDifficultyName()
                + " | " + level.getInitialEnemyDescription());
        return level;
    }
}
