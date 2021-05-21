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
    void launcher() throws FileNotFoundException;

    //getter del client model
    ClientModel getClientModel();

    //chide il numero di giocatori voluto
    int askNumPlayer();

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
     * Shows the faith track
     *
     */
    void showFaithTrack();

    /**
     * Shows the LadderBoard of the match
     *
     */
    void showLadderBoard(EndGameEventS2C endGameEvent);

    /**
     * shows a message to the user.
     * @param message showed
     */
    void showMessage(String message,boolean cls);

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
     * Notify all that a player has been disconnected (and the game has ended ?FA)
     * @param disconnected The nickname of the disconnected player
     */
    void showDisconnectionMessage(String disconnected);

    /**
     * Notify whose turn is
     * @param currentNickname The nickname of whom taking the turn
     */
    void showTurn(String currentNickname);

    /**
     * Notify that the game has ended and the winning status
     * @param winner The nickname of the winner
     * @param youWon True if the player has win
     */
    void showEndGameMessage(String winner, boolean youWon);
}
