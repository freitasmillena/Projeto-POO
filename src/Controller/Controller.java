package Controller;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Entities.Model;
import Entities.Exceptions.DateAlreadyExistsException;
import Entities.Exceptions.HouseAlreadyExists;
import Entities.Exceptions.InvalidDateException;
import Entities.Exceptions.LocationAlreadyExists;
import Entities.Exceptions.LocationDoesntExist;
import Entities.Exceptions.SupplierAlreadyExists;
import FIle.Parser;
import Window.Menu;
import Window.Window;

public class Controller {
    
    // Responsável por inicializar o programa
    public static void run() throws FileNotFoundException, SupplierAlreadyExists, HouseAlreadyExists, LocationAlreadyExists, DateAlreadyExistsException, LocationDoesntExist, InvalidDateException {
        boolean end_program = false; 

        Scanner scanner = new Scanner(System.in);

        Model model = new Model();
        LocalDate today = LocalDate.now();
        model.setFromDate(today); // definir a data de execução do programa como a data inicial do Model

        Window.createPOO();
        

        // ---| PARSING |---

        Window.viewParsing();
        Parser p = new Parser();
        try {
            end_program = p.parseLogs(model);
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            Window.finalText();
            System.exit(0);
        }
        catch (SupplierAlreadyExists e) {
            System.out.println(e.getMessage());
            Window.finalText();
            System.exit(0);
        }
        catch (HouseAlreadyExists e) {
            System.out.println(e.getMessage());
            Window.finalText();
            System.exit(0);
        }
        catch (LocationAlreadyExists e) {
            System.out.println(e.getMessage());
            Window.finalText();
            System.exit(0);
        }
        catch (DateAlreadyExistsException e) {
            System.out.println(e.getMessage());
            Window.finalText();
            System.exit(0);
        }
        catch (LocationDoesntExist e) {
            System.out.println(e.getMessage());
            Window.finalText();
            System.exit(0);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            Window.finalText();
            System.exit(0);
        }

        if (!end_program) {
            Window.parsingConcluded();

            // ---| Manual ou Automatização |---
            int opcao_inicial = -1;
            while(opcao_inicial == -1) {
                try{
                    opcao_inicial = Menu.menuInicial();
                }
                catch (InputMismatchException e) { // Não foi inscrito um int
                    System.out.println(e.getMessage());
                    opcao_inicial = -1;
                }  
            }
    
            
            // Para chegar a este ponto, o opcao_inicial
            switch (opcao_inicial) {
    
                case 1: // Automatizar a simulação
                    controllerAutomatizacao(model);
                    break;
    
                case 2: // Menu para o utilizador
                    while(!end_program) {
                        end_program = end_program || controllerUtilizador(model);
                    }
                    break;
                
                default: // Sair do programa
                    end_program = true;
                    break;
            }
        }
        scanner.close();
        Window.finalText();
    }

    public static void controllerAutomatizacao(Model model) throws FileNotFoundException {
        Window.parsingAdvanced();
        Parser.parseAdvanced(model); // CORRIGIR
        Window.parsingConcluded();

        int opcao_utilizador = Menu.menuOpcoesAutomatizacao();;

        switch (opcao_utilizador) {
            case 1 : // Estatísticas
                controllerEstatisticas(model);
                break;
        }

    }

    public static boolean controllerUtilizador(Model model) throws InvalidDateException, DateAlreadyExistsException {
        int opcao_utilizador = -1;
        boolean end_program = false;
        boolean avancou_tempo = false; // indica se o utilizador já avançou no tempo pelo menos uma vez
                                       // caso tenha, é disponibilizado as poções de ver as afturas e de evr as estatísticas
        while(!end_program && opcao_utilizador == -1) {
            opcao_utilizador = Menu.menuUtilizadorManual(avancou_tempo);

            if (!avancou_tempo) {
                switch (opcao_utilizador) {

                    case 1: // Alterar dados do modelo
                        end_program = end_program || controllerAlterar(model);
                        break;
                    case 2: // Avançar no tempo
                        avancou_tempo = true;
                        controllerTempo(model);
                        break;
                    default: // Sair do programa
                        end_program = true;
                        break;
                } 
            }
            else { // avancou_tempo 
                switch (opcao_utilizador) {

                    case 1: // Alterar dados do modelo
                        end_program = end_program || controllerAlterar(model);
                        break;
                    case 2: // Avançar no tempo
                        avancou_tempo = true;
                        controllerTempo(model);
                        break;
                    case 3: // Apresentar TODAS as Faturas
                        model.generateInvoices(model.getFromDate());
                        model.printInvoices();
                        break;
                    case 4: // Estatísticas
                        controllerEstatisticas(model);
                        break;
                    default: // Sair do programa
                        end_program = true;
                        break;
                } 
            }

           
        }

        return end_program;

    }

    public static boolean controllerAlterar(Model model) {
        int opcao_utilizador = -1;
        boolean end_program = false;
        
        while(!end_program && opcao_utilizador == -1) {

            opcao_utilizador = Menu.menuAlterar();

            switch (opcao_utilizador) {

                case 1: // Alterar Fornecedor de uma Casa
                    controllerAlterar(model);
                    break;
                case 2: // Ligar/Desligar 1 dispositivo
                    controllerDeviceMode(model);
                    break;
                case 3: // Ligar TODOS os dispositivos

                    break;
                case 4: // Desligar TODOS os dispositivos
                    
                    break;
                default: // Desligar o programa
                    end_program = true;
                    break;
            } 
        }
        return end_program;


        

        // model.setSupplier();
    }

    public static void controllerTempo(Model model) throws InvalidDateException, DateAlreadyExistsException {
        Window.clear();
        while(true) {
            try {
                LocalDate data_nova = Menu.menuTempo(model.getFromDate());
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


    public static void controllerDeviceMode(Model model) {
        int device = Menu.menuDispositivo();
    }

    public static boolean controllerEstatisticas(Model model) {
        boolean end_program = false;
        int opcao_utilizador = -1;
        opcao_utilizador = Menu.menuEstatisticas();

        switch (opcao_utilizador) {
            case 1: // Casa que mais gastou
                Window.estatistica1();
                break;
            case 2: // Comercializador com maior volume de faturação
                Window.estatistica2();
                break;
            case 3: // Faturas emitidas por um comercializador
                controllerEstatistica3(model);
                break;
            case 4: // maiores consumidores de energia
                controllerEstatistica4(model);
                break;
            default: // Desligar o programa
                end_program = true;
                break;
        }
        return end_program;
    }

    public static void controllerEstatistica3(Model model) {
        model.generateInvoicesFornecedor();
        System.out.println("");
        model.printInvoices();
    
    }

    public static void controllerEstatistica4(Model model) {
        LocalDate from_date = Menu.menuEstatistica4FromDate();
        LocalDate to_date = Menu.menuEstatistica4FromDate();

        Window.waitEstatisticas();

        List<String> list = model.biggestEnergyConsumers(LocalDate.parse("2022-05-13"),LocalDate.parse("2022-05-18"));

        Window.resultsEstatisticas();
        System.out.println("");
        for(String str : list){
            System.out.println(str);
        }
    }
}