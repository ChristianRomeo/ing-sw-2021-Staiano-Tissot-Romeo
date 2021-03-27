package it.polimi.ingsw.model;

import com.google.gson.*;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.io.*;
import java.util.stream.Collectors;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.*;


public class DevelopmentCardBoard {
    public static List<DevelopmentCard>[][] carte = new ArrayList[3][4];
    public static void main(String args[]) throws IOException {
        String path = "src/main/resources/Cards.json";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        JsonArray json = new Gson().fromJson(bufferedReader, JsonArray.class);
        List<DevelopmentCard> list = new Gson().fromJson(String.valueOf(json), new TypeToken<List<DevelopmentCard>>() {
        }.getType());
        //List<List<Integer>> matrix = new List<List<Integer>>();
        for (int i = 0; i < 4; i++){
            for(CardType color : CardType.values())
                {
                carte[0][i] = list.stream().filter(x -> x.getLevel() == 1 && x.getType().equals(color)).collect(Collectors.toList());
                carte[1][i] = list.stream().filter(x -> x.getLevel() == 2 && x.getType().equals(color)).collect(Collectors.toList());
                carte[2][i] = list.stream().filter(x -> x.getLevel() == 3 && x.getType().equals(color)).collect(Collectors.toList());
                }
                }


        //for (int i = 0; i < 3; i++)
            //for (int j = 0; j < 4; j++)
                carte[1][3].forEach(x -> System.out.println(x.getType()));
        //carte[1][1] = list.stream().filter(x-> x.getVictoryPoints()>1).collect(Collectors.toList());

    }

    //private DevelopmentCard[][][] developmentCards;   SCOMODO
    private List<DevelopmentCard>[][] developmentCards;

    public DevelopmentCardBoard() throws FileNotFoundException {
    }

    public DevelopmentCard getCard(int i, int j) {
        if(!isCardPileEmpty(i,j)) {
            return developmentCards[i][j].get(developmentCards[i][j].size() - 1);
        }
        else{
            return null; //exception is better, deve scegliere un altra pila
        }
    }

    public void removeCard(int i, int j) {
        if(!isCardPileEmpty(i,j)){
            developmentCards[i][j].remove(developmentCards[i][j].size()-1);
        }
    }
    public boolean isCardPileEmpty(int i, int j) {
        return (developmentCards[i][j] == null || developmentCards[i][j].size() == 0);
    }



}