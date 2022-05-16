package Entities;

import java.io.Serializable;
import java.time.LocalDate;

public class Invoice implements Comparable<Invoice>, Serializable {

    private String id;
    private String owner;
    private String nif;
    private String supplier;
    private LocalDate fromDate;
    private LocalDate toDate;
    private double totalCost;
    private double totalConsumption;

    //Construtor vazio
    public Invoice() {
        this.id = "";
        this.owner = "";
        this.nif= "";
        this.supplier = "";
        this.totalCost = 0;
        this.totalConsumption = 0;
    }

    //Construtor completo
    public Invoice(String id, String owner, String nif, String supplier, LocalDate fromDate, LocalDate toDate, double totalCost, double totalConsumption) {
        this.id = id;
        this.owner = owner;
        this.nif = nif;
        this.supplier = supplier;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.totalCost = totalCost;
        this.totalConsumption = totalConsumption;
    }

    //Construtor de cópia
    public Invoice(Invoice invoice){
        this.id = invoice.getId();
        this.owner = invoice.getOwner();
        this.nif = invoice.getNif();
        this.supplier = invoice.getSupplier();
        this.fromDate = invoice.getFromDate();
        this.toDate = invoice.getToDate();
        this.totalCost = invoice.getTotalCost();
        this.totalConsumption = invoice.getTotalConsumption();
    }

    //Getters e Setters
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getNif() {
        return this.nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getSupplier() {
        return this.supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public LocalDate getFromDate() {
        return this.fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return this.toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public double getTotalCost() {
        return this.totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getTotalConsumption() {
        return this.totalConsumption;
    }

    public void setTotalConsumption(double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    //Clone
    public Invoice clone(){
        return new Invoice(this);
    }

    //Equals
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Invoice in = (Invoice) o;
        return (this.id.equals(in.getId())&&
                this.owner.equals(in.getOwner()) &&
                this.nif.equals(in.getNif()) &&
                this.supplier.equals(in.getSupplier()) &&
                this.totalCost == in.getTotalCost() &&
                this.toDate.equals(in.getToDate()) &&
                this.fromDate.equals(in.getFromDate()) &&
                this.totalConsumption == in.getTotalConsumption());
    }

    //toString
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("Dono: ").append(this.owner)
                .append("  NIF: ").append(this.nif)
                .append("\n")
                .append("Fornecedor: ").append(this.supplier)
                .append("\n")
                .append("Período de faturamento: ").append(this.fromDate).append(" a ").append(this.toDate)
                .append("\n")
                .append("Comsumo total: ").append(String.format("%,.2f",this.totalConsumption)).append(" kwh")
                .append("\n")
                .append("Custo total: ").append(String.format("%,.2f €",this.totalCost))
                .append("\n");

        return sb.toString();

    }


    //Ordenar decrescentemente por consumo de energia
    public int compareTo(Invoice o) {
        if(this.totalConsumption > o.getTotalConsumption()) return -1;
        else if(this.totalConsumption < o.getTotalConsumption()) return 1;
        else return 0;
    }
}
