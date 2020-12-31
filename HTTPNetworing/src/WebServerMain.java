import java.io.File;
import java.io.FileNotFoundException;

public class WebServerMain {

    /**
     *Initializes new WebServer when run from command line.
     * @param args - <document_root> <port> e.g ../www 12345
     */
    public static void main(String[] args) {

        try {
            if (args.length != 2) { //throws exception if too many or too few command line arguments.
                throw new Exception();
            }
            String filepath = args[0];
            int port = Integer.parseInt(args[1]);
            File dir = new File(filepath);
            boolean exists = dir.exists(); //check that directory from command line e.g ../wwx exists.
            if (!exists) { //raises exception and exits if it does not exist.
                throw new FileNotFoundException();
            }
            new WebServer(port, filepath);
        }
        catch (FileNotFoundException f) { //catches missing directory
            System.out.println("Directory not found.");
        }
        catch (Exception e) { //catches all other errors due including missing command line statments.
            System.out.println("Usage: java WebServerMain <document_root> <port>");

        }
    }
}
