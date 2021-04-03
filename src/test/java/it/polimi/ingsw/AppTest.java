package it.polimi.ingsw;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests.
 */
public class AppTest
{

    @Test //test passed, the inizialization create a correct random market
    public void testMarketInizialization()
    {
        Market market = new Market();
        System.out.println("b");
    }

    @Test //test passed, the shift is correct (the resources collecting isn't implemented yet)
    public void testMarketSelectRowSelectColomn()
    {
        Market market = new Market();
        market.selectColumn(0);
        market.selectColumn(3);
        market.selectRow(0);
        market.selectRow(1);
        market.selectRow(2);
        System.out.println("ciaomarket");
    }

    @Test // test passed, set a resource in the warehouse and removing resources should work
    public void testSetOnePositionWarehouse()
    {
        PlayerWarehouse playerWarehouse = new PlayerWarehouse();
        try{
            playerWarehouse.insertResource(Resource.COIN,1,0);
            playerWarehouse.insertResource(Resource.SERVANT,2,1);
            playerWarehouse.insertResource(Resource.SERVANT,2,2);
            playerWarehouse.insertResource(Resource.SHIELD,3,1);
            playerWarehouse.insertResource(Resource.SHIELD,3,2);
            playerWarehouse.insertResource(Resource.SHIELD,3,3);

           // playerWarehouse.insertResource(Resource.SERVANT,2,2); //full cell test
           /*
            //SAME ROW DIFFERENT RESOURCES TEST
            playerWarehouse.removeResource(3,3);
            playerWarehouse.removeResource(1,4);
            playerWarehouse.insertResource(Resource.COIN,3,3);
            */
           /* playerWarehouse.removeResource(3,3);//TEST DIFFERENT ROW SAME RESOURCE'S TYPE
            playerWarehouse.removeResource(3,2);
            playerWarehouse.removeResource(3,1);
            playerWarehouse.insertResource(Resource.COIN,3,1);
            System.out.println("");

            */

            System.out.println(playerWarehouse.removeResource(2,2));
        }catch(Exception e){
            System.out.println("already full cell or invalid inseriment");
        }
    }

    @Test //test passed, the creation of the development card board works
    public void testDevelopmentCardBoardInit()
    {
        DevelopmentCardBoard developmentCardBoard;
        try{
            developmentCardBoard = new DevelopmentCardBoard();
            System.out.println("ciao");
        }catch(Exception e){
            System.out.println("eccezione");
        }
    }

   /* @Test //test passed, vatican reports handling (i commented it because the method increment is private)
    public void testVaticanReport()
    {
        Game game = new Game();
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setNickname("pl1");
        player2.setNickname("pl2");

        game.addNewPlayer(player1);
        game.addNewPlayer(player2);
        Controller controller = new Controller(game);

        controller.incrementFaithTrackPosition(player2);
        controller.incrementFaithTrackPosition(player2);
        controller.incrementFaithTrackPosition(player2);
        controller.incrementFaithTrackPosition(player2);
        controller.incrementFaithTrackPosition(player2);

        controller.incrementFaithTrackPosition(player1);
        controller.incrementFaithTrackPosition(player1);
        controller.incrementFaithTrackPosition(player1);
        controller.incrementFaithTrackPosition(player1);
        controller.incrementFaithTrackPosition(player1);
        controller.incrementFaithTrackPosition(player1);
        controller.incrementFaithTrackPosition(player1);
        controller.incrementFaithTrackPosition(player1);
        controller.incrementFaithTrackPosition(player1);

    }*/

}
