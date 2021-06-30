package it.polimi.ingsw.controller.Events;

import java.util.List;
import java.util.Map;

/**
 *serve to client event triggered when the game ends
 */
public class EndGameEventS2C extends ServerEvent{

    private final List<String> winners; //list of the winners' nicknames.
    private final Map<String, Integer> victoryPoints; //map of nickname->pv of that player

    public EndGameEventS2C(List<String> winners, Map<String, Integer> victoryPoints) {
        this.winners = winners;
        this.victoryPoints = victoryPoints;
    }

    public List<String> getWinners() {
        return winners;
    }

    public Map<String, Integer> getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * notify the eventHandler (which is the virtual view in this case) to handle this specific EndGameEventS2C
     * @param eventHandler is the handler which will handle this specific EndGameEventS2C
     */
    @Override
    public void notifyHandler(ServerEventObserver eventHandler){
        eventHandler.handleEvent(this);
    }
}
