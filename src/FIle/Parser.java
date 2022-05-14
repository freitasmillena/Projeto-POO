package FIle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Entities.Casa;
import Entities.Exceptions.*;
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
import Enums.Mode;
import Enums.Tone;

public class Parser {

    public boolean parseLogs(Model model) throws FileNotFoundException, SupplierAlreadyExists, HouseAlreadyExists, LocationAlreadyExists, DateAlreadyExistsException, LocationDoesntExist {
        List<String> linhas = lerFicheiro("logs.txt");
        boolean end_program = false; // Caso apareceça um registo inválido, paramos o programa
        String[] linhaPartida;
        String fornecedor_string = null; // ainda não foi inserido nenhum fornecedor
        String divisao_string = null; // ainda não foi inserida nenhuma divisão de uma casa
        Casa casaMaisRecente = null; // ainda não foi inserida nenhuma casa

        // Para fornecer os id's dos vários dispsoitivos
        int id_smartbulb = 0;
        int id_smartcamera = 0;
        int id_smartspeaker = 0;

        for (String linha : linhas) {
            linhaPartida = linha.split(":", 2);
            switch(linhaPartida[0]){

                case "Forncedor":
                    if (casaMaisRecente != null) {
                        end_program = true;
                        System.out.println("Linha inválida no ficheiro 'logs.txt'.");
                    }
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
                            break;
                    }
                    fornecedor.setInstallationCost(randomCustoInstalacao());
                    model.addFornecedor(fornecedor);
                    break;

                case "Casa":
                    if (casaMaisRecente != null) model.addCasa(casaMaisRecente);
                    if (fornecedor_string == null) System.out.println("Linha inválida no ficheiro 'logs.txt'."); 
                    casaMaisRecente = parseCasa(linhaPartida[1]);
                    break;

                case "Divisão":
                    if (casaMaisRecente == null) System.out.println("Linha inválida no ficheiro 'logs.txt'.");
                    divisao_string = linhaPartida[1];
                    casaMaisRecente.addLocation(divisao_string);
                    break;

                case "SmartBulb":
                    if (divisao_string == null) System.out.println("Linha inválida no ficheiro 'logs.txt'.");
                    SmartBulb sb = parseSmartBulb(linhaPartida[1], model, id_smartbulb);
                    casaMaisRecente.addDeviceToLocation(divisao_string, sb, sb.getConsumptionBase());
                    id_smartbulb++;
                    break;

                case "SmartSpeaker":
                    if (divisao_string == null) System.out.println("Linha inválida no ficheiro 'logs.txt'.");
                    SmartSpeaker ss = parseSmartSpeaker(linhaPartida[1], model, id_smartspeaker);
                    casaMaisRecente.addDeviceToLocation(divisao_string, ss, ss.getConsumptionBase());
                    id_smartspeaker++;
                    break;
                    
                case "SmartCamera":
                    if (divisao_string == null) System.out.println("Linha inválida no ficheiro 'logs.txt'.");
                    SmartCamera sc = parseSmartCamera(linhaPartida[1], model, id_smartcamera);
                    casaMaisRecente.addDeviceToLocation(divisao_string, sc, sc.getConsumptionBase());
                    id_smartcamera++;
                    break;

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
    public static void parseAdvanced(Model model) throws FileNotFoundException {
        // PREENCHER ------------------------------------------------------------------------------------------------------------
        List<String> linhas = lerFicheiro("     ");
        String[] linhaPartida;
        LocalDate date;
        Command command;
        for (String linha : linhas) {
            linhaPartida = linha.split(" ");
            date = LocalDate.parse(linhaPartida[0]);
            System.out.println(date + " " + linhaPartida[1]);
            switch (linhaPartida[1]) {
                case "setMode" -> { //data setMode casa dispositivo mode
                    Mode mode = model.whichMode(linhaPartida[4]);
                    try {
                        model.setModeAdvanced(linhaPartida[2], linhaPartida[3], mode, date);
                    } catch (InvalidDateException | DateAlreadyExistsException e) {
                        System.out.println(e.getMessage());
                    }
                } //data changeSupplier casa fornecedor
                case "changeSupplier", "changeFormula" -> { //data changeFormula fornecedor formula
                    command = new Command(date, linhaPartida[1], linhaPartida[2], linhaPartida[3]);
                    model.addComandBasic(command);
                }
            }

        }

    }



    public static List<String> lerFicheiro(String nomeFich) throws FileNotFoundException {
        List<String> lines;
        try { 
            lines = Files.readAllLines(Paths.get(nomeFich), StandardCharsets.UTF_8); 
        }
        catch(IOException exc) { 
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

    private static SmartBulb parseSmartBulb(String input, Model model, int id) {
        String[] campos = input.split(",");
        String tone_string = campos[0];
        double dimensao = Double.parseDouble(campos[1]);
        double consumo = Double.parseDouble(campos[2]);

        // Por definição, consideramos que os dispositivos são ligados ON, aquando da sua inserção numa casa
        Mode mode = Mode.ON;
        LocalDate data = model.getFromDate();
        Tone tone = parseTone(tone_string);
        if (tone_string.equals("Warm")) tone = Tone.WARM;
        else if (tone_string.equals("Neutral")) tone = Tone.NEUTRAL;
        else if (tone_string.equals("Cold")) tone = Tone.COLD;
        else tone = null;

        SmartBulb sb = new SmartBulb(String.valueOf(id), tone, consumo, dimensao, mode, data);
        return sb;
    }

    private static SmartSpeaker parseSmartSpeaker(String input, Model model, int id) {
        String[] campos = input.split(",");
        int volume = Integer.parseInt(campos[0]);
        String canal = campos[2];
        String marca = campos[3];
        double consumo = Double.parseDouble(campos[4]);

        // Por definição, consideramos que os dispositivos são ligados ON, aquando da sua inserção numa casa
        Mode mode = Mode.ON;
        LocalDate data = model.getFromDate();
        SmartSpeaker ss = new SmartSpeaker(String.valueOf(id), consumo, canal, volume, marca, mode, data);
        return ss;
    }

    private static SmartCamera parseSmartCamera(String input, Model model, int id) {
        String[] campos = input.split(",");
        String resolucao = campos[0];
        
        String[] resolucoes = resolucao.split("x");
        int resolucaoX = Integer.parseInt(resolucoes[1].substring(1, resolucoes[0].length() -1));
        int resolucaoY = Integer.parseInt(resolucoes[2].substring(0, resolucoes[1].length() -2));

        int fileSize = Integer.parseInt(campos[1]);
        double consumo = Double.parseDouble(campos[2]);

        // Por definição, consideramos que os dispositivos são ligados ON, aquando da sua inserção numa casa
        Mode mode = Mode.ON;
        LocalDate data = model.getFromDate();
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
    public static Tone parseTone(String s) {
        Tone tone;
        if (s.equals("Warm")) tone = Tone.WARM;
        else if (s.equals("Neutral")) tone = Tone.NEUTRAL;
        else if (s.equals("Cold")) tone = Tone.COLD;
        else tone = null;
        return tone;
    }

}
