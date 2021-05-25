package it.polimi.ingswModelTests;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.SameTypePair;
import org.junit.jupiter.api.Test;

public class SameTypePairTest {

    @Test
    public void getAndSetVal1Test() {
        SameTypePair sameTypePair = new SameTypePair();
        sameTypePair.setVal1(Resource.COIN);
        assert(!sameTypePair.getVal1().equals(Resource.SERVANT));
        assert(!sameTypePair.getVal1().equals(Resource.SHIELD));
        assert(!sameTypePair.getVal1().equals(Resource.STONE));
        assert(sameTypePair.getVal1().equals(Resource.COIN));
    }

    @Test
    public void getAndSetVal2Test() {
        SameTypePair sameTypePair = new SameTypePair();
        sameTypePair.setVal2(Resource.COIN);
        assert(!sameTypePair.getVal2().equals(Resource.SERVANT));
        assert(!sameTypePair.getVal2().equals(Resource.SHIELD));
        assert(!sameTypePair.getVal2().equals(Resource.STONE));
        assert(sameTypePair.getVal2().equals(Resource.COIN));
    }
}
