package Entities;

import Entities.Exceptions.FormulaDoesntExist;
import Entities.Exceptions.HouseAlreadyExists;
import Entities.Exceptions.SupplierAlreadyExists;
import Entities.Exceptions.SupplierDoesntExists;

import java.time.LocalDate;
import java.util.*;

public class Model {

    private Map<String, Casa> casas;
    private Map<String, Fornecedor> fornecedores;
    private Map<String, Invoice> invoices;
    private Map<String, FormulaConsumo> formulas;
    private List<String> commands;
    private LocalDate fromDate;


    //Construtor vazio
    public Model (){

        this.casas = new HashMap<>();
        this.fornecedores = new HashMap<>();
        this.invoices = new TreeMap<>();
        this.fromDate = LocalDate.now();
        this.formulas = new HashMap<>();
        this.commands = new ArrayList<>();

    }

    //Construtor
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

    //Atualizar data inicial da fatura
    public void setFromDate(LocalDate newDate){
        this.fromDate = newDate;
    }

    //Adicionar casa
    public void addCasa(Casa casa) throws HouseAlreadyExists {
        if(this.casas.containsKey(casa.getNIF())){
            throw new HouseAlreadyExists("This house already exists.");
        }
        this.casas.put(casa.getNIF(),casa.clone());
    }

    //Adicionar fornecedor
    public void addFornecedor(Fornecedor fornecedor) throws SupplierAlreadyExists {
        if(this.fornecedores.containsKey(fornecedor.getSupplier())){
            throw new SupplierAlreadyExists("This supplier: " + fornecedor.getSupplier() + " already exists.");
        }
        this.fornecedores.put(fornecedor.getSupplier(), fornecedor.clone());
    }

    //Retornar custo total de instalação de um fornecedor
    public double getInstallationCost(String supplier) throws SupplierDoesntExists {
        if(!this.fornecedores.containsKey(supplier)){
            throw new SupplierDoesntExists("This supplier: " + supplier + " doesn't exist.");
        }
        return this.fornecedores.get(supplier).getInstallationCost();
    }

    //Adiciona fatura
    public void addInvoice(Invoice invoice){
        this.invoices.put(invoice.getId(),invoice.clone());
    }

    //Adiciona formula
    public void addFormula(String name, FormulaConsumo formula){
        this.formulas.put(name,formula);
    }

    //Retorna formula
    public FormulaConsumo getFormula(String formula) throws FormulaDoesntExist {
        if(!this.formulas.containsKey(formula)){
            throw new FormulaDoesntExist("This formula doesnt exist.");
        }
        return this.formulas.get(formula);
    }

    //Avançar com a data gera faturas
    public void generateInvoices(LocalDate toDate){

        //Percorre todas as casas
        for(Casa casa : this.casas.values()){
            double consumption = casa.totalConsumption(this.fromDate, toDate); /* Calcula consumo total da casa */

            Fornecedor fornecedor = this.fornecedores.get(casa.getSupplier()); /* Fornecedor da casa */

            /* Calcula custo total da fatura */
            double totalCost = fornecedor.invoiceAmount(consumption,this.fromDate,toDate,casa.devicesON(),casa.getTotalInstallationCost());

            /* Guarda fatura no programa */
            addInvoice(new Invoice(this.fromDate + casa.getNIF(), casa.getOwner(), casa.getNIF(), fornecedor.getSupplier(), this.fromDate, toDate, totalCost));

            /* Atualiza número de faturas geradas por este fornecedor */
            fornecedor.setnFaturas(fornecedor.getnFaturas()+1);

            /* Atualiza custo de instalação da casa para 0*/
            casa.setTotalInstallationCost(0);

        }
        /* Atualiza nova data de início da próxima fatura */
        setFromDate(toDate);
    }

    //Adiciona pedido na lista de espera
    public void addComand(String command){
        this.commands.add(command);
    }

    //Imprime faturas -> apenas para teste interno. Isto não vai para o trabalho
    public void printInvoices(){
        for(Invoice in : this.invoices.values()){
            System.out.println("Owner: " + in.getOwner() + "Fornecedor: " + in.getSupplier() + "Total: " + in.getTotalCost());
        }
    }

    //percorrer commands e executá-los

    //função modo smartdevices

    //função trocar fornecedor

    //função fornecedor trocar fórmula
    
 }
