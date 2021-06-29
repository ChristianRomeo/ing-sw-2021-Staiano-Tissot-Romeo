package it.polimi.ingsw.controller.Events;

import java.io.Serializable;

/**
 *client to server event triggered when the player has bought a Development card
 */
public class BoughtCardEvent extends ClientEvent implements Serializable {

    private final int row; // row is the row of the selected card, 0<=row<=2
                    // row 0 is for card of level 1,..., row 2 is for card of level 3

    private final int column; // col is the column of the selected card, 0<=col<=3

    private final int pile; //pile is the number of the pile where you want to insert the bought card, 0<=pile<=2

    public BoughtCardEvent(int row, int column, int pile){
        this.row=row;
        this.column=column;
        this.pile=pile;
    }

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
