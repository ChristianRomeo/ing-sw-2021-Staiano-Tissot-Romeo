package it.polimi.ingsw.controller;

import java.util.ArrayList;
import java.util.List;

public class VirtualView {
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
}
