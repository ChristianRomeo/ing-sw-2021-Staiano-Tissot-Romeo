package it.polimi.ingsw.client;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class ClientModel is a simplified model which the client sees
 */


public class ClientModel {
   //liste delle cose dei giocatori, volendo possiamo fare una classe ClientPlayer che racchiude queste info
   //e poi facciamo una sola lista di ClientPlayer (ma non per forza)

   private DevelopmentCardBoard developmentCardBoard;

   private Market market;

   private List<String> playersNicknames; //in ordine di gioco

   private List<PersonalCardBoard> playersCardBoards;

   private List<Map<Resource,Integer>> playersStrongboxes;

   private List<PlayerWarehouse> playersWarehouses;

   private List<Integer> playersFTPositions;

   private int blackCrossPosition;

   private SoloAction lastSoloActionUsed = null;

   private List<SameTypeTriple<PopeFavorTileStatus>> playersPopeTiles;

   private List<List<LeaderCard>> playersLeaderCards;

   private List<String> winners; //list of the winners of the match

   private Map<String,Integer> victoryPoints; //map of nickname->pv of that player

   private int serverCookie;

   private int numPlayers;

   private int myIndex; //index del giocatore (se tipo è primo o secondo ecc)

   private String myNickname; //il nick del giocatore

   private String currentPlayerNick; // il nick del current player

   private boolean isPregame=false; //stiamo nel pregame

   private boolean hasGameStarted=false; //è cominciato il gioco normale

   private boolean hasGameEnded = false;

   /**
    * constructor of the ClientModel class
    * @param serverCookie is the server cookie to be set
    */
   public ClientModel(int serverCookie) {
      this.serverCookie = serverCookie;
   }

   /**
    * getter of the market
    * @return the market
    */
   public synchronized Market getMarket() {
      return market;
   }

   public synchronized List<String> getPlayersNicknames() {
      return playersNicknames;
   }

   /**
    * gets all the players card boards
    * @return  a list containing all the players card boards
    */
   public synchronized List<PersonalCardBoard> getPlayersCardBoards() {
      return playersCardBoards;
   }

   /**
    * gets all the players faith track positions
    * @return a list containing the players faith track positions
    */
   public synchronized List<Integer> getPlayersFTPositions() {
      return playersFTPositions;
   }

   public synchronized int getBlackCrossPosition(){
      return blackCrossPosition;
   }

   /**
    * gets all the players pope favor tiles
    * @return a list containing the players pope tiles
    */
   public synchronized List<SameTypeTriple<PopeFavorTileStatus>> getPlayersPopeTiles() {
      return playersPopeTiles;
   }

   public synchronized List<List<LeaderCard>> getPlayersLeaderCards() {
      return playersLeaderCards;
   }

   public synchronized int getServerCookie() {
      return serverCookie;
   }

   public synchronized int getNumPlayers(){
      return numPlayers;
   }

   /**
    * gets the development card board
    * @return the development card board
    */
   public synchronized DevelopmentCardBoard getDevelopmentCardBoard() {
      return developmentCardBoard;
   }

   // public List<Integer> getPlayersVP() { credo non serve
   //   return playersVP;
   //}
   /**
    * checks if the game has ended
    * @return a boolean which says if the game has ended or not
    */
   public boolean hasGameEnded() {
      return hasGameEnded;
   }

   /**
    * sets the status of the game
    * @param gameEnded is a boolean containing if the game has ended or not
    */
   public void setGameEnded(boolean gameEnded) {
      hasGameEnded = gameEnded;
   }

   /**
    * checks if the game is in pre-game mode
    * @return a boolean which says if the game is in pre-game mode or not
    */
   public synchronized boolean isPregame() {
      return isPregame;
   }

   /**
    * checks if the game has started
    * @return a boolean which says if the game has started or not
    */
   public synchronized boolean hasGameStarted() {
      return hasGameStarted;
   }

   /**
    * sets the game status to pre-game
    * @param isPregame is a boolean containing if the game has to be set to pregame
    */
   public synchronized void setIsPregame(boolean isPregame) {
      this.isPregame = isPregame;
   }

   /**
    * sets the game status to started
    * @param  gameStarted is a boolean containing if the game has to be set to started
    */
   public synchronized void setHasGameStarted(boolean gameStarted) {
      hasGameStarted = gameStarted;
   }

   /**
    * sets the nickname of the player
    * @param  nickname is the nickname to be set for the player who called it
    */
   public synchronized void setMyNickname(String nickname){
      this.myNickname=nickname;
   }

   /**
    * gets the nickname of the player
    * @return nickname of the player who called it
    */
   public synchronized String getMyNickname(){
      return myNickname;
   }

   /**
    * gets the current player's nickname
    * @return the current player's nickname
    */
   public synchronized String getCurrentPlayerNick(){
      return currentPlayerNick;
   }

   /**
    * checks if the player who called it is the current player
    * @return a boolean containing if the player who called it is the current player or not
    */
   public synchronized boolean isCurrentPlayer(){
      return myNickname.equals(currentPlayerNick);
   }

   public synchronized List<Map<Resource, Integer>> getPlayersStrongboxes() {
      return new ArrayList<>(playersStrongboxes);
   }

   /**
    * gets all the players warehouses
    * @return the players warehouses
    */
   public synchronized List<PlayerWarehouse> getPlayersWarehouses() {
      return new ArrayList<>(playersWarehouses);
   }

   /**
    * initializes the client model, also setting for example all the players pope favore tiles statuses to INACTIVE
    * @param  nicknames is the list containing all the players nicknames
    * @param  market is the market
    * @param  developmentCardBoard is the development card board
    */
   public synchronized void initClientModel(List<String> nicknames, Market market, DevelopmentCardBoard developmentCardBoard){
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

      for(int i=0; i<numPlayers; ++i){
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

   /**
    * sets the current player
    * @param nickname is the player's nickname to be set to current player
    */
   public synchronized void setCurrentPlayer(String nickname){
      this.currentPlayerNick=nickname;
   }

   /**
    * sets the development card board
    * @param developmentCardBoard is the development card board to be set
    */
   public synchronized void setDevelopmentCardBoard(DevelopmentCardBoard developmentCardBoard) {
      this.developmentCardBoard = developmentCardBoard;
   }
   /**
    * sets the market
    * @param market is the market to be set
    */
   public synchronized void setMarket(Market market) {
      this.market = market;
   }

/* //metodo credo inutile
   public void setPlayersNicknames(List<String> playersNicknames) {
      this.playersNicknames = playersNicknames;
   }
*/
   /**
    * sets the index for the player who called it (might be first player, second player etc...)
    * @param myIndex is the index to be set for the player
    */
   public synchronized void setMyIndex(int myIndex) {
      this.myIndex = myIndex;
   }

   /**
    * assigns the leader cards to every player
    * @param player is the player who will receive the specific leader cards
    * @param leaderCards represents the leader cards to be set for the specific player
    */
   public synchronized void setLeaderCards(String player, List<LeaderCard> leaderCards){
      playersLeaderCards.get(playersNicknames.indexOf(player)).clear();
      playersLeaderCards.get(playersNicknames.indexOf(player)).addAll(leaderCards);
   }

   //this method sets the warehouse of a player
   public synchronized void setWarehouse(String player, PlayerWarehouse warehouse){
      playersWarehouses.set(playersNicknames.indexOf(player),warehouse);
   }
   /**
    * getter of a player's index
    * @return the index of the player who called it
    */
   public synchronized int getMyIndex() {
      return myIndex;
   }

   /**
    * gets all the players nicknames
    * @return a list containing all the players nicknames
    */
   public synchronized List<String> getNicknames(){
      return  new ArrayList<>(playersNicknames);
   }

   /**
    * getter of a player's leader cards
    * @param player is the player's nickname
    * @return the list of the player's leader cards
    */
   public synchronized List<LeaderCard> getPlayerLeaderCards(String player){
      return playersLeaderCards.get(playersNicknames.indexOf(player));
   }

   /**
    * sets the player's strongbox
    * @param player is the player's nickname
    * @param strongbox is a map containg all the resources to be set to the player's strongbox
    */
   public synchronized void setStrongbox(String player, Map<Resource,Integer> strongbox){
      playersStrongboxes.set(playersNicknames.indexOf(player),strongbox);
   }

   /**
    * sets the player's personal card board
    * @param player is the player's nickname
    * @param personalCardBoard is the player's personal card board to be set
    */
   public synchronized void setPersonalCardBoard(String player, PersonalCardBoard personalCardBoard){
      playersCardBoards.set(playersNicknames.indexOf(player),personalCardBoard);
   }

   /**
    * sets the player's faith track position to a specific position
    * @param player is the player's nickname
    * @param position is the player's faith track position to be set
    */
   public synchronized void setFTPosition(String player, int position){
      playersFTPositions.set(playersNicknames.indexOf(player),position);
   }
   /**
    * sets the Lorenzo's black cross position to a specific position
    * (this method will be called only in single player mode)
    * @param blackCrossPosition is the player's black cross position to be set
    */
   public synchronized void setBlackCrossPosition(int blackCrossPosition){
      this.blackCrossPosition = blackCrossPosition;
   }
   /**
    * sets the last solo action used by Lorenzo.
    * @param soloAction the last solo action used.
    */
   public synchronized void setLastSoloActionUsed(SoloAction soloAction){
      this.lastSoloActionUsed = soloAction;
   }
   /**
    * @return the last solo action used by Lorenzo.
    *
    */
   public synchronized SoloAction getLastSoloActionUsed(){
      return lastSoloActionUsed;
   }

   /**
    * sets the player's pope tiles status to specific statuses
    * @param player is the player's nickname
    * @param popeTileStatus is the triple of pope favor tiles statuses to be set for the specific player
    */
   public synchronized void setPopeTiles(String player, SameTypeTriple<PopeFavorTileStatus> popeTileStatus){
      playersPopeTiles.set(playersNicknames.indexOf(player), popeTileStatus);
   }

   public synchronized void setWinners(List<String> winners){
      this.winners = winners;
   }

   public synchronized List<String> getWinners(){
      return winners;
   }

   public synchronized void setVictoryPoints(Map<String,Integer> victoryPoints){
      this.victoryPoints = victoryPoints;
   }

   public synchronized Map<String,Integer> getVictoryPoints(){
      return victoryPoints;
   }

   //this method takes marbles and returns the corresponding resources
   public synchronized List<Resource> fromMarblesToResources(List<MarbleColor> marbles, List<Integer> whiteMarbleChoices){

      if(marbles==null)
         return null;
      List<Integer> whiteMarbleChoices1= null;
      if(whiteMarbleChoices!=null){
         whiteMarbleChoices1 = new ArrayList<>(whiteMarbleChoices);
      }
      List<LeaderCard> leaderCards = getPlayerLeaderCards(getMyNickname());
      List<Resource> boughtResources = new ArrayList<>();
      for(MarbleColor m: marbles)
         switch (m) {
            case WHITE -> {
               if (leaderCards.get(0).isActivated() && leaderCards.get(0).getWhiteMarbleResource() != null){
                  if(leaderCards.get(1).isActivated() && leaderCards.get(1).getWhiteMarbleResource() != null){
                     boughtResources.add(leaderCards.get(whiteMarbleChoices1.remove(0)).getWhiteMarbleResource());
                  }else{
                     boughtResources.add(leaderCards.get(0).getWhiteMarbleResource());
                  }
               }else{
                  if(leaderCards.get(1).isActivated() && leaderCards.get(1).getWhiteMarbleResource() != null){
                     boughtResources.add(leaderCards.get(1).getWhiteMarbleResource());
                  }
               }
            }
            case RED -> { }
            case BLUE -> boughtResources.add(Resource.SHIELD);
            case GREY -> boughtResources.add(Resource.STONE);
            case PURPLE -> boughtResources.add(Resource.SERVANT);
            case YELLOW -> boughtResources.add(Resource.COIN);
         }
      return boughtResources;
   }
}
