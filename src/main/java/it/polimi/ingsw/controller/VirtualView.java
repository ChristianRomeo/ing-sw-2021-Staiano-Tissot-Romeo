package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.Events.*;
import it.polimi.ingsw.model.IllegalAction;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.SameTypeTriple;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class VirtualView implements ClientEventHandler, ServerEventObserver {
    private final List<ClientHandler> clientHandlers;
    private final Controller controller;
    private final static Logger logger = Logger.getLogger(VirtualView.class.getName());


    public VirtualView(Controller controller) {
        this.clientHandlers = new ArrayList<>();
        this.controller = controller;
    }

    public Controller getController(){
        return controller;
    }

    public synchronized List<ClientHandler> getClientHandlers(){ return  clientHandlers;}

    public synchronized void addClientHandler(ClientHandler clientHandler) {

            clientHandlers.add(clientHandler);
            controller.getGame().addNewPlayer(new Player(clientHandler.getNickname()));                                 //copia aggiunta utente in game e creazione giocatore

        logger.info("A new player joined: "+ clientHandler.getNickname());
    }

    /**
     * sends an event to every ClientHandler
     * @param serverEvent is the event to be sent
     */
    public void sendToEveryone(ServerEvent serverEvent){
        for (ClientHandler clientHandler : clientHandlers)
            if(clientHandler.isConnected())
                clientHandler.send(serverEvent);
    }

    /**
     * sends an event to a specific ClientHandler
     * @param serverEvent is the event to be sent
     * @param nickname is the player whose ClientHandler will receive the event
     */
    public void sendTo(ServerEvent serverEvent, String nickname){
        for (ClientHandler clientHandler : clientHandlers)
            if(clientHandler.isConnected() && clientHandler.getNickname().equals(nickname)){
                clientHandler.send(serverEvent);
                return;
            }
    }

    /**
     * closes every connected socket
     */
    public synchronized void closeAll() {
        for (ClientHandler clientHandler : clientHandlers)
            if (clientHandler.isConnected())
                clientHandler.closeSocket();
    }

    // ---- events from the client----
    /**
     * BoughCardEvent from the client
     * checks if an action has already been done:
     * if not, it sends the event to the controller which will then edit the model;
     * if yes, it adds a new IllegalAction to communicate that an action has already been done
     * @param event is the event received from the client
     */
    public synchronized void handleEvent(BoughtCardEvent event){

        if(!controller.getGame().hasDoneAction()){
            controller.buyDevelopmentCard(event.getRow(), event.getColumn(), event.getPile());
        }else{
            controller.getGame().addIllegalAction(new IllegalAction(controller.getGame().getCurrentPlayer(),"AlreadyDoneAction"));
        }
    }

    /**
     * NumPlayerEvent from the client, sent to the controller which will then edit the model
     * @param event is the event received from the client
     */
    public synchronized void handleEvent(NumPlayerEvent event) {
        logger.info("received the number of players: "+ event.getNumPlayers());
        controller.getGame().setWantedNumPlayers(event.getNumPlayers());
    }

    /**
     *LeaderCardActionEvent from the client
     * action could be discard ("d") or activate "a")
     * it sends the event to the controller, which will then edit the model
     * if yes, it adds a new IllegalAction to communicate that an action has already been done
     * @param event is the event received from the client
     */
    public synchronized void handleEvent(LeaderCardActionEvent event){

        if(event.getDiscardOrActivate()=='d')
            controller.discardLeaderCard(event.getIndex());

        if(event.getDiscardOrActivate()=='a')
            controller.activateLeaderCard(event.getIndex());

    }

    /**
     * ActivateProductionEvent from the client
     * checks if an action has already been done:
     * if not, it sends the event to the controller which will then edit the model;
     * if yes, it adds a new IllegalAction to communicate that an action has already been done
     * @param event is the event received from the client
     */
    public synchronized void handleEvent(ActivatedProductionEvent event){
        SameTypeTriple<Resource> BPResources = new SameTypeTriple<>(event.getRequestedResBP1(),event.getRequestedResBP2(),event.getProducedResBP());
        if(!controller.getGame().hasDoneAction()){
            controller.activateProduction(event.getActivatedProduction(),event.isBPActivated(),BPResources,event.getProducedResLC1(),event.getProducedResLC2());
        }else{
            controller.getGame().addIllegalAction(new IllegalAction(controller.getGame().getCurrentPlayer(),"AlreadyDoneAction"));
        }
    }

    /**
     * UseMarketEvent from the client
     * checks if an action has already been done:
     * if not, it sends the event to the controller which will then edit the model;
     * if yes, it adds a new IllegalAction to communicate that an action has already been done
     * @param event is the event received from the client
     */
    public synchronized void handleEvent(UseMarketEvent event){
        if(!controller.getGame().hasDoneAction()){
            controller.useMarket(event.getRowOrColumn(), event.getIndex(),event.getNewWarehouse(),event.getDiscardedRes(),event.getLeaderCardSlots1(),event.getLeaderCardSlots2(), event.getWhiteMarbleChoices());
        }else{
            controller.getGame().addIllegalAction(new IllegalAction(controller.getGame().getCurrentPlayer(),"AlreadyDoneAction"));
        }
    }

    /**
     * boughCardEvent from the client
     * checks if an action has already been done:
     * if not, it adds a new IllegalAction to communicate that an action has already been done;
     * if yes, it sends the event to the controller which will then edit the model;
     * @param event is the event received from the client
     */
    public synchronized void handleEvent(EndTurnEvent event){
        if(controller.getGame().hasDoneAction()){
            controller.getGame().nextTurn();
        }else{ //the player has to do a main action before he can end his turn
            controller.getGame().addIllegalAction(new IllegalAction(controller.getGame().getCurrentPlayer(),"EndTurnWithoutAction"));
        }
    }

    /**
     * NewConnectionEvent from the client
     * @param event is the event received from the client
     */
    public synchronized void handleEvent(NewConnectionEvent event){

    }

    /**
     * InitialChoiceEvent from the client
     * sends the event to the controller which will then edit the model
     * @param event is the event received from the client
     */
    public synchronized void handleEvent(InitialChoiceEvent event){
        controller.initialChoiceHandler(event); //poi magari faccio in un altro modo
    }



    //      ---- SERVER TO CLIENT EVENTS ----

    /**
     * LeaderActionEvent from the server
     * sends the event to every client
     * @param event is the event received from the server
     */
    @Override
    public void handleEvent(LeaderCardActionEventS2C event) {
        //invia evento ai dovuti client
        sendToEveryone(event);
    }

    /**
     * BoughtCardEvent from the server
     * sends the event to every client
     * @param event is the event received from the server
     */
    @Override
    public void handleEvent(BoughtCardEventS2C event) {
        //invia evento ai dovuti client
        sendToEveryone(event);
    }

    /**
     * ActivateProductionEvent from the server
     * sends the event to every client
     * @param event is the event received from the server
     */
    @Override
    public void handleEvent(ActivatedProductionEventS2C event) {
        //invia evento ai dovuti client
        sendToEveryone(event);
    }

    /**
     * IncrementPosition from the server
     * sends the event to every client
     * @param event is the event received from the server
     */
    @Override
    public void handleEvent(IncrementPositionEventS2C event) {
        //invia evento ai dovuti client
        sendToEveryone(event);
    }

    /**
     * VaticanReportEvent from the server
     * sends the event to every client
     * @param event is the event received from the server
     */
    @Override
    public void handleEvent(VaticanReportEventS2C event) {
        //invia evento ai dovuti client
        sendToEveryone(event);
    }

    /**
     * UseMarketEvent from the server
     * sends the event to every client
     * @param event is the event received from the server
     */
    @Override
    public void handleEvent(UseMarketEventS2C event) {
        //invia evento ai dovuti client
        sendToEveryone(event);
    }

    /**
     * NewTurnEvent from the server
     * sends the event to every client
     * @param event is the event received from the server
     */
    @Override
    public void handleEvent(NewTurnEventS2C event) {
        //invia evento ai dovuti client
        sendToEveryone(event);
    }

    /**
     * IllegalActionEvent from the server
     * sends the event to the specific player who has done it
     * @param event is the event received from the server
     */
    @Override
    public void handleEvent(IllegalActionEventS2C event) {
        //invia evento ai dovuti client
        sendTo(event, event.getIllegalAction().getPlayerNickname());
    }

    /**
     * LeaderActionEvent from the server
     * sends the event to the specific player
     * @param event is the event received from the server
     */
    @Override
    public void handleEvent(GameStarterEventS2C event) {
        //invia evento ai dovuti client
        sendTo(event, controller.getGame().getPlayerByIndex(event.getIndexPlayer()).getNickname());
    }

    /**
     * EndGameEvent from the server
     * sends the event to every client
     * @param event is the event received from the server
     */
    @Override
    public void handleEvent(EndGameEventS2C event) {
        //invia evento ai dovuti client
        logger.info("Game ended");
        sendToEveryone(event);
        closeAll();
    }

    /**
     * LorenzoTurnEvent from the server
     * sends the event to every client
     * @param event is the event received from the server
     */
    @Override
    public void handleEvent(LorenzoTurnEventS2C event) {
        //invia evento ai dovuti client
        sendToEveryone(event);
    }

    /**
     * EndPreparationEvent from the server
     * sends the event to every client
     * @param event is the event received from the server
     */
    @Override
    public void handleEvent(EndPreparationEventS2C event) {
        //invia evento ai dovuti client
        sendToEveryone(event);
    }

    /**
     * LeaderActionEvent from the server
     * @param event is the event received from the server
     */
    @Override
    public void handleEvent(NewConnectionEventS2C event) {

    }
}
