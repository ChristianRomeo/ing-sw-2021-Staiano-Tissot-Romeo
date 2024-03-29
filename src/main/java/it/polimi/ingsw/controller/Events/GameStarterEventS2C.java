package it.polimi.ingsw.controller.Events;

import it.polimi.ingsw.model.DevelopmentCardBoard;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.Market;

import java.util.List;

/**
 *server to client event triggered when the game starts
 */
public class GameStarterEventS2C extends ServerEvent{
    private final List<LeaderCard> choiceLeaderCards; //carte tra cui può scegliere
    private final DevelopmentCardBoard cardBoard;
    private final Market market;
    private final int indexPlayer; //tipo se è il primo, o il secondo ecc
    private final List<String> nicknames; //lista dei nicknames di tutti

    public GameStarterEventS2C(List<LeaderCard> choiceLeaderCards, DevelopmentCardBoard cardBoard, Market market, int indexPlayer, List<String> nicknames) {
        this.choiceLeaderCards = choiceLeaderCards;
        this.cardBoard = cardBoard;
        this.market = market;
        this.indexPlayer = indexPlayer;
        this.nicknames = nicknames;
    }

    public List<LeaderCard> getChoiceLeaderCards() {
        return choiceLeaderCards;
    }

    public DevelopmentCardBoard getCardBoard() {
        return cardBoard;
    }

    public Market getMarket() {
        return market;
    }

    public int getIndexPlayer() {
        return indexPlayer;
    }

    public List<String> getNicknames() {
        return nicknames;
    }

    /**
     * notify the eventHandler (which is the virtual view in this case) to handle this specific GameStarterS2C
     * @param eventHandler is the handler which will handle this specific GameStarterEventS2C
     */
    @Override
    public void notifyHandler(ServerEventObserver eventHandler){
        eventHandler.handleEvent(this);
    }
}
