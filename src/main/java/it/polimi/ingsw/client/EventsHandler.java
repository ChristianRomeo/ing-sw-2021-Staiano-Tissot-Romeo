package it.polimi.ingsw.client;

import it.polimi.ingsw.client.GUI.EventsHandlerGUI;
import it.polimi.ingsw.client.GUI.GuiView;
import it.polimi.ingsw.controller.Events.*;
import it.polimi.ingsw.controller.View;
import it.polimi.ingsw.model.LeaderCard;

import java.util.List;

//forse devo fare un server event observer impl per la cli e uno per la gui
//oppure creo un'altra cosa che mostra i messaggi nella cli e nella gui (ma non penso va bene)
//o forse basta che metto dei metodi show (tipo show messaggio di inizio pregame) nella view e li chiamo da
//qua, poi a seconda di che view è agisce in un modo diverso

//questo dovrebbe settare le cose nel client model in base a che evento arriva
//poi passa l'evento a eventhandlerview che mostra le cose all'utente

/**
 * Handles Server incoming Events and dispatch actions, it uses VISITOR PATTERN
 *  todo:cli and gui
 */
public class EventsHandler implements ServerEventObserver {

    private final ClientModel clientModel;
    private final View view;            //non so se dobbiamo dividere tra cliview e guiview

    private final ServerEventObserver eventHandlerView; //questo gestisce gli eventi dal server per quanto riguarda la view

    public EventsHandler(ClientModel clientModel, View view){
        this.clientModel =clientModel;
        this.view=view;
        if(view instanceof CliView){
            eventHandlerView = new EventsHandlerCLI(clientModel,(CliView) view);
        }else{
            eventHandlerView = new EventsHandlerGUI(clientModel,(GuiView)view);
        }
    }

    @Override
    public void handleEvent(LeaderCardActionEventS2C event) {
        List<LeaderCard> leaderCards = clientModel.getPlayerLeaderCards(clientModel.getCurrentPlayerNick());

        if(event.isActive1())
            leaderCards.get(0).activate();

        if(event.isActive2())
            leaderCards.get(1).activate();

        if(event.isDiscarded1())
            leaderCards.get(0).discard();

        if(event.isDiscarded2())
            leaderCards.get(1).discard();

        view.showMessage(clientModel.getCurrentPlayerNick() + " has used a leader card");
        //qua magari mostro la sua nuova situazione delle sue carte leader

    }

    @Override
    public void handleEvent(BoughtCardEventS2C event) {
        clientModel.setDevelopmentCardBoard(event.getNewCardBoard());
        clientModel.setPersonalCardBoard(clientModel.getCurrentPlayerNick(), event.getNewPersonalCardBoard());
        clientModel.setWarehouse(clientModel.getCurrentPlayerNick(), event.getNewWarehouse());
        clientModel.setStrongbox(clientModel.getCurrentPlayerNick(), event.getNewStrongbox());

        if(event.getFullSlotsLeaderCard1()!=null)
            clientModel.getPlayerLeaderCards(clientModel.getCurrentPlayerNick()).get(0).setFullSlotsNumber(event.getFullSlotsLeaderCard1());
        if(event.getFullSlotsLeaderCard2()!=null)
            clientModel.getPlayerLeaderCards(clientModel.getCurrentPlayerNick()).get(1).setFullSlotsNumber(event.getFullSlotsLeaderCard2());

        view.showMessage(clientModel.getCurrentPlayerNick() + " has bought a card");
        //qui magari mostro le cose che sono cambiate
    }

    @Override
    public void handleEvent(ActivatedProductionEventS2C event) {
        clientModel.setWarehouse(clientModel.getCurrentPlayerNick(), event.getNewWarehouse());
        clientModel.setStrongbox(clientModel.getCurrentPlayerNick(), event.getNewStrongbox());

        if(event.getFullSlotsLeaderCard1()!=null)
            clientModel.getPlayerLeaderCards(clientModel.getCurrentPlayerNick()).get(0).setFullSlotsNumber(event.getFullSlotsLeaderCard1());
        if(event.getFullSlotsLeaderCard2()!=null)
            clientModel.getPlayerLeaderCards(clientModel.getCurrentPlayerNick()).get(1).setFullSlotsNumber(event.getFullSlotsLeaderCard2());

        view.showMessage(clientModel.getCurrentPlayerNick() + " has used his production");
        //qui magari mostro le cose che sono cambiate
    }

    @Override
    public void handleEvent(IncrementPositionEventS2C event) {
        if(event.getPlayerNickname().equals("Lorenzo il Magnifico")){
            clientModel.setBlackCrossPosition(event.getNewPosition());
        }else{
            clientModel.setFTPosition(event.getPlayerNickname(), event.getNewPosition());
        }
        view.showMessage(event.getPlayerNickname()+ " has proceeded in the faith track");   //mostrare tasto per nuova situazione
        //qui volendo gli mostro qualcosa
    }

    @Override
    public void handleEvent(VaticanReportEventS2C event) {
        for(String player: clientModel.getNicknames())
            clientModel.setPopeTiles(player,event.getNewPopeTilesStatus().get(player));

        view.showMessage("A vatican report has been activated");
        //qui volendo gli mostro qualcosa
    }

    @Override
    public void handleEvent(UseMarketEventS2C event) {
        clientModel.setMarket(event.getNewMarket());
        clientModel.setWarehouse(clientModel.getCurrentPlayerNick(), event.getNewWarehouse());

        if(event.getFullSlotsLeaderCard1()!=null)
            clientModel.getPlayerLeaderCards(clientModel.getCurrentPlayerNick()).get(0).setFullSlotsNumber(event.getFullSlotsLeaderCard1());
        if(event.getFullSlotsLeaderCard2()!=null)
            clientModel.getPlayerLeaderCards(clientModel.getCurrentPlayerNick()).get(1).setFullSlotsNumber(event.getFullSlotsLeaderCard2());

        view.showMessage(clientModel.getCurrentPlayerNick() + " has used the market");
        //qui magari mostro le cose che sono cambiate
    }

    @Override
    public void handleEvent(NewTurnEventS2C event) {
        clientModel.setCurrentPlayer(event.getNickname());
        //qui avviso l'utente che è il turno di questo tizio,poi magari se è il suo gli dico "è il tuo turno", e le azioni che può fare

        event.notifyHandler(eventHandlerView);
    }

    @Override
    public void handleEvent(IllegalActionEventS2C event) { //si potrà fare meglio
        Styler.cls();
        view.showErrorMessage("Illegal action: "+event.getIllegalAction().getDescription());
        //show choices
    }

    //todo pregame da riguardare
    @Override
    public void handleEvent(GameStarterEventS2C event) {
        clientModel.setIsPregame(true);
        clientModel.initClientModel(event.getNicknames(), event.getMarket(), event.getCardBoard());
        clientModel.setMyIndex(event.getIndexPlayer());
        clientModel.setLeaderCards(clientModel.getMyNickname(), event.getChoiceLeaderCards());

        //ho messo al giocatore tutte le leader card tra cui può scegliere, cosi poi gli mostro direttamente
        //le sue leadercards
        // view.showMessage(Styler.ANSI_TALK+"Please wait your turn..."); QUESTO MO STA NELL EVENTHANDLERVIEW
        //gli devo mostrare le cose

        event.notifyHandler(eventHandlerView); //ciò poi si occupa di mostrare le cose (in base a che view è)

    }

    @Override
    public void handleEvent(EndGameEventS2C event) {
        view.showLadderBoard(event);
        clientModel.setHasGameStarted(false);
        clientModel.setGameEnded(true); //è finito il gioco
        view.showMessage("Write 'exit' to continue.");
    }

    @Override
    public void handleEvent(LorenzoTurnEventS2C event) {
        clientModel.setDevelopmentCardBoard(event.getNewBoard());
        view.showLorenzoTurn(event.getActivatedSoloAction());
    }

    @Override
    public void handleEvent(EndPreparationEventS2C event) {
        clientModel.setIsPregame(false);
        clientModel.setHasGameStarted(true);
        //setto i warehouse arrivati (e anche le leader cards)
        for(String player: clientModel.getNicknames()){
            clientModel.setWarehouse(player, event.getWarehouses().get(player));
            clientModel.setLeaderCards(player,event.getLeaderCards().get(player));
        }
        clientModel.setCurrentPlayer(clientModel.getNicknames().get(0));

        event.notifyHandler(eventHandlerView);
    }


    //todo questo non si dovrà implementare qui
    @Override
    public void handleEvent(NewConnectionEventS2C event) {

    }
}
