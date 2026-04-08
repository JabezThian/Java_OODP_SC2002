package ui;

import entities.*;
import java.util.List;

public class BattleDisplay {
    public void showRoundHeader(int round) {
        System.out.println("\n########################################");
        System.out.println("           ROUND " + round);
        System.out.println("########################################");
    }

    public void showStats(Player p, List<Enemy> enemies) {
        System.out.println("\n--- PARTY STATUS ---");
        System.out.printf("[PLAYER] %-10s | HP: %-3d | ATK: %-3d | DEF: %-2d%n",
                p.getName(), p.getHealth().getValue(), p.getAttack().getValue(), p.getDefense().getValue());

        System.out.println("\n--- ENEMIES ---");
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            // Displaying (i + 1) so the user sees 1, 2, 3
            System.out.printf("%d. %-12s | HP: %-3d | ATK: %-3d | DEF: %-2d%n",
                    (i + 1), e.getName(), e.getHealth().getValue(), e.getAttack().getValue(), e.getDefense().getValue());
        }
    }

    public void showMenu(Player p) {
        System.out.println("\n--- YOUR TURN: " + p.getName() + " ---");
        System.out.println("1. Basic Attack");
        if (p.getSpecialCooldown() == 0) {
            System.out.println("2. Special Skill: " + p.getSpecialSkillName());
        } else {
            System.out.println("2. [LOCKED] Special Skill (" + p.getSpecialCooldown() + " turns left)");
        }
        System.out.println("3. Defend (Reduce incoming damage)");
        System.out.print("Choose an action: ");
    }

    public void showTurnOrder(List<Combatant> order) {
        System.out.print("TURN ORDER: ");
        for (int i = 0; i < order.size(); i++) {
            System.out.print(order.get(i).getName() + " (" + order.get(i).getSpeed().getValue() + ")");
            if (i < order.size() - 1) System.out.print(" -> ");
        }
        System.out.println("\n");
    }
}