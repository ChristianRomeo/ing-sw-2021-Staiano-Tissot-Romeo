package it.polimi.ingswModelTests;
import it.polimi.ingsw.client.ClientModel;
import it.polimi.ingsw.model.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unit tests.
 */
public class StatusPlayerTest {
    @Test
    public void calculateVP(){

    }


    @Test//test passed, getAllResources work
    public void getAllResourcesTest() throws Exception{

            StatusPlayer statusPlayer = new StatusPlayer();
            PlayerWarehouse playerWarehouse = statusPlayer.getPlayerWarehouse();
            Map<Resource,Integer> allResources;
            playerWarehouse.insertResource(Resource.COIN,1,0);
            playerWarehouse.insertResource(Resource.SERVANT,2,1);
            playerWarehouse.insertResource(Resource.SERVANT,2,2);
            playerWarehouse.insertResource(Resource.SHIELD,3,1);
            playerWarehouse.insertResource(Resource.SHIELD,3,2);
            playerWarehouse.insertResource(Resource.SHIELD,3,3);

            Map<Resource,Integer> resources = new HashMap<>();
            resources.put(Resource.STONE,3);
            resources.put(Resource.COIN,2);
            statusPlayer.addStrongboxResources(resources);

            allResources=statusPlayer.getAllResources();

            assert (allResources.size()==4);
        assert (allResources.get(Resource.STONE)==3);
        assert (allResources.get(Resource.COIN)==3);
        assert (allResources.get(Resource.SHIELD)==3);
        assert (allResources.get(Resource.SERVANT)==2);
    }

    @Test//test passed, adding resources to the strongbox works
    public void addResourcesStrongboxTest(){

        StatusPlayer statusPlayer = new StatusPlayer();

        Map<Resource,Integer> resources = new HashMap<>();
        resources.put(Resource.STONE,3);
        resources.put(Resource.COIN,2);
        resources.put(Resource.SHIELD,10);

        statusPlayer.addStrongboxResources(resources);

        assert (statusPlayer.getStrongboxResources().size()==3);
        assert (statusPlayer.getStrongboxResources().get(Resource.STONE)==3);
        assert (statusPlayer.getStrongboxResources().get(Resource.COIN)==2);
        assert (statusPlayer.getStrongboxResources().get(Resource.SHIELD)==10);

    }

    @Test//test passed, removing resources from the strongbox works
    public void removeResourcesStrongboxTest(){

        StatusPlayer statusPlayer = new StatusPlayer();

        Map<Resource,Integer> resources = new HashMap<>();
        resources.put(Resource.STONE,3);
        resources.put(Resource.COIN,1);
        resources.put(Resource.SHIELD,10);

        statusPlayer.addStrongboxResources(resources);

        statusPlayer.removeStrongboxResource(Resource.COIN);
        statusPlayer.removeStrongboxResource(Resource.STONE);

        assert (statusPlayer.getStrongboxResources().size()==3);
        assert (statusPlayer.getStrongboxResources().get(Resource.STONE)==2);
        assert (statusPlayer.getStrongboxResources().get(Resource.COIN)==0);
        assert (statusPlayer.getStrongboxResources().get(Resource.SHIELD)==10);
    }

    @Test//test passed, method removeResources works
    public void removeResourcesMethodTest() throws Exception{


        StatusPlayer statusPlayer = new StatusPlayer();
        PlayerWarehouse playerWarehouse = statusPlayer.getPlayerWarehouse();

        playerWarehouse.insertResource(Resource.COIN,1,0);
        playerWarehouse.insertResource(Resource.SERVANT,2,1);
        playerWarehouse.insertResource(Resource.SERVANT,2,2);
        playerWarehouse.insertResource(Resource.SHIELD,3,1);
        playerWarehouse.insertResource(Resource.SHIELD,3,2);
        playerWarehouse.insertResource(Resource.SHIELD,3,3);

        Map<Resource,Integer> resources = new HashMap<>();
        resources.put(Resource.STONE,3);
        resources.put(Resource.COIN,2);
        resources.put(Resource.SHIELD,10);
        statusPlayer.addStrongboxResources(resources);

        resources.clear();
        resources.put(Resource.COIN,2);
        resources.put(Resource.STONE,3);
        resources.put(Resource.SHIELD,3);

        statusPlayer.removeResources(resources);
        assert (statusPlayer.getStrongboxResources().size()==3);
        assert (statusPlayer.getStrongboxResources().get(Resource.STONE)==0);
        assert (statusPlayer.getStrongboxResources().get(Resource.COIN)==1);
        assert (statusPlayer.getStrongboxResources().get(Resource.SHIELD)==10);
        assertNull (playerWarehouse.getResource(1,0));
        assertNull (playerWarehouse.getResource(3,1));
        assertNull (playerWarehouse.getResource(3,2));
        assertNull (playerWarehouse.getResource(3,3));
        assert (playerWarehouse.getResource(2,1)==Resource.SERVANT);
        assert (playerWarehouse.getResource(2,2)==Resource.SERVANT);
    }

    /* @Test //test passed, vatican reports handling
    //(I commented it because the method increment is private)
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
