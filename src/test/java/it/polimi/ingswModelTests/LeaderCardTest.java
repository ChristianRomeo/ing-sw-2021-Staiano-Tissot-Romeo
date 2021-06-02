package it.polimi.ingswModelTests;

import it.polimi.ingsw.controller.Configs;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.LeaderCardWhiteMarble;
import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LeaderCardTest {

    @Test
    public void getIdTest()
    {
        List<LeaderCard> leaderCards = Configs.getLeaderCards();
        System.out.println(leaderCards.get(0).getId());
    }

    @Test
    public void getWhiteMarbleResourceTest()
    {
        List<LeaderCard> leaderCards = Configs.getLeaderCards();
        for(LeaderCard l: leaderCards) {
            if (l.getAbility().toString().equals("WHITEMARBLE")) {
                assert(!l.isActivated() && l.getWhiteMarbleResource() == null);
                l.activate();
                System.out.println(l.getWhiteMarbleResource());
                break;
            }
        }
    }

    @Test
    public void getTotalProducedFpTest()
    {
        List<LeaderCard> leaderCards = Configs.getLeaderCards();
        for(LeaderCard l: leaderCards) {
            if (l.getAbility().toString().equals("PRODUCTION")) {
                l.activate();
                System.out.println(l.getTotalProducedFP(1));
                break;
            }
        }
    }

    @Test
    public void getFullSlotsNumberTest()
    {
        List<LeaderCard> leaderCards = Configs.getLeaderCards();
        for(LeaderCard l: leaderCards) {
            if (l.getAbility().toString().equals("SLOTS")) {
                assert(!l.isActivated() && l.getFullSlotsNumber() == null);
                l.activate();
                System.out.println(l.getFullSlotsNumber());
                break;
            }
        }
    }

}
