package Entities;

import java.io.Serializable;

public class Formula6 implements FormulaConsumo, Serializable {

    public Formula6() {
    }

    public double calculaTotal(double taxes, double consumption, double dailyCost, double nDevices) {
        return consumption*1.2*(0.9+taxes)*dailyCost+(nDevices-2)*0.35;
    }

    public FormulaConsumo clone() {
        return new Formula6();
    }

    public String toString(){
        return "Formula 6";
    }
}
