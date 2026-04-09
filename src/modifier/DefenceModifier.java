package modifier;

public class DefenceModifier {

    private double defenceModifier = 1.0;

    public DefenceModifier(double defenceModifier) {
        this.defenceModifier = defenceModifier;
    }

    public double getDefenceModifier() {
        return defenceModifier;
    }

    public void setDefenceModifier(double defenceModifier) {
        this.defenceModifier = defenceModifier;
    }

    public int applyDefenceModifier(int defence, char operation) {
        switch(operation) {
            case '+':
                return (int)(defence + this.getDefenceModifier());
            case '-':
                return (int)(defence - this.getDefenceModifier());
            case '/':
                return (int)(defence / this.getDefenceModifier());
            case '*':
                return (int)(defence * this.getDefenceModifier());
            default:
                return defence;
        }
    }
}