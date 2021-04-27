package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.LeaderCard;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Logger;

public class CliView /*implements View*/{
    private final Scanner scanner;
    private final InputValidator inputValidator;
    private ServerHandler serverHandler;
    private final static Logger logger = Logger.getLogger(Server.class.getName());

    /**
     * Constructor
     */
    public CliView() {
        this.inputValidator = new InputValidator();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Sets the serverHandler
     *
     * @param serverHandler The serverHandler
     */
    public void setServerHandler(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    //todo: this is really necessary? possiamo aggregare?
    public void launch() throws FileNotFoundException {

        Format.resetScreen();
        System.out.println(Format.style('i', "  > You will be added to the first available game..."));

        serverHandler.setConnection();
    }

    /**
     * Shows a message to the user
     *
     * @param message   The message to be shown
     * @param newScreen True want to clean the console
     */
    public void showMessage(String message, boolean newScreen) {
        if (newScreen)
            Format.resetScreen();
        System.out.println(message);
    }

    /**
     * Shows a waiting message to the user
     */
    public void showWaiting() {
        Format.resetScreen();
        System.out.println(Format.style('i', "  > The game will start shortly, brace yourself!"));
    }

    /**
     * Asks nickname and if it's a new game the number of players for the game and notify the information to the serverHandler
     *
     * @param newGame True if the it is a new game, otherwise false
     */
    public void setUpGame(boolean newGame) {
        Format.resetScreen();
        String nickname = askNickname();

        boolean correct;
        Integer num = 0;
        if (newGame) {
            Format.resetScreen();
            do {
                System.out.print(Format.style('b', "\n Enter the number of Players [1..4]: "));
                String numPlayersString = scanner.nextLine();
                num=inputValidator.isNumPlayers(numPlayersString);
                correct = num!=null;        //op. ternario

                if (!correct) {
                    Format.resetScreen();
                    System.out.println(Format.color('r', "  > Invalid choice. Try again."));
                }
            } while (!correct);
        }
        Format.resetScreen();
        System.out.println(Format.style('i', "\n  > Waiting for the other players to connect..."));
        serverHandler.sendSetUpGame(nickname, num);
    }

    /**
     * Asking nickname
     *
     * @return The chosen nickname
     */
    private String askNickname() {
        boolean correct;
        String nickname;
        do {
            System.out.print(Format.style('b', "\n Enter your nickname: "));
            nickname = scanner.nextLine();
            correct = inputValidator.isNickname(nickname);
            if (!correct) {
                Format.resetScreen();
                System.out.println(Format.color('r', "  > Invalid nickname. Try again."));
            }
        } while (!correct);

        return nickname;
    }

    /**
     * Asking the game cards
     */
    public void askLeaderCards() throws FileNotFoundException {
        List<LeaderCard> chosenLeaderCards = new ArrayList<>();

        boolean correct;

        Format.resetScreen();

        do {
            System.out.println("\n\n " + Format.style('b', "Choose 2 cards :"));
            //List<LeaderCard> leadercard = metodo invio dal server
            //System.out.println(Format.style('b', "\n   ❖ " + leadercad.getId())); //dobbiamo capire cosa stampare
            System.out.print("  ↳: ");
            String chosenCards = scanner.next();

            TreeSet<Integer> enteredCard = inputValidator.isLeaderCard(chosenCards);
            correct = (enteredCard!=null&&enteredCard.first()<4&&enteredCard.last()<4);
            if(!correct){
                Frmt.clearScreen();
                System.out.println(Frmt.color('r', "   > Invalid choice. Try again."));
            }else{
                chosenCards.add(enteredCard);
            }

        } while (!correct || chosenCards.size() < numCards);

        showLoading();
        serverHandler.sendGameCards(chosenCards);
    }


}
