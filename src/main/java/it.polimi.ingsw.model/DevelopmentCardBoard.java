package it.polimi.ingsw.model;

import com.google.gson.*;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.stream.Collectors;
import com.google.gson.reflect.TypeToken;


/**
 * DevelopmentCardBoard contains the Board's cards from where the player can pick his choice
 *
 * @see DevelopmentCard
 *
 * @author chris tommy enrico
 */
public class DevelopmentCardBoard {
    /**
     *  MAXROWS contains the rows of the card's matrix
     */
    public static final int MAXROWS = 3;
    /**
     *  MAXCOLUMNS contains the columns of the card's matrix
     */
    public static final int MAXCOLUMNS = 4;
    /**
     *  PATH contains the relative path to card's json file
     */
    public static final String PATH = "src/main/resources/Cards.json";
    public static int i = 0; //todo temporaneo
    /**
     *  cardBoard is the card's matrix, where every cell is a list up to 4 cards
     */
    public static List<DevelopmentCard>[][] cardBoard = new ArrayList[MAXROWS][MAXCOLUMNS];

    /**
     * Constructor of the matrix.
     * We use BufferedReader together with FileReader to buffer the input (text from "Cards.json") and to improve efficiency.
     * Then, we create a JSON Array and we use the "fromJson" method which takes 2 parameters:
     * the JSON string we want to parse and the class to parse JSON string.
     * Then, we iterate over the enum CardType, in order to build the Card Board.
     * @throws FileNotFoundException whenever Cards.json couldn't be found
     */
    public DevelopmentCardBoard() throws FileNotFoundException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH));
        JsonArray json = new Gson().fromJson(bufferedReader, JsonArray.class);
        List<DevelopmentCard> list = new Gson().fromJson(String.valueOf(json), new TypeToken<List<DevelopmentCard>>() { }.getType());

        for(CardType color : CardType.values()){
            cardBoard[0][i]   = list.stream().filter(x -> x.getLevel() == 1 && x.getType().equals(color)).collect(Collectors.toList());
            cardBoard[1][i]   = list.stream().filter(x -> x.getLevel() == 2 && x.getType().equals(color)).collect(Collectors.toList());
            cardBoard[2][i++] = list.stream().filter(x -> x.getLevel() == 3 && x.getType().equals(color)).collect(Collectors.toList());
        }
    }

    /**
     * Method removeCard to remove a card from a given pile
     * @param i rows
     * @param j columns
     */
    public void removeCard(int i, int j) {
        if(!isCardPileEmpty(i,j))
            cardBoard[i][j].remove(cardBoard[i][j].size()-1);

    }

    /**
     * Method isCardPileEmpty to check whether a given pile is empty
     * @param i rows
     * @param j columns
     * @return true / false
     */
    public boolean isCardPileEmpty(int i, int j) {
        return (cardBoard[i][j] == null || cardBoard[i][j].size() == 0);
    }



}