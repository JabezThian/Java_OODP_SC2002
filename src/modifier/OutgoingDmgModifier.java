package modifier;

public class OutgoingDmgModifier {

    private double outgoingDamageModifier = 1.0;

    public OutgoingDmgModifier(double outgoingDamageModifier) {
        this.outgoingDamageModifier = outgoingDamageModifier;
    }

    public double getOutgoingDamageModifier() {
        return outgoingDamageModifier;
    }

    public void setOutgoingDamageModifier(double outgoingDamageModifier) {
        this.outgoingDamageModifier = outgoingDamageModifier;
    }

    public int applyOutgoingDamageModifier(int outgoingDamage, char operation) {
        switch(operation) {
            case '+':
                return (int)(outgoingDamage + this.getOutgoingDamageModifier());
            case '-':
                return (int)(outgoingDamage - this.getOutgoingDamageModifier());
            case '/':
                return (int)(outgoingDamage / this.getOutgoingDamageModifier());
            case '*':
                return (int)(outgoingDamage * this.getOutgoingDamageModifier());
            default:
                return outgoingDamage;
        }
    }
}