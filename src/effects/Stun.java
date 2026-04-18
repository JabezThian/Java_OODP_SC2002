package effects;

import entities.Combatant;
import triggers.LocalTriggerTypes;

public class Stun extends StatusEffect {

    public Stun(Combatant affectedEntity, int duration) {
        super(affectedEntity, duration);
        this.setStatusName("Stun");
        this.setPriority(1);
        this.addLocalTriggerCondition(LocalTriggerTypes.ON_TURN_END);
        this.applyStatus();
    }

    public void onTurnEnd() {
        this.updateCurStatusDuration();
    }
    
}