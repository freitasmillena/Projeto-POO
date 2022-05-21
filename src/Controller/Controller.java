package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import Object.Object;
import Entities.Casa;
import Entities.Command;
import Entities.Formula1;
import Entities.Formula2;
import Entities.Formula3;
import Entities.Formula4;
import Entities.Formula5;
import Entities.Formula6;
import Entities.FormulaConsumo;
import Entities.Fornecedor;
import Entities.Invoice;
import Entities.Model;
import Entities.SmartBulb;
import Entities.SmartCamera;
import Entities.SmartSpeaker;
import Entities.Exceptions.DateAlreadyExistsException;
import Entities.Exceptions.DeviceDoesntExists;
import Entities.Exceptions.HouseAlreadyExists;
import Entities.Exceptions.HouseDoesntExists;
import Entities.Exceptions.InvalidDateException;
import Entities.Exceptions.LocationAlreadyExists;
import Entities.Exceptions.LocationDoesntExist;
import Entities.Exceptions.SupplierAlreadyExists;
import Entities.Exceptions.SupplierDoesntExists;
import FIle.Parser;
import Window.Menu;
import Window.Window;

public class Controller extends Exception {
    
    // Responsável por inicializar o programa
    public static void run() {
        boolean end_program = false; 

        Scanner scanner = new Scanner(System.in);

        Model model = new Model();

        //Adicionar fórmulas ao modelo
        FormulaConsumo f1 = new Formula1();
        FormulaConsumo f2 = new Formula2();
        FormulaConsumo f3 = new Formula3();
        FormulaConsumo f4 = new Formula4();
        FormulaConsumo f5 = new Formula5();
        FormulaConsumo f6 = new Formula6();

        model.addFormula("Formula1", f1);
        model.addFormula("Formula2", f2);
        model.addFormula("Formula3", f3);
        model.addFormula("Formula4", f4);
        model.addFormula("Formula5", f5);
        model.addFormula("Formula6", f6);

        LocalDate today = LocalDate.now();
        model.setFromDate(today); // definir a data de execução do programa como a data inicial do Model

        Window.createPOO();
    

        // ---| Manual ou Automatização |---
        int opcao_inicial = -1;

        boolean carregou_ficheiro = false;
        boolean automatizado = false; // só pode ser feito uma vez

        while(end_program == false) {
            opcao_inicial = Menu.menuInicial(scanner, carregou_ficheiro, automatizado);

            switch (opcao_inicial) {
                case 1: // Automatizar a simulação
                    if (!carregou_ficheiro || (carregou_ficheiro && automatizado)) {
                        Window.indisponivel();
                    }
                    else if(carregou_ficheiro && !automatizado) {
                        try {
                            automatizado = controllerAutomatizacao(scanner, model);
                        }
                        catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                case 2: // Menu para o utilizador
                    if (!carregou_ficheiro) {
                        Window.indisponivel();
                    }
                    else {
                        try {
                            controllerUtilizador(scanner, model, automatizado); // não avançou no tempo
                        }
                        catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                case 3: // Guardar o estado do programa
                    saveProgram(model);
                    break;
                case 4: // Carregar estado do programa
                    try {
                        model = Object.loadObject("modelObject.txt");
                        System.out.println("");
                        System.out.println(" Object carregado!");
                        System.out.println("");
                        carregou_ficheiro = true;
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5: // Carregar ficheiro de texto de dados (Parsing)
                    if (parseFile(model, scanner) == true) carregou_ficheiro = true;
                    break;
                default: // Sair do programa
                    end_program = true;
            }
        }
        scanner.close();
        Window.finalText();
    }

    // Parse fo ficheiro
    private static boolean parseFile(Model model, Scanner scanner) {
        boolean leu_ficheiro = false;
        // ---| PARSING |---

        String file_logs = Menu.menuParser(scanner);

        Window.viewParsing();
        Parser p = new Parser();
        try {
            p.parseLogs(model, file_logs);
            Window.parsingConcluded();
            leu_ficheiro = true;
        }
        catch (FileNotFoundException e) {
            System.out.println("");
            System.out.println("!!!! Ficheiro " + file_logs + "não foi encontrado !!!!!");
            System.out.println("");
        }
        catch (Exception e) {
            System.out.println("");
            System.out.println(e.getMessage());
            System.out.println("");
        }
        return leu_ficheiro;
    }

    // salvar o estado atual do programa
    private static void saveProgram(Model model) {
        Window.guardarPrograma();
        try {
            Object.writeObject(model);
        }
        catch (IOException e) {
			System.out.println("Error initializing stream");
		}
        Window.finalCarregarPrograma();
    }

    public static boolean controllerAutomatizacao(Scanner scanner, Model model) {
        boolean end_program = false;

        // ---| Automatização |---
        String auto;
        Window.clear();

        while(end_program == false) {

            auto = Menu.menuAuto(scanner);
            try { 
                Window.parsingAdvanced();
                Parser.parseAdvanced(model, auto);
                Window.parsingConcluded();
                end_program = true;
            }
            catch (FileNotFoundException e) {
                System.out.println("File not " + auto + " found");
                continue;
            }
        }

        return end_program;
    }

    public static void controllerUtilizador(Scanner scanner, Model model, Boolean tempo) throws InvalidDateException, DateAlreadyExistsException {
        int opcao_utilizador = -1;
        boolean end_program = false;
        boolean avancou_tempo = tempo; // indica se o utilizador já avançou no tempo pelo menos uma vez
                                       // caso tenha, é disponibilizado as opoções de ver as faturas e as estatísticas
        while(end_program == false) {
            opcao_utilizador = Menu.menuUtilizadorManual(scanner, avancou_tempo);

            switch (opcao_utilizador) {

                case 1: // Inserir dados no modelo
                    controllerInsert(scanner, model);
                    break;
                case 2: // Alterar dados do modelo
                    controllerAlterar(scanner, model);
                    break;
                case 3: // Avançar no tempo
                    avancou_tempo = true;
                    controllerTempo(scanner, model);
                    break;
                case 4: // 
                    saveProgram(model);
                    break;
                case 5: // Apresentar TODAS as Faturas
                    if (avancou_tempo == false) {
                        System.out.println("");
                        System.out.println("Não há faturas");
                        System.out.println("");
                    }
                    else {
                        System.out.println("");
                        model.printInvoices();
                    }
                    break;
                case 6: // Estatísticas
                    if (avancou_tempo == false) {
                        System.out.println("");
                        System.out.println("Não há faturas");
                        System.out.println("");
                    }
                    else {
                        controllerEstatisticas(scanner, model);
                    }
                    break;
                default: // Sair do programa
                    end_program = true;
            }
        }
    }


    public static void controllerInsert(Scanner scanner, Model model) {
        int opcao_utilizador = -1;
        boolean end_program = false;
        
        while(end_program == false) {
            opcao_utilizador = Menu.menuInserir(scanner);

            switch (opcao_utilizador) {
                case 1: // Fornecedor
                    controllerInsertFornecedor(scanner, model);
                    break;
                case 2: // Casa
                    controllerInsertCasa(scanner, model);
                    break;
                case 3: // Divisão numa casa
                    controllerInsertLocation(scanner, model);
                    break;
                case 4: // Dispositivo numa divisão de uma casa 
                    controllerInsertDevice(scanner, model);
                    break;
                default: // Desligar o programa
                    end_program = true;
            } 
        }
    }

    public static void controllerInsertFornecedor(Scanner scanner, Model model) {
        Fornecedor fornecedor = new Fornecedor();
        int formula;
        boolean cont = false;
        double custo = Parser.randomCustoInstalacao();
        while(!cont) {
            cont = false;
            try {
                String name_fornecedor = Menu.menuFornecedorName(scanner, model);
                // Vai fornecer fórmulas aleatóreas
                formula = Parser.randomFormula();
                switch(formula) {
                    case 1:
                        Formula1 f1 = new Formula1();
                        fornecedor.setFormulaConsumo(f1);
                        break;
                    case 2:
                        Formula2 f2 = new Formula2();
                        fornecedor.setFormulaConsumo(f2);     
                        break;       
                    case 3:
                        Formula3 f3 = new Formula3();
                        fornecedor.setFormulaConsumo(f3);   
                        break;         
                    case 4:
                        Formula4 f4 = new Formula4();
                        fornecedor.setFormulaConsumo(f4); 
                        break;               
                    case 5:
                        Formula5 f5 = new Formula5();
                        fornecedor.setFormulaConsumo(f5);  
                        break;              
                    case 6:
                        Formula6 f6 = new Formula6();
                        fornecedor.setFormulaConsumo(f6);
                        break;
                    default:
                        cont = true;                
                }
                fornecedor.setInstallationCost(custo);
                fornecedor.setSupplier(name_fornecedor);
                model.addFornecedor(fornecedor);
            }
            catch (SupplierAlreadyExists e) {
                System.out.println(e.getMessage());
                continue;
            }
            catch (Exception e) {
                continue;
            }
        }
        
        try {
            model.addFornecedor(fornecedor);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void controllerInsertCasa(Scanner scanner, Model model) {
        String nome_dono = Menu.menuNomeDonoCasa(scanner);
    
        String nif_casa;
        while(true) {
            try {
                nif_casa = Menu.menuCasa(scanner, model, 1);
            }
            catch (HouseAlreadyExists e) {
                System.out.println(e.getMessage());
                continue;
            }
            catch (Exception e) {
                continue;
            }
            break;
        }

        String name_fornecedor;
        while(true) {
            try {
                name_fornecedor = Menu.menuFornecedorName(scanner, model);
            }
            catch (SupplierDoesntExists e) {
                System.out.println(e.getMessage());
                continue;
            }
            catch (Exception e) {
                continue;
            }
            break;
        }

        Casa novo = new Casa(nome_dono, nif_casa, name_fornecedor);
        try {
            model.addCasa(novo);
        }
        catch (HouseAlreadyExists e) {
            System.out.println(e.getMessage());
        }
    }

    public static void controllerInsertLocation(Scanner scanner, Model model) {
        String nif_casa;
        while(true) {
            try {
                nif_casa = Menu.menuCasa(scanner, model,0); // usar casa já existente
            }
            catch (HouseDoesntExists e) {
                System.out.println(e.getMessage());
                continue;
            }
            catch (Exception e) {
                continue;
            }
            break;
        }

        String location;
        while(true) {
            try {
                location = Menu.menuLocation(scanner);
                if (location.equals("0")) {
                    List<String> l = model.printLocations(nif_casa);
                    System.out.println(l);
                }
                else {
                    model.addLocationToHouse(location, nif_casa);
                    break;
                }
            }
            catch (LocationAlreadyExists e) {
                System.out.println(e.getMessage());
                continue;
            }
            catch (Exception e) { 
                continue;
            }
        }
    }

    public static void controllerInsertDevice(Scanner scanner, Model model) {
        boolean end_program = false;
        
        String nif_casa;
        while(true) {
            try {
                nif_casa = Menu.menuCasa(scanner, model,0); // usar casa já existente
            }
            catch (HouseDoesntExists e) {
                System.out.println(e.getMessage());
                continue;
            }
            catch (Exception e) {
                continue;
            }
            break;
        }
        String location;
        while(true) {
            try {
                location = Menu.menuLocation(scanner);
                if (location.equals("0")) {
                    List<String> l = model.printLocations(nif_casa);
                    System.out.println(l);
                }
                else {
                    model.addLocationToHouse(location, nif_casa);
                    break;
                }
            }
            catch (Exception e) { 
                continue;
            }
        }

        int opcao_utilizador = -1;
        while(end_program == false) {

            opcao_utilizador = Menu.menuChooseDevice(scanner);

            switch (opcao_utilizador) {

                case 1: // SmartBulb
                    try {    
                        SmartBulb sb = new SmartBulb();
                        model.addDeviceToHouse(nif_casa, location, sb);
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2: // SmartSpeaker
                    try {    
                        SmartSpeaker ss = new SmartSpeaker();
                        model.addDeviceToHouse(nif_casa, location, ss);
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3: // SmarCamera
                    try {    
                        SmartCamera sc = new SmartCamera();
                        model.addDeviceToHouse(nif_casa, location, sc);
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                default: // Desligar o programa
                    end_program = true;
            } 
        }
    }

    public static void controllerAlterar(Scanner scanner, Model model) {
        int opcao_utilizador = -1;
        boolean end_program = false;
        
        while(end_program == false) {

            opcao_utilizador = Menu.menuAlterar(scanner);

            switch (opcao_utilizador) {

                case 1: // Alterar Fornecedor de uma Casa
                    controllerChangeFornecedor(scanner, model);
                    break;
                case 2: // Ligar/Desligar 1 dispositivo
                    controllerDeviceMode(scanner, model);
                    break;
                case 3: // Ligar TODOS os dispositivos de uma divisão da casa
                    controllerTurnAllDevice(scanner, model, 1);
                    break;
                case 4: // Desligar TODOS os dispositivos de uma divisão da casa
                    controllerTurnAllDevice(scanner, model, 0);
                    break;
                case 5: // Mudar fórmula de um fornecedor
                    controllerChangeFormula(scanner, model);
                    break;
                default: // Desligar o programa
                    end_program = true;
            } 
        }
    }

    private static void controllerChangeFornecedor(Scanner scanner, Model model) {
        String nif_casa;
        while(true) {
            try {
                nif_casa = Menu.menuCasa(scanner, model,0); // usar casa já existente
            }
            catch (HouseDoesntExists e) {
                System.out.println(e.getMessage());
                continue;
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            break;
        }
        
        String fornecedor;
        while(true) {
            fornecedor = Menu.menuEscolherFornecedor(scanner, model);
            if (model.hasFornecedor(fornecedor)) break;
        }

        Command comando = new Command(LocalDate.parse("1998-01-27"), "changeSupplier", nif_casa, fornecedor);
        model.addComandBasic(comando);
    }

    public static void controllerChangeFormula(Scanner scanner, Model model) {
        
        String fornecedor;
        while(true) {
            fornecedor = Menu.menuEscolherFornecedor(scanner, model);
            if (model.hasFornecedor(fornecedor)) break;
        }
        int formula = -1;
        String fc = null;
        while(formula < 1 || formula > 6 ) {
            formula = Menu.menuFormula(scanner);
            switch (formula) {
                case 1:
                    fc = "Formula1";
                    break;
                case 2:
                    fc = "Formula2";  
                    break;         
                case 3:
                    fc = "Formula3";  
                    break;      
                case 4:
                    fc = "Formula4";   
                    break;         
                case 5:
                    fc = "Formula5"; 
                    break;            
                case 6:
                    fc = "Formula6";
                    break;
            }            
        }

        Command comando = new Command(LocalDate.parse("1998-01-27"), "changeFormula", fornecedor, fc);
        model.addComandBasic(comando);
    }

    public static void controllerTempo(Scanner scanner, Model model) {
        Window.clear();
        while(true) {
            try {
                LocalDate data_nova = Menu.menuTempo(scanner, model.getFromDate());
                model.moveForward(data_nova);
            }
            catch (InvalidDateException e) {
                System.out.println(e.getMessage());
                continue;
            }
            catch (DateAlreadyExistsException e) {
                System.out.println(e.getMessage());
                continue;
            }
            catch (LocationDoesntExist e) {
                System.out.println(e.getMessage());
                continue;
            }
            catch (DeviceDoesntExists e) {
                System.out.println(e.getMessage());
                continue;
            }
            break;
        }
    }

    public void controllerFaturas(Model model) {
        System.out.println("");
        model.printInvoices();
    }

    public static void controllerDeviceMode(Scanner scanner, Model model) {
        String nif_casa;
        while(true) {
            try {
                nif_casa = Menu.menuCasa(scanner, model,0); // usar casa já existente
            }
            catch (HouseDoesntExists e) {
                System.out.println(e.getMessage());
                continue;
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            break;
        }
        
        String id_device;
        while(true) {
            try {
                id_device = Integer.toString(Menu.menuDispositivo(scanner));
                if (id_device.equals("0")) {
                    System.out.println(model.printDevices(nif_casa));
                    continue;
                }
            }
            catch (Exception e) { 
                System.out.println(e.getMessage());
                continue;
            }
            break;
        }

        int int_mode = -1;
        while(int_mode != 1 || int_mode != 2) {
            try {
                int_mode = Menu.menuMode(scanner);
            }
            catch (Exception e) { 
                System.out.println(e.getMessage());
                continue;
            }
            break;
        }

        String mode = null;
        switch (int_mode) {
            case 1:
                mode = "ON";
            case 2:
                mode = "OFF";
        }

        Command comando = new Command(LocalDate.parse("1998-01-27"), "setMode", nif_casa, id_device, mode);
        model.addComandBasic(comando);
    }

    public static void controllerTurnAllDevice(Scanner scanner, Model model, int mode) {
        String NIF;
        
        while(true) {
            try {
                NIF = Menu.menuCasa(scanner, model, 0);
            }
            catch (HouseAlreadyExists | HouseDoesntExists e) {
                System.out.println(e.getMessage());
                continue;
            }
            break;
        }

        String location;
        while(true) {
            try {
                location = Menu.menuLocation(scanner);
                if (location.equals("0")) {
                    List<String> l = model.printLocations(NIF);
                    System.out.println(l);
                }
                else {
                    model.addLocationToHouse(location, NIF);
                    break;
                }
            }
            catch (LocationAlreadyExists e) {
                System.out.println(e.getMessage());
                continue;
            }
        }

        Command comando = null;

        switch (mode) {
            case 1: // turnON
                comando = new Command(LocalDate.parse("1998-01-27"), "setModeLocation", NIF, location, "ON");
                break;
            case 0: // turnOFF
                comando = new Command(LocalDate.parse("1998-01-27"), "setModeLocation", NIF, location, "OFF");
                break;
        }

        model.addComandBasic(comando);
    }

    public static void controllerEstatisticas(Scanner scanner, Model model) {
        int opcao_utilizador = -1;
        boolean end_program = false;

        Window.clear();

        while(end_program == false) {
            opcao_utilizador = Menu.menuEstatisticas(scanner);
    
            switch (opcao_utilizador) {
                case 1: // Casa que mais gastou
                    Window.estatistica1(model);
                    break;
                case 2: // Comercializador com maior volume de faturação
                    Window.estatistica2(model);
                    break;
                case 3: // Faturas emitidas por um comercializador
                    controllerEstatistica3(scanner, model);
                    break;
                case 4: // maiores consumidores de energia
                    controllerEstatistica4(scanner, model);
                    break;
                default: // Desligar o programa
                    end_program = true;
            }
        }
    }

    public static void controllerEstatistica3(Scanner scanner, Model model) {
        Window.resultsEstatisticas();
        System.out.println("----| 3. Listar TODAS as faturas emitidas por um comercializador |----");
        System.out.println("");
        String name_supplier;

        while(true){
            try{
                name_supplier = Menu.menuEstatistica3(scanner, model);
                System.out.println("");
            }
            catch (SupplierDoesntExists e) {
                System.out.println(e.getMessage());
                System.out.println("");
                continue;
            }
            break;
        }        
        List<Invoice> l_invoices = model.invoicesPerSupplier(name_supplier);

        for (Invoice invoice : l_invoices) {
            System.out.println(invoice.toString());
            System.out.println("");
        }

        Window.fimLista();
    }

    public static void controllerEstatistica4(Scanner scanner, Model model) {
        LocalDate from_date = Menu.menuEstatistica4FromDate(scanner);
        LocalDate to_date = Menu.menuEstatistica4ToDate(scanner);

        List<String> list = model.biggestEnergyConsumers(from_date, to_date);

        for (String s : list) {
            System.out.println(s);
            System.out.println("");
        }

        Window.fimLista();
    }
}