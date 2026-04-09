package actions;
import entities.Combatant;
import java.util.List;
import core.DamageCalculator;

public class BasicAttack implements Action {
    @Override
    public void execute(Combatant performer, List<Combatant> targets) {
        if (targets == null || targets.isEmpty()) return;
        Combatant target = targets.get(0);
        new DamageCalculator(performer, target).executeDamage();
    }
    public String getName() { return "Basic Attack"; }
}
