package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

/**
 * type of resource
 *
 * @author enrico
 */
public enum Resource {
    COIN,
    STONE,
    SERVANT,
    SHIELD;

    /**
     * this static method adds one resource to a map of resources, and returns the new map (not old one modified)
     * example1:
     * map1={(coin,1),(servant,2)} resource = servant => return = {(coin,1), (servant,3)}
     * example2:
     * map1={(coin,1),(servant,2)} resource = stone => return={(coin,1), (servant,2), (stone,1)}
     */
    public static Map<Resource,Integer> addOneResource(Map<Resource,Integer> map, Resource resource){
        Map<Resource,Integer> newMap = new HashMap<>();
        newMap.put(resource,1);
        return sumResourcesMap(newMap,map);
    }

    /**
     * this static method sum two map of resources, example:
     * map1={(coin,1),(servant,2)} map2={(coin,2) , (shield,1)} => sum={(coin,3), (servant,2), (shield,1)}
     */
    public static Map<Resource,Integer> sumResourcesMap(Map<Resource,Integer> map1,Map<Resource,Integer> map2){
        if(map1==null)
            return new HashMap<>(map2);

        if(map2==null)
            return new HashMap<>(map1);

        Map<Resource, Integer> sum = new HashMap<>(map1);
        for(Resource r: map2.keySet()) {
            if (!sum.containsKey(r))
                sum.put(r, map2.get(r));
            else
                sum.put(r, sum.get(r) + map2.get(r));
        }
        return sum;
    }

    /**
     * this static method removes a map of resources from another map (map1-map2).
     * you can call this method only if the map1 contains all the resources of map2
     */
    public static Map<Resource,Integer> removeResourcesMap(Map<Resource,Integer> map1,Map<Resource,Integer> map2){
        if(!Resource.enoughResources(map1,map2)){
            return null;
        }
        Map<Resource, Integer> newMap = new HashMap<>(map1);
        for(Resource r: map2.keySet()) {
            newMap.put(r,newMap.get(r)-map2.get(r));
            if(newMap.get(r)==0){
                newMap.remove(r);
            }
        }
        return newMap;
    }

    /**
     * this static method tells if in map1 you have all the resources of map2.
     * it returns true only if for every resource type in map2, map1 has that resource type
     * in quantity equal or greater of the quantity in map2
     */
    public static boolean enoughResources(Map<Resource,Integer> map1,Map<Resource,Integer> map2){
        if(map2==null)
            return true;

        if(map1==null)
            return false;

        for(Resource r: map2.keySet())
            if(!map1.containsKey(r) || map1.get(r) < map2.get(r))
                return false;

        return true;
    }

    /**
     * this static method returns the number of all the resources in map1.
     */
    public static int resourcesNum(Map<Resource,Integer> map1){
        if(map1==null){
            return 0;
        }
        int resourcesNumber=0;
        for(Resource r: map1.keySet()){
            resourcesNumber += map1.get(r);
        }
        return  resourcesNumber;
    }
}
