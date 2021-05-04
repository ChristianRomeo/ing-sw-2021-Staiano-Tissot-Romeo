package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.Events.*;

//questa classe gestisce gli eventi che arrivano da server (usa il pattern visitor)

public class ServerEventObserverImpl implements ServerEventObserver {

    @Override
    public void handleEvent(LeaderCardActionEventS2C event) {

    }

    @Override
    public void handleEvent(BoughtCardEventS2C event) {

    }

    @Override
    public void handleEvent(ActivatedProductionEventS2C event) {

    }

    @Override
    public void handleEvent(IncrementPositionEventS2C event) {

    }

    @Override
    public void handleEvent(VaticanReportEventS2C event) {

    }

    @Override
    public void handleEvent(UseMarketEventS2C event) {

    }

    @Override
    public void handleEvent(NewTurnEventS2C event) {

    }

    @Override
    public void handleEvent(IllegalActionEventS2C event) {

    }

    @Override
    public void handleEvent(GameStarterEventS2C event) {

    }

    @Override
    public void handleEvent(EndGameEventS2C event) {

    }

    @Override
    public void handleEvent(LorenzoTurnEventS2C event) {

    }

    @Override
    public void handleEvent(EndPreparationEventS2C event) {

    }
}
