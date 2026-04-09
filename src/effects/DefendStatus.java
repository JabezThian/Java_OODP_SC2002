package effects;

import entities.Combatant;
import modifier.DefenceModifier;
import triggers.LocalTriggerTypes;
import triggers.GlobalTriggerTypes;


public class DefendStatus extends StatusEffect {

    private DefenceModifier defenceModifier = new DefenceModifier(10);

    public DefendStatus(Combatant affectedEntity, int duration) {
        super(affectedEntity, duration);
        this.setStatusName("Defend");
        this.setPriority(0);
        this.addLocalTriggerCondition(LocalTriggerTypes.ON_MODIFY_DEFENCE);
        this.addGlobalTriggerCondition(GlobalTriggerTypes.ON_ROUND_END);
        this.applyStatus();
    }

    public int onModifyDefence(int defence) {
        return defenceModifier.applyDefenceModifier(defence, '+');
    }

    public void onRoundEnd() {
        this.updateCurStatusDuration();
    }
}