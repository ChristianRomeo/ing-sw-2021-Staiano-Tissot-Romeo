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

    @Test //test passed
    public void removeCardTest() throws IOException {

        DevelopmentCardBoard developmentCardBoard = new DevelopmentCardBoard();
        for(int i=0; i<3;i++)
            for(int j=0; j<4;j++)
                assert (developmentCardBoard.getPileSize(i,j)==4);

        developmentCardBoard.removeCard(2,3);
        assert (developmentCardBoard.getPileSize(2,3)==3);
        developmentCardBoard.removeCard(2,3);
        developmentCardBoard.removeCard(2,3);
        developmentCardBoard.removeCard(2,3);
        assert (developmentCardBoard.getPileSize(2,3)==0);
        assert (developmentCardBoard.isCardPileEmpty(2,3));
    }

    @Test //test inizializzazione carte
    public void cardsTest() throws IOException {

        DevelopmentCardBoard developmentCardBoard = new DevelopmentCardBoard();
        //controlla che la riga 0 ha il livello 1, riga 1 livello 2, riga 3 livello 2.
        for(int i=0; i<3;i++){
            for(int j=0; j<4;j++){
                assert (developmentCardBoard.getCard(i,j).getLevel()==i+1);
            }
        }

        //il livello di righe superiori è più alto
        assert (developmentCardBoard.getCard(0,0).getLevel()
                <developmentCardBoard.getCard(1,0).getLevel());
        assert (developmentCardBoard.getCard(1,0).getLevel()
                <developmentCardBoard.getCard(2,0).getLevel());

        //il tipo di carte di colonne diverse è diverso
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

        //i colori delle carte sono giusti
        for(int i = 0; i < 3; ++i){
            assert developmentCardBoard.getCard(i,0).getType() == CardType.GREEN;
            assert developmentCardBoard.getCard(i,1).getType() == CardType.BLUE;
            assert developmentCardBoard.getCard(i,2).getType() == CardType.YELLOW;
            assert developmentCardBoard.getCard(i,3).getType() == CardType.PURPLE;
        }
    }
}
