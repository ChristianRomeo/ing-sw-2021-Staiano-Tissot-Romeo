package it.polimi.ingsw.controller.Events;

public class EndTurnEvent extends ClientEvent{

    @Override
    public void notifyHandler(ClientEventHandler eventHandler){
        eventHandler.handleEvent(this);
    }
}
