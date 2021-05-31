package it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.controller.Events.ActivatedProductionEvent;
import it.polimi.ingsw.controller.Events.BoughtCardEvent;
import it.polimi.ingsw.controller.Events.EndTurnEvent;
import it.polimi.ingsw.controller.Events.LeaderCardActionEvent;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.LeaderCardProduction;
import it.polimi.ingsw.model.Resource;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class GameSceneController extends FXMLController {

    //---- cose per la schermata principale---
    @FXML
    private Label messageLabel;
    @FXML
    private Button leaderActionButton;
    @FXML
    private Button buyCardButton;
    @FXML
    private Button endTurnButton;
    @FXML
    private Button activateProductionButton;

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

    //----- cose per pane compra carta -----
    //pane1
    @FXML
    private AnchorPane buyCardPane1;
    @FXML
    private ChoiceBox<Integer> buyCardRowChoiceBox;
    @FXML
    private ChoiceBox<Integer> buyCardColumnChoiceBox;
    @FXML
    private Button nextBuyCardButton;
    //pane2
    @FXML
    private AnchorPane buyCardPane2;
    @FXML
    private Button submitBuyCardButton;
    @FXML
    private ToggleGroup toggleGroupSelectPile;

    //----- cose per pane attiva produzione -----
    //pane principale
    private List<Integer> cardProductions;
    private boolean activateBaseProduction;
    private Resource requestedResBP1;
    private Resource requestedResBP2;
    private Resource producedResBP;
    private Resource leaderProductionResource1;
    private Resource leaderProductionResource2;

    @FXML
    private AnchorPane productionPane;
    @FXML
    private RadioButton cardProductionButton1;
    @FXML
    private RadioButton cardProductionButton2;
    @FXML
    private RadioButton cardProductionButton3;
    @FXML
    private Button baseProductionButton;
    @FXML
    private Button submitProductionButton;
    //pane base production
    @FXML
    private AnchorPane baseProductionPane;
    @FXML
    private ToggleGroup toggleGroupResourceBP1;
    @FXML
    private ToggleGroup toggleGroupResourceBP2;
    @FXML
    private ToggleGroup toggleGroupResourceBP3;
    @FXML
    private Button submitBaseProductionButton;
    //pane leader productions
    private boolean hasLeaderCardProduction;
    @FXML
    private AnchorPane leaderProductionPane;
    @FXML
    private ToggleGroup toggleGroupLeaderResource;
    @FXML
    private Button activeLeaderProductionButton1;
    @FXML
    private Button activeLeaderProductionButton2;
    @FXML
    private Button submitLeaderProductionButton;

    @Override
    public void updateScene(){ //todo: da fare
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

    @FXML //sto metodo inizializza la scena solo quando viene caricata la prima volta, in automaticao
    public void initialize(){
        buyCardRowChoiceBox.getItems().removeAll();
        buyCardRowChoiceBox.getItems().addAll(0,1,2);
        buyCardRowChoiceBox.setValue(0);

        buyCardColumnChoiceBox.getItems().removeAll();
        buyCardColumnChoiceBox.getItems().addAll(0,1,2,3);
        buyCardColumnChoiceBox.setValue(0);
    }

    //---- cose per la schermata principale---

    @FXML
    public void leaderAction(){
        initializeLeaderPanel();
        leaderActionPane.setVisible(true);
    }
    @FXML
    public void buyCard(){
        buyCardPane1.setVisible(true);
    }
    @FXML
    public void endTurn(){
        serverHandler.send(new EndTurnEvent());
    }
    @FXML
    public void activateProduction(){
        initializeProductionPane();
        productionPane.setVisible(true);
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

    //----- cose per pane compra carta -----

    @FXML
    public void nextBuyCard(){
        buyCardPane1.setVisible(false);
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
    }

    //----- cose per pane attiva produzione -----

    public void initializeProductionPane(){
        cardProductions = new ArrayList<>();
        activateBaseProduction=false;
        requestedResBP1=null;
        requestedResBP2=null;
        producedResBP=null;
        baseProductionButton.setDisable(false);
        activeLeaderProductionButton1.setVisible(false);
        activeLeaderProductionButton2.setVisible(false);
        hasLeaderCardProduction=false;
        leaderProductionResource1=null;
        leaderProductionResource2=null;

        List<LeaderCard> leaderCards = clientModel.getPlayerLeaderCards(clientModel.getMyNickname());
        if(leaderCards.get(0).isActivated() &&(leaderCards.get(0) instanceof LeaderCardProduction)){
            activeLeaderProductionButton1.setVisible(true);
            activeLeaderProductionButton1.setDisable(false);
            hasLeaderCardProduction=true;
        }
        if(leaderCards.get(1).isActivated() &&(leaderCards.get(1) instanceof LeaderCardProduction)){
            activeLeaderProductionButton2.setVisible(true);
            activeLeaderProductionButton2.setDisable(false);
            hasLeaderCardProduction=true;
        }
    }

    @FXML
    public void baseProduction(){
        baseProductionPane.setVisible(true);
    }
    @FXML
    public void submitBaseProduction(){
        activateBaseProduction=true;
        //todo: ciò si basa sull'ordine delle risorse sui bottoni, quindi non cambiarlo
        requestedResBP1 = Resource.values()[toggleGroupResourceBP1.getToggles().indexOf(toggleGroupResourceBP1.getSelectedToggle())];
        requestedResBP2 = Resource.values()[toggleGroupResourceBP2.getToggles().indexOf(toggleGroupResourceBP2.getSelectedToggle())];
        producedResBP = Resource.values()[toggleGroupResourceBP3.getToggles().indexOf(toggleGroupResourceBP3.getSelectedToggle())];
        baseProductionPane.setVisible(false);
        baseProductionButton.setDisable(true);
    }

    @FXML
    public void submitProduction(){
        //devo mettere la send
        if(cardProductionButton1.isSelected()){
            cardProductions.add(0);
        }
        if(cardProductionButton2.isSelected()){
            cardProductions.add(1);
        }
        if(cardProductionButton3.isSelected()){
            cardProductions.add(2);
        }
        productionPane.setVisible(false);
        if(hasLeaderCardProduction){
            leaderProductionPane.setVisible(true);
        }else{
            serverHandler.send(new ActivatedProductionEvent(cardProductions,activateBaseProduction,requestedResBP1,requestedResBP2,producedResBP,null,null));
        }
    }
    @FXML
    public void activeLeaderProduction1(){
        leaderProductionResource1 = Resource.values()[toggleGroupLeaderResource.getToggles().indexOf(toggleGroupLeaderResource.getSelectedToggle())];
        activeLeaderProductionButton1.setDisable(true);
    }
    @FXML
    public void activeLeaderProduction2(){
        leaderProductionResource2 = Resource.values()[toggleGroupLeaderResource.getToggles().indexOf(toggleGroupLeaderResource.getSelectedToggle())];
        activeLeaderProductionButton2.setDisable(true);
    }
    @FXML
    public void submitLeaderProduction(){
        serverHandler.send(new ActivatedProductionEvent(cardProductions,activateBaseProduction,requestedResBP1,requestedResBP2,producedResBP,leaderProductionResource1,leaderProductionResource2));
        leaderProductionPane.setVisible(false);
    }


}
