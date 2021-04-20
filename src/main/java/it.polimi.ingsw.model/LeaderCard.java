package it.polimi.ingsw.model;

import java.util.Map;

public abstract class LeaderCard {

    protected int id;
    protected LeaderCardType ability;
    protected int victoryPoints;
    protected Resource abilityResource; //this is the resource used for the ability of the card,
                                        // for example it's the discounted resource, or the resource type
                                        //of the slots.
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

    /**
     * this method is for the production card ability, it's redefined in that type of card,
     * in the other types of cards it doesn't do anything but returns the argument you pass.
     * In the production card type, it returns the updated required resources for the production.
     */
    public Map<Resource,Integer> getTotalRequiredResources(Map<Resource,Integer> oldRequiredResources){
        return oldRequiredResources;
    }
    /**
     * this method is for the production card ability, it's redefined in that type of card,
     * in the other types of cards it doesn't do anything but returns the argument you pass.
     * In the production card type, it returns the updated produced resources of the production.
     */
    public Map<Resource,Integer> getTotalProducedResources(Map<Resource,Integer> oldProducedResources, Resource addedResource){
        return oldProducedResources;
    }
    /**
     * this method is for the production card ability, it's redefined in that type of card,
     * in the other types of cards it doesn't do anything but returns the argument you pass.
     * In the production card type, it returns the updated produced faith points of the production.
     */
    public int getTotalProducedFP(int oldProducedFP){
        return oldProducedFP;
    }

    /**
     * this method is for the slots card ability, it's redefined in that type of card,
     * in the other types of cards it doesn't do anything but returns null.
     * In the slots card type, it returns the number of full slots in the card (0,1,2).
     */
    public Integer getFullSlotsNumber() {
        return null;
    }
    /**
     * this method is for the slots card ability, it's redefined in that type of card,
     * in the other types of cards it doesn't do anything.
     * In the slots card type, it adds (if there is space) a Resource in a slot.
     */
    public void addResource() {}
    /**
     * this method is for the slots card ability, it's redefined in that type of card,
     * in the other types of cards it doesn't do anything.
     * In the slots card type, it removes a Resource (if there is one) from a slot.
     */
    public void removeResource() {}
    /**
     * this method is for the slots card ability, it's redefined in that type of card,
     * in the other types of cards it doesn't do anything.
     * In the slots card type, it set to n the number of full slots in that card .
     */
    public void setFullSlotsNumber(int n){}
}
