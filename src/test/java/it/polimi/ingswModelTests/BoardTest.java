package it.polimi.ingswModelTests;

/**
 * Unit tests.      NON FUNZIONANO GLI ASSERT
 */
public class BoardTest {
    /*
    @Test
    public void shuffleTest() throws IOException { //a volte sto test fallisce
        Board board = new Board();
        DevelopmentCardBoard devcardb = new DevelopmentCardBoard();
        SoloAction s1= board.pickSoloAction();
        int oldNumberOfGreen = devcardb.getPileSize(0,0)+devcardb.getPileSize(1,0)+devcardb.getPileSize(2,0);
        int oldNumberOfBlue = devcardb.getPileSize(0,1)+devcardb.getPileSize(1,1)+devcardb.getPileSize(2,1);
        int oldNumberOfYellow = devcardb.getPileSize(0,2)+devcardb.getPileSize(1,2)+devcardb.getPileSize(2,2);
        int oldNumberOfPurple = devcardb.getPileSize(0,3)+devcardb.getPileSize(1,3)+devcardb.getPileSize(2,3);
        int oldBlackCrossPosition = board.getBlackCrossPosition();
        switch (s1.getType()){
            case MOVETWO -> {
                assert board.getBlackCrossPosition() == oldBlackCrossPosition + 2;
            }
            case MOVEONEANDSHUFFLE -> {
                assert board.getBlackCrossPosition()== oldBlackCrossPosition + 1;
            }
            case DISCARDTWOCARDS -> {
                switch (s1.getDiscardedCardsType()){
                    case GREEN -> {
                        if(oldNumberOfGreen == 0)
                            assert devcardb.getPileSize(0,0)+devcardb.getPileSize(1,0)+devcardb.getPileSize(2,0) == 0;
                        else
                            assert (devcardb.getPileSize(0,0)+devcardb.getPileSize(1,0)+devcardb.getPileSize(2,0) < oldNumberOfGreen);
                    }
                    case BLUE -> {
                        if(oldNumberOfBlue == 0)
                            assert devcardb.getPileSize(0,1)+devcardb.getPileSize(1,1)+devcardb.getPileSize(2,1) == 0;
                        else
                            assert (devcardb.getPileSize(0,1)+devcardb.getPileSize(1,1)+devcardb.getPileSize(2,1) < oldNumberOfBlue);
                    }
                    case YELLOW -> {
                        if(oldNumberOfYellow == 0)
                            assert devcardb.getPileSize(0,2)+devcardb.getPileSize(1,2)+devcardb.getPileSize(2,2) == 0;
                        else
                            assert (devcardb.getPileSize(0,2)+devcardb.getPileSize(1,2)+devcardb.getPileSize(2,2) < oldNumberOfYellow);
                    }
                    case PURPLE -> {
                        if(oldNumberOfPurple == 0)
                            assert devcardb.getPileSize(0,3)+devcardb.getPileSize(1,3)+devcardb.getPileSize(2,3) == 0;
                        else
                            assert (devcardb.getPileSize(0,3)+devcardb.getPileSize(1,3)+devcardb.getPileSize(2,3) < oldNumberOfPurple);
                    }
                }
            }
        }
    }
    */

}
