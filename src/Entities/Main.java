package Entities;

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
            c2.addDeviceToLocation("Cozinha", new SmartBulb("lampada1", 0, 1.57, 3.57, 1, LocalDate.parse("2022-05-12")), model.getInstallationCost(c2.getSupplier()));
            c2.addDeviceToLocation("Jardim", new SmartBulb("lampada2", 2, 1.57, 3.57, 1, LocalDate.parse("2022-05-12")), model.getInstallationCost(c2.getSupplier()));
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

        SmartBulb sb = new SmartBulb("lampada1", 0, 1.57, 3.57,  1, LocalDate.parse("2022-05-12"));

        try {
            c1.addDeviceToLocation("Quarto", new SmartCamera("camera5", 2.1, 1388, 760, 20, 1, LocalDate.parse("2022-05-12")), model.getInstallationCost(c1.getSupplier()));
            c1.addDeviceToLocation("Sala", new SmartCamera("camera2", 2.1, 1388, 760, 20, 1, LocalDate.parse("2022-05-12")), model.getInstallationCost(c1.getSupplier()));
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



        Casa c3 = new Casa("Alice", "8910", "EDP");

        try {
            c3.addLocation("Banheiro");
            c3.addLocation("Quarto");
            //c3.addDeviceToLocation("Banheiro", new SmartSpeaker("alexa", 2.53, "FM O DIA", 35, "JBL", Mode.ON, LocalDate.parse("2022-05-12")), model.getInstallationCost(c2.getSupplier()));
           //c3.addDeviceToLocation("Quarto", new SmartSpeaker("siri", 2.53, "AM O DIA", 35, "Sony", Mode.ON, LocalDate.parse("2022-05-12")), model.getInstallationCost(c2.getSupplier()));
            model.addCasa(c3);
        }
        catch (Exception e){

        }

        SmartSpeaker sp = new SmartSpeaker("alexa", 2.53, "FM O DIA", 35, "JBL", 1, LocalDate.parse("2022-05-12"));
        SmartCamera sc = new SmartCamera("camera5", 2.1, 1388, 760, 20, 1, LocalDate.parse("2022-05-12"));


        model.generateInvoices(LocalDate.parse("2022-05-15"));
        model.printInvoices();

        List<String> list = model.biggestEnergyConsumers(LocalDate.parse("2022-05-13"),LocalDate.parse("2022-05-18"));

        for(String str : list){
            System.out.println(str);
        }

        System.out.println(list.size());

        try {
            System.out.println(model.higherTotalCost());
            System.out.println(model.hasMoreInvoices());
            System.out.println(model.invoicesPerSupplier("Luzboa"));
        }
        catch (Exception e){

        }


        System.out.println(sb);
        System.out.println(sp);
        System.out.println(sc);

    }
}
