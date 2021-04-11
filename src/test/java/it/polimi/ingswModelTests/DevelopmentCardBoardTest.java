package it.polimi.ingswModelTests;
import it.polimi.ingsw.model.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests.
 */
public class DevelopmentCardBoardTest {
    @Test
    public void removeCardTest(){

    }
    @Test //test a caso
    public void cardsTest() throws IOException {

        DevelopmentCardBoard developmentCardBoard = new DevelopmentCardBoard();
        assert (developmentCardBoard.getCard(0,0).getLevel()
                <developmentCardBoard.getCard(1,0).getLevel());
        assert (developmentCardBoard.getCard(1,0).getLevel()
                <developmentCardBoard.getCard(2,0).getLevel());
        assert (developmentCardBoard.getCard(0,0).getType()
                !=developmentCardBoard.getCard(0,1).getType() &&
                developmentCardBoard.getCard(0,1).getType()
                        !=developmentCardBoard.getCard(0,2).getType()&&
                developmentCardBoard.getCard(0,2).getType()
                        !=developmentCardBoard.getCard(0,3).getType());
        assert (developmentCardBoard.getCard(1,0).getType()
                !=developmentCardBoard.getCard(1,1).getType() &&
                developmentCardBoard.getCard(1,1).getType()
                        !=developmentCardBoard.getCard(1,2).getType()&&
                developmentCardBoard.getCard(1,2).getType()
                        !=developmentCardBoard.getCard(1,3).getType());
        assert (developmentCardBoard.getCard(2,0).getType()
                !=developmentCardBoard.getCard(2,1).getType() &&
                developmentCardBoard.getCard(2,1).getType()
                        !=developmentCardBoard.getCard(2,2).getType()&&
                developmentCardBoard.getCard(2,2).getType()
                        !=developmentCardBoard.getCard(2,3).getType());
        assert developmentCardBoard.getPileSize(0,0)==4;
        developmentCardBoard.removeCard(0,0);
        assert developmentCardBoard.getPileSize(0,0)==3;
        developmentCardBoard.removeCard(0,0);
        developmentCardBoard.removeCard(0,0);
        developmentCardBoard.removeCard(0,0);
        assert developmentCardBoard.isCardPileEmpty(0,0);


    }
}
