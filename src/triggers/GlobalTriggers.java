package triggers;

import effects.StatusEffect;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GlobalTriggers {
    
    private static GlobalTriggers onlyObject = null;
    private Map<GlobalTriggerTypes, List<StatusEffect>> globalTriggerList = new HashMap<>();

    public GlobalTriggers() {
        for(GlobalTriggerTypes condition : GlobalTriggerTypes.values()) {
            globalTriggerList.put(condition, new ArrayList<>());
        }
    }

    public static GlobalTriggers getGlobalTriggerObject() {
        if(onlyObject == null) {
            onlyObject = new GlobalTriggers();
        }
        return onlyObject;
    }

    public void addGlobalTrigger(GlobalTriggerTypes condition, StatusEffect status) {
        globalTriggerList.get(condition).add(status);
        Collections.sort(globalTriggerList.get(condition), Comparator.comparingInt(StatusEffect::getPriority));
    }

    public void removeGlobalTrigger(GlobalTriggerTypes condition, StatusEffect status) {
        globalTriggerList.get(condition).remove(status);
    }

    public List<StatusEffect> getGlobalTriggers(GlobalTriggerTypes condition) {
        return globalTriggerList.get(condition);
    }

    public void trigger(GlobalTriggerTypes condition) {
        for(StatusEffect status : new ArrayList<>(globalTriggerList.get(condition))) {
            switch(condition) {
                case ON_ROUND_START -> status.onRoundStart();
                case ON_ROUND_END -> status.onRoundEnd();
                default -> {}
            }
        }
    }
}