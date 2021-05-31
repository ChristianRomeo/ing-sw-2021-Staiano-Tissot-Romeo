package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.concurrent.Task;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class InitialSceneController extends FXMLController {
   // private ClientModel clientModel;
   // private ServerHandler serverHandler;


    //private int numPlayers=0;

    @FXML
    private Pane pane;
    @FXML
    private TextField textField;
    @FXML
    private Button submitNickButton;
    @FXML
    private Label messageLabel;
    @FXML
    private Label upperLabel;


    @FXML
    public void initialize(){
        textField.setText("Nickname here");
        //submitNumButton.setVisible(false);


    }
    @FXML
    public void submitNick(){

        if( textField.getText().matches("^[\\p{Alnum}\\s._-]+$")){
            clientModel.setMyNickname(textField.getText());
            messageLabel.setVisible(false);
            //devo far cambiare la scena e metterla tipo attendi
            textField.setVisible(false);
            submitNickButton.setVisible(false);
            upperLabel.setText("Nickname inserito! Attendi...");

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
        }
        else{
            messageLabel.setText("Nickname invalido!!! ");
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
    */


/*
    public void askNumPLayer(){
        upperLabel.setText("Inserisci il numero giocatori (1-4): ");
        textField.clear();
        upperLabel.setVisible(true);
        textField.setVisible(true);
        submitNumButton.setVisible(true);
    }*/
/*
    public synchronized int getNumPlayers(){
        return numPlayers;
    }*/
}
