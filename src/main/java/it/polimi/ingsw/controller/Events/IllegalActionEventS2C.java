package it.polimi.ingsw.controller.Events;

import it.polimi.ingsw.model.IllegalAction;

import java.io.Serializable;

public class IllegalActionEventS2C extends ServerEvent implements Serializable {

    private final IllegalAction illegalAction;

    public IllegalActionEventS2C(IllegalAction illegalAction) {
        this.illegalAction = illegalAction;
    }

    public IllegalAction getIllegalAction() {
        return illegalAction;
    }

    @Override
    public void notifyHandler(ServerEventObserver eventHandler){
        eventHandler.handleEvent(this);
    }
}
