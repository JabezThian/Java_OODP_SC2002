package core;

import difficulty.Level;
import entities.*;
import actions.*;
import triggers.*;
import item.*;
import ui.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BattleEngine {

    private final LoadingScreen loadingScreen;
    private final BattleDisplay display;
    private final GameEndScreen endScreen;
    private final TurnOrderStrategy turnStrategy;
    private final GameSetupController setupController;
    private final PlayerTurnController playerTurnController;

    public BattleEngine() {
        this.loadingScreen = new LoadingScreen();
        this.display = new BattleDisplay();
        this.endScreen = new GameEndScreen();
        this.turnStrategy = new TurnOrderStrategy();
        this.setupController = new GameSetupController();
        this.playerTurnController = new PlayerTurnController(display);
    }

    public void runGame(Scanner scanner) {
        boolean running = true;

        while (running) {
            new GlobalTriggers();
            loadingScreen.showBanner();
            String playerName = loadingScreen.promptPlayerName(scanner);
            int classChoice = loadingScreen.promptClassChoice(scanner);
            Player player = setupController.createPlayer(playerName, classChoice);
            System.out.println();
            ItemBag bag = new ItemBag(2, player);
            loadingScreen.showItemOptions();
            System.out.println();
            for (int pick = 1; pick <= 2; pick++) {
                int itemChoice = loadingScreen.promptItemChoice(scanner, pick);
                Item item = setupController.createItem(itemChoice);
                bag.gainItem(item);
                System.out.println(">> Added: " + item.getName());
            }
            System.out.println(">> Items selected:");
            bag.displayItems();
            System.out.println();
            int diffChoice = loadingScreen.promptDifficultyChoice(scanner);
            Level chosenLevel = setupController.createLevel(diffChoice);

            boolean keepPlaying = true;
            while (keepPlaying) {
                WaveManager waveManager = new WaveManager(chosenLevel);
                List<Enemy> enemies = waveManager.getActiveEnemies();
                int roundCounter = 1;

                player.getHealth().setValue(player.getHealth().getMaxHealth());
                player.setSpecialCooldown(0);
                //Game loop
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
                            playerTurnController.handleTurn((Player) c, enemies, bag, scanner);
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

                // End screen
                if (player.getHealth().isDead()) {
                    endScreen.showDefeat(player, enemies.size(), roundCounter - 1);
                } else {
                    endScreen.showVictory(player, roundCounter - 1);
                }

                // Replay screen
                int replayChoice = endScreen.showReplayMenu(scanner);
                switch (replayChoice) {
                    case 1:
                        System.out.println("\n>> Restarting with the same settings...");
                        new GlobalTriggers();
                        bag = new ItemBag(2, player);
                        loadingScreen.showItemOptions();
                        System.out.println();
                        for (int pick = 1; pick <= 2; pick++) {
                            int itemChoice = loadingScreen.promptItemChoice(scanner, pick);
                            Item item = setupController.createItem(itemChoice);
                            bag.gainItem(item);
                            System.out.println(">> Added: " + item.getName());
                        }
                        System.out.println(">> Items selected:");
                        bag.displayItems();
                        break;
                    case 2:
                        System.out.println("\n>> Starting a new game...");
                        keepPlaying = false;
                        break;
                    case 3:
                        System.out.println("\n>> Thanks for playing. Goodbye!");
                        keepPlaying = false;
                        running = false;
                        break;
                }
            }
        }
    }
}
