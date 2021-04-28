package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.Events.ClientEvent;
import it.polimi.ingsw.controller.Events.ServerEvent;
import it.polimi.ingsw.model.LeaderCard;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

/**
 * it's the executor client-side of methods and callings, one for each client
 */
public class ServerHandler {
    private final Object lock = new Object();
    private ObjectInputStream input;    //quello che arriva dal server
    private ObjectOutputStream output;  //quello che va verso il server
    private Socket socket;              //la socket del client
    private View view;                  //reference to the corresponding view
    private String nickname;            //player nick
    private boolean isConnected = false;
    private final static Logger logger = Logger.getLogger(Server.class.getName());

    /**
     * Starts listening for server messages and execute them client-side
     */
    public void listener() {
        while (isConnected)
            try {
                ServerEvent serverMessage = (ServerEvent) input.readObject();       //corretto?

                    //when server tells to to disconnect
                    if ( /*ShowDisconnection &&*/ isConnected) {    //todo: evento showdisconnection
                        // Under control disconnection
                        isConnected = false;
                        closeConnection();
                    }
                    else {
                        //if messaggio diverso
                        //(CVMessage)serverMessage.execute(view);   //metodo per eseguire comandi inviati sul client
                    }
            } catch (ClassNotFoundException | IOException e) {
                if (isConnected)
                    view.showErrorMessage("Server unreachable" + (Configs.isServerAlive() ? " during reading" : "") + ".");
                //do things
                isConnected = false;
            }
    }

    /**
     * Setter of the view
     * @param view  the client view
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Getter of the nickname
     * @return the player nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets the connection
     */
    public void setUpConnection() {
        try {
            socket = new Socket(Configs.getServerIp(), Configs.getServerPort());
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            isConnected = true;
            // Sets the connection timeout to 20 seconds and start sending pings to the server every 3 seconds
            socket.setSoTimeout(20000);
            //starts pinging
            (new Thread(() -> {
                while(true)
                    try {
                        logger.warning("Pinging...");
                        if (!socket.getInetAddress().isReachable(3000)){
                            logger.warning(" s'è disconnesso il server");
                            sendNewGame(true);  //schermata home
                        }
                        logger.warning("tt'appost");
                    } catch (IOException ignored) {}
            })).start();

        } catch (IOException e) {
            view.showErrorMessage("Server unreachable" + (Configs.isServerAlive() ? " during setup" : "") + ".");
        }
        listener();
    }

    /**
     * Graceful closes the connection to the server
     */
    public void closeConnection() {
        try {
            socket.close();
            isConnected = false;
        } catch (IOException e) {
            view.showErrorMessage("An error occurred " + (Configs.isServerAlive() ? "  when closing the connection" : "") + ".");
            logger.info(""+e);
        }
    }

    /**
     * Connection status of the client
     *
     * @return True if it's connected, false otherwise
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * Sends a message
     *
     * @param message The message to be sent
     */
    public void send(ClientEvent message) {
        if (isConnected) {
            try {
                synchronized (lock) {
                    output.writeUnshared(message);
                    output.flush();
                    output.reset();
                }
            } catch (IOException e) {
                isConnected = false;
                view.showErrorMessage("Server unreachable" + (Configs.isServerAlive() ? " during sending" : "") + ".");
            }
        }
    }

    /**
     * Sends to the server the nickname (and player number se vogliamo fa così)
     *
     * @param nickname   The chosen username
     * @param numPlayers The number of players of the game to be created (irrelevant if the player is joining an existing game)
     */
    public void sendSetUpPlayer(String nickname, int numPlayers) {
        //send(new SetUpGame(nickname, numPlayers));
        this.nickname = nickname;
    }

    /**
     * Sends to the server the leaderCards chosen
     *
     * @param chosenCards The chosen cards
     */
    public void sendLeaderCards(List<LeaderCard> chosenCards) {
        //send(new SetUpGameCards(chosenCards));
    }

    /**
     * Response to the server Turn request
     *
     * @param theAction The chosen action
     */
    public void sendAction(ClientEvent theAction) {
        //send(new Turn(theAction, nickname));
    }

    /**
     * New game or shutdown
     *
     * @param choice The choice of the user
     */
    public void sendNewGame(boolean choice) throws FileNotFoundException {
        isConnected = false;
        closeConnection();
        if (choice)
            Client.main(null);
    }


// ### SERVER TO CLIENT ####    forse non va qui
    /**
     * Update the nickname of the client
     *
     * @param newNickname The message received
     */
    private void execute(ServerEvent newNickname) {
        //this.nickname= newNickname.getNickname;
    }
}
