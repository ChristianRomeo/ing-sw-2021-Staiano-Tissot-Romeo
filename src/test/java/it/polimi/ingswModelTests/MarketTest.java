package it.polimi.ingswModelTests;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests.
 */
//DONE
public class MarketTest {

    @Test //test passed, the initialization is correct
    public void testMarketInitialization1() {
        Market market = new Market();
        int numberWhite=4;
        int numberBlu=2;
        int numberGrey=2;
        int numberYellow=2;
        int numberPurple=2;
        int numberRed=1;

        for(int i=0; i<3;i++){
            for(int j=0; j<4;j++){
                switch (market.getColor(i,j)){
                    case WHITE -> numberWhite--;
                    case BLUE -> numberBlu--;
                    case GREY -> numberGrey--;
                    case YELLOW -> numberYellow--;
                    case PURPLE -> numberPurple--;
                    case RED -> numberRed--;
                }
            }
        }
        switch (market.getSlideMarble()){
            case WHITE -> numberWhite--;
            case BLUE -> numberBlu--;
            case GREY -> numberGrey--;
            case YELLOW -> numberYellow--;
            case PURPLE -> numberPurple--;
            case RED -> numberRed--;
        }
        assert (numberWhite==0);
        assert (numberBlu==0);
        assert (numberGrey==0);
        assert (numberYellow==0);
        assert (numberPurple==0);
        assert (numberRed==0);
    }


    @Test //test passed
    public void testMarketSelectColumn()
    {
        Market market = new Market();
        MarbleColor oldMarbleUp = market.getColor(0,0);
        MarbleColor oldMarbleMiddle = market.getColor(1,0);
        MarbleColor oldMarbleDown = market.getColor(2,0);
        MarbleColor oldSlideMarble = market.getSlideMarble();

        market.selectColumn(0);

        assert market.getSlideMarble() == oldMarbleUp;
        assert market.getColor(2,0) == oldSlideMarble;
        assert market.getColor(1,0) == oldMarbleDown;
        assert market.getColor(0,0) == oldMarbleMiddle;
    }

    @Test //test passed
    public void testMarketSelectRow()
    {
        Market market = new Market();
        MarbleColor oldMarble0 = market.getColor(0,0);
        MarbleColor oldMarble1 = market.getColor(0,1);
        MarbleColor oldMarble2 = market.getColor(0,2);
        MarbleColor oldMarble3 = market.getColor(0,3);
        MarbleColor oldSlideMarble = market.getSlideMarble();

        market.selectRow(0);

        assert market.getSlideMarble() == oldMarble0;
        assert market.getColor(0,0) == oldMarble1;
        assert market.getColor(0,1) == oldMarble2;
        assert market.getColor(0,2) == oldMarble3;
        assert market.getColor(0,3) == oldSlideMarble;

    }


    @Disabled
    public void checkMarketReturnResources() throws IOException {
        Market market = new Market();
        List<MarbleColor> color = new ArrayList<>();

        Game game = new Game();
        Controller controller= new Controller(game);

        Player player = new Player("pl1");


        game.addNewPlayer(player);
        game.setCurrentPlayer(player);


        color.add(market.getColor(0,0));
        color.add(market.getColor(1,0));
        color.add(market.getColor(2,0));

        List<MarbleColor> taken = market.selectColumn(0);

        assertEquals(color, taken);

        Map<Resource,Integer> oldResources = player.getStatusPlayer().getAllResources();
        int oldFaithTrackPosition = player.getStatusPlayer().getFaithTrackPosition();

        for(MarbleColor m: color)
            switch (m) {
                case WHITE -> { }
                case RED ->{
                    assert player.getStatusPlayer().getFaithTrackPosition() == oldFaithTrackPosition + 1;
                }
                case BLUE -> {
                    assert player.getStatusPlayer().getAllResources().get(Resource.SHIELD) == oldResources.get(Resource.SHIELD)+1;
                }
                case GREY -> {
                    assert player.getStatusPlayer().getAllResources().get(Resource.STONE) == oldResources.get(Resource.STONE)+1;
                }
                case PURPLE -> {
                    assert player.getStatusPlayer().getAllResources().get(Resource.SERVANT) == oldResources.get(Resource.SERVANT)+1;
                }
                case YELLOW -> {
                    assert player.getStatusPlayer().getAllResources().get(Resource.COIN) == oldResources.get(Resource.COIN)+1;
                }
            }

    }

}
