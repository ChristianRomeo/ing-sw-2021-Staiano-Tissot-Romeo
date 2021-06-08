package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class LeaderCardProduction extends LeaderCard {

    /**
     * this method is for the production card ability, it's redefined in that type of card,
     * in the other types of cards it doesn't do anything but returns the argument you pass.
     * In the production card type, it returns the updated required resources for the production.
     */
    public Map<Resource,Integer> getTotalRequiredResources(Map<Resource,Integer> oldRequiredResources){
        if(isActivated){
            Map<Resource,Integer> totalRequiredResources = new HashMap<>(oldRequiredResources);
            totalRequiredResources = Resource.addOneResource(totalRequiredResources,abilityResource);
            return totalRequiredResources;
        }
        return oldRequiredResources;
    }

    /**
     * this method is for the production card ability, it's redefined in that type of card,
     * in the other types of cards it doesn't do anything but returns the argument you pass.
     * In the production card type, it returns the updated produced resources of the production.
     */
    public Map<Resource,Integer> getTotalProducedResources(Map<Resource,Integer> oldProducedResources,Resource addedResource){
        if(isActivated){
            Map<Resource,Integer> totalProducedResources = new HashMap<>(oldProducedResources);
            totalProducedResources = Resource.addOneResource(totalProducedResources,addedResource);
            return totalProducedResources;
        }
        return oldProducedResources;
    }

    /**
     * this method is for the production card ability, it's redefined in that type of card,
     * in the other types of cards it doesn't do anything but returns the argument you pass.
     * In the production card type, it returns the updated produced faith points of the production.
     */
    public int getTotalProducedFP(int oldProducedFP){
        if(isActivated){
            return oldProducedFP+1;
        }
        return oldProducedFP;
    }
}
