package it.polimi.ingsw.model;

public class LeaderCardWhiteMarble extends LeaderCard{

    public Resource getWhiteMarbleResource() {
        if(isActivated){
            return abilityResource;
        }
        return null;
    }
}
