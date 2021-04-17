package it.polimi.ingsw.controller;

//the client has bought a development card
public class BoughtCardEvent extends ClientEvent{

    private int row; // row is the row of the selected card, 0<=row<=2
                    // row 0 is for card of level 1,..., row 2 is for card of level 3

    private int column; // col is the column of the selected card, 0<=col<=3

    private int pile; //pile is the number of the pile where you want to insert the bought card, 0<=pile<=2


    @Override
    public void notifyHandler(ClientEventHandler eventHandler){
        eventHandler.handleEvent(this);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPile() {
        return pile;
    }
}
