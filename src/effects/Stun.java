package effects;

import entities.Combatant;

public class Stun extends StatusEffect {
    public Stun(int duration) {
        super("Stunned", duration);
    }
    // Combatant uses this to check when to skip a turn
}
