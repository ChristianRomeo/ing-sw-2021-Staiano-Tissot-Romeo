package it.polimi.ingsw.controller.Events;

import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.PlayerWarehouse;

import java.util.List;
import java.util.Map;

/**
 *server to client event triggered when all the players have finished their initial choices
 * it sends everyone's warehouse, together with everyone's Leader cards
 * when this events is reached by the client, it means the "real" game starts and it's the first player's turn
 */
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

    /**
     * notify the eventHandler (which is the virtual view in this case) to handle this specific EndPreparationEventS2C
     * @param eventHandler is the handler which will handle this specific EndPreparationEventS2C
     */
    @Override
    public void notifyHandler(ServerEventObserver eventHandler){
        eventHandler.handleEvent(this);
    }

}
