package Entities;

import Enums.Mode;
import Enums.Tone;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) throws FileNotFoundException{

        //Controller.run();

        Model model = new Model();
        model.setFromDate(LocalDate.parse("2022-05-12"));

        model.addFormula("LuzBoa", new Formula1());
        model.addFormula("EDP", new Formula2());
        model.addFormula("GoldEnergy",  new Formula3());

        try {
            model.addFornecedor(new Fornecedor("Luzboa", model.getFormula("LuzBoa"), 2.5));
            model.addFornecedor(new Fornecedor("EDP", model.getFormula("EDP"), 2));
            model.addFornecedor(new Fornecedor("GoldEnergy", model.getFormula("GoldEnergy"), 3));
        }
        catch (Exception e){
            System.out.println(e);
        }


        Casa c2 = new Casa("Rafael", "4567", "EDP" );

        try {
            c2.addLocation("Cozinha");
            c2.addLocation("Jardim");
        }
        catch (Exception e){
            System.out.println(e);
        }

        try {
            c2.addDeviceToLocation("Cozinha", new SmartBulb("lampada1", Tone.COLD, 1.57, 3.57, Mode.ON, LocalDate.parse("2022-05-12")), model.getInstallationCost(c2.getSupplier()));
            c2.addDeviceToLocation("Jardim", new SmartBulb("lampada2", Tone.WARM, 1.57, 3.57, Mode.ON, LocalDate.parse("2022-05-12")), model.getInstallationCost(c2.getSupplier()));
        }
        catch (Exception e){
            System.out.println(e);
        }
        try {
            model.addCasa(c2);
        }
        catch (Exception e){
            System.out.println(e);
        }



        //String id, Tone tone, double consumptionBase, double dimension
        Casa c1 = new Casa("Zezinho", "1234", "Luzboa");
        try {
            c1.addLocation("Quarto");
            c1.addLocation("Sala");
        }
        catch (Exception e){
            System.out.println(e);
        }

        SmartBulb sb = new SmartBulb("lampada1", Tone.COLD, 1.57, 3.57,  Mode.ON, LocalDate.parse("2022-05-12"));

        try {
            c1.addDeviceToLocation("Quarto", new SmartCamera("camera", 2.1, 1388, 760, 20, Mode.ON, LocalDate.parse("2022-05-12")), model.getInstallationCost(c1.getSupplier()));
            c1.addDeviceToLocation("Sala", new SmartCamera("camera2", 2.1, 1388, 760, 20, Mode.ON, LocalDate.parse("2022-05-12")), model.getInstallationCost(c1.getSupplier()));
        }

        catch (Exception e){
            System.out.println(e);
        }

        try {
            model.addCasa(c1);
        }
        catch (Exception e){
            System.out.println(e);
        }



        model.generateInvoices(LocalDate.parse("2022-05-15"));
        model.printInvoices();

        List<String> list = model.biggestEnergyConsumers(LocalDate.parse("2022-05-13"),LocalDate.parse("2022-05-18"));

        for(String str : list){
            System.out.println(str);
        }

        System.out.println(list.size());



        /*
        Casa c3 = new Casa("Alice", "8910", "GoldEnergy");

        c3.addLocation("Banheiro");
        c3.addLocation("Quarto");
        c3.addDeviceToLocation("Banheiro", new SmartSpeaker("alexa", 2.53, "FM O DIA", 35, "JBL", Mode.ON, LocalDate.parse("2022-05-12")), model.getInstallationCost(c2.getSupplier()));
        c3.addDeviceToLocation("Quarto", new SmartSpeaker("siri", 2.53, "AM O DIA", 35, "Sony", Mode.ON, LocalDate.parse("2022-05-12")), model.getInstallationCost(c2.getSupplier()));

        model.addCasa(c3);
        model.generateInvoices(LocalDate.parse("2022-05-15"));

        model.printInvoices();


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
