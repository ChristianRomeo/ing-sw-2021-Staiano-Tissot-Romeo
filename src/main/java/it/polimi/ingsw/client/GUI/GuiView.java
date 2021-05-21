package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.ClientModel;
import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.controller.Events.EndGameEventS2C;
import it.polimi.ingsw.controller.View;
import it.polimi.ingsw.model.*;
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
        //currentScene.getStylesheets().add("/style.css");
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
     * @param serverHandler The handler of the server connection
     */
    @Override
    public void setConnectionHandler(ServerHandler serverHandler) {

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
     * Asks the game cards
     * @return the set of chosen cards
     */
    @Override
    public SameTypePair<Integer> askChoiceLeaderCards() {

        return null;
    }

    /**
     * Asks the index of a leader card
     */
    @Override
    public List<Integer> askLeaderCard() {
        return new ArrayList<>();
    }

    /**
     * Shows the developmentCardBoard matrix
     *
     */
    @Override
    public void showDevelopmentCardBoard() {

    }

    /**
     * Shows the Players in the game
     *
     */
    @Override
    public void showPlayersBoard() {

    }

    /**
     * Show the activated leaderCards of others players
     *
     */
    @Override
    public void showPlayersLeaderCards() {

    }

    /**
     * Shows the faith track
     *
     */
    @Override
    public void showFaithTrack() {

    }


    /**
     * Shows the LadderBoard of the match
     *
     */
    @Override
    public void showLadderBoard(EndGameEventS2C endGameEvent) {

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

    /**
     * Shows an error message
     *
     * @param errorMessage The message to be shown
     */
    @Override
    public void showErrorMessage(String errorMessage) {

    }

    @Override
    public void showLorenzoTurn(SoloAction soloAction){

    }
    /**
     * Asks the player what action should engage
     */
    @Override
    public void askActions() {

    }

    /**
     * asks the nickname
     *
     * @return the nickname
     */
    @Override
    public String askNickname() {
        return null;
    }

    /**
     * This method asks the user a position of a development card in the development card board
     *
     * @return the position (row,col)
     */
    @Override
    public SameTypePair<Integer> askDevelopmentCard() {
        return null;
    }

    /**
     * asks in what pile of production should the bought card be inserted
     *
     * @return the pile number
     */
    @Override
    public int askCardPile() {
        return 0;
    }

    /**
     * Shows the market
     */
    @Override
    public void showMarket() {

    }

    /**
     * shows a player LeaderCard
     *
     * @param card to show
     */
    @Override
    public void showLeaderCard(LeaderCard card) {

    }

    /**
     * shows a player card
     *
     * @param card to show
     */
    @Override
    public void showCard(DevelopmentCard card) {

    }

    /**
     * shows a player warehouse
     *
     * @param warehouse to show
     */
    @Override
    public void showWarehouse(PlayerWarehouse warehouse) {

    }

    /**
     * shows a player Strongbox
     *
     * @param strongbox to show
     */
    @Override
    public void showStrongbox(Map<Resource,Integer> strongbox) {

    }

    /**
     * Notify all that a player has been disconnected (and the game has ended ?FA)
     *
     * @param disconnected The nickname of the disconnected player
     */
    @Override
    public void showDisconnectionMessage(String disconnected) {

    }

    /**
     * Notify whose turn is
     *
     * @param currentNickname The nickname of whom taking the turn
     */
    @Override
    public void showTurn(String currentNickname) {

    }

    /**
     * Notify that the game has ended and the winning status
     *
     * @param winner The nickname of the winner
     * @param youWon True if the player has win
     */
    @Override
    public void showEndGameMessage(String winner, boolean youWon) {

    }
}
