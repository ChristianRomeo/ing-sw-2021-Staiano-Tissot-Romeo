package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.Client;
import it.polimi.ingsw.controller.OldServerHandler;
import it.polimi.ingsw.controller.View;
import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.Player;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Logger;

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
        this.scanner = new Scanner(System.in);
        clientModel = new ClientModel();
    }

    @Override
    public void setConnectionHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler=connectionHandler;
    }

    @Override
    public void launch() {
        actionHandler = new ActionHandler(clientModel,this,connectionHandler);
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
        String nickname;
        showMessage("Inserisci nickname: ", true);
        nickname = scanner.nextLine();
        while (!checkNickname(nickname)){
            showMessage("Scelta non valida, riprova: ",false);
            nickname = scanner.nextLine();
        }
        showMessage(Styler.format('i', Styler.ANSI_WAIT + " The game will start shortly, brace yourself!"),true);
        return nickname;
    }

    //chiede il num di giocatori voluto
    public int askNumPlayer(){
        String numPlayer;
        showMessage("Inserisci numero giocatori: ",true);
        numPlayer = scanner.nextLine();
        while (checkNumPlayer(numPlayer)==null){
            showMessage("Scelta non valida, riprova: ",false);
            numPlayer = scanner.nextLine();
        }
        return checkNumPlayer(numPlayer);
    }

    public ClientModel getClientModel() {
        return clientModel;
    }

    @Override
    public void setUpGame(boolean newGame) {
        //ask nickname
        //validate
        //if newGame chiedi giocatori
        //validate
        //connectionhandler.sendPlayer(nickname,num)
    }

    @Override
    public void askLeaderCards() throws FileNotFoundException {
        //show leadercards
        //choice
        //connectionHandler.sendLeaderCards(enteredCard);
    }

    /**
     * This method asks to the user an index (0/1) of a leader card.
     * It keeps asking until the user select a valid index.
     * @return the index selected.
     */
    public int askLeaderCard(){
        String string;
        int index=5;
        while (index!=0 && index!=1) {
            showMessage("Quale carta leader vuoi selezionare? 0/1",false);
            string =scanner.nextLine();
            try {
                index = Integer.parseInt(String.valueOf(string.charAt(0)));
            } catch (NumberFormatException ignored) {}
        }
        return index;
    }

    @Override
    public void showDevelopmentCards(List<DevelopmentCard> cards) {

    }
    private void showLeaderCard(Player player, int id) { //da testare

        showMessage(Styler.color('b', switch (player.getStatusPlayer().getPlayerLeaderCards().get(id).getAbilityResource().toString()){
            case "COIN"-> Styler.ANSI_COIN;
            case "STONE"-> Styler.ANSI_STONE;
            case "SERVANT"-> Styler.ANSI_SERVANT;
            case "SHIELD"-> Styler.ANSI_SHIELD;
            default -> "";
        }+ player.getStatusPlayer().getPlayerLeaderCards().get(id).getAbility().toString()),false);
        showMessage(Styler.color('b', "Victory Points: " + player.getStatusPlayer().getPlayerLeaderCards().get(id).getVictoryPoints()),false);

        //controllare quando è null
       player.getStatusPlayer().getPlayerLeaderCards().get(id).getRequiredResources().forEach((k, v) ->
               showMessage(Styler.color('b', Styler.ANSI_TOGIVE + "" + switch(k.toString()){
           case "COIN"-> Styler.ANSI_COIN;
           case "STONE"-> Styler.ANSI_STONE;
           case "SERVANT"-> Styler.ANSI_SERVANT;
           case "SHIELD"-> Styler.ANSI_SHIELD;
           default -> "";
       }+" "+ v + "/n"),false));

        //controllare quando è null
        player.getStatusPlayer().getPlayerLeaderCards().get(id).getRequiredCards().forEach((k, v) ->
               showMessage(Styler.color('b', Styler.ANSI_TOGIVE + "" + switch(k.toString()){
           case "YELLOW"-> Styler.ANSI_YELLOW;
           case "BLUE"-> Styler.ANSI_BLUE;
           case "GREEN"-> Styler.ANSI_GREEN;
           case "PURPLE"-> Styler.ANSI_PURPLE;
           default -> "";
       }+" "+ v + "/n"),false));
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
            showCard(player, player.getStatusPlayer().getPersonalCardBoard().getUpperCard(0));
            showCard(player, player.getStatusPlayer().getPersonalCardBoard().getUpperCard(1));
            showCard(player, player.getStatusPlayer().getPersonalCardBoard().getUpperCard(2));
        }
    }

    private void showCard(Player player, DevelopmentCard upperCard) {

    }

    /**
     * Shows other player LeaderCards, if activated
     * @param playerList The list of players of the game
     */
    @Override
    public void showPlayersLeaderCards(List<Player> playerList) {
        showMessage(" " + Styler.format('b', "Players' LeaderCards:"),true);

        for (Player player : playerList) {
            showMessage(Styler.format('b', " ▷ " + player.getNickname()),false);
            if (player.getStatusPlayer().getLeaderCard(0).isActivated()){
                showMessage(Styler.format('i', " Has Activated "),false);
                showLeaderCard(player, player.getStatusPlayer().getLeaderCard(0).getId());
            }
            if (player.getStatusPlayer().getLeaderCard(1).isActivated()){
                showMessage(Styler.format('i', " Has Activated "),false);
                showLeaderCard(player, player.getStatusPlayer().getLeaderCard(1).getId());
            }

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


    @Override
    public void showFaithTrack(List<Integer> trackInfo) {

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

        while (true)//invalid choice
        {
            showMessage(Styler.color('r',"Scelta non valida, riprova: "),false);
            choice = scanner.nextLine();
        }

        //connectionHandler.sendAction();
    }

    /**
     * Print the map received in input with the ladder
     * @param scores a map with list of player and his relative score
     */
    @Override
    public void showLadderBoard(Map<Player, Integer> scores) throws FileNotFoundException {
        showMessage(Styler.format('b',Styler.ANSI_TALK + "This is the LadderBoard of the game:"+ Styler.ANSI_VICTORY ),true);

        scores.forEach((k, v) -> System.out.format("Player %s obtained %d points",k,v));

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
     * @param devNum  The entered text
     * @return  The null value if the string is not a correct value, otherwise a map with row and column
     */
    public Map<Integer,Integer> checkDevCardNum(String devNum) {
        int row=9, col=9;
        try {
            row = Integer.parseInt(String.valueOf(devNum.charAt(0)));
            col = Integer.parseInt(String.valueOf(devNum.charAt(1)));
        } catch (NumberFormatException ignored) {}
        return (row < 3 || col < 4)? new HashMap<>(row,col) : null;
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
}
