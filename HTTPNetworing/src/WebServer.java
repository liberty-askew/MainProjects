import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class WebServer {

    private ServerSocket ss;
    public static int port;
    private static String filePath;
    private static ExecutorService executor; //fixed number thread pool. Maximum number of threads for multithreading set to 10.
    private static final int MAXTHREAD = 10; //magic number for maximum number of threads in pool.

    public WebServer(int port, String filePath) {

        this.port = port;
        this.filePath = filePath;
        executor = Executors.newFixedThreadPool(MAXTHREAD); //sets maximum number of threads to 10.
        try {
            ss = new ServerSocket(port);
            System.out.println("Server started ... listening on port " + port + " ..."); //terminal status output.
            while (true) {
                Socket conn = ss.accept();
                System.out.println("Server got new connection request from " + conn.getInetAddress()); //terminal status output.
                executor.execute(new ConnectionHandler(conn, filePath)); //using ExecutorService class initializes ConnectionHander.run.
                                                                        //ensures synchronization of thread handling.
            }
        }
        catch (IOException ioe) {
            System.out.println("Ooops " + ioe.getMessage());
        }
        catch (Exception e) { //catches all other exceptions cleanly.
            System.out.println("Exception:" + e.getMessage());
        }
    }
}
