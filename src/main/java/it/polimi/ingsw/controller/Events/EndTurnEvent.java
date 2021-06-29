package it.polimi.ingsw.controller.Events;

/**
 *client to server event triggered when the player ends his turn
 */
public class EndTurnEvent extends ClientEvent{

    @Override
    public void notifyHandler(ClientEventHandler eventHandler){
        eventHandler.handleEvent(this);
    }
}
