package effects;

import entities.Combatant;
import modifier.AttackModifier;
import triggers.LocalTriggerTypes;

public class ArcaneBlastStatus extends StatusEffect {

    private AttackModifier attackModifier = new AttackModifier(10);

    public ArcaneBlastStatus(Combatant affectedEntity, int duration) {
        super(affectedEntity, duration);
        this.setStatusName("Arcane Blast");
        this.setPriority(0);
        this.addLocalTriggerCondition(LocalTriggerTypes.ON_MODIFY_ATTACK);
        this.applyStatus();
    }

    public int onModifyAttack(int attack) {
        return attackModifier.applyAttackModifier(attack, '+');
    }

}