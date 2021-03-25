package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class Market {
    private MarbleColor[][] marbles;
    private MarbleColor slideMarble;

    public Market(){
        marbles = new MarbleColor[3][4];
        initMarket();
    }

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

    public MarbleColor getColor(int i, int j) {
       return marbles[i][j];
    }
    public MarbleColor getSlideMarble() {
        return slideMarble;
    }
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
