package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.ClientModel;
import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.controller.Configs;
import it.polimi.ingsw.controller.Events.EndGameEventS2C;
import it.polimi.ingsw.controller.View;
import it.polimi.ingsw.model.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
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
        currentStage.setTitle("Masters Of Renaissance");

        currentStage.setScene(getScene("initialScene"));

        currentFXMLController = getSceneController("initialScene");



        currentStage.setResizable(false);
        currentStage.sizeToScene();

        currentStage.getIcons().add(new Image(Objects.requireNonNull(GuiView.class.getClassLoader().getResourceAsStream("gameicon.png"))));


        new AudioClip(Objects.requireNonNull(GuiView.class.getClassLoader().getResource("music.mp3")).toExternalForm()).play();


        currentStage.show();

        currentStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
        currentStage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()){
                stage.close();
                Platform.exit();
                System.exit(0);
            }
        });
                //System.out.println(askNumPlayers());
                //currentScene.getStylesheets().add("/style.css");
    }


    //ritorna il loader per quella scena, poi lo usiamo per ottenere la scena e il suo controller
    private FXMLLoader getFXMLLoader(String fxml){
        return new FXMLLoader(getClass().getResource("/Graphics/" + fxml + ".fxml"));
    }

    //sto metodo carica tutte le scene e i loro controller, dai file fxml
    public void loadScenes(){
        try{

            loadScene("initialScene");
            loadScene("pregameScene");
            loadScene("gameScene");
            loadScene("leaderActionScene");
            loadScene("buyCardScene");
            loadScene("activateProductionScene");
            loadScene("useMarketScene");
            loadScene("endGameScene");

        }catch (IOException e){
            logger.warning("Errore nel caricare file fxml"+e);
        }

    }

    //carica una scena dal file fxml, è solo un metodo helper di loadScenes
    private void loadScene(String sceneName) throws IOException{
        FXMLLoader sceneLoader = getFXMLLoader(sceneName);
        scenes.put(sceneName,new Scene(sceneLoader.load()));
        FXMLController sceneController = sceneLoader.getController();
        sceneController.setClientModel(clientModel);
        sceneController.setServerHandler(serverHandler);
        sceneController.setGuiView(this);
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

    @Override
    public ClientModel getClientModel() {
        return clientModel;
    }

    @Override
    public int askNumPlayers() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Seleziona il numero di giocatori.");

        //DialogPane dialogPane = alert.getDialogPane();
        //StackPane stackPane = new StackPane(new ImageView(new Image(Objects.requireNonNull(GuiView.class.getClassLoader().getResourceAsStream("gameicon.png")))));
        //stackPane.setAlignment(Pos.CENTER);
        //dialogPane.setGraphic(stackPane);

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
                                    initialSceneController.askNumPlayers();
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

    //tu gli dai una leader card e questo metodo ti ritorna la sua immagine
    public static Image getLeaderCardImage(LeaderCard leaderCard){
        if(leaderCard!=null){
            return new Image(String.valueOf(GuiView.class.getResource("/Cards/" + "lead" + leaderCard.getId() +  ".png")));
        }
        return null;
    }

    //tu gli dai una development card e questo metodo ti ritorna la sua immagine
    public static Image getDevelopmentCardImage(DevelopmentCard developmentCard){
        if(developmentCard!=null){
            return new Image(String.valueOf(GuiView.class.getResource("/Cards/" + "dev" + developmentCard.getId() +  ".png")));
        }else{
            return null;
        }
    }

    //ritorna l'immagine (piccola) della risorsa passata
    public static Image getResourceImage(Resource resource){
        if(resource!=null){
            String resourceName = resource.toString().toLowerCase();
            return new Image(String.valueOf(GuiView.class.getResource("/"+resourceName+"Picc.png")));
        }else{
            return null;
        }
    }

    //ritorna l'immagine della biglia passata
    public static Image getMarbleImage(MarbleColor marble){
        if(marble!=null){
            String marbleColorName = marble.toString().toLowerCase();
            return new Image(String.valueOf(GuiView.class.getResource("/"+marbleColorName+"Marble.png")));
        }else{
            return null;
        }
    }

    //ritorna l'immagine della pope tile passata, la 0,1 o 2 a seconda dell'index passato

    /**
     *  It returns the image of the pope tile passed.
     * @param popeTile the status of the pope tile
     * @param popeTileIndex the index (1,2,3) of the pope tile
     * @return the image
     */
    public static Image getPopeTileImage(PopeFavorTileStatus popeTile, int popeTileIndex){
        popeTileIndex= popeTileIndex+1; //perchè nel nome delle immagini sta 2,3,4
        if(popeTile!=null){
            if(popeTile == PopeFavorTileStatus.INACTIVE){
                return new Image(String.valueOf(GuiView.class.getResource("/papale"+popeTileIndex+".png")));
            }
            if(popeTile == PopeFavorTileStatus.ACTIVE){
                return new Image(String.valueOf(GuiView.class.getResource("/papale"+popeTileIndex+"ON.png")));
            }
            if(popeTile == PopeFavorTileStatus.DISCARDED){
                return null; // non devi settare un'immagine quindi tutto ok
            }
        }
        return null;
    }

    //ritorna l'immagine del solo action passato
    public static Image getSoloActionImage(SoloAction soloAction){
        if(soloAction!=null){
            if(soloAction.getType() == SoloActionType.MOVEONEANDSHUFFLE){
                return new Image(String.valueOf(GuiView.class.getResource("/moveoneandshuffle.png")));
            }
            if(soloAction.getType() == SoloActionType.MOVETWO){
                return new Image(String.valueOf(GuiView.class.getResource("/movetwo.png")));
            }
            if(soloAction.getType() == SoloActionType.DISCARDTWOCARDS){
                String cardTypeName = soloAction.getDiscardedCardsType().toString().toLowerCase();
                return new Image(String.valueOf(GuiView.class.getResource("/discard2"+cardTypeName+".png")));
            }
        }
        return null;
    }

    public void launcher() {}

    public void stop(){
    }

    /**
     * chiede se vuole fare una nuova partita, ciò dovrebbe avvenire solo in caso di errori di connessione.
     * se arriva alla fine della partita senza problemi lo chiede in altro modo.
     */
    @Override
    public void askNewGame() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Connection Error! The application will be closed.");
            ButtonType buttonTypeOne = new ButtonType("Exit");
            ButtonType buttonTypeTwo = new ButtonType("Play Again");

            alert.getButtonTypes().setAll(buttonTypeOne,buttonTypeTwo);
            Optional<ButtonType> result = alert.showAndWait();

            if(result.get()==buttonTypeOne){
                System.exit(0);
                Platform.exit();
            }else{
                currentStage.close();
                start( new Stage());
                //dovrebbe essere impossibile;
            }
        });
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
    public void showFaithTracks() {

    }

    /**
     * Shows a player's overall state(Warehouse, Strongbox, CardBoard, LeaderCards Faith Track position)
     *
     */
    @Override
    public void showMyState() {

    }

    /**
     * Shows all players overall state(Warehouse, Strongbox, CardBoard, LeaderCards Faith Track position)
     * except for the player who called it
     */
    @Override
    public void showOthersState() {

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
     * Shows a player Faith Track
     * @param faithTrackPosition is the player's faith track position
     * @param showLorenzoFT is used to check if the method has to show Lorenzo's Faith Track position or not
     */
    @Override
    public void showFaithTrack(int faithTrackPosition, boolean showLorenzoFT) {

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
