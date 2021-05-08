package it.polimi.ingsw.client;

import it.polimi.ingsw.model.*;

import java.util.List;
import java.util.Map;

/**
 * Class ClientModel is a simplified model which the client sees
 */
//bisogna inizializzarlo nel serverHandler
public class ClientModel {
   private DevelopmentCardBoard developmentCardBoard;
   private Market market;

   //liste delle cose dei giocatori, volendo possiamo fare una classe ClientPlayer che racchiude queste info
   //e poi facciamo una sola lista di ClientPlayer (ma non per forza)

   private List<String> playersNicknames;
   private List<PersonalCardBoard> playersCardBoards;
   private List<Map<Resource,Integer>> playersStrongboxes;
   private List<PlayerWarehouse> playersWarehouses;
   private List<Integer> playersFTPositions;
   private List<SameTypeTriple<PopeFavorTileStatus>> playersPopeTiles;
   private List<List<LeaderCard>> playersLeaderCards;
   private List<Integer> playersVP;

   private int serverCookie;

   private int myIndex; //index del giocatore (se tipo è primo o secondo ecc)
   private String myNickname; //il nick del giocatore
   private String currentPlayerNick; // il nick del current player

   private boolean isPregame=false; //stiamo nel pregame
   private boolean isGameStarted=false; //è cominciato il gioco normale

   public ClientModel(int serverCookie) {
      this.serverCookie = serverCookie;
   }
   public ClientModel(){

   }

   public boolean isPregame() {
      return isPregame;
   }

   public boolean isGameStarted() {
      return isGameStarted;
   }

   public void setIsPregame(boolean isPregame) {
      this.isPregame = isPregame;
   }

   public void setIsGameStarted(boolean gameStarted) {
      isGameStarted = gameStarted;
   }

   public void setMyNickname(String nickname){
      this.myNickname=nickname;
   }

   public String getMyNickname(){
      return myNickname;
   }

   //metodo che dice se il client è il current player o no
   public boolean isCurrentPlayer(){
      return myNickname.equals(currentPlayerNick);
   }

   public void setCurrentPlayer(String nickname){
      this.currentPlayerNick=nickname;
   }

   public void setDevelopmentCardBoard(DevelopmentCardBoard developmentCardBoard) {
      this.developmentCardBoard = developmentCardBoard;
   }

   public void setMarket(Market market) {
      this.market = market;
   }

   public void setPlayersNicknames(List<String> playersNicknames) {
      this.playersNicknames = playersNicknames;
   }

   public void setMyIndex(int myIndex) {
      this.myIndex = myIndex;
   }

   //this method sets the leader cards of a player
   public void setLeaderCards(String player, List<LeaderCard> leaderCards){
      playersLeaderCards.get(playersNicknames.indexOf(player)).clear();
      playersLeaderCards.get(playersNicknames.indexOf(player)).addAll(leaderCards);
   }
}
