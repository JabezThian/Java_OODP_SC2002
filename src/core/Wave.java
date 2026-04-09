package core;

import entities.Enemy;
import java.util.ArrayList;
import java.util.List;

public class Wave {
    private final List<Enemy> enemies;

    public Wave(List<Enemy> enemies) {
        this.enemies = new ArrayList<>(enemies);
    }

    public List<Enemy> getEnemies() {
        return new ArrayList<>(enemies);
    }
}
