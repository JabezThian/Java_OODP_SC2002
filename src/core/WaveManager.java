package core;

import difficulty.Level;
import entities.Enemy;
import java.util.ArrayList;
import java.util.List;

public class WaveManager {
    private final List<Wave> waves;
    private int currentWaveIndex;
    private final List<Enemy> activeEnemies;

    public WaveManager(Level level) {
        this.waves = new ArrayList<>();
        this.waves.add(level.getInitialWave());
        if (level.hasBackupWave()) {
            this.waves.add(level.getBackupWave());
        }
        this.currentWaveIndex = 0;
        this.activeEnemies = new ArrayList<>(waves.get(0).getEnemies());
    }

    public List<Enemy> getActiveEnemies() {
        return activeEnemies;
    }

    public void removeDeadEnemies() {
        activeEnemies.removeIf(e -> e.getHealth().isDead());
    }

    public boolean isCurrentWaveCleared() {
        return activeEnemies.isEmpty();
    }

    public boolean hasNextWave() {
        return currentWaveIndex + 1 < waves.size();
    }

    public List<Enemy> triggerNextWave() {
        currentWaveIndex++;
        List<Enemy> newEnemies = waves.get(currentWaveIndex).getEnemies();
        activeEnemies.addAll(newEnemies);
        return newEnemies;
    }

    public boolean isAllDefeated() {
        return activeEnemies.isEmpty() && !hasNextWave();
    }
}
