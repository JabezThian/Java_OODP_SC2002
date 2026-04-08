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
}