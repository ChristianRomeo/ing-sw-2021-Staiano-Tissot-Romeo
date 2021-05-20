package it.polimi.ingswModelTests;
import it.polimi.ingsw.model.*;

import it.polimi.ingsw.model.modelExceptions.InvalidWarehouseInsertionException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests.
 */

//DONE WAREHOUSE TESTS
public class PlayerWarehouseTest {

    @Test // test passed, set a resource in the warehouse should work
    public void testSetOnePositionWarehouse() throws Exception
    {
        PlayerWarehouse playerWarehouse = new PlayerWarehouse();

            playerWarehouse.insertResource(Resource.COIN,1,0);
            playerWarehouse.insertResource(Resource.SERVANT,2,1);
            playerWarehouse.insertResource(Resource.SERVANT,2,2);
            playerWarehouse.insertResource(Resource.SHIELD,3,1);
            playerWarehouse.insertResource(Resource.SHIELD,3,2);
            playerWarehouse.insertResource(Resource.SHIELD,3,3);

            assert (playerWarehouse.getResource(1,0)==Resource.COIN);
            assert (playerWarehouse.getResource(2,1)==Resource.SERVANT);
            assert (playerWarehouse.getResource(2,2)==Resource.SERVANT);
            assert (playerWarehouse.getResource(3,1)==Resource.SHIELD);
            assert (playerWarehouse.getResource(3,2)==Resource.SHIELD);
            assert (playerWarehouse.getResource(3,3)==Resource.SHIELD);

            assertNull(playerWarehouse.getResource(4,1));
    }

    @Test
    public void testCheckWarehouse() throws Exception{
        PlayerWarehouse playerWarehouse = new PlayerWarehouse();

        playerWarehouse.insertResource(Resource.COIN,1,0);
        playerWarehouse.insertResource(Resource.SERVANT,2,1);
        playerWarehouse.insertResource(Resource.SERVANT,2,2);
        playerWarehouse.insertResource(Resource.SHIELD,3,1);
        playerWarehouse.insertResource(Resource.SHIELD,3,2);
        playerWarehouse.insertResource(Resource.SHIELD,3,3);

        assert (playerWarehouse.checkWarehouse());

    }

    @Test // test passed, full cell check work
    public void fullCellTest() throws Exception
    {
        PlayerWarehouse playerWarehouse = new PlayerWarehouse();

            playerWarehouse.insertResource(Resource.COIN,1,0);
            playerWarehouse.insertResource(Resource.SERVANT,2,1);
            playerWarehouse.insertResource(Resource.SERVANT,2,2);
            playerWarehouse.insertResource(Resource.SHIELD,3,1);
            playerWarehouse.insertResource(Resource.SHIELD,3,2);
            playerWarehouse.insertResource(Resource.SHIELD,3,3);

            assertThrows(InvalidWarehouseInsertionException.class,
                    ()-> playerWarehouse.insertResource(Resource.SERVANT,2,2));
            //full cell test
    }

    @Test // test passed, SAME ROW DIFFERENT RESOURCES TEST
    public void checkWarehouseRules1() throws Exception
    {
        PlayerWarehouse playerWarehouse = new PlayerWarehouse();

        playerWarehouse.insertResource(Resource.COIN,1,0);
        playerWarehouse.insertResource(Resource.SERVANT,2,1);

        assertThrows(InvalidWarehouseInsertionException.class,
                ()-> playerWarehouse.insertResource(Resource.SHIELD,2,2));

    }

    @Test // test passed, TEST DIFFERENT ROW SAME RESOURCE'S TYPE
    public void checkWarehouseRules2() throws Exception
    {
        PlayerWarehouse playerWarehouse = new PlayerWarehouse();

        playerWarehouse.insertResource(Resource.COIN,1,0);
        playerWarehouse.insertResource(Resource.SERVANT,2,1);

        assertThrows(InvalidWarehouseInsertionException.class,
                ()-> playerWarehouse.insertResource(Resource.COIN,3,1));
    }

    @Test // test passed, removing resources should work
    public void removeResourceTest() throws Exception
    {
        PlayerWarehouse playerWarehouse = new PlayerWarehouse();

        playerWarehouse.insertResource(Resource.COIN,1,0);
        playerWarehouse.insertResource(Resource.SERVANT,2,1);
        playerWarehouse.removeResource(2,1);
        playerWarehouse.removeResource(1,1);

        assertNull(playerWarehouse.getResource(1, 0));
        assertNull(playerWarehouse.getResource(2, 1));
    }

    @Test // test passed
    public void getAllWarehouseResourcesTest() throws Exception
    {
        PlayerWarehouse playerWarehouse = new PlayerWarehouse();
        Map<Resource,Integer> allResources;

        playerWarehouse.insertResource(Resource.COIN,1,0);
        playerWarehouse.insertResource(Resource.SERVANT,2,1);
        playerWarehouse.insertResource(Resource.SERVANT,2,2);
        playerWarehouse.insertResource(Resource.SHIELD,3,1);
        playerWarehouse.insertResource(Resource.SHIELD,3,2);
        playerWarehouse.insertResource(Resource.SHIELD,3,3);

        allResources= playerWarehouse.getAllResources();
        assert (allResources.size()==3);
        assert (allResources.get(Resource.COIN)==1);
        assert (allResources.get(Resource.SERVANT)==2);
        assert (allResources.get(Resource.SHIELD)==3);
    }


}
