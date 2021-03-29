package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author enrico
 */
public class Market {
    private MarbleColor[][] marbles;
    private MarbleColor slideMarble;

    public Market(){
        marbles = new MarbleColor[3][4];
        initMarket();
    }

    /**
     * Method initMarket is an helper private method called by the constructor.
     * It initialize the Market board.
     */
    private void initMarket(){
        int randomNum;
        List<MarbleColor> colors = new ArrayList<MarbleColor>();

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

        randomNum = ThreadLocalRandom.current().nextInt(0, colors.size());
        slideMarble= colors.get(randomNum);
        colors.remove(randomNum);

        for(int i=0; i<3; i++){
            for(int j=0; j<4;j++){
                randomNum = ThreadLocalRandom.current().nextInt(0, colors.size());
                marbles[i][j] = colors.get(randomNum);
                colors.remove(randomNum);
            }
        }
    }

    /**
     * Method getColor is a getter of one of the marbles inside the Market (not the slide marble)
     *
     * @param i is the row of the marble you want to get
     * @param j is the column of the marble you want to get
     * @return the marble in that position of the market
     */
    public MarbleColor getColor(int i, int j) {
       return marbles[i][j];
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
     * @param i is the index of the column you want to select
     * @return the list of the taken (bought) marbles
     */
    public List<MarbleColor> selectColumn(int i) {
        List<MarbleColor> takenMarbles = new ArrayList<>();
        takenMarbles.add(marbles[0][i]);
        takenMarbles.add(marbles[1][i]);
        takenMarbles.add(marbles[2][i]);

        MarbleColor tempMarble = slideMarble;
        slideMarble = marbles[0][i];
        marbles[0][i] = marbles[1][i];
        marbles[1][i] = marbles[2][i];
        marbles[2][i] = tempMarble;

        return takenMarbles;
    }
    /**
     * Method selectRow takes the index of a row, shifts the row selected following the
     * game's rules, and returns the list of the taken marbles (the marbles that were in that row
     * in the beginning)
     *
     * @param i is the index of the row you want to select
     * @return the list of the taken (bought) marbles
     */
    public List<MarbleColor> selectRow(int i) {
        List<MarbleColor> takenMarbles = new ArrayList<>();
        takenMarbles.add(marbles[i][0]);
        takenMarbles.add(marbles[i][1]);
        takenMarbles.add(marbles[i][2]);
        takenMarbles.add(marbles[i][3]);

        MarbleColor tempMarble = slideMarble;
        slideMarble = marbles[i][0];
        marbles[i][0] = marbles[i][1];
        marbles[i][1] = marbles[i][2];
        marbles[i][2] = marbles[i][3];
        marbles[i][3] = tempMarble;

        return takenMarbles;
    }
}
