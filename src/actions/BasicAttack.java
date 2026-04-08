package actions;
import entities.Combatant;
import java.util.List;

public class BasicAttack implements Action {
    public void execute(Combatant performer, List<Combatant> targets) {
        Combatant target = targets.get(0);

        int atk = performer.getAttack().getValue();
        int def = target.getTotalDefense();

        int damage = Math.max(0, atk - def);
        target.getHealth().takeDamage(damage);

        // Display status
        System.out.println(performer.getName() + " struck (" + atk + " ATK - " + def + " DEF) = " + damage + " DMG to " + target.getName());
        System.out.println(">> " + target.getName() + " now has " + target.getHealth().getValue() + " HP left.");
        System.out.println();
    }
    public String getName() { return "Basic Attack"; }
}