package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.Map;

public abstract class LeaderCard implements Serializable {

    protected int id;
    protected LeaderCardType ability;
    protected int victoryPoints;
    protected Resource abilityResource; //this is the resource used for the ability of the card,
                                        // for example it's the discounted resource, or the resource type
                                        //of the slots.
    protected Map<CardType, Integer> requiredCards;
    protected Map<Resource, Integer> requiredResources;

    protected boolean isDiscarded=false;
    protected boolean isActivated=false;

    /**
     * @return a Map containing the required cards of a Leader card
     */
    public Map<CardType, Integer> getRequiredCards(){
        return requiredCards;
    }

    /**
     * @return a Map containing the required resources of a Leader card
     */
    public Map<Resource, Integer> getRequiredResources(){
        return requiredResources;
    }

    /**
     * @return a Leader Card's ID
     */
    public int getId(){ return id; }

    public Resource getAbilityResource(){ return  abilityResource; }

    /**
     * @return a Leader card's Victory Points
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * @return true if the Leader Card has been discarded, false otherwise
     */
    public boolean isDiscarded() {
        return isDiscarded;
    }

    /**
     * @return true if the Leader Card has been activated, false otherwise
     */
    public boolean isActivated() {
        return isActivated;
    }

    /**
     * sets a Leader Card's status to discarded
     */
    public void discard() {
        this.isDiscarded=true;
    }

    /**
     * sets a Leader Card's status to activated
     */
    public void activate() {
        this.isActivated=true;
    }

    /**
     * @return the LeaderCardType, to see which special ability the Leader Card has
     */
    public LeaderCardType getAbility(){
        return ability;
    }

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
