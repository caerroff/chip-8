import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Chip-8 Emulator");

        for(int i = 0; i < args.length; i++){
            System.out.println(args[i]);
        }

        if(args.length < 1){
            return;
        }

        Display display = new Display();
        display.clearFrame();
        System.out.println("Launching " + args[0]);
        try{
            FileReader reader = new FileReader(args[0]);
            reader.close();
        }catch(FileNotFoundException e){
            System.out.println("File not found. Exiting");
        }catch(IOException e){
            System.out.println("IOException...");
        }
    }
}
