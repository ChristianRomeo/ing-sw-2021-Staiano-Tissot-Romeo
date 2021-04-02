package it.polimi.ingsw.model;

import java.util.Map;

/**
 *
 * @see DevelopmentCardBoard
 * @author chris
 */

public class DevelopmentCard {
    private final int id;
    private final CardType type;                       //colore carta - type
    private final int level;
    private final Map<Resource, Integer> cost;
    private final int victoryPoints;
    private final Map<Resource, Integer> requiredResources;
    private final Map<Resource, Integer> producedResources;
    private final int producedFaithPoints;

    public DevelopmentCard(int id, Map<Resource, Integer> requiredResources, Map<Resource, Integer> producedResources,int producedFaithPoints, int victoryPoints, CardType type, int level, Map<Resource, Integer> cost) {
        this.id = id;
        this.type = type;
        this.level = level;
        this.cost = cost;
        this.victoryPoints = victoryPoints;
        this.requiredResources = requiredResources;
        this.producedResources = producedResources;
        this.producedFaithPoints=producedFaithPoints;
    }

    public DevelopmentCard(){
        id=0;
        type=null;
        level=0;
        cost=null;
        victoryPoints=0;
        requiredResources=null;
        producedResources=null;
        producedFaithPoints=0;
    }

    public int getId(){ return id; }

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

    public int getProducedFaithPoints() {return producedFaithPoints;}

    //you give to this method your resources, and it tells you if you have enough resources
    //to buy this card
    public boolean isBuyable(Map<Resource,Integer> ownedResources){
        for(Resource r: cost.keySet()){
            if(!ownedResources.containsKey(r) || ownedResources.get(r)<cost.get(r)){
                return false;
            }
        }
        return true;
    }

}