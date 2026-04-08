package actions;

import entities.Combatant;
import effects.Stun;
import java.util.List;

public class ShieldBash implements Action {
    @Override
    public void execute(Combatant performer, List<Combatant> targets) {
        Combatant target = targets.get(0);

        // Calculate and deal damage
        int atk = performer.getAttack().getValue();
        int def = target.getTotalDefense();
        int damage = Math.max(0, atk - def);
        target.getHealth().takeDamage(damage);

        // Apply the Stun status (2 rounds: Current + Next)
        target.addEffect(new Stun(2));

        System.out.println("\n>> " + performer.getName() + " performs Shield Bash on " + target.getName() + "!");
        System.out.println(performer.getName() + " struck (" + atk + " ATK - " + def + " DEF) = " + damage + " DMG to " + target.getName());
        System.out.println(">> " + target.getName() + " now has " + target.getHealth().getValue() + " HP left.");
        System.out.println(">> EFFECT: " + target.getName() + " is stunned for the current and next round!");
        System.out.println();
    }

    @Override
    public String getName() { return "Shield Bash"; }
}