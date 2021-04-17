package it.polimi.ingsw.controller;

//the client has activated or discarded a leader card
public class LeaderCardActionEvent extends ClientEvent {
    //qua ci vogliono gli attributi dell'evento specifico

    @Override
    public void notifyHandler(ClientEventHandler eventHandler){
        eventHandler.handleEvent(this);
    }
}
