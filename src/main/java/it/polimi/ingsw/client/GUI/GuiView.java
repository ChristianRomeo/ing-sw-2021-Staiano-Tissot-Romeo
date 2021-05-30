package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.ClientModel;
import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.controller.Configs;
import it.polimi.ingsw.controller.Events.EndGameEventS2C;
import it.polimi.ingsw.controller.View;
import it.polimi.ingsw.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

//to change game font

/**
 * @see javafx.application.Application
 * GUIDE
 * @link http://tutorials.jenkov.com/javafx/index.html
 * @link https://www.javatpoint.com/media-with-javafx
 */
public class GuiView extends Application implements View {
    private final ClientModel clientModel;
    private static ServerHandler serverHandler;

    //qui ci vanno tutte le varie scene del gioco ed i relativi controller ( home, menu, caricamento, fine...)
    private final Map<String, Scene> scenes = new HashMap<>();
    private final Map<String, FXMLController> controllers = new HashMap<>(); //qua dentro sta nomescena->controller

    private static Scene currentScene;
    private static Stage currentStage;
    private FXMLController currentFXMLController;

    //per riprodurre musica in background
    private MediaPlayer mediaPlayer;
    private final static Logger logger = Logger.getLogger(GuiView.class.getName());
    private static Configs conf;

    public GuiView(){
        clientModel = new ClientModel(1);
    }

    public static void main(Configs args) {
        conf=args;
        launch();
    }


    /**
     * @see Application#start(Stage)
     */
    @Override
    public void start(Stage stage) {

        serverHandler = new ServerHandler(this, conf);

        loadScenes();
        currentStage = stage;
        currentStage.setTitle("maestri del rinascimento");

        currentStage.setScene(getScene("initialScene"));
        currentFXMLController = getSceneController("initialScene");

        currentStage.show();

        //System.out.println(askNumPlayer());

        //currentScene.getStylesheets().add("/style.css");
    }

    static void setRoot(String fxml) throws IOException {
        currentStage.getScene().setRoot(loadFXML(fxml));
    }

    //credo metodo da togliere
    private static Parent loadFXML(String fxml) throws IOException {
        return FXMLLoader.load(Objects.requireNonNull(GuiView.class.getResource("/Graphics/" + fxml + ".fxml")));
    }

    //ritorna il loader per quella scena, poi lo usiamo per ottenere la scena e il suo controller
    private FXMLLoader getFXMLLoader(String fxml){
        return new FXMLLoader(getClass().getResource("/Graphics/" + fxml + ".fxml"));
    }


    public void stop(){
    }

    private void drawCards(GraphicsContext gc) {

    }


    //sto metodo carica tutte le scene e i loro controller, dai file fxml
    public void loadScenes(){
        try{
            /*
            FXMLController sceneController;

            FXMLLoader loaderInitialScene = getFXMLLoader("initialScene");
            scenes.put("initialScene",new Scene(loaderInitialScene.load()));
            sceneController = loaderInitialScene.getController();
            sceneController.setClientModel(clientModel);
            sceneController.setServerHandler(serverHandler);
            controllers.put("initialScene",sceneController);

            FXMLLoader loaderPregameScene = getFXMLLoader("pregameScene");
            scenes.put("pregameScene",new Scene(loaderPregameScene.load()));
            sceneController = loaderPregameScene.getController();
            sceneController.setClientModel(clientModel);
            sceneController.setServerHandler(serverHandler);
            controllers.put("pregameScene",sceneController);
            */

            loadScene("initialScene");
            loadScene("pregameScene");
            loadScene("gameScene");


        }catch (IOException e){
            System.out.println("Errore nel caricare file fxml");
        }

    }

    //carica una scena dal file fxml, è solo un metodo helper di loadScenes
    private void loadScene(String sceneName) throws IOException{
        FXMLLoader sceneLoader = getFXMLLoader(sceneName);
        scenes.put(sceneName,new Scene(sceneLoader.load()));
        FXMLController sceneController = sceneLoader.getController();
        sceneController.setClientModel(clientModel);
        sceneController.setServerHandler(serverHandler);
        controllers.put(sceneName,sceneController);
    }

    public Scene getScene(String sceneName){
        return scenes.get(sceneName);
    }

    public FXMLController getSceneController(String sceneName){
        return controllers.get(sceneName);
    }

    //setta la scena di cui da il nome come la corrente (cioè quella mostrata)
    public void setCurrentScene(String sceneName){
        currentStage.setScene(getScene(sceneName));
        currentScene = getScene(sceneName);
        currentFXMLController = getSceneController(sceneName);
    }

    public Scene getCurrentScene(){
        return currentScene;
    }

    public FXMLController getCurrentSceneController(){
        return currentFXMLController;
    }

    /**
     * Sets the serverHandler
     *
     * @param serverHandler The handler of the server connection
     */
    @Override
    public void setConnectionHandler(ServerHandler serverHandler) {
        GuiView.serverHandler = serverHandler;
    }


    public void launcher() {}

    @Override
    public ClientModel getClientModel() {
        return clientModel;
    }

    @Override
    public int askNumPlayer() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Seleziona il numero di giocatori.");
        ButtonType buttonTypeOne = new ButtonType("One");
        ButtonType buttonTypeTwo = new ButtonType("Two");
        ButtonType buttonTypeThree = new ButtonType("Three");
        ButtonType buttonTypeFour = new ButtonType("Four");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeFour);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get()==buttonTypeOne){
            return 1;
        }
        if(result.get()==buttonTypeTwo){
            return 2;
        }
        if(result.get()==buttonTypeThree){
            return 3;
        }
        if(result.get()==buttonTypeFour){
            return 4;
        }else{
            return -1; //dovrebbe essere impossibile
        }
        /* //cosi non va
        InitialSceneController initialSceneController = (InitialSceneController) currentFXMLController;
        Platform.runLater(new Runnable() {
                              @Override public void run() {
                                    initialSceneController.askNumPLayer();
                              }
        });
        synchronized (initialSceneController){
            while (initialSceneController.getNumPlayers()==0){
                try {
                    initialSceneController.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return initialSceneController.getNumPlayers();*/
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
     */
    @Override
    public void showMessage(String message) {

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

}
