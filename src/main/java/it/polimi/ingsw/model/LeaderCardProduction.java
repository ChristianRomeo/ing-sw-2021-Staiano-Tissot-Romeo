package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class LeaderCardProduction extends LeaderCard {

    //SAREBBE MEGLIO FARE UN SOLO METODO CHE AGGIORNA TUTTI E 3 I PARAMETRI MA E' UN PO DIFFICILE
    public Map<Resource,Integer> getTotalRequiredResources(Map<Resource,Integer> oldRequiredResources){
        if(isActivated){
            Map<Resource,Integer> totalRequiredResources = new HashMap<>(oldRequiredResources);
            totalRequiredResources = Resource.addOneResource(totalRequiredResources,abilityResource);
            return totalRequiredResources;
        }
        return oldRequiredResources;
    }

    public Map<Resource,Integer> getTotalProducedResources(Map<Resource,Integer> oldProducedResources,Resource addedResource){
        if(isActivated){
            Map<Resource,Integer> totalProducedResources = new HashMap<>(oldProducedResources);
            totalProducedResources = Resource.addOneResource(totalProducedResources,addedResource);
            return totalProducedResources;
        }
        return oldProducedResources;
    }

    public int getTotalProducedFP(int oldProducedFP){
        if(isActivated){
            return oldProducedFP+1;
        }
        return oldProducedFP;
    }
}
