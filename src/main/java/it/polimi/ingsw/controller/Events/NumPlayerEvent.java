package it.polimi.ingsw.controller.Events;

//evento che dice il num di giocatori della partita scelto dal primo client che si connette

/**
 *client to server event triggered when the first connected client chooses the number of players for the game
 */
public class NumPlayerEvent extends ClientEvent  {
    private final int numPlayers;

    /**
     * getter of the chosen number of players for the game
     * @return
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     * constructor
     */
    public NumPlayerEvent(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    /**
     * notify the eventHandler (which is the virtual view in this case) to handle this specific NewConnectionEvent
     * @param eventHandler is the handler which will handle this specific NewConnectionEvent
     */
    @Override
    public void notifyHandler(ClientEventHandler eventHandler){
        eventHandler.handleEvent(this);
    }
}
