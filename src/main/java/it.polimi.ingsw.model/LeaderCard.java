package it.polimi.ingsw.model;

import java.util.Map;

public abstract class LeaderCard {
    private Map<Resource,Integer> requiredResources;
    private Map<CardType,Integer> requiredCards;
    private Map<CardType,Integer> requiredCardsWithLevel;
    private int victoryPoints;
    private boolean isDiscarded;
    private boolean isActivated;

    public Map<Resource, Integer> getRequiredResources() {
        return requiredResources;
    }

    public Map<CardType, Integer> getRequiredCards() {
        return requiredCards;
    }

    public Map<CardType, Integer> getRequiredCardsWithLevel() {
        return requiredCardsWithLevel;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public boolean isDiscarded() {
        return isDiscarded;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void discard() {

    }
    public void activate() {

    }

}
