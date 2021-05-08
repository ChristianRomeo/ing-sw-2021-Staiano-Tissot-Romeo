package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.Events.*;

//questa classe gestisce gli eventi che arrivano da server (usa il pattern visitor)

public class ServerEventObserverImpl implements ServerEventObserver {

    private ClientModel clientModel;

    public ServerEventObserverImpl(ClientModel clientModel){
        this.clientModel =clientModel;
    }

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

    //mi invia le cose per il pregame
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

    @Override
    public void handleEvent(NewConnectionEventS2C event) {

    }
}
