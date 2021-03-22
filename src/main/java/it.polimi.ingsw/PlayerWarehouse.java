package it.polimi.ingsw;



public class PlayerWarehouse {
    private Resource upperRow;
    private Pair<Resource,Resource> middleRow;
    private Triple<Resource,Resource,Resource> lowerRow;

    public Resource getUpperRow(){
        return upperRow;

    }

    public Pair<Resource,Resource> getMiddelRow(){
        return middleRow;
    }
    public Triple<Resource,Resource,Resource> getLowerRow(){
        return lowerRow;
    }

    public void setOnePosition(Resource resource, int row, int col){

    }

    public void removeResource(int row, int col){

    }

}
