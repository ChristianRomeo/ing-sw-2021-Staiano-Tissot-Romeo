package it.polimi.ingsw.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class Board represents the Board object of the game which contains the progress of Lorenzo faith points: the blackCross
 * a List of solo Action to be used in the single player mode, for more:
 * @see SoloAction
 * @author chris, tommy, enrico
 */
public class Board {
    private static final int GREENCOLUMN = 0;
    private static final int BLUECOLUMN = 1;
    private static final int YELLOWCOLUMN = 2;
    private static final int PURPLECOLUMN = 3;
    private static final int MAXCARDSLEVEL = 3;
    private static final int firstActionPosition = 0;

    private final Market market;
    private final List<SoloAction> soloActions = new ArrayList<>();
    private final DevelopmentCardBoard developmentCardBoard;

    private final Player lorenzo;

    /**
     * Constructor that initializes the board.
     * @throws IOException from DevelopmentCardBoard where json is used
     */
    public Board() throws IOException {
        this.market = new Market();
        shuffleSoloActionPile();
        this.developmentCardBoard = new DevelopmentCardBoard();
        lorenzo= new Player("Lorenzo il Magnifico");
    }


    /**
     * <p> Return and remove from the pile of Solo Actions one element </p>
     * if the action picked is MOVEONEANDSHUFFLE then it increases the black cross position and shuffle the list.
     * If the action picked is MOVETWO, it simply increase the black cross position twice.
     * Otherwise, if the action picked is DISCARDTWOCARDS, the methods firstly checks which Card Type has been picked to be removed
     * (2 of them) and it checks whether the List of the Card Type picked has two cards of level 1 left;
     * If it has only one card of that type, it removes it and then goes on and check level 2 cards.
     * If it has , it goes on and checks for level 2, and so on until level 3 which is the maximum level for the cards.
     * IMPORTANT: keep in mind that we built the card's matrix with level 0 in the first row, contrary to the
     * game graphics; so, for example, to check the first level for green cards the method takes cardBoard[0][0].
     * @return SoloAction - type
     */
    public SoloAction pickSoloAction() {
        SoloAction removedSoloAction = soloActions.remove(firstActionPosition);
        switch (removedSoloAction.getType()) {
            case MOVETWO -> {
                //non fa niente, semplicemente ritorna il solo action selezionato e poi il controller agisce
            }
            case MOVEONEANDSHUFFLE -> //poi il controller incrementa posizione
                    shuffleSoloActionPile();   //DRY
            case DISCARDTWOCARDS -> {
                int discardedCards=0;
                int selectedColumn=0;

                switch (removedSoloAction.getDiscardedCardsType()) {
                    case GREEN -> selectedColumn = GREENCOLUMN;
                    case BLUE -> selectedColumn = BLUECOLUMN;
                    case YELLOW -> selectedColumn = YELLOWCOLUMN;
                    case PURPLE -> selectedColumn = PURPLECOLUMN;
                }
                for (int i = 0; i < MAXCARDSLEVEL; ++i) {
                    while (discardedCards<2 && getDevelopmentCardBoard().getPileSize(i,selectedColumn)>0){
                        getDevelopmentCardBoard().removeCard(i,selectedColumn);
                        discardedCards++;
                    }
                    if (discardedCards == 2)
                        break;
                }
            }
        }

        return removedSoloAction;
    }

    /**
     * <p>Clears the list and repopulates it, once done it shuffles it </p>
     * To be called during the init and when picked MOVEONEANDSHUFFLE
     */
    public void shuffleSoloActionPile() {
        soloActions.clear();
        this.soloActions.add(new SoloAction(SoloActionType.MOVETWO,null));
        this.soloActions.add(new SoloAction(SoloActionType.MOVETWO,null));
        this.soloActions.add(new SoloAction(SoloActionType.MOVEONEANDSHUFFLE,null));
        this.soloActions.add(new SoloAction(SoloActionType.DISCARDTWOCARDS,CardType.BLUE));
        this.soloActions.add(new SoloAction(SoloActionType.DISCARDTWOCARDS,CardType.YELLOW));
        this.soloActions.add(new SoloAction(SoloActionType.DISCARDTWOCARDS,CardType.PURPLE));
        this.soloActions.add(new SoloAction(SoloActionType.DISCARDTWOCARDS,CardType.GREEN));
        Collections.shuffle(soloActions);
    }

    /**
     * getter of the market
     * @return the market
     */
    public Market getMarket() {
        return market;
    }

    /**
     * getter of the development card board
     * @return the development card board
     */
    public DevelopmentCardBoard getDevelopmentCardBoard() {
        return developmentCardBoard;
    }

    /**
     * getter of Lorenzo player
     * @return the player Lorenzo
     */
    public Player getLorenzo(){
        return lorenzo;
    }
}
