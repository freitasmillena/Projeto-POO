package Window;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

import Entities.Model;
import Entities.Exceptions.HouseAlreadyExists;
import Entities.Exceptions.HouseDoesntExists;
import Entities.Exceptions.SupplierDoesntExists;

public class Menu {

    // Menu que saberá se o utilizador quer automatizar a simulção ou inserir manualmente as alterações ao estado do programa
    public static int menuInicial(Scanner scanner) {
        System.out.println("");
        System.out.println("-----------| MENU INICIAL |-----------");
        System.out.println("");
        System.out.println("-> 1) Automatizar a simulação");
        System.out.println("-> 2) Escrever dados manualmente");
        System.out.println("-> 3) Guardar o estado do programa");
        System.out.println("-> (Qualquer outra tecla) SAIR");
        System.out.println("");
        int opcao_utilizador;

        while (true) {
            System.out.print("Selecione a opção pretendida: ");
            try {
                opcao_utilizador = Integer.parseInt(scanner.nextLine());
                System.out.println("");
                return opcao_utilizador;
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO + "!!! Tipo Inválido !!! Por favor, insira um valor válido." + Cores.RESET);
                System.out.println("");
                continue;
            }
        }
    }
 

    public static int menuUtilizadorManual(Scanner scanner, boolean avancou_tempo) {
        System.out.println("");
        System.out.println("--------| MENU - Escrever Manualmente (Com ou Sem automatização) |--------");
        System.out.println("");
        System.out.println("-> 1) Inserir dados");
        System.out.println("-> 2) Alterar dados");
        System.out.println("-> 3) Avançar no tempo");
        System.out.println("-> 4) Guardar o estado do programa");
        if (!avancou_tempo) {
            System.out.println("-> 5) Apresentar TODAS as Faturas (INDISPONíVEL - Avançe no tempo)");
            System.out.println("-> 6) Estatísticas (INDISPONíVEL - Avançe no tempo)");
        }
        else {
            System.out.println("-> 5) Apresentar TODAS as Faturas");
            System.out.println("-> 6) Estatísticas");
        }
        System.out.println("-> (Qualquer outra tecla) SAIR");
        System.out.println("");

        int opcao_utilizador;

        while (true) {
            System.out.print("Selecione a opção pretendida: ");
            try {
                opcao_utilizador =  Integer.parseInt(scanner.nextLine());
                System.out.println("");
                return opcao_utilizador;
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO + "!!! Tipo Inválido !!! Por favor, insira um valor válido." + Cores.RESET);
                System.out.println("");
                continue;
            }
        }
    }

    public static LocalDate menuTempo(Scanner scanner, LocalDate data_atual) {
        System.out.println("");
        System.out.println("----------------| MENU - Avançar no Tempo |----------------");
        System.out.println("");
        System.out.println("       Data atual: " + data_atual.toString());
        System.out.println("");
        System.out.println(Cores.VERMELHO + "NOTA: Não possível retroceder no tempo!!!" + Cores.RESET);
        System.out.println("      Ou seja, não pode fornecer uma data igual ou anterior à apresentada acima!");
        System.out.println("");
        LocalDate data_nova;
        while (true) {
            System.out.println("(Estrutura de uma data -> yyyy-MM-dd (yyyy - ano; MM - mês; dd - dia))");
            System.out.print("Escreve a data a que quer avançar: ");
            try {

                data_nova = LocalDate.parse(scanner.nextLine());
                System.out.println("");
                return data_nova;
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO + "!!! Tipo Inválido !!! Por favor, insira um valor válido." + Cores.RESET);
                System.out.println("");
                continue;
            }
        }
    }

    public static int menuInserir(Scanner scanner) {
        System.out.println("");
        System.out.println("-------| MENU - Inserir |-------");
        System.out.println("");
        System.out.println("-> 1) Comercializador/Fornecedor");
        System.out.println("-> 2) Casa");
        System.out.println("-> 3) Divisão numa casa");
        System.out.println("-> 4) Dispositivo numa divisão de uma casa");
        System.out.println("-> (Qualquer outra tecla) VOLTAR");
        System.out.println("");
        int opcao_utilizador;

        while (true) {
            System.out.print("Selecione a opção pretendida: ");
            try {

                opcao_utilizador = Integer.parseInt(scanner.nextLine());
                System.out.println("");
                return opcao_utilizador;
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO + "!!! Tipo Inválido !!! Por favor, insira um valor válido." + Cores.RESET);
                System.out.println("");
                continue;
            }
        }
    }

    public static String menuNomeDonoCasa(Scanner scanner) {
        System.out.println("");
        String nome = null;
        System.out.print("Escreva o nome do dono da casa: ");
        try {
            nome = scanner.nextLine();
            System.out.println("");
            return nome;
        }
        catch (java.util.InputMismatchException e) {
            System.out.println("");
            System.out.println("");
            System.out.println(Cores.VERMELHO + "!!! Tipo Inválido !!! Por favor, insira um valor válido." + Cores.RESET);
            System.out.println("");
        }
        return nome;
    }

    // 1 - criar casa
    // 2 - usar casa que já existe
    public static String menuCasa(Scanner scanner, Model model, int opc) throws HouseAlreadyExists, HouseDoesntExists {
        System.out.println("");
        int NIF;

        while (true) {
            System.out.println("(Escreva 0 para ver os NIF's das casa)");
            System.out.print("Escreva o NIF da casa: ");
            try {
                NIF = Integer.parseInt(scanner.nextLine());
                System.out.println("");
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO + "!!! Tipo Inválido !!! Por favor, insira um valor válido." + Cores.RESET);
                System.out.println("");
                continue;
            }
            if ((opc == 1) && model.hasCasa(Integer.toString(NIF))) { // criar casa
                throw new HouseAlreadyExists("!!! House already exists !!!");
            }
            else if ((opc == 0) && (NIF != 0 && !model.hasCasa(Integer.toString(NIF)))) {
                throw new HouseDoesntExists("!!! House doesn't exist !!!");
            }


            if (NIF == 0) { 
                Window.casa();
                System.out.println(Arrays.toString(model.printNIFs().toArray()));
                continue;
            }
            break;
        }
        return Integer.toString(NIF);
    }

    public static String menuLocation(Scanner scanner) {
        System.out.println("");
        String divisao = null;
        System.out.print("Escreva a divisão que quer inserir na casa: ");
        try {
            divisao = scanner.nextLine();
            System.out.println("");
            return divisao;
        }
        catch (java.util.InputMismatchException e) {
            System.out.println("");
            System.out.println("");
            System.out.println(Cores.VERMELHO + "!!! Tipo Inválido !!! Por favor, insira um valor válido." + Cores.RESET);
            System.out.println("");
        }
        return divisao;
    }

    public static String menuDevice(Scanner scanner) {
        int opcao_utilizador;
        while (true) {
            System.out.println("");
            System.out.print("Selecione a opção pretendida: ");
            try {
                opcao_utilizador = Integer.parseInt(scanner.nextLine());
                System.out.println("");
                break;
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO + "!!! Tipo Inválido !!! Por favor, insira um valor válido." + Cores.RESET);
                System.out.println("");
                continue;
            }
        }
        return Integer.toString(opcao_utilizador);
    }

    public static int menuChooseDevice(Scanner scanner) {
        System.out.println("");
        System.out.println("-------| MENU - Inserir 'SmartDevice' |-------");
        System.out.println("");
        System.out.println("-> 1) SmartBulb");
        System.out.println("-> 2) SmartSpeaker");
        System.out.println("-> 3) SmarCamera");
        System.out.println("-> (Qualquer outra tecla) VOLTAR");
        int opcao_utilizador;

        while (true) {
            System.out.println("");
            System.out.print("Selecione a opção pretendida: ");
            try {
                opcao_utilizador = Integer.parseInt(scanner.nextLine());
                System.out.println("");
                return opcao_utilizador;
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO + "!!! Tipo Inválido !!! Por favor, insira um valor válido." + Cores.RESET);
                System.out.println("");
                continue;
            }
        }
    }


    public static int menuAlterar(Scanner scanner) {
        System.out.println("");
        System.out.println("-------| MENU - Alterar |-------");
        System.out.println("");
        System.out.println("-> 1) Alterar Fornecedor de uma Casa");
        System.out.println("-> 2) Ligar/Desligar dispositivo (individualmente) de uma casa");
        System.out.println("-> 3) Ligar TODOS os dispositivos de uma casa");
        System.out.println("-> 4) Desligar TODOS os dispositivos de uma casa");
        System.out.println("-> 5) Alterar Fórmula de um fornecedor");
        System.out.println("-> (Qualquer outra tecla) VOLTAR");
        System.out.println("");
        int opcao_utilizador;

        while (true) {
            System.out.print("Selecione a opção pretendida: ");
            try {
                opcao_utilizador = Integer.parseInt(scanner.nextLine());
                System.out.println("");
                return opcao_utilizador;
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO + "!!! Tipo Inválido !!! Por favor, insira um valor válido." + Cores.RESET);
                System.out.println("");
                continue;
            }
        }
    }


    public String menuFornecedorNIF(Scanner scanner, Model model) {
        System.out.println("");
        System.out.println("-------| MENU - Alterar - Fornecedor |-------");
        System.out.println("");
        int NIF;

        while (true) {
            System.out.print("Escreva o NIF da Casa (Caso queira ver a lista dos NIF's de todas casa, escreva 0): ");
            try {

                NIF = Integer.parseInt(scanner.nextLine());
                System.out.println("");
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO + "!!! Tipo Inválido !!! Por favor, insira um valor válido." + Cores.RESET);
                System.out.println("");
                continue;
            }
            if (NIF == 0) { 
                System.out.println(Arrays.toString(model.printNIFs().toArray()));
                continue;
            }
            break;
        }
        return Integer.toString(NIF);
    }

    public static String menuFornecedorName(Scanner scanner, Model model) throws SupplierDoesntExists {
        System.out.println("");
        String name_fornecedor;
        System.out.println("(Escreva 0 para visaulizar todas as marcas)");
        while (true) {
            System.out.print("Escreva o nome da marca do NOVO Fornecedor/Comercializador da casa: ");
            try {

                name_fornecedor = scanner.nextLine();
                System.out.println("");
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO + "!!! Tipo Inválido !!! Por favor, insira um valor válido." + Cores.RESET);
                System.out.println("");
                continue;
            }
            
            if (name_fornecedor.equals("0")) { 
                System.out.println(Arrays.toString(model.printSuppliers().toArray()));
                continue;
            }
            break;
        } 
        return name_fornecedor;
    }

    public static String menuEscolherFornecedor(Scanner scanner, Model model) {
        System.out.println("");
        String name_fornecedor;
        System.out.println("(Escreva 0 para visaulizar todas as marcas de comercializadores)");
        while (true) {
            System.out.print("Escreva o nome da marca do Fornecedor/Comercializador da casa: ");
            try {

                name_fornecedor = scanner.nextLine();
                System.out.println("");
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO + "!!! Tipo Inválido !!! Por favor, insira um valor válido." + Cores.RESET);
                System.out.println("");
                continue;
            }
            
            if (name_fornecedor.equals("0")) { 
                System.out.println(Arrays.toString(model.printSuppliers().toArray()));
                continue;
            }
            break;
        } 
        return name_fornecedor;
    }





    public static int menuEstatisticas(Scanner scanner) {
        System.out.println("");
        System.out.println("-------| Obter dados para as Estatísticas |-------");
        System.out.println("");
        System.out.println("-> 1) (NIF da) Casa que mais gastou até à data atual da simulação");
        System.out.println("-> 2) (Nome do) Comercializador com maior volume de faturação");
        System.out.println("-> 3) Listar TODAS as faturas emitidas por um comercializador");
        System.out.println("-> 4) Listar os maiores consumidores de energia durante um período");
        System.out.println("-> (Qualquer outra tecla) VOLTAR");
        System.out.println("");
        int opcao_utilizador;

        while(true) {
            System.out.print("Selecione a opção pretendida: ");
            try {
                opcao_utilizador = Integer.parseInt(scanner.nextLine());
                System.out.println("");
                return opcao_utilizador;
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO  + e.getMessage() + Cores.RESET);
                System.out.println("");
                continue;
            }
        }
    }

    public static String menuEstatistica3(Scanner scanner, Model model) throws SupplierDoesntExists {

        String name_supplier;

        while(true) {
            System.out.println("");
            System.out.println("(Para consultar os nomes de todos os comercializadores, escreva 0)");
            System.out.print("Escreva o nome do comercializador : ");
            try {
                name_supplier = scanner.nextLine();
                System.out.println("");
                if(!name_supplier.equals("0") && model.verifyFornecedor(name_supplier)){
                    throw new SupplierDoesntExists("O fornecedor com nome: " + name_supplier + " não existe.");
                }
                else if (name_supplier.equals("0")) {
                    Window.inicialWriteSupplier();
                    System.out.println("");
                    System.out.println(Arrays.toString(model.printSuppliers().toArray()));
                    System.out.println("");
                    continue;
                }
                return name_supplier;
            } 
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO + "!!! Data Inválida !!! Por favor, siga o formato de uma data." + Cores.RESET);
                System.out.println("");
                continue;
            }
        }
    }

    public static LocalDate menuEstatistica4FromDate(Scanner scanner) {
        Window.resultsEstatisticas();
        System.out.println("");
        System.out.println("----| 4. Listar os maiores consumidores de energia durante um período |----");
        System.out.println("");
        System.out.println("Para calcular o ponto 4. das estatísticas, precisamos do período das datas que deseja.");
        System.out.println("");
        System.out.println("Estrutura de uma data -> yyyy-MM-dd (yyyy - ano; MM - mês; dd - dia)");

        LocalDate data_inicial;

        while(true) {
            System.out.print("Escreva a data inicial: ");
            try {

                data_inicial = LocalDate.parse(scanner.nextLine());
                System.out.println("");
                return data_inicial;
            } 
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO + "!!! Data Inválida !!! Por favor, siga o formato de uma data." + Cores.RESET);
                System.out.println("");
                continue;
            }
        }
    }

    public static LocalDate menuEstatistica4ToDate(Scanner scanner) {
        LocalDate data_final;

        while(true) {
            System.out.print("Escreva a data final: ");
            try {
                data_final = LocalDate.parse(scanner.nextLine());
                System.out.println("");
                return data_final;
            } 
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO + "!!! Tipo Inválido !!! Por favor, insira um valor válido." + Cores.RESET);
                System.out.println("");
                continue;
            }
        }
    }



    public static int menuOpcoesAutomatizacao(Scanner scanner) {
        System.out.println("");
        System.out.println("-------| MENU - Automatização |-------");
        System.out.println("");
        System.out.println("-> 1) Estatísticas");
        System.out.println("-> 2) Guardar o estado do programa");
        System.out.println("-> (Qualquer outra tecla) VOLTAR");
        System.out.println("");
        int opcao_utilizador;

        while (true) {
            System.out.print("Selecione a opção pretendida: ");
            try {

                opcao_utilizador = Integer.parseInt(scanner.nextLine());
                System.out.println("");
                return opcao_utilizador;
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO + "!!! Tipo Inválido !!! Por favor, insira um valor válido." + Cores.RESET);
                System.out.println("");
                continue;
            }
        }
    }



    public int menuDispositivo(Scanner scanner) {
        int opcao_utilizador;
        System.out.println("");
        System.out.println("(Escreva 0 para ver a lista de todos os dispostivos da casa atual)");
        while (true) {
            System.out.print("Selecione o número do dispositivo pretendido: ");
            try {
                opcao_utilizador = Integer.parseInt(scanner.nextLine());
                System.out.println("");
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO + "!!! Tipo Inválido !!! Por favor, insira um valor válido." + Cores.RESET);
                System.out.println("");
                continue;
            }
            
            return opcao_utilizador;
        }
    }

    public static int menuFormula (Scanner scanner) {
        System.out.println("");
        System.out.println("-------| MENU - Formula |-------");
        System.out.println("");
        System.out.println("-> 1) Formula1");
        System.out.println("-> 2) Formula2");
        System.out.println("-> 3) Formula3");
        System.out.println("-> 4) Formula4");
        System.out.println("-> 5) Formula5");
        System.out.println("-> 6) Formula6");
        System.out.println("-> (Qualquer outra tecla) VOLTAR");
        System.out.println("");
        int opcao_utilizador;

        while (true) {
            System.out.print("Selecione a opção pretendida: ");
            try {

                opcao_utilizador = Integer.parseInt(scanner.nextLine());
                System.out.println("");
                return opcao_utilizador;
            }
            catch (java.util.InputMismatchException e) {
                System.out.println("");
                System.out.println("");
                System.out.println(Cores.VERMELHO + "!!! Tipo Inválido !!! Por favor, insira um valor válido." + Cores.RESET);
                System.out.println("");
                continue;
            }
        }
    }
}