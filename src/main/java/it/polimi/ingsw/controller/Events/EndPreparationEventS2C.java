package it.polimi.ingsw.controller.Events;

import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PlayerWarehouse;

import java.util.List;
import java.util.Map;

//si invia sto evento quando tutti i giocatori hanno finito di fare le proprie scelte iniziali
//invio i warehouse di tutti, perchè certi possono aver messo risorse
//invio anche le leader card di tutti, però poi nel client mostro solo quelle di quel giocatore e quelle attive degli altri
//quando arriva sto evento sul client vuol dire che si parte con i turni normali ed è il turno del primo player

public class EndPreparationEventS2C extends ServerEvent{
    private final Map<String, PlayerWarehouse> warehouses; //mappa con nickname-> warehouse di quel tizio
    private final Map<String, List<LeaderCard>> leaderCards; //mappa con nickname-> leadercards di quel tizio

    public EndPreparationEventS2C(Map<String, PlayerWarehouse> warehouses, Map<String, List<LeaderCard>> leaderCards) {
        this.warehouses = warehouses;
        this.leaderCards = leaderCards;
    }

    public Map<String, List<LeaderCard>> getLeaderCards() {
        return leaderCards;
    }

    public Map<String, PlayerWarehouse> getWarehouses() {
        return warehouses;
    }

    @Override
    public void notifyHandler(ServerEventObserver eventHandler){
        eventHandler.handleEvent(this);
    }

}
