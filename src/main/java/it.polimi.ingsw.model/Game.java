package it.polimi.ingsw.model;




import java.util.ArrayList;

/**
 * Game class contains the main logic of "Master of Renaissance", which is divided in several macro-areas. The
 * first area is the Player section, which contains information about the single player. The second area is the
 * GameBoard section which contains the faith track and
 *
 * @author chris
 */

public class Game {
    public static final int MAXPLAYERS = 4;

    private int playersNumber=0;
    //private boolean gameStarted_Ended;
    private int winnerIndex; //not sure it goes here
    private final Board board;
    private final ArrayList<Player> players;
    private Player currentPlayer;
    private int currentPlayerId;

    /**Constructor Game creates a new Game instance. */
    public Game() {
        board = new Board();
        players = new ArrayList<>();
    }

    /**
     * Method getBoard returns the board of this Game object.
     *
     * @return the board (type Board) of this Game object.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Method addNewPlayer creates a new player in the game.
     * The minimum length of Players is 1 elements, and the maximum is 4.
     *
     * @param player of type Player not null - the player to be added.
     */
    public void addNewPlayer(Player player) {
        //todo to be checked to not exceed MAXPLAYERS with getPlayerNumber()
        int idx=1;
        while (isNicknameTaken(player.getNickname()))
            player.setNickname(player.getNickname()+"_"+idx++);
        players.add(player);
        ++playersNumber;
    }

    /**
     * Method getPlayerByNickname finds the player identified by his nickname in the list of players.
     *
     * @param nickname of type String that is the nickname of the player.
     * @return Player that is the desired player, null if there's no player with that nickname.
     */
    public Player getPlayerByNickname(String nickname) {
        for (Player player : players)
            if (player.getNickname().equalsIgnoreCase(nickname))
                return player;

        return null;
    }

    /**
     * Method setCurrentPlayer updates the "currentPlayer" with the desired one.
     *
     * @param player of type Player that is the player to be set as current.
     */
    public void setCurrentPlayer(Player player) {
        this.currentPlayerId = players.indexOf(player);
        this.currentPlayer = player;
    }

    /**
     * Method getPlayerNumber returns the number of the players in this Game.
     *
     * @return int that is the number (1 to MAXPLAYERS) of players of this Game Object.
     */
    public int getPlayersNumber() {
        return playersNumber;
    }

    /**
     * Method getCurrentPlayer returns the currentPlayer of the game.
     *
     * @return the currentPlayer (type Player) of this Game object.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Method getPlayerByIndex finds the player identified by his index in the list of players.
     *
     * @param playerIndex of type int that is the index of the player.
     * @return Player that is the desired player.
     */
    public Player getPlayerByIndex(int playerIndex){
        //todo playerIndex must not exceed getPlayersNumber()
        return players.get(playerIndex);
    }

    /**
     * Method isNickNameTaken checks if a given nickname is already used by another player
     *
     * @return boolean
     */
    public boolean isNicknameTaken(String nickname){
        return getPlayerByNickname(nickname) != null;
    }

    /** Method nextTurn updates currentPlayer to the next player in "players" order. */
    public void nextTurn() {
        currentPlayerId = (currentPlayerId == players.size() - 1) ? 0 : currentPlayerId + 1;
        setCurrentPlayer(players.get(currentPlayerId));
    }
    /**
     * Method getCurrentPlayerId is a getter of the current player id.
     *
     * @return the current player id
     */
    public int getCurrentPlayerId(){
        return currentPlayerId;
    }
}