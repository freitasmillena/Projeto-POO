package Entities;

public interface FormulaConsumo  {

    public double calculaTotal(double taxes, double consumption, double dailyCost, double nDevices);
    public FormulaConsumo clone();
    public String toString();

}
