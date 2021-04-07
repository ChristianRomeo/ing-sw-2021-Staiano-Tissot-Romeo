package it.polimi.ingsw.model;

import java.util.Map;

public abstract class LeaderCard {

    protected int id;
    protected LeaderCardType ability;
    protected int victoryPoints;
    protected Resource abilityResource;
    protected Map<CardType, Integer> requiredCards;
    protected Map<Resource, Integer> requiredResources;

    protected boolean isDiscarded;
    protected boolean isActivated;

    public Map<CardType, Integer> getRequiredCards(){
        return requiredCards;
    }

    public Map<Resource, Integer> getRequiredResources(){
        return requiredResources;
    }

    public int getId(){ return id; }

    public Resource getAbilityResource(){ return  abilityResource; }

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
        this.isDiscarded=true;
    }

    public void activate() {
        this.isActivated=true;
    }

    public LeaderCardType getAbility(){
        return ability;
    }

    //this method is the discounted cost card ability, it's redefined in that type of card,
    //in the other types of cards it doesn't do anything but returns the original cost.

    /**
     * this method is for the discounted cost card ability, it's redefined in that type of card,
     * in the other types of cards it doesn't do anything but returns the original cost.
     * In the discounted cost card type, it changes the original cost and returns the discounted cost.
     */
    public Map<Resource,Integer> getDiscountedCost(Map<Resource,Integer> originalCost){
        return originalCost;
    }
    /**
     * this method is for the white marble card ability, it's redefined in that type of card,
     * in the other types of cards it doesn't do anything but returns null.
     * In the white marble card type, it returns the Resource to gain from the white marble.
     */
    public Resource getWhiteMarbleResource(){
        return null;
    }

}
