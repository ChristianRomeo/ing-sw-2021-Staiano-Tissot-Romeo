package it.polimi.ingsw.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class Board represents the Board object of the game which contains the progress of Lorenzo faith points: the blackCross
 * a List of solo Action to be used in the single player mode, for more:
 * @see SoloAction
 * @author chris, tommy
 */
public class Board {
    public static final int GREENCOLUMN = 0;
    public static final int BLUECOLUMN = 1;
    public static final int YELLOWCOLUMN = 2;
    public static final int PURPLECOLUMN = 3;
    public static final int MAXCARDSLEVEL = 3;

    private int blackCrossPosition;
    private final Market market;
    private final List<SoloAction> soloActions = new ArrayList<>();
    private final DevelopmentCardBoard developmentCardBoard;

    /**
     * Constructor that initializes blackCross position and the Solo Actions pile
     * @throws IOException from DevelopmentCardBoard where json is used
     */
    public Board() throws IOException {
        this.blackCrossPosition = 0;
        this.market = new Market();
        shuffleSoloActionPile();
        this.developmentCardBoard = new DevelopmentCardBoard();

    }

    /**
     * Getter of BlackCrossPosition
     * @return blackCrossPosition - int
     */
    public int getBlackCrossPosition() {
        return blackCrossPosition;
    }

    /**
     * Method that shifts by one position the BlackCross
     */
    public void increaseBlackCrossPosition() {
        ++blackCrossPosition;
    }

    /**
     * <p> Return and remove from the pile of Solo Actions one element </p>
     * if the action picked is MOVEONEANDSHUFFLE then it increases the black cross position and shuffle the list.
     * If the action picked is MOVETWO, it simply increase the black cross position twice.
     * Otherwise, if the action picked is DISCARDTWOCARDS, the methods firstly checks which Card Type has been picked to be removed
     * (2 of them) and it checks whether the List of the Card Type picked has two or more cards of level 1 left;
     * If not, it goes on and checks for level 2, and so on until level 3 which is the maximum level for the cards.
     * IMPORTANT: keep in mind that we buildt the card's matrix with level 0 in the first row, contrary to the
     * game graphics; so, for example, to check the first level for green cards the method takes cardBoard[0][0].
     * @return SoloAction - type
     */
    //TODO: DA SISTEMARE, PERCHE SE AD ESEMPIO C'E UNA SOLA CARTA DI LIVELLO 1 LUI DEVE SCARTARE QUELLA E POI PASSIAMO
    //TODO: A ELIMINARE QUELLE DI LIVELLO 2 O 3 (SE CI SONO). IN OGNI CASO SE C'E ANCHE SOLO UNA CARTA, LA TOGLIE.
    public SoloAction pickSoloAction() {
        switch (soloActions.get(0).getType()) {
            case MOVETWO -> {
                increaseBlackCrossPosition();
                increaseBlackCrossPosition();
            }
            case MOVEONEANDSHUFFLE -> {
                increaseBlackCrossPosition();
                shuffleSoloActionPile();

            }
            case DISCARDTWOCARDS -> {
                switch (soloActions.get(0).getDiscardedCardsType()) {
                    case GREEN -> {
                        for (int i = 0; i < MAXCARDSLEVEL; i++) {
                            if (getDevelopmentCardBoard().getPileSize(i,GREENCOLUMN) >= 2) {
                                getDevelopmentCardBoard().removeCard(i, GREENCOLUMN);
                                getDevelopmentCardBoard().removeCard(i, GREENCOLUMN);
                                break;
                            }
                        }
                    }
                    case BLUE -> {
                        for (int i = 0; i < MAXCARDSLEVEL; i++) {
                            if (getDevelopmentCardBoard().getPileSize(i,BLUECOLUMN) >= 2) {
                                getDevelopmentCardBoard().removeCard(i, BLUECOLUMN);
                                getDevelopmentCardBoard().removeCard(i, BLUECOLUMN);
                                break;
                            }
                        }
                    }
                    case YELLOW -> {
                        for (int i = 0; i < MAXCARDSLEVEL; i++) {
                            if (getDevelopmentCardBoard().getPileSize(i,YELLOWCOLUMN) >= 2) {
                                getDevelopmentCardBoard().removeCard(i, YELLOWCOLUMN);
                                getDevelopmentCardBoard().removeCard(i, YELLOWCOLUMN);
                                break;
                            }
                        }
                    }
                    case PURPLE -> {
                        for (int i = 0; i < MAXCARDSLEVEL; i++) {
                            if (getDevelopmentCardBoard().getPileSize(i,PURPLECOLUMN) >= 2) {
                                getDevelopmentCardBoard().removeCard(i, PURPLECOLUMN);
                                getDevelopmentCardBoard().removeCard(i, PURPLECOLUMN);
                                break;
                            }
                        }
                    }
                }
            }
        }

        return soloActions.remove(0);
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

    public Market getMarket() {
        return market;
    }

    public DevelopmentCardBoard getDevelopmentCardBoard() {
        return developmentCardBoard;
    }
}
