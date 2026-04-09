package modifier;

public class IncomingDmgModifier {

    private double incomingDamageModifier = 1.0;

    public IncomingDmgModifier(double incomingDamageModifier) {
        this.incomingDamageModifier = incomingDamageModifier;
    }

    public double getIncomingDamageModifier() {
        return incomingDamageModifier;
    }

    public void setIncomingDamageModifier(double incomingDamageModifier) {
        this.incomingDamageModifier = incomingDamageModifier;
    }

    public int applyIncomingDamageModifier(int incomingDamage, char operation) {
        switch(operation) {
            case '+':
                return (int)(incomingDamage + this.getIncomingDamageModifier());
            case '-':
                return (int)(incomingDamage - this.getIncomingDamageModifier());
            case '/':
                return (int)(incomingDamage / this.getIncomingDamageModifier());
            case '*':
                return (int)(incomingDamage * this.getIncomingDamageModifier());
            default:
                return incomingDamage;
        }
    }
}