package it.polimi.ingsw.controller.Events;

import java.io.Serializable;

/**
 *messages from server to client
 */
public abstract class ServerEvent implements Serializable {
    /**
     * definition of the notifyHandler class, which will be used for every event sent from the server, to notify its handler
     * (the virtual view), which is gonna handle it
     * @param eventHandler is the handler for the event
     */
    public void notifyHandler(ServerEventObserver eventHandler){

    }
}
