package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.SameTypeTriple;
import it.polimi.ingsw.model.modelExceptions.IllegalMarketUseException;

import java.util.ArrayList;
import java.util.List;

public class VirtualView implements ClientEventHandler {
    private final List<ClientHandler> clientHandlers;
    private final Controller controller;


    public VirtualView(Controller controller) {
        this.clientHandlers = new ArrayList<>();
        this.controller = controller;
    }


    public void addClientHandler(ClientHandler clientHandler) {
        synchronized (clientHandlers) {
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

    public void setNewNickname(){
        //se è già usato allora fai _1 o richiedi
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
        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler.isConnected()) {
                clientHandler.setDisconnected();
            }
        }
    }

    public void handleEvent(BoughtCardEvent event){
        //chiama metodo del controller perchè è stata comprata una carta
        try{
            System.out.println("compra carta"); //per debug
            controller.buyDevelopmentCard(event.getRow(), event.getColumn(), event.getPile());
        }catch (Exception e){
            //qui deve essere messo nel model che c'è stato un errore
            //o sto controllo lo posso fa direttamente nel controller
        }
    }

    public void handleEvent(LeaderCardActionEvent event){
        //chiama metodo del controller perchè è stata attivata/scartata una carta leader
        try{
            if(event.getDiscardOrActivate()=='d'){
                controller.discardLeaderCard(event.getIndex());
            }
            if(event.getDiscardOrActivate()=='a'){
                controller.activateLeaderCard(event.getIndex());
            }
        }catch (IllegalArgumentException e){
            //qui deve essere messo nel model che c'è stato un errore
            //o sto controllo lo posso fa direttamente nel controller
        }
    }
    public void handleEvent(ActivatedProductionEvent event){
        SameTypeTriple<Resource> BPResources = new SameTypeTriple<>(event.getRequestedResBP1(),event.getRequestedResBP2(),event.getProducedResBP());
        try{
            controller.activateProduction(event.getActivatedProduction(),event.isBPActivated(),BPResources,event.getProducedResLC1(),event.getProducedResLC2());
        }catch (Exception e){
            //qui deve essere messo nel model che c'è stato un errore
            //o sto controllo lo posso fa direttamente nel controller
        }
    }
    public void handleEvent(UseMarketEvent event){
        try {
            controller.useMarket(event.getRowOrColumn(), event.getIndex(),event.getNewWarehouse(),event.getDiscardedRes(),event.getLeaderCardSlots1(),event.getLeaderCardSlots2());
        } catch (IllegalMarketUseException e) {
            //qui deve essere messo nel model che sta un error, o posso controllare dirett nel controller
        }
    }

    public void handleEvent(EndTurnEvent event){
        controller.getGame().nextTurn();
    }
}
