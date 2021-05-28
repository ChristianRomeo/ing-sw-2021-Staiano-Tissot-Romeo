package it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.controller.Events.LeaderCardActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class GameSceneController extends FXMLController {

    //---- cose per la schermata principale---
    @FXML
    private Label messageLabel;
    @FXML
    private Button leaderActionButton;

    //----- cose per pane azione leader -----
    @FXML
    private AnchorPane leaderActionPane;
    @FXML
    private Button firstLeaderCardButton;
    @FXML
    private Button secondLeaderCardButton;
    @FXML
    private Button activateLeaderButton;
    @FXML
    private Button discardLeaderButton;

    private int selectedLeaderCard;

    @Override
    public void updateScene(){
        if(clientModel.isCurrentPlayer()){
            leaderActionButton.setVisible(true);
        }else{
            leaderActionButton.setVisible(false);
        }
    }

    @Override
    public void showMessage(String message){
        messageLabel.setText(message);
    }

    //---- cose per la schermata principale---

    @FXML
    public void leaderAction(){
        initializeLeaderPanel();
        leaderActionPane.setVisible(true);
    }

    //----- cose per pane azione leader -----

    private void initializeLeaderPanel(){
        selectedLeaderCard=-1;
        firstLeaderCardButton.setDisable(false);
        secondLeaderCardButton.setDisable(false);
        firstLeaderCardButton.setStyle("-fx-background-color: white;");
        secondLeaderCardButton.setStyle("-fx-background-color: white;");
    }
    @FXML
    public void selectFirstLeaderCard(){
        selectedLeaderCard=0;
        firstLeaderCardButton.setDisable(true);
        secondLeaderCardButton.setDisable(true);
        firstLeaderCardButton.setStyle("-fx-background-color: #ff0000; ");
    }
    @FXML
    public void selectSecondLeaderCard(){
        selectedLeaderCard=1;
        firstLeaderCardButton.setDisable(true);
        secondLeaderCardButton.setDisable(true);
        secondLeaderCardButton.setStyle("-fx-background-color: #ff0000; ");
    }
    @FXML
    public void activateLeaderCard(){
        if(selectedLeaderCard!=-1){
            serverHandler.send(new LeaderCardActionEvent('a',selectedLeaderCard));
            leaderActionPane.setVisible(false);
        }
    }
    @FXML
    public void discardLeaderCard(){
        if(selectedLeaderCard!=-1){
            serverHandler.send(new LeaderCardActionEvent('d',selectedLeaderCard));
            leaderActionPane.setVisible(false);
        }
    }



}
