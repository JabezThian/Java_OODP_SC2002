package actions;

import entities.Combatant;
import effects.AttackBuffEffect;
import java.util.List;

public class ArcaneBlast implements Action {
    public void execute(Combatant performer, List<Combatant> targets) {
        int kills = 0;
        for (Combatant target : targets) {
            if (target.getHealth().isDead()) continue;

            int atk = performer.getAttack().getValue();
            int def = target.getTotalDefense();
            int damage = Math.max(0, atk - def);

            int hpBefore = target.getHealth().getValue();
            performer.basicAttack(target);

            if (target.getHealth().isDead() && hpBefore > 0) {
                kills++;
            }
        }

        if (kills > 0) {
            // Duration -1 means it never expires (until end of game)
            performer.addEffect(new AttackBuffEffect(kills * 10, -1));
            System.out.println(performer.getName() + " absorbed souls! Attack + " + (kills * 10));
        }
    }
    public String getName() { return "Arcane Blast"; }
}