package it.polimi.ingsw.model;

public class PersonalCardBoard {
    private int numberOfCards;
    private DevelopmentCard[][] developmentCards;

    public int getNumberOfCards() {
        return numberOfCards;
    }
    public DevelopmentCard getCard(int i, int j) {
        return developmentCards[i][j];
    }
    public void addCard(DevelopmentCard card, int i) {

    }
}
