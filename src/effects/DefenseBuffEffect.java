package effects;

import entities.Combatant;

public class DefenseBuffEffect extends StatusEffect {
    private int amount;

    public DefenseBuffEffect(int amount, int duration) {
        super("Defending", duration);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
