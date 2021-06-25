package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.Configs;
import it.polimi.ingsw.controller.Events.*;
import it.polimi.ingsw.controller.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Class ServerHandler handles messages from server to client and viceversa (serverHandler)
 */
public class ServerHandler implements Runnable{
    private ObjectInputStream input;    //quello che arriva dal server
    private ObjectOutputStream output;  //quello che va verso il server
    private Socket socket;              //la socket del client
    private boolean isConnected=false;
    private final ServerEventObserver serverEventHandler;
    private final View view;
    private final Configs in;
    private final static Logger logger = Logger.getLogger(ServerHandler.class.getName());

    public ServerHandler(View view, Configs in){
        this.view=view;
        serverEventHandler = new EventsHandler(view.getClientModel(),view);
        this.in=in;
    }

    /**
     *receuves messages from the server and sends it to their handlers
     * (which is gonna be the event handler, who will handle them)
     */
    @Override
    public void run() { // qua vengono ricevuti i messaggi da server e mandati a chi li gestisce

        while (isConnected)
            try {
                ServerEvent serverMessage = (ServerEvent) input.readObject();

                if(serverMessage instanceof PingEventS2C)
                    send(new PingEventC2S());
                else
                    serverMessage.notifyHandler(serverEventHandler); //qua viene passato l'evento all'handler di eventi che farà le dovute cose

                if(serverMessage instanceof EndGameEventS2C)
                    break;

            } catch (ClassNotFoundException | IOException e) {
                if (isConnected)
                    logger.warning("Server down, closing");
                    //System.out.println("Server down, closing");
                closeConnection();
                view.askNewGame();
            }

        closeConnection();
    }

    public void setUpConnection(){
        try{
            socket = new Socket(Configs.getServerIp(in), Configs.getServerPort(in));    //metodo del server nel client
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            isConnected=true;
        }catch (IOException e){
            logger.warning("error during setUpConnection (cause: server down or config file)\nTrying to fall back to 127.0.0.1:9876 ...");
            //view.showErrorMessage("error during setUpConnection (cause: server down or config file)\nTrying to fall back to 127.0.0.1:9876 ...");
            if (socket==null)
                try {
                    socket = new Socket("127.0.0.1", 9876);
                    output = new ObjectOutputStream(socket.getOutputStream());
                    input = new ObjectInputStream(socket.getInputStream());
                    isConnected=true;
                }catch (IOException l) {
                    closeConnection();
                    view.askNewGame();
                }

        }

        if (isConnected){
           //faccio set nel model nel launcher della cli, ora lo mando al server e poi lo riprendo e lo risetto
            send(new NewConnectionEvent(view.getClientModel().getMyNickname())); // invio evento con nickname

            try {
                NewConnectionEventS2C serverAnswer = (NewConnectionEventS2C) input.readObject();            //ricevo risposta dal server
                String old= view.getClientModel().getMyNickname();
                view.getClientModel().setMyNickname(serverAnswer.getNickname());                            //si imposta il nick ricevuto

                if (!Objects.equals(view.getClientModel().getMyNickname(), old))
                    logger.info("Your new username is " + view.getClientModel().getMyNickname());
                    //view.showMessage(Color.color('g',"Your new username is " + view.getClientModel().getMyNickname()));

                if(serverAnswer.isFirstPlayer()){
                    int wantedNumPlayers = view.askNumPlayers();                                             //qui si chiede il numero di giocatori voluto all'utente
                    send(new NumPlayerEvent(wantedNumPlayers));
                    if (wantedNumPlayers!=1)
                        logger.info("Now please wait for others players...");
                        //view.showMessage("Now please wait for others players...");
                }
            } catch (IOException | ClassNotFoundException e) {
                logger.warning("Server down during setup reading "+e);
                closeConnection();
                view.askNewGame();
            }

            //quindi arrivato qua il client si è connesso con il server,ha inviato il suo nickname e
            //eventualmente il numero di giocatori

            (new Thread(this)).start();     //ora attivo la ricezione di messaggi da server
        }
    }


    /**
     * Graceful closes the connection to the server
     */
    public void closeConnection() {
        try {
            if (socket != null){
                socket.close();
                isConnected=false;
                logger.info("server disconnected");
            }else
                logger.info("server unreachable");

            //view.askNewGame();
        } catch (IOException e) {
            logger.warning("errore in chiusura connessione " + e);                                                 //IT SHOULD NOT HAPPEN
        }
    }


    /**
     * Sends a message to the server
     *
     * @param message The message to be sent
     */
    public synchronized void send(ClientEvent message) {
        if (isConnected) {
            try {
                    output.writeUnshared(message);
                    output.flush();
                    output.reset();
            } catch (IOException e) {
                logger.warning("error in sending");
                closeConnection();
                view.askNewGame();
            }
        }
    }
}
