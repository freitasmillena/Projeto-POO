package Entities;

public class Formula6 implements FormulaConsumo{

    public Formula6() {
    }

    public double calculaTotal(double taxes, double consumption, double dailyCost, double nDevices) {
        return consumption*1.2*(0.9+taxes)*dailyCost+(nDevices-2)*0.35;
    }

}
