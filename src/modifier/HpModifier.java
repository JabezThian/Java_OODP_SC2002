package modifier;

public class HpModifier {

    private double hpModifier = 1.0;

    public HpModifier(double hpModifier) {
        this.hpModifier = hpModifier;
    }

    public double getHpModifier() {
        return hpModifier;
    }

    public void setHpModifier(double hpModifier) {
        this.hpModifier = hpModifier;
    }

    public int applyHpModifier(int hp, char operation) {
        switch(operation) {
            case '+':
                return (int)(hp + this.getHpModifier());
            case '-':
                return (int)(hp - this.getHpModifier());
            case '/':
                return (int)(hp / this.getHpModifier());
            case '*':
                return (int)(hp * this.getHpModifier());
            default:
                return hp;
        }
    }
}