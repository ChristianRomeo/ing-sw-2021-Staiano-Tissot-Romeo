package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.View;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelExceptions.InvalidWarehouseInsertionException;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;


public class CliView implements View {

    private final Scanner scanner;
    private ServerHandler serverHandler;
    private final static Logger logger = Logger.getLogger(CliView.class.getName());
    private ActionHandler actionHandler;
    private final ClientModel clientModel;


    /**
     * Constructor
     */
    public CliView() {
        int cookie=1; //todo DOBBIAMO FARLO ARRIVARE DAL SERVER PER POTER RICONNETTERE IL PLAYER
        this.scanner = new Scanner(System.in);
        clientModel = new ClientModel(cookie);
    }

    public ClientModel getClientModel() {
        return clientModel;
    }

    @Override
    public void setConnectionHandler(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    @Override
    public void launcher() {
        actionHandler = new ActionHandler(clientModel,this, serverHandler);
        showMessage(Styler.format('i', Styler.ANSI_TALK + " Welcome!\nPlease wait, You will join the first available game..."),false);

        clientModel.setMyNickname(askNickname()); //chiedo e imposto il nickname
        serverHandler.setUpConnection();
        //poi credo qua devo far partire metodo che chiede cose a utente in continuazione:
        askActions();
    }

    @Override
    public void askActions(){
        while(true){ //poi ci sarà qualche altra condizione
            scanner.reset();
            String newAction = scanner.nextLine();
            //qui ora si deve passare questa stringa newAction a chi vede se è una azione valida o no
            //e nel caso si chiama il metodo corrispondente (che potrà ulteriormente parlare all'utente
            //e chiedergli cose):
            actionHandler.handleAction(newAction);
        }
    }

    /**
     * Shows a message to the user
     *
     * @param message   The message to be shown
     * @param cls       True if wants to clean the console
     */
    public synchronized void showMessage(String message,boolean cls){
        if (cls)
            Styler.cls();
        System.out.println(message);
    }


    // ------ ASK METHODS -----

    @Override
    public String askNickname(){

        showMessage("Inserisci nickname: ", true);
        String nickname = scanner.nextLine();
        while (!checkNickname(nickname)){
            showErrorMessage("Invalid choice! Try again: ");
            nickname = scanner.nextLine();
        }
        showMessage(Styler.format('i', Styler.ANSI_WAIT + " The game will start shortly, brace yourself!"),false);
        return nickname;
    }

    @Override
    public int askNumPlayer(){

        showMessage("Inserisci numero giocatori: ",true);
        String numPlayer = scanner.nextLine();
        while (checkNumber(numPlayer,1,4)==null){
            showErrorMessage("Invalid choice! Try again: ");
            numPlayer = scanner.nextLine();
        }
        return checkNumber(numPlayer,1,4);
    }

    /**
     * Asks the game cards in the initial choice
     * @return the set of the indexes of the chosen cards
     */
    @Override
    public TreeSet<Integer> askChoiceLeaderCards() {

        showMessage(Styler.ANSI_TALK + Styler.format('b', "Choose 2 cards :"),false);
        List<LeaderCard> leaderCard = clientModel.getPlayerLeaderCards(clientModel.getCurrentPlayerNick());
        showMessage(" ↳: ",false);
        leaderCard.forEach(this::showLeaderCard);

        String chosenCards = scanner.nextLine();
        while (checkLeaderCardNum(chosenCards)==null){
            showErrorMessage("Invalid choice! Try again: ");
            chosenCards = scanner.nextLine();
        }

        return checkLeaderCardNum(chosenCards);
    }

    /**
     * This method asks to the user an index (0/1) of a leader card.
     * And then what card
     * @return [0]=the action [1]=the card index.
     */
    @Override
    public List<Integer> askLeaderCard(){

        List<LeaderCard> leaderCard = clientModel.getPlayerLeaderCards(clientModel.getCurrentPlayerNick());
        showMessage(" ↳: ",false);
        leaderCard.forEach(this::showLeaderCard);

        showMessage(Styler.ANSI_TALK + Styler.format('b', "Choose activation/discard card [0/1]:"),false);
        String string =scanner.nextLine();
        while (checkNumber(string,0,1)==null) {
            showErrorMessage("Invalid choice! Try again: ");
            string =scanner.nextLine();
        }

        showMessage(Styler.ANSI_TALK + Styler.format('b', "Choose what card:"),false);
        String chosenCard = scanner.nextLine();
        while (checkNumber(chosenCard,0,1)==null){
            showErrorMessage("Invalid choice! Try again: ");
            chosenCard = scanner.nextLine();
        }
        List<Integer> ret = new ArrayList<>();
        ret.add(checkNumber(string,0,1));
        ret.add(checkNumber(chosenCard,0,1));

        return ret;
    }

    /**
     * This method asks the user a position of a development card in the development card board
     * @return the position (row,col)
     */
    @Override
    public SameTypePair<Integer> askDevelopmentCard(){
        showDevelopmentCardBoard();
        SameTypePair<Integer> position = new SameTypePair<>();
        showMessage("Inserisci la riga della carta che vuoi selezionare: ", false);
        String string = scanner.nextLine();
        while(checkNumber(string,0,2)==null){
            showErrorMessage("Invalid choice! Try again: ");
            string = scanner.nextLine();
        }
        position.setVal1(checkNumber(string,0,2));
        showMessage("Inserisci la colonna della carta che vuoi selezionare: ", false);
        string = scanner.nextLine();
        while(checkNumber(string,0,3)==null){
            showErrorMessage("Invalid choice! Try again: ");
            string = scanner.nextLine();
        }
        position.setVal2(checkNumber(string,0,3));
        return position;
    }

    /**
     * asks in what pile of production should the bought card be inserted
     * @return the pile number
     */
    @Override
    public int askCardPile(){
        showMessage("This is your card board: ", false);
        showPersonalCardBoard(clientModel.getPlayersCardBoards().get(clientModel.getMyIndex()));
        showMessage("Chose the pile where you want to insert your card (0 - 2): ", false);

        String string = scanner.nextLine();
        while(checkNumber(string,0,2)==null){
            showErrorMessage("Invalid choice! Try again: ");
            string = scanner.nextLine();
        }
        return checkNumber(string,0,2);
    }

    /**
     * this method asks the user what cards production he wants to activate. (not base production or leader cards productions)
     * @return the list of the indexes of the card he wants to activate.
     */
    public List<Integer> askCardProductions(){
        List<Integer> positions = new ArrayList<>();
        String choice;

        showMessage("This is your card board: ", false);
        showPersonalCardBoard(clientModel.getPlayersCardBoards().get(clientModel.getMyIndex()));

        for(int i=1; i<=3; i++){
            showMessage("Do you want to activate the production of the card in position "+i+ " ? y/n", false);
            choice = scanner.nextLine();
            while(!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n")){
                showErrorMessage("Invalid choice! Try again: ");
                choice = scanner.nextLine();
            }

            if(choice.equalsIgnoreCase("y"))
                positions.add(i-1);
        }

        return  positions;
    }

    /**
     * Asks if the user wants to activate the base production and how.
     * @return null if the base production is not activated, otherwise the resources for the production.
     */
    public SameTypeTriple<Resource> askBaseProduction(){
        SameTypeTriple<Resource> baseProductionResources = new SameTypeTriple<>();
        showMessage("Do you want to activate the base production? y/n",false);
        String choice = scanner.nextLine();
        while(!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n")){
            showErrorMessage("Invalid choice! Try again: ");
            choice = scanner.nextLine();
        }
        if(choice.equalsIgnoreCase("n"))
            return null;

        showMessage("You have to choose two resources you want to use for the production: ",false);
        baseProductionResources.setVal1(askResource());
        baseProductionResources.setVal2(askResource());
        showMessage("You have to choose a resource you want to product: ",false);
        baseProductionResources.setVal3(askResource());

        return baseProductionResources;
    }

    /**
     * Asks if the user wants to activate the leader card productions and how (if he has the cards)
     * @return the resources used in the productions.
     */
    public SameTypePair<Resource> askLeaderProductions(){
        List<LeaderCard> leaderCards = clientModel.getPlayerLeaderCards(clientModel.getMyNickname());

        SameTypePair<Resource> chosenResources = new SameTypePair<>(null,null);
        String choice;
        for(int i=0; i<2; i++){
            if(leaderCards.get(i).isActivated() &&(leaderCards.get(i) instanceof LeaderCardProduction)){
                showLeaderCard(leaderCards.get(i));
                showMessage("Do you want to activate this card production? y/n",false);
                choice = scanner.nextLine();
                while(!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n")){
                    showErrorMessage("Invalid choice! Try again: ");
                    choice = scanner.nextLine();
                }
                if(choice.equalsIgnoreCase("y")){
                    showMessage("You have to choose a resource you want to product: ",false);
                    chosenResources.set(askResource(),i);
                }
            }
        }
        return chosenResources;
    }

    /**
     * asks to the user a resource type.
     * @return the resource chosen.
     */
    public Resource askResource(){
        showMessage("Choose a resource (coin, shield, stone, servant): ",false);
        String choice = scanner.nextLine();
        while(!choice.equalsIgnoreCase("coin")&& !choice.equalsIgnoreCase("shield")&&!choice.equalsIgnoreCase("stone") &&!choice.equalsIgnoreCase("servant")){
            showErrorMessage("Invalid choice! Try again: ");
            choice = scanner.nextLine();
        }
        return Resource.valueOf(choice.toUpperCase());
    }

    public Pair<Character,Integer> askMarketUse(){
        char rowOrColumn;
        int index;

        showMarket();
        showMessage("Do you want to select a row or a column? r/c",false);
        String choice = scanner.nextLine();
        while(!choice.equalsIgnoreCase("r") && !choice.equalsIgnoreCase("c")){
            showErrorMessage("Invalid choice! Try again: ");
            choice = scanner.nextLine();
        }
        rowOrColumn = choice.charAt(0);

        if(rowOrColumn=='r'){
            showMessage("What row do you want to select? (0,1,2)",false);
            choice = scanner.nextLine();
            while(checkNumber(choice,0,2)==null){
                showErrorMessage("Invalid choice! Try again: ");
                choice = scanner.nextLine();
            }
            index = checkNumber(choice,0,2);
        }else{
            showMessage("What column do you want to select? (0,1,2,3)",false);
            choice = scanner.nextLine();
            while(checkNumber(choice,0,3)==null){
                showErrorMessage("Invalid choice! Try again: ");
                choice = scanner.nextLine();
            }
            index = checkNumber(choice,0,3);
        }
        return new Pair<>(rowOrColumn,index);
    }

    /**
     * Asks the user, who has 2 white marble leader cards active, which one he wants to use for a white marble.
     * @return the index of the chosen card
     */
    public int askWhiteMarbleChoice(){
        showLeaderCard(clientModel.getPlayerLeaderCards(clientModel.getMyNickname()).get(0));
        showLeaderCard(clientModel.getPlayerLeaderCards(clientModel.getMyNickname()).get(1));

        showMessage("You have two white marble leader cards active, which one do you want to use for this white marble? 0/1",false);
        String choice = scanner.nextLine();
        while(checkNumber(choice,0,1)==null){
            showErrorMessage("Invalid choice! Try again: ");
            choice = scanner.nextLine();
        }
        return checkNumber(choice,0,1);
    }

    /**
     * Asks the user how he wants to edit his warehouse.
     * It edit the warehouse passed and the number of full slots of the two leader cards (if the user has the right type of cards).
     */
    public void editWarehouse(PlayerWarehouse warehouse, SameTypePair<Integer> fullLeaderSlots) {
        //the player says what resources in he warehouse he wants to move, so these resources
        //are temporary removed from the warehouse and stored in a list. Than the player
        //can reinsert these resources where he wants (or he can again temporary remove some resources).
        //When he wants, the player can stop the edit of the warehouse, but only if he has
        //inserted every temporary removed resource.

        Resource leaderCardResource1 = clientModel.getPlayerLeaderCards(clientModel.getMyNickname()).get(0).getAbilityResource();
        Resource leaderCardResource2 = clientModel.getPlayerLeaderCards(clientModel.getMyNickname()).get(1).getAbilityResource();
        List<Resource> temporaryRemovedResources = new ArrayList<>();
        int resourceIndex;
        int choiceNumber;
        SameTypePair<Integer> selectedCell;

        while (true) {
            showMessage("This is your warehouse: ", false);
            showWarehouse(warehouse);
            showMessage("You can temporary remove a resource (0), re-insert a removed resource (1), exit (2).  ",false);
            if(fullLeaderSlots.getVal1()!=null){
                System.out.println("This is your Slot Leader Card:  type: " + leaderCardResource1 + " number of full slots: " + fullLeaderSlots.getVal1());
                showMessage("You can also temporary remove/re-insert a resource from/in your leader card of type "+ leaderCardResource1 +  " (3/4). ",false);
            }
            if(fullLeaderSlots.getVal2()!=null){
                showMessage("This is your Slot Leader Card:  type: " + leaderCardResource2 + " number of full slots: " + fullLeaderSlots.getVal2(),false);
                System.out.println("You can also temporary remove/re-insert a resource from/in your leader card of type "+ leaderCardResource2 +  " (5/6). ");
            }
            choiceNumber = askNumber(0,6);
            if (choiceNumber==0){
                selectedCell = askWarehouseCell();
                if (warehouse.getResource(selectedCell.getVal1(), selectedCell.getVal2()) != null)
                    temporaryRemovedResources.add(warehouse.removeResource(selectedCell.getVal1(), selectedCell.getVal2()));
            }

            if (choiceNumber == 1 && temporaryRemovedResources.size()>0){
                System.out.println("Temporary removed resources: "+ temporaryRemovedResources); //todo: da stampare meglio sta lista di risorse
                showMessage("Write the index of the resource you want to re-insert: ",false);
                resourceIndex = askNumber(0,temporaryRemovedResources.size());
                selectedCell = askWarehouseCell();
                try {
                    warehouse.insertResource(temporaryRemovedResources.get(resourceIndex), selectedCell.getVal1(), selectedCell.getVal2());
                    temporaryRemovedResources.remove(resourceIndex);
                } catch (InvalidWarehouseInsertionException e) {
                    showMessage("You can't do this insertion! ",false);
                }
            }

            if (choiceNumber==2) {
                if (temporaryRemovedResources.size() == 0)
                    break;
                else
                    showMessage("You has to insert every temporary removed resource! ",false);
            }

            if(choiceNumber == 3 && fullLeaderSlots.getVal1()!=null && fullLeaderSlots.getVal1()>0){
                temporaryRemovedResources.add(leaderCardResource1);
                fullLeaderSlots.setVal1(fullLeaderSlots.getVal1()-1);
            }
            if(choiceNumber == 5 && fullLeaderSlots.getVal2()!=null && fullLeaderSlots.getVal2()>0){
                temporaryRemovedResources.add(leaderCardResource2);
                fullLeaderSlots.setVal2(fullLeaderSlots.getVal2()-1);
            }
            if(choiceNumber == 4 &&fullLeaderSlots.getVal1()!=null && fullLeaderSlots.getVal1()<2){
                if(temporaryRemovedResources.contains(leaderCardResource1)){
                    temporaryRemovedResources.remove(leaderCardResource1);
                    fullLeaderSlots.setVal1(fullLeaderSlots.getVal1()+1);
                }
            }
            if(choiceNumber == 6 &&fullLeaderSlots.getVal2()!=null && fullLeaderSlots.getVal2()<2){
                if(temporaryRemovedResources.contains(leaderCardResource2)){
                    temporaryRemovedResources.remove(leaderCardResource2);
                    fullLeaderSlots.setVal2(fullLeaderSlots.getVal2()+1);
                }
            }
        }

    }

    /**
     * Asks the user how he wants to insert/discard the resources he has bought at the market.
     * It edit the warehouse passed and the number of full slots of the two leader cards (if the user has the right type of cards).
     * It also edit di list of discarded resources.
     */
    public void insertBoughtResources(PlayerWarehouse warehouse, SameTypePair<Integer> fullLeaderSlots, List<Resource> boughtResources, Map<Resource,Integer> discardedResources) {
        Resource leaderCardResource1 = clientModel.getPlayerLeaderCards(clientModel.getMyNickname()).get(0).getAbilityResource();
        Resource leaderCardResource2 = clientModel.getPlayerLeaderCards(clientModel.getMyNickname()).get(1).getAbilityResource();
        int choiceNumber;
        SameTypePair<Integer> selectedCell;
        while (boughtResources.size()>0) {
            System.out.println("These are the bought resources: "+ boughtResources); //todo: da stampare meglio sta lista di risorse
            System.out.println("Now the considered resource is: "+ boughtResources.get(0));
            showMessage("This is your warehouse: ", false);
            showWarehouse(warehouse);
            showMessage("You can insert the resource (0), discard the resource (1), edit your warehouse (2).  ",false);
            if(fullLeaderSlots.getVal1()!=null){
                System.out.println("This is your Slot Leader Card:  type: " + leaderCardResource1 + " number of full slots: " + fullLeaderSlots.getVal1());
                showMessage("You can also insert the resource in this leader card (if it has the considered resource type) (3). ",false);
            }
            if(fullLeaderSlots.getVal2()!=null){
                showMessage("This is your Slot Leader Card:  type: " + leaderCardResource2 + " number of full slots: " + fullLeaderSlots.getVal2(),false);
                showMessage("You can also insert the resource in this leader card (if it has the considered resource type) (4). ",false);
            }
            choiceNumber = askNumber(0,4);
            if (choiceNumber==0){
                selectedCell = askWarehouseCell();
                try {
                    warehouse.insertResource(boughtResources.get(0), selectedCell.getVal1(), selectedCell.getVal2());
                    boughtResources.remove(0);
                } catch (InvalidWarehouseInsertionException e) {
                    showMessage("You can't do this insertion! ",false);
                }
            }

            if (choiceNumber == 1){
                discardedResources = Resource.addOneResource(discardedResources,boughtResources.get(0));
                boughtResources.remove(0);
            }
            if (choiceNumber==2) {
                editWarehouse(warehouse,fullLeaderSlots);
            }
            if(choiceNumber == 3 && fullLeaderSlots.getVal1()!=null && fullLeaderSlots.getVal1()<2){
                if(leaderCardResource1 == boughtResources.get(0)){
                    boughtResources.remove(0);
                    fullLeaderSlots.setVal1(fullLeaderSlots.getVal1()+1);
                }
            }
            if(choiceNumber == 4 && fullLeaderSlots.getVal2()!=null && fullLeaderSlots.getVal2()<2){
                if(leaderCardResource2 == boughtResources.get(0)){
                    boughtResources.remove(0);
                    fullLeaderSlots.setVal2(fullLeaderSlots.getVal2()+1);
                }
            }
        }
    }
        /**
         * Asks the user a cell of the warehouse.
         * @return the coordinate of the cell.
         */
    public SameTypePair<Integer> askWarehouseCell(){
        showMessage("Insert the row of the warehouse's cell you want to select (1,2,3)", false);
        int row = askNumber(1,3);
        showMessage("Insert the column of the warehouse's cell you want to select (1,2,3)", false);
        int col = askNumber(1,3);
        return new SameTypePair<>(row,col);
    }

    /**
     * Asks the user a number >=lowLimit , <=highLimit. It doesn't print anything (only error messages)
     * @return the chosen number.
     */
    public int askNumber(int lowLimit, int highLimit){ //todo: sostituire ciò ovunque possibile
        String choice = scanner.nextLine();
        while(checkNumber(choice,lowLimit,highLimit)==null){
            showErrorMessage("Invalid choice! Try again: ");
            choice = scanner.nextLine();
        }
        return checkNumber(choice, 0 ,2);
    }



    //                  ------ SHOW METHODS -----   //da testare

    @Override
    public void showDevelopmentCardBoard(){
        for (int i=0;i<3;++i)
            for (int j=0;j<4;++j)
                showCard(clientModel.getDevelopmentCardBoard().getCard(i,j));
    }

    @Override
    public void showMarket(){

        showMessage(Styler.color('b',"#\t1\t2\t3\t4"),true);
        for (int i=0;i<Market.MAXROWS;++i){
            System.out.print(i+1 + "\t");
            for (int j=0;j<Market.MAXCOLUMNS+1;++j)
                System.out.print(clientModel.getMarket().getColor(i,j).toString() + "\t");
            System.out.print("\n");
        }
    }

    @Override
    public void showLeaderCard(LeaderCard card){ //da testare

        showMessage(Styler.format('r',card.getId()+""),false);
        showMessage(Styler.color('b', switch (card.getAbilityResource().toString()){
            case "COIN"-> Styler.ANSI_COIN;
            case "STONE"-> Styler.ANSI_STONE;
            case "SERVANT"-> Styler.ANSI_SERVANT;
            case "SHIELD"-> Styler.ANSI_SHIELD;
            default -> "";
        }+" "+ card.getAbility().toString()),false);
        showMessage(Styler.color('b', "Victory Points: " + card.getVictoryPoints()),false);

        //controllare quando è null
       card.getRequiredResources().forEach((k, v) ->
               showMessage(Styler.color('b', Styler.ANSI_TOGIVE + "" + switch(k.toString()){
           case "COIN"-> Styler.ANSI_COIN;
           case "STONE"-> Styler.ANSI_STONE;
           case "SERVANT"-> Styler.ANSI_SERVANT;
           case "SHIELD"-> Styler.ANSI_SHIELD;
           default -> "";
       }+" "+ v),false));

        //controllare quando è null
        card.getRequiredCards().forEach((k, v) ->
               showMessage(Styler.color('b', Styler.ANSI_TOGIVE + "" + switch(k.toString()){
           case "YELLOW"-> Styler.ANSI_YELLOW;
           case "BLUE"-> Styler.ANSI_BLUE;
           case "GREEN"-> Styler.ANSI_GREEN;
           case "PURPLE"-> Styler.ANSI_PURPLE;
           default -> "";
       }+" "+ v),false));
    }

    @Override
    public void showCard(DevelopmentCard card) {

        showMessage(Styler.format('r',Styler.color(switch (card.getType().toString()){
            case "YELLOW" -> 'y';
            case "BLUE"-> 'b';
            case "GREEN"-> 'g';
            case "PURPLE"-> 'p';
            default -> ' ';
        },card.getId()+"")),false);

        showMessage(Styler.format('b', "Card Cost: "),false);
        card.getCost().forEach((k, v) ->
                showMessage(Styler.color('b', switch(k.toString()){
                    case "COIN"-> Styler.ANSI_COIN;
                    case "STONE"-> Styler.ANSI_STONE;
                    case "SERVANT"-> Styler.ANSI_SERVANT;
                    case "SHIELD"-> Styler.ANSI_SHIELD;
                    default -> "";
                }+" "+ v),false));

        showMessage(Styler.color('b', "Card Level: " + card.getLevel()),false);

        card.getRequiredResources().forEach((k, v) ->
                showMessage(Styler.color('b', Styler.ANSI_TOGIVE + "" + switch(k.toString()){
                    case "COIN"-> Styler.ANSI_COIN;
                    case "STONE"-> Styler.ANSI_STONE;
                    case "SERVANT"-> Styler.ANSI_SERVANT;
                    case "SHIELD"-> Styler.ANSI_SHIELD;
                    default -> "";
                }+" "+ v + "/n"),false));

        if(card.getProducedResources()!=null)
        card.getProducedResources().forEach((k, v) ->
                showMessage(Styler.color('b', Styler.ANSI_TOHAVE + "" + switch(k.toString()){
                    case "COIN"-> Styler.ANSI_COIN;
                    case "STONE"-> Styler.ANSI_STONE;
                    case "SERVANT"-> Styler.ANSI_SERVANT;
                    case "SHIELD"-> Styler.ANSI_SHIELD;
                    default -> "";
                }+" "+ v + "/n"),false));

        if (card.getProducedFaithPoints()!=0)
        showMessage(Styler.color('b', Styler.ANSI_TOHAVE + "Faith Points: " + card.getProducedFaithPoints()),false);
        showMessage(Styler.color('b', "Victory Points: " + card.getVictoryPoints()),false);


    }

    @Override
    public void showFaithTrack() {

    }

    /**
     * Shows other player cards
     */
    @Override
    public void showPlayersBoard(){

        showMessage(" " + Styler.format('b', "CardBoards:"),true);
        AtomicInteger i= new AtomicInteger();
        clientModel.getNicknames().forEach(x-> {
            showMessage(Styler.format('b', " ▷ " + x + "has:"),false);
            showCard(clientModel.getPlayersCardBoards().get(i.get()).getUpperCard(0));
            showCard(clientModel.getPlayersCardBoards().get(i.get()).getUpperCard(1));
            showCard(clientModel.getPlayersCardBoards().get(i.getAndIncrement()).getUpperCard(2));
        });
    }

    /**
     * shows a personal card board.
     * @param personalCardBoard is the board you want to show.
     */
    public void showPersonalCardBoard(PersonalCardBoard personalCardBoard){
        for(int i=0; i<3; ++i){
            if(!personalCardBoard.isCardPileEmpty(i)){
                System.out.println("SLOT "+ (i+1));
                showCard(personalCardBoard.getUpperCard(i));
            }/*else{
                System.out.println("\nEMPTY SLOT \n");
            }*/
        }
    }

    /**
     * Shows other player LeaderCards, if activated
     */
    @Override
    public void showPlayersLeaderCards(){ // da capire

        showMessage(" " + Styler.format('b', "Players' LeaderCards:"),true);

        clientModel.getNicknames().forEach(x-> {
            showMessage(Styler.format('b', " ▷ " + x),false);
            if (clientModel.getPlayerLeaderCards(x).get(0).isActivated()){
                showMessage(Styler.format('i', " Has Activated "),false);
                showLeaderCard(clientModel.getPlayerLeaderCards(x).get(0));
            }
            if (clientModel.getPlayerLeaderCards(x).get(1).isActivated()){
                showMessage(Styler.format('i', " Has Activated "),false);
                showLeaderCard(clientModel.getPlayerLeaderCards(x).get(1));
            }
        });

    }

    /**
     * Print the map received in input with the ladder
     */
    @Override
    public void showLadderBoard() throws FileNotFoundException {
        showMessage(Styler.format('b',Styler.ANSI_TALK + "This is the LadderBoard of the game:"+ Styler.ANSI_VICTORY ),true);

        Map<String, Integer> temp = new HashMap<>();
        AtomicInteger i = new AtomicInteger();

        clientModel.getNicknames().forEach(x-> temp.put(x,clientModel.getPlayersVP().get(i.getAndIncrement())));

        temp.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(k-> showMessage(Styler.format('b', "Player ▷ " + k + "points"),false));

        askNewGame();
    }

    private void askNewGame() throws FileNotFoundException {

        showMessage(Styler.ANSI_TALK + "\n\tDo you wish to play again? [Yes/No]: ",false);
        String choice = scanner.nextLine();

        while (!choice.equalsIgnoreCase("yes") && !choice.equalsIgnoreCase("no")){
            showErrorMessage("Invalid choice! Try again: ");
            choice = scanner.nextLine();
        }
        serverHandler.sendNewGame(choice.equalsIgnoreCase("yes"));
    }

    @Override
    public void showWarehouse(PlayerWarehouse warehouse){

        if (warehouse.getResource(1,1)!=null)
        showMessage(Styler.format('i', " (1.1)▷ " + warehouse.getResource(1,1)),false);
        else
            showMessage("--",false);

        if (warehouse.getResource(2,1)== null || warehouse.getResource(2,2)==null){
            if (warehouse.getResource(2,1)!= null)
                showMessage(Styler.format('i', " (2.1)▷ " + warehouse.getResource(2,1) + "--"),false);
            else
                showMessage(Styler.format('i', " (2.2)▷ " + warehouse.getResource(2,2) + "--"),false);
        }else
            showMessage(Styler.format('i', " (2.1)▷ " + warehouse.getResource(2,1) +"(2.2)▷"+ warehouse.getResource(2,2)),false);

        if (warehouse.getResource(3,1)== null || warehouse.getResource(3,2)==null|| warehouse.getResource(3,3)==null){
            if (warehouse.getResource(3,1)!= null && warehouse.getResource(3,2)!= null)
                showMessage(Styler.format('i', " (3.1)▷ " + warehouse.getResource(3,1)+" (3.2)▷ " + warehouse.getResource(3,2) + "--"),false);
            if (warehouse.getResource(3,1)!= null && warehouse.getResource(3,3)!= null)
                showMessage(Styler.format('i', " (3.1)▷ " + warehouse.getResource(3,1)+" (3.3)▷ " + warehouse.getResource(3,3)+ "--"),false);
            if (warehouse.getResource(3,2)!= null && warehouse.getResource(3,3)!= null)
                showMessage(Styler.format('i', " (3.2)▷ " + warehouse.getResource(3,2)+" (3.3)▷ " + warehouse.getResource(3,3)+ "--"),false);

            if (warehouse.getResource(3,1)!= null && warehouse.getResource(3,2)==null && warehouse.getResource(3,3)==null)
                showMessage(Styler.format('i', " (3.1)▷ " + warehouse.getResource(3,1)+ "-- --"),false);
            if (warehouse.getResource(3,1)== null && warehouse.getResource(3,2)!=null && warehouse.getResource(3,3)==null)
                showMessage(Styler.format('i', " (3.2)▷ " + warehouse.getResource(3,2)+ "-- --"),false);
            if (warehouse.getResource(3,1)== null && warehouse.getResource(3,2)==null && warehouse.getResource(3,3)!=null)
                showMessage(Styler.format('i', " (3.3)▷ " + warehouse.getResource(3,3)+ "-- --"),false);

        }else
            showMessage(Styler.format('i', " (2.1)▷ " + warehouse.getResource(2,1) +"(2.2)▷"+ warehouse.getResource(2,2)),false);

    }

    @Override
    public void showStrongbox(Map<Resource,Integer> strongbox){
        //se null la skippa
        strongbox.entrySet().forEach(x-> showMessage(Styler.format('i', " ▷ " + x),false));     //stampa tipo "-> key:value"
    }

    /**
     * Notify all that a player has been disconnected (and the game has ended ?FA)
     * @param disconnected The nickname of the disconnected player
     */
    @Override
    public void showDisconnectionMessage(String disconnected){
        showMessage("GAME OVER: " + disconnected + " has disconnected.",true);   //oppure diverso in base alla FA
    }

    /**
     * Shows an error message
     * @param errorMessage The message to be shown
     */
    public void showErrorMessage(String errorMessage) {     //invalid action
        showMessage(Styler.color('r',"That's unfortunate: "+errorMessage),true);
    }

    /**
     * Notify whose turn is
     *
     * @param currentNickname The nickname of whom taking the turn
     */
    @Override
    public void showTurn(String currentNickname) {
        showMessage("It's " + currentNickname + "'s turn.", false);
    }

    /**
     * Notify that the game has ended and the winning status
     * @param winner The nickname of the winner
     * @param youWon True if the player has win
     */
    @Override
    public void showEndGameMessage(String winner, boolean youWon){
        if(youWon)
            showMessage(Styler.color('g',"Congratulations YOU WON " ),true);
        else
            showMessage(Styler.color('y',"That's sad, YOU LOSE " ),true);
    }

    //todo to be changed
    public void printActions() {
        List<Integer> availableActions= new ArrayList<>();
        showMessage("\n" + Styler.format('b', "Possible actions are: "),false);
        int index=1;
        if (availableActions.get(0).equals(1))
            showMessage(Styler.format('b', index++ +") Buy At Market"),false);
        if (availableActions.get(1).equals(1))
            showMessage(Styler.format('b', index++ +") Activate Production"),false);
        if (availableActions.get(2).equals(1))
            showMessage(Styler.format('b', index++ +") Edit Warehouse"),false);
        if (availableActions.get(3).equals(1))
            showMessage(Styler.format('b', index++ +") Show other players active LeaderCards"),false);
        if (availableActions.get(4).equals(1))
            showMessage(Styler.format('b', index++ +") Show faith track"),false);
        if (availableActions.get(5).equals(1))
            showMessage(Styler.format('b', index++ +") Show other players productions"),false);

        //PRINT OTHER USEFUL THINGS TO THE PLAYER

        showMessage(Styler.format('b', Styler.ANSI_TALK + "Insert your action: "),false);
        String choice = scanner.nextLine();

        //todo:fix it
        while (true)//invalid choice
        {
            showErrorMessage("Invalid choice! Try again: ");
            choice = scanner.nextLine();
        }

        //serverHandler.sendAction();
    }



                                                                //########################## CHECKS #########################

    /**
     * Tests if the input is a valid LeaderCard choice (1...4)
     * @param leaderNum The entered text
     * @return  The null value if the string is not a correct value, otherwise a TreeSet with row and column
     */
    public TreeSet<Integer> checkLeaderCardNum(String leaderNum) { //todo: da controllare
        int first=5, last=5;
        try {
            first = Integer.parseInt(String.valueOf(leaderNum.charAt(0)));
            last = Integer.parseInt(String.valueOf(leaderNum.charAt(1)));
        } catch (NumberFormatException ignored) {}
        TreeSet<Integer> s1 = new TreeSet<>();
        s1.add(first);
        return (first < 5 && last < 5 && s1.add(last)) ? s1 : null;
    }

    /**
     * Tests if the input is a valid nickname with alphanumeric, one space, point, dash, underscore in regex
     * @param nickname  The entered string
     * @return  True if the nickname is valid, false otherwise
     */
    public boolean checkNickname(String nickname) {
        String expression = "^[\\p{Alnum}\\s._-]+$";
        return nickname.matches(expression);
    }

    /**
     * This method checks if the number passed (in a string) is an integer between the two limits
     * @param number the string from the user
     * @param lowLimit the number has to be >= than the lowLimit
     * @param highLimit the number has to be <= than the highLimit
     * @return null if the check is false, otherwise the number
     */
    public Integer checkNumber(String number, int lowLimit, int highLimit){ //todo: se sto metodo funziona leviamo gli altri check dei numeri
        int num;
        try{
            num = Integer.parseInt(number);
        }catch(NumberFormatException e){
            return null;
        }
        return (num <= highLimit && num >= lowLimit)? num : null;
    }
}
