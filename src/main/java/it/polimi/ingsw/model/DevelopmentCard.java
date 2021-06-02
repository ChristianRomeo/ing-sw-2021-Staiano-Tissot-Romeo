package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  This class represents the type to assign at each card read from the json Card file
 * @see DevelopmentCardBoard
 * @author chris, enrico
 */

public class DevelopmentCard implements Serializable {
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

    /**
     * getter of the leader card's type
     * @return  the leader card's type
     */
    public CardType getType() {
        return type;
    }

    /**
     * getter of the leader card's level
     * @return  the leader card's level
     */
    public int getLevel() {
        return level;
    }

    /**
     * this method returns the (original) cost of the card
     * @return  the cost of the card
     */

    /**
     * getter of the leader card's cost
     * @return  the leader card's cost
     */
    public Map<Resource, Integer> getCost() {
        if(cost!=null){
            return new HashMap<>(cost);
        }
        return null;
    }
    /**
     * this method returns the cost of the card for a specific player, you have to pass
     * the leader cards he owns.
     * It considers the leader card discount if you have it.
     * @param ownedLeaderCards the player's leader cards
     * @return  the cost of the card for that player
     */
    public Map<Resource, Integer> getCost(List<LeaderCard> ownedLeaderCards) {
        if(cost!=null){
            Map<Resource,Integer> finalCost = getCost();

            for(LeaderCard leaderCard : ownedLeaderCards){
                finalCost= leaderCard.getDiscountedCost(finalCost);
            }
            return finalCost;
        }
        return null;
    }

    /**
     * getter of the leader card's victory points
     * @return  the leader card's victory points
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * getter of the required resources needed to activate the leader card
     * @return  a map containing all the resources needed, with their specific needed amount
     */
    public Map<Resource, Integer> getRequiredResources() {
        if(requiredResources!=null){
            return new HashMap<>(requiredResources);
        }
        return null;
    }

    /**
     * getter of the leader card's produced resources
     * @return  a map containing all the resources produced, with their specific produced amount
     */
    public Map<Resource, Integer> getProducedResources() {
        if(producedResources!=null){
            return new HashMap<>(producedResources);
        }
        return null;
    }

    /**
     * getter of the leader card's produced faith points
     * @return  the leader card's produced faith points
     */
    public int getProducedFaithPoints() {return producedFaithPoints;}

    /**
     *  you give to this method your resources and your leader cards,
     *  and it tells you if you have enough resources to buy this card.
     *  It considers the leader card discount if you have it.
     * @param ownedResources a map with all of the player resources
     * @param ownedLeaderCards a list with the player leader cards
     * @return  true/false
     */
    public boolean isBuyable(Map<Resource,Integer> ownedResources, List<LeaderCard> ownedLeaderCards){
        Map<Resource,Integer> finalCost = getCost(ownedLeaderCards);

        return Resource.enoughResources(ownedResources,finalCost);
    }

}