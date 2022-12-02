package MSP_Project;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
public class MSPClient implements Runnable{

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public MSPClient(Socket socket) throws IOException {
        this.socket = socket;
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1717);
        MSPClient client = new MSPClient(socket);
    }


    @Override
    public void run() {
        try {

            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

            Scanner sc = new Scanner(System.in);
            String userInput;
            String usuario,mensaje, destinatario;

            while (true) {
                System.out.print("MOVE> ");
                userInput = teclado.readLine();

                ArrayList<Character> usuarios = new ArrayList<>();
                ArrayList<String> users = new ArrayList<>();
                ArrayList<Character> mensajes = new ArrayList<>();
                ArrayList<Character> destinatarios = new ArrayList<>();


                if (userInput.trim().contains("CONNECT ")) {

                    for(int i = 8; i < userInput.length(); i++){
                        usuarios.add(userInput.charAt(i));
                    }
                    usuario = usuarios.stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining());
                    System.out.println("Hola de nuevo, " + usuario);
                    users.add(usuario);


                } else if (userInput.trim().contains("SEND #") && userInput.trim().contains("@")) {

                    for(int i = 6; i < userInput.length(); i++){
                        mensajes.add(userInput.charAt(i));
                    }
                    mensaje = mensajes.stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining());

                    String[] destinatarioArray = userInput.split("@");
                    destinatario = destinatarioArray[1];

                    String[] mensajeArray = mensaje.split("@");
                    mensaje = mensajeArray[0];



                    System.out.println("Su mensaje hacia " + destinatario + ": " + mensaje);


                } else if (userInput.trim().equals("LIST")) {
                    int i=1;
                    System.out.println("Lista de usuarios activos:");
                    for(String item:users){
                        i = i+1;
                        System.out.println(i+".- " + item);
                    }
                } else  if (userInput.trim().equals("DISCONNECT")) {
                    break;
                } else {
                    System.out.print("Ingrese un comando válido: \nCONNECT + usuario (para conectarse) \nLIST (usuarios conectados) \nSEND #mensaje@destinatario (enviar un mensaje) \nDISCONNECT (para desconectar)\n");
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Eroor con localhost");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to localhost");
            System.exit(1);
        }
    }
}