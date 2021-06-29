package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.ClientModel;
import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.controller.Configs;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

//to change game font
/**
 * implementation of the View interface for the GUI
 */
/**
 * @see javafx.application.Application
 */
public class GuiView extends Application implements View {
    private final ClientModel clientModel;
    private static ServerHandler serverHandler;
    private final AudioClip sound = new AudioClip(Objects.requireNonNull(GuiView.class.getClassLoader().getResource("song.mp3")).toExternalForm());

    //qui ci vanno tutte le varie scene del gioco e i relativi controller (home, menu, caricamento, fine...)
    private final Map<String, Scene> scenes = new HashMap<>();
    private final Map<String, FXMLController> controllers = new HashMap<>(); //qua dentro sta nomescena->controller

    private static Stage currentStage;
    private FXMLController currentFXMLController;

    //per riprodurre musica in background
    private final static Logger logger = Logger.getLogger(GuiView.class.getName());
    private static Configs conf;

    public GuiView(){
        clientModel = new ClientModel();
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
        sound.setCycleCount(AudioClip.INDEFINITE);
        sound.play();
        serverHandler = new ServerHandler(this, conf);

        loadScenes();
        currentStage = stage;
        currentStage.setTitle("Masters Of Renaissance");

        currentStage.setScene(getScene("initialScene"));

        currentFXMLController = getSceneController("initialScene");

        currentStage.setResizable(false);
        currentStage.sizeToScene();

        currentStage.getIcons().add(new Image(Objects.requireNonNull(GuiView.class.getClassLoader().getResourceAsStream("gameicon.png"))));

        currentStage.show();

        currentStage.setOnCloseRequest(t -> stop());
        currentStage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()){
                stage.close();
                stop();
            }
        });
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

    /**
     * getter of the scene
     * @param sceneName is the name of the scene to be get
     * @return the scene
     */
    public Scene getScene(String sceneName){
        return scenes.get(sceneName);
    }

    /**
     * getter of the scene controller
     * @param sceneName is the name of the scene to get the its controller
     * @return the controller of the scene
     */
    public FXMLController getSceneController(String sceneName){
        return controllers.get(sceneName);
    }

    /**
     * Sets the serverHandler
     * @param sceneName is the name of the scene to be set to current one
     */
    public void setCurrentScene(String sceneName){
        currentStage.setScene(getScene(sceneName));
        Scene currentScene = getScene(sceneName);
        currentFXMLController = getSceneController(sceneName);
    }

    /**
     * @return the current scene controller
     */
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

    /**
     * @return the client model
     */
    @Override
    public ClientModel getClientModel() {
        return clientModel;
    }

    /**
     * asks the wantee number of players for the game
     * @return the entered number of players
     */
    @Override
    public int askNumPlayers() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Seleziona il numero di giocatori.");

        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("gameicon.png"));

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

    }

    /**
     * getter of a Leader card image
     * @param leaderCard is the Leader card to be get
     * @return the Leader card image
     */
    public static Image getLeaderCardImage(LeaderCard leaderCard){
        if(leaderCard!=null){
            return new Image(String.valueOf(GuiView.class.getResource("/Cards/" + "lead" + leaderCard.getId() +  ".png")));
        }
        return null;
    }

    /**
     * getter of a Development card image
     * @param developmentCard is the development card to be get
     * @return the Development card image
     */
    public static Image getDevelopmentCardImage(DevelopmentCard developmentCard){
        if(developmentCard!=null){
            return new Image(String.valueOf(GuiView.class.getResource("/Cards/" + "dev" + developmentCard.getId() +  ".png")));
        }else{
            return null;
        }
    }

    /**
     * getter of a resource image
     * @param resource is the resource to be get
     * @return the resource image
     */
    public static Image getResourceImage(Resource resource){
        if(resource!=null){
            String resourceName = resource.toString().toLowerCase();
            return new Image(String.valueOf(GuiView.class.getResource("/"+resourceName+"Picc.png")));
        }else{
            return null;
        }
    }

    /**
     * getter of a marble image
     * @param marble is the resource to be get
     * @return the marble image
     */
    public static Image getMarbleImage(MarbleColor marble){
        if(marble!=null){
            String marbleColorName = marble.toString().toLowerCase();
            return new Image(String.valueOf(GuiView.class.getResource("/"+marbleColorName+"Marble.png")));
        }else{
            return null;
        }
    }

    /**
     *getter of a Pope tile's image
     * @param popeTile the status of the Pope tile
     * @param popeTileIndex the index (1,2,3) of the Pope tile
     * @return the Pope tile image
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

    /**
     * getter of a Solo action image
     * @param soloAction is the Solo action to be get
     * @return the solo action image
     */
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
        Platform.exit();
        System.exit(0);
    }

    /**
     * asks for a new game, only if some connection error happened
     * if the game ends successfully, it will be asked in a different method
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
                sound.stop();
                System.exit(0);
                Platform.exit();
            }else{
                currentStage.close();
                sound.stop();
                start( new Stage());
            }
        });
    }

    /**
     * Notify all that a player has been disconnected (and the game has ended ?FA)
     *
     * @param disconnected The nickname of the disconnected player
     */
    @Override
    public void showDisconnectionMessage(String disconnected) {

    }

}
