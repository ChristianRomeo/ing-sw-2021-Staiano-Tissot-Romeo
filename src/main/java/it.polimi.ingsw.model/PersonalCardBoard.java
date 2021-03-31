package it.polimi.ingsw.model;

import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author enrico
 */
public class PersonalCardBoard {
    private int numberOfCards;
    private List<List<DevelopmentCard>> ownedCards;     //List<DevelopmentCard>[3]?

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
     * Method getCard takes a int i between 0 and 2 to select one of the three pile of owned cards,
     * and a int j (between 0 and the size of the pile -1) and returns the owned development
     * card in that position
     * @param i it's the number of the pile you want to select (0,1,2)
     * @param j it's the number of the card you want to select in that pile
     * @return the card in that postion
     */
    public DevelopmentCard getCard(int i, int j) {
        if(j>=0 && j<ownedCards.get(i).size()){
            return ownedCards.get(i).get(j);
        }
        return null;
    }

    /**
     * Method getUpperCard takes a int i between 0 and 2 to select one of the three pile of owned cards,
     * and returns the card on top of that pile
     *
     * @param i it's the number of the pile you want to select (0,1,2)
     * @return the card on top of the selected pile
     */
    public DevelopmentCard getUpperCard(int i) {
        if(ownedCards.get(i).size()>0){
            return ownedCards.get(i).get(ownedCards.get(i).size()-1);
        }
        return null;
    }
    /**
     * Method addCard takes a int i between 0 and 2 to select one of the three pile of owned cards,
     * and add a card on top of that pile. It checks that the insertion in that pile respects
     * the rules of the game (level 2 on level 1 ecc), and if the insertion is invalid it throws an
     * exception.
     *
     * @param i it's the number of the pile you want to select (0,1,2)
     * @param card is the card you want to add
     */
    public void addCard(DevelopmentCard card, int i) throws InvalidCardInsertionException{
        if(card.getLevel()==(ownedCards.get(i).size()+1)){
            ownedCards.get(i).add(card);
            numberOfCards++;
        }else{
            throw new InvalidCardInsertionException();
        }
    }
}
