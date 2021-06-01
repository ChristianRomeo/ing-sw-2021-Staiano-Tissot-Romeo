package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.Events.EndGameEventS2C;
import it.polimi.ingsw.controller.View;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelExceptions.InvalidWarehouseInsertionException;

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

        clientModel.setMyNickname(askNickname()); //chiedo e imposto il nickname
        serverHandler.setUpConnection();
        //poi credo qua devo far partire metodo che chiede cose a utente in continuazione:
        askActions();
    }

    @Override
    public void askActions(){
        while(!clientModel.isGameEnded()){
            scanner.reset();
            //if (clientModel.hasGameStarted())   //bisogna scegliere se current o no, viene stampato "x ha usato il mercato" dopo questo show, non va bene, deve essere il contrario
            //showMessage("Scegli l'azione tra AZIONELEADER PRODUZIONE FINETURNO COMPRACARTA MERCATO MOSTRAFT MOSTRALEADERS MOSTRABOARDS EXIT :");

            String newAction = scanner.nextLine();

            actionHandler.handleAction(newAction);
        }
        askNewGame();
    }

    /**
     * Shows a message to the user
     *
     * @param message   The message to be shown
     */
    public synchronized void showMessage(String message){
        System.out.println(message);
    }


    // ------ ASK METHODS -----

    @Override
    public String askNickname(){

        Styler.header();
        scanner.nextLine();
        scanner.reset();
        showMessage(Styler.format('i', Styler.ANSI_HELLO + " Welcome!\nYou will join the first available game..."));
        showMessage(Styler.ANSI_TALK +"Select your nickname: ");
        String nickname = scanner.nextLine();
        while (!checkNickname(nickname)){
            showErrorMessage("Invalid choice! Try again: ");
            nickname = scanner.nextLine();
        }
        showMessage(Styler.format('i', Styler.ANSI_TALK +" The game will start shortly, brace yourself!"));
        return nickname;
    }

    @Override
    public int askNumPlayer(){
        Styler.cls();
        showMessage("Choose how many players will play: ");
        /*String numPlayer = scanner.nextLine();                          //todo: ASKNUMBER() ?
        while (checkNumber(numPlayer,1,4)==null){
            showErrorMessage("Invalid choice! Try again: ");
            numPlayer = scanner.nextLine();
        }
        return checkNumber(numPlayer,1,4);*/

        return askNumber(1,4);
    }



    /**
     * Asks the game cards in the initial choice
     * @return the set of the indexes of the chosen cards
     */
    @Override
    public SameTypePair<Integer> askChoiceLeaderCards() {

        List<LeaderCard> leaderCard = clientModel.getPlayerLeaderCards(clientModel.getMyNickname());
        showMessage("--------------------");
        leaderCard.forEach(this::showLeaderCard);
        showMessage("Legend: Stones -"+Styler.ANSI_STONE+" Shields -"+Styler.ANSI_SHIELD+" Servants -"+Styler.ANSI_SERVANT+" Coins -"+Styler.ANSI_COIN+" Yellow/Green/Blue/Purple Cards -"+Styler.ANSI_YELLOWCARD+Styler.ANSI_GREENCARD+Styler.ANSI_BLUECARD+Styler.ANSI_PURPLECARD);
        showMessage( Styler.format('b', "\n"+Styler.ANSI_TALK +"Choose the first card to discard (0,1,2,3) :"));
        int index1= askNumber(0,3);
        showMessage( Styler.format('b', Styler.ANSI_TALK +"Choose the second card to discard (0,1,2,3) :"));
        int index2 = askNumber(0,3);

        while(index1 == index2){
            showErrorMessage("You can't discard the same card twice! Try again, Choose the second card to discard (0,1,2,3) :");
            index2 = askNumber(0,3);
        }

        return new SameTypePair<>(index1,index2);
    }

    /**
     * This method asks to the user an index (0/1) of a leader card.
     * And then what card
     * @return [0]=the action [1]=the card index.
     */
    @Override
    public List<Integer> askLeaderCard(){

        List<LeaderCard> leaderCard = clientModel.getPlayerLeaderCards(clientModel.getCurrentPlayerNick());
        showMessage("--------------------");
        leaderCard.forEach(this::showLeaderCard);

        showMessage(Styler.ANSI_TALK + Styler.format('b', "Choose action:  [activation - 0 / discard - 1] :"));
        /*
        String string =scanner.nextLine();
        if (actionHandler.newGame)
            actionHandler.getNewGame(string);
        else
        while (checkNumber(string,0,1)==null) {
            showErrorMessage("Invalid choice! Try again: ");
            string =scanner.nextLine();
            if (actionHandler.newGame)
                actionHandler.getNewGame(string);
        }*/
        int choice = askNumber(0,1);

        //todo:controllare se carta già attiva o scartata
        showMessage(Styler.ANSI_TALK + Styler.format('b', "Choose what card:"));
        /*
        String chosenCard = scanner.nextLine();
        if (actionHandler.newGame)
            actionHandler.getNewGame(string);
        else
        while (checkNumber(chosenCard,0,1)==null){
            showErrorMessage("Invalid choice! Try again: ");
            chosenCard = scanner.nextLine();
            if (actionHandler.newGame)
                actionHandler.getNewGame(string);
        }*/
        int card = askNumber(0,1);
        List<Integer> ret = new ArrayList<>();
        //ret.add(checkNumber(string,0,1));
        //ret.add(checkNumber(chosenCard,0,1));
        ret.add(choice);
        ret.add(card);

        return ret;
    }

    /**
     * This method asks the user a position of a development card in the development card board
     * @return the position (row,col)
     */
    @Override
    public SameTypePair<Integer> askDevelopmentCard(){
        //todo:riguardare il modo di chiedere
        showDevelopmentCardBoard();
        SameTypePair<Integer> position = new SameTypePair<>();
        showMessage("\nChoose the card position, select row: ");
        /*String string = scanner.nextLine();
        while(checkNumber(string,0,2)==null){
            showErrorMessage("Invalid choice! Try again: ");
            string = scanner.nextLine();
        }*/
        position.setVal1(askNumber(0,2));
        showMessage("Now select column: ");

        /*string = scanner.nextLine();
        while(checkNumber(string,0,3)==null){
            showErrorMessage("Invalid choice! Try again: ");
            string = scanner.nextLine();
        }*/

        position.setVal2(askNumber(0,3));
        return position;
    }

    /**
     * asks in what pile of production should the bought card be inserted
     * @return the pile number
     */
    @Override
    public int askCardPile(){
        showMessage("This is your card board: ");
        showPersonalCardBoard(clientModel.getPlayersCardBoards().get(clientModel.getMyIndex()));
        showMessage("Chose the pile where you want to insert your card (0,1,2): ");

        /*String string = scanner.nextLine();
        while(checkNumber(string,0,2)==null){
            showErrorMessage("Invalid choice! Try again: ");
            string = scanner.nextLine();
        }
        return checkNumber(string,0,2);*/
        return askNumber(0,2);
    }

    /**
     * this method asks the user what cards production he wants to activate. (not base production or leader cards productions)
     * @return the list of the indexes of the card he wants to activate.
     */
    public List<Integer> askCardProductions(){
        List<Integer> positions = new ArrayList<>();
        String choice;

        showMessage("This is your card board: ");
        showPersonalCardBoard(clientModel.getPlayersCardBoards().get(clientModel.getMyIndex()));
        //todo: se lo slot è vuoto allora non chiedere se si vuole attivare la produzione li
        for(int i=1; i<=3; i++){
            showMessage("Do you want to activate the production of the card in position "+i+ " ? y/n");
            choice = askChoice();

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
        showMessage(Styler.ANSI_TALK+"Do you want to activate the base production? y/n");
        String choice = askChoice();

        if(choice.equalsIgnoreCase("n"))
            return null;

        showMessage(Styler.ANSI_TALK+"Now please choose two resources you want to use for the production: ");
        baseProductionResources.setVal1(askResource());
        baseProductionResources.setVal2(askResource());
        showMessage(Styler.ANSI_TALK+"You have to choose what resource you want to produce: ");
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
                showMessage("--------------------");
                showLeaderCard(leaderCards.get(i));
                showMessage(Styler.ANSI_TALK+"Do you want to activate this card production? y/n");
                choice = askChoice();

                if(choice.equalsIgnoreCase("y")){
                    showMessage("Choose what resource you want to product: ");
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
        showMessage(Styler.ANSI_TALK+"Choose a resource to obtain (*Stone* -"+Styler.ANSI_STONE+" *Shield* -"+Styler.ANSI_SHIELD+" *Servant* -"+Styler.ANSI_SERVANT+" *Coin* -"+Styler.ANSI_COIN+"):");
        String choice = scanner.nextLine();
        if (actionHandler.isNewGame())
            actionHandler.getNewGame(choice);
        while(!choice.equalsIgnoreCase("coin")&& !choice.equalsIgnoreCase("shield")&&!choice.equalsIgnoreCase("stone") &&!choice.equalsIgnoreCase("servant")){
            showErrorMessage("Invalid choice! Try again: ");
            choice = scanner.nextLine();
            if (actionHandler.isNewGame())
                actionHandler.getNewGame(choice);
        }
        return Resource.valueOf(choice.toUpperCase());
    }

    public Pair<Character,Integer> askMarketUse(){
        char rowOrColumn;
        int index;

        showMarket();
        showMessage(Styler.ANSI_TALK+"Do you want to select a row or a column? r/c");
        String choice = scanner.nextLine();
        if (actionHandler.isNewGame())
            actionHandler.getNewGame(choice);
        while(!choice.equalsIgnoreCase("r") && !choice.equalsIgnoreCase("c")){
            showErrorMessage("Invalid choice! Try again: ");
            choice = scanner.nextLine();
            if (actionHandler.isNewGame())
                actionHandler.getNewGame(choice);
        }
        rowOrColumn = choice.charAt(0);

        if(rowOrColumn=='r'){
            showMessage(Styler.ANSI_TALK+"What row do you want to select? (0,1,2)");
            index = askNumber(0,2);

        }else{
            showMessage(Styler.ANSI_TALK+"What column do you want to select? (0,1,2,3)");
            index = askNumber(0,3);
        }
        Styler.cls();
        return new Pair<>(rowOrColumn,index);
    }

    /**
     * Asks the user, who has 2 white marble leader cards active, which one he wants to use for a white marble.
     * @return the index of the chosen card
     */
    public int askWhiteMarbleChoice(){
        showMessage("--------------------");
        showLeaderCard(clientModel.getPlayerLeaderCards(clientModel.getMyNickname()).get(0));
        showLeaderCard(clientModel.getPlayerLeaderCards(clientModel.getMyNickname()).get(1));

        showMessage(Styler.ANSI_TALK+"You have two white marble leader cards active, which one do you want to use for this white marble? 0/1");
        return askNumber(0,1);
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
            showMessage("This is your warehouse: ");
            showWarehouse(warehouse);
            showMessage("You can temporary remove a resource (0), re-insert a removed resource (1) or exit (2).");
            if(fullLeaderSlots.getVal1()!=null){
                System.out.println("This is your Slot Leader Card:  type: " + leaderCardResource1 + " number of full slots: " + fullLeaderSlots.getVal1());
                showMessage("You can also temporary remove/re-insert a resource from/in your leader card of type "+ leaderCardResource1 +  " (3/4). ");
            }
            if(fullLeaderSlots.getVal2()!=null){
                showMessage("This is your Slot Leader Card:  type: " + leaderCardResource2 + " number of full slots: " + fullLeaderSlots.getVal2());
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
                showMessage("Write the index of the resource you want to re-insert (0,1,..): ");
                resourceIndex = askNumber(0,temporaryRemovedResources.size());
                selectedCell = askWarehouseCell();
                try {
                    warehouse.insertResource(temporaryRemovedResources.get(resourceIndex), selectedCell.getVal1(), selectedCell.getVal2());
                    temporaryRemovedResources.remove(resourceIndex);
                } catch (InvalidWarehouseInsertionException e) {
                    showErrorMessage("You can't do this insertion! ");
                }
            }

            if (choiceNumber==2) {
                if (temporaryRemovedResources.size() == 0)
                    break;
                else
                    showErrorMessage("You have to insert every temporary removed resource! ");
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
     * @return the map of the discarded resources.
     */
    public Map<Resource,Integer> insertBoughtResources(PlayerWarehouse warehouse, SameTypePair<Integer> fullLeaderSlots, List<Resource> boughtResources) {
        Resource leaderCardResource1 = clientModel.getPlayerLeaderCards(clientModel.getMyNickname()).get(0).getAbilityResource();
        Resource leaderCardResource2 = clientModel.getPlayerLeaderCards(clientModel.getMyNickname()).get(1).getAbilityResource();
        int choiceNumber;
        Map<Resource, Integer> discardedResources= new HashMap<>();
        SameTypePair<Integer> selectedCell;
        while (boughtResources.size()>0) {
            //qui si mettono degli spazi che non dovrebbero stare
            showMessage("These are the bought resources: "+ boughtResources);
            showMessage("\nThis is your warehouse: ");
            showWarehouse(warehouse);
            System.out.println("\nNow the considered resource is: "+ boughtResources.get(0));

            showMessage(Styler.ANSI_TALK+"You can insert the resource (0), discard the resource (1) or edit your warehouse (2).");
            if(fullLeaderSlots.getVal1()!=null){
                System.out.println("This is your Slot Leader Card:  type: " + leaderCardResource1 + " number of full slots: " + fullLeaderSlots.getVal1());
                showMessage("You can also insert the resource in this leader card (if it has the considered resource type) (3). ");
            }
            if(fullLeaderSlots.getVal2()!=null){
                showMessage("This is your Slot Leader Card:  type: " + leaderCardResource2 + " number of full slots: " + fullLeaderSlots.getVal2());
                showMessage("You can also insert the resource in this leader card (if it has the considered resource type) (4). ");
            }
            choiceNumber = askNumber(0,4);

            if (choiceNumber==0){
                selectedCell = askWarehouseCell();
                try {
                    warehouse.insertResource(boughtResources.get(0), selectedCell.getVal1(), selectedCell.getVal2());
                    boughtResources.remove(0);
                } catch (InvalidWarehouseInsertionException e) {
                    showErrorMessage("You can't do this insertion! ");
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
        showWarehouse(warehouse);
        return discardedResources;
    }
        /**
         * Asks the user a cell of the warehouse.
         * @return the coordinate of the cell.
         */
    public SameTypePair<Integer> askWarehouseCell(){
        showMessage(Styler.ANSI_TALK+"Select the row of your warehouse you want to use (1,2,3)");
        int row = askNumber(1,3);
        int col = 1;
        if (row!=1){
            showMessage(Styler.ANSI_TALK+"Select the column of your warehouse you want to use (1,2" + (row==3 ? ",3)" : ")") + ":");
             col = askNumber(1,3);
        }
        return new SameTypePair<>(row,col);
    }

    /**
     * Asks the user a number >=lowLimit , <=highLimit. It doesn't print anything (only error messages)
     * @return the chosen number.
     */
    public int askNumber(int lowLimit, int highLimit){

        String choice = scanner.nextLine();
        if (actionHandler.isNewGame())
                actionHandler.getNewGame(choice);
        else
            while(checkNumber(choice,lowLimit,highLimit)==null){
                showErrorMessage("Invalid choice! Try again: ");
                choice = scanner.nextLine();
                if (actionHandler.isNewGame())
                    actionHandler.getNewGame(choice);
            }

        return checkNumber(choice, lowLimit ,highLimit);

    }

    public String askChoice(){
        String choice = scanner.nextLine();
        if (actionHandler.isNewGame())
            actionHandler.getNewGame(choice);
        else
            while(!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n")){
                showErrorMessage("Invalid choice! Try again: ");
                choice = scanner.nextLine();
                if (actionHandler.isNewGame())
                    actionHandler.getNewGame(choice);
            }
            return choice.toLowerCase();
    }

        public void askNewGame() {

        actionHandler.setNewGame(true);
        showMessage("\n"+ Styler.ANSI_TALK + "Do you wish to play again? [y/n]: ");

        //String choice = scanner.nextLine();
        /*while (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n")){
            showErrorMessage("Invalid choice! Try again: ");
            choice = scanner.nextLine();
        }

        if (choice.equalsIgnoreCase("y")) {
            try {
                MastersOfRenaissance.main("cli ".split(" "));
            } catch (FileNotFoundException e) {
                System.out.println("Error while restarting the application.");
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }*/
    }



    //                  ------ SHOW METHODS -----   //da testare

    @Override
    public void showDevelopmentCardBoard(){
        showMessage("");
        for (int i=0;i<3;++i)
            for (int j=0;j<4;++j){
                showMessage(Styler.format('r',Styler.color(switch (clientModel.getDevelopmentCardBoard().getCard(i,j).getType().toString()){
                    case "YELLOW" -> 'y';
                    case "BLUE"-> 'b';
                    case "GREEN"-> 'g';
                    case "PURPLE"-> 'p';
                    default -> ' ';
                },"row: "+i+" Column: "+j)));
                showCard(clientModel.getDevelopmentCardBoard().getCard(i,j));
            }
    }

    /**
     * It shows the market in the client model.
     */
    @Override
    public void showMarket(){

        showMessage(Styler.color('y',"#\t0\t\t1\t\t2\t\t3"));
        for (int i = 0; i< Market.MAXROWS; ++i){
            System.out.print(Styler.color('y',i + "\t"));
            for (int j=0;j<Market.MAXCOLUMNS;++j){
                if(clientModel.getMarket().getColor(i,j)== MarbleColor.RED)
                    System.out.print("RED\t\t");
                else
                    System.out.print(clientModel.getMarket().getColor(i,j).toString() + "\t\t");
            }
            System.out.print("\n");
        }
        showMessage("");

    }

    @Override
    public void showLeaderCard(LeaderCard card){

        showMessage(Styler.format('i',switch(card.getAbilityResource().toString()){
            case "COIN"-> "COIN";
            case "STONE"-> "STONE";
            case "SERVANT"-> "SERVANT";
            case "SHIELD"-> "SHIELD";
            default -> "";
        }+" "+ card.getAbility().toString()));
        showMessage("Victory Points: " + card.getVictoryPoints());

        showMessage("Activation Cost:");
       card.getRequiredResources().forEach((k, v) ->
               showMessage(switch(k.toString()){
           case "COIN"-> Styler.ANSI_COIN;
           case "STONE"-> Styler.ANSI_STONE;
           case "SERVANT"-> Styler.ANSI_SERVANT;
           case "SHIELD"-> Styler.ANSI_SHIELD;
           default -> "";
       }+" "+ v));

        card.getRequiredCards().forEach((k, v) ->
               showMessage(switch(k.toString()){
           case "YELLOW"-> Styler.ANSI_YELLOWCARD;
           case "BLUE"-> Styler.ANSI_BLUECARD;
           case "GREEN"-> Styler.ANSI_GREENCARD;
           case "PURPLE"-> Styler.ANSI_PURPLECARD;
           default -> "";
       }+" "+ v));

        showMessage("--------------------");
    }

    @Override
    public void showCard(DevelopmentCard card) {

        showMessage(Styler.format('b', "Card Cost: "));
        card.getCost().forEach((k, v) ->
                showMessage( switch(k.toString()){
                    case "COIN"-> Styler.ANSI_COIN;
                    case "STONE"-> Styler.ANSI_STONE;
                    case "SERVANT"-> Styler.ANSI_SERVANT;
                    case "SHIELD"-> Styler.ANSI_SHIELD;
                    default -> "";
                }+" "+ v));

        showMessage( "Card Level: " + card.getLevel());
        showMessage( "Victory Points: " + card.getVictoryPoints());

        showMessage(Styler.format('b', "Activation Cost: "));
        card.getRequiredResources().forEach((k, v) ->
                showMessage( switch(k.toString()){
                    case "COIN"-> Styler.ANSI_COIN;
                    case "STONE"-> Styler.ANSI_STONE;
                    case "SERVANT"-> Styler.ANSI_SERVANT;
                    case "SHIELD"-> Styler.ANSI_SHIELD;
                    default -> "";
                }+" "+ v ));

        showMessage(Styler.format('b', "Activation earnings: "));
        if(card.getProducedResources()!=null)
            card.getProducedResources().forEach((k, v) ->
                    showMessage(switch(k.toString()){
                        case "COIN"-> Styler.ANSI_COIN;
                        case "STONE"-> Styler.ANSI_STONE;
                        case "SERVANT"-> Styler.ANSI_SERVANT;
                        case "SHIELD"-> Styler.ANSI_SHIELD;
                        default -> "";
                    }+" "+ v ));

        if (card.getProducedFaithPoints()!=0)
        showMessage("FP: " + card.getProducedFaithPoints());

        showMessage("--------------------");

    }

    /**
     * Shows Players faith tracks
     */
    @Override
    public void showFaithTrack() {
        int numPlayer;
        Styler.cls();
        showMessage(" " + Styler.format('b', "Faith Track player positions: \n"));
        for(int i = 0; i < clientModel.getNicknames().size(); i++)
        {
            numPlayer = i + 1;
            showMessage("Player "+numPlayer+" "+'"'+clientModel.getNicknames().get(i)+'"');
            showMessage("Faith Track Position is "+clientModel.getPlayersFTPositions().get(i).toString());
            showMessage("Pope Favor Tile 1 (5-8) status is "+clientModel.getPlayersPopeTiles().get(i).getVal1().toString());
            showMessage("Pope Favor Tile 2 (12-16) status is "+clientModel.getPlayersPopeTiles().get(i).getVal2().toString());
            showMessage("Pope Favor Tile 3 (19-24) status is "+clientModel.getPlayersPopeTiles().get(i).getVal3().toString());
            //show current victory points ??
            showMessage("--------------------");
        }
            //show list of actions
    }

    /**
     * Shows other player cards
     */
    @Override
    public void showPlayersBoard(){
        Styler.cls();
        showMessage(" " + Styler.format('b', "CardBoards:"));
        AtomicInteger i= new AtomicInteger();
        clientModel.getNicknames().forEach(x-> {
            showMessage(Styler.format('b', " ▷ " + x + " has:"));
            if (!clientModel.isPregame()){
                if (clientModel.getPlayersCardBoards().get(i.get()).getUpperCard(0)!=null)
                    showCard(clientModel.getPlayersCardBoards().get(i.get()).getUpperCard(0));
                if (clientModel.getPlayersCardBoards().get(i.get()).getUpperCard(1)!=null)
                    showCard(clientModel.getPlayersCardBoards().get(i.get()).getUpperCard(1));
                if (clientModel.getPlayersCardBoards().get(i.get()).getUpperCard(2)!=null)
                    showCard(clientModel.getPlayersCardBoards().get(i.getAndIncrement()).getUpperCard(2));
                showMessage("--------------------");
            }
        });
        //show list of actions

    }

    /**
     * shows a personal card board.
     * @param personalCardBoard is the board you want to show.
     */
    public void showPersonalCardBoard(PersonalCardBoard personalCardBoard){
        for(int i=0; i<3; ++i)
            if(!personalCardBoard.isCardPileEmpty(i)){
                System.out.println("SLOT "+ (i+1));
                showCard(personalCardBoard.getUpperCard(i));
            }else
                System.out.println("\nEMPTY SLOT");

    }

    /**
     * Shows other player LeaderCards, if activated
     */
    @Override
    public void showPlayersLeaderCards(){ // da capire
        Styler.cls();
        showMessage(" " + Styler.format('b', "Players' LeaderCards:"));

        clientModel.getNicknames().forEach(x-> {
            showMessage(Styler.format('b', " ▷ " + x));
            if (!clientModel.isPregame() && clientModel.getPlayerLeaderCards(x)!=null){
                showMessage("--------------------");
                if (clientModel.getPlayerLeaderCards(x).get(0).isActivated()){
                    showMessage(Styler.format('i', " Has Activated "));
                    showLeaderCard(clientModel.getPlayerLeaderCards(x).get(0));
                }
                if (clientModel.getPlayerLeaderCards(x).get(1).isActivated()){
                    showMessage(Styler.format('i', " Has Activated "));
                    showLeaderCard(clientModel.getPlayerLeaderCards(x).get(1));
                }
            }

        });

    }

    /**
     * Print the map received in input with the ladder
     */
    @Override
    public void showLadderBoard(EndGameEventS2C endGameEvent){
        Styler.cls();
        if(endGameEvent.getWinners().contains(clientModel.getMyNickname()))
            showMessage("You won!!!"+Styler.ANSI_VICTORY);
        else
            showMessage("You lost!!!"+Styler.ANSI_LOST);

        showMessage(Styler.format('b',Styler.ANSI_TALK + "This is the LadderBoard of the game:"+ Styler.ANSI_VICTORY ));

        for(String nickname : endGameEvent.getVictoryPoints().keySet())
            showMessage(Styler.format('b', "Player ▷ " + nickname + " has victory points: " + endGameEvent.getVictoryPoints().get(nickname)));

        for(String nickname: endGameEvent.getWinners())
            showMessage(nickname + " is a winner!");

    }

    /**
     * Print the received warehouse.
     */
    @Override
    public void showWarehouse(PlayerWarehouse warehouse){

        if (warehouse.getResource(1,1)!=null)
        showMessage(Styler.format('i', " (1.1)▷ " + warehouse.getResource(1,1)));
        else
            showMessage(Styler.format('i'," (1.1)▷ --"));

        if (warehouse.getResource(2,1)== null || warehouse.getResource(2,2)==null){
            if (warehouse.getResource(2,1)!= null)
                showMessage(Styler.format('i', " (2.1)▷ " + warehouse.getResource(2,1) + " (2.2)▷ --"));
            else{
                if(warehouse.getResource(2,2)!= null){
                    showMessage(Styler.format('i', " (2.1)▷ -- "+ " (2.2)▷ " + warehouse.getResource(2,2)));
                }else{
                    showMessage(Styler.format('i', " (2.1)▷ --"+ " (2.2)▷ --" ));
                }
            }
        }else
            showMessage(Styler.format('i', " (2.1)▷ " + warehouse.getResource(2,1) +" (2.2)▷ "+ warehouse.getResource(2,2)));

        if (warehouse.getResource(3,1)== null || warehouse.getResource(3,2)==null|| warehouse.getResource(3,3)==null){
            if (warehouse.getResource(3,1)!= null && warehouse.getResource(3,2)!= null)
                showMessage(Styler.format('i', " (3.1)▷ " + warehouse.getResource(3,1)+" (3.2)▷ " + warehouse.getResource(3,2) + " (3.3)▷ --"));
            if (warehouse.getResource(3,1)!= null && warehouse.getResource(3,3)!= null)
                showMessage(Styler.format('i', " (3.1)▷ " + warehouse.getResource(3,1)+ " (3.2)▷ --"+" (3.3)▷ " + warehouse.getResource(3,3)));
            if (warehouse.getResource(3,2)!= null && warehouse.getResource(3,3)!= null)
                showMessage(Styler.format('i', " (3.1)▷ --"+" (3.2)▷ " + warehouse.getResource(3,2)+" (3.3)▷ " + warehouse.getResource(3,3)));

            if (warehouse.getResource(3,1)!= null && warehouse.getResource(3,2)==null && warehouse.getResource(3,3)==null)
                showMessage(Styler.format('i', " (3.1)▷ " + warehouse.getResource(3,1)+" (3.2)▷ --" +" (3.3)▷ --"));
            if (warehouse.getResource(3,1)== null && warehouse.getResource(3,2)!=null && warehouse.getResource(3,3)==null)
                showMessage(Styler.format('i', " (3.1)▷ --"+" (3.2)▷ " + warehouse.getResource(3,2)+ " (3.3)▷ --"));
            if (warehouse.getResource(3,1)== null && warehouse.getResource(3,2)==null && warehouse.getResource(3,3)!=null)
                showMessage(Styler.format('i', " (3.1)▷ --"+" (3.2)▷ --"+" (3.3)▷ " + warehouse.getResource(3,3)));
            if(warehouse.getResource(3,1)== null && warehouse.getResource(3,2)==null&& warehouse.getResource(3,3)==null)
                showMessage(Styler.format('i', " (3.1)▷ --"+" (3.2)▷ --"+" (3.3)▷ --" ));
        }else
            showMessage(Styler.format('i', " (3.1)▷ " + warehouse.getResource(3,1) +" (3.2)▷ "+ warehouse.getResource(3,2) + " (3.3)▷ "+ warehouse.getResource(3,3)));

    }

    /**
     * Print the received strongbox.
     */
    @Override
    public void showStrongbox(Map<Resource,Integer> strongbox){

        strongbox.entrySet().forEach(x-> showMessage(Styler.format('i', " ▷ " + x)));     //stampa tipo "-> key:value"
        for(Resource r : Resource.values())
            if(!strongbox.containsKey(r))
                showMessage(Styler.format('i', " ▷ " + r.toString()+"=0"));

    }

    /**
     * Notify all that a player has been disconnected (and the game has ended ?FA)
     * @param disconnected The nickname of the disconnected player
     */
    @Override
    public void showDisconnectionMessage(String disconnected){
        Styler.cls();
        showMessage("GAME OVER: " + disconnected + " has disconnected.");   //oppure diverso in base alla FA
    }

    /**
     * Shows an error message
     * @param errorMessage The message to be shown
     */
    public void showErrorMessage(String errorMessage) {     //invalid action
        showMessage(Styler.color('r',"That's unfortunate: "+errorMessage));
    }

    /**
     * Notify whose turn is
     *
     * @param currentNickname The nickname of whom taking the turn
     */
    @Override
    public void showTurn(String currentNickname) {
        showMessage("It's " + currentNickname + "'s turn.");
    }   //todo:da usare


    @Override
    public void showLorenzoTurn(SoloAction soloAction){
        showMessage("Lorenzo has made his action! \nThe activated solo action was: ");
        System.out.println("Type: "+ soloAction.getType());
        if(soloAction.getType()==SoloActionType.DISCARDTWOCARDS)
            System.out.println("The discarded cards were of type: " + soloAction.getDiscardedCardsType());

    }

    //todo to be changed
    /*public void printActions() {
        List<Integer> availableActions= new ArrayList<>();
        showMessage("\n" + Styler.format('b', "Possible actions are: "));
        int index=1;
        if (availableActions.get(0).equals(1))
            showMessage(Styler.format('b', index++ +") Buy At Market"));
        if (availableActions.get(1).equals(1))
            showMessage(Styler.format('b', index++ +") Activate Production"));
        if (availableActions.get(2).equals(1))
            showMessage(Styler.format('b', index++ +") Edit Warehouse"));
        if (availableActions.get(3).equals(1))
            showMessage(Styler.format('b', index++ +") Show other players active LeaderCards"));
        if (availableActions.get(4).equals(1))
            showMessage(Styler.format('b', index++ +") Show faith track"));
        if (availableActions.get(5).equals(1))
            showMessage(Styler.format('b', index++ +") Show other players productions"));

        //PRINT OTHER USEFUL THINGS TO THE PLAYER

        showMessage(Styler.format('b', Styler.ANSI_TALK + "Insert your action: "));
        String choice = scanner.nextLine();

        //todo:fix it
        while (true)//invalid choice
        {
            showErrorMessage("Invalid choice! Try again: ");
            choice = scanner.nextLine();
        }

        //serverHandler.sendAction();
    }*/



                                                                //########################## CHECKS #########################


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
