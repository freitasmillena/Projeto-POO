package Window;

import java.util.Scanner;

public class Menu {

    // Menu que saberá se o utilizador quer automatizar a simulção ou inserir manualmente as alterações ao estado do programa
    public static int menuInicial() {
        Window.clear();
        System.out.println("-----------| MENU INICIAL |-----------");
        System.out.println("");
        System.out.println("-> 1) Automatizar a simulação");
        System.out.println("-> 2) Inserir dados manualmente");
        System.out.println("-> 0) Sair");
        System.out.println("");
        while (true) {
            System.out.print("Selecione a opção pretendida: ");
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("");
                return scanner.nextInt();
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO  + "!!! Valor errado !!! Por favor, insira um valor válido entre 0 e 2." + Cores.RESET);
                System.out.println("");
                continue;
            }
        }
    }
 

    public static int menuUtilizadorInicial() {
        Window.clear();
        System.out.println("-----------| MENU |-----------");
        System.out.println("");
        System.out.println("-> 1) Inserir");
        System.out.println("-> 2) Modificar");
        System.out.println("-> 3) Avançar no tempo");
        System.out.println("-> 0) Sair");
        System.out.println("");
        while (true) {
            System.out.print("Selecione a opção pretendida: ");
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("");
                return scanner.nextInt();
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO  + "!!! Valor errado !!! Por favor, insira um valor válido entre 0 e 2." + Cores.RESET);
                System.out.println("");
                continue;
            }
        }
    }



    public static int menuInserir() {
        Window.clear();
        System.out.println("-------| MENU Inserir |-------");
        System.out.println("");
        System.out.println("-> 1) Fornecedor");
        System.out.println("-> 2) Casa Inteligente");
        System.out.println("-> 3) Divisão de uma casa");
        System.out.println("-> 4) Smart Device");
        System.out.println("-> 0) Sair");
        System.out.println("");
        while (true) {
            System.out.print("Selecione a opção pretendida: ");
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("");
                return scanner.nextInt();
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO  + "!!! Valor errado !!! Por favor, insira um valor válido entre 0 e 2." + Cores.RESET);
                System.out.println("");
                continue;
            }
        }
    }

    // INCOMPLETO - Terminar
    public static int menuInserirFornecedor() {
        Window.clear();
        System.out.println("-------| MENU Inserir |-------");
        System.out.println("");

        System.out.println("-> 0) Sair");
        System.out.println("");
        while (true) {
            System.out.print("Selecione a opção pretendida: ");
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("");
                return scanner.nextInt();
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO  + "!!! Valor errado !!! Por favor, insira um valor válido entre 0 e 2." + Cores.RESET);
                System.out.println("");
                continue;
            }
        }
    }
}
