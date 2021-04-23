package it.polimi.ingsw.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final VirtualView virtualView;
    private String nickname;
    private final ObjectOutputStream output;
    private final ObjectInputStream input;
    private final boolean isFirstPlayer;
    private final Object lock = new Object();
    private boolean isConnected;
    private final static Logger logger = Logger.getLogger(Server.class.getName());


    /**
     *
     */
    public ClientHandler(boolean isFirstPlayer, Socket socket, VirtualView virtualView) throws IOException {
        this.isFirstPlayer = isFirstPlayer;
        this.socket = socket;
        this.virtualView = virtualView;
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());
        this.isConnected = true;
        socket.setSoTimeout(30000); //// Sets the connection timeout to 30 seconds
        //(new PingSender(this, true)).start(); //dobbiamo mandare un ping al client ogni 5 secondi per vedere se è connesso ancora dato che è tcp
        this.nickname=null;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    private void connectionSetUp() {
        //send(new SetUpGame(isFirstPlayer, nickname)); //Sends the initial connectionSetUp message to the client
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setDisconnected() {
        this.isConnected = false;
    }

    public void closeSocket() {
        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }

     /*public void send(Message message) {
        if (isConnected) {
            try {
                synchronized (lock) {
                    //output.writeUnshared(message);
                    output.flush();
                    output.reset();
                }
            } catch (IOException e) {
                if (isConnected) {
                    // This player has disconnected
                    logger.warning(nickname + " has disconnected during message sending");
                    isConnected = false;
                    virtualView.setDisconnected(nickname);
                } else {
                    // Another player has disconnected
                    logger.warning(nickname + " was forced to quit during message sending");
                }
               closeSocket();
            }
        }
    }*/

    /**
     * When an object implementing interface {@code Runnable} is used to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing thread.The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     * @see Thread#run()
     */
    @Override
    public void run() {

        if(isFirstPlayer){
            try {
                int playersNum = (Integer) input.readObject();
            }catch (Exception e){
                //
            }
        }

        connectionSetUp();
        while (isConnected) {
            try {

                //receive messages by input.readObject
                ClientEvent clientEvent = (ClientEvent) input.readObject();
                //viene chiamata la virtualview che gestirà l'evento chiamando il controller
                clientEvent.notifyHandler(virtualView);

                }
            catch (IOException | ClassNotFoundException e) {
                if (isConnected) {
                    // This player has disconnected
                    logger.warning(nickname + " has been disconnected during message receiving");
                    isConnected = false;
                    virtualView.setDisconnected(nickname);
                } else {
                    // Another player has disconnected
                    logger.warning(nickname + " was forced to quit during message receiving");
                }
                closeSocket();
            }
        }
    }
}
