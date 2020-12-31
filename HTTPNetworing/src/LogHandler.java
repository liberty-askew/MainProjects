import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogHandler {

    /**
     *Handles writing log.txt for connection requests form ConnectionHandler.
     */
    private static FileWriter log_in; //op
    private static BufferedWriter log_bf;

    /**
     * Initializes log, by adding new socket banner, opening log.txt file and making new BufferedWriter.
     */
    public LogHandler() {

        try {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            log_in = new FileWriter("log.txt", true); //Opens file and sets append to true to add to file instead of overwriting
            log_bf = new BufferedWriter(log_in); //makes new buffered writter to be written on when updating the log
            log_bf.write("\r\n--------------------------NEW SOCKET------------------------------\r\n"); //banner for new socket in log file.
            log_bf.write("TIME:" + timestamp + "\r\n");
            log_bf.write("PORT:" + WebServer.port + "\r\n");
        }
        catch (Exception e) {
            System.out.println("Exception Log Handler" + e.getMessage());
        }
    }

    /**
     *Writes the content for log input.
     * @param content - content in main body.
     */
    public static void writeContentLog(String content) {

        try {
            log_bf.write("CONTENT:\r\n");
            if (content.contains(".html") || content.contains(".txt")) { //writes entire body of to log only if html or txt file.
                File fin = new File(content);
                FileInputStream fis = new FileInputStream(fin);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                String line = null;
                    while ((line = in.readLine()) != null) { //loop iterates through line of file and writes to log.txt.
                        log_bf.write(line);
                        log_bf.write("\r\n");
                    }
            }
            else if (content.contains(".png") || content.contains(".gif") || content.contains(".jpg")) {
                log_bf.write("N/A - image file."); //does not write content for image file as txt cannot handle images.
            }
            else { //if content is an error message such as 404 or 501 so has no file ending.
                log_bf.write(content);
            }
        }
        catch (Exception e) {
            System.out.println("LOG Exception" + e);
            System.out.println("LOG Exception" + e.getMessage());
        }
    }

    /**
     *Writes the head of the log for requests including: request type, filepath & head.
     * @param requesttype - GET | HEAD | DELETE
     * @param filepath - from command line input.
     * @param content - HEAD content from ConnectionHandler.HEADHandler
     */
    public static void writeHeaderLog(String requesttype, String filepath, String content) {

        try {
            log_bf.write("REQUEST TYPE:" + requesttype + "\r\n");
            log_bf.write("FILEPATH:" + filepath + "\r\n");
            log_bf.write("HEAD:\r\n");
            log_bf.write(content);
        }
        catch (Exception e) {
            System.out.println("Exception" + e.getMessage());
        }
    }

    /**
     * closes file writer and buffered writer threads. Used by ConnectionHandler cleanup.
     */
    public static void cleanup() {

        try {
            log_bf.close();
        }
        catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
        }
    }
}