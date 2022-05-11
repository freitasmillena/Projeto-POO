package Entities;

public class Formula5 implements FormulaConsumo{
    public double calculaTotal(double taxes, double consumption, double dailyCost, double nDevices) {
        return (1+taxes)*(dailyCost-0.5)*consumption*0.85;
    }

    public FormulaConsumo clone() {
        return new Formula5();
    }
}
