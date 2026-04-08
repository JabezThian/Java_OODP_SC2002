package core;

import entities.*;
import actions.*;
import ui.*;
import java.util.ArrayList;
import java.util.List;

public class LevelFactory {
    public static List<Enemy> createLevel(int choice) {
        List<Enemy> enemies = new ArrayList<>();
        switch (choice) {
            case 1: // Easy
                System.out.println(">> Level: Easy (3 Goblins)");
                enemies.add(new Goblin("Goblin A"));
                enemies.add(new Goblin("Goblin B"));
                enemies.add(new Goblin("Goblin C"));
                break;
            case 2: // Medium
                System.out.println(">> Level: Medium (1 Goblin, 1 Wolf)");
                enemies.add(new Goblin("Goblin A"));
                enemies.add(new Wolf("Wolf A"));
                break;
            case 3: // Hard
                System.out.println(">> Level: Hard (2 Goblins - Higher Stats)");
                enemies.add(new Goblin("Goblin A"));
                enemies.add(new Goblin("Goblin B"));
                break;
            default:
                return null;
        }
        return enemies;
    }
}