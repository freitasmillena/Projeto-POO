package Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Object.Object;
import Entities.Casa;
import Entities.Formula1;
import Entities.Formula2;
import Entities.Formula3;
import Entities.Formula4;
import Entities.Formula5;
import Entities.Formula6;
import Entities.FormulaConsumo;
import Entities.Fornecedor;
import Entities.Model;
import Entities.SmartBulb;
import Entities.SmartCamera;
import Entities.SmartSpeaker;
import Entities.Exceptions.DateAlreadyExistsException;
import Entities.Exceptions.HouseAlreadyExists;
import Entities.Exceptions.HouseDoesntExists;
import Entities.Exceptions.InvalidDateException;
import Entities.Exceptions.LocationAlreadyExists;
import Entities.Exceptions.SupplierAlreadyExists;
import Entities.Exceptions.SupplierDoesntExists;
import FIle.Parser;
import Window.Menu;
import Window.Window;

public class Controller extends Exception {
    
    // Responsável por inicializar o programa
    public static void run(String file_logs, String file_auto) throws FileNotFoundException, InvalidDateException, DateAlreadyExistsException {
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
        

        // ---| PARSING |---

        Window.viewParsing();
        Parser p = new Parser();
        try {
            end_program = end_program || p.parseLogs(model, file_logs);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            Window.finalText();
            System.exit(0);
        }

        if (!end_program) {
            Window.parsingConcluded();

            model.printSuppliers();

            // ---| Manual ou Automatização |---
            int opcao_inicial = -1;
            while(opcao_inicial == -1) {
                try{
                    opcao_inicial = Menu.menuInicial(scanner);
                }
                catch (InputMismatchException e) { // Não foi inscrito um int
                    System.out.println(e.getMessage());
                    opcao_inicial = -1;
                }  
            }
    
            while(true) {
                switch (opcao_inicial) {
                    case 1: // Automatizar a simulação
                        end_program = end_program || controllerAutomatizacao(scanner, model, file_auto);
                    case 2: // Menu para o utilizador
                        while(!end_program) {
                            end_program = end_program || controllerUtilizador(scanner, model);
                        }
                    case 3: // Guardar o estado do programa
                        end_program = end_program || saveProgram(model);
                    default: // Sair do programa
                        end_program = true;
                }
                if (end_program == true) break;
            }
        }

        scanner.close();
        Window.finalText();
    }

    // salvar o estado autual do programa
    private static boolean saveProgram(Model model) {
        Window.guardarPrograma();
        try {
            Object.writeObject(model);
            return false;
        }
        catch (IOException e) {
			System.out.println("Error initializing stream");
            return true;
		}
    }

    // Corre um modelo e anavança diretamente para o Menu de alterações
    public static void runObject(Model model, String auto) throws InvalidDateException, DateAlreadyExistsException {
        Scanner scanner = new Scanner(System.in);
        boolean end_program = false;

        // ---| Manual ou Automatização |---
        int opcao_inicial = -1;
        while(opcao_inicial == -1) {
            try{
                opcao_inicial = Menu.menuInicial(scanner);
            }
            catch (InputMismatchException e) { // Não foi inscrito um int
                System.out.println(e.getMessage());
                opcao_inicial = -1;
            }  
        }

        while(true) {
            switch (opcao_inicial) {
                case 1: // Automatizar a simulação
                    try { 
                        end_program = end_program || controllerAutomatizacao(scanner, model, auto);
                    }
                    catch (FileNotFoundException e) {
                        System.out.println("File not " + auto + " found");
                    }
                case 2: // Menu para o utilizador
                    while(!end_program) {
                        end_program = end_program || controllerUtilizador(scanner, model);
                    }
                case 3: // Guardar o estado do programa
                    end_program = end_program || saveProgram(model);
                default: // Sair do programa
                    end_program = true;
            }
            if (end_program == true) break;
        }
        scanner.close();
        Window.finalText();
    }

    public static boolean controllerAutomatizacao(Scanner scanner, Model model, String file_auto) throws FileNotFoundException {
        boolean end_program = false;
        Window.parsingAdvanced();
        end_program = end_program || Parser.parseAdvanced(model, file_auto); // CORRIGIR
        Window.parsingConcluded();

        int opcao_utilizador = Menu.menuOpcoesAutomatizacao(scanner);;

        switch (opcao_utilizador) {
            case 1 : // Estatísticas
                controllerEstatisticas(scanner, model);
            default: // SAIR
                end_program = true;
        }

        return end_program;

    }

    public static boolean controllerUtilizador(Scanner scanner, Model model) throws InvalidDateException, DateAlreadyExistsException {
        int opcao_utilizador = -1;
        boolean end_program = false;
        boolean avancou_tempo = false; // indica se o utilizador já avançou no tempo pelo menos uma vez
                                       // caso tenha, é disponibilizado as poções de ver as afturas e de evr as estatísticas
        while(!end_program && opcao_utilizador == -1) {
            opcao_utilizador = Menu.menuUtilizadorManual(scanner);

            switch (opcao_utilizador) {

                case 1: // Inserir dados no modelo
                    end_program = end_program || controllerInsert(scanner, model);
                    break;
                case 2: // Alterar dados do modelo
                    end_program = end_program || controllerAlterar(scanner, model);
                    break;
                case 3: // Avançar no tempo
                    avancou_tempo = true;
                    controllerTempo(scanner, model);
                    break;
                case 4: // Apresentar TODAS as Faturas
                    if (avancou_tempo == false) {
                        System.out.println("");
                        System.out.println("Não há faturas");
                        System.out.println("");
                    }
                    else {
                        model.generateInvoices(model.getFromDate());
                        System.out.println("");
                        model.printInvoices();
                    }
                    break;
                case 5: // Estatísticas
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
                    break;
            }
        }
        return end_program;
    }


    public static boolean controllerInsert(Scanner scanner, Model model) {
        int opcao_utilizador = -1;
        boolean end_program = false;
        
        while(!end_program && opcao_utilizador == -1) {
            opcao_utilizador = Menu.menuAlterar(scanner);

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
                    break;
            } 
        }
        return end_program;
    }

    public static void controllerInsertFornecedor(Scanner scanner, Model model) {
        Fornecedor fornecedor = new Fornecedor();
        int formula;
        boolean cont = false;
        double custo = Parser.randomCustoInstalacao();
        while(true) {
            cont = false;
            try {
                String name_fornecedor = Menu.menuFornecedorName(scanner, model);
                // Vai fornecer fórmulas aleatóreas
                formula = Parser.randomFormula();
                switch(formula) {
                    case 1:
                        Formula1 f1 = new Formula1();
                        fornecedor.setFormulaConsumo(f1);
                    case 2:
                        Formula2 f2 = new Formula2();
                        fornecedor.setFormulaConsumo(f2);            
                    case 3:
                        Formula3 f3 = new Formula3();
                        fornecedor.setFormulaConsumo(f3);            
                    case 4:
                        Formula4 f4 = new Formula4();
                        fornecedor.setFormulaConsumo(f4);                
                    case 5:
                        Formula5 f5 = new Formula5();
                        fornecedor.setFormulaConsumo(f5);                
                    case 6:
                        Formula6 f6 = new Formula6();
                        fornecedor.setFormulaConsumo(f6);
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
            if (cont == true) continue;
            else break;
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
                model.addLocationToHouse(location, nif_casa);
            }
            catch (LocationAlreadyExists e) {
                System.out.println(e.getMessage());
                continue;
            }
            catch (Exception e) { 
                continue;
            }
            break;
        }
    }

    public static boolean controllerInsertDevice(Scanner scanner, Model model) {
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
            }
            catch (Exception e) { 
                continue;
            }
            break;
        }

        int opcao_utilizador = -1;
        while(!end_program && opcao_utilizador == -1) {

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
                    break;
            } 
        }
        return end_program;
    }

    public static boolean controllerAlterar(Scanner scanner, Model model) {
        int opcao_utilizador = -1;
        boolean end_program = false;
        
        while(!end_program && opcao_utilizador == -1) {

            opcao_utilizador = Menu.menuAlterar(scanner);

            switch (opcao_utilizador) {

                case 1: // Alterar Fornecedor de uma Casa
                    controllerAlterar(scanner, model);
                    break;
                case 2: // Ligar/Desligar 1 dispositivo
                    controllerDeviceMode(scanner, model);
                    break;
                case 3: // Ligar TODOS os dispositivos de uma casa
                    controllerTurnAllDevice(scanner, model, 1);
                    break;
                case 4: // Desligar TODOS os dispositivos de uma casa
                    controllerTurnAllDevice(scanner, model, 0);
                    break;
                case 5: // Mudar fórmula de um fornecedor
                    end_program = end_program || controllerChangeFormula(scanner, model);
                    break;
                default: // Desligar o programa
                    end_program = true;
                    break;
            } 
        }
        return end_program;
    }

    public static boolean controllerChangeFormula(Scanner scanner, Model model) {
        
        String fornecedor;
        while(true) {
            fornecedor = Menu.menuEscolherFornecedor(scanner, model);
            if (model.hasFornecedor(fornecedor)) break;
        }
        boolean end_program = false;
        int formula;
        FormulaConsumo fc = null;
        while(true) {
            formula = Menu.menuFormula(scanner);
            switch (formula) {
                case 1:
                    Formula1 f1 = new Formula1();
                    fc = f1;
                case 2:
                    Formula2 f2 = new Formula2();
                    fc = f2;            
                case 3:
                    Formula3 f3 = new Formula3(); 
                    fc = f3;         
                case 4:
                    Formula4 f4 = new Formula4();   
                    fc = f4;            
                case 5:
                    Formula5 f5 = new Formula5();
                    fc = f5;              
                case 6:
                    Formula6 f6 = new Formula6();
                    fc = f6;
                default:
                    end_program = true;
            }
            if (end_program == true) break;
        }

        if (fc != null) model.changeFormula(fornecedor, fc);
        
        return end_program;
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
                continue;
            }
            break;
        }
        
        String id_device;
        while(true) {
            try {
                id_device = Menu.menuDevice(scanner);
            }
            catch (Exception e) { 
                continue;
            }
            break;
        }

        model.turnOpossite(model.getFromDate(), nif_casa, id_device);
    }

    public static void controllerTurnAllDevice(Scanner scanner, Model model, int mode) {
        String NIF;
        
        while(true) {
            try {
                NIF = Menu.menuCasa(scanner, model, 0);
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
        switch (mode) {
            case 1: // turnON
                model.setAllDeviceON(NIF);
            case 0: // turnOFF
                model.setAllDeviceOFF(NIF);
        }
    }

    public static boolean controllerEstatisticas(Scanner scanner, Model model) {
        boolean end_program = false;
        int opcao_utilizador = -1;
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
                break;
        }
        return end_program;
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
            switch (name_supplier) {

                case "0" : // imprimir todas
                    model.printSuppliers();
                    continue;
                default:
                    break;
            }
            break;
        }        
        Window.inicialWriteSupplier();
        model.invoicesPerSupplier(name_supplier);

        Window.fimLista();
    }

    public static void controllerEstatistica4(Scanner scanner, Model model) {
        LocalDate from_date = Menu.menuEstatistica4FromDate(scanner);
        LocalDate to_date = Menu.menuEstatistica4FromDate(scanner);

        Window.waitEstatisticas();

        List<String> list = model.biggestEnergyConsumers(from_date, to_date);

        Window.resultsEstatisticas();
        System.out.println("");
        for(String str : list){
            System.out.println(str);
        }

        Window.fimLista();
    }
}