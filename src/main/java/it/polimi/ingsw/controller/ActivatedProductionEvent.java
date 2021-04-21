package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Resource;

import java.util.List;
//the player wants to activate the production.

public class ActivatedProductionEvent extends ClientEvent{
    private final List<Integer> activatedProduction; //what card productions you want to activate
    private final boolean activateBaseProduction;
    private final Resource requestedResBP1; //resources for the base production
    private final Resource requestedResBP2;//resources for the base production
    private final Resource producedResBP;//resources for the base production
    private final Resource producedResLC1;//resources for the leader card production
    private final Resource producedResLC2;//resources for the leader card production

    public ActivatedProductionEvent(List<Integer> activatedProduction, boolean activateBaseProduction, Resource requestedResBP1, Resource requestedResBP2, Resource producedResBP, Resource producedResLC1, Resource producedResLC2) {
        this.activatedProduction = activatedProduction;
        this.activateBaseProduction = activateBaseProduction;
        this.requestedResBP1 = requestedResBP1;
        this.requestedResBP2 = requestedResBP2;
        this.producedResBP = producedResBP;
        this.producedResLC1 = producedResLC1;
        this.producedResLC2 = producedResLC2;
    }

    //if you create the event without the lead card resources, it means you don't want to activate leader card production
    public ActivatedProductionEvent(List<Integer> activatedProduction, boolean activateBaseProduction, Resource requestedResBP1, Resource requestedResBP2, Resource producedResBP) {
        this.activatedProduction = activatedProduction;
        this.activateBaseProduction = activateBaseProduction;
        this.requestedResBP1 = requestedResBP1;
        this.requestedResBP2 = requestedResBP2;
        this.producedResBP = producedResBP;
        this.producedResLC1 = null;
        this.producedResLC2 = null;
    }

    public List<Integer> getActivatedProduction() {
        return activatedProduction;
    }

    public boolean isBPActivated() {
        return activateBaseProduction;
    }

    public Resource getRequestedResBP1() {
        return requestedResBP1;
    }

    public Resource getRequestedResBP2() {
        return requestedResBP2;
    }

    public Resource getProducedResBP() {
        return producedResBP;
    }

    public Resource getProducedResLC1() {
        return producedResLC1;
    }

    public Resource getProducedResLC2() {
        return producedResLC2;
    }

    @Override
    public void notifyHandler(ClientEventHandler eventHandler){
        eventHandler.handleEvent(this);
    }

}
