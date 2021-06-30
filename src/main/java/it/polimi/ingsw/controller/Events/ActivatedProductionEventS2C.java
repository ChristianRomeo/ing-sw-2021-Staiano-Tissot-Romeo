package it.polimi.ingsw.controller.Events;

import it.polimi.ingsw.model.*;

import java.util.Map;

/**
 *server to client event triggered when the player has activated production
 */
public class ActivatedProductionEventS2C extends ServerEvent {

    private final PlayerWarehouse newWarehouse;
    private final Map<Resource,Integer> newStrongbox;
    private final Integer fullSlotsLeaderCard1;
    private final Integer fullSlotsLeaderCard2;

    public ActivatedProductionEventS2C(PlayerWarehouse newWarehouse, Map<Resource, Integer> newStrongbox, Integer fullSlotsLeaderCard1, Integer fullSlotsLeaderCard2) {
        this.newWarehouse = newWarehouse;
        this.newStrongbox = newStrongbox;
        this.fullSlotsLeaderCard1 = fullSlotsLeaderCard1;
        this.fullSlotsLeaderCard2 = fullSlotsLeaderCard2;
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
     * notify the eventHandler (which is the virtual view in this case) to handle this specific ActivatedProductionEventS2C
     * @param eventHandler is the handler which will handle this specific ActivatedProductionEventS2C
     */
    @Override
    public void notifyHandler(ServerEventObserver eventHandler){
        eventHandler.handleEvent(this);
    }
}
