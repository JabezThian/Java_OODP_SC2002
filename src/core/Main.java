package core;

import difficulty.Level;
import entities.*;
import actions.*;
import triggers.*;
import ui.*;
import item.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoadingScreen loadingScreen = new LoadingScreen();
        BattleDisplay display = new BattleDisplay();
        GameEndScreen endScreen = new GameEndScreen();
        TurnOrderStrategy turnStrategy = new TurnOrderStrategy();
        boolean running = true;
        Level chosenLevel = null;
        Player player = null;
        ItemBag bag = null;

        while (running) {
            new GlobalTriggers();

            // ──────────────── LOADING SCREEN ────────────────
            loadingScreen.showBanner();

            // Player setup (name + class)
            player = loadingScreen.promptPlayerSetup(scanner);

            // Item selection (2 items → ItemBag with capacity 2)
            System.out.println();
            bag = new ItemBag(2, player);
            loadingScreen.promptItemSelection(scanner, bag);

            // Difficulty selection → Level → WaveManager
            System.out.println();
            chosenLevel = loadingScreen.promptDifficultyChoice(scanner);

            boolean keepPlaying = true;
            while (keepPlaying) {
                // Fresh wave manager and enemies for this run
                WaveManager waveManager = new WaveManager(chosenLevel);
                List<Enemy> enemies = waveManager.getActiveEnemies();
                int roundCounter = 1;

                // Reset player state for replay
                player.getHealth().setValue(player.getHealth().getMaxHealth());
                player.setSpecialCooldown(0);

                // ──────────────── GAME LOOP ────────────────
                while (!player.getHealth().isDead() && !waveManager.isAllDefeated()) {
                    display.showRoundHeader(roundCounter);
                    display.showStats(player, enemies, bag);

                    List<Combatant> participants = new ArrayList<>();
                    participants.add(player);
                    participants.addAll(enemies);

                    GlobalTriggers.getGlobalTriggerObject().trigger(GlobalTriggerTypes.ON_ROUND_START);
                    turnStrategy.sortBySpeed(participants);
                    display.showTurnOrder(participants);

                    for (Combatant c : participants) {
                        if (c.getHealth().isDead()
                                || player.getHealth().isDead()
                                || waveManager.isAllDefeated()) continue;

                        c.getLocalTriggerList().trigger(LocalTriggerTypes.ON_TURN_START);

                        if (c instanceof Player) {
                            handlePlayerTurn((Player) c, enemies, bag, scanner, display);
                            ((Player) c).updateCooldown();
                        } else {
                            if (c.isStunned()) {
                                System.out.println("[ENEMY TURN: " + c.getName() + "]");
                                System.out.println(">> " + c.getName() + " is stunned!\n");
                            } else {
                                System.out.println("[ENEMY TURN: " + c.getName() + "]");
                                new BasicAttack().execute(c, List.of(player));
                            }
                        }

                        c.getLocalTriggerList().trigger(LocalTriggerTypes.ON_TURN_END);
                    }

                    GlobalTriggers.getGlobalTriggerObject().trigger(GlobalTriggerTypes.ON_ROUND_END);

                    waveManager.removeDeadEnemies();
                    if (waveManager.isCurrentWaveCleared() && waveManager.hasNextWave()) {
                        List<Enemy> spawned = waveManager.triggerNextWave();
                        display.showBackupSpawn(spawned);
                    }

                    roundCounter++;
                }

                // ──────────────── END SCREEN ────────────────
                if (player.getHealth().isDead()) {
                    endScreen.showDefeat(player, enemies.size(), roundCounter - 1);
                } else {
                    endScreen.showVictory(player, roundCounter - 1);
                }

                // ──────────────── REPLAY MENU ────────────────
                int replayChoice = endScreen.showReplayMenu(scanner);
                switch (replayChoice) {
                    case 1: // Replay same settings
                        System.out.println("\n>> Restarting with the same settings...");
                        new GlobalTriggers();
                        // Reset bag items to their original quantities
                        bag = new ItemBag(2, player);
                        loadingScreen.promptItemSelection(scanner, bag);
                        break;
                    case 2: // New game
                        System.out.println("\n>> Starting a new game...");
                        keepPlaying = false;
                        break;
                    case 3: // Exit
                        System.out.println("\n>> Thanks for playing. Goodbye!");
                        keepPlaying = false;
                        running = false;
                        break;
                }
            }
        }

        scanner.close();
    }

    // ──────────────────────────────────────────────────────────────────
    // Player turn handler
    // ──────────────────────────────────────────────────────────────────
    private static void handlePlayerTurn(Player p, List<Enemy> enemies, ItemBag bag,
                                         Scanner scanner, BattleDisplay display) {
        if (p.isStunned()) {
            System.out.println("\n!! " + p.getName() + " is stunned and cannot move !!");
            return;
        }

        boolean turnComplete = false;

        while (!turnComplete) {
            display.showMenu(p, bag);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1: // Basic Attack
                    System.out.print("Select target (1-" + enemies.size() + "): ");
                    int t1 = scanner.nextInt() - 1;
                    if (t1 >= 0 && t1 < enemies.size()) {
                        new BasicAttack().execute(p, List.of(enemies.get(t1)));
                        turnComplete = true;
                    } else {
                        System.out.println(">> INVALID TARGET. Try again.");
                    }
                    break;

                case 2: // Special Skill
                    if (p.getSpecialCooldown() != 0) {
                        System.out.println(">> ERROR: " + p.getSpecialSkillName()
                                + " is on cooldown! (" + p.getSpecialCooldown() + " turn(s) left)");
                        break;
                    }
                    if (p instanceof Warrior) {
                        System.out.print("Select target for Shield Bash (1-" + enemies.size() + "): ");
                        int t2 = scanner.nextInt() - 1;
                        if (t2 >= 0 && t2 < enemies.size()) {
                            p.useSpecial(List.of(enemies.get(t2)));
                            turnComplete = true;
                        } else {
                            System.out.println(">> INVALID TARGET. Try again.");
                        }
                    } else if (p instanceof Wizard) {
                        p.useSpecial(new ArrayList<>(enemies));
                        turnComplete = true;
                    }
                    break;

                case 3: // Defend
                    new DefendAction().execute(p, null);
                    turnComplete = true;
                    break;

                case 4: // Use Item
                    if (bag == null || bag.isEmpty()) {
                        System.out.println(">> No items available.");
                        break;
                    }
                    display.showItemMenu(bag);
                    int itemIdx = scanner.nextInt() - 1;
                    if (itemIdx < 0 || itemIdx >= bag.getItemCount()) {
                        System.out.println(">> INVALID ITEM. Try again.");
                        break;
                    }
                    Item selected = bag.getItem(itemIdx);
                    List<Combatant> targets;
                    if (selected instanceof PowerStone && p instanceof Warrior) {
                        System.out.print("Select target for Shield Bash via Power Stone (1-"
                                + enemies.size() + "): ");
                        int t4 = scanner.nextInt() - 1;
                        if (t4 < 0 || t4 >= enemies.size()) {
                            System.out.println(">> INVALID TARGET. Try again.");
                            break;
                        }
                        targets = List.of(enemies.get(t4));
                    } else if (selected instanceof PowerStone && p instanceof Wizard) {
                        targets = new ArrayList<>(enemies);
                    } else {
                        // Potion / SmokeBomb — self-targeted
                        targets = List.of(p);
                    }
                    bag.useItem(itemIdx, targets);
                    turnComplete = true;
                    break;

                default:
                    System.out.println(">> INVALID CHOICE. Please enter 1"
                            + (bag != null && !bag.isEmpty() ? "-4." : "-3."));
            }
        }
    }
}
