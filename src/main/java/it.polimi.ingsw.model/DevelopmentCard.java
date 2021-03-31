package it.polimi.ingsw.model;

import java.util.Map;

/**
 *
 * @see DevelopmentCardBoard
 * @author chris
 */

public class DevelopmentCard {
    private final int id;
    private final CardType type;                      //colore carta - type
    private final int level;                          //level
    private final Map<Resource, Integer> needs;      //cost
    private final int victoryPoints;                  //victoryPoints
    private final Map<Resource, Integer> toGive;   //requiredResources
    private final Map<Resource, Integer> toHave;   //producedResources
    //private int producedFaithPoints;            //??

    public DevelopmentCard(int id, Map<Resource, Integer> toGive, Map<Resource, Integer> toHave, int victoryPoints, CardType type, int level, Map<Resource, Integer> needs) {
        this.id = id;
        this.type = type;
        this.level = level;
        this.needs = needs;
        this.victoryPoints = victoryPoints;
        this.toGive = toGive;
        this.toHave = toHave;
    }

    public int getId(){ return id; }

    public CardType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public Map<Resource, Integer> getNeeds() {
        return needs;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public Map<Resource, Integer> getToGive() {
        return toGive;
    }

    public Map<Resource, Integer> getToHave() {
        return toHave;
    }

    //public int getProducedFaithPoints() {return producedFaithPoints;}

}