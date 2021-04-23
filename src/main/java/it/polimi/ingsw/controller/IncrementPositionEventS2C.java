package it.polimi.ingsw.controller;

//a player is moving forward in his faith track.
public class IncrementPositionEventS2C extends ServerEvent {

    private final int newPosition;
    private final String playerNickname; //nickname of the player who is moving forward (it can be any player, not only the current one)

    public IncrementPositionEventS2C(int newPosition, String playerNickname) {
        this.newPosition = newPosition;
        this.playerNickname = playerNickname;
    }

    public int getNewPosition() {
        return newPosition;
    }

    public String getPlayerNickname() {
        return playerNickname;
    }

    @Override
    public void notifyHandler(ServerEventObserver eventHandler){
        eventHandler.handleEvent(this);
    }
}
