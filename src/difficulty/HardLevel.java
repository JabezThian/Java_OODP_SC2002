package difficulty;

import core.Wave;
import entities.Goblin;
import entities.Wolf;
import java.util.Arrays;

public class HardLevel implements Level {

    @Override
    public String getDifficultyName() {
        return "Hard";
    }

    @Override
    public String getInitialEnemyDescription() {
        return "Initial: 2 Goblins | Backup: 1 Goblin, 2 Wolves";
    }

    @Override
    public Wave getInitialWave() {
        return new Wave(Arrays.asList(
                new Goblin("Goblin A"),
                new Goblin("Goblin B")
        ));
    }

    @Override
    public boolean hasBackupWave() {
        return true;
    }

    @Override
    public Wave getBackupWave() {
        return new Wave(Arrays.asList(
                new Goblin("Goblin C"),
                new Wolf("Wolf A"),
                new Wolf("Wolf B")
        ));
    }
}
