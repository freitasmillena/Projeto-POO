package Entities;

import java.time.LocalDate;

public class Invoice {

    private String id;
    private String owner;
    private String nif;
    private String supplier;
    private LocalDate fromDate;
    private LocalDate toDate;
    private double totalCost;

    public Invoice() {
        this.id = "";
        this.owner = "";
        this.nif= "";
        this.supplier = "";
        this.totalCost = 0;
    }

    public Invoice(String id, String owner, String nif, String supplier, LocalDate fromDate, LocalDate toDate, double totalCost) {
        this.id = id;
        this.owner = owner;
        this.nif = nif;
        this.supplier = supplier;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.totalCost = totalCost;
    }

    public Invoice(Invoice invoice){
        this.id = invoice.getId();
        this.owner = invoice.getOwner();
        this.nif = invoice.getNif();
        this.supplier = invoice.getSupplier();
        this.fromDate = invoice.getFromDate();
        this.toDate = invoice.getToDate();
        this.totalCost = invoice.getTotalCost();
    }

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

    public Invoice clone(){
        return new Invoice(this);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Invoice in = (Invoice) o;
        return (this.id.equals(in.getId())&&
                this.owner.equals(in.getOwner()) &&
                this.nif.equals(in.getNif()) &&
                this.supplier.equals(in.getSupplier()) &&
                this.totalCost == in.getTotalCost()) &&
                this.toDate.equals(in.getToDate()) &&
                this.fromDate.equals(in.getFromDate());
    }
}
