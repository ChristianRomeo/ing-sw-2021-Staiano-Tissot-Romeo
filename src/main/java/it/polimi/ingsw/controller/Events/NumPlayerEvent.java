package it.polimi.ingsw.controller.Events;

//evento che dice il num di giocatori della partita scelto dal primo client che si connette

import java.io.Serializable;

public class NumPlayerEvent extends ClientEvent implements Serializable {
    private final int numPlayers;

    public int getNumPlayers() {
        return numPlayers;
    }

    public NumPlayerEvent(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    @Override
    public void notifyHandler(ClientEventHandler eventHandler){
        eventHandler.handleEvent(this);
    }
}
