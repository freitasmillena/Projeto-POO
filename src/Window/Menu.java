package Window;

import java.time.LocalDate;
import java.util.Scanner;

import Entities.Model;

public class Menu {

    // Menu que saberá se o utilizador quer automatizar a simulção ou inserir manualmente as alterações ao estado do programa
    public static int menuInicial() {
        Window.clear();
        System.out.println("-----------| MENU INICIAL |-----------");
        System.out.println("");
        System.out.println("-> 1) Automatizar a simulação");
        System.out.println("-> 2) Escrever dados manualmente");
        System.out.println("-> (Qualquer outra tecla) SAIR");
        System.out.println("");
        int opcao_utilizador;

        while (true) {
            System.out.print("Selecione a opção pretendida: ");
            try (Scanner scanner = new Scanner(System.in)) {
                opcao_utilizador = scanner.nextInt();
                System.out.println("");
                return opcao_utilizador;
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO  + "!!! Valor errado !!! Por favor, insira um valor válido." + Cores.RESET);
                System.out.println("");
                continue;
            }
        }
    }
 

    public static int menuUtilizadorManual(boolean avancou_tempo) {
        Window.clear();
        System.out.println("-----------| MENU - Escrever Manualmente |-----------");
        System.out.println("");
        System.out.println("-> 1) Alterar dados");
        System.out.println("-> 2) Avançar no tempo");
        if (avancou_tempo) {
            System.out.println("-> 3) Apresentar TODAS as Faturas");
            System.out.println("-> 4) Estatísticas");
        }
        System.out.println("-> (Qualquer outra tecla) SAIR");
        System.out.println("");
        int opcao_utilizador;

        while (true) {
            System.out.print("Selecione a opção pretendida: ");
            try (Scanner scanner = new Scanner(System.in)) {
                opcao_utilizador =  scanner.nextInt();
                System.out.println("");
                return opcao_utilizador;
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO  + "!!! Valor errado !!! Por favor, insira um valor válido." + Cores.RESET);
                System.out.println("");
                continue;
            }
        }
    }


    public static LocalDate menuTempo(LocalDate data_atual) {
        System.out.println("----------------| MENU - Avançar no Tempo |----------------");
        System.out.println("");
        System.out.println("       Data atual: " + data_atual.toString());
        System.out.println("");
        System.out.println(Cores.VERMELHO + "NOTA: Não possível retroceder no tempo!!!" + Cores.RESET);
        System.out.println("      Ou seja, não pode fornecer uma data igual ou anterior à apresentada acima!");
        System.out.println("");
        LocalDate data_nova;
        while (true) {
            System.out.print("Estrutura de uma data -> yyyy-MM-dd (yyyy - ano; MM - mês; dd - dia)");
            System.out.print("Escreve a data a que quer avançar: ");
            try (Scanner scanner = new Scanner(System.in)) {
                data_nova = LocalDate.parse(scanner.nextLine());
                System.out.println("");
                return data_nova;
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO  + "!!! Data errada !!! Por favor, siga a estrutura correta de uma data" + Cores.RESET);
                System.out.println("");
                continue;
            }
        }
    }


    public static int menuAlterar() {
        Window.clear();
        System.out.println("-------| MENU - Alterar |-------");
        System.out.println("");
        System.out.println("-> 1) Alterar Fornecedor de uma Casa");
        System.out.println("-> 2) Ligar/Desligar dispositivo (individualmente)");
        System.out.println("-> 3) Ligar TODOS os dispositivos");
        System.out.println("-> 4) Desligar TODOS os dispositivos");
        System.out.println("-> 5) Alterar Fórmula de um fornecedor");
        System.out.println("-> (Qualquer outra tecla) SAIR");
        System.out.println("");
        int opcao_utilizador;

        while (true) {
            System.out.print("Selecione a opção pretendida: ");
            try (Scanner scanner = new Scanner(System.in)) {
                opcao_utilizador = scanner.nextInt();
                System.out.println("");
                return opcao_utilizador;
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

    public static String menuFornecedorNIF(Model model) {
        Window.clear();
        System.out.println("-------| MENU - Alterar - Fornecedor |-------");
        System.out.println("");
        int NIF;

        while (true) {
            System.out.print("Escreva o NIF da Casa (Caso queira ver a lista dos NIF's de todas casa, escreva 0): ");
            try (Scanner scanner = new Scanner(System.in)) {
                 NIF = scanner.nextInt();
                 System.out.println("");
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO  + "!!! Tipo errado !!! Por favor, insira um NIF válido." + Cores.RESET);
                System.out.println("");
                continue;
            }
            if (NIF == 0) { 
                model.printNIFs();
                continue;
            }
            break;
        }
        return Integer.toString(NIF);
    }

    public static String menuFornecedorName() {
        System.out.println("");
        String name_fornecedor;

        while (true) {
            System.out.println("Escreva o nome da marca do NOVO Fornecedor da casa: ");
            try (Scanner scanner = new Scanner(System.in)) {
                name_fornecedor = scanner.nextLine();
                System.out.println("");
                return name_fornecedor;
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO + "!!! Tipo errado !!! Por favor, insira um nome válido." + Cores.RESET);
                System.out.println("");
                continue;
            }
        } 
    }





    public static int menuEstatisticas() {
        Window.clear();
        System.out.println("-------| Obter dados para as Estatísticas |-------");
        System.out.println("");
        System.out.println("-> 1) (NIF da) Casa que mais gastou até à data atual da simulação");
        System.out.println("-> 2) (Nome do) Comercializador com maior volume de faturação");
        System.out.println("-> 3) Listar TODAS as faturas emitidas por um comercializador");
        System.out.println("-> 4) Listar os maiores consumidores de energia durante um período");
        System.out.println("-> (Qualquer outra tecla) SAIR");
        System.out.println("");
        int opcao_utilizador;

        while(true) {
            System.out.print("Selecione a opção pretendida: ");
            try (Scanner scanner = new Scanner(System.in)) {
                opcao_utilizador = scanner.nextInt();
                System.out.println("");
                return opcao_utilizador;
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO  + "!!! Tipo inválido !!! Por favor, insira um valor válido entre 0 e 2." + Cores.RESET);
                System.out.println("");
                continue;
            }
        }
    }

    public static void menuEstatistica3() {
        Window.resultsEstatisticas();
        System.out.println("----| 3. Listar TODAS as faturas emitidas por um comercializador |----");
    }

    public static LocalDate menuEstatistica4FromDate() {
        Window.resultsEstatisticas();
        System.out.println("----| 4. Listar os maiores consumidores de energia durante um período |----");
        System.out.println("");
        System.out.println("Para calcular o ponto 4. das estatísticas, precisamos do período das datas que deseja.");
        System.out.println("");
        System.out.print("Estrutura de uma data -> yyyy-MM-dd (yyyy - ano; MM - mês; dd - dia)");

        LocalDate data_inicial;

        while(true) {
            System.out.print("Escreva a data inicial: ");
            try (Scanner scanner = new Scanner(System.in);) {
                data_inicial = LocalDate.parse(scanner.nextLine());
                System.out.println("");
                return data_inicial;
            } 
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO  + "!!! Data errada !!! Por favor, siga a estrutura correta de uma data" + Cores.RESET);
                continue;
            }
        }
    }

    public static LocalDate menuEstatistica4ToDate() {
        LocalDate data_final;

        while(true) {
            System.out.print("Escreva a data inicial: ");
            try (Scanner scanner = new Scanner(System.in);) {
                data_final = LocalDate.parse(scanner.nextLine());
                System.out.println("");
                return data_final;
            } 
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO  + "!!! Data errada !!! Por favor, siga a estrutura correta de uma data" + Cores.RESET);
                continue;
            }
        }
    }



    public static int menuOpcoesAutomatizacao() {
        Window.clear();
        System.out.println("-------| MENU - Alterar |-------");
        System.out.println("");
        System.out.println("-> 1) Estatísticas");
        System.out.println("-> (Qualquer outra tecla) SAIR");
        System.out.println("");
        int opcao_utilizador;

        while (true) {
            System.out.print("Selecione a opção pretendida: ");
            try (Scanner scanner = new Scanner(System.in)) {
                opcao_utilizador = scanner.nextInt();
                System.out.println("");
                return opcao_utilizador;
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO  + "!!! Valor errado !!! Por favor, insira um valor válido." + Cores.RESET);
                System.out.println("");
                continue;
            }
        }
    }
}