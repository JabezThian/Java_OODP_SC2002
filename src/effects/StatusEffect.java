package effects;

import entities.Combatant;
import triggers.LocalTriggerTypes;
import triggers.GlobalTriggerTypes;
import triggers.GlobalTriggers;

import java.util.List;
import java.util.ArrayList;

public abstract class StatusEffect {

    private String statusName;
    private int statusDuration = 2;
    private int curStatusDuration = 2;
    private Combatant affectedEntity;
    private int priority = 1;
    private boolean isExpired = false;
    private List<LocalTriggerTypes> localTriggerConditions = new ArrayList<>();
    private List<GlobalTriggerTypes> globalTriggerConditions = new ArrayList<>();

    public StatusEffect(Combatant affectedEntity, int duration) {
        this.affectedEntity = affectedEntity;
        this.statusDuration = duration;
        this.curStatusDuration = duration;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String name) {
        this.statusName = name;
    }

    public int getStatusDuration() {
        return statusDuration;
    }

    public void setStatusDuration(int duration) {
        this.statusDuration = duration;
    }

    public int getCurStatusDuration() {
        return curStatusDuration;
    }

    public void setCurStatusDuration(int duration) {
        this.curStatusDuration = duration;
    }

    public Combatant getAffectedEntity() {
        return affectedEntity;
    }

    public void setAffectedEntity(Combatant affectedEntity) {
        this.affectedEntity = affectedEntity;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(boolean exp) {
        this.isExpired = exp;
    }

    public void updateCurStatusDuration() {
        this.setCurStatusDuration(Math.max((this.getCurStatusDuration() - 1), 0));
        if (this.getCurStatusDuration() <= 0) {
            this.setIsExpired(true);
            this.removeStatus();
    }
    }

    public void applyStatus() {
        if (this.getAffectedEntity() == null) {
            System.out.println(this.getStatusName() + " cannot be applied!");
            return;
        }
        for(LocalTriggerTypes condition : this.localTriggerConditions) {
            this.getAffectedEntity().getLocalTriggerList().addLocalTrigger(condition, this);
        }
        for(GlobalTriggerTypes condition : this.globalTriggerConditions) {
            GlobalTriggers.getGlobalTriggerObject().addGlobalTrigger(condition, this);
        }
        this.getAffectedEntity().addEffect(this);
    }

    public void removeStatus() {
        if (this.getAffectedEntity() == null) {
            System.out.println(this.getStatusName() + " cannot be removed!");
            return;
        }
        for(LocalTriggerTypes condition : this.localTriggerConditions) {
            this.getAffectedEntity().getLocalTriggerList().removeLocalTrigger(condition, this);
        }
        for(GlobalTriggerTypes condition : this.globalTriggerConditions) {
            GlobalTriggers.getGlobalTriggerObject().removeGlobalTrigger(condition, this);
        }
        this.getAffectedEntity().removeEffect(this);
    }

    public void addLocalTriggerCondition(LocalTriggerTypes condition) {
        this.localTriggerConditions.add(condition);
    }

    public void removeLocalTriggerCondition(LocalTriggerTypes condition) {
        this.localTriggerConditions.remove(condition);
    }

    public void addGlobalTriggerCondition(GlobalTriggerTypes condition) {
        this.globalTriggerConditions.add(condition);
    }

    public void removeGlobalTriggerCondition(GlobalTriggerTypes condition) {
        this.globalTriggerConditions.remove(condition);
    }

    public void onTurnStart() {}
    public void onTurnEnd() {}
    public void onAttack(Combatant defender) {}
    public void onGettingAttacked(Combatant attacker) {}
    public void onDeath() {}
    public void onKill(Combatant defender) {}
    public int onIncomingDamage(int damage) {
        return damage;
    }
    public int onOutgoingDamage(int damage) {
        return damage;
    }
    public int onModifyHP(int hp) {
        return hp;
    }
    public int onModifyAttack(int attack) {
        return attack;
    }
    public int onModifyDefence(int defence) {
        return defence;
    }
    public int onModifySpeed(int speed) {
        return speed;
    }
    public void onRoundStart() {}
    public void onRoundEnd() {}
}