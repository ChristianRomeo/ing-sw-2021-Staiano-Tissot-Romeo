package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.Events.InitialChoiceEvent;
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
    private VirtualView virtualView;    //serve?
    private final static Logger logger = Logger.getLogger(Server.class.getName());
    private final ServerEventCreator eventCreator;
    private boolean preGameStarted;

    /**
     * constructor
     */
    public Controller(Game game){
        this.game=game;
        eventCreator = new ServerEventCreator(this);
        game.setEventCreator(eventCreator);
        preGameStarted=false;
    }

    public boolean isPreGameStarted() {
        return preGameStarted;
    }

    public void setPreGameStarted(){
        preGameStarted=true;
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
     * @throws FileNotFoundException for the leaderCards
     */
    public void gameStarter() throws FileNotFoundException {
        setPreGameStarted();

        //in teoria non serve, c'è già il controllo prima di chiamarlo
        /*synchronized (this) {
            while (!gameIsReady() && isRunning()) {
                this.wait();
            }
        }*/

        game.shufflePlayers();
        logger.info("Starting the game");
        List<Player> players = game.getPlayers();
        List<LeaderCard> leaderCardList = Configs.getLeaderCards();
        Collections.shuffle(leaderCardList);

        for (Player pl : players){

            List<LeaderCard> choices = new ArrayList<>();
            pl.getStatusPlayer().addLeaderCard(leaderCardList.get(0));
            pl.getStatusPlayer().addLeaderCard(leaderCardList.get(1));
            pl.getStatusPlayer().addLeaderCard(leaderCardList.get(2));
            pl.getStatusPlayer().addLeaderCard(leaderCardList.get(3));

            choices.add(leaderCardList.remove(0));
            choices.add(leaderCardList.remove(0));
            choices.add(leaderCardList.remove(0));
            choices.add(leaderCardList.remove(0));
            logger.info("Welcoming the Client...");
            notifyAllObservers(eventCreator.createGameStarterEvent(choices,pl));
        }

        game.setCurrentPlayer(game.getPlayerByIndex(0));
        notifyAllObservers(eventCreator.createNewTurnEvent(getGame().getCurrentPlayer()));
        //game() ??
    }

    public void initialChoiceHandler(InitialChoiceEvent event){
        int indexLeader1= event.getIndexLeader1(), indexLeader2 = event.getIndexLeader2();
        Resource resource1=event.getResource1(),resource2=event.getResource2();

        if(indexLeader1<0 || indexLeader1>3 || indexLeader2<0 || indexLeader2>3 ||indexLeader1==indexLeader2){
            game.addIllegalAction(new IllegalAction(game.getCurrentPlayer(), "IllegalInitialChoice"));
            return; //scelta invalida
        }

        if(game.getCurrentPlayerId()>0){
            if(resource1==null||event.getResPosition1().getVal1()<1||event.getResPosition1().getVal2()<1|| event.getResPosition1().getVal1()>3||event.getResPosition1().getVal2()>3){
                game.addIllegalAction(new IllegalAction(game.getCurrentPlayer(), "IllegalInitialChoice"));
                return; //scelta invalida
            }
            try {
                game.getCurrentPlayer().getStatusPlayer().getPlayerWarehouse().insertResource(resource1,event.getResPosition1().getVal1(),event.getResPosition1().getVal2());
            } catch (InvalidWarehouseInsertionException e) {
                game.getCurrentPlayer().getStatusPlayer().getPlayerWarehouse().clear();
                game.addIllegalAction(new IllegalAction(game.getCurrentPlayer(), "IllegalInitialChoice"));
                return; //scelta invalida
            }
            if(game.getCurrentPlayerId()>2){
                if(resource2==null||event.getResPosition2().getVal1()<1||event.getResPosition2().getVal2()<1|| event.getResPosition2().getVal1()>3||event.getResPosition2().getVal2()>3){
                    game.addIllegalAction(new IllegalAction(game.getCurrentPlayer(), "IllegalInitialChoice"));
                    return; //scelta invalida
                }
                try {
                    game.getCurrentPlayer().getStatusPlayer().getPlayerWarehouse().insertResource(resource2,event.getResPosition2().getVal1(),event.getResPosition2().getVal2());
                } catch (InvalidWarehouseInsertionException e) {
                    game.getCurrentPlayer().getStatusPlayer().getPlayerWarehouse().clear();
                    game.addIllegalAction(new IllegalAction(game.getCurrentPlayer(), "IllegalInitialChoice"));
                    return; //scelta invalida
                }
            }
        }
        if(game.getCurrentPlayerId()>1){
            game.incrementFaithTrackPosition(game.getCurrentPlayer());
        }
        game.getCurrentPlayer().getStatusPlayer().removeTwoLeaderCards(indexLeader1,indexLeader2);

        if(game.getCurrentPlayerId()== game.getPlayersNumber()-1){
            game.setCurrentPlayer(game.getPlayerByIndex(0));
            notifyAllObservers(eventCreator.createEndPreparationEvent()); //ho finito di prendere le scelte, devo iniziare i veri turni
        }else{
            game.setCurrentPlayer(game.getPlayerByIndex(game.getCurrentPlayerId()+1));
            notifyAllObservers(eventCreator.createNewTurnEvent(getGame().getCurrentPlayer()));
        }
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

    public void notifyController() { //????
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
        //activatedProductions contiene numeri da 0 a 2 che ti dicono quali produzioni di carte attivare
        if(activatedProductions.size()==0 && !baseProd &&leaderRes1==null &&leaderRes2==null){ //this production doesn't activate anything
            game.addIllegalAction(new IllegalAction(game.getCurrentPlayer(),"CannotActivateProduction"));
            return;
        }
        PersonalCardBoard personalCardBoard = game.getCurrentPlayer().getStatusPlayer().getPersonalCardBoard();
        Map<Resource,Integer> requiredResources, producedResources;
        int producedFaithPoints;
        try{
            requiredResources = personalCardBoard.getReqResProduction(activatedProductions);
            producedResources = personalCardBoard.getProductionResources(activatedProductions);
            producedFaithPoints = personalCardBoard.getProductionFP(activatedProductions);
        }catch (IllegalArgumentException e){
            game.addIllegalAction(new IllegalAction(game.getCurrentPlayer(),"CannotActivateProduction"));
            return;
        }
        //the player wants to activate the base production too
        if(baseProd ){  //todo:DA METTERE IN PERSONAL CARD BOARD??
            requiredResources = Resource.addOneResource(requiredResources,baseRes.getVal1());
            requiredResources = Resource.addOneResource(requiredResources,baseRes.getVal2());
            producedResources = Resource.addOneResource(producedResources,baseRes.getVal3());
        }
        //the player wants to activate the production of the first leader card too
        if(leaderRes1!=null ){
            requiredResources = game.getCurrentPlayer().getStatusPlayer().getLeaderCard(0).getTotalRequiredResources(requiredResources);
            producedResources = game.getCurrentPlayer().getStatusPlayer().getLeaderCard(0).getTotalProducedResources(producedResources, leaderRes1);
            producedFaithPoints = game.getCurrentPlayer().getStatusPlayer().getLeaderCard(0).getTotalProducedFP(producedFaithPoints);
        }
        //the player wants to activate the production of the second leader card too
        if(leaderRes2!=null ){
            requiredResources = game.getCurrentPlayer().getStatusPlayer().getLeaderCard(1).getTotalRequiredResources(requiredResources);
            producedResources = game.getCurrentPlayer().getStatusPlayer().getLeaderCard(1).getTotalProducedResources(producedResources, leaderRes2);
            producedFaithPoints = game.getCurrentPlayer().getStatusPlayer().getLeaderCard(1).getTotalProducedFP(producedFaithPoints);
        }

        Map<Resource,Integer> ownedResources = game.getCurrentPlayer().getStatusPlayer().getAllResources();

        if(!Resource.enoughResources(ownedResources,requiredResources)){
            game.addIllegalAction(new IllegalAction(game.getCurrentPlayer(),"CannotActivateProduction"));
            return;
        }

        game.getCurrentPlayer().getStatusPlayer().removeResources(requiredResources);
        game.getCurrentPlayer().getStatusPlayer().addStrongboxResources(producedResources);
        for(int i=0; i<producedFaithPoints; ++i)
            game.incrementFaithTrackPosition(game.getCurrentPlayer());

        game.setHasDoneAction();
        notifyAllObservers(eventCreator.createProductionEvent());
    }


    /**
     * Method buyDevelopmentCard allows the player to buy a new development card from the board.
     * You have to give the position of the card you want to buy on the board and the pile of your
     * personal card board where you want to insert the bought card.
     *
     * @param row is the row of the selected card, 0<=row<=2: level 1, level 2,or level 3
     * @param col is the column of the selected card, 0<=col<=3, choosing the color
     * @param pile is the number of the pile where you want to insert the bought card on the board, 0<=pile<=2
     */
    public void buyDevelopmentCard(int row, int col, int pile) throws IllegalArgumentException{
        DevelopmentCardBoard developmentCardBoard = game.getBoard().getDevelopmentCardBoard();
        StatusPlayer statusCurrentPlayer = game.getCurrentPlayer().getStatusPlayer();

        //check if the selected pile in the board is empty
        if(developmentCardBoard.isCardPileEmpty(row,col)){
            game.addIllegalAction(new IllegalAction(game.getCurrentPlayer(),"CannotBuyCard"));
            return;
        }

        //check if the player has space in his personal card board, in the pile selected
        if(!statusCurrentPlayer.getPersonalCardBoard().canInsertCardOfLevel(row+1,pile)){
            game.addIllegalAction(new IllegalAction(game.getCurrentPlayer(),"CannotBuyCard"));
            return;
        }
        DevelopmentCard card = developmentCardBoard.getCard(row, col);

        //check if the player has the resources to buy the card
        if(!card.isBuyable(statusCurrentPlayer.getAllResources(),statusCurrentPlayer.getPlayerLeaderCards())){
            game.addIllegalAction(new IllegalAction(game.getCurrentPlayer(), "CannotBuyCard"));
            return;
        }

        //the player can buy the card
        developmentCardBoard.removeCard(row, col);
        statusCurrentPlayer.removeResources(card.getCost(statusCurrentPlayer.getPlayerLeaderCards()));
        try {
            statusCurrentPlayer.getPersonalCardBoard().addCard(card,pile);
        } catch (InvalidCardInsertionException e) {
            logger.warning("error adding a card"); //todo: remove, this shouldn't happen, because earlier the method does a check.
        }

        //a player has bought 7 cards, so we enter the last phase of the game
        if(statusCurrentPlayer.getPersonalCardBoard().getNumberOfCards()>=7)
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
    public void useMarket(char rowOrColumn, int index, PlayerWarehouse newWarehouse, Map<Resource,Integer> discardedRes, int leaderCardSlots1, int leaderCardSlots2, List<Integer> whiteMarbleChoices){
        Player currentPlayer = game.getCurrentPlayer();
        if(!useMarketCheck(rowOrColumn, index, newWarehouse, discardedRes, leaderCardSlots1, leaderCardSlots2, whiteMarbleChoices)){
            game.addIllegalAction(new IllegalAction(currentPlayer,"IllegalMarketUse"));
            return;
        }

        if(rowOrColumn=='c')
            fromMarblesToResources(game.getBoard().getMarket().selectColumn(index),true, whiteMarbleChoices);
        else
            fromMarblesToResources(game.getBoard().getMarket().selectRow(index),true, whiteMarbleChoices);


        currentPlayer.getStatusPlayer().getPlayerWarehouse().setWarehouse(newWarehouse);
        //i parametri leadercardslots1 e 2 ti dicono quanti slot della carta leader 1 e 2 sono pieni (nella versione dell'utente)
        currentPlayer.getStatusPlayer().getLeaderCard(0).setFullSlotsNumber(leaderCardSlots1);
        currentPlayer.getStatusPlayer().getLeaderCard(1).setFullSlotsNumber(leaderCardSlots2);

        for(int i=0; i<Resource.resourcesNum(discardedRes);++i){
            game.incrementOthersFpByDiscarding();
            if(game.getPlayersNumber()==1)
                game.incrementFaithTrackPosition(game.getBoard().getLorenzo());
        }

        notifyAllObservers(eventCreator.createUseMarketEvent());
        game.setHasDoneAction();
    }

    /**
     * Helper that validate whether the player could or couldn't do the operation made during the usage of the market
     * @return false if the operation is illegal, otherwise it returns true.
     */
    private boolean useMarketCheck(char rowOrColumn, int index, PlayerWarehouse newWarehouse, Map<Resource,Integer> discardedRes, int leaderCardSlots1, int leaderCardSlots2, List<Integer> whiteMarbleChoices){
        Map<Resource,Integer> takenResources;
        Player currentPlayer = game.getCurrentPlayer();

        if((rowOrColumn!='c'&& rowOrColumn!='r')|| leaderCardSlots1>2 || leaderCardSlots2>2|| leaderCardSlots1<0||leaderCardSlots2<0)
            return false; //invalid message

        if(rowOrColumn=='c')
            takenResources = fromMarblesToResources(game.getBoard().getMarket().getColumnColors(index),false, whiteMarbleChoices);
        else
            takenResources = fromMarblesToResources(game.getBoard().getMarket().getRowColors(index),false, whiteMarbleChoices);

        //the player has discarded resources he couldn't discard, so the action fails.
        if(!Resource.enoughResources(takenResources,discardedRes))
            return false;

        if(!newWarehouse.checkWarehouse())
            return false; //the player has sent an invalid warehouse

        Map<Resource,Integer> newAllResources = Resource.sumResourcesMap(newWarehouse.getAllResources(),currentPlayer.getStatusPlayer().getStrongboxResources());

        if(currentPlayer.getStatusPlayer().getLeaderCard(0).getFullSlotsNumber()!=null)
            for(int i=0; i<leaderCardSlots1;++i)
                newAllResources=Resource.addOneResource(newAllResources,currentPlayer.getStatusPlayer().getLeaderCard(0).getAbilityResource());


        //newAllResources sarebbero tutte le risorse che avrebbe mo l'utente
        if(currentPlayer.getStatusPlayer().getLeaderCard(1).getFullSlotsNumber()!=null)
            for(int i=0; i<leaderCardSlots2;i++)
                newAllResources=Resource.addOneResource(newAllResources,currentPlayer.getStatusPlayer().getLeaderCard(1).getAbilityResource());


        takenResources = Resource.removeResourcesMap(takenResources,discardedRes);
        //controlResources sono le risorse che l'utente dovrebbe avere dopo l'acquisto al mercato
        Map<Resource,Integer> controlResources= Resource.sumResourcesMap(takenResources,currentPlayer.getStatusPlayer().getAllResources());

        //l'acquisto è lecito se le 2 mappe corrispondono, allora posso effettivamente fare le modifiche
        return controlResources.equals(newAllResources);
    }

    /**
     * Method activateLeaderCard allows the player to activate one of his leader cards.
     * @param index is the position of that leader card (owned by the player)
     * @throws IllegalArgumentException when bad argument is passed
     */
    public void activateLeaderCard(int index) throws IllegalArgumentException{
        //sendToEveryone che il current player ha attivato la carta x
        LeaderCard leaderCard = game.getCurrentPlayer().getStatusPlayer().getLeaderCard(index);
        Map<Resource, Integer> playerResources = game.getCurrentPlayer().getStatusPlayer().getAllResources();
        PersonalCardBoard playerCardBoard = game.getCurrentPlayer().getStatusPlayer().getPersonalCardBoard();
        boolean canActivate = false;

        if(leaderCard.isActivated() || leaderCard.isDiscarded()) //the card is already active, or is discarded, so you can't activate it
            game.addIllegalAction(new IllegalAction(game.getCurrentPlayer(),"IllegalLeaderAction"));

        else
        {
            //check if the player has the required resources to be able to activate the Leader Card
            if (leaderCard.getRequiredResources()!=null && leaderCard.getRequiredResources().size() > 0) {
                if(Resource.enoughResources(playerResources,leaderCard.getRequiredResources()))
                    canActivate=true;
            }
            //check if the player has the required cards to be able to activate the Leader Card
            else
                if(leaderCard.getRequiredCards().size() > 0) {
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
                notifyAllObservers(eventCreator.createLeaderActionEvent()); //creation event to send to the clients
            }
            else
                game.addIllegalAction(new IllegalAction(game.getCurrentPlayer(),"IllegalLeaderAction"));

        }
        //notifyController(); //??
    }

    /**
     * Method discardLeaderCard allows the player to discard one of his leader cards.
     * @param index is the position of that leader card (owned by the player)
     * @throws IllegalArgumentException when a bad argument is passed
     */
    public void discardLeaderCard(int index) throws IllegalArgumentException{

        LeaderCard leaderCard = game.getCurrentPlayer().getStatusPlayer().getLeaderCard(index);
        if(leaderCard.isActivated() || leaderCard.isDiscarded())//the card is already discarded, or is active, so you can't discard it
            game.addIllegalAction(new IllegalAction(game.getCurrentPlayer(),"IllegalLeaderAction"));
        else{
            leaderCard.discard();
            game.incrementFaithTrackPosition(game.getCurrentPlayer());
            //creation event to send to the clients
            notifyAllObservers(eventCreator.createLeaderActionEvent());
        }
    }

    // STO METODO ANDREBBE MEGLIO COME STATICO IN MARBLE COLOR O RESOURCE ENUM (aggiungi parametro current player) O PROPRIO IN MARKET
    /**
     * Method fromMarblesToResources transforms a list of marbles in the corresponding list of resources,
     * (the red marbles are transformed in increments of the faith track position)
     *
     * @param marbles is a list of marbles
     * @param incrementPosition true when you want also the player faith track position incremented if it should
     *                          false when you just want the resources and not the position changed.
     * @return a list of resources
     */
    public Map<Resource,Integer> fromMarblesToResources(List<MarbleColor> marbles, boolean incrementPosition, List<Integer> whiteMarbleChoices){

        if(marbles==null)
            return null;

        List<Integer> whiteMarbleChoices1= null;
        if(whiteMarbleChoices!=null){
            whiteMarbleChoices1 = new ArrayList<>(whiteMarbleChoices);
        }

        List<LeaderCard> leaderCards = game.getCurrentPlayer().getStatusPlayer().getPlayerLeaderCards();

        Map<Resource,Integer> boughtResources = new HashMap<>();
        for(MarbleColor m: marbles)
            switch (m) {
                case WHITE -> { //todo: la parte isActivated credo è inutile, sta già controllo nella carta
                    if (leaderCards.get(0).isActivated() && leaderCards.get(0).getWhiteMarbleResource() != null){
                        if(leaderCards.get(1).isActivated() && leaderCards.get(1).getWhiteMarbleResource() != null){
                            boughtResources =Resource.addOneResource(boughtResources,leaderCards.get(whiteMarbleChoices1.remove(0)).getWhiteMarbleResource());
                        }else{
                            boughtResources =Resource.addOneResource(boughtResources,leaderCards.get(0).getWhiteMarbleResource());
                        }
                    }else{
                        if(leaderCards.get(1).isActivated() && leaderCards.get(1).getWhiteMarbleResource() != null){
                            boughtResources =Resource.addOneResource(boughtResources,leaderCards.get(1).getWhiteMarbleResource());
                        }
                    }
                }
                case RED -> {
                    if(incrementPosition)
                        game.incrementFaithTrackPosition(game.getCurrentPlayer());
                }
                case BLUE -> boughtResources = Resource.addOneResource(boughtResources, Resource.SHIELD);
                case GREY -> boughtResources = Resource.addOneResource(boughtResources, Resource.STONE);
                case PURPLE -> boughtResources = Resource.addOneResource(boughtResources, Resource.SERVANT);
                case YELLOW -> boughtResources = Resource.addOneResource(boughtResources, Resource.COIN);
            }

        return boughtResources;
    }

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