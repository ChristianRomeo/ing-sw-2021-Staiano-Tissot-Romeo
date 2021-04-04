package it.polimi.ingsw.model;

import java.util.HashMap;
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
        if(cost!=null){
            return new HashMap<>(cost);
        }
        return null;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public Map<Resource, Integer> getRequiredResources() {
        if(requiredResources!=null){
            return new HashMap<>(requiredResources);
        }
        return null;
    }

    public Map<Resource, Integer> getProducedResources() {
        if(producedResources!=null){
            return new HashMap<>(producedResources);
        }
        return null;
    }

    public int getProducedFaithPoints() {return producedFaithPoints;}

    /**
     *  you give to this method your resources, and it tells you if you have enough resources to buy this card
     * @param ownedResources a map with all of the player resources
     * @return  true/false
     */
    public boolean isBuyable(Map<Resource,Integer> ownedResources){
        return Resource.enoughResources(ownedResources,cost);
    }

}