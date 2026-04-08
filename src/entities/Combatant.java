package entities;

import actions.*;
import attributes.*;
import effects.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Combatant {
    private String name;
    private HealthAttribute health;
    private AttackAttribute attack;
    private DefenseAttribute defense;
    private speedAttribute speed;

    public Combatant(String name, int health, int attack, int defense, int speed) {
        this.name = name;
        this.health = new HealthAttribute(health);
        this.attack = new AttackAttribute(attack);
        this.defense = new DefenseAttribute(defense);
        this.speed = new speedAttribute(speed);
    }

    protected List<StatusEffect> activeEffects = new ArrayList<>();

    public void addEffect(StatusEffect effect) {
        activeEffects.add(effect);
    }

    public boolean isStunned() {
        return activeEffects.stream().anyMatch(e -> e instanceof Stun);
    }

    public int getTotalAttack() {
        int bonus = activeEffects.stream()
                .filter(e -> e instanceof AttackBuffEffect)
                .mapToInt(e -> ((AttackBuffEffect) e).getAmount())
                .sum();
        return this.attack.getValue() + bonus;
    }

    public void updateEffects() {
        // Tick down durations
        for (StatusEffect effect : activeEffects) {
            effect.decrementDuration(); // duration goes from 2 -> 1, then 1 -> 0
        }

        // Remove expired effects (duration == 0)
        // Note: If you used -1 for permanent buffs, they won't be removed
        activeEffects.removeIf(effect -> effect.isExpired());
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
        int bonus = 0;
        // Look through all active effects for any Defense Buffs
        for (StatusEffect effect : activeEffects) {
            if (effect instanceof DefenseBuffEffect) {
                bonus += ((DefenseBuffEffect) effect).getBonusAmount();
            }
        }
        // Return the base stat + the temporary buff
        return this.defense.getValue() + bonus;
    }
}