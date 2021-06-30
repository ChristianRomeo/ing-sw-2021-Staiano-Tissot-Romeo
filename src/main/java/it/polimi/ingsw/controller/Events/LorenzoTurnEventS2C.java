package it.polimi.ingsw.controller.Events;

import it.polimi.ingsw.model.*;

/**
 *server to client event triggered when it's Lorenzo's turn
 */
public class LorenzoTurnEventS2C extends ServerEvent{

    private final SoloAction activatedSoloAction;
    private final DevelopmentCardBoard newBoard;

    public LorenzoTurnEventS2C(SoloAction activatedSoloAction, DevelopmentCardBoard newBoard) {
        this.activatedSoloAction = activatedSoloAction;
        this.newBoard = newBoard;
    }

    public SoloAction getActivatedSoloAction() {
        return activatedSoloAction;
    }

    public DevelopmentCardBoard getNewBoard() {
        return newBoard;
    }

    /**
     * notify the eventHandler (which is the virtual view in this case) to handle this specific LorenzoTurnEventS2C
     * @param eventHandler is the handler which will handle this specific LorenzoTurnEventS2C
     */
    @Override
    public void notifyHandler(ServerEventObserver eventHandler){
        eventHandler.handleEvent(this);
    }
}
