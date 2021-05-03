package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.Client;
import it.polimi.ingsw.controller.OldInputValidator;
import it.polimi.ingsw.controller.OldServerHandler;
import it.polimi.ingsw.controller.View;
import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.Player;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class CliView implements View {

    private final Scanner scanner;
    private ConnectionHandler connectionHandler;
    private final static Logger logger = Logger.getLogger(Client.class.getName());

    /**
     * Constructor
     */
    public CliView() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void setServerHandler(OldServerHandler oldServerHandler) {

    }

    @Override
    public void launch() throws FileNotFoundException {
        connectionHandler.setUpConnection();
        //poi credo qua devo far partire metodo che chiede cose a utente in continuazione

    }

    @Override
    public void setUpGame(boolean newGame) {

    }

    @Override
    public void askLeaderCards() throws FileNotFoundException {

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

    }

    @Override
    public void showLadderBoard(Map<Player, Integer> scores) throws FileNotFoundException {

    }
}
