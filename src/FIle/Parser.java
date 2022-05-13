package FIle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Entities.Casa;
import Entities.Fornecedor;
import Entities.Model;
import Entities.SmartBulb;
import Entities.SmartCamera;
import Entities.SmartSpeaker;

public class Parser {


    public static void parse(Model model) throws FileNotFoundException {
        List<String> linhas = lerFicheiro("logs.txt");
        String[] linhaPartida;
        String fornecedor_string = null; // ainda não foi inserido nenhum fornecedor
        String divisao_string = null; // ainda não foi inserida nenhuma divisão de uma casa
        Casa casaMaisRecente = null; // ainda não foi inserida nenhuma casa
        for (String linha : linhas) {
            linhaPartida = linha.split(":", 2);
            switch(linhaPartida[0]){
                case "Forncedor":
                    if (casaMaisRecente != null) System.out.println("Linha inválida");
                    fornecedor_string = linhaPartida[1];
                    Fornecedor fornecedor = parseFornecedor(fornecedor_string);
                    model.addFornecedor(fornecedor);

                case "Casa":
                    if (fornecedor == null) 
                    casaMaisRecente = parseCasa(linhaPartida[1]);
                    model.addCasa(casaMaisRecente);

                    break;
                case "divisao_string":
                    if (casaMaisRecente == null) System.out.println("Linha inválida no ficheiro 'logs.txt'.");
                    divisao_string = linhaPartida[1];
                    casaMaisRecente.addLocation(divisao_string);
                    

                    break;
                case "SmartBulb":
                    if (divisao_string == null) System.out.println("Linha inválida no ficheiro 'logs.txt'.");
                    SmartBulb sb = parseSmartBulb(linhaPartida[1]);
                    casaMaisRecente.addDevice(sb);
                    casaMaisRecente.addDeviceToLocation(divisao_string, sb.getId());
                    

                    break;
                case "SmartSpeaker":
                    if (divisao_string == null) System.out.println("Linha inválida no ficheiro 'logs.txt'.");
                    SmartBulb sd = parseSmartBulb(linhaPartida[1]);
                    casaMaisRecente.addDevice(sd);
                    casaMaisRecente.addDeviceToLocation(divisao_string, sd.getId());


                    break;
                case "SmartCamera":


                    break;
                default:
                    System.out.println("Linha inválida.");
                    break;
            }

        }
        System.out.println("done!");
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


    private static Fornecedor parseFornecedor(String string) {
        return null;
    }

    public static Casa parseCasa(String input){
        String[] campos = input.split(",");
        String nome = campos[0];
        int nif = Integer.parseInt(campos[1]);
        
        
        return new Casa();
    }        

    private static SmartBulb parseSmartBulb(String string) {
        return null;
    }

    private SmartSpeaker parseSmartSpeaker(String string) {
        return null;
    }

    private SmartCamera parseSmartCamera(String string) {
        return null;
    }

}
