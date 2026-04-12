package entities;

import actions.*;
import attributes.*;
import effects.*;

public abstract class Enemy extends Combatant {
    public Enemy(String name, int health, int attack, int defense, int speed) {
        super(name, health, attack, defense, speed);
    }
}
