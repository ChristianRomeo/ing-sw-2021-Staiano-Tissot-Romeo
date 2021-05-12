package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.ClientModel;
import it.polimi.ingsw.client.ConnectionHandler;
import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.Player;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public interface View {

    /**
     * Sets the serverHandler
     *
     * @param connectionHandler The handler of the server connection
     */
    void setConnectionHandler(ConnectionHandler connectionHandler);

    /**
     * Interface launcher. set connection CAN AGGREGATE
     */
    void launcher() throws FileNotFoundException;

    //getter del client model
    ClientModel getClientModel();

    //chide il numero di giocatori voluto
    int askNumPlayer();

    /**
     * Asks nickname and if it's a new game the number of players for the game and notify the information to the serverHandler
     *
     * @param newGame True if the it is a new game, otherwise false
     */
    void setUpGame(boolean newGame);

    /**
     * Asks the game cards
     * @return the set of chosen cards
     */
    TreeSet<Integer> askLeaderCards() throws FileNotFoundException;

    /**
     * Asks the index of a leader card
     */
    int askLeaderCard();

    /**
     * Shows the developmentCardBoard matrix
     *
     * @param cards All the remaining cards
     */
    void showDevelopmentCards(List<DevelopmentCard> cards) throws FileNotFoundException;

    /**
     * Shows the Players in the game
     *
     * @param playerList All the players
     */
    void showPlayersBoard(List<Player> playerList);

    /**
     * Show the activated leaderCards of others players
     *
     * @param playerList LeaderCards activated
     */
    void showPlayersLeaderCards(List<Player> playerList);

    /**
     * Shows the faith track
     *
     * @param trackInfo The track's player position ([0] first, [1] second...)
     */
    void showFaithTrack(List<Integer> trackInfo);

    /**
     * Asks what the player want to do
     *
     * @param roundActions The possible actions    ([0]market, [1] prod, [2]warehouse)
     */
    void askAction(List<Integer> roundActions);

    /**
     * Shows the LadderBoard of the match
     *
     */
    void showLadderBoard() throws FileNotFoundException;

    /**
     * shows a message to the user.
     * @param message showed
     */
    void showMessage(String message,boolean cls);

}
