package Main;

import java.io.FileNotFoundException;
import Controller.Controller;
import Entities.Model;
import Window.Cores;
import Window.Window;
import Object.Object;

// javac -cp Main.java
public class Main {

    // 2 possibilidades de argumentos
    // 6 argumentos :  java -cp ./out Main.Main menu db/logs.txt db/auto.txt
    // 5 argumentos :  java -cp ./out Main.Main read modelObject.txt db/auto.txt
    public static void main(String[] args) throws FileNotFoundException{
        Window.clear();
        int argc = args.length;
        if (argc == 3 && args[0].equals("menu")) {
            try {
                Controller.run(args[1], args[2]);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        else if (argc == 3 && args[0].equals("read")) {
            Model model; 
            Window.carregarPrograma();
            try {
                model = Object.loadObject(args[1]);
                Controller.runObject(model, args[2]);
            }
            catch (FileNotFoundException e) {
                System.out.println("File not " + args[0] + " found");
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
        else {
            System.out.println(Cores.VERMELHO + "Wrong number of arguments" + Cores.RESET);
        }
    }
}
