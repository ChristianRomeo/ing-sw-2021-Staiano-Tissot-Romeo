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
    private List<String> disconnectedClients;
    private final Controller controller;
    private final static Logger logger = Logger.getLogger(Server.class.getName());


    public VirtualView(Controller controller) {
        this.clientHandlers = new ArrayList<>();
        this.controller = controller;
    }

    public Controller getController(){
        return controller;
    }

    public List<ClientHandler> getClientHandlers(){ return  clientHandlers;}

    public void addClientHandler(ClientHandler clientHandler) {
        synchronized (clientHandlers) {
            clientHandlers.add(clientHandler);
            controller.getGame().addNewPlayer(new Player(clientHandler.getNickname())); //copia aggiunta utente in game e creazione giocatore
        }
    }

    public void sendToEveryone(ServerEvent serverEvent){
        for (ClientHandler clientHandler : clientHandlers) {
        //if client connected send message
            if(clientHandler.isConnected())
                clientHandler.send(serverEvent);
        }
    }

    //gli dai il nickname di chi non deve ricevere il messaggio
    public void sendToEveryoneExcept(ServerEvent serverEvent, String nickname){
        //quando dobbiamo mandare informazioni a tutti gli altri tranne il giocatore corrente che in teoria l'ha già, per risparmiare
        for (ClientHandler clientHandler : clientHandlers) {
            if(clientHandler.isConnected() && !clientHandler.getNickname().equals(nickname)){
                clientHandler.send(serverEvent);
            }
        }
    }

    //invia evento a solo un client
    public void sendTo(ServerEvent serverEvent, String nickname){
        for (ClientHandler clientHandler : clientHandlers) {
            if(clientHandler.isConnected() && clientHandler.getNickname().equals(nickname)){
                clientHandler.send(serverEvent);
                return;
            }
        }
    }

    /*public void setUpGame(){
        //setnickname and if già usato chiama setnewnickname che lo richiede o lo incrementa, setnumplayers
        //controller.wakeUpController();
    }*/

    public void setChosenLeaderCards(){
        //controller.setLeaderCards(choice, nickname)
    }

    public void setFirstPlayer(){
        //controller.setFirstPlayer(nickname)
    }

    public void setAction(){
        //notify controller that player has chosen an action
    }
    public void setDisconnected(ClientHandler client){
        //notify controller that player has been disconnected
        disconnectedClients.add(client.getNickname());
        clientHandlers.remove(client);
    }

    public List<String> getDisconnectedClients() {
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

    public void closeAll() {
        for (ClientHandler clientHandler : clientHandlers)
            if (clientHandler.isConnected())
                clientHandler.setDisconnected();
    }

    // ---- events from the client----

    public void handleEvent(BoughtCardEvent event){

        logger.info("compra carta"); //per debug
        if(!controller.getGame().hasDoneAction()){
            logger.info("compra carta"); //per debug
            controller.buyDevelopmentCard(event.getRow(), event.getColumn(), event.getPile());
        }
    }

    public void handleEvent(NumPlayerEvent event) {
        logger.info("ricevuto numero di giocatori: "+ event.getNumPlayers());
        controller.getGame().setWantedNumPlayers(event.getNumPlayers());
    }

    public void handleEvent(LeaderCardActionEvent event){

        if(event.getDiscardOrActivate()=='d')
            controller.discardLeaderCard(event.getIndex());

        if(event.getDiscardOrActivate()=='a')
            controller.activateLeaderCard(event.getIndex());

    }

    public void handleEvent(ActivatedProductionEvent event){
        SameTypeTriple<Resource> BPResources = new SameTypeTriple<>(event.getRequestedResBP1(),event.getRequestedResBP2(),event.getProducedResBP());
        if(!controller.getGame().hasDoneAction()){
            controller.activateProduction(event.getActivatedProduction(),event.isBPActivated(),BPResources,event.getProducedResLC1(),event.getProducedResLC2());
        }
    }

    public void handleEvent(UseMarketEvent event){
        if(!controller.getGame().hasDoneAction()){
            controller.useMarket(event.getRowOrColumn(), event.getIndex(),event.getNewWarehouse(),event.getDiscardedRes(),event.getLeaderCardSlots1(),event.getLeaderCardSlots2());
        }
    }

    public void handleEvent(EndTurnEvent event){
        if(controller.getGame().hasDoneAction()){
            controller.getGame().nextTurn();
        }else{ //the player has to do a main action before he can end his turn
            controller.getGame().addIllegalAction(new IllegalAction(controller.getGame().getCurrentPlayer(),"IllegalAction"));
        }
    }

    public void handleEvent(NewConnectionEvent event){

    }

    public void handleEvent(InitialChoiceEvent event){

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
}
