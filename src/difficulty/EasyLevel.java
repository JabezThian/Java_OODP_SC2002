package difficulty;

import core.Wave;
import entities.Goblin;
import java.util.Arrays;

public class EasyLevel implements Level {

    @Override
    public String getDifficultyName() {
        return "Easy";
    }

    @Override
    public String getInitialEnemyDescription() {
        return "Initial: 3 Goblins | No backup";
    }

    @Override
    public Wave getInitialWave() {
        return new Wave(Arrays.asList(
                new Goblin("Goblin A"),
                new Goblin("Goblin B"),
                new Goblin("Goblin C")
        ));
    }

    @Override
    public boolean hasBackupWave() {
        return false;
    }

    @Override
    public Wave getBackupWave() {
        return null;
    }
}
