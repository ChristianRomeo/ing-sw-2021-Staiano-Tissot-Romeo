package it.polimi.ingswModelTests;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Unit tests.
 */
public class GameTest {
    @Test
    public void initGameTest(){

    }

    @Test
    public void getAndSetWantedNumPlayersTest() throws IOException {
        Game game = new Game();
        boolean check;
        game.setWantedNumPlayers(3);
        check = game.getWantedNumPlayers() == 1;
        assert(!check);
        check = game.getWantedNumPlayers() == 3;
        assert(check);
    }

    @Test
    public void getPlayersTest() throws IOException {
        Game game = new Game();
        Player player1 = new Player("tom");
        Player player2 = new Player("chris");
        Player player3 = new Player("enrico");
        game.addNewPlayer(player1);
        game.addNewPlayer(player2);
        game.addNewPlayer(player3);
        assert(game.getPlayers().size() == 3);
        assert(game.getPlayers().get(0).getNickname().equals("tom"));
        assert(game.getPlayers().get(1).getNickname().equals("chris"));
        assert(game.getPlayers().get(2).getNickname().equals("enrico"));
    }

    @Test
    public void getPlayersByNicknameTest() throws IOException {
        Game game = new Game();
        Player player1 = new Player("tom");
        boolean check;
        game.addNewPlayer(player1);
        check = game.getPlayerByNickname("nick") != null;
        assert(!check);
        check = game.getPlayerByNickname("tom") != null;
        assert(check);
    }

    @Test
    public void getPlayerByIndexTest() throws IOException {
        Game game = new Game();
        Player player1 = new Player("tom");
        Player player2 = new Player("chris");
        Player player3 = new Player("enrico");
        game.addNewPlayer(player1);
        game.addNewPlayer(player2);
        game.addNewPlayer(player3);
        assert(game.getPlayerByIndex(0).getNickname().equals(player1.getNickname()));
        assert(game.getPlayerByIndex(1).getNickname().equals(player2.getNickname()));
        assert(game.getPlayerByIndex(2).getNickname().equals(player3.getNickname()));
    }

    @Test
    public void isActiveTest() throws IOException {
        Game game = new Game();
        assert(game.isActive());
        game.setInactive();
        assert(!game.isActive());
    }
}

