package it.polimi.ingsw.model;

import it.polimi.ingsw.model.modelExceptions.VaticanReportException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * This class represents all about the player.
 * @author enrico
 */

public class StatusPlayer {

        private int faithTrackPosition;
        private final PlayerWarehouse playerWarehouse;
        private Map<Resource,Integer> strongboxResources;
        private final SameTypeTriple<PopeFavorTileStatus> popeFavorTiles;
        private final PersonalCardBoard personalCardBoard;
        private final List<LeaderCard> leaderCards;

        /**
         * Constructor
         */
        public StatusPlayer(){
                popeFavorTiles = new SameTypeTriple<>(PopeFavorTileStatus.INACTIVE,PopeFavorTileStatus.INACTIVE,PopeFavorTileStatus.INACTIVE);
                faithTrackPosition = 0;
                playerWarehouse = new PlayerWarehouse();
                personalCardBoard = new PersonalCardBoard();
                strongboxResources= new HashMap<>();
                leaderCards = new ArrayList<>();
        }

        /**
         * getter of the Faith track position
         * @return the faithTrackPosition
         */
        public int getFaithTrackPosition() {
            return faithTrackPosition;
        }

        /**
         * Method getPopeFavorTile takes an integer between 1 and 3, and returns the
         * the status of the first, second or third pope favor tile correspondingly
         * @param index number of pope favor tile to get
         * @return the status of the selected pope favor tile
         */
        public PopeFavorTileStatus getPopeFavorTile(int index) {
                return popeFavorTiles.get(index);
        }

        /**
         * Method getPopeFavorTile takes an integer between 1 and 3, and returns the
         * the status of the first, second or third pope favor tile correspondingly
         * @return the status of all the player's pope favor tiles
         */
        public SameTypeTriple<PopeFavorTileStatus> getPopeFavorTiles(){
                return popeFavorTiles;
        }

        /**
         * Method incrementFaithTrackPosition is used to increment the faith track position,
         * it also checks if a vatican report is activated, in that case it throws an exception
         */
        public void incrementFaithTrackPosition(){
                if(faithTrackPosition<24)
                        faithTrackPosition++;
        }
        /**
         * throws an exception if a certain Faith Track position has been reached without activating a Vatican report
         * (e.g. if a player's Faith Track position is greater than 8, the player's first Pope Favor tile status must be
         * active
         */

        public void checkVaticanReport() throws VaticanReportException{
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
         * @param resource is the type of resource to be removed
         */
        public void removeStrongboxResource(Resource resource){
                if(strongboxResources.containsKey(resource) && strongboxResources.get(resource)>0)
                        strongboxResources.put(resource,strongboxResources.get(resource)-1);
        }

        /**
         *  this method returns a Map with the resources inside the strongbox
         * @return a Map with containing all the resources inside the strongbox
         */
        public Map<Resource,Integer> getStrongboxResources() {
                return new HashMap<>(strongboxResources);
        }

        /**
         *  you give to this method a Map of resources and it adds the resources in the strongbox
         *  (so in the end you have in the strongbox the old resources + the new resources)
         * @param resources ia the Map of resources to be added to the strongbox
         */
        public void addStrongboxResources(Map <Resource,Integer> resources) {
                strongboxResources = Resource.sumResourcesMap(strongboxResources,resources);
        }

        /**
         * getter of the warehouse
         *@return a player's warehouse
         */
        public PlayerWarehouse getPlayerWarehouse(){
                return playerWarehouse;
        }

        /**
         * getter of the personal card board
         *@return a player's personal card board
         */
        public PersonalCardBoard getPersonalCardBoard(){
                return personalCardBoard;
        }

        /**
         *  This method returns a list with the Leader cards of the player.
         * @return the Leader cards of the player
         */
        public List<LeaderCard> getPlayerLeaderCards(){
                return new ArrayList<>(leaderCards);
        }

        /**
         *  you give to this method an index and it returns the player's Leader card in that position
         * @param index is the Leader card's index
         * @return the index of the Leader card
         */
        public LeaderCard getLeaderCard(int index) throws IllegalArgumentException{
                if(index>=0 && index<=1)
                        return leaderCards.get(index);

                throw new IllegalArgumentException();
        }
        /**
         *  you give to this method a Leader card and it adds it to the player Leader cards list.
         * @param leaderCard is the added Leader card
         */
        public void addLeaderCard(LeaderCard leaderCard){
                if(leaderCard!=null){
                        leaderCards.add(leaderCard);
                }
        }

        /**
         * It removes two leader cards from the leader card list, you have to pass the indexes of the
         * card you want to remove.
         * @param index1 is the index of the first Leader card to be removed
         * @param index2 is the index of the second Leader card to be removed
         */
        public void removeTwoLeaderCards(int index1,int index2){
                List<LeaderCard> newList = new ArrayList<>();
                for(int i=0; i<4;i++){
                        if(i!=index1 && i!= index2){
                                newList.add(leaderCards.get(i));
                        }
                }
                leaderCards.clear();
                leaderCards.addAll(newList);
        }
        /**
         * this method returns all the resources of the player.
         * @return a Map with all of the player's resources.
         */
        public Map<Resource,Integer> getAllResources(){
                Map<Resource,Integer> allResources = Resource.sumResourcesMap(strongboxResources,playerWarehouse.getAllResources());
                for(LeaderCard leaderCard: leaderCards){
                        if(leaderCard.getFullSlotsNumber()!=null){
                                for(int i=0; i<leaderCard.getFullSlotsNumber();i++){
                                        allResources =Resource.addOneResource(allResources,leaderCard.getAbilityResource());
                                }
                        }
                }
                return allResources;
        }
        /**
         * this method returns the number of all the resources of the player.
         */
        public int getResourcesNumber(){
                return Resource.resourcesNum(getAllResources());
        }

        /**
         * this method removes resources: the priority is to remove resources from the warehouse,
         * then from the leader cards, then from the strongbox (it's the most favorable strategy
         * for the player)
         * PRECONDITION: before you call this method you have to be sure that the player owns the
         *               resources you want to remove
         * @param resources represents the resources to be removed
         */
        public void removeResources(Map<Resource,Integer> resources){
                int i;
                for(Resource r: resources.keySet()){
                        i=resources.get(r);
                        //search and remove resources from the warehouse
                        for(int rowW=1; rowW<=3 && i>0; ++rowW) {
                                for (int colW = 1; colW <= rowW && i > 0; ++colW){
                                        if (r == playerWarehouse.getResource(rowW, colW)) {
                                                playerWarehouse.removeResource(rowW, colW);
                                                --i;
                                        }
                                }
                        }
                        if(i>0){
                                for(LeaderCard leaderCard: leaderCards){
                                        if(leaderCard.getFullSlotsNumber()!=null && leaderCard.getAbilityResource()==r){
                                                while(leaderCard.getFullSlotsNumber()>0 && i>0){
                                                        leaderCard.removeResource();
                                                        i--;
                                                }
                                        }
                                }
                        }
                        while(i>0){
                                removeStrongboxResource(r);
                                --i;
                        }
                }
        }
}
