package it.polimi.ingsw.model;

import java.util.Map;
/**
 * @author enrico (vatican reports and increment faith position)
 */

public class StatusPlayer {

        private int faithTrackPosition;
        private PlayerWarehouse playerWarehouse;
        private Map<Resource,Integer> strongboxResources;
        private SameTypeTriple<PopeFavorTileStatus> popeFavorTiles;
        private PersonalCardBoard personalCardBoard;
        private LeaderCard[] leaderCards;

        public StatusPlayer(){
                popeFavorTiles = new SameTypeTriple<>(PopeFavorTileStatus.INACTIVE,PopeFavorTileStatus.INACTIVE,PopeFavorTileStatus.INACTIVE);
                faithTrackPosition = 0; //it starts at 0 also in the game (the first cell is 0)
                playerWarehouse = new PlayerWarehouse();
                personalCardBoard = new PersonalCardBoard();
                //c'è da inizializzare tutto il resto
        }

        public int getFaithTrackPosition() {
            return faithTrackPosition;
        }

        public Map<Resource,Integer> getStrongboxResources() {
                return strongboxResources;
        }
        /**
         * Method getPopeFavorTile takes an integer between 0 and 2, and returns the
         * the status of the first or second or third pope favor tile
         * @param i number of pope favor tile to get
         * @return the status of the selected pope favor tile
         */
        public PopeFavorTileStatus getPopeFavorTile(int i) {
                return popeFavorTiles.get(i);
        }

        /**
         * Method incrementFaithTrackPosition is used to increment the faith track position,
         * it also checks if a vatican report is activated, in that case it throws an exception
         */
        public void incrementFaithTrackPosition() throws VaticanReportException{
                if(faithTrackPosition<24){
                        faithTrackPosition++;
                }
                if(faithTrackPosition>= 8 && popeFavorTiles.get(1)==PopeFavorTileStatus.INACTIVE){
                        throw new VaticanReportException(1);
                }
                if(faithTrackPosition>=16 && popeFavorTiles.get(2)==PopeFavorTileStatus.INACTIVE){
                        throw new VaticanReportException(2);
                }
                if(faithTrackPosition==24 && popeFavorTiles.get(3)==PopeFavorTileStatus.INACTIVE){
                        throw new VaticanReportException(3);
                }
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

}
