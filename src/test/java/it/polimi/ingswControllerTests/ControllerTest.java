package it.polimi.ingswControllerTests;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.*;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit tests.
 */
public class ControllerTest {
    @Test
    public void controllerInit(){

    }

    @Test //test passed
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
            System.out.println("");
        }catch (Exception e){
            System.out.println("eccezione");
        }
    }

    @Test //test passed
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
            System.out.println("");
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

            LeaderCard leaderCard = new LeaderCardDiscount();

            LeaderCardDiscount leaderCardDiscount = (LeaderCardDiscount) leaderCard;

            System.out.println(leaderCardDiscount.getDiscountedResource());
            System.out.println("");
        }catch (Exception e){
            System.out.println("eccezione");
        }
    }

}
