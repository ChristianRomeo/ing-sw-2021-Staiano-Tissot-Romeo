package it.polimi.ingswModelTests;

import it.polimi.ingsw.model.PersonalCardBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.StatusPlayer;
import modelExceptions.InvalidWarehouseInsertionException;
import modelExceptions.VaticanReportException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit tests.
 */
public class PlayerTest {
    @Test // test successful
    public void isBetweenTest() throws VaticanReportException {
        StatusPlayer statusPlayer = new StatusPlayer();
        Player player = new Player();
        for(int i = 0; i < 3; i++)
            statusPlayer.incrementFaithTrackPosition();
        assert(player.isBetween(statusPlayer.getFaithTrackPosition(), 1, 7)); //should be true
        assert(!(player.isBetween(statusPlayer.getFaithTrackPosition(), 10, 15))); //should be false

    }
    //TODO: THIS TEST IS NOW COMMENTED CAUSE ITS LINKED METHOD IS NOT FULLY WORKING (LEADER CARDS MISSING...)
    //BUT SO FAR IT WORKS.
    @Test //test successful
    public void calculateAndSetVictoryPointsTest() throws VaticanReportException, InvalidWarehouseInsertionException {
            Player player = new Player();
            PersonalCardBoard personalCardBoard = new PersonalCardBoard();
            /*calculate victory points based on faith track position.
            by doing this, 1 should be added to the variable sum, since the faith track position would be 3*/
            for(int i = 0; i < 3; i++)
                player.getStatusPlayer().incrementFaithTrackPosition();
            player.calculateAndSetVictoryPoints();
            assert(player.getVictoryPoints() == 1);
             /*
            TODO: test: continue calculate victory points based on Leader cards, Development Cards and Pope Favor Tiles.
            Only activated Leader cards points are being added.
            2 is the amount of Leader cards per player.
            */

           //calculate victory points based on Strongbox Resources
            Map<Resource, Integer> resource = new HashMap<>();
            resource.put(Resource.SHIELD, 3);
            resource.put(Resource.COIN, 2);
            player.getStatusPlayer().addResourcesStrongbox(resource);
            player.calculateAndSetVictoryPoints();
            assert(player.getVictoryPoints() == 2);

            //calculate victory points based on Warehouse Resources
            player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.SERVANT, 1, 1);
            player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.SHIELD, 2, 1);
            player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.SHIELD, 2, 2);
            player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.COIN, 3, 1);
            player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.COIN, 3, 2);
            player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.COIN, 3, 3);
            player.calculateAndSetVictoryPoints();
            assert(player.getVictoryPoints() == 3);


    }



}
