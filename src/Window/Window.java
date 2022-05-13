package Window;

// Classe auxiliar para a criação (ou eliminação) de conteúdo 
public class Window {
    // Vamos garantir que a janela do terminal está totalmente limpa
    public static void clear() {
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public static void createPOO() {
        System.out.println("");

        System.out.println("   *-*   -----   ------   ------   *-*");
        System.out.println("   *-*   |   |   |    |   |    |   *-*");
        System.out.println("   *-*   |----   |    |   |    |   *-*");
        System.out.println("   *-*   |       |    |   |    |   *-*");
        System.out.println("   *-*   |       -----.   ------   *-*");

        System.out.println("");
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

