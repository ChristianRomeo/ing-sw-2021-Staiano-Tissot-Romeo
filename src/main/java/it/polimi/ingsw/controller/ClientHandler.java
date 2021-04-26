package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.Events.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

/**
 *  Uno per giocatore , legge i messaggi, setta il nickname, setta numero giocatori IF FIRST, manda il ping ogni 2 secondi, manda il giocatore alla virtualview
 */
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

    public ClientHandler(boolean isFirstPlayer, Socket socket, VirtualView virtualView) throws IOException {
        this.isFirstPlayer = isFirstPlayer;
        this.socket = socket;
        this.virtualView = virtualView;
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());
        this.isConnected = true;
        socket.setSoTimeout(30000); // Sets the connection timeout to 30 seconds
        //(new PingSender(this, true)).start(); //dobbiamo mandare un ping al client ogni 2 secondi per vedere se è connesso ancora dato che è tcp
        this.nickname=null;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    private void connectionSetUp() {

        //SEND setupgame dove c'è execute di asknickname, if(newgame) asknumplayer
        //il client dopo aver inserito il nick e avviato la connessione al server resta in attesa per l'esito e per l'eventuale inserimento di numero giocatori
        //manda il nickname (così se viene modificato lo sa) al client e se è first player chiede il numero di giocatori
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
        } catch (IOException ignored) {}
    }

     public void send(ServerEvent message) {
        if (isConnected) {
            try {
                synchronized (lock) {
                    output.writeUnshared(message);
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
    }

    @Override
    public void run() {
        //connectionSetUp();
        try {
            int idx=0;
            String nick = (String) input.readObject();  //forse va in setupconnection e non qui nel run
            String tempNick = nick;
            synchronized (virtualView){     //va bene sincronizzare la vv?
                for (ClientHandler cl : virtualView.getClientHandlers())
                    while (cl.getNickname().equalsIgnoreCase(nick))
                        nick = tempNick + "_" + idx++;

                setNickname(nick);
                virtualView.addClientHandler(this);
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.warning("errore nel leggere il nickname");
        }

        if(isFirstPlayer){
            try {
               // output.writeObject();     //INSERIRE NUMERO GIOCATORI
                int playersNum = (Integer) input.readObject();
                synchronized (virtualView.getController().getGame()){   //faccio la notify quando è stato impostato il numero di giocatori così che il server possa riprendere l'esecuzione
                    virtualView.getController().getGame().setWantedNumPlayers(playersNum);
                    virtualView.getController().getGame().notifyAll();
                }
            }catch (Exception e){
                logger.warning("errore nel leggere il numero giocatori");
            }
        }

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
