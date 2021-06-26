package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.controllerExceptions.DisconnectionException;
import it.polimi.ingsw.model.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 *  One single server instance that accepts all the clients
 */
public class Server {

    private static ServerSocket serverSocket;
    private int currentNumOfPlayers;
    private Game currentGame;
    private VirtualView currentVirtualView;
    private final static Logger logger = Logger.getLogger(Server.class.getName());

    /**
     * Constructor
     * It initializes the Server.
     */
    public Server() {
        this.currentNumOfPlayers = 0;
        this.currentGame = null;
        this.currentVirtualView = null;
    }

    public static void main(Configs in) {
        Server server = new Server();
        server.start(in);
    }

    // https://stackoverflow.com/questions/4684727/java-serversocket-why-is-the-ip-address-0-0-0-0-yet-i-can-still-connect-remote/4684806
    public void start(Configs in) {   //eccezione per il file di configurazione della porta e ip

        try {
            serverSocket = new ServerSocket(Configs.getServerPort(in));     //ascolto, una porta un server per tutti i clienti
        } catch (IOException e) {
            logger.severe("An error has occurred while trying to start the server!\n Config file isn't compatible, Cannot open server on port " + Configs.getServerPort(in) +"\nFalling back to port 9876..."); //server catch
            try {
                serverSocket = new ServerSocket(9876);
            } catch (IOException ignored) {}

        }

        logger.info("The Server has been successfully started on port "+ serverSocket.getLocalSocketAddress());

        //noinspection InfiniteLoopStatement
        while(true)
            try {
                startClient();
            } catch (IOException | InterruptedException | DisconnectionException e) {
                logger.warning("An error has occurred while trying to connect! The connection could not be accepted." + e);   //client catch
                if(serverSocket != null && !serverSocket.isClosed())
                    try {
                        serverSocket.close();
                    } catch (IOException ignored) {}
            }

    }

    /**
     * Accepts one client per time.
     * After that, it creates and initializes a new game instance
     * (creates a game if it doesn't exist and adds the client to it).
     * After that, it prepares the server side handling of the game.
     * Following, it sleeps until the number of players of the game has been set by the first player.
     * Only then, it checks whether the number chosen by the first player has been reached.
     *
     */
    public void startClient() throws IOException, InterruptedException, DisconnectionException {

        Socket socket = serverSocket.accept();  //accetto un singolo cliente ogni volta
        logger.info( socket.getRemoteSocketAddress() + " has connected. This is: " +socket.getLocalSocketAddress());
        /* Now it creates and initializes a new game instance
           (creates a game if it doesn't exist and adds the client to it)
         */
        if (currentGame == null) {
            logger.info("A new game has been created.");
            // Setup of the server side game management
            currentGame = new Game();
            Controller controller = new Controller(currentGame);
            currentVirtualView = new VirtualView(controller);
            controller.setVirtualView(currentVirtualView);
            new Thread(new ClientHandler(true, socket, currentVirtualView)).start();
        } else
            new Thread(new ClientHandler(false, socket, currentVirtualView)).start();


        //Sleep until the number of players of the game has been set by the first player (wakeupserver in controller)
        //noinspection SynchronizeOnNonFinalField
        synchronized (currentGame) {
            while (currentGame.getWantedNumPlayers() == 0 && currentGame.isActive())
                currentGame.wait();
        }

        //only then we can check whether the number chosen has been reached
        if (++currentNumOfPlayers == currentGame.getWantedNumPlayers() || !currentGame.isActive()) {
            //MULTIPLE GAMES FA to clear the game room and prepare it in order to accept new players
            logger.info("The game room is full.");
            currentNumOfPlayers = 0;
            currentGame = null;
            currentVirtualView = null;
        }
    }

}
