package it.polimi.ingswModelTests;
import it.polimi.ingsw.model.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests.
 */
//DONE
public class DevelopmentCardBoardTest {

    @Test //test passed, it checks the levels of the cards created
    public void initDevelopmentCardboardTest1() throws IOException {

        DevelopmentCardBoard developmentCardBoard = new DevelopmentCardBoard();
        for(int i=0; i<3;i++){
            for(int j=0; j<4;j++){
                assert (developmentCardBoard.getCard(i,j).getLevel()==i+1);
            }
        }
    }

    @Test //test passed, it checks the types of the cards created
    public void initDevelopmentCardboardTest2() throws IOException {

        DevelopmentCardBoard developmentCardBoard = new DevelopmentCardBoard();
        for(int i=0; i<3;i++){
            for(int j=0; j<4;j++){
                if(j==0){
                    assert (developmentCardBoard.getCard(i,j).getType()==CardType.GREEN);
                }
                if(j==1){
                    assert (developmentCardBoard.getCard(i,j).getType()==CardType.BLUE);
                }
                if(j==2){
                    assert (developmentCardBoard.getCard(i,j).getType()==CardType.YELLOW);
                }
                if(j==3){
                    assert (developmentCardBoard.getCard(i,j).getType()==CardType.PURPLE);
                }
            }
        }
    }

    @Test //test passed
    public void removeCardTest() throws IOException {

        DevelopmentCardBoard developmentCardBoard = new DevelopmentCardBoard();
        for(int i=0; i<3;i++){
            for(int j=0; j<4;j++){
                assert (developmentCardBoard.getPileSize(i,j)==4);
            }
        }
        developmentCardBoard.removeCard(2,3);
        assert (developmentCardBoard.getPileSize(2,3)==3);
        developmentCardBoard.removeCard(2,3);
        developmentCardBoard.removeCard(2,3);
        developmentCardBoard.removeCard(2,3);
        assert (developmentCardBoard.getPileSize(2,3)==0);
        assert (developmentCardBoard.isCardPileEmpty(2,3));
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
