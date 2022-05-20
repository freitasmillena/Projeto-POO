package FIle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Entities.Casa;
import Entities.Formula1;
import Entities.Formula2;
import Entities.Formula3;
import Entities.Formula4;
import Entities.Formula5;
import Entities.Formula6;
import Entities.Fornecedor;
import Entities.Model;
import Entities.SmartBulb;
import Entities.SmartCamera;
import Entities.SmartSpeaker;
import Entities.Command;
import Entities.Exceptions.*;

public class Parser {


    public boolean parseLogs(Model model, String fileName) throws FileNotFoundException, SupplierAlreadyExists, HouseAlreadyExists, LocationAlreadyExists, DateAlreadyExistsException, LocationDoesntExist {
        List<String> linhas = lerFicheiro(fileName);
        boolean end_program = false; // Caso apareceça um registo inválido, paramos o programa
        String[] linhaPartida;
        String fornecedor_string = null; // ainda não foi inserido nenhum fornecedor
        String divisao_string = null; // ainda não foi inserida nenhuma divisão de uma casa
        Casa casaMaisRecente = null; // ainda não foi inserida nenhuma casa

        LocalDate data = model.getFromDate();

        // Para fornecer os id's dos vários dispsoitivos
        int id_device = 1;

        for (String linha : linhas) {
            linhaPartida = linha.split(":", 2);
            switch(linhaPartida[0]){

                case "Fornecedor":
                    fornecedor_string = linhaPartida[1];
                    Fornecedor fornecedor = new Fornecedor();
                    fornecedor.setSupplier(fornecedor_string);
                    int num_formula = randomFormula();
                    switch(num_formula) {
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
                            end_program = true;
                            break;
                    }
                    fornecedor.setInstallationCost(randomCustoInstalacao());
                    model.addFornecedor(fornecedor);

                case "Casa":
                    if (casaMaisRecente != null) model.addCasa(casaMaisRecente);
                    if (fornecedor_string == null) { 
                        System.out.println("Linha inválida no ficheiro 'logs.txt'.");
                        end_program = true; 
                        break;
                    }
                    casaMaisRecente = parseCasa(linhaPartida[1]);
                    id_device = 1;

                case "Divisão":
                    if (casaMaisRecente == null) { 
                        System.out.println("Linha inválida no ficheiro 'logs.txt'.");
                        end_program = true;
                        break;
                    }
                    divisao_string = linhaPartida[1];
                    casaMaisRecente.addLocation(divisao_string);

                case "SmartBulb":
                    if (divisao_string == null) { 
                        System.out.println("Linha inválida no ficheiro 'logs.txt'.");
                        end_program = true; 
                        break;
                    }
                    SmartBulb sb = parseSmartBulb(linhaPartida[1], data, id_device);
                    casaMaisRecente.addDeviceToLocation(divisao_string, sb, sb.getConsumptionBase());
                    id_device++;

                case "SmartSpeaker":
                    if (divisao_string == null) { 
                        System.out.println("Linha inválida no ficheiro 'logs.txt'.");
                        end_program = true; 
                        break;
                    }
                    SmartSpeaker ss = parseSmartSpeaker(linhaPartida[1], data, id_device);
                    casaMaisRecente.addDeviceToLocation(divisao_string, ss, ss.getConsumptionBase());
                    id_device++;
                    
                case "SmartCamera":
                    if (divisao_string == null) { 
                        System.out.println("Linha inválida no ficheiro 'logs.txt'.");
                        end_program = true; 
                        break;
                    }
                    SmartCamera sc = parseSmartCamera(linhaPartida[1], data, id_device);
                    casaMaisRecente.addDeviceToLocation(divisao_string, sc, sc.getConsumptionBase());
                    id_device++;

                default:
                    System.out.println("Linha inválida no ficheiro 'logs.txt'.");
                    break;
            }
            if (casaMaisRecente != null) model.addCasa(casaMaisRecente);
            if (end_program == true) break;
        }
        return end_program;
    }

    // Paraser para ler os comandos do ficheiro de automatização da simulação
    public static boolean parseAdvanced(Model model, String fileName) throws FileNotFoundException {

        List<String> linhas = lerFicheiro(fileName); 
        boolean end_program = false; // Caso apareceça um registo inválido, paramos o programa
        String[] linhaPartida;

        LocalDate date;
        Command command;
        for (String linha : linhas) {
            linhaPartida = linha.split(" ");
            date = LocalDate.parse(linhaPartida[0]);
            System.out.println(date + " " + linhaPartida[1]);
            switch (linhaPartida[1]) {

                case "setMode" : //data setMode casa dispositivo mode
                    int mode = model.whichMode(linhaPartida[4]);
                    try {
                        model.setModeAdvanced(linhaPartida[2], linhaPartida[3], mode, date);
                    }
                    catch (InvalidDateException | DateAlreadyExistsException e) {
                        System.out.println(e.getMessage());
                    }
                
                case "changeSupplier": //data changeSupplier casa fornecedor
                case "changeFormula": //data changeFormula fornecedor formula
                    command = new Command(date, linhaPartida[1], linhaPartida[2], linhaPartida[3]);
                    model.addComandBasic(command);

                case "generateInvoices": //data generateInvoices
                    try {
                        model.moveForward(date);
                    }
                    catch (DateAlreadyExistsException | InvalidDateException e) {
                        System.out.println(e.getMessage());
                    }
                    catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                
                default: // Parar o programa
                    end_program = true;
            }
        }
        return end_program;
    }


    public static List<String> lerFicheiro(String nomeFich) throws FileNotFoundException {
        List<String> lines;
        try { 
            lines = Files.readAllLines(Paths.get(nomeFich), StandardCharsets.UTF_8); 
        }
        catch(IOException exc) { 
            System.out.println(exc.getMessage());
            lines = new ArrayList<>(); 
        }
        return lines;
    }

    public static Casa parseCasa(String input){
        String[] campos = input.split(",");
        String nome = campos[0];
        String nif = campos[1];
        String fornecedor = campos[2];
        Casa casa = new Casa(nome, nif, fornecedor);
        return casa;
    }

    private static SmartBulb parseSmartBulb(String input, LocalDate data, int id) {
        String[] campos = input.split(",");
        String tone_string = campos[0];
        double dimensao = Double.parseDouble(campos[1]);
        double consumo = Double.parseDouble(campos[2]);

        int mode;
        int mode_number = randomMode();
        if (mode_number == 0) mode = 0;
        else mode = 1;

        int tone = parseTone(tone_string);
        
        SmartBulb sb = new SmartBulb(String.valueOf(id), tone, consumo, dimensao, mode, data);
        return sb;
    }

    private static SmartSpeaker parseSmartSpeaker(String input, LocalDate data, int id) {
        String[] campos = input.split(",");
        int volume = Integer.parseInt(campos[0]);
        String canal = campos[2];
        String marca = campos[3];
        double consumo = Double.parseDouble(campos[4]);

        int mode;
        int mode_number = randomMode();
        if (mode_number == 0) mode = 0;
        else mode = 1;

        SmartSpeaker ss = new SmartSpeaker(String.valueOf(id), consumo, canal, volume, marca, mode, data);
        return ss;
    }

    private static SmartCamera parseSmartCamera(String input, LocalDate data, int id) {
        String[] campos = input.split(",");
        String resolucao = campos[0];
        
        String[] resolucoes = resolucao.split("x");
        int resolucaoX = Integer.parseInt(resolucoes[1].substring(1, resolucoes[0].length() -1));
        int resolucaoY = Integer.parseInt(resolucoes[2].substring(0, resolucoes[1].length() -2));

        int fileSize = Integer.parseInt(campos[1]);
        double consumo = Double.parseDouble(campos[2]);

        int mode;
        int mode_number = randomMode();
        if (mode_number == 0) mode = 0;
        else mode = 1;

        SmartCamera sc = new SmartCamera(String.valueOf(id), consumo, resolucaoX, resolucaoY, fileSize, mode, data);
        return sc;
    }


    // Seleciona aleatoriamente um inteiro da fórmula para aplicar a um fornecedor 
    // (já que no ficheiro 'logs.txt' não é fornecido )
    public static int randomFormula() {
        Random r = new Random();
        int low = 1;
        int high = 6;
        return r.nextInt(high-low) + low;
    }

    // Seleciona aleatoriamente o custo de instalação para aplicar a um fornecedor 
    // (já que no ficheiro 'logs.txt' não é fornecido )
    public static int randomCustoInstalacao() {
        Random r = new Random();
        int low = 2;
        int high = 4;
        return r.nextInt(high-low) + low;
    }

    // cria uma variável Tone, a partir do tone recebido pela função
    public static int parseTone(String s) {
        int tone;
        if (s.equals("Warm")) tone = 2;
        else if (s.equals("Neutral")) tone = 1;
        else if (s.equals("Cold")) tone = 0;
        else tone = 1;
        return tone;
    }

    // Seleciona aleatoriamente o Modo (ON ou OFF) para aplicar a um fornecedor 
    // (já que no ficheiro 'logs.txt' não é fornecido )
    // 0 -> OFF
    // 1 a 3 -> ON
    // Há uma probabilidade de 
    public static int randomMode() {
        Random r = new Random();
        int low = 0;
        int high = 3;
        return r.nextInt(high-low) + low;
    }
}
