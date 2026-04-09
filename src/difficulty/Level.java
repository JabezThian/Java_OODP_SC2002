package difficulty;

import core.Wave;

public interface Level {
    String getDifficultyName();
    String getInitialEnemyDescription();
    Wave getInitialWave();
    boolean hasBackupWave();
    Wave getBackupWave();
}
