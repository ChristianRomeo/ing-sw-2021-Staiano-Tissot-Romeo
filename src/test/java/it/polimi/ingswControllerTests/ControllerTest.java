package it.polimi.ingswControllerTests;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests.
 */
public class ControllerTest {
    @Test
    public void controllerInit(){

    }

    @Test //test passed
    public void buyCard(){
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
}
