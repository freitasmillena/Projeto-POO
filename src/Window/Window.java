package Window;

import Entities.Model;
import Entities.Exceptions.NoInvoicesAvailable;

// Classe auxiliar para a criação (ou eliminação) de conteúdo 
public class Window {
    // Vamos garantir que a janela do terminal está totalmente limpa
    public static void clear() {
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public static void createPOO() {
        Window.clear(); // limpar todo o conteúdo da linha de comandos

        System.out.println("");

        System.out.println("   *-*   -----   ------   ------   *-*");
        System.out.println("   *-*   |   |   |    |   |    |   *-*");
        System.out.println("   *-*   |----   |    |   |    |   *-*");
        System.out.println("   *-*   |       |    |   |    |   *-*");
        System.out.println("   *-*   |       -----.   ------   *-*");

        System.out.println("");
        System.out.println("");
    }

    public static void viewParsing() {
        System.out.println("*************************************************************");
        System.out.println("* Inicializado o parsing dos registos do ficheiro 'logs.txt *");
        System.out.println("*************************************************************");
        System.out.println("");
        System.out.println("   Por favor, espere!");
        System.out.println("");
    }

    public static void parsingConcluded() {
        System.out.println("*********************");
        System.out.println("* Parsing CONCLUIDO *");
        System.out.println("*********************");
        System.out.println("");
    }

    public void inicialWriteNIF() {
        System.out.println("");
        System.out.println("------------| Lista de NIFs dos donos das Casas |------------");
        System.out.println("");
    }

    public static void inicialWriteSupplier() {
        System.out.println("");
        System.out.println("-----------| Lista dos Nomes dos Comercializadores |-----------");
        System.out.println("");
    }

    public static void fimLista() {
        System.out.println("");
        System.out.println("****************");
        System.out.println("* FIM da Lista *");
        System.out.println("****************");
        System.out.println("");
    }

    public static void parsingAdvanced() {
        System.out.println("");
        System.out.println("**************************************************");
        System.out.println("* Inicializado o parsing automático dos comandos *");
        System.out.println("**************************************************");
        System.out.println("");
        System.out.println("   Por favor, espere!");
        System.out.println("");
    }

    public static void waitEstatisticas() {
        System.out.println("");
        System.out.println("********************************************");
        System.out.println("* A calcular os dados para as Estatísticas *");
        System.out.println("********************************************");
        System.out.println("");
        System.out.println("   Por favor, espere!");
        System.out.println("");
    }

    public static void estatistica1(Model model) {
        System.out.println("---| 1. Casa que mais gastou até à data atual da simulação |---");
        System.out.println("");
        String nif_casa;
        try {
            nif_casa = model.higherTotalCost();
            System.out.println("Resultado (NIF da Casa): " + nif_casa);
            System.out.println("");
        }
        catch (NoInvoicesAvailable e) {
            System.out.println(e.getMessage());
            System.out.println("");
        }
    }

    public static void estatistica2(Model model) {
        System.out.println("---| 2. Comercializador com maior volume de faturação |---");
        System.out.println("");
        String nome_comercializador;
        try {
            nome_comercializador = model.hasMoreInvoices();
            System.out.println("Nome de fornecedor: " + nome_comercializador);
            System.out.println("");
        }
        catch (NoInvoicesAvailable e) {
            System.out.println(e.getMessage());
            System.out.println("");
        }        
    }

    public static void resultsEstatisticas() {
        
        System.out.println("---| Estatísticas do programa |---");
        System.out.println("");
    }

    public static void finalText() {
        System.out.println("");

        System.out.println("   *-***************************-*");
        System.out.println("   *-*                         *-*");
        System.out.println("   *-*   -| End of program |-  *-*");
        System.out.println("   *-*                         *-*");
        System.out.println("   *-***************************-*");

        System.out.println("");
        System.out.println("");
    }

    public static void guardarPrograma() {
        System.out.println("");
        System.out.println("**********************************");
        System.out.println("* A guardar o estado do programa *");
        System.out.println("**********************************");
        System.out.println("");
        System.out.println("   Por favor, espere!");
        System.out.println("");
    }

    public static void carregarPrograma() {
        System.out.println("");
        System.out.println("***********************************");
        System.out.println("* A carregar o estado do programa *");
        System.out.println("***********************************");
        System.out.println("");
        System.out.println("   Por favor, espere!");
        System.out.println("");
    }
}

