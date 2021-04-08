package it.polimi.ingsw.model;

public class LeaderCardSlots extends LeaderCard{
    private int fullSlotsNumber;


    public Integer getFullSlotsNumber() {
        return fullSlotsNumber;
    }

    public void addResource() {
        if(fullSlotsNumber<=1){
            fullSlotsNumber++;
        }
    }

    public void removeResource() {
        if(fullSlotsNumber>=1){
            fullSlotsNumber--;
        }
    }
}
