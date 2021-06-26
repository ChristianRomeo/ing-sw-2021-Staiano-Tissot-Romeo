package it.polimi.ingswModelTests;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.SameTypeTriple;
import org.junit.jupiter.api.Test;

public class SameTypeTripleTest {

    @SuppressWarnings("unchecked")
    @Test
    public void getAndSetVal1Test() {
        SameTypeTriple sameTypeTriple = new SameTypeTriple();
        sameTypeTriple.setVal1(Resource.COIN);
        assert(!sameTypeTriple.getVal1().equals(Resource.SERVANT));
        assert(!sameTypeTriple.getVal1().equals(Resource.SHIELD));
        assert(!sameTypeTriple.getVal1().equals(Resource.STONE));
        assert(sameTypeTriple.getVal1().equals(Resource.COIN));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getAndSetVal2Test() {
        SameTypeTriple sameTypeTriple = new SameTypeTriple();
        sameTypeTriple.setVal2(Resource.COIN);
        assert(!sameTypeTriple.getVal2().equals(Resource.SERVANT));
        assert(!sameTypeTriple.getVal2().equals(Resource.SHIELD));
        assert(!sameTypeTriple.getVal2().equals(Resource.STONE));
        assert(sameTypeTriple.getVal2().equals(Resource.COIN));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getAndSetVal3Test() {
        SameTypeTriple sameTypeTriple = new SameTypeTriple();
        sameTypeTriple.setVal3(Resource.COIN);
        assert(!sameTypeTriple.getVal3().equals(Resource.SERVANT));
        assert(!sameTypeTriple.getVal3().equals(Resource.SHIELD));
        assert(!sameTypeTriple.getVal3().equals(Resource.STONE));
        assert(sameTypeTriple.getVal3().equals(Resource.COIN));
    }
}
