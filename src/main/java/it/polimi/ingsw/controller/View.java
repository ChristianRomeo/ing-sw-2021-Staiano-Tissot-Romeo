package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.ClientModel;
import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.controller.Events.EndGameEventS2C;
import it.polimi.ingsw.model.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

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

    /*
     * Asks nickname and if it's a new game the number of players for the game and notify the information to the serverHandler
     *
     * @param newGame True if the it is a new game, otherwise false
     */
    //void setUpGame(boolean newGame);

    /**
     * Asks the game cards
     * @return the set of chosen cards
     */
    SameTypePair<Integer> askChoiceLeaderCards() throws FileNotFoundException;

    /**
     * Asks the index of a leader card
     */
    List<Integer> askLeaderCard();

    /**
     * Shows the developmentCardBoard matrix
     *
     */
    void showDevelopmentCardBoard() throws FileNotFoundException;

    /**
     * Shows the Players in the game
     *
     */
    void showPlayersBoard();

    /**
     * Show the activated leaderCards of others players
     *
     */
    void showPlayersLeaderCards();

    /**
     * Shows the faith track of the match
     *
     */
    void showFaithTracks();


    /**
     * Shows a player's overall state(Warehouse, Strongbox, CardBoard, Faith Track position)
     *
     */
    void showMyState();

    /**
     * Shows all players overall state(Warehouse, Strongbox, CardBoard, Faith Track position)
     * except for the player who called it
     */
    void showOthersState();

    /**
     * Shows the LadderBoard of the match
     *
     */
    void showLadderBoard(EndGameEventS2C endGameEvent);

    /**
     * shows a message to the user.
     * @param message showed
     */
    void showMessage(String message);

    /**
     * Shows an error message
     * @param errorMessage The message to be shown
     */
    void showErrorMessage(String errorMessage);

    void showLorenzoTurn(SoloAction soloAction);

    /**
     * Asks the player what action should engage
     */
    void askActions();

    /**
     * asks the nickname
     * @return the nickname
     */
    String askNickname();

    /**
     * This method asks the user a position of a development card in the development card board
     * @return the position (row,col)
     */
    SameTypePair<Integer> askDevelopmentCard();

    /**
     * asks in what pile of production should the bought card be inserted
     * @return the pile number
     */
    int askCardPile();

    /**
     * Shows the market
     */
    void showMarket();

    /**
     * shows a player LeaderCard
     * @param card to show
     */
    void showLeaderCard(LeaderCard card);

    /**
     * shows a player card
     * @param card to show
     */
    void showCard(DevelopmentCard card);

    /**
     * shows a player warehouse
     * @param warehouse to show
     */
    void showWarehouse(PlayerWarehouse warehouse);

    /**
     * shows a player Strongbox
     * @param strongbox to show
     */
    void showStrongbox(Map<Resource,Integer> strongbox);

    /**
     * Shows a player Faith Track
     * @param faithTrackPosition is the player's faith track position
     * @param showLorenzoFT is used to check if the method has been called by "showMyState" method or not
     */
    void showFaithTrack(int faithTrackPosition, boolean showLorenzoFT);

    /**
     * Notify all that a player has been disconnected (and the game has ended ?FA)
     * @param disconnected The nickname of the disconnected player
     */
    void showDisconnectionMessage(String disconnected);

    /**
     * Notify whose turn is
     * @param currentNickname The nickname of whom taking the turn
     */
    void showTurn(String currentNickname);


}
