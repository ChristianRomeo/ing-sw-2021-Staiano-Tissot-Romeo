package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.ActionHandler;
import it.polimi.ingsw.client.ConnectionHandler;
import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.Player;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Logger;

/**
 * View side CLI Mode
 */
public class OldCliView implements View{
    private final Scanner scanner;
    //private final oldInputValidator oldInputValidator;
    private OldServerHandler oldServerHandler;
    private final static Logger logger = Logger.getLogger(OldCliView.class.getName());

    /**
     * Constructor
     */
    public OldCliView() {
        //this.oldInputValidator = new oldInputValidator();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Sets the serverHandler
     *
     * @param oldServerHandler The serverHandler
     */
    public void setServerHandler(OldServerHandler oldServerHandler) {
        this.oldServerHandler = oldServerHandler;
    }

    @Override
    public void setConnectionHandler(ConnectionHandler connectionHandler) {

    }

    /**
     * CLIui start, connects the client
     */
    public void launch() {  //todo: what does this do?
        /*

        showMessage(Format.style('i', Format.TALK + " Welcome\nPlease wait, You will join the first available game..."),true);
        serverHandler.setUpConnection();

         */


    }

    /**
     * Shows a message to the user
     *
     * @param message   The message to be shown
     * @param newScreen True want to clean the console
     */
    public void showMessage(String message, boolean newScreen) {
        if (newScreen)
            //Format.resetScreen();
        System.out.println(message);
    }

    /**
     * Shows a waiting message to the user
     */
    public void showWaiting() {
      //  showMessage(Format.style('i', Format.SLEEP + " The game will start shortly, brace yourself!"),true);
    }

    /**
     * Asks nickname and if it's a new game the number of players for the game and notify the information to the serverHandler
     *
     * @param newGame True if the it is a new game, otherwise false
     */
    public void setUpGame(boolean newGame) {
        /*
        String nickname = askNickname();

        boolean correct;
        Integer num = 0;
        if (newGame) {
            do {
                showMessage(Format.style('b', Format.HELLO + "\n You're the first player "),true);
                showMessage(Format.style('b', "\n Choose the number of Players [1..4]: "),false);
                String numPlayersString = scanner.nextLine();
                num=inputValidator.isNumPlayers(numPlayersString);
                correct = num!=null;        //op. ternario

                if (!correct)
                    showErrorMessage(Format.color('r', Format.CANT + " Invalid choice, Please try again: "));

            } while (!correct);
        }
        showMessage(Format.style('i', Format.SLEEP + "\n  Waiting for others players to connect..."),true);
        serverHandler.sendSetUpPlayer(nickname, num);
        */

    }

    /**
     * Asking nickname
     *
     * @return The chosen nickname
     */
    private String askNickname() {

        boolean correct=true;
        String nickname;
        do {

            //showMessage("\n Please enter your nickname: ",true);
            nickname = scanner.nextLine();
            //correct = oldInputValidator.checkNickname(nickname);

            //if (!correct)
                //showErrorMessage(" Invalid nickname, Please try again: ");

        } while (!correct);
        return nickname;
    }

    /**
     * Asking the game cards
     */
    public void askLeaderCards() {
        /*
        //List<LeaderCard> chosenLeaderCards = new ArrayList<>();
        TreeSet<Integer> enteredCard;
        boolean correct;

        Format.resetScreen();

        do {
            showMessage(Format.TALK + "\n\n " + Format.style('b', "Choose 2 cards :"),false);
            //List<LeaderCard> leadercard = metodo invio dal server
            //System.out.println(Format.style('b', "\n   ❖ " + leadercad.getId())); //dobbiamo capire cosa stampare delle carte
            showMessage(" ↳: ",false);
            String chosenCards = scanner.next();        //nextline  ?

            enteredCard = inputValidator.isLeaderCard(chosenCards);
            correct = enteredCard!=null;

            if(!correct)
                showErrorMessage(Format.color('r', Format.CANT + " Invalid choice, Please try again: "));

        } while (!correct);

        showWaiting();
       //serverHandler.sendLeaderCards(enteredCard);    //in base alle informazioni che avremo nel client scegliamo se mandare un numero o tipo LeaderCard
       */

    }

    /**
     * Print the DevelopmentCard matrix
     *
     * @param cards All the remaining DevelopmentCards
     */
    public void showDevelopmentCards(List<DevelopmentCard> cards) {
        /*
        //List<DevelopmentCard> allCard = Configs.getDevelopmentCards();

        showMessage(" " + Format.style('b', Format.TALK + "The cards used in this match will be:"),true);
        for (DevelopmentCard card : cards) {
                //System.out.println(Format.style('b', "\n   ❖ " + card.getName().toUpperCase()));      //again, cosa stampiamo

        }*/
    }

    /**
     * Shows other player cards
     *
     * @param playerList The list of players of the game
     */
    public void showPlayersBoard(List<Player> playerList) {
        /*

        showMessage(" " + Format.style('b', "CardBoards:"),true);
        for (Player player : playerList) {
            showMessage(Format.style('b', "\n   ▷ " + player.getNickname()),false);
            //System.out.println(Format.style('i', " Has " + player.getStatusPlayer().getPersonalCardBoard().getCard(i,j)));     //loop the boardcards
        }*/
    }

    /**
     * Shows other player LeaderCards, if activated
     *
     * @param playerList The list of players of the game
     */
    public void showPlayersLeaderCards(List<Player> playerList) {
        /*
        showMessage(" " + Format.style('b', "LeaderCards:"),true);
        for (Player player : playerList) {
            showMessage(Format.style('b', "\n   ▷ " + player.getNickname()),false);
            if (player.getStatusPlayer().getLeaderCard(0).isActivated())
            showMessage(Format.style('i', " Has Activated " + player.getStatusPlayer().getLeaderCard(0).getId()),false);   //again, cosa stampiamo
            if (player.getStatusPlayer().getLeaderCard(1).isActivated())
            showMessage(Format.style('i', " Has Activated " + player.getStatusPlayer().getLeaderCard(1).getId()),false);   //again, cosa stampiamo

        } */
    }

    /**
     * Shows the faith track
     *
     * @param trackInfo   The track's player position ([0] first, [1] second...)
     */
    public void showFaithTrack(List<Integer> trackInfo) {
       // Format.resetScreen();


    }

    /**
     * Asks what the player want to do
     *
     * @param roundActions  The possible actions    ([0]market, [1] prod, [2]warehouse)
     */
    public void askAction(List<Integer> roundActions) {
    /*
        boolean correct;

        //eventually reset screen
        do {
            correct=true;
            showMessage("\n" + Format.style('b', "Possible actions are: "),false);
            if (roundActions.get(0).equals(1))
                showMessage(Format.style('b', "1)Buy At Market"),false);
            if (roundActions.get(1).equals(1))
                showMessage(Format.style('b', "2)Activate Production"),false);
            if (roundActions.get(2).equals(1))
                showMessage(Format.style('b', "3)Edit Warehouse"),false);

            //PRINT OTHER USEFUL THINGS TO THE PLAYER

            showMessage(Format.style('b', Format.TALK + "Insert your action: "),false);
            int action;
            try {
                action= scanner.nextInt();
            }catch (InputMismatchException e){
                showErrorMessage(Format.style('b', Format.CANT + " Invalid choice, Please try again: "));
                correct=false;
            }

            //serverHandler.sendAction(action);     //send the choice
        } while (!correct);
        */

    }

    /**
     * Notify that the game has ended and the winning status
     *
     * @param winner The nickname of the winner
     * @param youWon         True if the player has win
     */
    public void showEndGameMessage(String winner, boolean youWon) {
        if(youWon)
            showMessage("Congratulations YOU WON " ,true);
        else
            showMessage("That's sad, YOU LOSE " ,true);
    }

    /**
     * Shows the LadderBoard of the match
     * @param scores a map with players scores
     */
    public void showLadderBoard(Map<Player,Integer> scores) throws FileNotFoundException {
        /*
        showMessage(Format.style('g',Format.TALK + "This is the LadderBoard of the game:"+ Format.VICTORY ),true);

        scores.forEach((k, v) -> System.out.format("Player %s obtained %d points",k,v));

        askNewGame(); */
    }

    /**
     * Notify all that a player has been disconnected (and the game has ended ?FA)
     *
     * @param disconnected The nickname of the disconnected player
     */
    public void showDisconnectionMessage(String disconnected) throws FileNotFoundException {
        showMessage("GAME OVER: " + disconnected + " has disconnected.",true);   //oppure diverso in base alla FA
    }

    /**
     * Shows an error message
     *
     * @param errorMessage The message to be shown
     */
    public void showErrorMessage(String errorMessage) {     //invalid action
        showMessage("> Error: ",true);
    }

    /**
     * Notify whose turn is
     *
     * @param currentNickname The nickname of whom taking the turn
     */
    public void showTurn(String currentNickname) {
        showMessage("It's " + currentNickname + "'s turn.", false);
    }

}
