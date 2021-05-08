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
        clientModel.setCurrentPlayer(event.getNickname());
        //qui avviso l'utente che è il turno di questo tizio,
        //poi magari se è il suo gli dico "è il tuo turno", e le azioni che può fare
    }

    @Override
    public void handleEvent(IllegalActionEventS2C event) {

    }

    //mi invia le cose per il pregame
    @Override
    public void handleEvent(GameStarterEventS2C event) {
        clientModel.setIsPregame(true);
        //imposto cose nel client model (board ecc)
        //dico al giocatore di aspettare il suo turno e poi cliccare tipo SCEGLI
        //intanto gli mostro le cose
        clientModel.setDevelopmentCardBoard(event.getCardBoard());
        clientModel.setMarket(event.getMarket());
        clientModel.setPlayersNicknames(event.getNicknames());
        clientModel.setMyIndex(event.getIndexPlayer());
        clientModel.setLeaderCards(clientModel.getMyNickname(), event.getChoiceLeaderCards());
        //ho messo al giocatore tutte le leader card tra cui può scegliere, cosi poi gli mostro direttamente
        //le sue leadercards

        //ora devo mostrargli le cose e gli dico di aspettare il suo turno, e poi di scrivere il comando SCEGLI
    }

    @Override
    public void handleEvent(EndGameEventS2C event) {

    }

    @Override
    public void handleEvent(LorenzoTurnEventS2C event) {

    }

    @Override
    public void handleEvent(EndPreparationEventS2C event) {
        clientModel.setIsPregame(false);
        clientModel.setIsGameStarted(true);
        //metodo non finito

    }

    @Override
    public void handleEvent(NewConnectionEventS2C event) {

    }
}
