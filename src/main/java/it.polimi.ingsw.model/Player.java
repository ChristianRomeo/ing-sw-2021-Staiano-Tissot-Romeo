package it.polimi.ingsw.model;

public class Player {
    /**
     * The player's leader cards number, constant and common to all players
     */
    private static final int LEADER_CARDS_OWNED = 2;
    /**
     * The name chosen by the physical player
     */
    private String nickname;
    private int victoryPoints;
    private StatusPlayer statusPlayer;

    /**
     * Getter of nickname
     *
     * @return the nickname of the player
     */
    public String getNickname() {
      return nickname;
    }
    public int getVictoryPoints() {
        return victoryPoints;
    }
    public void calculateAndSetVictoryPoints(){}
    public StatusPlayer getStatusPlayer(){
        return statusPlayer;
    }
    /**
     * Setter of nickname
     *
     * @param nickname a new nickname for the player
     */
    public void setNickname(String nickname) {
        this.nickname=nickname;
    }
}
