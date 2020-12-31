import java.io.*;
import java.net.Socket;
import java.nio.file.*;

public class ConnectionHandler implements Runnable {

    /**
     * Handles individual connections to WebServer. Sorts appropriate output of files / text for various command line
     * inputs.
     */
    private Socket conn; // socket representing TCP/IP connection to Client
    private InputStream is; // get data from client on this input stream
    private OutputStream os; // can send data back to the client on this output stream
    private BufferedReader br; // use buffered reader to read client data
    private LogHandler lh; //aggregation; log for connection handler.
    private String filepath;

    /**
     * Initializes new socket connection.
     * @param conn - induvidual socket connection.
     * @param fp - filepath from WebServer e.g ../www
     */
    public ConnectionHandler(Socket conn, String fp) {

        this.conn = conn;
        try {
            is = conn.getInputStream(); // get data from client on this input stream
            os = conn.getOutputStream(); // to send data back to the client on this stream
            br = new BufferedReader(new InputStreamReader(is)); // use buffered reader to read data
            lh = new LogHandler(); //opens up log ready to write.
            filepath = fp;
        } catch (IOException ioe) {
            System.out.println("ConnectionHandler: " + ioe.getMessage());
        }
    }

    /**
     *Implemented by EXECUTOR.execute( new ConnectionHandler(conn,filepath)) in WebServer to execute one thread by run.
     */
    public void run() {

        try {
            handleRequest();
        } catch (Exception e) {
            System.out.println("ConnectionHandler:run " + e.getMessage());
            cleanup();
        }
    }

    /**
     * Sorts request types GET | HEAD | DELETE to method handlers with appropriate inputs. Also returns Not Implemented
     * and Not Found errors.
     */
    public void handleRequest() {

        System.out.println("new ConnectionHandler constructed .... ");
        try {
            String line = br.readLine();
            String requesttype = line.split(" ")[0];
            String filename = line.split(" ")[1];
            try {
                byte[] filebytes = Files.readAllBytes(Paths.get(filepath + filename));
                File fileobj = new File(filepath + filename);
                if (requesttype.equals("DELETE")) {
                    fileobj.delete();
                }
                switch (requesttype) { //sorts the 3 available request types: GET | HEAD | DELETE
                    case "GET" :
                        os.write(GETHandler(filepath, "HTTP/1.1 200 OK\r\n", requesttype, filename));
                        os.flush();
                        break;
                    case "HEAD" :
                        os.write(HEADHandler(filebytes, "HTTP/1.1 200 OK\r\n", requesttype, filename));
                        os.flush();
                        break;
                    case "DELETE" :
                        os.write(GETHandler("<html><body><h1>File Deleted</h1></body></html>\r\n", "HTTP/1.1 200 OK\r\n", requesttype, filename));
                        os.flush();
                        break;
                    default :  //request passes to here if not one of the 3 specified above that the program can deal with.
                        os.write(HEADHandler(filebytes, "HTTP/1.1 501 Not Implemented\r\n", requesttype, filename));
                        System.out.println("ERROR: 501 Not Implemented"); //terminal print out for status update.
                        os.flush();
                }
                cleanup();
            } catch (NoSuchFileException n) { // catches file not found exception
                os.write(GETHandler("<html><body><h1>Content Not Found</h1></body></html>", "HTTP/1.1 404 Not Found\r\n", requesttype, filename));
                System.out.println("ERROR: 404 Not found"); //terminal print out for status update.
                cleanup();
            }
        } catch (Exception e) { // exit cleanly for any exception
            System.out.println("Handler Exception" + e.getMessage());
            cleanup();
        }
    }


    /**
     * Handles GET requests, also used for 404 error with content. Uses HEADHandler for the header.
     * @param input - path to file or html string. e.g ../www or <html><body><h1>File Deleted</h1></body></html>\
     * @param header - HTTP/1.1 200 OK\r\n or HTTP/1.1 404 Not Found\r\n
     * @param requesttype - GET | DELETE
     * @param filename - e.g page3.html Cannot be inherited for attribute because needs to be passed through for 404 error.
     * @return ByteArray to be written on output stream.
     */
    public byte[] GETHandler(String input, String header, String requesttype, String filename) {

        ByteArrayOutputStream outStream = null;
        try {
            byte[] inbytes = null;
            //sorts request depending if it is for a file or for a String.
            if (!input.contains("<h1>")) {
                input += filename; //adds filepath to filename e.g. ../www/index.html
                inbytes =  Files.readAllBytes(Paths.get(input));
            }
            else {
                inbytes = input.getBytes(); //converts string to bytes to be written.
            }
            outStream = new ByteArrayOutputStream();
            outStream.write(HEADHandler(inbytes, header, requesttype, filename)); //calls HEADHandler for header of output.
            outStream.write(inbytes);
            lh.writeContentLog(input); //updates log.

        } catch (Exception e) {
            System.out.println(" GET Exception:" + e.getMessage());
        }
        return outStream.toByteArray();
    }

    /**
     * Handles HEAD for HEAD requests and 501 error. Also called to write HEAD for GETHandler.
     * @param content - content for main body. Used for length.
     * @param header - e.g "HTTP/1.1 200 OK\r\n"
     * @param requesttype - HEAD | DELETE | INVALID
     * @param filename - name of file in command line request.
     * @return ByteArray to be written on output stream.
     */
    public byte[] HEADHandler(byte[] content, String header, String requesttype, String filename) {

        ByteArrayOutputStream outStream = null;
        String output = ""; //build head string on output.
        try {
            output += header;
            if (filename.contains(".txt") || filename.contains(".html")) { //sorts content type depending on filename.
                output += "Content-Type: text/html\r\n";
            } else if (filename.contains(".jpg")||filename.contains(".png")||filename.contains(".gif")) {
                if(filename.contains(".jpg")) {
                    output += "Content-Type: image/jpg\r\n";
                }
                else if(filename.contains(".png")) {
                    output += "Content-Type: image/png\r\n";
                }
                else{
                    output += "Content-Type: image/gif\r\n";
                }
            } else { //if non of the above then is not a valid file type. Will have 404 not found error.
                output += "Content-Type: invalid\r\n";
            }
            output += "Content-Length: " + content.length + "\r\n\r\n";
            lh.writeHeaderLog(requesttype, filepath + filename, output); //updates log for header.
            outStream = new ByteArrayOutputStream();
            outStream.write(output.getBytes());
        } catch (Exception e) {
            System.out.println("HEAD Exception:" + e.getMessage());
        }
        return outStream.toByteArray();
    }

    /**
     * cleans up and exits by closing all open streams, including the log handler. Prints out error message if this fails.
     */
    private void cleanup() {

        System.out.println("ConnectionHandler: ... cleaning up and exiting socket... ");
        try {
            lh.cleanup(); //closes log handler.
            br.close();
            is.close();
            conn.close();
        } catch (IOException ioe) {
            System.out.println("ConnectionHandler:cleanup " + ioe.getMessage());
        }
    }
}
