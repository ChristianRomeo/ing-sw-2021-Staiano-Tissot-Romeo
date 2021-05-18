package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Here lies the marble matrix logic, with initialization and player selection
 * @author enrico
 */
public class Market implements Serializable {
    public static final int MAXROWS=3;
    public static final int MAXCOLUMNS=4;

    private final MarbleColor[][] marbles;
    private MarbleColor slideMarble;

    public Market(){
        marbles = new MarbleColor[MAXROWS][MAXCOLUMNS];
        initMarket();
    }

    //NB: the first row of the market from above is the row 0 of the matrix,
    // the second row of the market from above is the row 1 of the matrix ecc.

    /**
     * Method initMarket is an helper private method called by the constructor.
     * It initialize the Market board.
     */
    private void initMarket(){
        List<MarbleColor> colors = new ArrayList<>();

        colors.add(MarbleColor.WHITE);
        colors.add(MarbleColor.WHITE);
        colors.add(MarbleColor.WHITE);
        colors.add(MarbleColor.WHITE);
        colors.add(MarbleColor.BLUE);
        colors.add(MarbleColor.BLUE);
        colors.add(MarbleColor.GREY);
        colors.add(MarbleColor.GREY);
        colors.add(MarbleColor.YELLOW);
        colors.add(MarbleColor.YELLOW);
        colors.add(MarbleColor.PURPLE);
        colors.add(MarbleColor.PURPLE);
        colors.add(MarbleColor.RED);

        int randomNum = ThreadLocalRandom.current().nextInt(0, colors.size());
        slideMarble= colors.remove(randomNum); //remove Returns the element that was removed from the list.

        for(int i=0; i<MAXROWS; ++i)
            for(int j=0; j<MAXCOLUMNS;++j){
                randomNum = ThreadLocalRandom.current().nextInt(0, colors.size());
                marbles[i][j] = colors.remove(randomNum); //remove Returns the element that was removed from the list.
            }

    }

    /**
     * Method getColor is a getter of one of the marbles inside the Market (not the slide marble)
     * Insert 0 for the first row from above, 1 for the second one ecc.
     * @param row of the marble you want to get
     * @param column of the marble you want to get
     * @return the marble in that position of the market
     */
    public MarbleColor getColor(int row, int column) {
        return marbles[row][column];
    }
    /**
     * Method getSlideMarble is the getter of the slide marble
     * @return the slide marble
     */
    public MarbleColor getSlideMarble() {
        return slideMarble;
    }
    /**
     * Method selectColumn takes the index of a column, shifts the column selected following the
     * game's rules, and returns the list of the taken marbles (the marbles that were in that column
     * in the beginning)
     *
     * @param column is the index of the column you want to select (0,1,2,3)
     * @return the list of the taken (bought) marbles
     */
    public List<MarbleColor> selectColumn(int column) {
        if(column<0 || column>3){
            return null;
        }
        List<MarbleColor> takenMarbles = new ArrayList<>();
        takenMarbles.add(marbles[0][column]);
        takenMarbles.add(marbles[1][column]);
        takenMarbles.add(marbles[2][column]);

        MarbleColor tempMarble = slideMarble;
        slideMarble = marbles[0][column];
        marbles[0][column] = marbles[1][column];
        marbles[1][column] = marbles[2][column];
        marbles[2][column] = tempMarble;

        return takenMarbles;
    }
    /**
     * Method selectRow takes the index of a row, shifts the row selected following the
     * game's rules, and returns the list of the taken marbles (the marbles that were in that row
     * in the beginning)
     *
     * @param row is the index of the row you want to select (0,1,2)
     * @return the list of the taken (bought) marbles
     */
    public List<MarbleColor> selectRow(int row) {
        if(row<0||row>2){
            return null;
        }
        List<MarbleColor> takenMarbles = new ArrayList<>();
        takenMarbles.add(marbles[row][0]);
        takenMarbles.add(marbles[row][1]);
        takenMarbles.add(marbles[row][2]);
        takenMarbles.add(marbles[row][3]);

        MarbleColor tempMarble = slideMarble;
        slideMarble = marbles[row][0];
        marbles[row][0] = marbles[row][1];
        marbles[row][1] = marbles[row][2];
        marbles[row][2] = marbles[row][3];
        marbles[row][3] = tempMarble;

        return takenMarbles;
    }

    //this method doesn't activate the market, it just returns the colors of a row
    public List<MarbleColor> getRowColors(int row){
        List<MarbleColor> rowMarbles = new ArrayList<>();
        rowMarbles.add(marbles[row][0]);
        rowMarbles.add(marbles[row][1]);
        rowMarbles.add(marbles[row][2]);
        rowMarbles.add(marbles[row][3]);
        return rowMarbles;
    }

    //this method doesn't activate the market, it just returns the colors of a column
    public List<MarbleColor> getColumnColors(int column){
        List<MarbleColor> columnMarbles = new ArrayList<>();
        columnMarbles.add(marbles[0][column]);
        columnMarbles.add(marbles[1][column]);
        columnMarbles.add(marbles[2][column]);
        return columnMarbles;
    }
}
