package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.ClientModel;
import it.polimi.ingsw.client.ServerHandler;

/**
 * interface from where CliView and GuiView are implemented
 */
public interface View {

    /**
     * Sets the serverHandler
     *
     * @param serverHandler The handler of the server connection
     */
    void setConnectionHandler(ServerHandler serverHandler);

    /**
     * Interface launcher. set connection CAN AGGREGATE
     */
    void launcher(); //throws FileNotFoundException; provo senza sta eccez

    /**
     * @return the whole Client Model
     */
    ClientModel getClientModel();

    /**
     * asks for wanted number of players
     * @return the wanted num of players
     */
    int askNumPlayers();

    /**
     * asks for a new game to start
     */
    void askNewGame();

    /**
     * Notify all that a player has been disconnected (and the game has ended ?FA)
     * @param disconnected The nickname of the disconnected player
     */
    void showDisconnectionMessage(String disconnected);


}
