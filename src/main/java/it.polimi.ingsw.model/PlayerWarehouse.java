package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author enrico
 */
public class PlayerWarehouse {
    private Resource upperRow;
    private SameTypePair<Resource> middleRow;
    private SameTypeTriple<Resource> lowerRow;

    public PlayerWarehouse(){
        upperRow=null;
        middleRow = new SameTypePair<Resource>();
        lowerRow = new SameTypeTriple<Resource>();
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
        if(row==1){
            return upperRow;
        }
        if(row==2){
            return middleRow.get(col);
        }
        if(row==3){
            return lowerRow.get(col);
        }
        return null;
    }

    /*the 3 following methods are getter too, if you prefer you can use them,
     but I recommend to use getResource*/
    public Resource getUpperRowResource(){
        return upperRow;
    }
    public Resource getMiddleRowResource(int col){
        return middleRow.get(col);
    }
    public Resource getLowerRowResource(int col){
        return lowerRow.get(col);
    }

    //this method returns all the resources in the warehouse
    public Map<Resource,Integer> getAllResources(){
        Map<Resource,Integer> resources = new HashMap<>();
        Resource r;
        for(int i=1; i<=3; i++){
            for(int j=1; j<=i; j++){
                r=getResource(i,j);
                if(r!=null){
                    if(!resources.containsKey(r)){
                        resources.put(r,1);
                    }else{
                        resources.put(r,resources.get(r)+1);
                    }
                }
            }
        }
        return resources;
    }
    /**
     * Method insertResource is used to insert a Resource in a position in the warehouse,
     * and it controls that the insertion respects the rules
     * of the warehouse (and that the position wasn't already full)
     *
     * @param row is the row of the warehouse where you want to insert the resource
     * @param col is the column of the warehouse where you want to insert the resource
     * @param resource is the resource you want to insert in the warehouse
     */
    public void insertResource(Resource resource, int row, int col) throws InvalidWarehouseInsertionException{
        if(row==1){
            setUpperRow(resource);
        }
        if(row==2){
            setMiddleRow(resource, col);
        }
        if(row==3){
            setLowerRow(resource,col);
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
        Resource removedResource;
        removedResource=getResource(row,col);
        if(row==1){
            upperRow=null;
        }
        if(row==2){
            middleRow.set(null,col);
        }
        if(row==3){
            lowerRow.set(null,col);
        }
        return removedResource;
    }

    //helper private methods
    private void setUpperRow(Resource resource) throws InvalidWarehouseInsertionException{
        if(upperRow!=null || middleRow.contains(resource)|| lowerRow.contains(resource)){
            throw new InvalidWarehouseInsertionException();
        }
        else{
            upperRow=resource;
        }
    }
    private void setMiddleRow(Resource resource, int col) throws InvalidWarehouseInsertionException{
        if(middleRow.get(col)!=null||upperRow==resource || lowerRow.contains(resource)){
            throw new InvalidWarehouseInsertionException();
        }

        for(Resource r: Resource.values()){
            if(r!=resource && middleRow.contains(r)){
                throw new InvalidWarehouseInsertionException();
            }
        }
        middleRow.set(resource,col);
    }
    private void setLowerRow(Resource resource, int col) throws InvalidWarehouseInsertionException{
        if(lowerRow.get(col)!=null||upperRow==resource || middleRow.contains(resource)){
            throw new InvalidWarehouseInsertionException();
        }
        for(Resource r: Resource.values()){
            if(r!=resource && lowerRow.contains(r)){
                throw new InvalidWarehouseInsertionException();
            }
        }
        lowerRow.set(resource, col);
    }
}
