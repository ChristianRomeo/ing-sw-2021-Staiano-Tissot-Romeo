package it.polimi.ingsw;

import java.util.List;

public class DevelopmentCardBoard {
    //private DevelopmentCard[][][] developmentCards;   SCOMODO
    private List<DevelopmentCard>[][] developmentCards;

    public DevelopmentCard getCard(int i, int j) {
        if(!isCardPileEmpty(i,j)) {
            return developmentCards[i][j].get(developmentCards[i][j].size() - 1);
        }
        else{
            return null; //exception is better
        }
    }
    public void removeCard(int i, int j) {
        if(!isCardPileEmpty(i,j)){
            developmentCards[i][j].remove(developmentCards[i][j].size()-1);
        }
    }
    public boolean isCardPileEmpty(int i, int j) {
        return (developmentCards[i][j] == null || developmentCards[i][j].size() == 0);
    }
}
