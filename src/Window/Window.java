package Window;

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

    public static void inicialWriteNIF() {
        System.out.println("");
        System.out.println("-------------| Lista de NIFs |-------------");
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

    public static void estatistica1() {

    }

    public static void estatistica2() {
        
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
}

