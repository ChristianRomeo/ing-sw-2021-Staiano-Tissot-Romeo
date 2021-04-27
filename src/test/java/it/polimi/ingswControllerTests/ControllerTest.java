package it.polimi.ingswControllerTests;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelExceptions.InvalidWarehouseInsertionException;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        Player player = new Player("player1");
        player.getStatusPlayer().addLeaderCard(new SonOfLeaderCard());
        player.getStatusPlayer().addLeaderCard(new SonOfLeaderCard());
        game.addNewPlayer(player);
        game.setCurrentPlayer(player);
       // assertThrows(CannotBuyCardException.class, ()-> controller.buyDevelopmentCard(0,0,0));//non tira più eccezz
        controller.buyDevelopmentCard(0,0,0);
        assert(game.getIllegalActions().size()==1);

    }

              //test passed, test if you haven't the right spot in the personal card board to buy
    @Test    //that card
    public void buyCardTest2() throws Exception{

        Game game = new Game();
        Controller controller = new Controller(game);
        Player player = new Player("player1");
        player.getStatusPlayer().addLeaderCard(new SonOfLeaderCard());
        player.getStatusPlayer().addLeaderCard(new SonOfLeaderCard());
        game.addNewPlayer(player);
        game.setCurrentPlayer(player);

        Map<Resource,Integer>  resources = new HashMap<>();
        resources.put(Resource.COIN,10);
        resources.put(Resource.STONE,10);
        resources.put(Resource.SERVANT,10);
        resources.put(Resource.SHIELD,10);
        player.getStatusPlayer().addStrongboxResources(resources);

        //assertThrows(CannotBuyCardException.class, ()-> controller.buyDevelopmentCard(1,0,0));
        controller.buyDevelopmentCard(1,0,0);
        assert(game.getIllegalActions().size()==1);
    }

    @Test //test passed, you can buy a card
    public void buyCardTest3() throws Exception{

        Game game = new Game();
        Controller controller = new Controller(game);
        Player player = new Player("player1");
        game.addNewPlayer(player);
        game.setCurrentPlayer(player);
        player.getStatusPlayer().addLeaderCard(new SonOfLeaderCard());
        player.getStatusPlayer().addLeaderCard(new SonOfLeaderCard());

        Map<Resource,Integer>  resources = new HashMap<>();
        resources.put(Resource.COIN,10);
        resources.put(Resource.STONE,10);
        resources.put(Resource.SERVANT,10);
        resources.put(Resource.SHIELD,10);
        player.getStatusPlayer().addStrongboxResources(resources);

        controller.buyDevelopmentCard(0,0,0);

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
            Player player = new Player("player1");
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
            controller.buyDevelopmentCard(0,0,0);
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
            Player player = new Player("player1");
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
            controller.buyDevelopmentCard(0,1,0);
            controller.buyDevelopmentCard(1,1,0);

            List<Integer> activatedProductions = new ArrayList<>();
            activatedProductions.add(0);
            controller.activateProduction(activatedProductions,false,null,null,null);
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
            Player player = new Player("player1");
            game.addNewPlayer(player);
            game.setCurrentPlayer(player);

            LeaderCardDiscount leaderCardDiscount = new LeaderCardDiscount();

            System.out.println(leaderCardDiscount.getAbilityResource());
            System.out.println("passed");
        }catch (Exception e){
            System.out.println("eccezione");
        }
    }

    @Test
    public void testReadingLeaderCards() throws Exception{

        Game game = new Game();
        Controller controller = new Controller(game);
        Player player = new Player("player1");
        game.addNewPlayer(player);
        game.setCurrentPlayer(player);

        Configs.getLeaderCards();
        System.out.println("passed");

    }

    @Test
    public void readerLeaderCardTest () throws IOException {

        List<LeaderCard> leaderCardsList = Configs.getLeaderCards();
        leaderCardsList.forEach(System.out::println);

    }

    @Test //è difficile testare use market
    public void useMarketTest() throws Exception{
        Game game = new Game();
        Player player = new Player("");
        player.getStatusPlayer().addLeaderCard(new SonOfLeaderCard());
        player.getStatusPlayer().addLeaderCard(new SonOfLeaderCard());

        Controller controller = new Controller(game);
        game.addNewPlayer(player);
        game.setCurrentPlayer(player);
        PlayerWarehouse playerWarehouse = new PlayerWarehouse();
        playerWarehouse.insertResource(Resource.COIN,1,0);
        playerWarehouse.insertResource(Resource.SERVANT,2,1);
        playerWarehouse.insertResource(Resource.SERVANT,2,2);
        playerWarehouse.insertResource(Resource.SHIELD,3,1);
        playerWarehouse.insertResource(Resource.SHIELD,3,2);
        playerWarehouse.insertResource(Resource.SHIELD,3,3);

        player.getStatusPlayer().getPlayerWarehouse().setWarehouse(playerWarehouse);

        Map<Resource,Integer> discardedRes =  new HashMap<>(controller.fromMarblesToResources(game.getBoard().getMarket().getRowColors(0),false));

        controller.useMarket('r',0,playerWarehouse,discardedRes,0,0);

        playerWarehouse.removeResource(1,1);

       // assertThrows(IllegalMarketUseException.class,()->controller.useMarket('r',0,playerWarehouse,discardedRes,0,0));
        controller.useMarket('r',0,playerWarehouse,discardedRes,0,0);
        assert(game.getIllegalActions().size()==1);

    }

    @Test
    public void activateLeaderCardTest() throws IOException, InvalidWarehouseInsertionException {
        Game game = new Game();
        Controller controller = new Controller(game);
        Player player = new Player("nickname");
        game.addNewPlayer(player);
        game.setCurrentPlayer(player);
        List<LeaderCard> list = Configs.getLeaderCards();
        player.getStatusPlayer().addLeaderCard(list.get(0));
        controller.activateLeaderCard(0);
        //il player non ha risorse a sufficienza per attivare la carta
        assert(!player.getStatusPlayer().getLeaderCard(0).isActivated());
        player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.SHIELD, 2, 1);
        player.getStatusPlayer().getPlayerWarehouse().insertResource(Resource.SHIELD, 2, 2);
        //adesso il giocatore ha le risorse necessarie in quanto le risorse richieste per attivare la prima
        //carta sono due SHIELD (vedi file JSON)
        assert(player.getStatusPlayer().getLeaderCard(0).isActivated());

    }

    @Test
    public void discardLeaderCardTest() throws IOException {
        Game game = new Game();
        Controller controller = new Controller(game);
        Player player = new Player("nickname");
        game.addNewPlayer(player);
        game.setCurrentPlayer(player);
        List<LeaderCard> list = Configs.getLeaderCards();
        player.getStatusPlayer().addLeaderCard(list.get(0));
        player.getStatusPlayer().addLeaderCard(list.get(1));
        player.getStatusPlayer().getLeaderCard(1).activate();
        controller.discardLeaderCard(1);
        //il player non può scartare la carta in quanto già attivata
        assert(!player.getStatusPlayer().getLeaderCard(1).isDiscarded());
        controller.discardLeaderCard(0);
        //il player può scartare la carta
        assert(player.getStatusPlayer().getLeaderCard(0).isDiscarded());

    }

}

