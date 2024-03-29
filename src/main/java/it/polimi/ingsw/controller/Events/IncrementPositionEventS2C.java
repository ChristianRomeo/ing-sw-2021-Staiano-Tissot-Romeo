package it.polimi.ingsw.controller.Events;

/**
 *server to client event triggered when a player has moved his Faith track position
 */
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

    /**
     * notify the eventHandler (which is the virtual view in this case) to handle this specific IncrementPositionEventS2C
     * @param eventHandler is the handler which will handle this specific IncrementPositionEventS2C
     */
    @Override
    public void notifyHandler(ServerEventObserver eventHandler){
        eventHandler.handleEvent(this);
    }
}
