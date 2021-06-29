package it.polimi.ingsw.controller.Events;

import java.io.Serializable;

/**
 *abstract class extended with all the client events
 */
public abstract class ClientEvent implements Serializable {
    //message from the client

    public void notifyHandler(ClientEventHandler eventHandler){}
}
