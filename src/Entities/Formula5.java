package Entities;

import java.io.Serializable;

public class Formula5 implements FormulaConsumo, Serializable {

    public Formula5() {
    }

    public double calculaTotal(double taxes, double consumption, double dailyCost, double nDevices) {
        return (1+taxes)*(dailyCost-0.5)*consumption*0.85;
    }

    public FormulaConsumo clone() {
        return new Formula5();
    }

    public String toString(){
        return "Formula 5";
    }
}
