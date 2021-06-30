package it.polimi.ingsw.controller.Events;

/**
 *client to server event triggered when the player ends his turn
 */
public class EndTurnEvent extends ClientEvent{

    /**
     * notify the eventHandler (which is the virtual view in this case) to handle this specific EndTurnEvent
     * @param eventHandler is the handler which will handle this specific EndTurnEvent
     */
    @Override
    public void notifyHandler(ClientEventHandler eventHandler){
        eventHandler.handleEvent(this);
    }
}
