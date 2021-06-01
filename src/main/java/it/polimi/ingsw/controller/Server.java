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
    private Game currentGame;
    private VirtualView currentVirtualView;
    private int addedPlayers;
    private final static Logger logger = Logger.getLogger(Server.class.getName());

    /**
     * Constructor: builds a Server
     */
    public Server() {
        this.currentGame = null;
        this.currentVirtualView = null;
        this.addedPlayers = 0;
    }

    public static void main(Configs in) throws IOException {
        Server server = new Server();
        server.launch(in);
    }

    /**
     *
     * @see {$link} https://stackoverflow.com/questions/4684727/java-serversocket-why-is-the-ip-address-0-0-0-0-yet-i-can-still-connect-remote/4684806
     */
    public void launch(Configs in) {   //eccezione per il file di configurazione della porta e ip

        try {
            serverSocket = new ServerSocket(Configs.getServerPort(in));     //ascolto, una porta un server per tutti i clienti
        } catch (IOException e) {
            logger.severe("Fatal error: Could not start the server.\n Conifg file isn't compatible, Cannot open server on port " + Configs.getServerPort(in) +"\nFalling back to port 9876..."); //server catch
            try {
                serverSocket = new ServerSocket(9876);
            } catch (IOException ignored) {}

        }

        logger.info("Server started successfully on port "+ serverSocket.getLocalSocketAddress());

        while(true){
            try {
                initClient();
            } catch (IOException | InterruptedException | DisconnectionException e) {
                logger.warning("Connection Error: Could not accept the connection." + e);   //client catch
                if(serverSocket != null && !serverSocket.isClosed())
                    try {
                        serverSocket.close();   //giusto?
                    } catch (IOException ignored) {}
            }
        }

    }

    public void initClient() throws IOException, InterruptedException, DisconnectionException {

        Socket socket = serverSocket.accept();  //accetto un singolo cliente ogni volta
        logger.info( socket.getRemoteSocketAddress() + " has connected. This is: " +socket.getLocalSocketAddress());

        // Creates a game if it doesn't exist and add the client to it
        if (currentGame == null) {
            initGame();
            new Thread(new ClientHandler(true, socket, currentVirtualView)).start();       //se era già connesso che succ?
        } else
            new Thread(new ClientHandler(false, socket, currentVirtualView)).start();


        //Sleep until the number of players of the game has been set by the first player (wakeupserver in controller)
        synchronized (currentGame) {
            while (currentGame.getWantedNumPlayers() == 0 && currentGame.isActive())
                currentGame.wait();
        }

        //only then we can check whether the number chosen has been reached
        if (++addedPlayers == currentGame.getWantedNumPlayers() || !currentGame.isActive()) //todo: synch?? forse non serve
            //currentVirtualView.getController().gameStarter();   //it's here but maybe shouldn't, servono più istanze per partite multiple?
            //ho spostato il game starter nel client handler per possibili problemi threads
            clearLobby();

    }

    /**
     * Clears the game room preparing it to welcome new users   PARTITE MULTIPLE
     */
    private void clearLobby() {
        logger.info("Status: Game room is full.");
        currentGame = null;
        currentVirtualView = null;
        addedPlayers = 0;
    }

    /**
     * Creates and initializes a new game instance
     */
    private void initGame() throws IOException {
        logger.info("Status: New game has been created.");

        // Setup of the server side game management
        currentGame = new Game();
        Controller controller = new Controller(currentGame);
        currentVirtualView = new VirtualView(controller);
        controller.setVirtualView(currentVirtualView);

    }

}
