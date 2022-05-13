package Entities;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;

public class Fornecedor {

    private String supplier;
    private final double dailyCost = 2.5;
    private final double taxes = 0.23;
    private FormulaConsumo formulaConsumo;
    private int nFaturas;
    private double installationCost;

    public Fornecedor() {
        this.supplier = "";
        this.nFaturas = 0;
        this.installationCost = 0;
    }

    public Fornecedor(String supplier, FormulaConsumo formulaConsumo, int nFaturas, double installationCost) {
        this.supplier = supplier;
        this.formulaConsumo = formulaConsumo;
        this.nFaturas = nFaturas;
        this.installationCost = installationCost;
    }

    public Fornecedor(Fornecedor fornecedor){
        this.supplier = fornecedor.getSupplier();
        this.formulaConsumo = fornecedor.getFormulaConsumo();
        this.nFaturas = fornecedor.getnFaturas();
        this.installationCost = fornecedor.getInstallationCost();
    }

    public String getSupplier() {
        return this.supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getnFaturas(){ return this.nFaturas;}

    public void setnFaturas(int nFaturas){this.nFaturas = nFaturas;}

    public double getDailyCost() {
        return this.dailyCost;
    }

    public double getTaxes() {
        return this.taxes;
    }

   public FormulaConsumo getFormulaConsumo() {
        return this.formulaConsumo;
    }

    public void setFormulaConsumo(FormulaConsumo formulaConsumo) {
        this.formulaConsumo = formulaConsumo;
    }

    public double getInstallationCost(){ return this.installationCost;}

    public void setInstallationCost(double installationCost){this.installationCost = installationCost;}

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fornecedor f = (Fornecedor) o;
        return (this.supplier.equals(f.supplier) &&
                this.formulaConsumo.equals(f.getFormulaConsumo()) &&
                this.nFaturas == f.getnFaturas() &&
                this.installationCost == f.getInstallationCost());
    }

    public Fornecedor clone(){
        return new Fornecedor(this);
    }

    public double invoiceAmount(double consumption, LocalDate fromDate, LocalDate toDate, double nDevices, double totalInstallationCost){

        return formulaConsumo.calculaTotal(this.taxes,consumption,this.dailyCost, nDevices) + totalInstallationCost;
    }


}