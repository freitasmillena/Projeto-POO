package Entities;

import Entities.Exceptions.*;
import Enums.Mode;

import java.time.LocalDate;
import java.util.*;

public class Model {

    private Map<String, Casa> casas;
    private Map<String, Fornecedor> fornecedores;
    private Map<String, Invoice> invoices;
    private Map<String, FormulaConsumo> formulas;
    private List<Command> commands;
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

    //Imprime faturas -> apenas para teste interno. Isto não vai para o trabalho
    public void printInvoices(){
        for(Invoice in : this.invoices.values()){
            System.out.println("Owner: " + in.getOwner() + "Fornecedor: " + in.getSupplier() + "Total: " + in.getTotalCost());
        }
    }

    //Adiciona pedido na lista de espera
    public void addComandBasic(Command command){
        this.commands.add(command.clone());
    }

    //String to mode
    public Mode whichMode(String mode){
        Mode m;
        if(mode.equals("ON")){
            m = Mode.ON;
        }
        else m = Mode.OFF;

        return m;
    }

    //String to Formula
    public FormulaConsumo whichFormula(String formula){
        FormulaConsumo formulaConsumo = null;
        switch (formula) {
            case "Formula1" -> formulaConsumo = new Formula1();
            case "Formula2" -> formulaConsumo = new Formula2();
            case "Formula3" -> formulaConsumo = new Formula3();
            case "Formula4" -> formulaConsumo = new Formula4();
            case "Formula5" -> formulaConsumo = new Formula5();
            case "Formula6" -> formulaConsumo = new Formula6();
        }
        return formulaConsumo;
    }

    //Executar 1 comando
    public void runCommand(Command command) throws DateAlreadyExistsException {
        //Se for esta data, são comandos da funcionalidade básica. Portanto tem que atualizar para data de início da próxima fatura
        //Se for funcionalidade avançada, n entra nesta condição e usa as datas passadas no ficheiro
        if(command.getDate().equals(LocalDate.parse("1998-01-27"))){
            command.setDate(this.fromDate);
        }

        switch(command.getName()){
            case "setMode": // setMode casa dispositivo mode
                Casa casa = this.casas.get(command.getCommand1());
                casa.setMode(command.getCommand2(), whichMode(command.getCommand3()), command.getDate());
                break;
            case "changeSupplier": //changeSupplier casa fornecedor
                Casa c = this.casas.get(command.getCommand1());
                c.setSupplier(command.getCommand2());
                break;
            case "changeFormula": //changeFormula supplier formula
                Fornecedor fornecedor = this.fornecedores.get(command.getCommand1());
                fornecedor.setFormulaConsumo(whichFormula(command.getCommand2()));
                break;
        }
    }

    //percorrer commands e executá-los
    public void runCommands() throws DateAlreadyExistsException{
        for(Command command : this.commands){
            runCommand(command);
        }
    }


    //avançar data
    public void moveForward(LocalDate toDate) throws DateAlreadyExistsException, InvalidDateException{
        if(this.fromDate.compareTo(toDate) >= 0){
            //se data de inicio for igual ou maior que data de fim
            throw new InvalidDateException("Invalid date " +  toDate + ". It must be after " + this.fromDate);
        }
        generateInvoices(toDate);
        runCommands();
    }


    /* Queries estatísticas */

    //qual é a casa que mais gastou naquele período

    //qual o comercializador com maior volume de facturação

    //listar as facturas emitidas por um comercializador

    //dar uma ordenação dos maiores consumidores de energia durante um período a determinar




    /* Modo avançado */
    /*
    * Enquanto há linhas no ficheiro lê
    * Se for setMode -> executa
    * Se for changeSupplier ou changeFormula -> cria command e adiciona à lista de commands
    * Se for generateInvoice -> chama função moveForward
    *
    * Modelo ficheiro:
    * ----------------
    * data pedido comandos
    * 2022-05-13 setMode casa1 d1 ON
    * 2022-05-13 setMode casa2 d2 OFF
    * 2022-05-14 changeSupplier EDP Galp
    * 2022-05-14 changeFormula EDP Formula1
    * 2022-05-15 generateInvoice  (este gera fatura com fromDate do model e toDate = 2022-05-15)
    *
    * */

}
