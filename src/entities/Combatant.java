package entities;

import actions.*;
import attributes.*;
import effects.*;
import triggers.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Combatant {
    private String name;
    private HealthAttribute health;
    private AttackAttribute attack;
    private DefenseAttribute defense;
    private speedAttribute speed;
    private LocalTriggers localTriggerList;

    public Combatant(String name, int health, int attack, int defense, int speed) {
        this.name = name;
        this.health = new HealthAttribute(health);
        this.attack = new AttackAttribute(attack);
        this.defense = new DefenseAttribute(defense);
        this.speed = new speedAttribute(speed);
        this.localTriggerList = new LocalTriggers();
    }

    protected List<StatusEffect> activeEffects = new ArrayList<>();

    public LocalTriggers getLocalTriggerList() {
        return  this.localTriggerList;
    }

    public void addEffect(StatusEffect effect) {
        activeEffects.add(effect);
    }

    public void removeEffect(StatusEffect effect) {
        activeEffects.remove(effect);
    }

    public boolean isStunned() {
        return activeEffects.stream().anyMatch(e -> e instanceof Stun);
    }

    public int getTotalAttack() {
        int attack = this.attack.getValue();

        for (StatusEffect status : localTriggerList.getLocalTriggers(LocalTriggerTypes.ON_MODIFY_ATTACK)) {
            attack = status.onModifyAttack(attack);
            }
        return attack;
        }

    // Update basicAttack to use getTotalAttack()
    public void basicAttack(Combatant target) {
        int damage = Math.max(0, this.getTotalAttack() - target.getDefense().getValue());
        target.getHealth().takeDamage(damage);
        System.out.println(this.name + " deals " + damage + " damage.");
    }

    // Getters for the attribute objects so other combatants can access them
    public HealthAttribute getHealth() {
        return health;
    }

    public AttackAttribute getAttack() {
        return attack;
    }

    public DefenseAttribute getDefense() {
        return defense;
    }

    public speedAttribute getSpeed() {
        return speed;
    }

    public String getName() {
        return name;
    }

    public int getTotalDefense() {
        int defence = this.defense.getValue();

        for (StatusEffect status : localTriggerList.getLocalTriggers(LocalTriggerTypes.ON_MODIFY_DEFENCE)) {
            defence = status.onModifyDefence(defence);
            }
        return defence;
        }

    public int getTotalSpeed() {
        int speed = this.speed.getValue();

        for (StatusEffect status : localTriggerList.getLocalTriggers(LocalTriggerTypes.ON_MODIFY_SPEED)) {
            speed = status.onModifySpeed(speed);
            }
        return speed;
        }
}