package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.Configs;
import it.polimi.ingsw.controller.Events.ServerEvent;
import it.polimi.ingsw.controller.Events.ServerEventObserver;

import java.io.IOException;
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
    private boolean isConnected=false;
    private ServerEventObserver serverEventHandler = new ServerEventObserverImpl();//l'inizializzazione può essere spostata da un'altra parte

    @Override
    public void run() { // qua vengono ricevuti i messaggi da server e mandati a chi li gestisce
        while (isConnected) {
            try {
                ServerEvent serverMessage = (ServerEvent) input.readObject();
                //qua viene passato l'evento all'handler di eventi che farà le dovute cose
                serverMessage.notifyHandler(serverEventHandler);


            } catch (ClassNotFoundException | IOException e) {
                if (isConnected)
                    //view.showErrorMessage("Server unreachable" + (Configs.isServerAlive() ? " during reading" : "") + ".");
                    //do things
                    isConnected = false;
            }
        }
    }

    public void setUpConnection(){
        try{
            socket = new Socket(Configs.getServerIp(), Configs.getServerPort());
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            isConnected=true;
        }catch (Exception e){
            System.out.println("errore"); // da fare meglio
        }

        //qui si chiede nickname a giocatore e lo imposta (o posso chiederlo anche da un'altra parte,
        //magari prima di cominciare la connessione e attivare la socket, è meglio)
        String nickname = "";

        //bisogna finire di definire la procedura di connessione iniziale anche lato server
        //per vedere se tipo si manda un evento se sei il primo ecc

        //comunque qui dovrebbe starci la ricezione di un evento da server che ci dice sei il primo o no
        // se non lo sei allora invii tipo un evento newConnection con solo il tuo nickname
        // se sei il primo allora qua parte un metodo che chiede  a utente il numero di giocatori
        // e poi si invia al server evento newConnection con nickname e num giocatori al server

        //quindi arrivato qua il client si è connesso con il server,ha inviato il suo nickname e
        //eventualmente il numero di giocatori

        //ora attivo la ricezione di messaggi da server
        (new Thread(this)).start();


    }
}
