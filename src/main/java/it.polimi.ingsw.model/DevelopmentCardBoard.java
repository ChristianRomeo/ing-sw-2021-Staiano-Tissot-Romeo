package it.polimi.ingsw.model;

import com.google.gson.*;
import java.util.ArrayList;
import java.util.Collections;
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
    public int col = 0, row;
    /**
     *  cardBoard is the card's matrix, where every cell is a list up to 4 cards
     */
    public List<DevelopmentCard>[][] cardBoard = new ArrayList[MAXROWS][MAXCOLUMNS];

    /**
     * Constructor of the matrix.
     * We use BufferedReader together with FileReader to buffer the input (text from "Cards.json") and to improve efficiency.
     * Then, we create a JSON Array and we use the "fromJson" method which takes 2 parameters:
     * the JSON string we want to parse and the class to parse JSON string.
     * Then, we iterate over the enum CardType, in order to build the Card Board.
     * @throws FileNotFoundException whenever Cards.json couldn't be found
     */
    public DevelopmentCardBoard() throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH));
        JsonArray json = new Gson().fromJson(bufferedReader, JsonArray.class);
        List<DevelopmentCard> list = new Gson().fromJson(String.valueOf(json), new TypeToken<List<DevelopmentCard>>() { }.getType());
        for(CardType color : CardType.values()){
            row=0;
            cardBoard[row++][col] = list.stream().filter(x -> x.getLevel() == 1 && x.getType().equals(color)).collect(Collectors.toList());
            cardBoard[row++][col] = list.stream().filter(x -> x.getLevel() == 2 && x.getType().equals(color)).collect(Collectors.toList());
            cardBoard[row][col++] = list.stream().filter(x -> x.getLevel() == 3 && x.getType().equals(color)).collect(Collectors.toList());
        }
        for(int i=0; i<MAXROWS;++i){
            for (int j=0; j<MAXCOLUMNS; ++j){
                Collections.shuffle(cardBoard[i][j]);
            }
        }
    }

    /**
     * Method removeCard to remove a card from a given pile
     * @param row -riga da estrarre
     * @param column -colonna da estrarre
     */
    public void removeCard(int row, int column) {
        if(!isCardPileEmpty(row,column))
            cardBoard[row][column].remove(cardBoard[row][column].size()-1);
    }

    /**
     * Method getCard...
     * @param row
     * @param column
     * @return DevelopmentCard type
     */
    public DevelopmentCard getCard(int row, int column) throws NullPointerException{   //todo throws deve scegliere un altra pila
        if(!isCardPileEmpty(row,column))
            return cardBoard[row][column].get(cardBoard[row][column].size() - 1);
        else
            return null; //exception
    }

    /**
     * Method isCardPileEmpty to check whether a given pile is empty
     * @param row
     * @param column
     * @return true / false
     */
    public boolean isCardPileEmpty(int row, int column) {
        return (cardBoard[row][column] == null || cardBoard[row][column].size() == 0);
    }



}