package effects;

import entities.Combatant;

public abstract class StatusEffect {
    protected String name;
    protected int duration;

    public StatusEffect(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public void decrementDuration() {
        if (duration > 0) duration--;
    }

    public boolean isExpired() {
        return duration == 0;
    }

    public String getName() {
        return name;
    }
}
