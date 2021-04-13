package it.polimi.ingsw.controller;

import com.google.gson.*;
import it.polimi.ingsw.controller.controllerExceptions.DisconnectionException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelExceptions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.logging.Logger;


/**
 * Controller, also
 * @see VirtualView
 */
public class Controller {
    private final Game game;
    private VirtualView virtualView;
    private Player currentPlayer;
    private final static Logger logger = Logger.getLogger(Server.class.getName());
    private List<LeaderCard> leaderCardList = new ArrayList<>();


    public Controller(Game game){
        this.game=game;
    }

    public void setVirtualView(VirtualView virtualView) {
        this.virtualView = virtualView;
    }

    public boolean gameIsReady() {
        return (game != null) && game.gameReady();
    }

    public void gameStarter() throws InterruptedException, DisconnectionException {
        synchronized (this) {
            while (!gameIsReady() && isRunning()) {
                this.wait();
            }
        }
        logger.info("Starting the game");
        List<Player> players = game.getPlayers();

        //for all players
        //virtualview.getclienthandler.getnickname.send setLeaderCardChoice ; leaderCardList - 4 ; logger.info(waiting cards) ;
        /* synchronized (this) {
            while (!(areCardsChosen()) && isRunning()) {
                this.wait();
            }
        } */
        //logger.info(card chosen) ;

        //set player 2, 3 , 4 theirs resources

        game();
    }

    private void game() throws InterruptedException, DisconnectionException {
        while (!game.hasWinner()) {

            //if currentplayer turn hasEnded
            //currentplayer = nextTurnPlayer
            //else
            //scelta mossa giocatore; synch this ; wait;
            //sendToEveryone mossa
        }
             manageWin();

    }

    private void manageWin() {

        logger.info("Game ended: " + currentPlayer.getNickname() + " has won");
        for (Player p : game.getPlayers()) {
            //virtualView.getClientHandlerByNickname sendTo winner EndMessage
        }
    }

    private void manageLose(){
        //che succede ai giocatori che perdono?
        //removePlayer(currentPlayer);
    }
    public void setNewNickname(){
        //bisogna capire come fare
    }

    public void actionDispatch(){
        //riceve l'azione dall'utente e chiama il metodo adeguato
        notifyController();
    }

    public void setDisconnected(String nickname) {
        if (game.getPlayerByNickname(nickname) != null && game.hasWinner()) {
            game.getPlayerByNickname(nickname).setConnected(false);
            return;
        }

        //virtualView.sendToEveryone disconnection nickname
        virtualView.closeAll();
        game.setActive(true);
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
    public void activateProduction(List<Integer> activatedProductions, boolean baseProd, SameTypeTriple<Resource> baseRes, Resource leaderRes1, Resource leaderRes2) throws CannotActivateProductionException,IllegalArgumentException{
        PersonalCardBoard personalCardBoard = game.getCurrentPlayer().getStatusPlayer().getPersonalCardBoard();
        //l'utente mi dice quali produzioni vuole attivare tramite la view
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

        if(!Resource.enoughResources(ownedResources,requiredResources))
            throw new CannotActivateProductionException();

        game.getCurrentPlayer().getStatusPlayer().removeResources(requiredResources);
        game.getCurrentPlayer().getStatusPlayer().addStrongboxResources(producedResources);
        for(int i=0; i<producedFaithPoints; i++)
            incrementFaithTrackPosition(game.getCurrentPlayer());
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
    public void buyDevelopmentCard(int row, int col, int pile) throws CannotBuyCardException,IllegalArgumentException, InvalidCardInsertionException{
        DevelopmentCardBoard developmentCardBoard = game.getBoard().getDevelopmentCardBoard();
        StatusPlayer statusCurrentPlayer = game.getCurrentPlayer().getStatusPlayer();

        //check if the selected pile in the board is empty
        if(developmentCardBoard.isCardPileEmpty(row,col))
            throw new IllegalArgumentException();

        //check if the player has space in his personal card board, in the pile selected
        if(!statusCurrentPlayer.getPersonalCardBoard().canInsertCardOfLevel(row+1,pile))
            throw new CannotBuyCardException();

        DevelopmentCard card = developmentCardBoard.getCard(row, col);

        //check if the player has the resources to buy the card
        if(!card.isBuyable(statusCurrentPlayer.getAllResources(),statusCurrentPlayer.getPlayerLeaderCards()))
            throw new CannotBuyCardException();

        //the player can buy the card
        developmentCardBoard.removeCard(row, col);
        statusCurrentPlayer.removeResources(card.getCost(statusCurrentPlayer.getPlayerLeaderCards()));

        statusCurrentPlayer.getPersonalCardBoard().addCard(card,pile);
        if(statusCurrentPlayer.getPersonalCardBoard().getNumberOfCards()>=7){
            //a player has bought 7 cards, so we enter the last phase of the game
            game.setLastTurnsTrue();
        }
    }


    /**
     * Method useMarket allows the player to buy new resources at the Market
     *
     * @param rowOrColumn is =r if the player wants to select a row, =c to select a column
     * @param index is the index of the row/column the player wants to select
     */
    public void useMarket(char rowOrColumn, int index){ //METODO DA CAMBIARE CAUSA INTERAZIONE UTENTE
        Market market = game.getBoard().getMarket();

        List<MarbleColor> takenMarbles;
        List<Resource> boughtResources;
        if(rowOrColumn=='r')
            takenMarbles=market.selectRow(index);
        else
            takenMarbles=market.selectColumn(index);

        boughtResources=fromMarblesToResources(takenMarbles);

        /*the player can change the position of the resources in the warehouse
            before the insertion of the new resources*/
        editWarehouse();
        //the player insert/discard the resources bought at the market
        insertBoughtResources(boughtResources);
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
            if(canActivate)
                leaderCard.activate();
            //else
                //not enough resources/cards to be able to activate the Leader Card
        }
        notifyController();
    }

    /**
     * Method discardLeaderCard allows the player to discard one of his leader cards.
     * @param index is the position of that leader card (owned by the player)
     */
    public void discardLeaderCard(int index) throws IllegalArgumentException{

        LeaderCard leaderCard = game.getCurrentPlayer().getStatusPlayer().getLeaderCard(index);
        if(leaderCard.isActivated() || leaderCard.isDiscarded()){
            //the card is already discarded, or is active, so you can't discard it
        }else{
            leaderCard.discard();
            incrementFaithTrackPosition(game.getCurrentPlayer());
        }
    }

    //METODI EDIT E INSERT WAREHOUSE DOVRANNO CONSIDERARE ANCHE I DEPOSITI LEADER
    /**
     * Method editWarehouse allows the player to change the position of the resources in the warehouse
     */
    //NB: this method can't work now, because it needs the view
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

            if(true /*player wants to temporary remove a resource*/)
                if(warehouse.getResource(i,j)!=null)
                    temporaryRemovedResources.add(warehouse.removeResource(i,j));


            if(true /*player wants to reinsert one of the temporary removed resources*/)
                if(k>=0 && k< temporaryRemovedResources.size()){
                    try{
                        warehouse.insertResource(temporaryRemovedResources.get(k),i,j);
                    }catch(InvalidWarehouseInsertionException e){
                        /*signal to the user, invalid insertion in the warehouse*/
                    }
                }

            if(true/* the player wants to end the Warehouse edit*/)
                if(temporaryRemovedResources.size()==0){
                    break;
                }else{
                    /*signal the player that he has to insert every temporary removed resource*/
                }

        }
    }

    /**
     * Method insertBoughtResources allows the player to insert/discard the new
     * resources bought at the market, in the warehouse
     */
    //NB: this method can't work now, because it needs the view
    private void insertBoughtResources(List<Resource> boughtResources){//METODO DA CAMBIARE CAUSA INTERAZIONE UTENTE

        int i=0,j=0;
        PlayerWarehouse warehouse = game.getCurrentPlayer().getStatusPlayer().getPlayerWarehouse();
        //i, j sono dati dall'utente per ogni risorsa
        for(Resource r: boughtResources){
            if(true /*the player wants to insert the resource*/)
                try{
                    warehouse.insertResource(r,i,j);
                }catch (InvalidWarehouseInsertionException e){
                    /*signal to the user, invalid inseriment in the warehouse*/
                }

            if(true /*the player wants to discard the resource*/){
                boughtResources.remove(r);
                for(int k=0; k< game.getPlayersNumber(); k++)
                    if (game.getCurrentPlayerId()!=k)
                        incrementFaithTrackPosition(game.getPlayerByIndex(k));

            }
        }
    }

    // STO METODO ANDREBBE MEGLIO COME STATICO IN MARBLE COLOR O RESOURCE ENUM (aggiungi parametro current player)
    /**
     * Method fromMarblesToResources transforms a list of marbles in the corresponding list of resources,
     * (the red marbles are transformed in increments of the faith track position)
     *
     * @param marbles is a list of marbles
     * @return a list of resources
     */
    private List<Resource> fromMarblesToResources(List<MarbleColor> marbles){

        List<Resource> boughtResources = new ArrayList<>();
        for(MarbleColor m: marbles)
            switch (m) {
                case WHITE -> {
                    //QUA NON STO CONSIDERANDO IL CASO IN CUI CI SONO 2 CARTE LEADER WHITE MARBLE
                    //(QUANDO L'UTENTE DOVREBBE SCEGLIERE). QUEL CASO POI VEDIAMO CON LA VIEW.
                    if (game.getCurrentPlayer().getStatusPlayer().getLeaderCard(0).getWhiteMarbleResource() != null) {
                        boughtResources.add(game.getCurrentPlayer().getStatusPlayer().getLeaderCard(0).getWhiteMarbleResource());
                    }
                    if (game.getCurrentPlayer().getStatusPlayer().getLeaderCard(1).getWhiteMarbleResource() != null) {
                        boughtResources.add(game.getCurrentPlayer().getStatusPlayer().getLeaderCard(1).getWhiteMarbleResource());
                    }
                }
                case RED -> incrementFaithTrackPosition(game.getCurrentPlayer());
                case BLUE -> boughtResources.add(Resource.SHIELD);
                case GREY -> boughtResources.add(Resource.STONE);
                case PURPLE -> boughtResources.add(Resource.SERVANT);
                case YELLOW -> boughtResources.add(Resource.COIN);
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
    private void incrementFaithTrackPosition(Player player){
        try{
            player.getStatusPlayer().incrementFaithTrackPosition();
        }catch(VaticanReportException e){
            for(int i=0; i< game.getPlayersNumber(); i++){
                game.getPlayerByIndex(i).getStatusPlayer().vaticanReportHandler(e.getReportId());
                if(e.getReportId()==3){
                    //a player is arrived in the last cell of the track, so the game is
                    //in the final phase
                    game.setLastTurnsTrue();
                }
            }
        }
    }

    /**
     * Reader LeaderCards
     */
    private static final String LEADERPATH = "src/main/resources/Leaders.json";
    public void leaderCardReader() throws FileNotFoundException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(LEADERPATH));
        Gson gson = new Gson();
        JsonArray json = gson.fromJson(bufferedReader, JsonArray.class);

        for (int i = 0; i < json.size(); ++i)
            switch (gson.fromJson(json.get(i), SonOfLeaderCard.class).getAbility()) {
                case DISCOUNT ->
                    leaderCardList.add(gson.fromJson(json.get(i), LeaderCardDiscount.class));

                case SLOTS ->
                    leaderCardList.add(gson.fromJson(json.get(i), LeaderCardSlots.class));

                case PRODUCTION ->
                    leaderCardList.add(gson.fromJson(json.get(i), LeaderCardProduction.class));

                case WHITEMARBLE ->
                    leaderCardList.add(gson.fromJson(json.get(i), LeaderCardWhiteMarble.class));
        }

        //leaderCardList.forEach(System.out::println);

        Collections.shuffle(leaderCardList);

        //leaderCardList.forEach(System.out::println);

    }

}
