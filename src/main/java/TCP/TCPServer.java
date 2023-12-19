package TCP;

import ContactDiscovery.ContactList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServer extends Thread{

    private ServerSocket serverSocket;
    public static int TCP_Server_Port=6666;


    public interface Observer{
        void messageReceived(TCPMessage msg);
    }
    ArrayList<TCPServer.Observer> observers;

    TCPServer(){
        this.observers = new ArrayList<Observer>();
    }

    public void run() {
        try {

            serverSocket = new ServerSocket(TCP_Server_Port);
            while (true){
                try{
                    new EchoClientHandler(serverSocket.accept()).start();
                }catch (IOException e){
                    System.out.println("Server Accept exception: " + e);
                }
            }

        }catch (IOException e){
            System.out.println("new Server Socket exception: "+e);
        }


    }

    public synchronized void addObserver(TCPServer.Observer obs){
        this.observers.add(obs);
    }


    private class EchoClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public EchoClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try{
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

            }catch(IOException e){
                System.out.println("Init Input & Output stream error");
            }

            try{
                String inputLine;;
                while ((inputLine = in.readLine()) != null) {

                    //Notify the observers that the server received a message
                    for(TCPServer.Observer obs:observers){
                        obs.messageReceived(new TCPMessage(inputLine));
                    }

                    if (".".equals(inputLine)) {
                        out.println("bye");
                        break;
                    }
                    out.println(inputLine);

                }
            }catch(IOException e){
                System.out.println("error Input readLine");
            }


            try{
                in.close();
                out.close();
                clientSocket.close();
            }catch(IOException e){
                System.out.println("closing Input stream error OR clientSocket closing error");
            }

        }
    }

}