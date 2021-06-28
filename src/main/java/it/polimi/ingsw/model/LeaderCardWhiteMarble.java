package it.polimi.ingsw.model;

/**
 * LeaderCardWhiteMarble class represents the Leader cards with special ability "White Marble" when taking resources from the market
 */
public class LeaderCardWhiteMarble extends LeaderCard{
    /**
     * this method is for the white marble card ability, it's redefined in that type of card,
     * in the other types of cards it doesn't do anything but returns null.
     * In the white marble card type, it returns the Resource to gain from the white marble.
     */
    public Resource getWhiteMarbleResource() {
        if(isActivated){
            return abilityResource;
        }
        return null;
    }
}
