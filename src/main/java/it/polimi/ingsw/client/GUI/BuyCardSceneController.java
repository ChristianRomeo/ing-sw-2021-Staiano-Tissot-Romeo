package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.controller.Events.BoughtCardEvent;
import it.polimi.ingsw.model.DevelopmentCardBoard;
import it.polimi.ingsw.model.PersonalCardBoard;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * controller which handles the buyCardScene
 */
public class BuyCardSceneController extends FXMLController {

    //pane1
    @FXML
    private ImageView developmentCardImage00;
    @FXML
    private ImageView developmentCardImage01;
    @FXML
    private ImageView developmentCardImage02;
    @FXML
    private ImageView developmentCardImage03;
    @FXML
    private ImageView developmentCardImage10;
    @FXML
    private ImageView developmentCardImage11;
    @FXML
    private ImageView developmentCardImage12;
    @FXML
    private ImageView developmentCardImage13;
    @FXML
    private ImageView developmentCardImage20;
    @FXML
    private ImageView developmentCardImage21;
    @FXML
    private ImageView developmentCardImage22;
    @FXML
    private ImageView developmentCardImage23;

    @FXML
    private ChoiceBox<Integer> buyCardRowChoiceBox;
    @FXML
    private ChoiceBox<Integer> buyCardColumnChoiceBox;
    @FXML
    private Button nextBuyCardButton;
    @FXML
    private Button exitButton;
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

    /**
     * updates the buyCardScene
     * it shows all the Development cards which can be chosen
     */
    public void updateScene(){
        DevelopmentCardBoard developmentCardBoard = clientModel.getDevelopmentCardBoard();

        developmentCardImage00.setImage(GuiView.getDevelopmentCardImage(developmentCardBoard.getCard(0,0)));
        developmentCardImage01.setImage(GuiView.getDevelopmentCardImage(developmentCardBoard.getCard(0,1)));
        developmentCardImage02.setImage(GuiView.getDevelopmentCardImage(developmentCardBoard.getCard(0,2)));
        developmentCardImage03.setImage(GuiView.getDevelopmentCardImage(developmentCardBoard.getCard(0,3)));
        developmentCardImage10.setImage(GuiView.getDevelopmentCardImage(developmentCardBoard.getCard(1,0)));
        developmentCardImage11.setImage(GuiView.getDevelopmentCardImage(developmentCardBoard.getCard(1,1)));
        developmentCardImage12.setImage(GuiView.getDevelopmentCardImage(developmentCardBoard.getCard(1,2)));
        developmentCardImage13.setImage(GuiView.getDevelopmentCardImage(developmentCardBoard.getCard(1,3)));
        developmentCardImage20.setImage(GuiView.getDevelopmentCardImage(developmentCardBoard.getCard(2,0)));
        developmentCardImage21.setImage(GuiView.getDevelopmentCardImage(developmentCardBoard.getCard(2,1)));
        developmentCardImage22.setImage(GuiView.getDevelopmentCardImage(developmentCardBoard.getCard(2,2)));
        developmentCardImage23.setImage(GuiView.getDevelopmentCardImage(developmentCardBoard.getCard(2,3)));

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
        nextBuyCardButton.setDisable(!clientModel.isCurrentPlayer());
    }

    /**
     * exits from the leaderActionScene and go back to gameScene
     */
    @FXML
    public void exit(){
        guiView.getSceneController("gameScene").updateScene();
        guiView.setCurrentScene("gameScene");
    }

    @FXML
    public void nextBuyCard(){
        buyCardPane2.setVisible(true);
    }
    /**
     * sends a BoughtCard event with the bought card
     */
    @FXML
    public void submitBuyCard(){
        int row = buyCardRowChoiceBox.getValue();
        int column = buyCardColumnChoiceBox.getValue();
        int pile = toggleGroupSelectPile.getToggles().indexOf(toggleGroupSelectPile.getSelectedToggle());
        serverHandler.send(new BoughtCardEvent(row,column,pile));
        buyCardPane2.setVisible(false);
        guiView.getSceneController("gameScene").updateScene();
        guiView.setCurrentScene("gameScene");
    }

    /**
     * closes the buy card pane
     */
    public void closePane() {
        buyCardPane2.setVisible(false);
    }
}
