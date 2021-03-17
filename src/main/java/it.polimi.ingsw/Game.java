package it.polimi.ingsw;

public class Game {
    private int playersNumber;
    private boolean hasGameStarted;
    private boolean hasGameEnded;
    private int winnerIndex;
    private Board board;
    private Player[] players;

    public int getPlayersNumber() {
        return playersNumber;
    }
    public boolean getHasGameStarted() {
        return hasGameStarted;
    }
    public boolean getHasGameEnded() {
        return hasGameEnded;
    }
}
