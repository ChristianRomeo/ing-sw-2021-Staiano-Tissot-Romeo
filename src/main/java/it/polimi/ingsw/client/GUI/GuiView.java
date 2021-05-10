package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.ActionHandler;
import it.polimi.ingsw.client.CliView;
import it.polimi.ingsw.client.ClientModel;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
//to change game font
import javafx.scene.text.Font;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @see javafx.application.Application
 * GUIDE
 * @link http://tutorials.jenkov.com/javafx/index.html
 * @link https://www.javatpoint.com/media-with-javafx
 */
public class GuiView extends Application {
    private ClientModel clientModel;

    //qui ci vanno tutte le varie scene del gioco ed i relativi controller ( home, menu, caricamento, fine...)
    private final Map<String, Scene> scenes = new HashMap<>();
    //private final Map<String, controllerFx> controller = new HashMap<>();

    private static Scene currentScene;
    private static Stage currentStage;
    //per riprodurre musica in background
    private MediaPlayer mediaPlayer;

    private final static Logger logger = Logger.getLogger(GuiView.class.getName());
    private SwingNode swingNode;

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

    //prova
    private void createAndSetSwingContent() {
        SwingUtilities.invokeLater(() -> swingNode.setContent(new JButton("Click me!")));
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
}
