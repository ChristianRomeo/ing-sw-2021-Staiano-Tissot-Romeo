package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.controller.Events.InitialChoiceEvent;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.SameTypePair;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.List;


public class PregameSceneController extends FXMLController{
    @FXML
    private GridPane grid1;
    @FXML
    private GridPane grid2;
    @FXML
    private ImageView black;
    @FXML
    private Label textlabel;

    private int removedLeaderCard1=-1;
    private int removedLeaderCard2=-1;
    private Resource resource1=null;
    private SameTypePair<Integer> position1=null;
    private Resource resource2=null;
    private SameTypePair<Integer> position2=null;

    @FXML
    private Label upperLabel;
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
    private ToggleGroup toggleGroupInitialResPos2;
    @FXML
    private ToggleGroup toggleGroupInitialResource2;
    @FXML
    private Button submitInitialResourceButton2;


    @Override
    public void updateScene(){
        if(clientModel.isCurrentPlayer()){
            upperLabel.setText("E' il tuo turno, scegli le carte che vuoi scartare: ");

            List<LeaderCard> myLeaderCards = clientModel.getPlayerLeaderCards(clientModel.getMyNickname());

            leaderCardImage1.setImage(GuiView.getLeaderCardImage(myLeaderCards.get(0)));
            leaderCardImage2.setImage(GuiView.getLeaderCardImage(myLeaderCards.get(1)));
            leaderCardImage3.setImage(GuiView.getLeaderCardImage(myLeaderCards.get(2)));
            leaderCardImage4.setImage(GuiView.getLeaderCardImage(myLeaderCards.get(3)));
            //System.out.println("id:"+ myLeaderCards.get(0).getId()); //debug
        }else{
            upperLabel.setText( clientModel.getCurrentPlayerNick()+ " sta scegliendo, attendi...");
        }
    }

    public void initialize(){
        //BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(new Image(Objects.requireNonNull(InitialSceneController.class.getClassLoader().getResourceAsStream("backboardgame.png")))), CornerRadii.EMPTY, Insets.EMPTY);
        //root.setBackground(new Background(backgroundFill));
    }

    @FXML
    public void choseFirstCard(){
        DropShadow d= new DropShadow();
        d.setRadius(80);
        d.setColor(Color.web("#7e0608"));
        leaderCardImage1.setEffect(d);
        leaderCardImage1.setDisable(true);

        if(removedLeaderCard1==-1){
            removedLeaderCard1=0;
        }else{
            if(removedLeaderCard2==-1){
                removedLeaderCard2=0;
                leaderCardImage2.setDisable(true);
                leaderCardImage3.setDisable(true);
                leaderCardImage4.setDisable(true);

                chooseResource();
            }
        }
    }
    @FXML
    public void choseSecondCard(){
        DropShadow d= new DropShadow();
        d.setRadius(80);
        d.setColor(Color.web("#7e0608"));

        leaderCardImage2.setEffect(d);
        leaderCardImage2.setDisable(true);

        if(removedLeaderCard1==-1){
            removedLeaderCard1=1;
        }else{
            if(removedLeaderCard2==-1){
                removedLeaderCard2=1;
                leaderCardImage1.setDisable(true);
                leaderCardImage3.setDisable(true);
                leaderCardImage4.setDisable(true);

                chooseResource();
            }
        }
    }
    @FXML
    public void choseThirdCard(){
        DropShadow d= new DropShadow();
        d.setRadius(70);

        d.setColor(Color.web("#7e0608"));

        leaderCardImage3.setEffect(d);
        leaderCardImage3.setDisable(true);

        if(removedLeaderCard1==-1){
            removedLeaderCard1=2;
        }else{
            if(removedLeaderCard2==-1){
                removedLeaderCard2=2;
                leaderCardImage2.setDisable(true);
                leaderCardImage1.setDisable(true);
                leaderCardImage4.setDisable(true);
                chooseResource();
            }
        }
    }
    @FXML
    public void choseFourthCard(){
        DropShadow d= new DropShadow();
        d.setRadius(70);

        d.setColor(Color.web("#7e0608"));

        leaderCardImage4.setEffect(d);
        leaderCardImage4.setDisable(true);

        if(removedLeaderCard1==-1){
            removedLeaderCard1=3;
        }else{
            if(removedLeaderCard2==-1){
                removedLeaderCard2=3;
                leaderCardImage2.setDisable(true);
                leaderCardImage1.setDisable(true);
                leaderCardImage3.setDisable(true);
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
        //if(clientModel.getMyIndex()==1 || clientModel.getMyIndex()==2) {
            chooseInitialResourcesPane.setVisible(true);
            black.setOpacity(0.8);
        //}
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
            black.setOpacity(0.4);
        }else{
            textlabel.setText("Choose your second resource and position");
            submitInitialResourceButton1.setVisible(false);
            submitInitialResourceButton2.setVisible(true);
            submitInitialResourceButton2.setDisable(false);
            grid1.setVisible(false);
            grid2.setVisible(true);

            for(Toggle toggle: toggleGroupInitialResource1.getToggles()){
                ((Node) toggle).setVisible(false);
            }
            for(Toggle toggle: toggleGroupInitialResPos1.getToggles()){
                ((Node) toggle).setVisible(false);
            }
            for(Toggle toggle: toggleGroupInitialResource2.getToggles()){
                ((Node) toggle).setVisible(true);
            }
            for(Toggle toggle: toggleGroupInitialResPos2.getToggles()){
                ((Node) toggle).setVisible(true);
            }

            //chooseInitialResourcesPane.setVisible(true);
            //submitInitialResourceButton2.setDisable(true);
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
            textlabel.setText("Invalid choice! Try again to choose the second resource and its position.");
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
