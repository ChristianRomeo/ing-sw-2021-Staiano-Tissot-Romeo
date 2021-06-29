package it.polimi.ingsw.controller.Events;

import it.polimi.ingsw.model.*;

/**
 *server to client event triggered when the player wants to use the market
 */
public class UseMarketEventS2C extends ServerEvent {

    private final Market newMarket;
    private final PlayerWarehouse newWarehouse;
    private final Integer fullSlotsLeaderCard1;
    private final Integer fullSlotsLeaderCard2;

    public UseMarketEventS2C(Market newMarket, PlayerWarehouse newWarehouse, Integer fullSlotsLeaderCard1, Integer fullSlotsLeaderCard2) {
        this.newMarket = newMarket;
        this.newWarehouse = newWarehouse;
        this.fullSlotsLeaderCard1 = fullSlotsLeaderCard1;
        this.fullSlotsLeaderCard2 = fullSlotsLeaderCard2;
    }

    public Market getNewMarket() {
        return newMarket;
    }

    public PlayerWarehouse getNewWarehouse() {
        return newWarehouse;
    }

    public Integer getFullSlotsLeaderCard1() {
        return fullSlotsLeaderCard1;
    }

    public Integer getFullSlotsLeaderCard2() {
        return fullSlotsLeaderCard2;
    }

    @Override
    public void notifyHandler(ServerEventObserver eventHandler){
        eventHandler.handleEvent(this);
    }
}
