package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.Events.ServerEventCreator;
import it.polimi.ingsw.controller.Events.ServerObservable;
import it.polimi.ingsw.controller.controllerExceptions.DisconnectionException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelExceptions.*;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Logger;


/**
 * Controller, also
 * @see VirtualView
 */
public class Controller extends ServerObservable {
    private final Game game;
    private VirtualView virtualView;
    private final static Logger logger = Logger.getLogger(Server.class.getName());
    private final ServerEventCreator eventCreator;

    /**
     * constructor
     */
    public Controller(Game game){
        this.game=game;
        eventCreator = new ServerEventCreator(this);
        game.setEventCreator(eventCreator);
    }

    public Game getGame(){
        return game;
    }

    public void setVirtualView(VirtualView virtualView) {
        this.virtualView = virtualView;
        addObserver(virtualView);       //così la virtual view riceverà i server events inviati dal controller
        game.addObserver(virtualView); //cosi la virtual view riceve anche i server events inviati dal game
    }

    /**
     * By now the connection should be already set with all the players, the game should already initialized
     * initializing now clients LC and eventually resources and fp before starting the match
     * @throws FileNotFoundException
     */
    public void gameStarter() throws FileNotFoundException {

        /*synchronized (this) {     //in teoria non serve, c'è già il controllo prima di chiamarlo
            while (!gameIsReady() && isRunning()) {
                this.wait();
            }
        }*/

        logger.info("Starting the game");
        List<Player> players = game.getPlayers();
        List<LeaderCard> leaderCardList = Configs.getLeaderCards();

        //List<ClientHandler> players = virtualView.getClientHandlers();
        //for all players
        for (Player pl : players){

            List<LeaderCard> choices = new ArrayList<>();
            choices.add(leaderCardList.remove(0));
            choices.add(leaderCardList.remove(0));
            choices.add(leaderCardList.remove(0));
            choices.add(leaderCardList.remove(0));
            logger.info("Welcoming the Client...");
            notifyAllObservers(eventCreator.createGameStarterEvent(choices,pl));
            logger.info("cards from player"+ pl +" are chosen");
            if (game.getCurrentPlayerId()>0)
            logger.info("player"+ pl +"is choosing resources");
            if (game.getCurrentPlayerId()>1)
            logger.info("player"+ pl +"is getting fp");

        }
        //e mo che si fa? come si fa ad iniziare?
        //game();
    }

    /*private void game() {
        while (!game.hasWinner()) {
                if (!game.hasDoneAction()){
                    actionDispatch();   //bisogna passargli l'azione scelta
                    //sendToEveryone mossa
                }
                else{
                    //cambia turno
                }

            //if currentplayer turn hasEnded
            //currentplayer = nextTurnPlayer
            //else
            //scelta mossa giocatore; synch this ; wait;

        }

             manageWin();
    }

    private void manageWin() {

        logger.info("Game ended: " + game.getCurrentPlayer().getNickname() + " has won");
        for (Player p : game.getPlayers()) {
            //virtualView.getClientHandlerByNickname sendTo winner EndMessage
        }
    }

    private void manageLose(){
        //che succede ai giocatori che perdono?
        //removePlayer(currentPlayer);
        //facciamo una classifica?
    }

    public void actionDispatch(){
        //riceve l'azione dall'utente e chiama il metodo adeguato
        notifyController();
    }
    */

    public void setDisconnected(String nickname) {
        if (game.getPlayerByNickname(nickname) != null && game.hasWinner()) {
            game.getPlayerByNickname(nickname).setConnected(false);
            return;
        }

        //virtualView.sendToEveryone disconnection nickname
        //virtualView.closeAll();
        game.setInactive(); //if disconnection occur and want to close the game (to change if PERSISTENCE is made)
        notifyController();
        //wakeUpServerLauncher();   //synchronized (game) game.notifyAll();   //PER PARTITE MULTIPLE DOVREBBE STARE SEMPRE SVEGLIO RIGHT?
    }

    public boolean isRunning() throws DisconnectionException {
        if (!game.isActive())
            throw new DisconnectionException();

        return true;
    }

    public void notifyController() {
        synchronized (this) {
            this.notifyAll();
        }
    }

    /**
     * Method activateProduction allows the player to activate the production of one or more cards.
     * Also the base production and the leader card production can be activated.
     * @param activatedProductions tells which card productions the player want to activate, it contains
     *                             numbers between 0 and 2, the indexes of the cards he wants to activate
     *                             in his personal card board.
     * @param baseProd tells if the player wants to activate the base production or not.
     * @param baseRes it's a triple that contains three resources: the first two are the selected required
     *                resources of the base production, the third is the selected produced resource.
     * @param leaderRes1 it's the resource you want to produce using the first leader card you own.
     *                   If you can't or don't want to activate the production of that leader card
     *                   you have to set leaderRes1 to null.
     * @param leaderRes2 it's the resource you want to produce using the first leader card you own.
     *                   If you can't or don't want to activate the production of that leader card
     *                   you have to set leaderRes1 to null.
     *
     */
    public void activateProduction(List<Integer> activatedProductions, boolean baseProd, SameTypeTriple<Resource> baseRes, Resource leaderRes1, Resource leaderRes2) throws IllegalArgumentException{
        PersonalCardBoard personalCardBoard = game.getCurrentPlayer().getStatusPlayer().getPersonalCardBoard();
        //activatedProductions contiene numeri da 0 a 2 che ti dicono quali produzioni di carte attivare

        Map<Resource,Integer> requiredResources = personalCardBoard.getReqResProduction(activatedProductions);
        Map<Resource,Integer> producedResources = personalCardBoard.getProductionResources(activatedProductions);
        int producedFaithPoints = personalCardBoard.getProductionFP(activatedProductions);

        if(baseProd /*the player wants to activate the base production too*/){//DA METTERE IN PERSONAL CARD BOARD??
            requiredResources = Resource.addOneResource(requiredResources,baseRes.getVal1());
            requiredResources = Resource.addOneResource(requiredResources,baseRes.getVal2());
            producedResources = Resource.addOneResource(producedResources,baseRes.getVal3());
        }
        if(leaderRes1!=null /*the player wants to activate the production of the first leader card too*/){
            requiredResources = game.getCurrentPlayer().getStatusPlayer().getLeaderCard(0).getTotalRequiredResources(requiredResources);
            producedResources = game.getCurrentPlayer().getStatusPlayer().getLeaderCard(0).getTotalProducedResources(producedResources, leaderRes1);
            producedFaithPoints = game.getCurrentPlayer().getStatusPlayer().getLeaderCard(0).getTotalProducedFP(producedFaithPoints);
        }
        if(leaderRes2!=null /*the player wants to activate the production of the second leader card too*/){
            requiredResources = game.getCurrentPlayer().getStatusPlayer().getLeaderCard(1).getTotalRequiredResources(requiredResources);
            producedResources = game.getCurrentPlayer().getStatusPlayer().getLeaderCard(1).getTotalProducedResources(producedResources, leaderRes2);
            producedFaithPoints = game.getCurrentPlayer().getStatusPlayer().getLeaderCard(1).getTotalProducedFP(producedFaithPoints);
        }

        Map<Resource,Integer> ownedResources = game.getCurrentPlayer().getStatusPlayer().getAllResources();

        if(!Resource.enoughResources(ownedResources,requiredResources)){
            game.addIllegalAction(new IllegalAction(game.getCurrentPlayer(),"CannotActivateProduction"));
            return;
            //throw new CannotActivateProductionException();
        }
        game.getCurrentPlayer().getStatusPlayer().removeResources(requiredResources);
        game.getCurrentPlayer().getStatusPlayer().addStrongboxResources(producedResources);
        for(int i=0; i<producedFaithPoints; i++)
            game.incrementFaithTrackPosition(game.getCurrentPlayer());

        game.setHasDoneAction();
        notifyAllObservers(eventCreator.createProductionEvent());
    }


    /**
     * Method buyDevelopmentCard allows the player to buy a new development card from the board.
     * You have to give the position of the card you want to buy on the board and the pile of your
     * personal card board where you want to insert the bought card.
     *
     * @param row is the row of the selected card, 0<=row<=2,
     *            row 0 is for card of level 1,..., row 2 is for card of level 3
     *
     * @param col is the column of the selected card, 0<=col<=3
     * @param pile is the number of the pile where you want to insert the bought card, 0<=pile<=2
     */
    public void buyDevelopmentCard(int row, int col, int pile) throws IllegalArgumentException{
        DevelopmentCardBoard developmentCardBoard = game.getBoard().getDevelopmentCardBoard();
        StatusPlayer statusCurrentPlayer = game.getCurrentPlayer().getStatusPlayer();

        //check if the selected pile in the board is empty
        if(developmentCardBoard.isCardPileEmpty(row,col)){
            game.addIllegalAction(new IllegalAction(game.getCurrentPlayer(),"CannotBuyCard"));
            return; //throw new IllegalArgumentException();
        }
        //check if the player has space in his personal card board, in the pile selected
        if(!statusCurrentPlayer.getPersonalCardBoard().canInsertCardOfLevel(row+1,pile)){
            game.addIllegalAction(new IllegalAction(game.getCurrentPlayer(),"CannotBuyCard"));
            return;//throw new CannotBuyCardException();
        }
        DevelopmentCard card = developmentCardBoard.getCard(row, col);

        //check if the player has the resources to buy the card
        if(!card.isBuyable(statusCurrentPlayer.getAllResources(),statusCurrentPlayer.getPlayerLeaderCards())){
            game.addIllegalAction(new IllegalAction(game.getCurrentPlayer(), "CannotBuyCard"));
            return;//throw new CannotBuyCardException();
        }
        //the player can buy the card
        developmentCardBoard.removeCard(row, col);
        statusCurrentPlayer.removeResources(card.getCost(statusCurrentPlayer.getPlayerLeaderCards()));
        try {
            statusCurrentPlayer.getPersonalCardBoard().addCard(card,pile);
        } catch (InvalidCardInsertionException e) {
            System.out.println("error"); //this shouldn't happen, because earlier the method does a check.
        }
        if(statusCurrentPlayer.getPersonalCardBoard().getNumberOfCards()>=7) //a player has bought 7 cards, so we enter the last phase of the game
            game.setLastTurnsTrue();

        game.setHasDoneAction();
        notifyAllObservers(eventCreator.createBoughtCardEvent());
    }

    /**
     * Method useMarket allows the player to buy new resources at the Market
     *
     * @param rowOrColumn is =r if the player wants to select a row, =c to select a column
     * @param index is the index of the row/column the player wants to select
     * @param newWarehouse is the new warehouse of the player after the editing and the inserting of the new resources
     * @param discardedRes is the map of the resources the player wants to discard
     * @param leaderCardSlots1 is the number of full slots the player wants to set in his first leader card
     * @param leaderCardSlots2 is the number of full slots the player wants to set in his second leader card
     */
    public void useMarket(char rowOrColumn, int index, PlayerWarehouse newWarehouse, Map<Resource,Integer> discardedRes, int leaderCardSlots1, int leaderCardSlots2){
        Player currentPlayer = game.getCurrentPlayer();
        if(!useMarketCheck(rowOrColumn, index, newWarehouse, discardedRes, leaderCardSlots1, leaderCardSlots2)){
            game.addIllegalAction(new IllegalAction(game.getCurrentPlayer(),"IllegalMarketUse"));
            //throw new IllegalMarketUseException();
            return;
        }
        if(rowOrColumn=='c'){
            fromMarblesToResources(game.getBoard().getMarket().selectColumn(index),true);
        }else{
            fromMarblesToResources(game.getBoard().getMarket().selectRow(index),true);
        }
        currentPlayer.getStatusPlayer().getPlayerWarehouse().setWarehouse(newWarehouse);
        //i parametri leadercardslots1 e 2 ti dicono quanti slot della carta leader 1 e 2 sono pieni (nella versione dell'utente)
        currentPlayer.getStatusPlayer().getLeaderCard(0).setFullSlotsNumber(leaderCardSlots1);
        currentPlayer.getStatusPlayer().getLeaderCard(1).setFullSlotsNumber(leaderCardSlots2);

        for(int i=0; i<Resource.resourcesNum(discardedRes);i++){
            game.incrementOthersFpByDiscarding();
            if(game.getPlayersNumber()==1){
                game.incrementFaithTrackPosition(game.getBoard().getLorenzo());
            }
        }
        notifyAllObservers(eventCreator.createUseMarketEvent());
        game.setHasDoneAction();
    }

    /**
     * Helper private method that checks if the operations made by the player in the process of using
     * the market are legitimate or not.
     * @return false if the operation is illegal, otherwise it returns true.
     */
    private boolean useMarketCheck(char rowOrColumn, int index, PlayerWarehouse newWarehouse, Map<Resource,Integer> discardedRes, int leaderCardSlots1, int leaderCardSlots2){
        Map<Resource,Integer> takenResources;
        Player currentPlayer = game.getCurrentPlayer();
        if((rowOrColumn!='c'&&rowOrColumn!='r')|| leaderCardSlots1>2 || leaderCardSlots2>2|| leaderCardSlots1<0||leaderCardSlots2<0){
            return false; //invalid message
        }
        if(rowOrColumn=='c'){
            takenResources = fromMarblesToResources(game.getBoard().getMarket().getColumnColors(index),false);
        }else{
            takenResources = fromMarblesToResources(game.getBoard().getMarket().getRowColors(index),false);
        }
        if(!Resource.enoughResources(takenResources,discardedRes)){
            return false; //the player has discarded resources he couldn't discard, so the action fails.
        }
        if(!newWarehouse.checkWarehouse()){
            return false; //the player has sent an invalid warehouse
        }
        Map<Resource,Integer> newAllResources = Resource.sumResourcesMap(newWarehouse.getAllResources(),currentPlayer.getStatusPlayer().getStrongboxResources());
        if(currentPlayer.getStatusPlayer().getLeaderCard(0).getFullSlotsNumber()!=null){
            for(int i=0; i<leaderCardSlots1;i++){
                newAllResources=Resource.addOneResource(newAllResources,currentPlayer.getStatusPlayer().getLeaderCard(0).getAbilityResource());
            }
        }
        if(currentPlayer.getStatusPlayer().getLeaderCard(1).getFullSlotsNumber()!=null){
            for(int i=0; i<leaderCardSlots2;i++){
                newAllResources=Resource.addOneResource(newAllResources,currentPlayer.getStatusPlayer().getLeaderCard(1).getAbilityResource());
            }
        }
        //newALLresources sarebbero tutte le risorse che avrebbe mo l'utente
        //controlResources sono le risorse che l'utente dovrebbe avere dopo l'acquisto al mercato
        takenResources = Resource.removeResourcesMap(takenResources,discardedRes);
        Map<Resource,Integer> controlResources= Resource.sumResourcesMap(takenResources,currentPlayer.getStatusPlayer().getAllResources());
        if(!controlResources.equals(newAllResources)){
            return false;
        }
        //se sono arrivato qui allora l'acquisto è lecito, allora posso effettivamente fare le modifiche
        return true;
    }

    /**
     * Method activateLeaderCard allows the player to activate one of his leader cards.
     * @param index is the position of that leader card (owned by the player)
     *
     */
    public void activateLeaderCard(int index) throws IllegalArgumentException{
        //sendToEveryone che il current player ha attivato la carta x
        LeaderCard leaderCard = game.getCurrentPlayer().getStatusPlayer().getLeaderCard(index);
        Map<Resource, Integer> playerResources = game.getCurrentPlayer().getStatusPlayer().getAllResources();
        PersonalCardBoard playerCardBoard = game.getCurrentPlayer().getStatusPlayer().getPersonalCardBoard();
        boolean canActivate = false;

        if(leaderCard.isActivated() || leaderCard.isDiscarded()) {
            game.addIllegalAction(new IllegalAction(game.getCurrentPlayer(),"IllegalLeaderAction"));
            //the card is already active, or is discarded, so you can't activate it
        }
        else {
            //check if the player has the required resources to be able to activate the Leader Card
            if (leaderCard.getRequiredResources()!=null && leaderCard.getRequiredResources().size() > 0) {
                if(Resource.enoughResources(playerResources,leaderCard.getRequiredResources())){
                    canActivate=true;
                }
            }
            //check if the player has the required cards to be able to activate the Leader Card
            else if(leaderCard.getRequiredCards().size() > 0) {
                if (leaderCard.getAbility().equals(LeaderCardType.PRODUCTION)) {
                    /*gets the first (and only) element of the cards requirements since the Card Type is PRODUCTION,
                    there will be only one card required with its associated level*/ //level 2 richiesto sempre
                    CardType requiredCardType = leaderCard.getRequiredCards().entrySet().iterator().next().getKey();
                    //int requiredLevel = leaderCard.getRequiredCards().entrySet().iterator().next().getValue();
                    canActivate = playerCardBoard.containsTypeLevel(requiredCardType,2);
                }
                else {
                    Map<CardType,Integer> playerCardsType = playerCardBoard.getCardsType();
                    canActivate= true;
                    for(CardType cardType: leaderCard.getRequiredCards().keySet()){
                        if(!playerCardsType.containsKey(cardType) || playerCardsType.get(cardType) < leaderCard.getRequiredCards().get(cardType)) {
                            canActivate = false;
                            break;
                        }
                    }
                }
            }
            if(canActivate){
                leaderCard.activate();
                //creation event to send to the clients
                notifyAllObservers(eventCreator.createLeaderActionEvent());
            }
            else{
                game.addIllegalAction(new IllegalAction(game.getCurrentPlayer(),"IllegalLeaderAction"));
            }
        }
        //notifyController(); //??
    }

    /**
     * Method discardLeaderCard allows the player to discard one of his leader cards.
     * @param index is the position of that leader card (owned by the player)
     */
    public void discardLeaderCard(int index) throws IllegalArgumentException{

        LeaderCard leaderCard = game.getCurrentPlayer().getStatusPlayer().getLeaderCard(index);
        if(leaderCard.isActivated() || leaderCard.isDiscarded()){
            //the card is already discarded, or is active, so you can't discard it
            game.addIllegalAction(new IllegalAction(game.getCurrentPlayer(),"IllegalLeaderAction"));
        }else{
            leaderCard.discard();
            game.incrementFaithTrackPosition(game.getCurrentPlayer());
            //creation event to send to the clients
            notifyAllObservers(eventCreator.createLeaderActionEvent());

        }
    }

    // STO METODO ANDREBBE MEGLIO COME STATICO IN MARBLE COLOR O RESOURCE ENUM (aggiungi parametro current player)
    //O PROPRIO IN MARKET
    /**
     * Method fromMarblesToResources transforms a list of marbles in the corresponding list of resources,
     * (the red marbles are transformed in increments of the faith track position)
     *
     * @param marbles is a list of marbles
     * @param incrementPosition must be true if you want that the player will increment his faith track position
     *                          if there is a red marble in the list, must be false if you don't want this
     *                          and you just want the resources.
     * @return a list of resources
     */
    public Map<Resource,Integer> fromMarblesToResources(List<MarbleColor> marbles, boolean incrementPosition){
        if(marbles==null){
            return null;
        }
        Map<Resource,Integer> boughtResources = new HashMap<>();
        for(MarbleColor m: marbles)
            switch (m) {
                case WHITE -> {
                    //QUA NON STO CONSIDERANDO IL CASO IN CUI CI SONO 2 CARTE LEADER WHITE MARBLE
                    //(QUANDO L'UTENTE DOVREBBE SCEGLIERE). QUEL CASO POI VEDIAMO CON LA VIEW.
                    if (game.getCurrentPlayer().getStatusPlayer().getLeaderCard(0).getWhiteMarbleResource() != null) {
                        boughtResources =Resource.addOneResource(boughtResources,game.getCurrentPlayer().getStatusPlayer().getLeaderCard(0).getWhiteMarbleResource());
                    }
                    if (game.getCurrentPlayer().getStatusPlayer().getLeaderCard(1).getWhiteMarbleResource() != null) {
                        boughtResources =Resource.addOneResource(boughtResources,game.getCurrentPlayer().getStatusPlayer().getLeaderCard(1).getWhiteMarbleResource());
                    }
                }
                case RED -> {
                    if(incrementPosition){
                        game.incrementFaithTrackPosition(game.getCurrentPlayer());
                    }
                }
                case BLUE -> boughtResources = Resource.addOneResource(boughtResources, Resource.SHIELD);
                case GREY -> boughtResources = Resource.addOneResource(boughtResources, Resource.STONE);
                case PURPLE -> boughtResources = Resource.addOneResource(boughtResources, Resource.SERVANT);
                case YELLOW -> boughtResources = Resource.addOneResource(boughtResources, Resource.COIN);
            }

        return boughtResources;
    }

    /**
     * Method incrementFaithTrackPosition is used to increment the faith track position of a
     * player you choose. If a vatican report is activated, it calls the handlers of the players.
     * It also checks if the match is ending (a player arrives in the last cell)
     *
     * @param player is the chosen player
     */
    /*
    private void incrementFaithTrackPosition(Player player){
        try{
            player.getStatusPlayer().incrementFaithTrackPosition();
            notifyAllObservers(eventCreator.createIncrementPositionEvent(player));
            player.getStatusPlayer().checkVaticanReport();
        }catch(VaticanReportException e){
            for(int i=0; i< game.getPlayersNumber(); i++){
                game.getPlayerByIndex(i).getStatusPlayer().vaticanReportHandler(e.getReportId());
            }
            notifyAllObservers(eventCreator.createVaticanReportEvent());
            if(e.getReportId()==3){
                //a player is arrived in the last cell of the track, so the game is
                //in the final phase
                game.setLastTurnsTrue();
            }
        }
    }
    */

/*
    //incrementa la faith track position di 1 per tutti i giocatori, tranne che per il current (sta scartando le risorse)
    private void incrementOthersFpByDiscarding(){
        for(int k=0; k< game.getPlayersNumber(); k++){
            if (game.getCurrentPlayerId()!=k){
                game.getPlayerByIndex(k).getStatusPlayer().incrementFaithTrackPosition();
                notifyAllObservers(eventCreator.createIncrementPositionEvent(game.getPlayerByIndex(k)));
            }
        }
        try{
            for(int k=0; k< game.getPlayersNumber(); k++){
                if (game.getCurrentPlayerId()!=k){
                    game.getPlayerByIndex(k).getStatusPlayer().checkVaticanReport();
                }
            }
        }catch (VaticanReportException e){
            for(int i=0; i< game.getPlayersNumber(); i++){
                game.getPlayerByIndex(i).getStatusPlayer().vaticanReportHandler(e.getReportId());
            }
            notifyAllObservers(eventCreator.createVaticanReportEvent());
            if(e.getReportId()==3){
                //a player is arrived in the last cell of the track, so the game is
                //in the final phase
                game.setLastTurnsTrue();
            }
        }
    }
*/

    //metto lorenzo turn in game
    /**
     * Method to manage Lorenzo's turn in the Solo mode
     * Lorenzo will act based on the SoloAction picked from the SoloAction pile
     * checks if a Pile of DevelopmentCards is empty, then Lorenzo wins
     */
    /*
    public void lorenzoTurn(){
        SoloActionType activatedSoloAction = game.getBoard().pickSoloAction().getType();

        if(game.getBoard().getDevelopmentCardBoard().isAColumnEmpty()){
            game.setLastTurnsTrue();    //lorenzo wins
            return;
        }

        if(activatedSoloAction==SoloActionType.MOVEONEANDSHUFFLE){
            incrementFaithTrackPosition(game.getBoard().getLorenzo());
            //if lorenzo posizione 24 allora win
        }

        if(activatedSoloAction==SoloActionType.MOVETWO){
            incrementFaithTrackPosition(game.getBoard().getLorenzo());
            incrementFaithTrackPosition(game.getBoard().getLorenzo());
        }

        if(game.getBoard().getDevelopmentCardBoard().isAColumnEmpty())
            game.setLastTurnsTrue();    //lorenzo wins

    }
*/
}

 /*
    //METODI EDIT E INSERT WAREHOUSE DA NON USARE, FORSE ANDRANNO NELLA VIEW
    //METODI EDIT E INSERT WAREHOUSE DOVRANNO CONSIDERARE ANCHE I DEPOSITI LEADER
    // Method editWarehouse allows the player to change the position of the resources in the warehouse
    private void editWarehouse(){//METODO DA CAMBIARE CAUSA INTERAZIONE UTENTE
        //the player says what resources in he warehouse he wants to move, so these resources
        //are temporary removed from the warehouse and stored in a list. Than the player
        //can reinsert these resources where he wants (or he can again temporary remove some resources).
        //When he wants, the player can stop the edit of the warehouse, but only if he has
        //inserted every temporary removed resource.

        PlayerWarehouse warehouse = game.getCurrentPlayer().getStatusPlayer().getPlayerWarehouse();
        List<Resource> temporaryRemovedResources = new ArrayList<>();
        int i=0,j=0,k=0;
        while(true){ //la condizione di stop sarà detta da utente
            //i valori di i, j e k devono essere detti dall'utente, interazione con la view

            if(true) //player wants to temporary remove a resource
                if(warehouse.getResource(i,j)!=null)
                    temporaryRemovedResources.add(warehouse.removeResource(i,j));


            if(true )//player wants to reinsert one of the temporary removed resources
                if(k>=0 && k< temporaryRemovedResources.size()){
                    try{
                        warehouse.insertResource(temporaryRemovedResources.get(k),i,j);
                    }catch(InvalidWarehouseInsertionException e){
                        //signal to the user, invalid insertion in the warehouse
                    }
                }

            if(true)//the player wants to end the Warehouse edit
                if(temporaryRemovedResources.size()==0){
                    break;
                }else{
                    //signal the player that he has to insert every temporary removed resource
                }

        }
    }
    // Method insertBoughtResources allows the player to insert/discard the new
    //resources bought at the market, in the warehouse
    private void insertBoughtResources(List<Resource> boughtResources){//METODO DA CAMBIARE CAUSA INTERAZIONE UTENTE

        int i=0,j=0;
        PlayerWarehouse warehouse = game.getCurrentPlayer().getStatusPlayer().getPlayerWarehouse();
        //i, j sono dati dall'utente per ogni risorsa
        for(Resource r: boughtResources){
            if(true )//the player wants to insert the resource
                try{
                    warehouse.insertResource(r,i,j);
                }catch (InvalidWarehouseInsertionException e){
                    //signal to the user, invalid inseriment in the warehouse
                }

            if(true ){  //the player wants to discard the resource
                boughtResources.remove(r);
                for(int k=0; k< game.getPlayersNumber(); k++)
                    if (game.getCurrentPlayerId()!=k)
                        incrementFaithTrackPosition(game.getPlayerByIndex(k));

            }
        }
    }
*/