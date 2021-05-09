package it.polimi.ingsw.controller.Events;

import it.polimi.ingsw.model.IllegalAction;


public class IllegalActionEventS2C extends ServerEvent {

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
