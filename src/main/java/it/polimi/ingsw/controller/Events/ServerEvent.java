package it.polimi.ingsw.controller.Events;

import java.io.Serializable;

public abstract class ServerEvent implements Serializable {
//message from the server to the client

    public void notifyHandler(ServerEventObserver eventHandler){

    }
}
