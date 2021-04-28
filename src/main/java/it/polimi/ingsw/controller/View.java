package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.Player;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public interface View {

    /**
     * Sets the serverHandler
     *
     * @param serverHandler The serverHandler
     */
    void setServerHandler(ServerHandler serverHandler);

    /**
     * Interface launcher. set connection CAN AGGREGATE
     */
    void launch() throws FileNotFoundException;

    /**
     * Shows a message to the user
     *
     * @param message   The message to be shown
     * @param newScreen True want to clean the console
     */
    void showMessage(String message, boolean newScreen);

    /**
     * Shows a waiting message to the user
     */
    void showWaiting();

    /**
     * Asks nickname and if it's a new game the number of players for the game and notify the information to the serverHandler
     *
     * @param newGame True if the it is a new game, otherwise false
     */
    void setUpGame(boolean newGame);

    /**
     * Asks the game cards
     */
    void askLeaderCards() throws FileNotFoundException;

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
     * Show the activated leadercards of others players
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
     * Notify that the game has ended and the winning status
     *
     * @param winner The nickname of the winner
     * @param youWon True if the player has win
     */
    void showEndGameMessage(String winner, boolean youWon);

    /**
     * Shows the LadderBoard of the match
     *
     * @param scores a map with players scores
     */
    void showLadderBoard(Map<Player, Integer> scores) throws FileNotFoundException;

    /**
     * Notify all that a player has been disconnected (and the game has ended ?FA)
     *
     * @param disconnected The nickname of the disconnected player
     */
    void showDisconnectionMessage(String disconnected) throws FileNotFoundException;

    /**
     * Notify whose turn is
     *
     * @param currentNickname The nickname of whom taking the turn
     */
    void showTurn(String currentNickname);

    /**
     * Shows an error message
     *
     * @param errorMessage The message to be shown
     */
    void showErrorMessage(String errorMessage);

}
