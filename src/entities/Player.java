package entities;

public abstract class Player extends Combatant {
    protected int specialCooldown = 0;
    protected Action specialSkill;

    public Player(String name, int health, int attack, int defense, int speed, Action skill) {
        super(name, health, attack, defense, speed);
        this.specialSkill = skill;
    }
}