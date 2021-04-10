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
    private static final int GREENCOLUMN = 0;
    private static final int BLUECOLUMN = 1;
    private static final int YELLOWCOLUMN = 2;
    private static final int PURPLECOLUMN = 3;
    private static final int MAXCARDSLEVEL = 3;
    private static final int firstActionPosition = 0;
    private static final int lastFaithTrackPosition = 24;

    private int blackCrossPosition;
    private int discardedCards = 0;
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
        if (getBlackCrossPosition() < lastFaithTrackPosition)
        ++blackCrossPosition;
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
    //TODO: DA SISTEMARE, PERCHE SE AD ESEMPIO C'E UNA SOLA CARTA DI LIVELLO 1 LUI DEVE SCARTARE QUELLA E POI PASSIAMO
    //TODO: A ELIMINARE QUELLE DI LIVELLO 2 O 3 (SE CI SONO). IN OGNI CASO SE C'E ANCHE SOLO UNA CARTA, LA TOGLIE.
    public SoloAction pickSoloAction() {
        switch (soloActions.get(firstActionPosition).getType()) {
            case MOVETWO -> {
                increaseBlackCrossPosition();
                increaseBlackCrossPosition();
                break;
            }
            case MOVEONEANDSHUFFLE -> {
                increaseBlackCrossPosition();
                shuffleSoloActionPile();
                break;

            }
            case DISCARDTWOCARDS -> {
                switch (soloActions.get(firstActionPosition).getDiscardedCardsType()) {
                    case GREEN -> {
                        for (int i = 0; i < MAXCARDSLEVEL; ++i) {
                            if(getDevelopmentCardBoard().getPileSize(i, GREENCOLUMN) == 0)
                                continue;
                            else if(getDevelopmentCardBoard().getPileSize(i, GREENCOLUMN) == 1) {
                                getDevelopmentCardBoard().removeCard(i, GREENCOLUMN);
                                ++discardedCards;

                            }
                            else {
                                getDevelopmentCardBoard().removeCard(i, GREENCOLUMN);
                                getDevelopmentCardBoard().removeCard(i, GREENCOLUMN);
                            }
                            if (discardedCards == 2)
                                break;
                        }
                        break;
                    }
                    case BLUE -> {
                        for (int i = 0; i < MAXCARDSLEVEL; ++i) {
                            if(getDevelopmentCardBoard().getPileSize(i, BLUECOLUMN) == 0)
                                continue;
                            else if(getDevelopmentCardBoard().getPileSize(i, BLUECOLUMN) == 1) {
                                getDevelopmentCardBoard().removeCard(i, BLUECOLUMN);
                                ++discardedCards;

                            }
                            else {
                                getDevelopmentCardBoard().removeCard(i, BLUECOLUMN);
                                getDevelopmentCardBoard().removeCard(i, BLUECOLUMN);
                            }
                            if (discardedCards == 2)
                                break;
                        }
                        break;
                    }
                    case YELLOW -> {
                        for (int i = 0; i < MAXCARDSLEVEL; ++i) {
                            if(getDevelopmentCardBoard().getPileSize(i, YELLOWCOLUMN) == 0)
                                continue;
                            else if(getDevelopmentCardBoard().getPileSize(i, YELLOWCOLUMN) == 1) {
                                getDevelopmentCardBoard().removeCard(i, YELLOWCOLUMN);
                                ++discardedCards;

                            }
                            else {
                                getDevelopmentCardBoard().removeCard(i, YELLOWCOLUMN);
                                getDevelopmentCardBoard().removeCard(i, YELLOWCOLUMN);
                            }
                            if (discardedCards == 2)
                                break;
                        }
                        break;
                    }
                    case PURPLE -> {
                        for (int i = 0; i < MAXCARDSLEVEL; ++i) {
                            if(getDevelopmentCardBoard().getPileSize(i, PURPLECOLUMN) == 0)
                                continue;
                            else if(getDevelopmentCardBoard().getPileSize(i, PURPLECOLUMN) == 1) {
                                getDevelopmentCardBoard().removeCard(i, PURPLECOLUMN);
                                ++discardedCards;

                            }
                            else {
                                getDevelopmentCardBoard().removeCard(i, PURPLECOLUMN);
                                getDevelopmentCardBoard().removeCard(i, PURPLECOLUMN);
                            }
                            if (discardedCards == 2)
                                break;
                        }
                        break;
                    }
                }
                discardedCards = 0; //restore the value of discardedCards to 0, so it will work for other DISCARDTWOCARDS actions
                break;
            }
        }

        return soloActions.remove(firstActionPosition);
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
