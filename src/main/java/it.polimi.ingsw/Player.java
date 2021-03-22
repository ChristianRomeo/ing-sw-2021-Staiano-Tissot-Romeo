package it.polimi.ingsw;

public class Player {
    private String nickname;
    private int victoryPoints;
    private StatusPlayer statusPlayer;

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
}
