package MSP_Project;

import MSP_Project.MSPService;

import java.net.*;
import java.io.*;
import java.util.Date;
import java.net.ServerSocket;
import java.net.Socket;

public class ConcurrentMSPServer {
    private ServerSocket serverSocket;
    public static final int PORT = 1717;

    public ConcurrentMSPServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(1717);
        ConcurrentMSPServer server = new ConcurrentMSPServer(serverSocket);

        int portNumber = 0;

        if (args.length == 0) {
            System.err.println("Usage: java EchoServer <port number>");
            portNumber = PORT;
        } else {
            portNumber = Integer.parseInt(args[0]);
        }

        try {
            System.out.println("Esperando a cliente...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Bienvenido");
                MSPClient mspClient = new MSPClient(socket);
                Thread sesiones = new Thread(mspClient);
                sesiones.start();
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}