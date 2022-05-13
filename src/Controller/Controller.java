package Controller;

import java.io.FileNotFoundException;
import java.time.LocalDate;

import Entities.Model;
import FIle.Parser;
import Window.Menu;
import Window.Window;

public class Controller {
    
    // Responsável por inicializar o programa
    public static void run() throws FileNotFoundException {

        // Map<String, Casa> casas = new HashMap<>();
        // Map<String, Fornecedor> fornecedores = new HashMap<>();
        // Map<String, Invoice> invoices = new HashMap<>();
        // LocalDate fromDate;

        Window.clear(); // limpar todo o conteúdo da linah de comandos

        Model model = new Model();
        LocalDate today = LocalDate.now();
        model.setFromDate(today); // definir a data de execução do programa como a data inicial do Model

        // boolean errorMessage = false;

        Window.createPOO();

        System.out.println("*************************************************************");
        System.out.println("* Inicializado o parsing dos registos do ficheiro 'logs.txt *");
        System.out.println("*************************************************************");
        System.out.println("");
        System.out.println("   Por favor, espere!");
        System.out.println("");

        Parser.parse(model);

        System.out.println("*********************");
        System.out.println("* Parsing CONCLUIDO *");
        System.out.println("*********************");
        System.out.println("");


        
        int opcao_inicial = -1;
        opcao_inicial = Menu.menuInicial();
        switch (opcao_inicial) {
            case 0: // Sair do programa

                break;

            case 1: // Automatizar a simulação
                controllerAutomatizacao(model);
                break;

            case 2: // Menu para o utilizador
                controllerUtilizador(model);
                break;
            default:
                break;
        }



    
    }

    public static void controllerAutomatizacao(Model model) {

    }

    public static void controllerUtilizador(Model model) {
        int opcao_utilizador = -1;

        boolean end_program = false;

        while(!end_program) {
            opcao_utilizador = Menu.menuUtilizadorInicial();

            switch (opcao_utilizador) {
                case 0: // Sair do programa
                    end_program = true;
                    break;
                case 1: // Inserir dados no modelo

                    break;
                case 2: // Alterar dados do modelo
                    
                    break;
                default:
                    break;
            }            
        }

        Window.finalText();

    }
}