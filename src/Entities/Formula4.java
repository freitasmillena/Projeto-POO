package Entities;

import java.io.Serializable;

public class Formula4 implements FormulaConsumo, Serializable {
    public Formula4() {
    }

    public double calculaTotal(double taxes, double consumption, double dailyCost, double nDevices) {
        return nDevices*0.1+dailyCost*consumption*(1+taxes);
    }

    public FormulaConsumo clone() {
        return new Formula4();
    }

    public String toString(){
        return "Formula 4";
    }
}
