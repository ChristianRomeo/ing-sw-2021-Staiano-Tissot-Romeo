package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.ClientModel;
import it.polimi.ingsw.client.ConnectionHandler;
import it.polimi.ingsw.controller.View;
import it.polimi.ingsw.model.DevelopmentCard;
import it.polimi.ingsw.model.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.media.MediaPlayer;
//to change game font
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * @see javafx.application.Application
 * GUIDE
 * @link http://tutorials.jenkov.com/javafx/index.html
 * @link https://www.javatpoint.com/media-with-javafx
 */
public class GuiView extends Application implements View {
    private ClientModel clientModel;

    //qui ci vanno tutte le varie scene del gioco ed i relativi controller ( home, menu, caricamento, fine...)
    private final Map<String, Scene> scenes = new HashMap<>();
    //private final Map<String, controllerFx> controller = new HashMap<>();

    private static Scene currentScene;
    private static Stage currentStage;
    //per riprodurre musica in background
    private MediaPlayer mediaPlayer;

    private final static Logger logger = Logger.getLogger(GuiView.class.getName());

    public static void main(String[] args) {
        launch();
    }

    /**
     * @see Application#start(Stage)
     */
    @Override
    public void start(Stage stage) throws IOException {

        currentStage = stage;
        currentStage.setTitle("maestri del rinascimento");
        currentScene = new Scene(loadFXML("primary"));
        currentStage.setScene(currentScene);
        currentStage.show();
    }

    static void setRoot(String fxml) throws IOException {
        currentStage.getScene().setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        return FXMLLoader.load(Objects.requireNonNull(GuiView.class.getResource("/Graphics/" + fxml + ".fxml")));
    }


    public void stop(){
    }

    private void drawCards(GraphicsContext gc) {

    }

    /**
     * Sets the serverHandler
     *
     * @param connectionHandler The handler of the server connection
     */
    @Override
    public void setConnectionHandler(ConnectionHandler connectionHandler) {

    }

    /**
     * Interface launcher. set connection CAN AGGREGATE
     */
    @Override
    public void launcher() {

    }

    @Override
    public ClientModel getClientModel() {
        return null;
    }

    @Override
    public int askNumPlayer() {
        return 0;
    }

    /**
     * Asks nickname and if it's a new game the number of players for the game and notify the information to the serverHandler
     *
     * @param newGame True if the it is a new game, otherwise false
     */
    @Override
    public void setUpGame(boolean newGame) {

    }

    /**
     * Asks the game cards
     * @return the set of chosen cards
     */
    @Override
    public TreeSet<Integer> askChoiceLeaderCards() {

        return null;
    }

    /**
     * Asks the index of a leader card
     */
    @Override
    public int askLeaderCard() {
        return 0;
    }

    /**
     * Shows the developmentCardBoard matrix
     *
     * @param cards All the remaining cards
     */
    @Override
    public void showDevelopmentCards(List<DevelopmentCard> cards) {

    }

    /**
     * Shows the Players in the game
     *
     * @param playerList All the players
     */
    @Override
    public void showPlayersBoard(List<Player> playerList) {

    }

    /**
     * Show the activated leaderCards of others players
     *
     * @param playerList LeaderCards activated
     */
    @Override
    public void showPlayersLeaderCards(List<Player> playerList) {

    }

    /**
     * Shows the faith track
     *
     * @param trackInfo The track's player position ([0] first, [1] second...)
     */
    @Override
    public void showFaithTrack(List<Integer> trackInfo) {

    }

    /**
     * Asks what the player want to do
     *
     * @param roundActions The possible actions    ([0]market, [1] prod, [2]warehouse)
     */
    @Override
    public void askAction(List<Integer> roundActions) {

    }

    /**
     * Shows the LadderBoard of the match
     *
     */
    @Override
    public void showLadderBoard() {

    }

    /**
     * shows a message to the user.
     *
     * @param message showed
     * @param cls if you want to clear the screen
     */
    @Override
    public void showMessage(String message, boolean cls) {

    }
}
