package triggers;

import effects.StatusEffect;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LocalTriggers {
    
    private Map<LocalTriggerTypes, List<StatusEffect>> localTriggerList = new HashMap<>();

    public LocalTriggers() {
        for(LocalTriggerTypes condition : LocalTriggerTypes.values()) {
            localTriggerList.put(condition, new ArrayList<>());
        }
    }

    public void addLocalTrigger(LocalTriggerTypes condition, StatusEffect status) {
        localTriggerList.get(condition).add(status);
        Collections.sort(localTriggerList.get(condition), Comparator.comparingInt(StatusEffect::getPriority));
    }

    public void removeLocalTrigger(LocalTriggerTypes condition, StatusEffect status) {
        localTriggerList.get(condition).remove(status);
    }

    public List<StatusEffect> getLocalTriggers(LocalTriggerTypes condition) {
        return localTriggerList.get(condition);
    }

    public void trigger(LocalTriggerTypes condition) {
        for(StatusEffect status : new ArrayList<>(localTriggerList.get(condition))) {
            switch(condition) {
                case ON_TURN_START -> status.onTurnStart();
                case ON_TURN_END -> status.onTurnEnd();
                case ON_DEATH -> status.onDeath();
                default -> {}
            }
        }
    }

    public int trigger(LocalTriggerTypes condition, int value) {
        int modifiedValue = value;
        for(StatusEffect status : new ArrayList<>(localTriggerList.get(condition))) {
            switch(condition) {
                case ON_MODIFY_ATTACK -> modifiedValue = status.onModifyAttack(modifiedValue);
                case ON_MODIFY_DEFENCE -> modifiedValue = status.onModifyDefence(modifiedValue);
                case ON_MODIFY_HP -> modifiedValue = status.onModifyHP(modifiedValue);
                case ON_MODIFY_SPEED -> modifiedValue = status.onModifySpeed(modifiedValue);
                case ON_OUTGOING_DAMAGE -> modifiedValue = status.onOutgoingDamage(modifiedValue);
                case ON_INCOMING_DAMAGE -> modifiedValue = status.onIncomingDamage(modifiedValue);
                default -> {}
            }
        }
        return modifiedValue;
    }
}