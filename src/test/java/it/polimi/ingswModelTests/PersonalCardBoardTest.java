package it.polimi.ingswModelTests;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.*;

import it.polimi.ingsw.model.modelExceptions.CannotBuyCardException;
import it.polimi.ingsw.model.modelExceptions.InvalidCardInsertionException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests.
 */
public class PersonalCardBoardTest {

    @Test //test passed, card added successfully
    public void addCardTest1() throws Exception{
        PersonalCardBoard personalCardBoard = new PersonalCardBoard();
        DevelopmentCardBoard developmentCardBoard =new DevelopmentCardBoard();
        DevelopmentCard card = developmentCardBoard.getCard(0,0); // card of level 1

        personalCardBoard.addCard(card,0);

        assert personalCardBoard.getNumberOfCards()==1;
        assert personalCardBoard.getUpperCard(0)==card;
        assert personalCardBoard.getCard(0,0)==card;
        assert !personalCardBoard.isCardPileEmpty(0);
        assert personalCardBoard.canInsertCardOfLevel(2,0);
    }

    @Test //test passed, you can't insert a card of level two on an empty space.
    public void addCardTest2() throws Exception{
        PersonalCardBoard personalCardBoard = new PersonalCardBoard();
        DevelopmentCardBoard developmentCardBoard =new DevelopmentCardBoard();
        DevelopmentCard card = developmentCardBoard.getCard(1,0); // card of level 2

        assertThrows(InvalidCardInsertionException.class, ()-> personalCardBoard.addCard(card,0));
        assert personalCardBoard.getNumberOfCards()==0;
        assert personalCardBoard.isCardPileEmpty(0);
        assert personalCardBoard.canInsertCardOfLevel(1,0);
    }
}
