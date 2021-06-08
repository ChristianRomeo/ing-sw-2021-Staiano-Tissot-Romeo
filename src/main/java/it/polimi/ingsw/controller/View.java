package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.ClientModel;
import it.polimi.ingsw.client.ServerHandler;

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

    //getter del client model
    ClientModel getClientModel();

    //chide il numero di giocatori voluto
    int askNumPlayers();

    /**
     * chiede se vuole fare una nuova partita
     */
    void askNewGame();

    /**
     * Notify all that a player has been disconnected (and the game has ended ?FA)
     * @param disconnected The nickname of the disconnected player
     */
    void showDisconnectionMessage(String disconnected);


}
