package Entities;

import java.io.Serializable;

public class Formula1 implements FormulaConsumo, Serializable {

    public Formula1() {
    }

    public double calculaTotal(double taxes, double consumption, double dailyCost, double nDevices) {

        return (nDevices > 10? dailyCost*consumption*(1+taxes)*0.9 : dailyCost*consumption*(1+taxes)*0.75);
    }

    public FormulaConsumo clone() {
        return new Formula1();
    }

    public String toString(){
        return "Formula 1";
    }




}
