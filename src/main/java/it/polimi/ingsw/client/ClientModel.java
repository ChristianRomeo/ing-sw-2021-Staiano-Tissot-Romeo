package it.polimi.ingsw.client;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class ClientModel is a simplified model which the client sees
 */
//todo: bisogna inizializzarlo nel serverHandler

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

   private int numPlayers;
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

   public String getCurrentPlayerNick(){
      return currentPlayerNick;
   }

   //metodo che dice se il client è il current player o no
   public boolean isCurrentPlayer(){
      return myNickname.equals(currentPlayerNick);
   }


   public void initClientModel(List<String> nicknames, Market market, DevelopmentCardBoard developmentCardBoard){
      setMarket(market);
      setDevelopmentCardBoard(developmentCardBoard);
      this.playersNicknames = nicknames;
      numPlayers = playersNicknames.size();
      playersCardBoards = new ArrayList<>();
      playersWarehouses = new ArrayList<>();
      playersLeaderCards = new ArrayList<>();
      playersStrongboxes = new ArrayList<>();
      playersFTPositions = new ArrayList<>();
      playersPopeTiles = new ArrayList<>();

      for(int i=0; i<numPlayers; i++){
         playersCardBoards.add(new PersonalCardBoard());
         playersWarehouses.add(new PlayerWarehouse());
         playersLeaderCards.add(new ArrayList<>());
         playersStrongboxes.add(new HashMap<>());
         playersFTPositions.add(0);
         playersPopeTiles.add(new SameTypeTriple<>());
         playersPopeTiles.get(i).setVal1(PopeFavorTileStatus.INACTIVE);
         playersPopeTiles.get(i).setVal2(PopeFavorTileStatus.INACTIVE);
         playersPopeTiles.get(i).setVal3(PopeFavorTileStatus.INACTIVE);
      }
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

/* //metodo credo inutile
   public void setPlayersNicknames(List<String> playersNicknames) {
      this.playersNicknames = playersNicknames;
   }
*/
   public void setMyIndex(int myIndex) {
      this.myIndex = myIndex;
   }

   //this method sets the leader cards of a player
   public void setLeaderCards(String player, List<LeaderCard> leaderCards){
      playersLeaderCards.get(playersNicknames.indexOf(player)).clear();
      playersLeaderCards.get(playersNicknames.indexOf(player)).addAll(leaderCards);
   }

   //this method sets the warehouse of a player
   public void setWarehouse(String player, PlayerWarehouse warehouse){

      playersWarehouses.set(playersNicknames.indexOf(player),warehouse);
   }

   public int getMyIndex() {
      return myIndex;
   }

   //ritorna tutti i nicknames
   public List<String> getNicknames(){
      return  new ArrayList<>(playersNicknames);
   }

   public List<LeaderCard> getPlayerLeaderCards(String player){
      return playersLeaderCards.get(playersNicknames.indexOf(player));
   }


   //this method sets the strongbox of a player
   public void setStrongbox(String player, Map<Resource,Integer> strongbox){
      playersStrongboxes.set(playersNicknames.indexOf(player),strongbox);
   }

   //this method sets the personal card board of a player
   public void setPersonalCardBoard(String player, PersonalCardBoard personalCardBoard){
      playersCardBoards.set(playersNicknames.indexOf(player),personalCardBoard);
   }

   //this method sets the position of a player
   public void setFTPosition(String player, int position){
      playersFTPositions.set(playersNicknames.indexOf(player),position);
   }

   //this method sets the position of a player
   public void setPopeTiles(String player, SameTypeTriple<PopeFavorTileStatus> popeTileStatus){
      playersPopeTiles.set(playersNicknames.indexOf(player), popeTileStatus);
   }

}
