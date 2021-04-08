package it.polimi.ingswControllerTests;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.*;

import modelExceptions.CannotBuyCardException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit tests.
 */
public class ControllerTest {
    @Test
    public void controllerInit(){

    }
    @Test //test passed, test if you haven't enough resources to buy a card
    public void buyCardTest1() throws Exception{

        Game game = new Game();
        Controller controller = new Controller(game);
        Player player = new Player();
        player.setNickname("player1");
        game.addNewPlayer(player);
        game.setCurrentPlayer(player);
        assertThrows(CannotBuyCardException.class, ()-> controller.buyDevelopmentCard(0,0));

    }

              //test passed, test if you haven't the right spot in the personal card board to buy
    @Test    //that card
    public void buyCardTest2() throws Exception{

        Game game = new Game();
        Controller controller = new Controller(game);
        Player player = new Player();
        player.setNickname("player1");
        game.addNewPlayer(player);
        game.setCurrentPlayer(player);

        Map<Resource,Integer>  resources = new HashMap<>();
        resources.put(Resource.COIN,10);
        resources.put(Resource.STONE,10);
        resources.put(Resource.SERVANT,10);
        resources.put(Resource.SHIELD,10);
        player.getStatusPlayer().addStrongboxResources(resources);

        assertThrows(CannotBuyCardException.class, ()-> controller.buyDevelopmentCard(1,0));

    }

    @Test //test passed, you can buy a card
    public void buyCardTest3() throws Exception{

        Game game = new Game();
        Controller controller = new Controller(game);
        Player player = new Player();
        player.setNickname("player1");
        game.addNewPlayer(player);
        game.setCurrentPlayer(player);

        Map<Resource,Integer>  resources = new HashMap<>();
        resources.put(Resource.COIN,10);
        resources.put(Resource.STONE,10);
        resources.put(Resource.SERVANT,10);
        resources.put(Resource.SHIELD,10);
        player.getStatusPlayer().addStrongboxResources(resources);

        controller.buyDevelopmentCard(0,0);

        assert(!player.getStatusPlayer().getPersonalCardBoard().isCardPileEmpty(0));
        assert(player.getStatusPlayer().getPersonalCardBoard().getNumberOfCards()==1);
        assert(player.getStatusPlayer().getPersonalCardBoard().getUpperCard(0).getLevel()==1);
        assert(player.getStatusPlayer().getPersonalCardBoard().getUpperCard(0).getType()==CardType.GREEN);

    }
    @Test //test passed, VECCHIO TEST, NON CONSIDERARE
    public void buyCardTest(){
        try{
            Game game = new Game();
            Controller controller = new Controller(game);
            Player player = new Player();
            player.setNickname("player1");
            game.addNewPlayer(player);
            game.setCurrentPlayer(player);
           // controller.buyDevelopmentCard(0,0); //test if you haven't enough resources
            //controller.buyDevelopmentCard(1,0); //test if you haven't space in your board
            player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.COIN,1,0);
            player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.SERVANT,2,1);
            player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.SERVANT,2,2);
            player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.SHIELD,3,1);
            player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.SHIELD,3,2);
            player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.SHIELD,3,3);
            controller.buyDevelopmentCard(0,0);
            //controller.buyDevelopmentCard(1,0);
            System.out.println("passed");
        }catch (Exception e){
            System.out.println("eccezione");
        }
    }

    @Test //test passed, VECCHIO TEST, NON CONSIDERARE
    public void productionTest(){
        try{
            Game game = new Game();
            Controller controller = new Controller(game);
            Player player = new Player();
            player.setNickname("player1");
            game.addNewPlayer(player);
            game.setCurrentPlayer(player);

            Map<Resource,Integer>  resources = new HashMap<>();
            resources.put(Resource.COIN,10);
            resources.put(Resource.STONE,10);
            resources.put(Resource.SERVANT,10);
            resources.put(Resource.SHIELD,10);

            player.getStatusPlayer().addStrongboxResources(resources);

            player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.COIN,1,0);
            player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.SERVANT,2,1);
            player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.SERVANT,2,2);
            player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.SHIELD,3,1);
            player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.SHIELD,3,2);
            player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.SHIELD,3,3);
            controller.buyDevelopmentCard(0,1);
            controller.buyDevelopmentCard(1,1);

            controller.activateProduction();
            System.out.println("passed");
        }catch (Exception e){
            System.out.println("eccezione");
        }
    }


    @Test
    public void provaLeader(){//è giusto una prova
        try{
            Game game = new Game();
            Controller controller = new Controller(game);
            Player player = new Player();
            player.setNickname("player1");
            game.addNewPlayer(player);
            game.setCurrentPlayer(player);

            LeaderCardDiscount leaderCardDiscount = new LeaderCardDiscount();

            System.out.println(leaderCardDiscount.getAbilityResource());
            System.out.println("passed");
        }catch (Exception e){
            System.out.println("eccezione");
        }
    }

}
