package MSP_Project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MSPService implements Runnable {

    private Socket clientSocket;

    protected BufferedReader in = null;
    protected boolean moreQuotes = true;

    public MSPService(Socket c, BufferedReader i) {
        clientSocket = c;
        in = i;

    }


    @Override
    public void run() {
        PrintWriter out  = null;
        try {
            out  = new PrintWriter( clientSocket.getOutputStream(), true);
            // Flujo de entrada (leer datos que envia el cliente)
            BufferedReader in = new BufferedReader(
                    new InputStreamReader( clientSocket.getInputStream() ));

            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(MSP_Project.MSPService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }
    }

    protected String getNextQuote() {
        String returnValue = null;
        try {
            if ((returnValue = in.readLine()) == null) {
                in.close();
                moreQuotes = false;
                returnValue = "No more quotes. Goodbye.";
            }
        } catch (IOException e) {
            returnValue = "IOException occurred in server.";
        }
        return returnValue;
    }

}