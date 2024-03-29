package it.polimi.ingsw.controller.Events;

/**
 * creator of the events to be sent to the client
 */
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerEventCreator{
    Controller controller;

    public ServerEventCreator(Controller controller){
        this.controller=controller;
    }

    public LeaderCardActionEventS2C createLeaderActionEvent(){
        boolean isActive1 = controller.getGame().getCurrentPlayer().getStatusPlayer().getLeaderCard(0).isActivated();
        boolean isActive2 = controller.getGame().getCurrentPlayer().getStatusPlayer().getLeaderCard(1).isActivated();
        boolean isDiscarded1 = controller.getGame().getCurrentPlayer().getStatusPlayer().getLeaderCard(0).isDiscarded();
        boolean isDiscarded2 = controller.getGame().getCurrentPlayer().getStatusPlayer().getLeaderCard(1).isDiscarded();

        return new LeaderCardActionEventS2C(isActive1,isActive2,isDiscarded1,isDiscarded2);
    }

    public BoughtCardEventS2C createBoughtCardEvent(){
        PersonalCardBoard personalCardBoard= controller.getGame().getCurrentPlayer().getStatusPlayer().getPersonalCardBoard();
        DevelopmentCardBoard board = controller.getGame().getBoard().getDevelopmentCardBoard();
        PlayerWarehouse warehouse = controller.getGame().getCurrentPlayer().getStatusPlayer().getPlayerWarehouse();
        Map<Resource,Integer> strongbox = controller.getGame().getCurrentPlayer().getStatusPlayer().getStrongboxResources();
        Integer fullSlotsLeader1 = controller.getGame().getCurrentPlayer().getStatusPlayer().getLeaderCard(0).getFullSlotsNumber();
        Integer fullSlotsLeader2 = controller.getGame().getCurrentPlayer().getStatusPlayer().getLeaderCard(1).getFullSlotsNumber();

        return new BoughtCardEventS2C(personalCardBoard,board,warehouse,strongbox,fullSlotsLeader1,fullSlotsLeader2);
    }

    public ActivatedProductionEventS2C createProductionEvent(){
        PlayerWarehouse warehouse = controller.getGame().getCurrentPlayer().getStatusPlayer().getPlayerWarehouse();
        Map<Resource,Integer> strongbox = controller.getGame().getCurrentPlayer().getStatusPlayer().getStrongboxResources();
        Integer fullSlotsLeader1 = controller.getGame().getCurrentPlayer().getStatusPlayer().getLeaderCard(0).getFullSlotsNumber();
        Integer fullSlotsLeader2 = controller.getGame().getCurrentPlayer().getStatusPlayer().getLeaderCard(1).getFullSlotsNumber();

        return new ActivatedProductionEventS2C(warehouse,strongbox,fullSlotsLeader1,fullSlotsLeader2);
    }

    public IncrementPositionEventS2C createIncrementPositionEvent(Player player){
        int position = player.getStatusPlayer().getFaithTrackPosition();

        return new IncrementPositionEventS2C(position, player.getNickname());
    }

    public VaticanReportEventS2C createVaticanReportEvent(){
        Map<String,SameTypeTriple<PopeFavorTileStatus>> tilesStatus = new HashMap<>();
        for(Player player : controller.getGame().getPlayers()){
            tilesStatus.put(player.getNickname(), player.getStatusPlayer().getPopeFavorTiles());
        }
        return new VaticanReportEventS2C(tilesStatus);
    }

    public UseMarketEventS2C createUseMarketEvent(){
        PlayerWarehouse warehouse = controller.getGame().getCurrentPlayer().getStatusPlayer().getPlayerWarehouse();
        Integer fullSlotsLeader1 = controller.getGame().getCurrentPlayer().getStatusPlayer().getLeaderCard(0).getFullSlotsNumber();
        Integer fullSlotsLeader2 = controller.getGame().getCurrentPlayer().getStatusPlayer().getLeaderCard(1).getFullSlotsNumber();
        Market market = controller.getGame().getBoard().getMarket();
        return new UseMarketEventS2C(market,warehouse,fullSlotsLeader1,fullSlotsLeader2);
    }

    public NewTurnEventS2C createNewTurnEvent(Player player){
        return new NewTurnEventS2C(player.getNickname());
    }

    public GameStarterEventS2C createGameStarterEvent(List<LeaderCard> leaderCardList, Player player){
        Market market = controller.getGame().getBoard().getMarket();
        DevelopmentCardBoard developmentCardBoard = controller.getGame().getBoard().getDevelopmentCardBoard();
        int playerIndex = controller.getGame().getPlayers().indexOf(player);
        List<String> nicknames = new ArrayList<>();
        for(Player player1: controller.getGame().getPlayers()){
            nicknames.add(player1.getNickname());
        }
        return  new GameStarterEventS2C(leaderCardList,developmentCardBoard,market,playerIndex,nicknames);
    }

    public EndGameEventS2C createEndGameEvent(){
        List<String> winners = new ArrayList<>();
        Map<String,Integer> victoryPoints = new HashMap<>();
        for(Player p: controller.getGame().getPlayers()){
            if(p.isWinner()){
                winners.add(p.getNickname());
            }
            victoryPoints.put(p.getNickname(),p.getVictoryPoints());
        }
        return new EndGameEventS2C(winners,victoryPoints);
    }

    public EndPreparationEventS2C createEndPreparationEvent(){
        Map<String,PlayerWarehouse> warehouses = new HashMap<>();
        Map<String,List<LeaderCard>> leaderCards = new HashMap<>();
        for(Player p: controller.getGame().getPlayers()){
            warehouses.put(p.getNickname(),p.getStatusPlayer().getPlayerWarehouse());
            leaderCards.put(p.getNickname(),p.getStatusPlayer().getPlayerLeaderCards());
        }
        return new EndPreparationEventS2C(warehouses,leaderCards);
    }
}
