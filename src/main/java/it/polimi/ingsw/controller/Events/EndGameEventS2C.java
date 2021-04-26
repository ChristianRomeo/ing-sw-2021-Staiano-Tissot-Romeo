package it.polimi.ingsw.controller.Events;

import java.util.List;
import java.util.Map;

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

    @Override
    public void notifyHandler(ServerEventObserver eventHandler){
        eventHandler.handleEvent(this);
    }
}
