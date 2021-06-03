package it.polimi.ingsw.client.GUI;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.ImagePattern;

public class InitialSceneController extends FXMLController {

    @FXML
    private AnchorPane root;
    @FXML
    private ImageView sfondo;
    @FXML
    private TextField textField;
    @FXML
    private Button submitNickButton;
    @FXML
    private Label messageLabel;


    @FXML
    public void initialize(){

        BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(sfondo.getImage()), CornerRadii.EMPTY, Insets.EMPTY);
        root.setBackground(new Background(backgroundFill));

        //submitNumButton.setVisible(false);


    }
    public  void updateWidthConstraints(double width) {

        AnchorPane.setRightAnchor(submitNickButton, width * 0.45 );
        AnchorPane.setRightAnchor(textField, width * 0.35);
        AnchorPane.setRightAnchor(messageLabel,width * 0.40);

    }

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

            serverHandler.setUpConnection();
            messageLabel.setVisible(true);
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
