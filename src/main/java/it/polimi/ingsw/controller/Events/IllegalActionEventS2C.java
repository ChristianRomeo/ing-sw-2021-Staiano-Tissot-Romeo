package it.polimi.ingsw.controller.Events;

import it.polimi.ingsw.model.IllegalAction;

/**
 *server to client event triggered when an illegal action happens
 */
public class IllegalActionEventS2C extends ServerEvent {

    private final IllegalAction illegalAction;

    public IllegalActionEventS2C(IllegalAction illegalAction) {
        this.illegalAction = illegalAction;
    }

    public IllegalAction getIllegalAction() {
        return illegalAction;
    }

    /**
     * notify the eventHandler (which is the virtual view in this case) to handle this specific IllegalActionEventS2C
     * @param eventHandler is the handler which will handle this specific IllegalActionEventS2C
     */
    @Override
    public void notifyHandler(ServerEventObserver eventHandler){
        eventHandler.handleEvent(this);
    }
}
