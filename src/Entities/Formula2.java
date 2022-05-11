package Entities;

public class Formula2 implements FormulaConsumo{


    public double calculaTotal(double taxes, double consumption, double dailyCost, double nDevices) {
        return (consumption > 150 ? dailyCost*consumption*(1+taxes)*0.7 : dailyCost*consumption*(1+taxes)*1.1);
    }


    public FormulaConsumo clone() {
        return new Formula2();
    }
}
