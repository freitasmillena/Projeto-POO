package Entities;

import Enums.Mode;
import Enums.Tone;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args){

        Model model = new Model();
        model.setFromDate(LocalDate.parse("2022-05-12"));

        model.addFormula("LuzBoa", new Formula1());
        model.addFormula("EDP", new Formula2());
        model.addFormula("GoldEnergy",  new Formula3());

        model.addFornecedor(new Fornecedor("Luzboa", model.getFormula("LuzBoa"), 0, 2.5));
        model.addFornecedor(new Fornecedor("EDP", model.getFormula("EDP"), 0, 2));
        model.addFornecedor(new Fornecedor("GoldEnergy", model.getFormula("GoldEnergy"), 0, 3));

        //String id, Tone tone, double consumptionBase, double dimension
        Casa c1 = new Casa("Millena", "1234", "Luzboa", 0);
        c1.addLocation("Quarto");
        c1.addLocation("Sala");
        c1.addDeviceToLocation("Quarto", new SmartBulb("lampada1", Tone.COLD, 1.57, 3.57,  Mode.ON, LocalDate.parse("2022-05-12") ), model.getInstallationCost(c1.getSupplier()));
        c1.addDeviceToLocation("Sala", new SmartBulb("lampada2", Tone.WARM, 1.57, 3.57,  Mode.ON, LocalDate.parse("2022-05-12")), model.getInstallationCost(c1.getSupplier()));

        model.addCasa(c1);

        Casa c2 = new Casa("Rafael", "4567", "EDP", 0 );

        c2.addLocation("Cozinha");
        c2.addLocation("Jardim");
        c2.addDeviceToLocation("Cozinha", new SmartCamera("camera", 2.1, 1388,760,20,Mode.ON, LocalDate.parse("2022-05-12")), model.getInstallationCost(c2.getSupplier()));
        c2.addDeviceToLocation("Jardim", new SmartCamera("camera2", 2.1, 1388,760,20, Mode.ON, LocalDate.parse("2022-05-12")), model.getInstallationCost(c2.getSupplier()));

        model.addCasa(c2);

        Casa c3 = new Casa("Alice", "8910", "GoldEnergy", 0 );

        c3.addLocation("Banheiro");
        c3.addLocation("Quarto");
        c3.addDeviceToLocation("Banheiro", new SmartSpeaker("alexa", 2.53, "FM O DIA", 35, "JBL", Mode.ON, LocalDate.parse("2022-05-12")), model.getInstallationCost(c2.getSupplier()));
        c3.addDeviceToLocation("Quarto", new SmartSpeaker("siri", 2.53, "AM O DIA", 35, "Sony", Mode.ON, LocalDate.parse("2022-05-12")), model.getInstallationCost(c2.getSupplier()));

        model.addCasa(c3);

        model.generateInvoices(LocalDate.parse("2022-05-15"));

        model.printInvoices();

        /*
        String cod, int consumptionBase, String channel, int volume, String brand
        SortedMap<LocalDate, Log> logs = new TreeMap<LocalDate, Log>();
        logs.put(LocalDate.parse("2022-05-12"), new Log(LocalDate.parse("2022-05-12"), Mode.ON));
        logs.put(LocalDate.parse("2022-05-13"), new Log(LocalDate.parse("2022-05-13"), Mode.ON));
        logs.put(LocalDate.parse("2022-05-14"), new Log(LocalDate.parse("2022-05-14"), Mode.ON));
        logs.put(LocalDate.parse("2022-05-15"), new Log(LocalDate.parse("2022-05-15"), Mode.ON));
        logs.put(LocalDate.parse("2022-05-16"), new Log(LocalDate.parse("2022-05-16"), Mode.ON));

        for(Log l : logs.values()){
            System.out.println(l.getFromDate());
        }
        System.out.println(logs.get(LocalDate.parse("2022-05-13")));

        System.out.println("Tail is " + logs.tailMap(LocalDate.parse("2022-05-13")));
        System.out.println("Tail is " + logs.tailMap(LocalDate.parse("2022-05-14")));

         */
    }
}
