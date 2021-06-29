package it.polimi.ingsw.controller.Events;

import it.polimi.ingsw.model.PlayerWarehouse;
import it.polimi.ingsw.model.Resource;

import java.util.List;
import java.util.Map;

/**
 *client to server event triggered when the player wants to use the market
 */
public class UseMarketEvent extends ClientEvent{

    private final char rowOrColumn; // 'c' for column, 'r' for row
    private final int index; //index of the row /column of the market
    private final PlayerWarehouse newWarehouse; //warehouse of the player after the insertion of the new resources
    private final Map<Resource,Integer> discardedRes;//discarded resources by the player
    private final int leaderCardSlots1; //number of fulls slots of the first leader card, after the bought at the market
    private final int leaderCardSlots2;//number of fulls slots of the second leader card, after the bought at the market
    private final List<Integer> whiteMarbleChoices; //the leader cards chosen for the white marbles (in case you have two white marble cards)

    public UseMarketEvent(char rowOrColumn, int index, PlayerWarehouse newWarehouse, Map<Resource, Integer> discardedRes, int leaderCardSlots1, int leaderCardSlots2, List<Integer> whiteMarbleChoices) {
        this.rowOrColumn = rowOrColumn;
        this.index = index;
        this.newWarehouse = newWarehouse;
        this.discardedRes = discardedRes;
        this.leaderCardSlots1 = leaderCardSlots1;
        this.leaderCardSlots2 = leaderCardSlots2;
        this.whiteMarbleChoices = whiteMarbleChoices;
    }

    public char getRowOrColumn() {
        return rowOrColumn;
    }

    public int getIndex() {
        return index;
    }

    public PlayerWarehouse getNewWarehouse() {
        return newWarehouse;
    }

    public Map<Resource, Integer> getDiscardedRes() {
        return discardedRes;
    }

    public int getLeaderCardSlots1() {
        return leaderCardSlots1;
    }

    public int getLeaderCardSlots2() {
        return leaderCardSlots2;
    }

    public List<Integer> getWhiteMarbleChoices() {
        return whiteMarbleChoices;
    }

    @Override
    public void notifyHandler(ClientEventHandler eventHandler){
        eventHandler.handleEvent(this);
    }
}
