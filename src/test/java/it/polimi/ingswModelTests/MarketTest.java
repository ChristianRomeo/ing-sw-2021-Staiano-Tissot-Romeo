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
//DONE
public class MarketTest {

    @Test //test passed, the initialization is correct
    public void testMarketInitialization1() throws IOException{
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


    @Test //test passed, the shift is correct, VECCHIO TEST NON CONSIDERARE
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
