package it.polimi.ingsw.model;

import modelExceptions.InvalidCardInsertionException;

import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author enrico
 */
public class PersonalCardBoard {
    private int numberOfCards;
    private List<List<DevelopmentCard>> ownedCards;     //final?

    public PersonalCardBoard(){
        ownedCards = new ArrayList<>();
        ownedCards.add(new ArrayList<>());
        ownedCards.add(new ArrayList<>());
        ownedCards.add(new ArrayList<>());
        numberOfCards = 0;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    /**
     *   this method tells you if you can buy a card of level "level" or not (because there is no space)
     *   level must be between 1 and 3
     */
    public boolean canBuyCardOfLevel(int level){
        for(int i=0; i<=2; i++)
            if(ownedCards.get(i).size() == level-1)
                return true;

        return false;
    }

    /**
     * Method getCard takes a int i between 0 and 2 to select one of the three pile of owned cards,
     * and a int j (between 0 and the size of the pile -1) and returns the owned development
     * card in that position
     * @param pile it's the number of the pile you want to select (0,1,2)
     * @param depth it's the number of the card you want to select in that pile
     * @return the card in that postion
     */
    public DevelopmentCard getCard(int pile, int depth) {
        if(depth>=0 && depth<ownedCards.get(pile).size())
            return ownedCards.get(pile).get(depth);

        return null;
    }

    /**
     * Method getUpperCard takes a int i between 0 and 2 to select one of the three pile of owned cards,
     * and returns the card on top of that pile
     *
     * @param pile it's the number of the pile you want to select (0,1,2)
     * @return the card on top of the selected pile
     */
    public DevelopmentCard getUpperCard(int pile) {
        if(ownedCards.get(pile).size()>0){
            return ownedCards.get(pile).get(ownedCards.get(pile).size()-1);
        }
        return null;
    }
    /**
     * Method addCard takes a int i between 0 and 2 to select one of the three pile of owned cards,
     * and add a card on top of that pile. It checks that the insertion in that pile respects
     * the rules of the game (level 2 on level 1 ecc), and if the insertion is invalid it throws an
     * exception.
     *
     * @param pile it's the number of the pile you want to select (0,1,2)
     * @param card is the card you want to add
     */
    public void addCard(DevelopmentCard card, int pile) throws InvalidCardInsertionException {
        if(card.getLevel()==(ownedCards.get(pile).size()+1)){
            ownedCards.get(pile).add(card);
            numberOfCards++;
        }else
            throw new InvalidCardInsertionException();
    }
}
