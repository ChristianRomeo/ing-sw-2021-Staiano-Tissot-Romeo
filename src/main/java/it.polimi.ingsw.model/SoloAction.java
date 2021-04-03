package it.polimi.ingsw.model;

public class SoloAction {
    private final SoloActionType type;
    private final CardType discardedCardsType;

    public SoloAction(SoloActionType type, CardType discardedCardsType) {
        this.type = type;
        this.discardedCardsType = discardedCardsType;
    }

    public SoloActionType getType() {
        return type;
    }

    public CardType getDiscardedCardsType() {
        return discardedCardsType;
    }
}
