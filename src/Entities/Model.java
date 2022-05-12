package Entities;

import java.time.LocalDate;
import java.util.*;

public class Model {

    private Map<String, Casa> casas;
    private Map<String, Fornecedor> fornecedores;
    private Map<String, Invoice> invoices;
    private Map<String, FormulaConsumo> formulas;
    private List<String> commands;
    private LocalDate fromDate;


    public Model (){

        this.casas = new HashMap<>();
        this.fornecedores = new HashMap<>();
        this.invoices = new TreeMap<>();
        this.fromDate = LocalDate.now();
        this.formulas = new HashMap<>();
        this.commands = new ArrayList<>();

    }

    public Model(Map<String, Casa> casas, Map<String, Fornecedor> fornecedores, Map<String, Invoice> invoices, LocalDate fromDate){
        this.casas = new HashMap<>();
        for(Casa casa : casas.values()){
            this.casas.put(casa.getNIF(), casa.clone());
        }

        this.fornecedores = new HashMap<>();
        for(Fornecedor f : fornecedores.values()){
            this.fornecedores.put(f.getSupplier(), f.clone());
        }

        this.invoices = new TreeMap<>();
        for(Invoice in : invoices.values()){
            this.invoices.put(in.getId(),in.clone());
        }

        this.formulas = new HashMap<>();
        this.commands = new ArrayList<>();
        this.fromDate = fromDate;
    }

    public void setFromDate(LocalDate newDate){
        this.fromDate = newDate;
    }

    public void addCasa(Casa casa){
        this.casas.put(casa.getNIF(),casa.clone());
    }

    public void addFornecedor(Fornecedor fornecedor){
        this.fornecedores.put(fornecedor.getSupplier(), fornecedor.clone());
    }

    public void addInvoice(Invoice invoice){
        this.invoices.put(invoice.getId(),invoice.clone());
    }

    public void addFormula(String name, FormulaConsumo formula){
        this.formulas.put(name,formula);
    }

    //avançar com a data gera faturas
    public void generateInvoices(LocalDate toDate){

        for(Casa casa : this.casas.values()){
            double consumption = casa.totalConsumption();
            Fornecedor fornecedor = this.fornecedores.get(casa.getSupplier());
            double totalCost = fornecedor.invoiceAmount(consumption,this.fromDate,toDate,casa.devicesON(),casa.getTotalInstallationCost());
            addInvoice(new Invoice(this.fromDate + casa.getNIF(), casa.getOwner(), casa.getNIF(), fornecedor.getSupplier(), this.fromDate, toDate, totalCost));
            fornecedor.setnFaturas(fornecedor.getnFaturas()+1);
            casa.setTotalInstallationCost(0);

        }
        setFromDate(toDate);
    }

    public void addComand(String command){
        this.commands.add(command);
    }

    //percorrer commands e executá-los

    //função modo smartdevices

    //função trocar fornecedor

    //função fornecedor trocar fórmula
    
 }
