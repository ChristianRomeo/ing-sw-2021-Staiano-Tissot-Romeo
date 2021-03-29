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

                //c'è da inizializzare tutto il resto
        }

        public int getFaithTrackPosition() {
            return faithTrackPosition;
        }

        public Map<Resource,Integer> getStrongboxResources() {
                return strongboxResources;
        }

        //you give to this method 0,1,2 and it returns the status of the first/second/third
        //pope favor tile
        public PopeFavorTileStatus getPopeFavorTile(int i) {
                return popeFavorTiles.get(i);
        }
        //this method is used to increment the fairh track position,
        //it also checks if a vatican report is activated
        public void incrementFaithTrackPosition() throws VaticanReportException{
                if(faithTrackPosition<24){
                        faithTrackPosition++;
                }
                //posso fare un metodo che contiene i controlli
                if(faithTrackPosition>= 8 && popeFavorTiles.get(1)==PopeFavorTileStatus.INACTIVE){
                        throw new VaticanReportException(1);
                }
                if(faithTrackPosition>=16 && popeFavorTiles.get(2)==PopeFavorTileStatus.INACTIVE){
                        throw new VaticanReportException(2);
                }
                if(faithTrackPosition==24 && popeFavorTiles.get(3)==PopeFavorTileStatus.INACTIVE){
                        // è finita la partita e io sono il primo ad arrivare
                        //ora bisogna eventualmente fare gli ultimi turni
                        throw new VaticanReportException(3); //qui devo mettere exception fine partita
                }
        }

        //this method set the pope favor tiles when a vatican report is activated
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
