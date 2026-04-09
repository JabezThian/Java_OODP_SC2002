package difficulty;

import core.Wave;
import entities.Goblin;
import entities.Wolf;
import java.util.Arrays;

public class MediumLevel implements Level {

    @Override
    public String getDifficultyName() {
        return "Medium";
    }

    @Override
    public String getInitialEnemyDescription() {
        return "Initial: 1 Goblin, 1 Wolf | Backup: 2 Wolves";
    }

    @Override
    public Wave getInitialWave() {
        return new Wave(Arrays.asList(
                new Goblin("Goblin A"),
                new Wolf("Wolf A")
        ));
    }

    @Override
    public boolean hasBackupWave() {
        return true;
    }

    @Override
    public Wave getBackupWave() {
        return new Wave(Arrays.asList(
                new Wolf("Wolf B"),
                new Wolf("Wolf C")
        ));
    }
}
