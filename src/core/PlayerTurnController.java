package core;

import entities.*;
import actions.*;
import item.*;
import ui.BattleDisplay;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayerTurnController {

    private final BattleDisplay display;

    public PlayerTurnController(BattleDisplay display) {
        this.display = display;
    }

    public void handleTurn(Player p, List<Enemy> enemies, ItemBag bag, Scanner scanner) {
        if (p.isStunned()) {
            System.out.println("\n!! " + p.getName() + " is stunned and cannot move !!");
            return;
        }

        boolean turnComplete = false;

        while (!turnComplete) {
            display.showMenu(p, bag);
            int choice = -1;
            try {
                choice = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                scanner.next();
            }

            switch (choice) {
                case 1: // Basic Attack
                    System.out.print("Select target (1-" + enemies.size() + "): ");
                    int t1 = -1;
                    try {
                        t1 = scanner.nextInt() - 1;
                    } catch (java.util.InputMismatchException e) {
                        scanner.next();
                    }
                    if (t1 >= 0 && t1 < enemies.size()) {
                        new BasicAttack().execute(p, List.of(enemies.get(t1)));
                        turnComplete = true;
                    } else {
                        System.out.println(">> Invalid target, try again.");
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
                        int t2 = -1;
                        try {
                            t2 = scanner.nextInt() - 1;
                        } catch (java.util.InputMismatchException e) {
                            scanner.next();
                        }
                        if (t2 >= 0 && t2 < enemies.size()) {
                            p.useSpecial(List.of(enemies.get(t2)));
                            turnComplete = true;
                        } else {
                            System.out.println(">> Invalid target, try again.");
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
                    int itemIdx = -1;
                    try {
                        itemIdx = scanner.nextInt() - 1;
                    } catch (java.util.InputMismatchException e) {
                        scanner.next();
                    }
                    if (itemIdx < 0 || itemIdx >= bag.getItemCount()) {
                        System.out.println(">> Invalid item, try again.");
                        break;
                    }
                    Item selected = bag.getItem(itemIdx);
                    List<Combatant> targets;
                    if (selected instanceof PowerStone && p instanceof Warrior) {
                        System.out.print("Select target for Shield Bash via Power Stone (1-"
                                + enemies.size() + "): ");
                        int t4 = -1;
                        try {
                            t4 = scanner.nextInt() - 1;
                        } catch (java.util.InputMismatchException e) {
                            scanner.next();
                        }
                        if (t4 < 0 || t4 >= enemies.size()) {
                            System.out.println(">> Invalid target, try again.");
                            break;
                        }
                        targets = List.of(enemies.get(t4));
                    } else if (selected instanceof PowerStone && p instanceof Wizard) {
                        targets = new ArrayList<>(enemies);
                    } else {
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
