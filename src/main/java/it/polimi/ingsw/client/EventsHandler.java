package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.Events.*;
import it.polimi.ingsw.controller.View;
import it.polimi.ingsw.model.LeaderCard;

import java.util.List;

//forse devo fare un server event observer impl per la cli e uno per la gui
//oppure creo un'altra cosa che mostra i messaggi nella cli e nella gui (ma non penso va bene)
//o forse basta che metto dei metodi show (tipo show messaggio di inizio pregame) nella view e li chiamo da
//qua, poi a seconda di che view è agisce in un modo diverso

/**
 * Handles Server incoming Events and dispatch actions, it uses VISITOR PATTERN
 *  todo:cli and gui
 */
public class EventsHandler implements ServerEventObserver {

    private final ClientModel clientModel;
    private final View view;            //non so se dobbiamo dividere tra cliview e guiview

    public EventsHandler(ClientModel clientModel, View view){
        this.clientModel =clientModel;
        this.view=view;
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

        view.showMessage(clientModel.getCurrentPlayerNick() + " ha fatto un'azione leader!",false);
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

        view.showMessage(clientModel.getCurrentPlayerNick() + " ha comprato una carta!",false);
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

        view.showMessage(clientModel.getCurrentPlayerNick() + " ha attivato la produzione!",false);
        //qui magari mostro le cose che sono cambiate
    }

    @Override
    public void handleEvent(IncrementPositionEventS2C event) {
        clientModel.setFTPosition(event.getPlayerNickname(), event.getNewPosition());
        view.showMessage(event.getPlayerNickname()+ " è andato avanti di 1 nel percorso fede!",false);
        //qui volendo gli mostro qualcosa
    }

    @Override
    public void handleEvent(VaticanReportEventS2C event) {
        for(String player: clientModel.getNicknames())
            clientModel.setPopeTiles(player,event.getNewPopeTilesStatus().get(player));

        view.showMessage("E' stato attivato un rapporto in vaticano!",false);
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

        view.showMessage(clientModel.getCurrentPlayerNick() + " ha attivato il Mercato!",false);
        //qui magari mostro le cose che sono cambiate
    }

    @Override
    public void handleEvent(NewTurnEventS2C event) {
        clientModel.setCurrentPlayer(event.getNickname());
        //qui avviso l'utente che è il turno di questo tizio,poi magari se è il suo gli dico "è il tuo turno", e le azioni che può fare

        //todo riguardare
        if(event.getNickname().equals(clientModel.getMyNickname())){
            view.showMessage(Styler.color('g',"è il tuo turno"),true);
            if(clientModel.isPregame()){
                view.showMessage("scrivi SCEGLI per iniziare la scelta",false);
            }else{
                if(clientModel.hasGameStarted()){
                    //qui mostro tutte le azioni che può fare
                }
            }
        }else{
            view.showMessage("è il turno di "+event.getNickname(),false);
            //qui potrei mettere tipo le azioni che può fare quando non è il suo turno (cioè solo show roba)
        }
    }

    @Override
    public void handleEvent(IllegalActionEventS2C event) { //si potrà fare meglio
        view.showErrorMessage("Attenzione hai fatto un'azione illegale "+event.getIllegalAction().getDescription());
    }

    //todo pregame da riguardare
    @Override
    public void handleEvent(GameStarterEventS2C event) {
        clientModel.setIsPregame(true);
        //imposto cose nel client model (board ecc)
        //dico al giocatore di aspettare il suo turno e poi cliccare tipo SCEGLI
        //intanto gli mostro le cose

        clientModel.initClientModel(event.getNicknames(), event.getMarket(), event.getCardBoard());
        clientModel.setMyIndex(event.getIndexPlayer());
        clientModel.setLeaderCards(clientModel.getMyNickname(), event.getChoiceLeaderCards());

        //ho messo al giocatore tutte le leader card tra cui può scegliere, cosi poi gli mostro direttamente
        //le sue leadercards
        view.showMessage("è iniziato il pregame, aspetta il tuo turno e poi scrivi SCEGLI",false); //da fare meglio
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
        clientModel.setIsPregame(false);
        clientModel.setHasGameStarted(true);
        //setto i warehouse arrivati (e anche le leader cards)
        for(String player: clientModel.getNicknames()){
            clientModel.setWarehouse(player, event.getWarehouses().get(player));
            clientModel.setLeaderCards(player,event.getLeaderCards().get(player));
        }

        clientModel.setCurrentPlayer(clientModel.getNicknames().get(0));
        view.showMessage("Finito il pregame, ora inizia il vero gioco. E' il turno di "+ clientModel.getNicknames().get(0),true);

        if(clientModel.getMyNickname().equals(clientModel.getNicknames().get(0)))
            view.showMessage("E' il tuo turno! Le azioni che puoi fare sono ....",true);
        else
            view.showMessage("E' il turno di "+ clientModel.getNicknames().get(0)+" Le azioni che puoi fare sono ....",false);

    }


    //todo questo non si dovrà implementare qui
    @Override
    public void handleEvent(NewConnectionEventS2C event) {

    }
}
