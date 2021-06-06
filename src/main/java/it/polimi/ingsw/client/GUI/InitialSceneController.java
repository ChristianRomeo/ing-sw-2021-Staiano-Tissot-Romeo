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


    @FXML
    public void initialize(){

        BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(new Image(Objects.requireNonNull(InitialSceneController.class.getClassLoader().getResourceAsStream("MoRWall.png")))), CornerRadii.EMPTY, Insets.EMPTY);
        root.setBackground(new Background(backgroundFill));
        textField.setFocusTraversable(false);
        //submitNumButton.setVisible(false);
        credits.setText("Powered by CG24 \nE.Staiano T.Tissot C.Romeo");
        credits.setFont(Font.font("System", FontWeight.EXTRA_BOLD, 14));

    }
    /*public  void updateWidthConstraints(double width) {

        AnchorPane.setRightAnchor(submitNickButton, width * 0.35 + 55);
        AnchorPane.setLeftAnchor(submitNickButton, width * 0.35 + 55);
        AnchorPane.setRightAnchor(textField, width * 0.35);
        AnchorPane.setLeftAnchor(textField, width * 0.35);
        AnchorPane.setRightAnchor(messageLabel,width * 0.40);
        AnchorPane.setLeftAnchor(messageLabel,width * 0.40);
        AnchorPane.setRightAnchor(credits,width * 0.65);
        AnchorPane.setLeftAnchor(credits,width * 0.05);


    }
    public  void updateHeightConstraints(double height ) {

        AnchorPane.setTopAnchor(submitNickButton,  height * 0.45 );
        AnchorPane.setTopAnchor(textField, height  * 0.35);
        AnchorPane.setTopAnchor(messageLabel,height  * 0.40);
        AnchorPane.setTopAnchor(credits,height  * 0.02);

    }*/

    @FXML
    public void submitNick(){

        if( textField.getText().matches("^[\\p{Alnum}\\s._-]+$")){
            clientModel.setMyNickname(textField.getText());

            //devo far cambiare la scena e metterla tipo attendi
            textField.setVisible(false);
            submitNickButton.setVisible(false);


            /* non va con il task
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    serverHandler.setUpConnection();
                    return null;
                }
            };
            (new Thread(task)).start();*/
            messageLabel.setVisible(true);
            curtain.setVisible(true);
            serverHandler.setUpConnection();


        }
        else{
            textField.clear();
            textField.setPromptText("Nickname invalido!!! ");
            textField.setEditable(true);
        }

       // chosenNick.setText("hey "+textField.getText()+" ciaone");//prova
    }

    /*
    @FXML
    public synchronized void submitNum(){
        int num;
        try{
            num = Integer.parseInt(textField.getText());
        }catch(NumberFormatException e){
            messageLabel.setText("Numero giocatori invalido!!! ");
            return;
        }
        if(num<0 || num>4){
            messageLabel.setText("Numero giocatori invalido!!! ");
        }else{
            numPlayers= num;
            this.notifyAll();
        }

    }

    public void askNumPLayers(){
        upperLabel.setText("Inserisci il numero giocatori (1-4): ");
        textField.clear();
        upperLabel.setVisible(true);
        textField.setVisible(true);
        submitNumButton.setVisible(true);
    }

    public synchronized int getNumPlayers(){
        return numPlayers;
    }*/
}
