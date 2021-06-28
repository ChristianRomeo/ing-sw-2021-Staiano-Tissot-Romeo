package it.polimi.ingsw.model;

/**
 * LeaderCardSlots class represents the Leader cards with special ability "Slots" which gives 2 special slots in which to put
 * resources
 */
public class LeaderCardSlots extends LeaderCard{
    private int fullSlotsNumber=0;

    /**
     * this method is for the slots card ability, it's redefined in that type of card,
     * in the other types of cards it doesn't do anything but returns null.
     * In the slots card type, it returns the number of full slots in the card (0,1,2).
     */
    public Integer getFullSlotsNumber() {
        if(isActivated){
            return fullSlotsNumber;
        }
        return null;
    }

    /**
     * this method is for the slots card ability, it's redefined in that type of card,
     * in the other types of cards it doesn't do anything.
     * In the slots card type, it set to n the number of full slots in that card .
     */
    public void setFullSlotsNumber(int n){
        if(isActivated){
            if(n>=0 && n<=2){
                fullSlotsNumber=n;
            }
        }
    }

    /**
     * this method is for the slots card ability, it's redefined in that type of card,
     * in the other types of cards it doesn't do anything.
     * In the slots card type, it adds (if there is space) a Resource in a slot.
     */
    public void addResource() {
        if(isActivated){
            if(fullSlotsNumber<=1){
                fullSlotsNumber++;
            }
        }
    }

    /**
     * this method is for the slots card ability, it's redefined in that type of card,
     * in the other types of cards it doesn't do anything.
     * In the slots card type, it removes a Resource (if there is one) from a slot.
     */
    public void removeResource() {
        if(isActivated){
            if(fullSlotsNumber>=1){
                fullSlotsNumber--;
            }
        }
    }

}
