package it.polimi.ingsw.controller.Events;

//the client has activated or discarded a leader card
public class LeaderCardActionEvent extends ClientEvent {
    //qua ci vogliono gli attributi dell'evento specifico
    private final char discardOrActivate; //'d' to discard, 'a' to activate
    private final int index; //0 or 1, the index of the leader card you want to activate

    public char getDiscardOrActivate() {
        return discardOrActivate;
    }

    public int getIndex() {
        return index;
    }

    public LeaderCardActionEvent(char discardOrActivate, int index) {
        this.discardOrActivate = discardOrActivate;
        this.index = index;
    }

    @Override
    public void notifyHandler(ClientEventHandler eventHandler){
        eventHandler.handleEvent(this);
    }
}
