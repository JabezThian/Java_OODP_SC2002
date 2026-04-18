package ui;

import entities.Combatant;
import entities.Enemy;
import entities.Player;
import effects.StatusEffect;
import item.ItemBag;
import java.util.List;

public class BattleDisplay {

    public void showRoundHeader(int round) {
        System.out.println("\n########################################");
        System.out.println("               ROUND " + round);
        System.out.println("########################################");
    }

    public void showStats(Player p, List<Enemy> enemies, ItemBag bag) {
        System.out.println("\n--- PARTY STATUS ---");
        System.out.printf("[PLAYER] %-10s | HP: %3d/%-3d | ATK: %-3d | DEF: %-2d | SPD: %-2d%n",
                p.getName(),
                p.getHealth().getValue(),
                p.getHealth().getMaxHealth(),
                p.getTotalAttack(),
                p.getTotalDefense(),
                p.getTotalSpeed());

        // Cooldown info
        if (p.getSpecialCooldown() == 0) {
            System.out.printf("           Special [%-12s]: READY%n", p.getSpecialSkillName());
        } else {
            System.out.printf("           Special [%-12s]: Cooldown %d turn(s)%n",
                    p.getSpecialSkillName(), p.getSpecialCooldown());
        }

        // Item info
        if (bag != null && !bag.isEmpty()) {
            System.out.print("           Items: ");
            List<item.Item> items = bag.getItems();
            for (int i = 0; i < items.size(); i++) {
                System.out.print(items.get(i).getName() + " (x" + items.get(i).getQuantity() + ")");
                if (i < items.size() - 1) System.out.print(", ");
            }
            System.out.println();
        } else {
            System.out.println("           Items: None");
        }

        showStatusEffects(p);

        System.out.println("\n--- ENEMIES ---");
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            String stunTag = e.isStunned() ? " [STUNNED]" : "";
            System.out.printf("%d. %-12s | HP: %3d/%-3d | ATK: %-3d | DEF: %-2d | SPD: %-2d%s%n",
                    (i + 1),
                    e.getName(),
                    e.getHealth().getValue(),
                    e.getHealth().getMaxHealth(),
                    e.getTotalAttack(),
                    e.getTotalDefense(),
                    e.getTotalSpeed(),
                    stunTag);
        }
    }

    /** Legacy overload — called without an ItemBag (no item display). */
    public void showStats(Player p, List<Enemy> enemies) {
        showStats(p, enemies, null);
    }

    public void showMenu(Player p, ItemBag bag) {
        System.out.println("\n--- YOUR TURN: " + p.getName() + " ---");
        System.out.println("  1. Basic Attack");
        if (p.getSpecialCooldown() == 0) {
            System.out.println("  2. Special Skill: " + p.getSpecialSkillName());
        } else {
            System.out.println("  2. [LOCKED] Special Skill (" + p.getSpecialCooldown() + " turn(s) left)");
        }
        System.out.println("  3. Defend (+10 DEF this round and next)");
        if (bag != null && !bag.isEmpty()) {
            System.out.println("  4. Use Item");
        }
        System.out.print("Choose an action: ");
    }

    /** Legacy overload — called without an ItemBag. */
    public void showMenu(Player p) {
        showMenu(p, null);
    }

    public void showItemMenu(ItemBag bag) {
        bag.displayItems();
        System.out.print("Select item number: ");
    }

    public void showTurnOrder(List<Combatant> order) {
        System.out.print("TURN ORDER: ");
        for (int i = 0; i < order.size(); i++) {
            System.out.print(order.get(i).getName() + " (SPD " + order.get(i).getSpeed().getValue() + ")");
            if (i < order.size() - 1) System.out.print(" → ");
        }
        System.out.println("\n");
    }

    public void showBackupSpawn(List<Enemy> spawned) {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║       !! BACKUP WAVE INCOMING !!     ║");
        System.out.println("╚══════════════════════════════════════╝");
        for (Enemy e : spawned) {
            System.out.printf("  + %-12s | HP: %3d | ATK: %-3d | DEF: %-2d | SPD: %-2d%n",
                    e.getName(),
                    e.getHealth().getValue(),
                    e.getAttack().getValue(),
                    e.getDefense().getValue(),
                    e.getSpeed().getValue());
        }
        System.out.println();
    }

    public void showStatusEffects(Combatant c) {
        List<StatusEffect> effects = c.getActiveEffects();
        if (effects.isEmpty()) return;
        System.out.print("           Status: ");
        for (int i = 0; i < effects.size(); i++) {
            System.out.print(effects.get(i).getStatusName());
            if (i < effects.size() - 1) System.out.print(", ");
        }
        System.out.println();
    }
}
