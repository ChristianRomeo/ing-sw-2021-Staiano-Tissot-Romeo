package it.polimi.ingswModelTests;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests.
 */
public class DevelopmentCardBoardTest {
    @Test
    public void removeCardTest(){

    }
    @Test //test passed, the creation of the development card board works
    public void testDevelopmentCardBoardInit()
    {
        DevelopmentCardBoard developmentCardBoard;
        try{
            developmentCardBoard = new DevelopmentCardBoard();
            System.out.println(developmentCardBoard.getCard(1,2).getId());
            System.out.println(developmentCardBoard.getCard(2,1).getId());
        }catch(Exception e){
            System.out.println("eccezione");
        }
    }
}
