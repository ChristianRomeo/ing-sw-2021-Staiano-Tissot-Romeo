package it.polimi.ingsw.controller;

import com.google.gson.JsonIOException;
import it.polimi.ingsw.controller.Events.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Logger;

/**
 *  a ClientHandler for every player. It represents the client from the server-side, reading received messages from the client, setting nickname, number of players for the match,
 *  sends the player to the virtual view which will add him to the list of players and to the game
 */
public class ClientHandler implements Runnable {
    private final Socket socket;                //univoca per ogni player, se si disconnette la perde?
    private final VirtualView virtualView;      //una per game
    private String nickname;                    //identifica il giocatore univocamente, per poi essere riconnesso?
    private final ObjectOutputStream output;    //invia messaggi al client
    private final ObjectInputStream input;      //riceve messaggi dal client
    private final boolean isFirstPlayer;        //clue per fargli scegliere le carte
    private boolean isConnected;                //modificato nel ping e basta?
    private final static Logger logger = Logger.getLogger(Server.class.getName());

    /**
     * Constructor initializes stuff and starts pinging
     * @param isFirstPlayer tells if the player has to choose cards
     * @param socket    the client socket connected to the server
     * @param virtualView   orchestrator of the game
     * @throws IOException  socket exception
     */
    public ClientHandler(boolean isFirstPlayer, Socket socket, VirtualView virtualView) throws IOException {
        this.isFirstPlayer = isFirstPlayer;
        this.socket = socket;
        this.virtualView = virtualView;
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());
        this.isConnected = true;
        socket.setSoTimeout(60000);                                                                                     // cosi il tizio deve mandare il num di giocatori entro 60 secondi
    }

    /**
     * Getter of the user's nickname
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * private Setter of the nickname
     * @param nickname  nick of the user
     */
    private void setNickname(String nickname) {
        this.nickname = nickname;
    }


    private void connectionSetUp() {

        //SEND setupgame dove c'è execute di asknickname, if(newgame) asknumplayers
        //il client dopo aver inserito il nick e avviato la connessione al server resta in attesa per l'esito e per l'eventuale inserimento di numero giocatori
        //manda il nickname (così se viene modificato lo sa) al client e se è first player chiede il numero di giocatori
        //send(new SetUpGame(isFirstPlayer, nickname)); //Sends the initial connectionSetUp message to the client

        try {
            int idx=0;
            NewConnectionEvent newConnectionEvent = (NewConnectionEvent) input.readObject();

            String nick = newConnectionEvent.getNickname();                                                             //forse sta parte si può mettere nella virtual view per coerenza
            String tempNick = nick;
            synchronized (virtualView){
                for (ClientHandler cl : virtualView.getClientHandlers())
                    while (cl.getNickname().equalsIgnoreCase(nick) || cl.getNickname().equalsIgnoreCase("Lorenzo il Magnifico"))
                        nick = tempNick + "_" + ++idx;
                setNickname(nick);

                //if (virtualView.getDisconnectedClients().stream().anyMatch(x-> x.equalsIgnoreCase(nickname))){
                    //reconnect if disconnected? //ho commentato sta parte perchè dava problemi
              //  }else {
                virtualView.addClientHandler(this);

             //   }
            }

        } catch (IOException | ClassNotFoundException e) {
            logger.warning("error while reading the nickname");
            setDisconnected();
        }

        if(isFirstPlayer) {
            try {
                NewConnectionEventS2C newConnectionEventS2C = new NewConnectionEventS2C(nickname,true);
                send(newConnectionEventS2C);

                synchronized (virtualView.getController().getGame()) {
                    NumPlayerEvent numPlayerEvent = (NumPlayerEvent) input.readObject();
                    numPlayerEvent.notifyHandler(virtualView);
                    virtualView.getController().getGame().notifyAll();                                                  //faccio la notify quando è stato impostato il numero di giocatori così che il server possa riprendere l'esecuzione
                }
            } catch (Exception e) {
                logger.warning("error while reading the number of players: the first player has disconnected");

                synchronized (virtualView.getController().getGame()){
                    virtualView.getController().getGame().notifyAll();
                    virtualView.getController().getGame().setInactive();
                }
                setDisconnected();
            }
        }else{
            NewConnectionEventS2C newConnectionEventS2C = new NewConnectionEventS2C(nickname,false);
            send(newConnectionEventS2C);
        }

        if (isConnected()){
            logger.info("ends of clientHandler-connectionSetUp, starting ping..."); // debug
            startPing();
            try {
                socket.setSoTimeout(8000);
            } catch (SocketException e) {
                logger.info("ping not received");
                setDisconnected();
            }
        }                                                                                           //start ping

    }

    /**
     * getter status of the client
     * @return check status
     */
    public synchronized boolean isConnected() {
        return isConnected;
    }

    private synchronized void setIsConnected(boolean isConnected){
        this.isConnected = isConnected;
    }

    public void setDisconnected() {

            virtualView.getController().getGame().setInactive();
            virtualView.closeAll();

    }

    /**
     * Closes the player socket for graceful disconnection
     */
    public void closeSocket() {
        try {
            input.close();
            output.close();
            this.socket.close();
            setIsConnected(false);
        } catch (IOException ignored) {}
    }

    /**
     * Sends to the client a message if he's available
     * @param message what to be sent to the player
     */
     public synchronized void send(ServerEvent message) {

         if (isConnected())
            try {
                    output.writeUnshared(message);
                    output.flush();
                    output.reset();

            } catch (IOException e) {
                logger.warning(nickname + " has disconnected in send");
                setDisconnected();
            }

    }

    /**
     * Checks if game can start and then listens to input messages
     */
    @Override
    public void run() {

        connectionSetUp();

        //Controlla rispetto al game se è stato inizializzato il numero di giocatori e allora il game può iniziare perchè al completo
        synchronized (virtualView.getController().getGame()){
            if(!virtualView.getController().isPreGameStarted() && virtualView.getController().getGame().getWantedNumPlayers() == virtualView.getController().getGame().getPlayersNumber())
                try {
                    virtualView.getController().gameStarter();
                } catch (JsonIOException ignored) {}                                                               //IT SHOULD NOT HAPPEN IN JAR

        }

        while (isConnected)
            try {
                ClientEvent clientEvent = (ClientEvent) input.readObject();

                if(!(clientEvent instanceof PingEventC2S))
                    synchronized (virtualView){
                        if(nickname.equals(virtualView.getController().getGame().getCurrentPlayer().getNickname()))
                            clientEvent.notifyHandler(virtualView); //notifica la view solo se è il current player, forse sarà da cambiare, viene chiamata la virtualView che gestirà l'evento chiamando il controller
                    }
            }
            catch (IOException | ClassNotFoundException e) {
                logger.warning(nickname + " has disconnected in receive");
                setDisconnected();
            }

    }

    /**
     * starts pinging its Client (every ClientHandler pings its own Client)
     */
    @SuppressWarnings("BusyWait")
    public void startPing(){
        (new Thread(() -> {
            while(isConnected()){
                try {
                    Thread.sleep(5000);
                    send(new PingEventS2C());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        })).start();
    }
}
