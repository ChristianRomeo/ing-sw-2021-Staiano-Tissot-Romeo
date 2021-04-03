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
     * Otherwise, if the action picked is MOVETWO, it simply increase the black cross position twice.
     * @return SoloAction - type
     */
    public SoloAction pickSoloAction() {
        if(soloActions.get(0).getType().equals(SoloActionType.MOVEONEANDSHUFFLE)) {
            increaseBlackCrossPosition();
            shuffleSoloActionPile();
        }
        else if(soloActions.get(0).getType().equals(SoloActionType.MOVETWO)) {
            increaseBlackCrossPosition();
            increaseBlackCrossPosition();
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
