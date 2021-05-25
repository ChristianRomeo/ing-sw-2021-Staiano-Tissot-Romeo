package it.polimi.ingsw.client;

import it.polimi.ingsw.client.GUI.GuiView;
import it.polimi.ingsw.controller.Events.*;

//questo dovrebbe solo mostrare le cose alla cli , in base a che eventi arrivano


public class EventsHandlerCLI implements ServerEventObserver {

    private final ClientModel clientModel;
    private final CliView cliView;            //non so se dobbiamo dividere tra cliview e guiview


    public EventsHandlerCLI(ClientModel clientModel, CliView cliView){
        this.clientModel =clientModel;
        this.cliView=cliView;
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

    @Override
    public void handleEvent(GameStarterEventS2C event) {
        cliView.showMessage(Styler.ANSI_TALK+"Please wait your turn..."); //da fare meglio
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
