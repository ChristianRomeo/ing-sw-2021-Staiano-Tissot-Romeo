package it.polimi.ingsw.controller.Events;

import it.polimi.ingsw.model.PlayerWarehouse;

import java.util.Map;

//si invia sto evento quando tutti i giocatori hanno finito di fare le proprie scelte iniziali
//invio i warehouse di tutti, perchè certi possono aver messo risorse
//quando arriva sto evento sul client vuol dire che si parte con i turni normali ed è il turno del primo player

public class EndPreparationEventS2C extends ServerEvent{
    Map<String, PlayerWarehouse> warehouses; //mappa con nickname-> warehouse di quel tizio

    public EndPreparationEventS2C(Map<String, PlayerWarehouse> warehouses) {
        this.warehouses = warehouses;
    }

    public Map<String, PlayerWarehouse> getWarehouses() {
        return warehouses;
    }

    @Override
    public void notifyHandler(ServerEventObserver eventHandler){
        eventHandler.handleEvent(this);
    }

}
