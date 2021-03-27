package it.polimi.ingsw.model;

import java.util.Map;

/**
 *
 * @see DevelopmentCardBoard
 */

public class DevelopmentCard {
    private final int id;
    private String il;
    private final CardType type;                      //colore carta - type
    private final int level;                          //level
    private final Map<Resource, Integer>[] cost;      //needs
    private final int victoryPoints;                  //victoryPoints
    private final Map<Resource, Integer>[] requiredResources;   //toGive
    private final Map<Resource, Integer>[] producedResources;   //toHave
    //private int producedFaithPoints;            //??

    public DevelopmentCard(int id, Map<Resource, Integer>[] requiredResources, Map<Resource, Integer>[] producedResources, int victoryPoints, CardType type, int level, Map<Resource, Integer>[] cost) {
        this.id = id;
        this.type = type;
        this.level = level;
        this.cost = cost;
        this.victoryPoints = victoryPoints;
        this.requiredResources = requiredResources;
        this.producedResources = producedResources;
    }

    public int getId(){ return id; }

    public String getIl(){ return il; }

    public CardType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public Map<Resource, Integer>[] getCost() {
        return cost;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public Map<Resource, Integer>[] getRequiredResources() { return requiredResources; }

    public Map<Resource, Integer>[] getProducedResources() {
        return producedResources;
    }

    //public int getProducedFaithPoints() {return producedFaithPoints;}

}