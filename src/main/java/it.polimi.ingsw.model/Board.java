package it.polimi.ingsw.model;

import java.util.List;

public class Board {
    private int blackCrossPosition;
    private Market market;
    private List<SoloAction> soloActions;
    private DevelopmentCardBoard developmentCardBoard;

    public int getBlackCrossPosition() {
        return blackCrossPosition;
    }
    public void increaseBlackCrossPosition() {


    }
    public void pickSoloAction() {

    }
    public void shuffleSoloActionPile() {

    }

    public Market getMarket(){
        return market;
    }

    public DevelopmentCardBoard getDevelopmentCardBoard(){
        return developmentCardBoard;
    }
}
