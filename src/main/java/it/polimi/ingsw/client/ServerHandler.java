package it.polimi.ingsw.client;


import it.polimi.ingsw.controller.Configs;
import it.polimi.ingsw.controller.Events.*;
import it.polimi.ingsw.controller.View;
import java.io.FileNotFoundException;
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
    private final static Logger logger = Logger.getLogger(ServerHandler.class.getName());


    public ServerHandler(View view){
        this.view=view;
        serverEventHandler = new EventsHandler(view.getClientModel(),view);
    }

    @Override
    public void run() { // qua vengono ricevuti i messaggi da server e mandati a chi li gestisce
        while (isConnected) {
            try {
                ServerEvent serverMessage = (ServerEvent) input.readObject();
                //qua viene passato l'evento all'handler di eventi che farà le dovute cose
                if(serverMessage instanceof PingEventS2C){
                    send(new PingEventC2S());
                    //System.out.println("ping ricevuto e inviato");
                }else{
                    serverMessage.notifyHandler(serverEventHandler);
                }

                if(serverMessage instanceof EndGameEventS2C){
                    break;
                }


            } catch (ClassNotFoundException | IOException e) {
                if (isConnected)
                    //CliView.showMessage("Server unreachable" + (Configs.isServerAlive() ? " during reading" : "") + ".",true);    //during sending if server is alive
                    //do things
                    isConnected = false;
                    System.out.println("Disconnesso server");
                    break;
            }
        }
        closeConnection();
    }

    public void setUpConnection(){
        try{
            socket = new Socket(Configs.getServerIp(), Configs.getServerPort());    //metodo del server nel client
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            isConnected=true;
        }catch (Exception e){
            view.showErrorMessage("errore"+e); // da fare meglio
        }

        //faccio set nel model nel launcher della cli, ora lo mando al server e poi lo riprendo e lo risetto
        send(new NewConnectionEvent(view.getClientModel().getMyNickname())); // invio evento con nickname

        try {
            //ricevo risposta dal server
            NewConnectionEventS2C serverAnswer = (NewConnectionEventS2C) input.readObject();
            view.getClientModel().setMyNickname(serverAnswer.getNickname()); //si imposta il nick ricevuto

            if(serverAnswer.isFirstPlayer()){
                //qui si chiede il numero di giocatori voluto all'utente
                int wantedNumPlayers = view.askNumPlayer();
                send(new NumPlayerEvent(wantedNumPlayers));
                if (wantedNumPlayers!=1)
                    view.showMessage("Now please wait for others players...");
            }
        } catch (IOException | ClassNotFoundException e) {
            view.showErrorMessage("errore"+e); // da fare meglio
        }

        //quindi arrivato qua il client si è connesso con il server,ha inviato il suo nickname e
        //eventualmente il numero di giocatori
        //ora attivo la ricezione di messaggi da server
        (new Thread(this)).start();

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
        } catch (IOException e) {
            logger.warning(""+e);
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
                //synchronized (lock) { //??
                    output.writeUnshared(message);
                    output.flush();
                    output.reset();
                //}
            } catch (IOException e) {
                isConnected = false;
                //view.showMessage("Server unreachable" + (Configs.isServerAlive() ? " during sending" : "") + ".",false);  //during sending if server is alive
            }
        }
    }
}
