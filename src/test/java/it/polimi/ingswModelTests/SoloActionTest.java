package it.polimi.ingswModelTests;

import it.polimi.ingsw.model.CardType;
import it.polimi.ingsw.model.SoloAction;
import it.polimi.ingsw.model.SoloActionType;
import org.junit.jupiter.api.Test;

/**
 * Unit tests.
 */
public class SoloActionTest {
    @Test
    public void pickOneTile(){

    }

    @Test
    public void getTypeTest(){
        SoloActionType soloActionType = SoloActionType.MOVETWO;
        SoloAction soloAction = new SoloAction(soloActionType, null);
        assert(!soloAction.getType().equals(SoloActionType.DISCARDTWOCARDS));
        assert(!soloAction.getType().equals(SoloActionType.MOVEONEANDSHUFFLE));
        assert(soloAction.getType().equals(SoloActionType.MOVETWO));
    }

    @Test
    public void getDiscardedCardsTypeTest(){
        SoloActionType soloActionType = SoloActionType.DISCARDTWOCARDS;
        SoloAction soloAction = new SoloAction(soloActionType, CardType.BLUE);
        assert(!soloAction.getDiscardedCardsType().equals(CardType.YELLOW));
        assert(!soloAction.getDiscardedCardsType().equals(CardType.GREEN));
        assert(!soloAction.getDiscardedCardsType().equals(CardType.PURPLE));
        assert(soloAction.getDiscardedCardsType().equals(CardType.BLUE));
    }
}
