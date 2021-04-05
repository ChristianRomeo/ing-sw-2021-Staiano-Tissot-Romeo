package it.polimi.ingsw.model;

import java.util.Map;

public abstract class LeaderCard {

    protected int id;
    protected int victoryPoints;
    protected Resource discountedResource;
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

    public Resource getDiscountedResource(){ return  discountedResource; }

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

}
