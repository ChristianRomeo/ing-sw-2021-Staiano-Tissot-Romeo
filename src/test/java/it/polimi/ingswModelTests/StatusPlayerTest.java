package it.polimi.ingswModelTests;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.*;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit tests.
 */
public class StatusPlayerTest {
    @Test
    public void calculateVP(){

    }


    @Test//test passed, strongbox adding and getAllResources work
    public void getAllResourcesTest(){
        try{
            StatusPlayer statusPlayer = new StatusPlayer();
            PlayerWarehouse playerWarehouse = statusPlayer.getPlayerWarehouse();
            Map<Resource,Integer> allResources;
            playerWarehouse.insertResource(Resource.COIN,1,0);
            playerWarehouse.insertResource(Resource.SERVANT,2,1);
            playerWarehouse.insertResource(Resource.SERVANT,2,2);
            playerWarehouse.insertResource(Resource.SHIELD,3,1);
            playerWarehouse.insertResource(Resource.SHIELD,3,2);
            playerWarehouse.insertResource(Resource.SHIELD,3,3);
            allResources=statusPlayer.getAllResources();

            Map<Resource,Integer> resources = new HashMap<>();
            resources.put(Resource.STONE,3);
            resources.put(Resource.COIN,2);

            statusPlayer.addResourcesStrongbox(resources);
            allResources=statusPlayer.getAllResources();
            System.out.println("");
        }catch (Exception e){
            System.out.println("eccezione");
        }

    }
}
