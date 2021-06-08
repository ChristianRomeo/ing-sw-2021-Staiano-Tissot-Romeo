package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class LeaderCardDiscount extends LeaderCard {

    /**
     * this method is for the discounted cost card ability, it's redefined in that type of card,
     * in the other types of cards it doesn't do anything but returns the original cost.
     * In the discounted cost card type, it changes the original cost and returns the discounted cost.
     */
    public Map<Resource,Integer> getDiscountedCost(Map<Resource,Integer> originalCost){
        if(isActivated){
            Map<Resource,Integer> discountedCost = new HashMap<>(originalCost);
            if(originalCost.containsKey(abilityResource) && originalCost.get(abilityResource)>0){
                discountedCost.put(abilityResource,originalCost.get(abilityResource)-1);
            }
            return discountedCost;
        }
        return originalCost;
    }


}
