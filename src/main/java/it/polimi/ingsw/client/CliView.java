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
    private final ActionHandler actionHandler;
    private final ClientModel clientModel;

    /**
     * Constructor
     */
    public CliView() {
        this.scanner = new Scanner(System.in);
        clientModel = new ClientModel();
        actionHandler = new ActionHandler(clientModel);

    }

    @Override
    public void setConnectionHandler(ConnectionHandler connectionHandler) {
        this.connectionHandler=connectionHandler;
    }

    @Override
    public void launch() throws FileNotFoundException {
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

    public String askNickname(){
        //sto metodo chiede il nick al giocatore e lo ritorna
        String nickname = scanner.nextLine();

        return nickname; //è giusto per fare un metodo, poi bisogna modificarlo, fare controlli ecc
    }

    //chiede il num di giocatori voluto
    public int askNumPlayer(){
        return scanner.nextInt(); //si devono fare controlli ecc
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

    @Override
    public void showDevelopmentCards(List<DevelopmentCard> cards) throws FileNotFoundException {

    }

    @Override
    public void showPlayersBoard(List<Player> playerList) {

    }

    @Override
    public void showPlayersLeaderCards(List<Player> playerList) {

    }

    @Override
    public void showFaithTrack(List<Integer> trackInfo) {

    }

    @Override
    public void askAction(List<Integer> roundActions) {
        //print list of actions
        //choice validated
        //connectionHandler.sendAction()
    }

    @Override
    public void showLadderBoard(Map<Player, Integer> scores) throws FileNotFoundException {

    }

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
