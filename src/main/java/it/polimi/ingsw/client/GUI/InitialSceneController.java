package it.polimi.ingsw.client.GUI;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Objects;

public class InitialSceneController extends FXMLController {

    @FXML
    private ImageView curtain;
    @FXML
    private Label credits;
    @FXML
    private AnchorPane root;
    @FXML
    private TextField textField;
    @FXML
    private Button submitNickButton;
    @FXML
    private Label messageLabel;

    /**
     * sets initialScene background and label with credits
     */
    @FXML
    public void initialize(){

        BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(new Image(Objects.requireNonNull(InitialSceneController.class.getClassLoader().getResourceAsStream("MoRWall.png")))), CornerRadii.EMPTY, Insets.EMPTY);
        root.setBackground(new Background(backgroundFill));
        textField.setFocusTraversable(false);
        credits.setFont(Font.font("System", FontWeight.EXTRA_BOLD, 14));

    }

    /**
     * allows the player to insert his nickname
     */
    @FXML
    public void submitNick(){

        if( textField.getText().matches("^[\\p{Alnum}\\s._-]+$")){
            clientModel.setMyNickname(textField.getText());

            //devo far cambiare la scena e metterla tipo attendi
            textField.setVisible(false);
            submitNickButton.setVisible(false);
            messageLabel.setVisible(true);
            curtain.setVisible(true);
            serverHandler.setUpConnection();


        }
        else{
            textField.clear();
            textField.setPromptText("Invalid nickname! Please try again");
            textField.setEditable(true);
        }

    }
}
