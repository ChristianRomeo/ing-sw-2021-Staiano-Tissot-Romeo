package it.polimi.ingsw.model;

import modelExceptions.VaticanReportException;

import java.util.HashMap;
import java.util.Map;
/**
 * This class represent all about the player, faith track sit rep,
 * @author enrico (vatican reports and increment faith position)
 */

public class StatusPlayer {

        private int faithTrackPosition;
        private final PlayerWarehouse playerWarehouse;
        private final Map<Resource,Integer> strongboxResources;
        private final SameTypeTriple<PopeFavorTileStatus> popeFavorTiles;
        private final PersonalCardBoard personalCardBoard;
        private LeaderCard[] leaderCards;       //pair?

        /**
         * Constructor
         */
        public StatusPlayer(){
                popeFavorTiles = new SameTypeTriple<>(PopeFavorTileStatus.INACTIVE,PopeFavorTileStatus.INACTIVE,PopeFavorTileStatus.INACTIVE);
                faithTrackPosition = 0;
                playerWarehouse = new PlayerWarehouse();
                personalCardBoard = new PersonalCardBoard();
                strongboxResources= new HashMap<>();
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
         * @param reportId is a int between 1 and 3, it indicates if the report activated
         * is the first, the second or the third
         */
        public void vaticanReportHandler(int reportId){
                switch(reportId){
                        case 1:
                                if(faithTrackPosition>=5){
                                        popeFavorTiles.set(PopeFavorTileStatus.ACTIVE,1);
                                }
                                else{
                                        popeFavorTiles.set(PopeFavorTileStatus.DISCARDED,1);
                                }
                                break;
                        case 2:
                                if(faithTrackPosition>=12){
                                        popeFavorTiles.set(PopeFavorTileStatus.ACTIVE,2);
                                }
                                else{
                                        popeFavorTiles.set(PopeFavorTileStatus.DISCARDED,2);
                                }
                                break;
                        case 3:
                                if(faithTrackPosition>=19){
                                        popeFavorTiles.set(PopeFavorTileStatus.ACTIVE,3);
                                }
                                else{
                                        popeFavorTiles.set(PopeFavorTileStatus.DISCARDED,3);
                                }
                                break;
                }
        }

        //you give to this method a resource, and it remove one resource of that type from the
        //strongbox
        public void removeStrongboxResource(Resource resource){
                if(strongboxResources.containsKey(resource) && strongboxResources.get(resource)>0){
                        strongboxResources.put(resource,strongboxResources.get(resource)-1);
                }
        }

        public Map<Resource,Integer> getStrongboxResources() {
                return strongboxResources;
        }

        public void setResourceStrongbox(Map <Resource,Integer> resources) {

        }

        public PlayerWarehouse getPlayerWarehouse(){
                return playerWarehouse;
        }

        public PersonalCardBoard getPersonalCardBoard(){
                return personalCardBoard;
        }

        public LeaderCard[] getPlayerLeaderCards(){
                return leaderCards;
        }

        //this method returns all the resources of the player
        public Map<Resource,Integer> getAllResources(){
                Map<Resource, Integer> allResources = new HashMap<>(strongboxResources);

                Map<Resource, Integer> warehouseResources= playerWarehouse.getAllResources();
                for(Resource r: warehouseResources.keySet()){
                        if(!allResources.containsKey(r)){
                                allResources.put(r,warehouseResources.get(r));
                        }else{
                                allResources.put(r,allResources.get(r)+warehouseResources.get(r));
                        }
                }

                //todo:qui vanno aggiunte eventuali risorse in depositi carte leader
                return allResources;
        }

}
