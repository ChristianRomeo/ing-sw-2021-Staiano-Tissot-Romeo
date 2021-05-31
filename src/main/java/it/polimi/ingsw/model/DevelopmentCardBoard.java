package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.*;
import java.util.stream.Collectors;
import it.polimi.ingsw.controller.Configs;

/**
 * DevelopmentCardBoard contains the Board's cards from where the player can pick his choice
 *
 * @see DevelopmentCard
 *
 * @author chris tommà enrico
 */
public class DevelopmentCardBoard implements Serializable{
    /**
     *  MAXROWS contains the rows of the card's matrix
     */
    private static final int MAXROWS = 3;
    /**
     *  MAXCOLUMNS contains the columns of the card's matrix
     */
    private static final int MAXCOLUMNS = 4;
    /**
     *  cardBoard is the card's matrix, where every cell is a list up to 4 cards
     */
    private final List<DevelopmentCard>[][] cardBoard = new ArrayList[MAXROWS][MAXCOLUMNS];    //public? GETTER

    /**
     * Constructor of the matrix.
     * We use BufferedReader together with FileReader to buffer the input (text from "developmentcards.json") and to improve efficiency. NOT ANYMORE
     * Then, we create a JSON Array and we use the "fromJson" method which takes 2 parameters:
     * the JSON string we want to parse and the class to parse JSON string.
     * Then, we iterate over the enum CardType, in order to build the Card Board.
     * @throws FileNotFoundException whenever developmentcards.json couldn't be found
     */
    public DevelopmentCardBoard() throws IOException {

        List<DevelopmentCard> list = Configs.getDevelopmentCards();

        int col = 0,row;
        for(CardType color : CardType.values()){
            row=0;
            cardBoard[row++][col] = list.stream().filter(x -> x.getLevel() == 1 && x.getType().equals(color)).collect(Collectors.toList());
            cardBoard[row++][col] = list.stream().filter(x -> x.getLevel() == 2 && x.getType().equals(color)).collect(Collectors.toList());
            cardBoard[row][col++] = list.stream().filter(x -> x.getLevel() == 3 && x.getType().equals(color)).collect(Collectors.toList());
        }

        for(int i=0; i<MAXROWS;++i)
            for (int j=0; j<MAXCOLUMNS; ++j)
                Collections.shuffle(cardBoard[i][j]);

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
     * @param row between 0 and 2
     * @param column between 0 and 3
     * @return DevelopmentCard type or null if there is no card in that position
     */
    public DevelopmentCard getCard(int row, int column){
        if(!isCardPileEmpty(row,column))
            return cardBoard[row][column].get(cardBoard[row][column].size() - 1);
        else
            return null;
    }
    /**
     * Method getPileSize returns the size of a selected pile of the board
     * @param row between 0 and 2
     * @param column between 0 and 3
     * @return size of the pile
     */
    public int getPileSize(int row, int column){
        if(isCardPileEmpty(row,column))
            return 0;
        else
            return cardBoard[row][column].size();
    }

    /**
     * Method isCardPileEmpty to check whether a given pile is empty
     * @param row   row of the pile
     * @param column column of the pile
     * @return true / false
     */
    public boolean isCardPileEmpty(int row, int column) {
        return (cardBoard[row][column] == null || cardBoard[row][column].size() == 0);
    }

    /**
     * @return true if a column of the board is all empty, otherwise false.
     */
    public boolean isAColumnEmpty(){
        for(int col=0; col<4; col++){
            if(isCardPileEmpty(0,col) && isCardPileEmpty(1,col) && isCardPileEmpty(2,col)){
                return true;
            }
        }
        return false;
    }

}