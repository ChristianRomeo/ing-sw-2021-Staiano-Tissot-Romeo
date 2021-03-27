package it.polimi.ingsw;
import it.polimi.ingsw.model.*;

import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    /*
     * Rigorous Test :-)
     */
   @Test
    public void shouldAnswerWithTrue()
    {
        System.out.println("hello");
    }


    @Test //test passed, the inizialization create a correct random market
    public void testMarketInizialization()
    {
        Market market = new Market();
        System.out.println("");
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
        System.out.println("");
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
}
