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

    public Fornecedor() {
        this.supplier = "";
    }

    public Fornecedor(String supplier, FormulaConsumo formulaConsumo) {
        this.supplier = supplier;
        this.formulaConsumo = formulaConsumo.clone();
    }

    public Fornecedor(Fornecedor fornecedor){
        this.supplier = fornecedor.getSupplier();
        this.formulaConsumo = fornecedor.getFormulaConsumo();
    }

    public String getSupplier() {
        return this.supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public double getDailyCost() {
        return this.dailyCost;
    }

    public double getTaxes() {
        return this.taxes;
    }

   public FormulaConsumo getFormulaConsumo() {
        return this.formulaConsumo.clone();
    }

    public void setFormulaConsumo(FormulaConsumo formulaConsumo) {
        this.formulaConsumo = formulaConsumo.clone();
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fornecedor f = (Fornecedor) o;
        return (this.supplier.equals(f.supplier) &&
                this.formulaConsumo.equals(f.getFormulaConsumo()));
    }

    public Fornecedor clone(){
        return new Fornecedor(this);
    }

    public double invoiceAmount(double consumption, LocalDate fromDate, LocalDate toDate, double nDevices, double totalInstallationCost){

        double consumptionTotal = consumption*DAYS.between(fromDate,toDate);
        return formulaConsumo.calculaTotal(this.taxes,consumptionTotal,this.dailyCost, nDevices) + totalInstallationCost;
    }
}