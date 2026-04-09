package core;

import difficulty.EasyLevel;
import difficulty.HardLevel;
import difficulty.Level;
import difficulty.MediumLevel;

public class LevelFactory {
    public static Level createLevel(int choice) {
        switch (choice) {
            case 1: return new EasyLevel();
            case 2: return new MediumLevel();
            case 3: return new HardLevel();
            default: return null;
        }
    }
}
