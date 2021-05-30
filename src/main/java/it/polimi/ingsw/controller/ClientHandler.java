package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.Events.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Logger;

/**
 *  Uno per giocatore , rappresenta il client lato server, legge i messaggi ricevuti dal client, setta il nickname,
 *  setta numero giocatori IF FIRST, manda il ping ogni 2 secondi, manda il giocatore alla virtualView che lo aggiunge alla lista giocatori ed al game
 */
public class ClientHandler implements Runnable {
    private final Socket socket;                //univoca per ogni player, se si disconnette la perde?
    private final VirtualView virtualView;      //una per game
    private String nickname;                    //identifica il giocatore univocamente, per poi essere riconnesso?
    private final ObjectOutputStream output;    //invia messaggi al client
    private final ObjectInputStream input;      //riceve messaggi dal client
    private final boolean isFirstPlayer;        //clue per fargli scegliere le carte
    private final Object lock = new Object();
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
        socket.setSoTimeout(60000); // cosi il tizio deve mandare il num di giocatori entro 60 secondi
        //starts pinging tcp client
        /*
        (new Thread(() -> {
            while(true)
                try {
                    logger.warning("Pinging...");
                    if (!socket.getInetAddress().isReachable(3000)){
                        logger.warning("s'è disconnesso il client");
                        setDisconnected();
                    }
                    logger.warning("tt'appost");
                } catch (IOException ignored) {}
        })).start();
        */
        //(new PingSender(this, true)).start();
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

        //SEND setupgame dove c'è execute di asknickname, if(newgame) asknumplayer
        //il client dopo aver inserito il nick e avviato la connessione al server resta in attesa per l'esito e per l'eventuale inserimento di numero giocatori
        //manda il nickname (così se viene modificato lo sa) al client e se è first player chiede il numero di giocatori
        //send(new SetUpGame(isFirstPlayer, nickname)); //Sends the initial connectionSetUp message to the client

        try {
            int idx=0;
            NewConnectionEvent newConnectionEvent = (NewConnectionEvent) input.readObject();
            String nick = newConnectionEvent.getNickname(); //forse sta parte si può mettere nella virtual view per coerenza
            //System.out.println("nick ricevuto"); // debug

            String tempNick = nick;
            synchronized (virtualView){     //ha senso sincronizzare la vv?
                for (ClientHandler cl : virtualView.getClientHandlers())
                    while (cl.getNickname().equalsIgnoreCase(nick) || cl.getNickname().equalsIgnoreCase("Lorenzo il Magnifico"))
                        nick = tempNick + "_" + ++idx;
                setNickname(nick);
                //System.out.println("nick impostato"); // debug

                //if (virtualView.getDisconnectedClients().stream().anyMatch(x-> x.equalsIgnoreCase(nickname))){
                    //reconnect if disconnected? //ho commentato sta parte perchè dava problemi
              //  }else {
                virtualView.addClientHandler(this);

             //   }
            }

        } catch (IOException | ClassNotFoundException e) {
            logger.warning("errore nel leggere il nickname");
            setDisconnected();
        }

        if(isFirstPlayer) {
            try {

                NewConnectionEventS2C newConnectionEventS2C = new NewConnectionEventS2C(nickname,true);
                send(newConnectionEventS2C);

                synchronized (virtualView.getController().getGame()) {   //faccio la notify quando è stato impostato il numero di giocatori così che il server possa riprendere l'esecuzione
                    NumPlayerEvent numPlayerEvent = (NumPlayerEvent) input.readObject();
                    numPlayerEvent.notifyHandler(virtualView);
                    virtualView.getController().getGame().notifyAll();
                }
            } catch (Exception e) {
                logger.warning("errore nel leggere il numero giocatori");
                setDisconnected();
            }

        }else{
            NewConnectionEventS2C newConnectionEventS2C = new NewConnectionEventS2C(nickname,false);
            send(newConnectionEventS2C);
        }
        System.out.println("fine connection setup"); // debug
        startPing();
        try {
            socket.setSoTimeout(8000);
        } catch (SocketException e) {
            e.printStackTrace();
            setDisconnected();
        }
    }

    /**
     * getter status of the client
     * @return check status
     */
    public boolean isConnected() {
        return isConnected;
    }

    public void setDisconnected() {
        this.isConnected = false;
        virtualView.setDisconnected(this);
        closeSocket();
    }

    /**
     * Closes the player socket for graceful disconnection
     */
    public void closeSocket() {
        try {
            socket.close();
        } catch (IOException ignored) {}
    }

    /**
     * Sends to the client a message if he's available
     * @param message what to be sent to the player
     */
     public synchronized void send(ServerEvent message) {

         if (isConnected) //todo: se quando non è più connesso si chiude tutto forse, quindi questo if che serve?
            try {
                synchronized (lock) { //lock??
                    output.writeUnshared(message);
                    output.flush();
                    output.reset();
                }
            } catch (IOException e) {
                if (isConnected) {  // This player has disconnected
                    logger.warning(nickname + " has disconnected in send");
                    isConnected = false;
                    virtualView.setDisconnected(this);
                } else  // Another player has disconnected
                    logger.warning(nickname + " was disconnected due to shutdown in send");
               closeSocket();
            }
    }

    /**
     * Checks if game can start and then listen to input messages
     */
    @Override
    public void run() {

        connectionSetUp();

        //Controlla rispetto al game se è stato inizializzato il numero di giocatori e allora il game può iniziare perchè al completo
        synchronized (virtualView.getController().getGame()){ //synch su game???
            if(!virtualView.getController().isPreGameStarted()&&virtualView.getController().getGame().getWantedNumPlayers()==virtualView.getController().getGame().getPlayersNumber())
                try {
                    virtualView.getController().gameStarter();
                } catch (FileNotFoundException e) {
                    logger.warning("file di carte config non trovato");
                }
        }

        //finchè è connesso allora ascolta lo stream input
        while (isConnected)
            try {
                ClientEvent clientEvent = (ClientEvent) input.readObject();
                //viene chiamata la virtualView che gestirà l'evento chiamando il controller
                if(!(clientEvent instanceof PingEventC2S)){
                    synchronized (virtualView){
                        if(nickname.equals(virtualView.getController().getGame().getCurrentPlayer().getNickname())){
                            clientEvent.notifyHandler(virtualView); //notifica la view solo se è il current player, forse sarà da cambiare
                        }
                    }
                }
            }
            catch (IOException | ClassNotFoundException e) {
                if (isConnected) {  // This player has disconnected
                    logger.warning(nickname + " has disconnected in receive");
                    isConnected = false;
                    virtualView.setDisconnected(this);
                } else  // Another player has disconnected
                    logger.warning(nickname + " was disconnected due to shutdown in receive");

                closeSocket();
            }
    }

    public void startPing(){
        (new Thread(() -> {
            while(isConnected){
                try {
                    Thread.sleep(5000);
                    send(new PingEventS2C());
                    logger.info("ping inviato");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        })).start();
    }
}
