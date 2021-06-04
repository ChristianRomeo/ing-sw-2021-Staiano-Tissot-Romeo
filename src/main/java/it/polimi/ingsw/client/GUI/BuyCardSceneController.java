package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.controller.Events.BoughtCardEvent;
import it.polimi.ingsw.model.PersonalCardBoard;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class BuyCardSceneController extends FXMLController {


    //pane1
    @FXML
    private ChoiceBox<Integer> buyCardRowChoiceBox;
    @FXML
    private ChoiceBox<Integer> buyCardColumnChoiceBox;
    @FXML
    private Button nextBuyCardButton;
    //pane2
    @FXML
    private ImageView choosePersonalCardBoardPileImage1;
    @FXML
    private ImageView choosePersonalCardBoardPileImage2;
    @FXML
    private ImageView choosePersonalCardBoardPileImage3;
    @FXML
    private AnchorPane buyCardPane2;
    @FXML
    private Button submitBuyCardButton;
    @FXML
    private ToggleGroup toggleGroupSelectPile;


    @FXML //sto metodo inizializza la scena solo quando viene caricata la prima volta, in automatico
    public void initialize(){
        buyCardRowChoiceBox.getItems().removeAll();
        buyCardRowChoiceBox.getItems().addAll(0,1,2);
        buyCardRowChoiceBox.setValue(0);

        buyCardColumnChoiceBox.getItems().removeAll();
        buyCardColumnChoiceBox.getItems().addAll(0,1,2,3);
        buyCardColumnChoiceBox.setValue(0);
    }

    public void updateScene(){
        PersonalCardBoard myPersonalCardBoard = clientModel.getPlayersCardBoards().get(clientModel.getMyIndex());
        if(myPersonalCardBoard.getUpperCard(0)!=null){
            choosePersonalCardBoardPileImage1.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getUpperCard(0)));
        }
        if(myPersonalCardBoard.getUpperCard(1)!=null){
            choosePersonalCardBoardPileImage2.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getUpperCard(1)));
        }
        if(myPersonalCardBoard.getUpperCard(2)!=null){
            choosePersonalCardBoardPileImage3.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getUpperCard(2)));
        }
       // buyCardPane1.setVisible(true);
    }

    @FXML
    public void nextBuyCard(){
        //buyCardPane1.setVisible(false);
        buyCardPane2.setVisible(true);
    }
    @FXML
    public void submitBuyCard(){
        int row = buyCardRowChoiceBox.getValue();
        int column = buyCardColumnChoiceBox.getValue();
        int pile = toggleGroupSelectPile.getToggles().indexOf(toggleGroupSelectPile.getSelectedToggle());
        serverHandler.send(new BoughtCardEvent(row,column,pile));
        buyCardPane2.setVisible(false);
        //System.out.println(row + " "+ column+ " "+ pile +" ");//debug
        guiView.getSceneController("gameScene").updateScene();
        guiView.setCurrentScene("gameScene");
    }
}
