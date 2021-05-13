package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.View;
import it.polimi.ingsw.model.*;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class CliView implements View {

    private final Scanner scanner;
    private ConnectionHandler connectionHandler;
    private final static Logger logger = Logger.getLogger(CliView.class.getName());
    private ActionHandler actionHandler;
    private final ClientModel clientModel;


    /**
     * Constructor
     */
    public CliView() {
        int cookie=1; //DOBBIAMO FARLO ARRIVARE DAL SERVER PER POTER RICONNETTERE IL PLAYER
        this.scanner = new Scanner(System.in);
        clientModel = new ClientModel(cookie);
    }

    @Override
    public void setConnectionHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler=connectionHandler;
    }

    @Override
    public void launcher() {
        actionHandler = new ActionHandler(clientModel,this,connectionHandler);
        showMessage(Styler.format('i', Styler.ANSI_TALK + " Welcome!\nPlease wait, You will join the first available game..."),false);

        clientModel.setMyNickname(askNickname()); //chiedo e imposto il nickname
        connectionHandler.setUpConnection();
        //poi credo qua devo far partire metodo che chiede cose a utente in continuazione:
        askActions();
    }

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

    //sto metodo chiede il nick al giocatore e lo ritorna
    public String askNickname(){

        showMessage("Inserisci nickname: ", true);
        String nickname = scanner.nextLine();
        while (!checkNickname(nickname)){
            showMessage("Scelta non valida, riprova: ",false);
            nickname = scanner.nextLine();
        }
        showMessage(Styler.format('i', Styler.ANSI_WAIT + " The game will start shortly, brace yourself!"),false);
        return nickname;
    }

    //chiede il num di giocatori voluto
    public int askNumPlayer(){

        showMessage("Inserisci numero giocatori: ",true);
        String numPlayer = scanner.nextLine();
        while (checkNumber(numPlayer,1,4)==null){
            showMessage("Scelta non valida, riprova: ",false);
            numPlayer = scanner.nextLine();
        }
        return checkNumber(numPlayer,1,4);
    }

    public ClientModel getClientModel() {
        return clientModel;
    }

    @Override
    public void setUpGame(boolean newGame) {        //remove?
        //ask nickname
        //validate
        //if newGame chiedi giocatori
        //validate
        //connectionhandler.sendPlayer(nickname,num)
    }

    /**
     * Asks the game cards in the initial choice
     * @return the set of chosen cards
     */
    @Override
    public TreeSet<Integer> askChoiceLeaderCards() {

        showMessage(Styler.ANSI_TALK + Styler.format('b', "Choose 2 cards :"),false);
        List<LeaderCard> leaderCard = clientModel.getPlayerLeaderCards(clientModel.getCurrentPlayerNick());
        showMessage(" ↳: ",false);
        leaderCard.forEach(this::showLeaderCard);

        String chosenCards = scanner.nextLine();
        while (checkLeaderCardNum(chosenCards)==null){
            showMessage("Scelta non valida, riprova: ",false);
            chosenCards = scanner.nextLine();
        }

        return checkLeaderCardNum(chosenCards);

        /*
        TreeSet<Integer> enteredCard = checkLeaderCardNum(chosenCards);
        List<LeaderCard> temp = new ArrayList<>();
        temp.add(leaderCard.get(enteredCard.first()));
        temp.add(leaderCard.get(enteredCard.last()));

        clientModel.setLeaderCards(clientModel.getCurrentPlayerNick(),temp);*/

        //connectionHandler.sendLeaderCards(enteredCard);   ?
    }

    /**
     * This method asks to the user an index (0/1) of a leader card.
     * It keeps asking until the user select a valid index.
     * @return the index selected.
     */
    @Override
    public int askLeaderCard(){

        List<LeaderCard> leaderCard = clientModel.getPlayerLeaderCards(clientModel.getCurrentPlayerNick());
        showMessage(" ↳: ",false);
        leaderCard.forEach(this::showLeaderCard);

        showMessage(Styler.ANSI_TALK + Styler.format('b', "Choose activation/discard card [0/1]:"),false);
        String string =scanner.nextLine();
        while (checkNumber(string,0,1)==null) {
            showMessage(Styler.color('r',"Scelta non valida! Riprova: "),false);
            string =scanner.nextLine();
        }
        return checkNumber(string,0,1);
    }

    /**
     * This method asks the user a position of a development card in the development card board
     * @return the position (row,col)
     */
    public SameTypePair<Integer> askDevelopmentCard(){
        SameTypePair<Integer> position = new SameTypePair<>();
        showMessage("Inserisci la riga della carta che vuoi selezionare: ", false);
        String string = scanner.nextLine();
        while(checkNumber(string,0,2)==null){
            showMessage(Styler.color('r',"Scelta non valida! Riprova: "),false);
            string = scanner.nextLine();
        }
        position.setVal1(checkNumber(string,0,2));
        showMessage("Inserisci la colonna della carta che vuoi selezionare: ", false);
        string = scanner.nextLine();
        while(checkNumber(string,0,3)==null){
            showMessage(Styler.color('r',"Scelta non valida! Riprova: "),false);
            string = scanner.nextLine();
        }
        position.setVal2(checkNumber(string,0,3));
        return position;
    }

    @Override
    public void showDevelopmentCards(List<DevelopmentCard> cards) {
        cards.forEach(this::showCard);
    }

    private void showLeaderCard(LeaderCard card) { //da testare

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

    private void showCard(DevelopmentCard card) {

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
    public void showFaithTrack(List<Integer> trackInfo) {

    }

    /**
     * Shows other player cards
     *
     * @param playerList The list of players of the game
     */
    @Override
    public void showPlayersBoard(List<Player> playerList) {

        showMessage(" " + Styler.format('b', "CardBoards:"),true);
        for (Player player : playerList) {
            showMessage(Styler.format('b', " ▷ " + player.getNickname() + "has:"),false);
            showCard(player.getStatusPlayer().getPersonalCardBoard().getUpperCard(0));
            showCard(player.getStatusPlayer().getPersonalCardBoard().getUpperCard(1));
            showCard(player.getStatusPlayer().getPersonalCardBoard().getUpperCard(2));
        }
    }

    /**
     * Shows other player LeaderCards, if activated
     * @param playerList The list of players of the game
     */
    @Override
    public void showPlayersLeaderCards(List<Player> playerList) {
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

    @Override
    public void askAction(List<Integer> availableActions) {
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
            showMessage(Styler.color('r',"Scelta non valida, riprova: "),false);
            choice = scanner.nextLine();
        }

        //connectionHandler.sendAction();
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
            showMessage(Styler.color('r',"Scelta non valida, riprova: "),false);
            choice = scanner.nextLine();
        }
        connectionHandler.sendNewGame(choice.equalsIgnoreCase("yes"));
    }

    public void showWarehouse(Player player){

        showMessage(Styler.format('b', " Player warehouse " + player.getNickname() + "has:"),false);

        showMessage(Styler.format('i', " ▷ " +
        clientModel.getPlayersWarehouses().get(clientModel.getNicknames().indexOf(player.getNickname())).getUpperRowResource()),false);                 //if null cosa succede?

        showMessage(Styler.format('i', " ▷ " +
                clientModel.getPlayersWarehouses().get(clientModel.getNicknames().indexOf(player.getNickname())).getMiddleRowResource(0)),false);   //if null cosa succede?
        showMessage(Styler.format('i', " ▷ " +
                clientModel.getPlayersWarehouses().get(clientModel.getNicknames().indexOf(player.getNickname())).getMiddleRowResource(1)),false);   //if null cosa succede?

        showMessage(Styler.format('i', " ▷ " +
                clientModel.getPlayersWarehouses().get(clientModel.getNicknames().indexOf(player.getNickname())).getLowerRowResource(0)),false);   //if null cosa succede?
        showMessage(Styler.format('i', " ▷ " +
                clientModel.getPlayersWarehouses().get(clientModel.getNicknames().indexOf(player.getNickname())).getLowerRowResource(1)),false);   //if null cosa succede?
        showMessage(Styler.format('i', " ▷ " +
                clientModel.getPlayersWarehouses().get(clientModel.getNicknames().indexOf(player.getNickname())).getLowerRowResource(2)),false);   //if null cosa succede?
    }

    public void showStrongbox(Player player){

        showMessage(Styler.format('b', " Player strongbox" + player.getNickname() + "has:"),false);
        clientModel.getPlayersWarehouses().get(clientModel.getNicknames().indexOf(player.getNickname()))
                .getAllResources().entrySet().forEach(x-> showMessage(Styler.format('i', " ▷ " + x),false));
    }

    /**
     * Notify all that a player has been disconnected (and the game has ended ?FA)
     * @param disconnected The nickname of the disconnected player
     */
    public void showDisconnectionMessage(String disconnected) {
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
    public void showTurn(String currentNickname) {
        showMessage("It's " + currentNickname + "'s turn.", false);
    }

    /**
     * Notify that the game has ended and the winning status
     * @param winner The nickname of the winner
     * @param youWon True if the player has win
     */
    public void showEndGameMessage(String winner, boolean youWon) {
        if(youWon)
            showMessage(Styler.color('g',"Congratulations YOU WON " ),true);
        else
            showMessage(Styler.color('y',"That's sad, YOU LOSE " ),true);
    }







                                                                //########################## CHECKS #########################
    /**
     * Tests if the input is a correct number of players
     * @param num  The entered text
     * @return  The null value if the string is not a correct value, otherwise its integer value
     */
    //metodo vecchio da togliere
    public Integer checkNumPlayer(String num) {
        return switch (Integer.parseInt(num)){
            case 1-> 1;
            case 2-> 2;
            case 3-> 3;
            case 4-> 4;
            default -> null;
        };
    }

    /**
     * Tests if the input is a valid LeaderCard choice (1...4)
     * @param leaderNum The entered text
     * @return  The null value if the string is not a correct value, otherwise a TreeSet with row and column
     */
    public TreeSet<Integer> checkLeaderCardNum(String leaderNum) {
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
     * Tests if the input is a valid card position in the matrix
     * @param devNum  The entered text, it has to be "row,col"
     * @return  The null value if the string is not a correct value, otherwise a map with row and column
     */
    //metodo vecchio da togliere
    public SameTypePair<Integer> checkDevCardNum(String devNum) {
        int row=9, col=9;
        try {
            row = Integer.parseInt(String.valueOf(devNum.charAt(0)));
            col = Integer.parseInt(String.valueOf(devNum.charAt(2)));
        } catch (NumberFormatException ignored) {}
        return (row < 3 && col < 4 && row>=0 &&col>=0)? new SameTypePair<>(row,col) : null;
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
        Integer num= null;
        try{
            num = Integer.parseInt(number);
        }catch(NumberFormatException e){
            return null;
        }
        return (num <= highLimit && num >= lowLimit)? num : null;
    }
}
