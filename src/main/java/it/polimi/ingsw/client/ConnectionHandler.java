package it.polimi.ingsw.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/**
 * Class ConnectionHandler handles messages from server to client and viceversa
 */
public class ConnectionHandler implements Runnable{
    private ObjectInputStream input;    //quello che arriva dal server
    private ObjectOutputStream output;  //quello che va verso il server
    private Socket socket;              //la socket del client

    @Override
    public void run() {

    }
}
