package it.polimi.ingsw.model;

import java.util.Map;

/**
 *  This class represents the type to assign at each card read from the json Card file
 * @see DevelopmentCardBoard
 * @author chris
 */

public class DevelopmentCard {
    private final int id;
    private final CardType type;                //colore carta - type
    private final int level;
    private final Map<Resource, Integer> cost;
    private final int victoryPoints;
    private final Map<Resource, Integer> requiredResources;
    private final Map<Resource, Integer> producedResources;
    private final int producedFaithPoints;

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

    /**
     *  you give to this method your resources, and it tells you if you have enough resources to buy this card
     * @param ownedResources a map with all of the player resources
     * @return  true/false
     */
    public boolean isBuyable(Map<Resource,Integer> ownedResources){
        assert cost != null;    //todo testare, se non è un problema, togliere
        for(Resource r: cost.keySet())
            if(!ownedResources.containsKey(r) || ownedResources.get(r) < cost.get(r))
                return false;

        return true;
    }

}