import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Chip-8 Emulator");
        if(args.length < 1){
            return;
        }


        Display display = new Display();
        Processor processor = new Processor(display);
        display.clearFrame();
        System.out.println("Launching " + args[0]);
        try{
            Path path = Paths.get(args[0]);
            byte[] bytes = Files.readAllBytes(path);
            System.out.println("Length: " + bytes.length);
            processor.setCode(bytes);

            processor.parse();

        }catch(InvalidPathException e){
            System.out.println("File not found. Exiting");
        }catch(IOException e){
            System.out.println("IOException...");
        }catch(Exception e){
            System.out.println(e);
            return;
        }
    }
}
