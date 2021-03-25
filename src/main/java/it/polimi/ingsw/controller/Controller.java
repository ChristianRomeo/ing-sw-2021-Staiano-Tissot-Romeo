package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.List;

public class Controller {
    private Market market;
    public void useMarket(char rowOrColumn, int index){
        //rowOrColumn=r if you want to select a row, c to select column
        List<MarbleColor> takenMarbles;
        if(rowOrColumn=='r'){
            takenMarbles=market.selectRow(index);
        }
        else
        {takenMarbles=market.selectColumn(index);}

        for(MarbleColor m: takenMarbles){
            if(m==MarbleColor.RED){
                //metodo per incrementare faith track pointer del giocatore va qui
                takenMarbles.remove(m);
            }else{
                if (m==MarbleColor.WHITE){
                    //controllo carta leader va qui
                    takenMarbles.remove(m);
                }
            }
        }
        //metodo modificaWarehouse va qui
        //metodo inserisci / scarta risorse comprate va qui
    }
}
