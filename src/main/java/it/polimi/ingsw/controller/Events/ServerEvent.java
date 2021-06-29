package it.polimi.ingsw.controller.Events;

import java.io.Serializable;

/**
 *messages from server to client
 */
public abstract class ServerEvent implements Serializable {

    public void notifyHandler(ServerEventObserver eventHandler){

    }
}
