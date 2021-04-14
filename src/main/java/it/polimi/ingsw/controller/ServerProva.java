package it.polimi.ingsw.controller;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ServerProva {
    //private static final int DEFAULTPORT = 1234;            //da file config
    //private static final String HOSTNAME = "127.0.0.1";   //da file config

    //private final static Logger logger = Logger.getLogger(it.polimi.ingsw.controller.Server.class.getName());

    public static void main(String[] args) {
        ServerSocket server = null;
        ExecutorService executor = Executors.newCachedThreadPool();
        try {

            // server is listening on port 1234
            server = new ServerSocket(9876);
            //server.setReuseAddress(true);

            // running infinite loop for getting
            // client request
            while (true) {

                // socket object to receive incoming client
                // requests
                Socket client = server.accept();

                // Displaying that new client is connected
                // to server
                System.out.println("New client connected" + client.getInetAddress().getHostAddress());

                // create a new thread object
                executor.submit(new ClientHandlerProva(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}