package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.DevelopmentCardBoard;
import it.polimi.ingsw.model.PersonalCardBoard;

//the current player has bought a development card

public class BoughtCardEventS2C extends ServerEvent{
    private final PersonalCardBoard newPersonalCardBoard; //new personal card board of the current player
    private final DevelopmentCardBoard newCardBoard; //new card board

    public BoughtCardEventS2C(PersonalCardBoard newPersonalCardBoard, DevelopmentCardBoard newCardBoard) {

        this.newPersonalCardBoard = newPersonalCardBoard;
        this.newCardBoard = newCardBoard;
    }

    public PersonalCardBoard getNewPersonalCardBoard() {
        return newPersonalCardBoard;
    }

    public DevelopmentCardBoard getNewCardBoard() {
        return newCardBoard;
    }

    @Override
    public void notifyHandler(ServerEventObserver eventHandler){
        eventHandler.handleEvent(this);
    }
}
