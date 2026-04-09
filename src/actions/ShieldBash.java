package actions;

import entities.Combatant;
import effects.Stun;
import java.util.List;
import core.DamageCalculator;

public class ShieldBash implements Action {
    @Override
    public void execute(Combatant performer, List<Combatant> targets) {
        if (targets == null || targets.isEmpty()) return;
        Combatant target = targets.get(0);
        // Calculate and deal damage
        new DamageCalculator(performer, target).executeDamage();
        // Apply the Stun status (2 rounds: Current + Next)
        new Stun(target, 2);
    }

    @Override
    public String getName() { return "Shield Bash"; }
}
