package it.polimi.ingsw.model;

import modelExceptions.VaticanReportException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * This class represents all about the player, faith track sit rep,
 * @author enrico
 */

public class StatusPlayer {

        private int faithTrackPosition;
        private final PlayerWarehouse playerWarehouse;
        private Map<Resource,Integer> strongboxResources;
        private final SameTypeTriple<PopeFavorTileStatus> popeFavorTiles;
        private final PersonalCardBoard personalCardBoard;
        private List<LeaderCard> leaderCards;       //pair?


        /**
         * Constructor
         */
        public StatusPlayer(){
                popeFavorTiles = new SameTypeTriple<>(PopeFavorTileStatus.INACTIVE,PopeFavorTileStatus.INACTIVE,PopeFavorTileStatus.INACTIVE);
                faithTrackPosition = 0;
                playerWarehouse = new PlayerWarehouse();
                personalCardBoard = new PersonalCardBoard();
                strongboxResources= new HashMap<>();
                //leaderCards
                //c'è da inizializzare leader cards
        }

        /**
         * Getter faithTrackPosition
         * @return faithTrackPosition
         */
        public int getFaithTrackPosition() {
            return faithTrackPosition;
        }

        /**
         * Method getPopeFavorTile takes an integer between 0 and 2, and returns the
         * the status of the first, second or third pope favor tile correspondingly
         * @param index number of pope favor tile to get
         * @return the status of the selected pope favor tile
         */
        public PopeFavorTileStatus getPopeFavorTile(int index) {
                return popeFavorTiles.get(index);
        }

        /**
         * Method incrementFaithTrackPosition is used to increment the faith track position,
         * it also checks if a vatican report is activated, in that case it throws an exception
         */
        public void incrementFaithTrackPosition() throws VaticanReportException {
                if(faithTrackPosition<24)
                        faithTrackPosition++;

                if(faithTrackPosition>= 8 && popeFavorTiles.get(1)==PopeFavorTileStatus.INACTIVE)
                        throw new VaticanReportException(1);

                if(faithTrackPosition>=16 && popeFavorTiles.get(2)==PopeFavorTileStatus.INACTIVE)
                        throw new VaticanReportException(2);

                if(faithTrackPosition==24 && popeFavorTiles.get(3)==PopeFavorTileStatus.INACTIVE)
                        throw new VaticanReportException(3);

        }

        /**
         * Method vaticanReportHandler set the pope favor tiles when a vatican report is activated
         * @param reportId is a int between 1 and 3, it indicates if the report activated is the first, the second or the third
         */
        public void vaticanReportHandler(int reportId){
                switch(reportId){
                        case 1:
                                if(faithTrackPosition>=5)
                                        popeFavorTiles.set(PopeFavorTileStatus.ACTIVE,1);
                                else
                                        popeFavorTiles.set(PopeFavorTileStatus.DISCARDED,1);
                                break;
                        case 2:
                                if(faithTrackPosition>=12)
                                        popeFavorTiles.set(PopeFavorTileStatus.ACTIVE,2);
                                else
                                        popeFavorTiles.set(PopeFavorTileStatus.DISCARDED,2);
                                break;
                        case 3:
                                if(faithTrackPosition>=19)
                                        popeFavorTiles.set(PopeFavorTileStatus.ACTIVE,3);
                                else
                                        popeFavorTiles.set(PopeFavorTileStatus.DISCARDED,3);
                                break;
                }
        }

        /**
         *  you give to this method a resource, and it remove one resource of that type from the strongbox.
         */
        public void removeStrongboxResource(Resource resource){
                if(strongboxResources.containsKey(resource) && strongboxResources.get(resource)>0)
                        strongboxResources.put(resource,strongboxResources.get(resource)-1);
        }

        public Map<Resource,Integer> getStrongboxResources() {
                return new HashMap<>(strongboxResources);
        }


        /**
         *  you give to this method a map of resources and it adds the resources in the strongbox
         *  (so in the end you have in the strongbox the old resources + the new resources)
         */
        public void addResourcesStrongbox(Map <Resource,Integer> resources) {
                strongboxResources = Resource.sumResourcesMap(strongboxResources,resources);
        }

        public PlayerWarehouse getPlayerWarehouse(){
                return playerWarehouse;
        }

        public PersonalCardBoard getPersonalCardBoard(){
                return personalCardBoard;
        }

        public List<LeaderCard> getPlayerLeaderCards(){
                return leaderCards;
        }
        /**
         *  you give to this method an index and it returns the player's leader card in that position
         */
        public LeaderCard getLeaderCard(int index) throws IllegalArgumentException{
                if(index>=0 && index<=1){
                        return leaderCards.get(index);
                }
                throw new IllegalArgumentException();
        }

        /**
         * this method returns all the resources of the player.
         * @return a Map with all of the player's resources.
         */
        public Map<Resource,Integer> getAllResources(){

                //todo:qui vanno aggiunte eventuali risorse in depositi carte leader
                return Resource.sumResourcesMap(strongboxResources,playerWarehouse.getAllResources());
        }

        /**
         * this method removes resources: the priority is to remove resources from the warehouse,
         * then from the leader cards, then from the strongbox (it's the most favorable strategy
         * for the player)
         * PRECONDITION: before you call this method you have to be sure that the player owns the
         *               resources you want to remove
         */
        public void removeResources(Map<Resource,Integer> resources){
                int i=0;
                for(Resource r: resources.keySet()){
                        i=resources.get(r);
                        //search and remove resources from the warehouse
                        for(int rowW=1; rowW<=3 && i>0; ++rowW){
                                for(int colW=1; colW<=rowW && i>0; ++colW){
                                        if(r==playerWarehouse.getResource(rowW,colW)){
                                                playerWarehouse.removeResource(rowW,colW);
                                                --i;
                                        }
                                }
                        }
                        //rimozione risorse da depositi carte leader va qui (se i>0)
                        while(i>0){
                                removeStrongboxResource(r);
                                --i;
                        }
                }
        }

        //METODO DI PROVA, NON SO SE LO USEREMO
        public Resource getLeaderResource(LeaderCardType type, int index){
                if(leaderCards.get(index).getAbility() == type){

                }
                //se la carta leader al posto index è attiva ed è di quel tipo
                //allora ritorna la risorsa, se no null
                return null;
        }


}
