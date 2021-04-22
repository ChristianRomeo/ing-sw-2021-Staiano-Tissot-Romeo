package it.polimi.ingsw.controller;

public class EndTurnEvent extends ClientEvent{

    @Override
    public void notifyHandler(ClientEventHandler eventHandler){
        eventHandler.handleEvent(this);
    }
}
