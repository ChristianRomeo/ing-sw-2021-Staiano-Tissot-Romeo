package it.polimi.ingsw.model;

import it.polimi.ingsw.model.modelExceptions.InvalidWarehouseInsertionException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represent the warehouse of the player with 3 rows of storage.
 * @author enrico
 */
public class PlayerWarehouse implements Serializable {
    private Resource upperRow;
    private final SameTypePair<Resource> middleRow;
    private final SameTypeTriple<Resource> lowerRow;

    public PlayerWarehouse(){
        upperRow=null;
        middleRow = new SameTypePair<>();
        lowerRow = new SameTypeTriple<>();
    }

    /**
     * Method getResource takes a row and a column and returns the Resource
     * in that position of the warehouse (or null)
     *
     * @param row is the row of the warehouse you want to select (1,2,3)
     * @param col is the column of the warehouse you want to select (1,2,3)
     * @return the resource in that position of the warehouse
     */
    public Resource getResource(int row, int col){
         switch (row) {
            case 1 -> {
                return upperRow;
            }
            case 2 -> {
                return middleRow.get(col);
            }
            case 3 -> {
                return lowerRow.get(col);
            }
            default -> {
                 return null;
             }
         }
    }

    /**
     * the following 3 methods are getter too, if you prefer you can use them,
     * but I recommend to use getResource. #Utils
     * @return Resource - type
     */
    public Resource getUpperRowResource(){
        return upperRow;
    }
    public Resource getMiddleRowResource(int col){
        return middleRow.get(col);
    }
    public Resource getLowerRowResource(int col){
        return lowerRow.get(col);
    }

    /**
     *  this method returns all the resources in the warehouse
     * @return A Map of all the resources owned by a player
     */
    public Map<Resource,Integer> getAllResources(){
        Map<Resource,Integer> resources = new HashMap<>();
        Resource r;
        for(int row=1; row<=3; ++row)
            for(int column=1; column<=row; ++column){
                r=getResource(row,column);

                if(r!=null)
                    if(!resources.containsKey(r))
                        resources.put(r,1);
                    else
                        resources.put(r,resources.get(r)+1);
            }


        return resources;
    }

    /**
     * Method insertResource is used to insert a Resource in a position in the warehouse,
     * and it controls that the insertion respects the rules
     * of the warehouse (and that the position wasn't already full)
     *
     * @param row is the row of the warehouse where you want to insert the resource (1,2,3)
     * @param col is the column of the warehouse where you want to insert the resource (1,2,3)
     * @param resource is the resource you want to insert in the warehouse
     */
    public void insertResource(Resource resource, int row, int col) throws InvalidWarehouseInsertionException {
        switch (row) {
            case 1 -> setUpperRow(resource);
            case 2 -> setMiddleRow(resource, col);
            case 3 -> setLowerRow(resource, col);
        }
    }

    /**
     * Method removeResource removes a resource from a position in the warehouse
     * and returns the removed Resource (the position is set as null)
     * @param row is the row of the resource you want to remove
     * @param col is the column of the resource you want to remove
     * @return the removed resource
     */
    public Resource removeResource(int row, int col){
        Resource removedResource=getResource(row,col);
        switch (row) {
            case 1 -> upperRow = null;
            case 2 -> middleRow.set(null, col);
            case 3 -> lowerRow.set(null, col);
        }
        return removedResource;
    }

    /**
     *     this method tells you if the warehouse respects the rules of the warehouse or not.
     */
    public boolean checkWarehouse() {
        if(middleRow.contains(upperRow)|| lowerRow.contains(upperRow)){
            return false;
        }
        for(Resource r: Resource.values()) {
            if (middleRow.contains(r) && lowerRow.contains(r)) {
                return false;
            }
        }
        for(int row=2; row<=3; ++row) {
            for (int col = 1; col <= row-1; ++col) {
                if (getResource(row, col) != null && getResource(row, col + 1) != null && getResource(row, col) != getResource(row, col + 1)) {
                    return false;
                }
            }
        }
        return getResource(3, 1) == null || getResource(3, 3) == null || getResource(3, 1) == getResource(3, 3);
    }

    /**
     *     this method removes all the resources from the warehouse.
     */
    public void clear(){
        for(int row=1; row<=3; ++row) {
            for (int column = 1; column <= row; ++column) {
                removeResource(row,column);
            }
        }
    }

    /**
     *     this method set all the resources in the warehouse, based on another warehouse you pass.
     *     If the warehouse you pass isn't valid it doesn't do anything (the warehouse won't change).
     */
    public void setWarehouse(PlayerWarehouse newWarehouse){
        if(!newWarehouse.checkWarehouse()){
            return;
        }
        this.clear();
        for(int row=1; row<=3; ++row) {
            for (int column = 1; column <= row; ++column) {
                try{
                    insertResource(newWarehouse.getResource(row,column),row,column);
                }catch (InvalidWarehouseInsertionException e){
                    System.out.println();//this exception won't be called, because before i do a check.
                }
            }
        }
    }

    //STI METODI POSSONO ESSERE CAMBIATI USANDO CHECK WAREHOUSE CREDO
    /**
     *     helper private methods
     */
    private void setUpperRow(Resource resource) throws InvalidWarehouseInsertionException{
        if(upperRow!=null || middleRow.contains(resource)|| lowerRow.contains(resource))
            throw new InvalidWarehouseInsertionException();
        else
            upperRow=resource;
    }
    //DRY violation
    private void setMiddleRow(Resource resource, int col) throws InvalidWarehouseInsertionException{
        if(middleRow.get(col)!=null||upperRow==resource || lowerRow.contains(resource))
            throw new InvalidWarehouseInsertionException();

        for(Resource r: Resource.values())
            if(r!=resource && middleRow.contains(r))
                throw new InvalidWarehouseInsertionException();

        middleRow.set(resource,col);
    }
    private void setLowerRow(Resource resource, int col) throws InvalidWarehouseInsertionException{
        if(lowerRow.get(col)!=null||upperRow==resource || middleRow.contains(resource))
            throw new InvalidWarehouseInsertionException();

        for(Resource r: Resource.values())
            if(r!=resource && lowerRow.contains(r))
                throw new InvalidWarehouseInsertionException();

        lowerRow.set(resource, col);
    }
}
