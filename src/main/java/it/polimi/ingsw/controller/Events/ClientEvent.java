package it.polimi.ingsw.controller.Events;

import java.io.Serializable;

/**
 *abstract class extended with all the client events
 */
public abstract class ClientEvent implements Serializable {
    //message from the client
    /**
     * definition of the notifyHandler class, which will be used for every event sent from the client, to notify its handler
     * (the virtual view), which is gonna handle it
     * @param eventHandler is the handler for the event
     */
    public void notifyHandler(ClientEventHandler eventHandler){}
}
