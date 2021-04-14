package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 *
 */
public class Server {
    private static final int DEFAULTPORT = 9838;            //da file config
    //private static final String HOSTNAME = "127.0.0.1";   //da file config

    private static ServerSocket serverSocket;
    private Game currentGame;
    private VirtualView currentVirtualView;
    private int addedPlayers;
    private final static Logger logger = Logger.getLogger(Server.class.getName());
    private final ExecutorService executor;

    /**
     * Constructor: build a Server
     */
    public Server() {
        this.executor = Executors.newCachedThreadPool();
        this.currentGame = null;
        this.currentVirtualView = null;
        this.addedPlayers = 0;
    }

    public static void main() {
        Server server = new Server();

        try{
            server.launch();
        }catch (IOException e){
            logger.warning("Fatal error: Could not start the server. Cannot open server on port " + DEFAULTPORT); //server catch
             //close server
        }
    }

    public void launch() throws IOException {   //eccezione per il file di configurazione della porta e ip
        serverSocket = new ServerSocket(DEFAULTPORT);   //ascolto, una porta un server per tutti i clienti
        logger.info("Server started successfully");

        while(true){
            try {
                initClient();
            } catch (IOException | InterruptedException e) {
                logger.warning("Connection Error: Could not accept the connection.");   //client catch
                e.printStackTrace();
                if(serverSocket != null && !serverSocket.isClosed()) {
                    try {
                        serverSocket.close();   //giusto?
                        //executor.shutdown();    //qui?
                    } catch (IOException ignored) {
                    }
                }
            }
        }
    }

    public void initClient() throws IOException, InterruptedException {
        //ClientHandler clientHandler;

        Socket socket = serverSocket.accept();  //accetto un singolo cliente ogni volta
        logger.info( socket.getRemoteSocketAddress() + " has connected.");

        // Creates a game if it doesn't exist and add the client to it
        if (currentGame == null) {
            initGame();
            executor.submit(new ClientHandler(true, socket, currentVirtualView));
            //clientHandler = new ClientHandler(currentVirtualView, socket, true);
        } else {
            executor.submit(new ClientHandler(false, socket, currentVirtualView));
            //clientHandler = new ClientHandler(currentVirtualView, socket, false);
        }
        //currentVirtualView.addClientHandler(clientHandler);   //crea effettivamente diversi thread o no??
        //clientHandler.start();    //thread
        //executor.submit(clientHandler);   //runnables
        ++addedPlayers;

        // Sleep until the number of players of the game has been set   //vedere
        synchronized (currentGame) {
            while (currentGame.getPlayersNumber() == -1 && currentGame.isActive()) {
                currentGame.wait();
            }
        }
        if (addedPlayers == currentGame.getPlayersNumber() || !currentGame.isActive()) {
            clearGameRoom();
        }
    }

    /**
     * Clears the game room preparing it to welcome new users   PARTITE MULTIPLE
     */
    private void clearGameRoom() {
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

        //executor.submit(controller.gameStarter());
        //OR
        // Starts the game controller
        (new Thread(() -> {
            try {
                controller.gameStarter();
            } catch (Exception ignored) {
            }
            logger.warning("Status: Controller has stopped.");
        })).start();
    }
    //executor.shutdown();  //dentro alla funzione dove si usa
}
