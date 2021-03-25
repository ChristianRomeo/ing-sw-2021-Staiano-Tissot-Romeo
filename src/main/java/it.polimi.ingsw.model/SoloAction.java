package it.polimi.ingsw.model;

public class SoloAction {
    private SoloActionType type;
    private CardType discardedCardsType;

    public SoloActionType getType() {
        return type;
    }

    public CardType getDiscardedCardsType() {
        return discardedCardsType;
    }
}
