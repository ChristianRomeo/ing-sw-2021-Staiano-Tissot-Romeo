package it.polimi.ingsw.model;

public class LeaderCardSlots extends LeaderCard{
    private int fullSlotsNumber;


    public Integer getFullSlotsNumber() {
        if(isActivated){
            return fullSlotsNumber;
        }
        return null;
    }

    public void addResource() {
        if(isActivated){
            if(fullSlotsNumber<=1){
                fullSlotsNumber++;
            }
        }
    }

    public void removeResource() {
        if(isActivated){
            if(fullSlotsNumber>=1){
                fullSlotsNumber--;
            }
        }
    }

    public void setFullSlotsNumber(int n){
        if(isActivated){
            if(n>=0 && n<=2){
                fullSlotsNumber=n;
            }
        }
    }
}
