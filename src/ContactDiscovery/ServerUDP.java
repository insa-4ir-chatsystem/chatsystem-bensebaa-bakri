package ContactDiscovery;
import java.util.ArrayList;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class ServerUDP {

    static  final int Broadcast_Port=6666;

    public void listenOtherUsers() throws SocketException{
        //different scénario ?
        //demande d'envoyer son pseudo
        //Updater la contactList

        EchoServer server = new EchoServer();
        server.start();
    }


/************** A SUPPRIMER ********************/
//    public void updateContactList(String pseudo,String ip) {
//        //On ajoute le otherUser qui s'est connecté (message recu est le broadcast associé à la connexion réussie)
//        String elt = pseudo+"/"+ip;
//        ContactList.getInstance().
//    }

    public static void main(String[] args) throws IOException {
        EchoServer server = new EchoServer();
        server.start();
    }


    private static class EchoServer extends Thread {

        private DatagramSocket socket;
        private boolean running;
        private byte[] buf = new byte[256];

        public EchoServer() throws  SocketException{
            try{
                //TEST TO REMOVE LATER
                User.getInstance().nickname="test";
            }catch (UnknownHostException e){
                System.out.println("unknown host");
            }
            socket = new DatagramSocket(Broadcast_Port);
        }

        public void run() {
            running = true;

            while (running) {
                DatagramPacket packet
                        = new DatagramPacket(buf, buf.length);

                try{
                    socket.receive(packet);
                    System.out.println("after receiving");
                }catch (IOException e){
                    System.out.println("receive method error");

                }

                //make sure not to receive its own broadcast
                try{
                    if(!packet.getAddress().equals(InetAddress.getLocalHost())){
                        String received
                                = new String(packet.getData(), 0, packet.getLength());

                        if(received.equals("Hello")){
                            System.out.println("broadcast received");

                            //sends pseudo after receiving broadcast
                            InetAddress address = packet.getAddress();
                            int port = packet.getPort();
                            String pseudo = User.getInstance().nickname;
                            packet = new DatagramPacket(pseudo.getBytes(), pseudo.length(), address, port);
                            System.out.println(new String(packet.getData(), StandardCharsets.UTF_8));

                            try{
                                System.out.println("sending pseudo");
                                socket.send(packet);
                            }catch (IOException e){
                                System.out.println("send method error");
                            }

                        }else{
                            //Not a broadcast ==> Save new pseudo user
                            String elt=received+"/"+packet.getAddress().toString();
                            System.out.println(elt);
                            ContactList.getInstance().addLine(elt);

                        }


                        if (received.equals("end")) {
                            running = false;
                        }
                    }else System.out.println("received same address");


                }catch (UnknownHostException u){
                    System.out.println("Unknown host exception when getLocalHost invoked");
                }



            }
            socket.close();
        }
    }
}