package actions;
import entities.Combatant;
import java.util.List;

public interface Action {
    void execute(Combatant performer, List<Combatant> targets);
    String getName();
}
