package it.polimi.ingswControllerTests;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.SonOfLeaderCard;
import org.junit.jupiter.api.Test;

public class ClientServerTests {

    @Test
    public void initServer() {

    }

    @Test
    public void messageSC() throws Exception {
        Game game = new Game();
        Player player = new Player("");
        player.getStatusPlayer().addLeaderCard(new SonOfLeaderCard());
        player.getStatusPlayer().addLeaderCard(new SonOfLeaderCard());
        game.addNewPlayer(player);
        game.setCurrentPlayer(player);
        Controller controller = new Controller(game);
        VirtualView virtualView= new VirtualView(controller);
        controller.setVirtualView(virtualView);

        controller.discardLeaderCard(0);

        controller.buyDevelopmentCard(0,0,0);
    }
}
