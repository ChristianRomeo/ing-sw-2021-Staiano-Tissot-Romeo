package it.polimi.ingswModelTests;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests.
 */
public class BoardTest {
    /*
    @Test
    public void shuffleTest() throws IOException { //a volte sto test fallisce
        Board board = new Board();
        SoloAction s1= board.pickSoloAction();
        if (s1.getType()==SoloActionType.MOVETWO)
            assert board.getBlackCrossPosition()==2;
        if (s1.getType()==SoloActionType.MOVEONEANDSHUFFLE)
            assert board.getBlackCrossPosition()==1;

        board.shuffleSoloActionPile();
        SoloAction s2= board.pickSoloAction();



    }
    */

    @Test
    public void soloActionShuffleTest(){

    }
    @Test
    public void IncreaseBlackCrossTest(){

    }
}
