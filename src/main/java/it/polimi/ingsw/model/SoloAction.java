package it.polimi.ingsw.model;

import java.io.Serializable;

public class SoloAction implements Serializable {
    private final SoloActionType type;
    private final CardType discardedCardsType;

    /**
     * constructor
     * @param type is the type of the Solo action
     * @param discardedCardsType is the type of discarded cards (in case of 'discard two cards' action)
     */
    public SoloAction(SoloActionType type, CardType discardedCardsType) {
        this.type = type;
        this.discardedCardsType = discardedCardsType;
    }

    /**
     * @return the type of the Solo action
     */
    public SoloActionType getType() {
        return type;
    }

    /**
     * @return the type of discarded cards (in case of 'discard two cards' action)
     */
    public CardType getDiscardedCardsType() {
        return discardedCardsType;
    }
}
