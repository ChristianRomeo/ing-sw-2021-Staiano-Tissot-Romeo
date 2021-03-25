package it.polimi.ingsw.model;

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

    public void setNickname(String nickname) {
        this.nickname=nickname;
    }
}
