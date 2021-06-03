package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.controller.Events.InitialChoiceEvent;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.SameTypePair;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.List;


public class PregameSceneController extends FXMLController{
    private int removedLeaderCard1=-1;
    private int removedLeaderCard2=-1;
    private Resource resource1=null;
    private SameTypePair<Integer> position1=null;
    private Resource resource2=null;
    private SameTypePair<Integer> position2=null;

    @FXML
    private Label upperLabel;
    @FXML
    private Button firstCardButton;
    @FXML
    private Button secondCardButton;
    @FXML
    private Button thirdCardButton;
    @FXML
    private Button fourthCardButton;
    @FXML
    private ImageView leaderCardImage1;
    @FXML
    private ImageView leaderCardImage2;
    @FXML
    private ImageView leaderCardImage3;
    @FXML
    private ImageView leaderCardImage4;

    //---- cose per pane scelta risorse iniziali -----
    @FXML
    private AnchorPane chooseInitialResourcesPane;
    @FXML
    private ToggleGroup toggleGroupInitialResPos1;
    @FXML
    private ToggleGroup toggleGroupInitialResource1;
    @FXML
    private Button submitInitialResourceButton1;
    @FXML
    private Label chooseSecondInitialResLabel;
    @FXML
    private ToggleGroup toggleGroupInitialResPos2;
    @FXML
    private ToggleGroup toggleGroupInitialResource2;
    @FXML
    private Button submitInitialResourceButton2;

    @Override
    public void updateScene(){
        if(clientModel.isCurrentPlayer()){
            upperLabel.setText("E' il tuo turno, scegli le carte che vuoi scartare: ");
            firstCardButton.setVisible(true);
            secondCardButton.setVisible(true);
            thirdCardButton.setVisible(true);
            fourthCardButton.setVisible(true);
            List<LeaderCard> myLeaderCards = clientModel.getPlayerLeaderCards(clientModel.getMyNickname());

            leaderCardImage1.setImage(GuiView.getLeaderCardImage(myLeaderCards.get(0)));
            leaderCardImage2.setImage(GuiView.getLeaderCardImage(myLeaderCards.get(1)));
            leaderCardImage3.setImage(GuiView.getLeaderCardImage(myLeaderCards.get(2)));
            leaderCardImage4.setImage(GuiView.getLeaderCardImage(myLeaderCards.get(3)));
            //System.out.println("id:"+ myLeaderCards.get(0).getId()); //debug
        }else{
            upperLabel.setText("E' il turno di " + clientModel.getCurrentPlayerNick()+ ", attendi...");
        }
    }

    @FXML
    public void choseFirstCard(){
        firstCardButton.setDisable(true);
        firstCardButton.setStyle("-fx-background-color: #ff0000; ");
        if(removedLeaderCard1==-1){
            removedLeaderCard1=0;
        }else{
            if(removedLeaderCard2==-1){
                removedLeaderCard2=0;
                secondCardButton.setDisable(true);
                thirdCardButton.setDisable(true);
                fourthCardButton.setDisable(true);
                chooseResource();
            }
        }
    }
    @FXML
    public void choseSecondCard(){
        secondCardButton.setDisable(true);
        secondCardButton.setStyle("-fx-background-color: #ff0000; ");
        if(removedLeaderCard1==-1){
            removedLeaderCard1=1;
        }else{
            if(removedLeaderCard2==-1){
                removedLeaderCard2=1;
                firstCardButton.setDisable(true);
                thirdCardButton.setDisable(true);
                fourthCardButton.setDisable(true);
                chooseResource();
            }
        }
    }
    @FXML
    public void choseThirdCard(){
        thirdCardButton.setDisable(true);
        thirdCardButton.setStyle("-fx-background-color: #ff0000; ");
        if(removedLeaderCard1==-1){
            removedLeaderCard1=2;
        }else{
            if(removedLeaderCard2==-1){
                removedLeaderCard2=2;
                firstCardButton.setDisable(true);
                secondCardButton.setDisable(true);
                fourthCardButton.setDisable(true);
                chooseResource();
            }
        }
    }
    @FXML
    public void choseFourthCard(){
        fourthCardButton.setDisable(true);
        fourthCardButton.setStyle("-fx-background-color: #ff0000; ");
        if(removedLeaderCard1==-1){
            removedLeaderCard1=3;
        }else{
            if(removedLeaderCard2==-1){
                removedLeaderCard2=3;
                firstCardButton.setDisable(true);
                secondCardButton.setDisable(true);
                thirdCardButton.setDisable(true);
                chooseResource();
            }
        }
    }

    public void chooseResource(){
        if(clientModel.getMyIndex()==0){
            serverHandler.send(new InitialChoiceEvent(removedLeaderCard1,removedLeaderCard2,resource1,resource2,position1,position2));
            return;
        }

        //qui dentro rendo visibile la parte per scegliere le risorse, poi lui fa click di altri bottoni e succedono altre cose
        if(clientModel.getMyIndex()==1 || clientModel.getMyIndex()==2){
            chooseInitialResourcesPane.setVisible(true);

            chooseSecondInitialResLabel.setVisible(false);
            for(Toggle toggle: toggleGroupInitialResource2.getToggles()){
                ((Node) toggle).setVisible(false);
            }
            for(Toggle toggle: toggleGroupInitialResPos2.getToggles()){
                ((Node) toggle).setVisible(false);
            }
            submitInitialResourceButton2.setVisible(false);
        }
        if(clientModel.getMyIndex()==3){
            chooseInitialResourcesPane.setVisible(true);
            submitInitialResourceButton2.setDisable(true);
        }
    }

    @FXML
    public void submitInitialResource1(){
        submitInitialResourceButton1.setDisable(true);
        for(Toggle toggle: toggleGroupInitialResource1.getToggles()){
            ((Node) toggle).setDisable(true);
        }
        for(Toggle toggle: toggleGroupInitialResPos1.getToggles()){
            ((Node) toggle).setDisable(true);
        }
        resource1 = Resource.values()[toggleGroupInitialResource1.getToggles().indexOf(toggleGroupInitialResource1.getSelectedToggle())];
        position1 = getCellFromToggleGroup(toggleGroupInitialResPos1);
        if(clientModel.getMyIndex()<3){
            serverHandler.send(new InitialChoiceEvent(removedLeaderCard1,removedLeaderCard2,resource1,resource2,position1,position2));
            chooseInitialResourcesPane.setVisible(false);
        }else{
            submitInitialResourceButton2.setDisable(false);
        }
    }
    @FXML
    public void submitInitialResource2(){
        resource2 = Resource.values()[toggleGroupInitialResource2.getToggles().indexOf(toggleGroupInitialResource2.getSelectedToggle())];
        position2 = getCellFromToggleGroup(toggleGroupInitialResPos2);
        if(checkInitialChoice()){
            serverHandler.send(new InitialChoiceEvent(removedLeaderCard1,removedLeaderCard2,resource1,resource2,position1,position2));
            chooseInitialResourcesPane.setVisible(false);
        }else{
            chooseSecondInitialResLabel.setText("Invalid choice! Try again to choose the second resource and its position.");
        }
    }


    /**
     * Helper method of initial choice.
     */
    private boolean checkInitialChoice(){

        if(position1.getVal1().equals(position2.getVal1()) && position1.getVal2().equals(position2.getVal2()))
            return false;

        if(resource1==resource2 && !position1.getVal1().equals(position2.getVal1()))
            return false;

        return resource1 == resource2 || !position1.getVal1().equals(position2.getVal1());
    }

    //questo metodo ti ritorna la cella del warehouse selezionata partendo dal toggle selezionato nel toggle group
    private SameTypePair<Integer> getCellFromToggleGroup(ToggleGroup toggleGroup){
        int  indexSelectedCellToggle= toggleGroup.getToggles().indexOf(toggleGroup.getSelectedToggle());
        int selectedRow=0, selectedCol=0;
        switch (indexSelectedCellToggle) {
            case 0 -> {
                selectedRow = 1;
                selectedCol = 1;
            }
            case 1 -> {
                selectedRow = 2;
                selectedCol = 1;
            }
            case 2 -> {
                selectedRow = 2;
                selectedCol = 2;
            }
            case 3 -> {
                selectedRow = 3;
                selectedCol = 1;
            }
            case 4 -> {
                selectedRow = 3;
                selectedCol = 2;
            }
            case 5 -> {
                selectedRow = 3;
                selectedCol = 3;
            }
        }
        return new SameTypePair<>(selectedRow,selectedCol);
    }

}
