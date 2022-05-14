package Entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;

public class Fornecedor {

    private String supplier;
    private final double dailyCost = 2.5;
    private final double taxes = 0.23;
    private FormulaConsumo formulaConsumo;
    private List<String> invoices;
    private double installationCost;

    //Construtor vazio
    public Fornecedor() {
        this.supplier = "";
        this.invoices = new ArrayList<>();
        this.installationCost = 0;
    }

    //Construtor nome, formula, installationCost
    public Fornecedor(String supplier, FormulaConsumo formulaConsumo, double installationCost) {
        this.supplier = supplier;
        this.formulaConsumo = formulaConsumo;
        this.invoices = new ArrayList<>();
        this.installationCost = installationCost;
    }

    //Construtor sem formula
    public Fornecedor(String supplier, double installationCost) {
        this.supplier = supplier;
        this.formulaConsumo = null;
        this.invoices = new ArrayList<>();
        this.installationCost = installationCost;
    }

    //Construtor de cópia
    public Fornecedor(Fornecedor fornecedor){
        this.supplier = fornecedor.getSupplier();
        this.formulaConsumo = fornecedor.getFormulaConsumo();
        this.invoices = fornecedor.getInvoices();
        this.installationCost = fornecedor.getInstallationCost();
    }

    //Getters e Setters
    public String getSupplier() {
        return this.supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public List<String> getInvoices() {
        List<String> result = new ArrayList<>();

       for(String str : this.invoices){
           result.add(str);
       }

        return result;
    }

    public void setInvoices(List<String> invoices) {
        this.invoices = new ArrayList<>();

        for(String str : invoices){
           this.invoices.add(str);
        }
    }

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

    //Equals
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fornecedor f = (Fornecedor) o;
        return (this.supplier.equals(f.supplier) &&
                this.formulaConsumo.equals(f.getFormulaConsumo()) &&
                this.invoices.equals(f.getInvoices()) &&
                this.installationCost == f.getInstallationCost());
    }

    //Clone
    public Fornecedor clone(){
        return new Fornecedor(this);
    }

    //toString
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Supplier: ").append(this.supplier)
                .append("\n");

        return sb.toString();
    }


    //Adiciona id da fatura ao fornecedor
    public void addInvoice(String id){
        this.invoices.add(id);
    }

    public int countInvoices(){
        return this.invoices.size();
    }

    //Calcula valor da fatura de acordo com sua fórmula de cálculo
    public double invoiceAmount(double consumption, LocalDate fromDate, LocalDate toDate, double nDevices, double totalInstallationCost){

        return formulaConsumo.calculaTotal(this.taxes,consumption,this.dailyCost, nDevices) + totalInstallationCost;
    }


}