package core;

import entities.*;
import actions.*;
import ui.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BattleDisplay display = new BattleDisplay();
        TurnOrderStrategy turnStrategy = new TurnOrderStrategy();

        // NAME & CLASS SELECTION
        System.out.print("Enter your Hero's name: ");
        String name = scanner.nextLine();

        Player player = null;
        while (player == null) {
            System.out.println("\nSelect your class:");
            System.out.println("1. Warrior");
            System.out.println("2. Wizard");
            System.out.print("Choice » ");

            int classChoice = scanner.nextInt();
            if (classChoice == 1) {
                player = new Warrior(name);
            } else if (classChoice == 2) {
                player = new Wizard(name);
            } else {
                System.out.println(">> Invalid class choice. Please select 1 or 2.");
            }
        }

        // DIFFICULTY SELECTION
        List<Enemy> enemies = null;
        while (enemies == null) {
            System.out.println("\nSelect Difficulty:");
            System.out.println("1. Easy (3 Goblins)");
            System.out.println("2. Medium (1 Goblin, 1 Wolf)");
            System.out.println("3. Hard (2 Goblins)");
            System.out.print("Choice » ");

            int diffChoice = scanner.nextInt();

            // We only proceed if the choice is 1, 2, or 3
            if (diffChoice >= 1 && diffChoice <= 3) {
                enemies = LevelFactory.createLevel(diffChoice);
            } else {
                System.out.println(">> Invalid difficulty. Please select 1, 2, or 3.");
            }
        }

        int roundCounter = 1;

        while (!turnStrategy.isGameOver(player, enemies)) {
            display.showRoundHeader(roundCounter);
            display.showStats(player, enemies);

            // Determine turn order for the round
            List<Combatant> participants = new ArrayList<>();
            participants.add(player);
            participants.addAll(enemies);
            turnStrategy.sortBySpeed(participants);

            display.showTurnOrder(participants);

            for (Combatant c : participants) {
                if (c.getHealth().isDead() || turnStrategy.isGameOver(player, enemies)) continue;

                if (c instanceof Player) {
                    handlePlayerTurn((Player) c, enemies, scanner, display);
                } else {
                    if (c.isStunned()) {
                        System.out.println("[ENEMY TURN: " + c.getName() + "]");
                        System.out.println(">> " + c.getName() + " is stunned!\n");
                    } else {
                        System.out.println("[ENEMY TURN: " + c.getName() + "]");
                        new BasicAttack().execute(c, List.of(player));
                    }
                }
            }

            // EFFECTS UPDATE (End of Round)
            player.updateEffects();
            player.updateCooldown();
            for (Enemy e : enemies) {
                e.updateEffects();
            }

            enemies.removeIf(e -> e.getHealth().isDead());
            roundCounter++;
        }

        turnStrategy.announceWinner(player, enemies);
    }

    private static void handlePlayerTurn(Player p, List<Enemy> enemies, Scanner scanner, BattleDisplay display) {
        if (p.isStunned()) {
            System.out.println("\n!! " + p.getName() + " is stunned and cannot move !!");
            return;
        }

        boolean turnComplete = false;
        int targetInput;
        int actualIdx;

        while (!turnComplete) {
            display.showMenu(p);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1: // Basic Attack
                    System.out.print("Select target (1-" + enemies.size() + "): ");
                    targetInput = scanner.nextInt();
                    actualIdx = targetInput - 1;

                    if (actualIdx >= 0 && actualIdx < enemies.size()) {
                        new BasicAttack().execute(p, List.of(enemies.get(actualIdx)));
                        turnComplete = true;
                    } else {
                        System.out.println(">> INVALID TARGET. Try again.");
                    }
                    break;

                case 2: // Special Skill
                    if (p.getSpecialCooldown() == 0) {
                        if (p instanceof Warrior) {
                            System.out.print("Select target for Shield Bash (1-" + enemies.size() + "): ");
                            targetInput = scanner.nextInt();
                            actualIdx = targetInput - 1;

                            if (actualIdx >= 0 && actualIdx < enemies.size()) {
                                p.useSpecial(List.of(enemies.get(actualIdx)));
                                turnComplete = true;
                            } else {
                                System.out.println(">> INVALID TARGET. Try again.");
                            }
                        } else if (p instanceof Wizard) {
                            // Wizard casts Arcane Blast on all current enemies
                            p.useSpecial(new ArrayList<>(enemies));
                            turnComplete = true;
                        }
                    } else {
                        System.out.println(">> ERROR: " + p.getSpecialSkillName() + " is on cooldown! (" + p.getSpecialCooldown() + " turns left)");
                    }
                    break;

                case 3: // Defend
                    // We pass null list because Defend only targets the 'performer'
                    new DefendAction().execute(p, null);
                    turnComplete = true;
                    break;

                default:
                    System.out.println(">> INVALID CHOICE. Please enter 1, 2, or 3.");
                    break;
            }
        }
    }
}