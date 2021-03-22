package it.polimi.ingsw;

import java.util.Map;

public class StatusPlayer {

        private int faithTrackPosition;
        private PlayerWarehouse playerWarehouse;
        private Map<Resource,Integer> strongboxResources;
        private Triple<Integer,Integer,Integer> popeFavorTiles;
        private PersonalCardBoard personalCardBoard;
        private LeaderCard[] leaderCards;

        public int getFaithTrackPosition() {
            return faithTrackPosition;
        }

        public Map<Resource,Integer> getStrongboxResources() {
            return strongboxResources;
        }
        public Triple<Integer, Integer, Integer> getPopeFavorTilesSituation() {

                return popeFavorTiles;
        }
        public void incrementFaithTrackPosition() {

        }
        public void setPopeFavorTileState(int tileID, int tileNewState) {

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
