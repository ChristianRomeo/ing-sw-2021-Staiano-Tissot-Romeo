package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import modelExceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
     * Method activateProduction allows the player to activate the production of one or more cards.
     * Also the base production can be activated.
     *
     */
    public void activateProduction() throws CannotActivateProductionException,IllegalArgumentException{
        PersonalCardBoard personalCardBoard = game.getCurrentPlayer().getStatusPlayer().getPersonalCardBoard();
        //l'utente mi dice quali produzioni vuole attivare tramite la view
        List<Integer> activatedProductions = new ArrayList<>();//contiene numeri da 0 a 2 che ti dicono
                                                                // quali produzioni di carte attivare
        boolean activateBaseProduction = false; //detto da utente
        Resource reqBaseProduction1 = Resource.COIN;//per esempio
        Resource reqBaseProduction2 = Resource.SERVANT;
        Resource producedResBaseProd = Resource.STONE;

        activatedProductions.add(0); //per esempio, detto da utente

        Map<Resource,Integer> requiredResources = personalCardBoard.getReqResProduction(activatedProductions);

        if(activateBaseProduction /*the player wants to activate the base production too*/){
            requiredResources = Resource.addOneResource(requiredResources,reqBaseProduction1);
            requiredResources = Resource.addOneResource(requiredResources,reqBaseProduction2);
        }
        Map<Resource,Integer> ownedResources = game.getCurrentPlayer().getStatusPlayer().getAllResources();

        if(!Resource.enoughResources(ownedResources,requiredResources)){
            throw new CannotActivateProductionException();
        }

        game.getCurrentPlayer().getStatusPlayer().removeResources(requiredResources);
        Map<Resource,Integer> producedResources = personalCardBoard.getProductionResources(activatedProductions);

        if(activateBaseProduction /*the player wants to activate the base production too*/){
            producedResources= Resource.addOneResource(producedResources,producedResBaseProd);
        }
        game.getCurrentPlayer().getStatusPlayer().addResourcesStrongbox(producedResources);
        int producedFaithPoints = personalCardBoard.getProductionFP(activatedProductions);
        for(int i=0; i<producedFaithPoints; i++){
            incrementFaithTrackPosition(game.getCurrentPlayer());
        }
    }


    /**
     * Method buyDevelopmentCard allows the player to buy a new development card from the board.
     * You have to give the position of the card you want to buy on the board.
     *
     * @param row is the row of the selected card, 0<=row<=2,
     *            row 0 is for card of level 1,..., row 2 is for card of level 3
     *
     * @param col is the column of the selected card, 0<=col<=3
     */
    public void buyDevelopmentCard(int row, int col) throws CannotBuyCardException,IllegalArgumentException{
        DevelopmentCardBoard developmentCardBoard = game.getBoard().getDevelopmentCardBoard();
        StatusPlayer statusCurrentPlayer = game.getCurrentPlayer().getStatusPlayer();

        //check if the selected pile in the board is empty
        if(developmentCardBoard.isCardPileEmpty(row,col)){
            throw new IllegalArgumentException();
        }
        //check if the player has space in his personal card board
        if(!statusCurrentPlayer.getPersonalCardBoard().canBuyCardOfLevel(row+1)){
            throw new CannotBuyCardException();
        }
        DevelopmentCard card = developmentCardBoard.getCard(row, col);
        //check if the player has the resources to buy the card
        if(!card.isBuyable(statusCurrentPlayer.getAllResources())) {
            throw new CannotBuyCardException();
        }
        //the player can buy the card
        developmentCardBoard.removeCard(row, col);
        statusCurrentPlayer.removeResources(card.getCost());

        //the player has to decide where he wants to put his new card, in what position(between 0 and 2)
        //of his personal card board. If he selects an invalid position, he has to try again.
        //THE FOLLOWING PART NEEDS THE INTERACTION WITH THE VIEW
        int position = 0;
        while(true){
            try{
                statusCurrentPlayer.getPersonalCardBoard().addCard(card,position);
                break;
            }catch (InvalidCardInsertionException e){
                //this position was invalid, insert another position
            }
        }
    }


    /**
     * Method useMarket allows the player to buy new resources at the Market
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
