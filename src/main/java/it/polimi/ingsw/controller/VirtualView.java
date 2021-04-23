package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.Events.*;
import it.polimi.ingsw.model.IllegalAction;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.SameTypeTriple;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class VirtualView implements ClientEventHandler, ServerEventObserver {
    private final List<ClientHandler> clientHandlers;
    private final Controller controller;
    private final static Logger logger = Logger.getLogger(Server.class.getName());


    public VirtualView(Controller controller) {
        this.clientHandlers = new ArrayList<>();
        this.controller = controller;
    }


    public void addClientHandler(ClientHandler clientHandler) {
        synchronized (clientHandlers) {
            int idx=0;
            for (ClientHandler cl : clientHandlers)
                while (cl.getNickname().equalsIgnoreCase(clientHandler.getNickname()))
                    clientHandler.setNickname(clientHandler.getNickname() + "_" + idx++);
            clientHandlers.add(clientHandler);
        }
    }

    public void sendToEveryone(){
        for (ClientHandler clientHandler : clientHandlers) {
        //if client connected send message
        }
    }

    public void sendToEveryoneExcept(){
        //quando dobbiamo mandare informazioni a tutti gli altri tranne il giocatore corrente che in teoria l'ha già, per risparmiare
    }

    public void setUpGame(){
        //setnickname and if già usato chiama setnewnickname che lo richiede o lo incrementa, setnumplayers
        //controller.wakeUpController();
    }

    public void setChosenLeaderCards(){
        //controller.setLeaderCards(choice, nickname)
    }

    public void setFirstPlayer(){
        //controller.setFirtPlayer(nickname)
    }

    public void setAction(){
        //notify controller that player has chosen an action
    }
    public void setDisconnected(String nickname){
        //notify controller that player has been disconnected
    }

    public boolean checkGameStatus() {
        boolean status =true; //togliere inizializzazione
       /* try {
            status = controller.isRunning();
        } catch (DisconnectionException e) {
            status = false;
        }*/
        return status;
    }

    public void closeAll() {
        for (ClientHandler clientHandler : clientHandlers)
            if (clientHandler.isConnected())
                clientHandler.setDisconnected();
    }

    public void handleEvent(BoughtCardEvent event){

        logger.info("compra carta"); //per debug
        if(!controller.getGame().hasDoneAction())
            controller.buyDevelopmentCard(event.getRow(), event.getColumn(), event.getPile());

    }

    public void handleEvent(LeaderCardActionEvent event){

        if(event.getDiscardOrActivate()=='d'){
            controller.discardLeaderCard(event.getIndex());
        }
        if(event.getDiscardOrActivate()=='a'){
            controller.activateLeaderCard(event.getIndex());
        }
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

    //      ---- SERVER TO CLIENT EVENTS ----

    @Override
    public void handleEvent(LeaderCardActionEventS2C event) {
        //invia evento ai dovuti client
    }

    @Override
    public void handleEvent(BoughtCardEventS2C event) {
        //invia evento ai dovuti client
    }
}
