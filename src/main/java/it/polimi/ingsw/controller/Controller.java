package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import modelExceptions.InvalidWarehouseInsertionException;
import modelExceptions.VaticanReportException;

import java.util.ArrayList;
import java.util.List;
/**
 * @author enrico
 */
public class Controller {
    private Game game;

    public Controller(Game game){
        //costruttore creato a caso solo per fare testing
        this.game=game;
    }

    /**
     * Method useMarket allows the player to bought new resources at the Market
     *
     * @param rowOrColumn is =r if the player wants to select a row, =c to select a column
     * @param index is the index of the row/column the player wants to select
     */
    public void useMarket(char rowOrColumn, int index){
        Market market = game.getBoard().getMarket();

        List<MarbleColor> takenMarbles;
        List<Resource> boughtResources;
        if(rowOrColumn=='r'){
            takenMarbles=market.selectRow(index);
        }
        else
        {takenMarbles=market.selectColumn(index);}

        boughtResources=fromMarblesToResources(takenMarbles);

        /*the player can change the position of the resources in the warehouse
            before the insertion of the new resources*/
        editWarehouse();
        //the player insert/discard the resources bought at the market
        insertBoughtResources(boughtResources);
    }

    //METODI EDIT E INSERT WAREHOUSE DOVRANNO CONSIDERARE ANCHE I DEPOSITI LEADER
    /**
     * Method editWarehouse allows the player to change the position of the resources in the warehouse
     */
    //NB: this method can't work now, because it needs the view
    private void editWarehouse(){
        //the player says what resources in he warehouse he wants to move, so these resources
        //are temporary removed from the warehouse and stored in a list. Than the player
        //can reinsert these resources where he wants (or he can again temporary remove some resources).
        //When he wants, the player can stop the edit of the warehouse, but only if he has
        //inserted every temporary removed resource.

        PlayerWarehouse warehouse = game.getCurrentPlayer().getStatusPlayer().getPlayerWarehouse();
        List<Resource> temporaryRemovedResources = new ArrayList<>();
        int i=0,j=0;
        int k=0;
        while(true){ //la condizione di stop sarà detta da utente
            //i valori di i, j e k devono essere detti dall'utente, interazione con la view

            if(true /*player wants to temporary remove a resource*/){
                if(warehouse.getResource(i,j)!=null){
                    temporaryRemovedResources.add(warehouse.removeResource(i,j));
                }
            }
            if(true /*player wants to reinsert one of the temporary removed resources*/){
                if(k>=0 && k< temporaryRemovedResources.size()){
                    try{
                        warehouse.insertResource(temporaryRemovedResources.get(k),i,j);
                    }catch(InvalidWarehouseInsertionException e){
                        /*signal to the user, invalid insertion in the warehouse*/
                    }
                }
            }
            if(true/* the player wants to end the Warehouse edit*/){
                if(temporaryRemovedResources.size()==0){
                    break;
                }else{
                    /*signal the player that he has to insert every temporary removed resource*/
                }
            }
        }
    }

    /**
     * Method insertBoughtResources allows the player to insert/discard the new
     * resources bought at the market, in the warehouse
     */
    //NB: this method can't work now, because it needs the view
    private void insertBoughtResources(List<Resource> boughtResources){
        int i=0,j=0;
        PlayerWarehouse warehouse = game.getCurrentPlayer().getStatusPlayer().getPlayerWarehouse();
        //i, j sono dati dall'utente per ogni risorsa
        for(Resource r: boughtResources){
            if(true /*the player wants to insert the resource*/){
                try{
                    warehouse.insertResource(r,i,j);
                }catch (InvalidWarehouseInsertionException e){
                    /*signal to the user, invalid inseriment in the warehouse*/
                }
            }
            if(true /*the player wants to discard the resource*/){
                boughtResources.remove(r);
                for(int k=0; k< game.getPlayersNumber(); k++){
                    if (game.getCurrentPlayerId()!=k){
                        incrementFaithTrackPosition(game.getPlayerByIndex(k));
                    }
                }
            }
        }
    }


    /**
     * Method fromMarblesToResources transforms a list of marbles in the corresponding list of resources,
     * (the red marbles are transformed in increments of the faith track position)
     *
     * @param marbles is a list of marbles
     * @return a list of resources
     */
    private List<Resource> fromMarblesToResources(List<MarbleColor> marbles){
        List<Resource> boughtResources = new ArrayList<>();
        for(MarbleColor m: marbles){
            switch(m){
                case WHITE:
                    //controllo carta leader va qui
                    break;
                case RED:
                    incrementFaithTrackPosition(game.getCurrentPlayer());
                    break;
                case BLUE:
                    boughtResources.add(Resource.SHIELD);
                    break;
                case GREY:
                    boughtResources.add(Resource.STONE);
                    break;
                case PURPLE:
                    boughtResources.add(Resource.SERVANT);
                    break;
                case YELLOW:
                    boughtResources.add(Resource.COIN);
                    break;
            }
        }
        return boughtResources;
    }

    /**
     * Method incrementFaithTrackPosition is used to increment the faith track position of a
     * player you choose. If a vatican report is activated, it calls the handlers of the players.
     * It also checks if the match is ending (a player arrives in the last cell)
     *
     * @param player is the chosen player
     */
    private void incrementFaithTrackPosition(Player player){
        try{
            player.getStatusPlayer().incrementFaithTrackPosition();
        }catch(VaticanReportException e){
            for(int i=0; i< game.getPlayersNumber(); i++){
                game.getPlayerByIndex(i).getStatusPlayer().vaticanReportHandler(e.getReportId());
                if(e.getReportId()==3){
                    //a player is arrived in the last cell of the track, so the game is
                    //in the final phase
                }
            }
        }
    }



}
