package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.controller.Events.LeaderCardActionEvent;
import it.polimi.ingsw.model.LeaderCard;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class LeaderActionSceneController extends FXMLController{

    @FXML
    private Button firstLeaderCardButton;
    @FXML
    private Button secondLeaderCardButton;
    @FXML
    private Button activateLeaderButton;
    @FXML
    private Button discardLeaderButton;
    @FXML
    private ImageView chooseLeaderCardImage1;
    @FXML
    private ImageView chooseLeaderCardImage2;

    private int selectedLeaderCard;

    public void updateScene(){
        selectedLeaderCard=-1;
        firstLeaderCardButton.setDisable(false);
        secondLeaderCardButton.setDisable(false);
        firstLeaderCardButton.setStyle("-fx-background-color: white;");
        secondLeaderCardButton.setStyle("-fx-background-color: white;");
        List<LeaderCard> myLeaderCards = clientModel.getPlayerLeaderCards(clientModel.getMyNickname());
        if(myLeaderCards.get(0).isActivated()||myLeaderCards.get(0).isDiscarded()){
            chooseLeaderCardImage1.setImage(new Image(String.valueOf(GuiView.class.getResource("/Cards/backleader.png"))));
        }else{
            chooseLeaderCardImage1.setImage(GuiView.getLeaderCardImage(myLeaderCards.get(0)));
        }
        if(myLeaderCards.get(1).isActivated()||myLeaderCards.get(1).isDiscarded()){
            chooseLeaderCardImage2.setImage(new Image(String.valueOf(GuiView.class.getResource("/Cards/backleader.png"))));
        }else{
            chooseLeaderCardImage2.setImage(GuiView.getLeaderCardImage(myLeaderCards.get(1)));
        }
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
            //leaderActionPane.setVisible(false);
            guiView.getSceneController("gameScene").updateScene();
            guiView.setCurrentScene("gameScene");
        }
    }
    @FXML
    public void discardLeaderCard(){
        if(selectedLeaderCard!=-1){
            serverHandler.send(new LeaderCardActionEvent('d',selectedLeaderCard));
            //leaderActionPane.setVisible(false);
            guiView.getSceneController("gameScene").updateScene();
            guiView.setCurrentScene("gameScene");
        }
    }
}
