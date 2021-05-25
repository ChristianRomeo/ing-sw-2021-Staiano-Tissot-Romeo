package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.ClientModel;
import it.polimi.ingsw.client.Styler;
import it.polimi.ingsw.controller.Events.*;
import it.polimi.ingsw.controller.View;

//per ora li tengo separati gli events handler di gui e cli, se no è un casino, però sarebbe meglio
//tenere la parte in cui si settano le cose nel client model in una sola classe, quindi potrei fare
//un events handler apposito

//questo dovrebbe solo mostrare le cose alla gui , in base a che eventi arrivano

/**
 * Handles Server incoming Events and dispatch actions, it uses VISITOR PATTERN.
 * This handler is used for the gui.
 *
 */
public class EventsHandlerGUI implements ServerEventObserver {

    private final ClientModel clientModel;
    private final GuiView guiView;

    public EventsHandlerGUI(ClientModel clientModel, GuiView guiView){
        this.clientModel =clientModel;
        this.guiView=guiView;
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

        //ho messo al giocatore tutte le leader card tra cui può scegliere, cosi poi gli mostro direttamente
        //le sue leadercards
        //guiView.showMessage(Styler.ANSI_TALK+"Please wait your turn..."); //da fare meglio
        //gli devo mostrare le cose


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
