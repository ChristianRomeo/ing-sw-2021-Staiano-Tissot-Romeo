package it.polimi.ingsw.client;


import it.polimi.ingsw.controller.Configs;
import it.polimi.ingsw.controller.Events.*;
import it.polimi.ingsw.controller.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
    private final Object lock= new Object();
    private final static Logger logger = Logger.getLogger(ServerHandler.class.getName());


    public ServerHandler(View view, Configs in){
        this.view=view;
        serverEventHandler = new EventsHandler(view.getClientModel(),view);
        this.in=in;
    }

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
                    System.out.println("Disconnesso server");
                closeConnection();
            }

        //closeConnection();
    }

    public void setUpConnection(){
        try{

            socket = new Socket(Configs.getServerIp(in), Configs.getServerPort(in));    //metodo del server nel client

            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            isConnected=true;
        }catch (Exception e){
            view.showErrorMessage("error during setUpConnection "+e); // da fare meglio
        }

        //faccio set nel model nel launcher della cli, ora lo mando al server e poi lo riprendo e lo risetto
        send(new NewConnectionEvent(view.getClientModel().getMyNickname())); // invio evento con nickname

        try {
            NewConnectionEventS2C serverAnswer = (NewConnectionEventS2C) input.readObject();            //ricevo risposta dal server
            view.getClientModel().setMyNickname(serverAnswer.getNickname());                            //si imposta il nick ricevuto

            if(serverAnswer.isFirstPlayer()){
                int wantedNumPlayers = view.askNumPlayer();                                             //qui si chiede il numero di giocatori voluto all'utente
                send(new NumPlayerEvent(wantedNumPlayers));
                if (wantedNumPlayers!=1)
                    view.showMessage("Now please wait for others players...");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Disconnesso server "+e);
            closeConnection();
        }

        //quindi arrivato qua il client si è connesso con il server,ha inviato il suo nickname e
        //eventualmente il numero di giocatori

        (new Thread(this)).start();     //ora attivo la ricezione di messaggi da server
    }


    /**
     * Graceful closes the connection to the server
     */
    public void closeConnection() {
        try {
            input.close();
            output.close();
            socket.close();
            isConnected=false;
            view.askNewGame();
        } catch (IOException e) {
            logger.warning("errore in chiusura connessione"+e);
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
                synchronized (lock) { //per ping
                    output.writeUnshared(message);
                    output.flush();
                    output.reset();
                }
            } catch (IOException e) {
                System.out.println("errore in lettura, la partita si è chiusa, per rientrare, usare lo stesso username assegnato");
               closeConnection();
            }
        }
    }
}
