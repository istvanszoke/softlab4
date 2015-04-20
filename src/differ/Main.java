import java.io.*;

public class Main {
    public static void main(String[] args){
        FileReader fr = null;

        try {
            fr = new FileReader(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println("Megadott ellenőrzőfájl nem megnyitható");
            return;
        }

        Differ differ = new Differ(new BufferedReader(new InputStreamReader(System.in)), new BufferedReader(fr));

        try {
            differ.generateOutputTo(new OutputStreamWriter(System.out));
        } catch (IOException e) {
            System.out.println("Hiba történt a kimenet írása közben");
        }

    }
}
