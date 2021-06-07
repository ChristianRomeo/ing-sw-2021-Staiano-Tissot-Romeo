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
    private final List<String> disconnectedClients = new ArrayList<>();
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

    public void sendToEveryone(ServerEvent serverEvent){
        for (ClientHandler clientHandler : clientHandlers)
            if(clientHandler.isConnected())
                clientHandler.send(serverEvent);
    }

    //gli dai il nickname di chi non deve ricevere il messaggio, quando dobbiamo mandare informazioni a tutti gli altri tranne il giocatore corrente che in teoria l'ha già, per risparmiare
    public void sendToEveryoneExcept(ServerEvent serverEvent, String nickname){
        for (ClientHandler clientHandler : clientHandlers)
            if(clientHandler.isConnected() && !clientHandler.getNickname().equals(nickname))
                clientHandler.send(serverEvent);
    }

    //invia evento a solo un client
    public void sendTo(ServerEvent serverEvent, String nickname){
        for (ClientHandler clientHandler : clientHandlers)
            if(clientHandler.isConnected() && clientHandler.getNickname().equals(nickname)){
                clientHandler.send(serverEvent);
                return;
            }
    }

    //per la disconnessione , DEPRECATED
    public synchronized void setDisconnected(ClientHandler client){
        //notify controller that player has been disconnected
        disconnectedClients.add(client.getNickname());
        clientHandlers.remove(client);
    }

    //per la disconnessione , DEPRECATED
    public synchronized List<String> getDisconnectedClients() {
        return new ArrayList<>(disconnectedClients);
    }

    public boolean checkGameStatus() {
        /* try {
            status = controller.isRunning();
        } catch (DisconnectionException e) {
            status = false;
        }*/
        return true;
    }

    public synchronized void closeAll() {
        for (ClientHandler clientHandler : clientHandlers)
            if (clientHandler.isConnected())
                clientHandler.closeSocket();
    }

    // ---- events from the client----

    public synchronized void handleEvent(BoughtCardEvent event){

        if(!controller.getGame().hasDoneAction()){
            controller.buyDevelopmentCard(event.getRow(), event.getColumn(), event.getPile());
        }else{
            controller.getGame().addIllegalAction(new IllegalAction(controller.getGame().getCurrentPlayer(),"AlreadyDoneAction"));
        }
    }

    public synchronized void handleEvent(NumPlayerEvent event) {
        logger.info("ricevuto numero di giocatori: "+ event.getNumPlayers());
        controller.getGame().setWantedNumPlayers(event.getNumPlayers());
    }

    public synchronized void handleEvent(LeaderCardActionEvent event){

        if(event.getDiscardOrActivate()=='d')
            controller.discardLeaderCard(event.getIndex());

        if(event.getDiscardOrActivate()=='a')
            controller.activateLeaderCard(event.getIndex());

    }

    public synchronized void handleEvent(ActivatedProductionEvent event){
        SameTypeTriple<Resource> BPResources = new SameTypeTriple<>(event.getRequestedResBP1(),event.getRequestedResBP2(),event.getProducedResBP());
        if(!controller.getGame().hasDoneAction()){
            controller.activateProduction(event.getActivatedProduction(),event.isBPActivated(),BPResources,event.getProducedResLC1(),event.getProducedResLC2());
        }else{
            controller.getGame().addIllegalAction(new IllegalAction(controller.getGame().getCurrentPlayer(),"AlreadyDoneAction"));
        }
    }

    public synchronized void handleEvent(UseMarketEvent event){
        if(!controller.getGame().hasDoneAction()){
            controller.useMarket(event.getRowOrColumn(), event.getIndex(),event.getNewWarehouse(),event.getDiscardedRes(),event.getLeaderCardSlots1(),event.getLeaderCardSlots2(), event.getWhiteMarbleChoices());
        }else{
            controller.getGame().addIllegalAction(new IllegalAction(controller.getGame().getCurrentPlayer(),"AlreadyDoneAction"));
        }
    }

    public synchronized void handleEvent(EndTurnEvent event){
        if(controller.getGame().hasDoneAction()){
            controller.getGame().nextTurn();
        }else{ //the player has to do a main action before he can end his turn
            controller.getGame().addIllegalAction(new IllegalAction(controller.getGame().getCurrentPlayer(),"EndTurnWithoutAction"));
        }
    }

    public synchronized void handleEvent(NewConnectionEvent event){

    }

    public synchronized void handleEvent(InitialChoiceEvent event){
        controller.initialChoiceHandler(event); //poi magari faccio in un altro modo
    }



    //      ---- SERVER TO CLIENT EVENTS ----

    @Override
    public void handleEvent(LeaderCardActionEventS2C event) {
        //invia evento ai dovuti client
        sendToEveryone(event);
    }

    @Override
    public void handleEvent(BoughtCardEventS2C event) {
        //invia evento ai dovuti client
        sendToEveryone(event);
    }

    @Override
    public void handleEvent(ActivatedProductionEventS2C event) {
        //invia evento ai dovuti client
        sendToEveryone(event);
    }

    @Override
    public void handleEvent(IncrementPositionEventS2C event) {
        //invia evento ai dovuti client
        sendToEveryone(event);
    }

    @Override
    public void handleEvent(VaticanReportEventS2C event) {
        //invia evento ai dovuti client
        sendToEveryone(event);
    }

    @Override
    public void handleEvent(UseMarketEventS2C event) {
        //invia evento ai dovuti client
        sendToEveryone(event);
    }

    @Override
    public void handleEvent(NewTurnEventS2C event) {
        //invia evento ai dovuti client
        sendToEveryone(event);
    }

    @Override
    public void handleEvent(IllegalActionEventS2C event) {
        //invia evento ai dovuti client
        sendTo(event, event.getIllegalAction().getPlayerNickname());
    }

    @Override
    public void handleEvent(GameStarterEventS2C event) {
        //invia evento ai dovuti client
        sendTo(event, controller.getGame().getPlayerByIndex(event.getIndexPlayer()).getNickname());
    }

    @Override
    public void handleEvent(EndGameEventS2C event) {
        //invia evento ai dovuti client
        logger.info("Game ended");
        sendToEveryone(event);
        //todo: qua forse dobbiamo chiudere i client handler di sta partita.
    }

    @Override
    public void handleEvent(LorenzoTurnEventS2C event) {
        //invia evento ai dovuti client
        sendToEveryone(event);
    }

    @Override
    public void handleEvent(EndPreparationEventS2C event) {
        //invia evento ai dovuti client
        sendToEveryone(event);
    }

    @Override
    public void handleEvent(NewConnectionEventS2C event) {

    }
}
