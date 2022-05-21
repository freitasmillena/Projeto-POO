package Main;

import java.io.FileNotFoundException;
import Controller.Controller;
import Window.Window;

// javac -cp Main.java
public class Main {

    public static void main(String[] args) throws FileNotFoundException{
        Window.clear();
        Controller.run();
    }
}
