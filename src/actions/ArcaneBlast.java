package actions;

import entities.Combatant;
import effects.ArcaneBlastStatus;
import java.util.List;
import core.DamageCalculator;

public class ArcaneBlast implements Action {
    @Override
    public void execute(Combatant performer, List<Combatant> targets) {
        int kills = 0;
        for (Combatant target : targets) {
            if (target.getHealth().isDead()) continue;

            int hpBefore = target.getHealth().getValue();
            new DamageCalculator(performer, target).executeDamage();

            if (target.getHealth().isDead() && hpBefore > 0) {
                kills++;
            }
        }

        if (kills > 0) {
            new ArcaneBlastStatus(performer, -1);
            System.out.println(performer.getName() + " absorbed souls! Attack + " + (kills * 10));
        }
    }
    public String getName() { return "Arcane Blast"; }
}
