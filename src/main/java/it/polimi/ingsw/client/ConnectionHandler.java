package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.Configs;
import it.polimi.ingsw.controller.Events.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/**
 * Class ConnectionHandler handles messages from server to client and viceversa (serverHandler)
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
        String nickname = "provaNickname1";

        send(new NewConnectionEvent(nickname)); // invio evento con nickname
        System.out.println("nick inviato"); // debug

        try {
            //ricevo risposta dal server
            NewConnectionEventS2C serverAnswer = (NewConnectionEventS2C) input.readObject();

            System.out.println("risposta ricevuta"); // debug
    //BISOGNA METTERE EVENTI SERIALIZABLE
            //serverAnswer.getNickname() e si imposta il nickname ricevuto da qualche parte (nel clientModel)

            if(serverAnswer.isFirstPlayer()){

                //qui si chiede il numero di giocatori voluto all'utente
                int wantedNumPlayers=2; //per esempio

                send(new NumPlayerEvent(wantedNumPlayers));

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); //errore
        }



        //quindi arrivato qua il client si è connesso con il server,ha inviato il suo nickname e
        //eventualmente il numero di giocatori



        //ora attivo la ricezione di messaggi da server
        (new Thread(this)).start();


    }

    /**
     * Sends a message to the server
     *
     * @param message The message to be sent
     */
    public void send(ClientEvent message) {
        if (isConnected) {
            try {
                //synchronized (lock) {
                    output.writeUnshared(message);
                    output.flush();
                    output.reset();
              //  }
            } catch (IOException e) {
                isConnected = false;
                //view.showErrorMessage("Server unreachable" + (Configs.isServerAlive() ? " during sending" : "") + ".");
            }
        }
    }
}
