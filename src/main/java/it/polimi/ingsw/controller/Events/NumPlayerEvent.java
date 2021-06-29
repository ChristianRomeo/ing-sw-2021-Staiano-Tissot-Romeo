package it.polimi.ingsw.controller.Events;

//evento che dice il num di giocatori della partita scelto dal primo client che si connette

/**
 *client to server event triggered when the first connected client chooses the number of players for the match
 */
public class NumPlayerEvent extends ClientEvent  {
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
