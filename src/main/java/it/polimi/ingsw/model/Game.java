package it.polimi.ingsw.model;


import it.polimi.ingsw.controller.Events.*;
import it.polimi.ingsw.model.modelExceptions.VaticanReportException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Game class contains the main logic of "Master of Renaissance", which is divided in several macro-areas. The
 * first area is the Player section, which contains information about the single player. The second area is the
 * GameBoard section which contains the faith track and
 *
 */

public class Game extends ServerObservable { //game is observed by the virtual view
    private static final int MAXPLAYERS = 4; //todo:controllare che non viene superato
    private final Board board;
    private final List<Player> players;
    private Player currentPlayer;
    private int currentPlayerId;
    private final List<LeaderCard> leaderCards = new ArrayList<>();
    private boolean lastTurns;
    private int wantedNumPlayers=0; //lo 0 serve per un check in Server.java
    private boolean isActive;
    private final List<IllegalAction> illegalActions; //list of illegal action
    private boolean hasDoneAction; // true if the current player already did a main action (leader actions not included)
    private ServerEventCreator eventCreator;

    /**
     * Constructor Game creates a new Game instance.
     */
    public Game() throws IOException {
        board = new Board();
        players = new ArrayList<>();
        currentPlayerId=0;
        lastTurns = false;
        illegalActions =new ArrayList<>();
        this.isActive=true;
        hasDoneAction=false;
    }

    /**
     * shuffles the players' list
     */
    public void shufflePlayers(){
        Collections.shuffle(players);
    }

    public void setEventCreator(ServerEventCreator eventCreator){
        this.eventCreator = eventCreator;
    }

    /**
     * @return the game status: true if it is active, false otherwise
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * sets the game status to inactive
     */
    public void setInactive() {
        isActive = false;
    }

    /**
     * @return the wanted number of players for the match
     */
    public int getWantedNumPlayers() {
        return wantedNumPlayers;
    }

    /**
     * sets the wanted number of players for the match
     */
    public void setWantedNumPlayers(int numPlayers) {
        this.wantedNumPlayers = numPlayers;
    }

    /**
     * @return a list containing all the match's players
     */
    public List<Player> getPlayers(){   //questi sono tutti, connessi e disconnessi eventuali, se servono sono quelli connessi fare altro metodo
        return new ArrayList<>(players);
    }

    /**
     * Method getBoard returns the board of this Game object.
     *
     * @return the board (type Board) of this Game object.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Method addNewPlayer creates a new player in the game.
     * The minimum length of Players is 1 elements, and the maximum is 4.
     *
     * @param player of type Player not null - the player to be added.
     */
    public void addNewPlayer(Player player) {
            players.add(player);
    }

    /**
     * Method getPlayerByNickname finds the player identified by his nickname in the list of players.
     *
     * @param nickname of type String that is the nickname of the player.
     * @return Player that is the desired player, null if there's no player with that nickname.
     */
    public Player getPlayerByNickname(String nickname) {
        for (Player player : players)
            if (player.getNickname().equalsIgnoreCase(nickname))
                return player;

        return null;    //todo: throws error
    }

    /**
     * Method setCurrentPlayer updates the "currentPlayer" with the desired one.
     *
     * @param player of type Player that is the player to be set as current.
     */
    public void setCurrentPlayer(Player player) {
        this.currentPlayerId = players.indexOf(player);
        this.currentPlayer = player;
    }

    /**
     * Method getPlayerNumber returns the number of the players in this Game.
     *
     * @return int that is the number (1 to MAXPLAYERS) of players of this Game Object.
     */
    public int getPlayersNumber() {
        return players.size();
    }

    /**
     * Method getCurrentPlayer returns the currentPlayer of the game.
     *
     * @return the currentPlayer (type Player) of this Game object.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Method getPlayerByIndex finds the player identified by his index in the list of players.
     *
     * @param playerIndex of type int that is the index of the player.
     * @return Player that is the desired player.
     */
    public Player getPlayerByIndex(int playerIndex){
        //todo playerIndex must not exceed getPlayersNumber()
        return players.get(playerIndex);
    }

    /** Method nextTurn updates currentPlayer to the next player in "players" order. */
    public void nextTurn() {
        hasDoneAction=false;
        if(players.size()==1){
            if(lastTurns){
                endGame();
            }else{
                SoloAction activatedSoloAction = lorenzoTurn();
                notifyAllObservers(new LorenzoTurnEventS2C(activatedSoloAction,getBoard().getDevelopmentCardBoard())); //qui notifico con evento cose che ha fatto lorenzo
                if(lastTurns){
                    endGame();
                }else{
                    notifyAllObservers(eventCreator.createNewTurnEvent(players.get(0)));//manda messaggio è il tuo turno di nuovo
                }
            }
        }
        else{ //per partita non in solitario
            if(!lastTurns || currentPlayerId!=(players.size()-1)){  //controlli tipo player == null || !players.contains(player) ci vanno?
                currentPlayerId = (currentPlayerId == players.size() - 1) ? 0 : currentPlayerId + 1;
                setCurrentPlayer(players.get(currentPlayerId));
                notifyAllObservers(eventCreator.createNewTurnEvent(currentPlayer));
            }
            else
                endGame();
        }
    }

    /**
     * Method getCurrentPlayerId is a getter of the current player id.
     *
     * @return the current player id
     */
    public int getCurrentPlayerId(){
        return currentPlayerId;
    }

    /**
     * Method setLastTurnsTrue set the flag lastTurns=true.
     */
    public void setLastTurnsTrue(){
        lastTurns=true;
    }


    /**
     * compares all the points obtained by the players:
     * If solo mode, it checks whether you Lorenzo's Faith Track position has reached 24 or if there's an empty column
     * from the Development Card Board. In these cases, Lorenzo wins. Otherwise, you win.
     * If not Solo mode, it checks which player has the highest Victory Points;
     * if some players have the same Victory Points, it compares their amount of resources, and the winner is the one who has
     * more resources.
     * If they have the same Victory Points and the same amount of resources, they'll be winners together.
     */
    private void endGame(){
        for(Player p: players)
            p.calculateAndSetVictoryPoints();

        if(players.size()==1){
            if(!board.getDevelopmentCardBoard().isAColumnEmpty() && board.getLorenzo().getStatusPlayer().getFaithTrackPosition()<24){
                players.get(0).setIsWinner();
            }
        }
        else{// questa parte può essere refactorizzata
            List<Player> bestPlayers = new ArrayList<>(players);
            for(Player p: bestPlayers)
                for(Player p1: bestPlayers){
                    if(p1.getVictoryPoints()>p.getVictoryPoints()){
                        bestPlayers.remove(p);
                        break;
                    }
                    if(p1.getVictoryPoints()==p.getVictoryPoints() && p1.getStatusPlayer().getResourcesNumber()>p.getStatusPlayer().getResourcesNumber()){
                        bestPlayers.remove(p);
                        break;
                    }
                }


            for(Player p: bestPlayers)
                p.setIsWinner();
        }

        notifyAllObservers(eventCreator.createEndGameEvent());
    }


    /**
     * Adds an illegal action to the list.
     */
    public void addIllegalAction(IllegalAction illegalAction){
        illegalActions.add(illegalAction);
        notifyAllObservers(new IllegalActionEventS2C(illegalAction));
    }

    public List<IllegalAction> getIllegalActions(){
        return new ArrayList<>(illegalActions);
    }

    public boolean hasDoneAction() {
        return hasDoneAction;
    }

    public void setHasDoneAction() {
        hasDoneAction=true;
    }


    /**
     * Increases all players Faith Track position by 1, except for the current player (who is discarding resources)
     * If a vatican report is activated, it calls the handlers of the players.
     * * It also checks if the match is ending (a player has come to the last cell)
     */

    public void incrementOthersFpByDiscarding(){
        for(int k=0; k<getPlayersNumber(); k++){
            if (getCurrentPlayerId()!=k){
                getPlayerByIndex(k).getStatusPlayer().incrementFaithTrackPosition();
                notifyAllObservers(eventCreator.createIncrementPositionEvent(getPlayerByIndex(k)));
            }
        }
        try{
            for(int k=0; k< getPlayersNumber(); k++){
                if (getCurrentPlayerId()!=k){
                    getPlayerByIndex(k).getStatusPlayer().checkVaticanReport();
                }
            }
        }catch (VaticanReportException e){
            for(int i=0; i<getPlayersNumber(); i++){
                getPlayerByIndex(i).getStatusPlayer().vaticanReportHandler(e.getReportId());
            }
            notifyAllObservers(eventCreator.createVaticanReportEvent());
            if(e.getReportId()==3){
                //a player has come to the last cell of the track, so the game is in the final phase

                setLastTurnsTrue();
            }
        }
    }

    /**
     * Method incrementFaithTrackPosition is used to increment the faith track position of a
     * player you choose. If a vatican report is activated, it calls the handlers of the players.
     * It also checks if the match is ending (a player has come to the last cell)
     *
     * @param player is the chosen player
     */
    public void incrementFaithTrackPosition(Player player){
        try{
            player.getStatusPlayer().incrementFaithTrackPosition();
            notifyAllObservers(eventCreator.createIncrementPositionEvent(player));
            player.getStatusPlayer().checkVaticanReport();
        }catch(VaticanReportException e){
            for(int i=0; i< getPlayersNumber(); ++i){
                getPlayerByIndex(i).getStatusPlayer().vaticanReportHandler(e.getReportId());
            }

            board.getLorenzo().getStatusPlayer().vaticanReportHandler(e.getReportId());

            notifyAllObservers(eventCreator.createVaticanReportEvent());
            if(e.getReportId()==3){
                //a player has come to the last cell of the track, so the game is
                //in the final phase
                setLastTurnsTrue();
            }
        }
    }

    /**
     * Method to manage Lorenzo's turn in the Solo mode
     * Lorenzo will act based on the SoloAction picked from the SoloAction pile
     * checks if a Pile of DevelopmentCards is empty, then Lorenzo wins.
     *
     * It returns the activated solo action in this turn.
     */
    public SoloAction lorenzoTurn(){
        SoloAction activatedSoloAction = getBoard().pickSoloAction();

        if(getBoard().getDevelopmentCardBoard().isAColumnEmpty()){
            setLastTurnsTrue();    //lorenzo wins
            return activatedSoloAction;
        }

        if(activatedSoloAction.getType()==SoloActionType.MOVEONEANDSHUFFLE){
            incrementFaithTrackPosition(getBoard().getLorenzo());
            //if lorenzo posizione 24 allora win (ciò è già settato in incrementFTP)
        }

        if(activatedSoloAction.getType()==SoloActionType.MOVETWO){
            incrementFaithTrackPosition(getBoard().getLorenzo());
            incrementFaithTrackPosition(getBoard().getLorenzo());
        }

        if(getBoard().getDevelopmentCardBoard().isAColumnEmpty())
            setLastTurnsTrue();    //lorenzo wins

        return activatedSoloAction;
    }
}