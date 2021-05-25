package it.polimi.ingswModelTests;
import it.polimi.ingsw.model.Triple;
import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.Test;

public class TripleTest {

    @Test
    public void getAndSetVal1Test() {
        Triple triple = new Triple();
        Player player = new Player("tom");
        boolean check;
        triple.setVal1(player.getNickname());
        check = triple.getVal1().equals("chris");
        assert(!check);
        check = triple.getVal1().equals("tom");
        assert(check);
    }

    @Test
    public void getAndSetVal2Test() {
        Triple triple = new Triple();
        Player player = new Player("enrico");
        boolean check;
        triple.setVal2(player.getNickname());
        check = triple.getVal2().equals("chris");
        assert(!check);
        check = triple.getVal2().equals("enrico");
        assert(check);
    }

    @Test
    public void getAndSetVal3Test() {
        Triple triple = new Triple();
        Player player = new Player("nick");
        boolean check;
        triple.setVal3(player.getNickname());
        check = triple.getVal3().equals("tom");
        assert(!check);
        check = triple.getVal3().equals("nick");
        assert(check);
    }
}
