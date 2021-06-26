package it.polimi.ingswModelTests;
import it.polimi.ingsw.model.Pair;
import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.Test;

public class PairTest {

    @SuppressWarnings("unchecked")
    @Test
    public void getAndSetVal1Test() {
        Pair pair = new Pair();
        Player player = new Player("tom");
        boolean check;
        pair.setVal1(player.getNickname());
        check = pair.getVal1().equals("chris");
        assert(!check);
        check = pair.getVal1().equals("tom");
        assert(check);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getAndSetVal2Test() {
        Pair pair = new Pair();
        Player player = new Player("enrico");
        boolean check;
        pair.setVal2(player.getNickname());
        check = pair.getVal2().equals("chris");
        assert(!check);
        check = pair.getVal2().equals("enrico");
        assert(check);
    }
}

