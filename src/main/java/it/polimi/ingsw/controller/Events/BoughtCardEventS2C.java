package it.polimi.ingsw.controller.Events;

import it.polimi.ingsw.model.DevelopmentCardBoard;
import it.polimi.ingsw.model.PersonalCardBoard;
import it.polimi.ingsw.model.PlayerWarehouse;
import it.polimi.ingsw.model.Resource;

import java.util.Map;

/**
 *server to client event triggered when the player has bought a Development card
 */

public class BoughtCardEventS2C extends ServerEvent{
    private final PersonalCardBoard newPersonalCardBoard; //new personal card board of the current player
    private final DevelopmentCardBoard newCardBoard; //new card board
    private final PlayerWarehouse newWarehouse;
    private final Map<Resource,Integer> newStrongbox;
    private final Integer fullSlotsLeaderCard1;
    private final Integer fullSlotsLeaderCard2;

    public BoughtCardEventS2C(PersonalCardBoard newPersonalCardBoard, DevelopmentCardBoard newCardBoard, PlayerWarehouse newWarehouse, Map<Resource, Integer> newStrongbox, Integer fullSlotsLeaderCard1, Integer fullSlotsLeaderCard2) {
        this.newPersonalCardBoard = newPersonalCardBoard;
        this.newCardBoard = newCardBoard;
        this.newWarehouse = newWarehouse;
        this.newStrongbox = newStrongbox;
        this.fullSlotsLeaderCard1 = fullSlotsLeaderCard1;
        this.fullSlotsLeaderCard2 = fullSlotsLeaderCard2;
    }

    public PersonalCardBoard getNewPersonalCardBoard() {
        return newPersonalCardBoard;
    }

    public DevelopmentCardBoard getNewCardBoard() {
        return newCardBoard;
    }

    public PlayerWarehouse getNewWarehouse() {
        return newWarehouse;
    }

    public Map<Resource, Integer> getNewStrongbox() {
        return newStrongbox;
    }

    public Integer getFullSlotsLeaderCard1() {
        return fullSlotsLeaderCard1;
    }

    public Integer getFullSlotsLeaderCard2() {
        return fullSlotsLeaderCard2;
    }

    /**
     * notify the eventHandler (which is the virtual view in this case) to handle this specific BoughtCardEventS2C
     * @param eventHandler is the handler which will handle this specific BoughtCardEventS2C
     */
    @Override
    public void notifyHandler(ServerEventObserver eventHandler){
        eventHandler.handleEvent(this);
    }
}
