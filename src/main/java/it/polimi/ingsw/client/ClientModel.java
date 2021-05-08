package it.polimi.ingsw.client;

import it.polimi.ingsw.model.*;

import java.util.List;

/**
 * Class ClientModel is a simplified model which the client sees
 */
//bisogna inizializzarlo nel serverHandler
public class ClientModel {
   private DevelopmentCardBoard developmentCardBoard;
   private Market market;
   private List<Player> players;
   private List<LeaderCard> personalLeaderCards;
   private List<PersonalCardBoard> othersCardBoard;
   private List<List<LeaderCard>> othersLeaderCards;
   private PersonalCardBoard personalCardBoard;
   private int currentVictoryPoints;
   private List<Integer> othersCurrentVP;
   private int serverCookie;
   private String myNickname; //il nick del giocatore

   public void setMyNickname(String nickname){
      this.myNickname=nickname;
   }

   public String getMyNickname(){
      return myNickname;
   }


   public ClientModel(int serverCookie) {
      this.serverCookie = serverCookie;
   }
   public ClientModel(){

   }
}
