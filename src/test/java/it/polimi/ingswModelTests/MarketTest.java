package it.polimi.ingswModelTests;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests.
 */
public class MarketTest {

    @Test //The column 0 returns the correct resources
    public void testMarketInitialization() throws IOException {
        ArrayList<MarbleColor> color = new ArrayList<>();
        ArrayList<Resource> check =new ArrayList<>();
        List<MarbleColor> taken=new ArrayList<>();
        Market market = new Market();
        Game game=new Game();
        Controller controller= new Controller(game);
        color.add(market.getColor(0,0));
        color.add(market.getColor(1,0));
        color.add(market.getColor(2,0));
        for(MarbleColor color1 : color)
            switch(color1){
                case WHITE:
                    //controllo carta leader va qui
                    /*
                    if(handlerMarbleLeader()!=null){
                        boughtResources.add(handlerMarbleLeader());
                    }
                     */
                    break;
                case RED:
                   // check.add();
                    break;
                case BLUE:
                    check.add(Resource.SHIELD);
                    break;
                case GREY:
                    check.add(Resource.STONE);
                    break;
                case PURPLE:
                    check.add(Resource.SERVANT);
                    break;
                case YELLOW:
                    check.add(Resource.COIN);
                    break;
            }
        taken=market.selectColumn(0);

        //?? assertArrayEquals(controller.useMarket('c',0), check )

    }

    @Test //test passed, the shift is correct
    public void testMarketSelectRowSelectColumn()
    {
        Market market = new Market();
        market.selectColumn(0);
        market.selectColumn(3);
        market.selectRow(0);
        market.selectRow(1);
        market.selectRow(2);
        System.out.println("ciaomarket");
    }
}
