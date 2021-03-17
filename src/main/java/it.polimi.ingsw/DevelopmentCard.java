package it.polimi.ingsw;

import java.util.Map;

public class DevelopmentCard {
    private CardType type;
    private int level;
    private Map<Resource,Integer> cost;
    private int victoryPoints;
    private Map<Resource,Integer> requiredResources;
    private Map<Resource,Integer> producedResources;
    private int producedFaithPoints;

    public CardType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public Map<Resource, Integer> getCost() {
        return cost;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public Map<Resource, Integer> getRequiredResources() {
        return requiredResources;
    }

    public Map<Resource, Integer> getProducedResources() {
        return producedResources;
    }

    public int getProducedFaithPoints() {
        return producedFaithPoints;
    }
}
