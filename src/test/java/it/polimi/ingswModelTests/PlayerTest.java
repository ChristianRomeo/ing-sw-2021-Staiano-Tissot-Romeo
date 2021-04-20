package it.polimi.ingswModelTests;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelExceptions.InvalidCardInsertionException;
import it.polimi.ingsw.model.modelExceptions.InvalidWarehouseInsertionException;
import it.polimi.ingsw.model.modelExceptions.VaticanReportException;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        /*assert(player.isBetween(statusPlayer.getFaithTrackPosition(), 1, 7)); //should be true
        assert(!(player.isBetween(statusPlayer.getFaithTrackPosition(), 10, 15))); //should be false
        */ //commentato perché isBetween è privato
    }

    @Test //test successful
    public void calculateAndSetVictoryPointsTestBasedOnFaithTrackTest() throws VaticanReportException {
        Player player = new Player();
        for(int i = 0; i < 3; i++)
            player.getStatusPlayer().incrementFaithTrackPosition();
        player.calculateAndSetVictoryPoints();
        //since the faith track position is 3, the victory points given should be 1.
        assert(player.getVictoryPoints() == 1);
    }

    @Test //test successful
    public void calculateAndSetVictoryPointsBasedOnStrongboxResourcesTest(){
        Player player = new Player();
        Map<Resource, Integer> resource = new HashMap<>();
        resource.put(Resource.SHIELD, 3);
        resource.put(Resource.COIN, 2);
        player.getStatusPlayer().addStrongboxResources(resource);
        player.calculateAndSetVictoryPoints();
        assert(player.getVictoryPoints() == 1);
    }

    @Test //test successful
    public void calculateAndSetVictoryPointsBasedOnWarehouseResourcesTest() throws InvalidWarehouseInsertionException {
        Player player = new Player();
        player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.SERVANT, 1, 1);
        player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.SHIELD, 2, 1);
        player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.SHIELD, 2, 2);
        player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.COIN, 3, 1);
        player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.COIN, 3, 2);
        player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.COIN, 3, 3);
        player.calculateAndSetVictoryPoints();
        assert(player.getVictoryPoints() == 1);
    }

    @Test //test successfull
    public void calculateAndSetVictoryPointsBasedOnPopeFavorTilesTest() throws VaticanReportException {
        Player player = new Player();
        for(int i = 0; i < 5; i++)
        player.getStatusPlayer().incrementFaithTrackPosition();
        player.getStatusPlayer().vaticanReportHandler(1);
        /*now the faith track position of the player is 5, so the first Pope favor Tile is being activated and
        and it should give 2 victory points, since the first Pope favor Tiles gives 2 points; so in total the player
        should have 3 victory points now, cause 1 point is given by the faith track position*/
        player.calculateAndSetVictoryPoints();
        assert(player.getVictoryPoints() == 3);
    }

    @Test //test successful
    public void calculateAndSetVictoryPointsBasedOnDevelopmentCardsTest() throws InvalidWarehouseInsertionException, IOException, InvalidCardInsertionException, VaticanReportException {
        Player player = new Player();
        DevelopmentCardBoard developmentCardBoard = new DevelopmentCardBoard();
        //let's add a GREEN card to the player personal card board and calculate his victory points
        player.getStatusPlayer().getPersonalCardBoard().addCard(developmentCardBoard.getCard(0,0), 0);
        /*it is necessary to keep track of the card victory points since they're not always the same, since each single
         pile of cards in the development card board is being shuffled. */
        int cardVictoryPoints = player.getStatusPlayer().getPersonalCardBoard().getCard(0,0).getVictoryPoints();
        player.calculateAndSetVictoryPoints();
        assert(player.getVictoryPoints() == cardVictoryPoints);
    }

    @Test //test successful
    public void calculateAndSetVictoryPointsBasedOnLeaderCardsTest() throws FileNotFoundException {
        Player player = new Player();
        final String CARDPATH = "src/main/resources/Leaders.json";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(CARDPATH));
        JsonArray json = new Gson().fromJson(bufferedReader, JsonArray.class);
        List<LeaderCard> list = new Gson().fromJson(String.valueOf(json), new TypeToken<List<SonOfLeaderCard>>() { }.getType());
        player.getStatusPlayer().addLeaderCard(list.get(0));
        //only activated leader cards give victory points, so now the player should have 0 victory points
        assert(player.getVictoryPoints() == 0);
        player.getStatusPlayer().getLeaderCard(0).activate();
        player.calculateAndSetVictoryPoints();
        //now the player should have 2 victory points, which corresponds to the first Leader Card in the JSON file.
        assert(player.getVictoryPoints() == player.getStatusPlayer().getLeaderCard(0).getVictoryPoints());
    }

}
