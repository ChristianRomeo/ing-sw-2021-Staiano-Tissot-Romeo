package it.polimi.ingsw.controller.Events;

import it.polimi.ingsw.model.*;

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

    @Override
    public void notifyHandler(ServerEventObserver eventHandler){
        eventHandler.handleEvent(this);
    }
}
