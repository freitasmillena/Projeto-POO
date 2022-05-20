package Object;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Entities.Model;

public class Object {
    // função para escrever o ficheiro
    public static void writeObject(Model model) throws IOException {
        FileOutputStream f = new FileOutputStream(new File("modelObject.txt"));
        ObjectOutputStream o = new ObjectOutputStream(f);
        o.writeObject(model);
        o.close();
    }

    // funcão pra ler o ficheiro
    public static Model loadObject(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Model m = (Model) ois.readObject();
        ois.close();
        return m;
    }
}
