package entities;

import actions.*;
import attributes.*;
import effects.*;

public abstract class Player extends Combatant {
    protected int specialCooldown = 0;
    protected Action specialSkill;

    public Player(String name, int health, int attack, int defense, int speed, Action skill) {
        super(name, health, attack, defense, speed);
        this.specialSkill = skill;
    }

    public void useSpecial(java.util.List<Combatant> targets) {
        if (specialCooldown == 0) {
            specialSkill.execute(this, targets);
            specialCooldown = 3; // Cooldown including current turn
        } else {
            System.out.println("Skill on cooldown! " + specialCooldown + " turns left.");
        }
    }

    public void updateCooldown() { if (specialCooldown > 0) specialCooldown--; }

    public int getSpecialCooldown() {
        return specialCooldown;
    }

    public void setSpecialCooldown(int value) {
        this.specialCooldown = value;
    }

    public String getSpecialSkillName() {
        return specialSkill.getName();
    }
}