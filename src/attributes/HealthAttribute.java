public class HealthAttribute extends Attribute{
    private int maxhealth;

    public HealthAttribute(int value) {
        super(value);
        this.maxhealth = value;
    }

    public void takeDamage(int amount) {
        this.value = Math.max(0, this.value - amount);
    }

//    public void heal(int amount) {
//        this.value = Math.min(maxHealth, this.value + amount);
//    }

    public boolean isDead() {
        return this.value <= 0;
    }
}
