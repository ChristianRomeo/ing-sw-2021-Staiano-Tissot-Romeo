package it.polimi.ingsw.controller.Events;

public abstract class ClientEvent {
    //message from the client

    public void notifyHandler(ClientEventHandler eventHandler){}
}
