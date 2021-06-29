package it.polimi.ingsw.model;

import it.polimi.ingsw.model.modelExceptions.InvalidCardInsertionException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PersonalCardBoard class represents the personal Development card board of each player
 */
public class PersonalCardBoard implements Serializable {
    private int numberOfCards;
    private final List<List<DevelopmentCard>> ownedCards;     //final?
    /**
     * constructor
     */
    public PersonalCardBoard(){
        ownedCards = new ArrayList<>();
        ownedCards.add(new ArrayList<>());
        ownedCards.add(new ArrayList<>());
        ownedCards.add(new ArrayList<>());
        numberOfCards = 0;
    }
    /**
     * getter of the number of cards in the personal card board
     * @return the number of cards in the personal card board
     */
    public int getNumberOfCards() {
        return numberOfCards;
    }

    /**
     *tells you if you can insert a card of level "level" or not (because there is no space),
     *in the pile selected. level must be between 1 and 3 and pile between 0 and 2.
     */
    public boolean canInsertCardOfLevel(int level, int pile){
        return level==(ownedCards.get(pile).size()+1);
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
        if(ownedCards.get(pile).size()>0)
            return ownedCards.get(pile).get(ownedCards.get(pile).size()-1);

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
        if(canInsertCardOfLevel(card.getLevel(),pile)){
            ownedCards.get(pile).add(card);
            numberOfCards++;
        }else
            throw new InvalidCardInsertionException();
    }

    /**
     * tells if the selected personal card pile is empty
     * @param pile is the selected pile index
     * @return true if the selected personal card pile is empty, false otherwise
     */
    public boolean isCardPileEmpty(int pile) {
        return (ownedCards.get(pile)== null || ownedCards.get(pile).size() == 0);
    }

    /**
     * you give to this method a list in which you say the positions of the card you want to activate for
     * the production, and it returns the required resources for that production.
     * Example: if you want to activate the production for cards in position 0 and 2
     *          you have to pass a list =(0,2)
     * @param activatedProductions is the the List containing the positions of the card to be activated for the production
     * @return the required resources for that production
     */
    public Map<Resource,Integer> getReqResProduction(List<Integer> activatedProductions) throws IllegalArgumentException{   //nome più intuitivo
        if (activatedProductions==null)
            throw new IllegalArgumentException();

        Map<Resource,Integer> requiredResources = new HashMap<>();
        for(Integer pos: activatedProductions){
            if(isCardPileEmpty(pos))
                throw new IllegalArgumentException();

            requiredResources = Resource.sumResourcesMap(requiredResources,getUpperCard(pos).getRequiredResources());
        }
        return requiredResources;
    }

    /**
     * you give to this method a list in which you say the positions of the card you want to activate for
     * the production, and it returns the produced resources of that production.
     * Example: if you want to activate the production for cards in position 0 and 2
     *          you have to pass a list =(0,2)
     * NB: it doesn't return the produced faith points
     * @param activatedProductions is the the List containing the positions of the card to be activated for the production
     * @return the required resources for that production
     */
    public Map<Resource,Integer> getProductionResources(List<Integer> activatedProductions) throws IllegalArgumentException{
        if (activatedProductions==null)
            throw new IllegalArgumentException();

        Map<Resource,Integer> producedResources = new HashMap<>();
        for(Integer pos: activatedProductions){
            if(isCardPileEmpty(pos))
                throw new IllegalArgumentException();

            producedResources = Resource.sumResourcesMap(producedResources,getUpperCard(pos).getProducedResources());
        }
        return producedResources;
    }

    /**
     * you give to this method a list in which you say the positions of the card you want to activate for
     * the production, and it returns the produced faith points of that production.
     * Example: if you want to activate the production for cards in position 0 and 2
     *          you have to pass a list =(0,2)
     */
    public int getProductionFP(List<Integer> activatedProductions) throws IllegalArgumentException{
        if (activatedProductions==null)
            throw new IllegalArgumentException();

        int producedFP=0;
        for(Integer pos: activatedProductions){
            if(isCardPileEmpty(pos))
                throw new IllegalArgumentException();

            producedFP = producedFP + getUpperCard(pos).getProducedFaithPoints();
        }
        return producedFP;
    }

    /**
     * this method returns a map with how many cards of each type there are in this personal card board.
     */
    public Map<CardType,Integer> getCardsType(){
        Map<CardType,Integer> cardsType = new HashMap<>();
        for(List<DevelopmentCard> pile: ownedCards) {
            for(DevelopmentCard card: pile) {
                if (!cardsType.containsKey(card.getType()))
                    cardsType.put(card.getType(), 1);
                else
                    cardsType.put(card.getType(), cardsType.get(card.getType()) + 1);
            }
        }
        return cardsType;
    }

    /**
     * this method tells you if the personal board contains a card of a certain type and level.
     */
    public boolean containsTypeLevel(CardType cardType, int level){
        for(List<DevelopmentCard> pile: ownedCards) {
            for(DevelopmentCard card: pile) {
                if (card.getType()==cardType && card.getLevel()==level)
                    return true;
            }
        }
        return false;
    }
}
