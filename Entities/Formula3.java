package Entities;

public class Formula3 implements FormulaConsumo{
    public Formula3() {
    }

    public double calculaTotal(double taxes, double consumption, double dailyCost, double nDevices) {
        return (Math.pow(dailyCost,2)/10)*(nDevices/10)*consumption*(1.1+taxes);
    }

}
