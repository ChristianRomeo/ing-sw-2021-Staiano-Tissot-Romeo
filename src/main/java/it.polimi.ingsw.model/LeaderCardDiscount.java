package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class LeaderCardDiscount extends LeaderCard {


    public Map<Resource,Integer> getDiscountedCost(Map<Resource,Integer> originalCost){
        Map<Resource,Integer> discountedCost = new HashMap<>(originalCost);
        if(originalCost.containsKey(abilityResource) && originalCost.get(abilityResource)>0){
            discountedCost.put(abilityResource,originalCost.get(abilityResource)-1);
        }
        return discountedCost;
    }

    public LeaderCardDiscount getCopy(LeaderCard leader){
        return (LeaderCardDiscount) leader;
    } //da togliere?
}
