package it.polimi.ingswModelTests;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests.
 */
public class PlayerWarehouseTest {
    @Test
    public void setWarehouse(){

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
