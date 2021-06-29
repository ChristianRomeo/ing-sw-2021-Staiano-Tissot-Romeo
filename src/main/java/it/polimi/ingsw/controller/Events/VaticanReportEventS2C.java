package it.polimi.ingsw.controller.Events;

import it.polimi.ingsw.model.*;

import java.util.Map;

/**
 *server to client event triggered when a Vatican report happens
 */
public class VaticanReportEventS2C extends ServerEvent{

    //map con coppie nickname->status delle pope tiles
    private final Map<String, SameTypeTriple<PopeFavorTileStatus>> newPopeTilesStatus;

    public VaticanReportEventS2C(Map<String, SameTypeTriple<PopeFavorTileStatus>> newPopeTilesStatus) {
        this.newPopeTilesStatus = newPopeTilesStatus;
    }

    public Map<String, SameTypeTriple<PopeFavorTileStatus>> getNewPopeTilesStatus() {
        return newPopeTilesStatus;
    }

    @Override
    public void notifyHandler(ServerEventObserver eventHandler){
        eventHandler.handleEvent(this);
    }
}
