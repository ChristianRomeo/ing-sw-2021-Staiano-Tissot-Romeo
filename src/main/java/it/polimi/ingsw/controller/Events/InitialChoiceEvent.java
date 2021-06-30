package it.polimi.ingsw.controller.Events;

import it.polimi.ingsw.model.*;
/**
 *client to server event triggered for the initial choice of Leader cards to be removed and resources to be put in the warehouse
 */
public class InitialChoiceEvent extends ClientEvent {

    //mi faccio dare gli indici delle carte leader che vuole RIMUOVERE
    private final int indexLeader1; //da 0 a 3
    private final int indexLeader2;

    private final Resource resource1;
    private final Resource resource2;

    private final SameTypePair<Integer> resPosition1; //posizioni del warehouse in cui lui vuole mettere la res
    private final SameTypePair<Integer> resPosition2;

    /**
     *constructor
     */
    public InitialChoiceEvent(int indexLeader1, int indexLeader2, Resource resource1, Resource resource2, SameTypePair<Integer> resPosition1, SameTypePair<Integer> resPosition2) {
        this.indexLeader1 = indexLeader1;
        this.indexLeader2 = indexLeader2;
        this.resource1 = resource1;
        this.resource2 = resource2;
        this.resPosition1 = resPosition1;
        this.resPosition2 = resPosition2;
    }

    /**
     * getter of the first resource to be chosen
     * @return the type of resource chosen
     */
    public Resource getResource1() {
        return resource1;
    }

    /**
     * getter of the second resource to be chosen
     * @return the type of resource chosen
     */
    public Resource getResource2() {
        return resource2;
    }

    /**
     * getter of the first resource position in the warehouse
     * @return the position in the warehouse
     */
    public SameTypePair<Integer> getResPosition1() {
        return resPosition1;
    }

    /**
     * getter of the second resource position in the warehouse
     * @return the position in the warehouse
     */
    public SameTypePair<Integer> getResPosition2() {
        return resPosition2;
    }

    /**
     * getter of the first Leader card index to be removed
     * @return the index of the card to be removed
     */
    public int getIndexLeader1() {
        return indexLeader1;
    }

    /**
     * getter of the second Leader card index to be removed
     * @return the index of the card to be removed
     */
    public int getIndexLeader2() {
        return indexLeader2;
    }

    /**
     * notify the eventHandler (which is the virtual view in this case) to handle this specific InitialChoiceEvent
     * @param eventHandler is the handler which will handle this specific InitialChoiceEvent
     */
    @Override
    public void notifyHandler(ClientEventHandler eventHandler){
        eventHandler.handleEvent(this);
    }
}
