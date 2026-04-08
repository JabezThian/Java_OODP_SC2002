package actions;

import entities.Combatant;
import effects.DefenseBuffEffect;
import java.util.List;

public class DefendAction implements Action {
    @Override
    public void execute(Combatant performer, List<Combatant> targets) {
        // Apply +10 Defense for 1 round
        performer.addEffect(new DefenseBuffEffect(10, 1));
        System.out.println(">> " + performer.getName() + " raises their shield! Defense increased by 10 for this round.");
    }

    @Override
    public String getName() { return "Defend"; }
}