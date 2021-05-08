package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.ConnectionHandler;
import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.Player;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class GuiView {// implements View{ //ho tolto l'implements view momentaneamente per comodità

    /**
     * Sets the serverHandler
     *
     * @param connectionHandler The serverHandler
     */
    public void setConnectionHandler(ConnectionHandler connectionHandler) {

    }

    /**
     * Interface launcher. set connection CAN AGGREGATE
     */
    public void launch() {

    }

    /**
     * Shows a message to the user
     *
     * @param message   The message to be shown
     * @param newScreen True want to clean the console
     */
    public void showMessage(String message, boolean newScreen) {

    }

    /**
     * Shows a waiting message to the user
     */
    public void showWaiting() {

    }

    /**
     * Asks nickname and if it's a new game the number of players for the game and notify the information to the serverHandler
     *
     * @param newGame True if the it is a new game, otherwise false
     */
    public void setUpGame(boolean newGame) {

    }

    /**
     * Asks the game cards
     */
    public void askLeaderCards() {

    }

    /**
     * Shows the developmentCardBoard matrix
     *
     * @param cards All the remaining cards
     */
    public void showDevelopmentCards(List<DevelopmentCard> cards) {

    }

    /**
     * Shows the Players in the game
     *
     * @param playerList All the players
     */
    public void showPlayersBoard(List<Player> playerList) {

    }

    /**
     * Show the activated leadercards of others players
     *
     * @param playerList LeaderCards activated
     */
    public void showPlayersLeaderCards(List<Player> playerList) {

    }

    /**
     * Shows the faith track
     *
     * @param trackInfo The track's player position ([0] first, [1] second...)
     */
    public void showFaithTrack(List<Integer> trackInfo) {

    }

    /**
     * Asks what the player want to do
     *
     * @param roundActions The possible actions    ([0]market, [1] prod, [2]warehouse)
     */
    public void askAction(List<Integer> roundActions) {

    }

    /**
     * Notify that the game has ended and the winning status
     *
     * @param winner The nickname of the winner
     * @param youWon True if the player has win
     */
    public void showEndGameMessage(String winner, boolean youWon) {

    }

    /**
     * Shows the LadderBoard of the match
     *
     * @param scores a map with players scores
     */
    public void showLadderBoard(Map<Player, Integer> scores) {

    }

    /**
     * Notify all that a player has been disconnected (and the game has ended ?FA)
     *
     * @param disconnected The nickname of the disconnected player
     */
    public void showDisconnectionMessage(String disconnected) {

    }

    /**
     * Notify whose turn is
     *
     * @param currentNickname The nickname of whom taking the turn
     */
    public void showTurn(String currentNickname) {

    }

    /**
     * Shows an error message
     *
     * @param errorMessage The message to be shown
     */
    public void showErrorMessage(String errorMessage) {

    }
}
