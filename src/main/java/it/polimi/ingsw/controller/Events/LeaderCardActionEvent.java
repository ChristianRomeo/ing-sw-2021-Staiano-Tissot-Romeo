package it.polimi.ingsw.controller.Events;

/**
 *client to server event triggered when the player has activated/discarded a Leader card
 */
public class LeaderCardActionEvent extends ClientEvent {
    //qua ci vogliono gli attributi dell'evento specifico
    private final char discardOrActivate; //'d' to discard, 'a' to activate
    private final int index; //0 or 1, the index of the leader card you want to activate

    /**
    *getter of the chosen action (activate - "a", discard - "d")
     * @return the chosen action
     */
    public char getDiscardOrActivate() {
        return discardOrActivate;
    }

    /**
     * getter of the Leader card's index
     * @return the Leader card's index
     */
    public int getIndex() {
        return index;
    }

    /**
     * constructor
     */
    public LeaderCardActionEvent(char discardOrActivate, int index) {
        this.discardOrActivate = discardOrActivate;
        this.index = index;
    }

    /**
     * notify the eventHandler (which is the virtual view in this case) to handle this specific LeaderCardActionEvent
     * @param eventHandler is the handler which will handle this specific LeaderCardActionEvent
     */
    @Override
    public void notifyHandler(ClientEventHandler eventHandler){
        eventHandler.handleEvent(this);
    }
}
