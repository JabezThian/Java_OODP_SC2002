package core;

import difficulty.*;
import entities.*;
import item.*;

public class GameSetupController {

    public Player createPlayer(String name, int classChoice) {
        switch (classChoice) {
            case 1:
                System.out.println(">> You chose Warrior!");
                return new Warrior(name);
            case 2:
                System.out.println(">> You chose Wizard!");
                return new Wizard(name);
            default:
                throw new IllegalArgumentException("Invalid class choice: " + classChoice);
        }
    }

    public Item createItem(int itemChoice) {
        switch (itemChoice) {
            case 1: return new Potion();
            case 2: return new PowerStone();
            case 3: return new SmokeBomb();
            default:
                throw new IllegalArgumentException("Invalid item choice: " + itemChoice);
        }
    }
    
    public Level createLevel(int difficultyChoice) {
        Level level;
        switch (difficultyChoice) {
            case 1: level = new EasyLevel();  break;
            case 2: level = new MediumLevel(); break;
            case 3: level = new HardLevel();  break;
            default:
                throw new IllegalArgumentException("Invalid difficulty choice: " + difficultyChoice);
        }
        System.out.println(">> Difficulty: " + level.getDifficultyName()
                + " | " + level.getInitialEnemyDescription());
        return level;
    }
}
