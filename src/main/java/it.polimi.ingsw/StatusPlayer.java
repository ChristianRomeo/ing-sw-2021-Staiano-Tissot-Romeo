package it.polimi.ingsw;

import java.util.Map;

public class StatusPlayer {

        private int faithTrackPosition;
        private Resource[][] warehouseResources;
        private Map<Resource,Integer> strongboxResources;
        private int popeFavorTiles[];
        private PersonalCardBoard personalCardBoard;
        private LeaderCard[] leaderCards;

        public int getFaithTrackPosition() {
            return faithTrackPosition;
        }
        public Resource[][] getWarehouseResources() {
            return warehouseResources;
        }
        public Map<Resource,Integer> getStrongboxResources() {
            return strongboxResources;
        }
        public int[] getPopeFavorTilesSituation() {
            return popeFavorTiles;
        }
        public void incrementFaithTrackPosition() {

        }
        public void setPopeFavorTileState(int tileID, int tileNewState) {

        }
        public void setWarehouse(Resource resources) {

        }

        public void setResourceStrongbox(Map <Resource,Integer> resources) {

        }


}
