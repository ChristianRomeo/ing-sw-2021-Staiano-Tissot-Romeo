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

    /**
     * constructor
     * @param row is the bought card's row
     * @param column is the bought card's column
     * @param pile is the bought card's pile
     */
    public BoughtCardEvent(int row, int column, int pile){
        this.row=row;
        this.column=column;
        this.pile=pile;
    }

    /**
     * notify the eventHandler (which is the virtual view in this case) to handle this specific BoughtCardEvent
     * @param eventHandler is the handler which will handle this specific BoughtCardEvent
     */
    @Override
    public void notifyHandler(ClientEventHandler eventHandler){
        eventHandler.handleEvent(this);
    }

    /**
     * getter of the bought card's row
     * @return the bough card's row
     */
    public int getRow() {
        return row;
    }

    /**
     * getter of the bought card's column
     * @return the bough card's column
     */
    public int getColumn() {
        return column;
    }

    /**
     * getter of the bought card's pile
     * @return the bough card's pile
     */
    public int getPile() {
        return pile;
    }
}
