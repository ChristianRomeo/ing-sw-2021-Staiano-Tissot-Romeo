package it.polimi.ingsw.model;


public class PlayerWarehouse {
    private Resource upperRow;
    private SameTypePair<Resource> middleRow;
    private SameTypeTriple<Resource> lowerRow;

    public PlayerWarehouse(){
        upperRow=null;
        middleRow = new SameTypePair<Resource>();
        lowerRow = new SameTypeTriple<Resource>();
    }
    //method which takes a row and a column and returns the Resource in that position of the warehouse (or null)
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
    public Resource getUpperRow(){
        return upperRow;
    }
    public Resource getMiddleRowResource(int col){
        return middleRow.get(col);
    }
    public Resource getLowerRowResource(int col){
        return lowerRow.get(col);
    }
    //this method is used to insert a Resource in a position in the warehouse, and it controls that the
    // inseriment respects the rules of the warehouse (and that the position wasn't already full)
    public void insertResource(Resource resource, int row, int col) throws InvalidWarehouseInserimentException{
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
    //this method remove a resource from a position and returns the removed Resource
    // (the position is setted as null)
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
    private void setUpperRow(Resource resource) throws InvalidWarehouseInserimentException{
        if(upperRow!=null || middleRow.contains(resource)|| lowerRow.contains(resource)){
            throw new InvalidWarehouseInserimentException();
        }
        else{
            upperRow=resource;
        }
    }
    private void setMiddleRow(Resource resource, int col) throws InvalidWarehouseInserimentException{
        if(middleRow.get(col)!=null||upperRow==resource || lowerRow.contains(resource)){
            throw new InvalidWarehouseInserimentException();
        }

        for(Resource r: Resource.values()){
            if(r!=resource && middleRow.contains(r)){
                throw new InvalidWarehouseInserimentException();
            }
        }
        middleRow.set(resource,col);
    }
    private void setLowerRow(Resource resource, int col) throws InvalidWarehouseInserimentException{
        if(lowerRow.get(col)!=null||upperRow==resource || middleRow.contains(resource)){
            throw new InvalidWarehouseInserimentException();
        }
        for(Resource r: Resource.values()){
            if(r!=resource && lowerRow.contains(r)){
                throw new InvalidWarehouseInserimentException();
            }
        }
        lowerRow.set(resource, col);
    }
}
