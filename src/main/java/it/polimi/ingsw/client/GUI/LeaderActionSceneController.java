package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.controller.Events.LeaderCardActionEvent;
import it.polimi.ingsw.model.LeaderCard;
import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.List;

public class LeaderActionSceneController extends FXMLController{

    @FXML
    private ImageView chooseLeaderCardImage1;
    @FXML
    private ImageView chooseLeaderCardImage2;

    private int selectedLeaderCard;

    public void updateScene(){
        selectedLeaderCard=-1;
        chooseLeaderCardImage1.setDisable(false);
        chooseLeaderCardImage2.setDisable(false);

        if (clientModel.getPlayerLeaderCards(clientModel.getMyNickname()).get(0).isDiscarded())
            chooseLeaderCardImage1.setDisable(true);
        if (clientModel.getPlayerLeaderCards(clientModel.getMyNickname()).get(1).isDiscarded())
            chooseLeaderCardImage2.setDisable(true);

        List<LeaderCard> myLeaderCards = clientModel.getPlayerLeaderCards(clientModel.getMyNickname());
        if(myLeaderCards.get(0).isActivated()||myLeaderCards.get(0).isDiscarded())
            chooseLeaderCardImage1.setImage(new Image(String.valueOf(GuiView.class.getResource("/Cards/backleader.png"))));
        else
            chooseLeaderCardImage1.setImage(GuiView.getLeaderCardImage(myLeaderCards.get(0)));

        if(myLeaderCards.get(1).isActivated()||myLeaderCards.get(1).isDiscarded())
            chooseLeaderCardImage2.setImage(new Image(String.valueOf(GuiView.class.getResource("/Cards/backleader.png"))));
        else
            chooseLeaderCardImage2.setImage(GuiView.getLeaderCardImage(myLeaderCards.get(1)));

    }
    @FXML
    public void selectFirstLeaderCard(){
        selectedLeaderCard=0;
        chooseLeaderCardImage1.setDisable(true);
        chooseLeaderCardImage2.setDisable(true);
        DropShadow d= new DropShadow();
        d.setRadius(80);

        d.setColor(Color.web("#7e0608"));

        chooseLeaderCardImage1.setEffect(d);
    }
    @FXML
    public void selectSecondLeaderCard(){
        selectedLeaderCard=1;
        DropShadow d= new DropShadow();
        d.setRadius(80);

        d.setColor(Color.web("#7e0608"));

        chooseLeaderCardImage2.setEffect(d);

        chooseLeaderCardImage1.setDisable(true);
        chooseLeaderCardImage2.setDisable(true);

    }
    @FXML
    public void activateLeaderCard(){
        if(selectedLeaderCard!=-1){
            serverHandler.send(new LeaderCardActionEvent('a',selectedLeaderCard));
            //leaderActionPane.setVisible(false);
            chooseLeaderCardImage1.setEffect(null);
            chooseLeaderCardImage2.setEffect(null);
            DropShadow d= new DropShadow();
            d.setRadius(80);

            d.setColor(Color.web("#7e0608"));
            if (clientModel.getPlayerLeaderCards(clientModel.getMyNickname()).get(0).isActivated())
                chooseLeaderCardImage1.setEffect(d);
            if (clientModel.getPlayerLeaderCards(clientModel.getMyNickname()).get(1).isActivated())
                chooseLeaderCardImage2.setEffect(d);


            guiView.getSceneController("gameScene").updateScene();
            guiView.setCurrentScene("gameScene");
        }
    }
    @FXML
    public void discardLeaderCard(){
        if(selectedLeaderCard!=-1){
            serverHandler.send(new LeaderCardActionEvent('d',selectedLeaderCard));
            //leaderActionPane.setVisible(false);
            chooseLeaderCardImage1.setEffect(null);
            chooseLeaderCardImage2.setEffect(null);
            guiView.getSceneController("gameScene").updateScene();
            guiView.setCurrentScene("gameScene");
        }
    }

    /**
     * exits from the leaderActionScene and go back to gameScene
     */
    @FXML
    public void exit(){
        guiView.getSceneController("gameScene").updateScene();
        guiView.setCurrentScene("gameScene");
    }
}
