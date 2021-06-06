package it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.controller.Events.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelExceptions.InvalidWarehouseInsertionException;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.ImagePattern;

import java.util.*;

public class GameSceneController extends FXMLController {

    @FXML
    private AnchorPane root;
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
    @FXML
    private Button useMarketButton;
    @FXML
    private Button showMarketButton;
    @FXML
    private Button showDevelopmentCardBoardButton;
    @FXML
    private Button showOtherPlayerBoardButton1;
    @FXML
    private Button showOtherPlayerBoardButton2;
    @FXML
    private Button showOtherPlayerBoardButton3;

    @FXML
    private ImageView myWarehouseImage11;
    @FXML
    private ImageView myWarehouseImage21;
    @FXML
    private ImageView myWarehouseImage22;
    @FXML
    private ImageView myWarehouseImage31;
    @FXML
    private ImageView myWarehouseImage32;
    @FXML
    private ImageView myWarehouseImage33;
    @FXML
    private ImageView myDevCardImage00;
    @FXML
    private ImageView myDevCardImage01;
    @FXML
    private ImageView myDevCardImage02;
    @FXML
    private ImageView myDevCardImage10;
    @FXML
    private ImageView myDevCardImage11;
    @FXML
    private ImageView myDevCardImage12;
    @FXML
    private ImageView myDevCardImage20;
    @FXML
    private ImageView myDevCardImage21;
    @FXML
    private ImageView myDevCardImage22;
    @FXML
    private Label strongboxNumberCoinsLabel;
    @FXML
    private Label strongboxNumberShieldsLabel;
    @FXML
    private Label strongboxNumberServantsLabel;
    @FXML
    private Label strongboxNumberStonesLabel;

    @FXML
    private AnchorPane otherBoardPane;
    @FXML
    private Button exitOtherBoardButton;
    @FXML
    private Label otherBoardNameLabel;
    @FXML
    private ImageView otherWarehouseImage11;
    @FXML
    private ImageView otherWarehouseImage21;
    @FXML
    private ImageView otherWarehouseImage22;
    @FXML
    private ImageView otherWarehouseImage31;
    @FXML
    private ImageView otherWarehouseImage32;
    @FXML
    private ImageView otherWarehouseImage33;
    @FXML
    private ImageView otherDevCardImage00;
    @FXML
    private ImageView otherDevCardImage01;
    @FXML
    private ImageView otherDevCardImage02;
    @FXML
    private ImageView otherDevCardImage10;
    @FXML
    private ImageView otherDevCardImage11;
    @FXML
    private ImageView otherDevCardImage12;
    @FXML
    private ImageView otherDevCardImage20;
    @FXML
    private ImageView otherDevCardImage21;
    @FXML
    private ImageView otherDevCardImage22;
    @FXML
    private Label otherStrongboxCoinLabel;
    @FXML
    private Label otherStrongboxShieldLabel;
    @FXML
    private Label otherStrongboxServantLabel;
    @FXML
    private Label otherStrongboxStoneLabel;


/*
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
    @FXML
    private ImageView chooseLeaderCardImage1;
    @FXML
    private ImageView chooseLeaderCardImage2;

    private int selectedLeaderCard;
*/
    //----- cose per pane compra carta -----
    /*
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
*/
    //----- cose per pane attiva produzione -----
    /*
    //pane principale
    private List<Integer> cardProductions;
    private boolean activateBaseProduction;
    private Resource requestedResBP1;
    private Resource requestedResBP2;
    private Resource producedResBP;
    private Resource leaderProductionResource1;
    private Resource leaderProductionResource2;

    @FXML
    private ImageView cardProductionImage1;
    @FXML
    private ImageView cardProductionImage2;
    @FXML
    private ImageView cardProductionImage3;

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
    private ImageView leaderCardProductionImage1;
    @FXML
    private ImageView leaderCardProductionImage2;
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
*/
    //----- cose per pane compra al mercato -----
    /*
    private char rowOrColumn;
    private int marketIndex;
    private List<Resource> boughtResources;
    private List<Integer> whiteMarbleChoices;
    private Map<Resource,Integer> discardedResources;
    private Integer fullLeaderSlots1;
    private Integer fullLeaderSlots2;
    private Resource leaderCardResource1;
    private Resource leaderCardResource2;
    private PlayerWarehouse newWarehouse;

    //pane con proprio il mercato
    @FXML
    private AnchorPane marketPane;
    @FXML
    private ToggleGroup toggleGroupMarket;
    @FXML
    private Button submitMarketButton;
    //pane per inserire/scartare risorse
    @FXML
    private AnchorPane insertResourcesPane;
    @FXML
    private Label insertResourceLabel;
    @FXML
    private ToggleGroup toggleGroupInsertWarehouse;
    @FXML
    private Button insertResourceButton;
    @FXML
    private Button discardResourceButton;
    @FXML
    private Button editWarehouseButton;
    @FXML
    private Button submitInsertResourceButton;
    @FXML
    private Button insertLeaderButton1;
    @FXML
    private Button insertLeaderButton2;

    //pane per edit Warehouse
    private List<Resource> temporaryRemovedResources;

    @FXML
    private AnchorPane editWarehousePane;
    @FXML
    private ToggleGroup toggleGroupEditWarehouse;
    @FXML
    private Button reinsertResourceButton;
    @FXML
    private Button temporaryRemoveResourceButton;
    @FXML
    private Button exitEditWarehouseButton;
    @FXML
    private Button reinsertLeaderResourceButton1;
    @FXML
    private Button reinsertLeaderResourceButton2;
    @FXML
    private Button removeLeaderResourceButton1;
    @FXML
    private Button removeLeaderResourceButton2;
*/

    //---- cose per la schermata principale---

    @Override
    public void updateScene(){ //todo: da fare

        showOtherPlayerBoardButton1.setVisible(false);
        showOtherPlayerBoardButton2.setVisible(false);
        showOtherPlayerBoardButton3.setVisible(false);
        if(clientModel.getNumPlayers()>1){
            showOtherPlayerBoardButton1.setVisible(true);
        }
        if(clientModel.getNumPlayers()>2){
            showOtherPlayerBoardButton2.setVisible(true);
        }
        if(clientModel.getNumPlayers()>3){
            showOtherPlayerBoardButton3.setVisible(true);
        }

        if(clientModel.isCurrentPlayer()){
            leaderActionButton.setVisible(true);
            buyCardButton.setVisible(true);
            activateProductionButton.setVisible(true);
            useMarketButton.setVisible(true);
            endTurnButton.setVisible(true);
        }else{
            leaderActionButton.setVisible(false);
            buyCardButton.setVisible(false);
            activateProductionButton.setVisible(false);
            useMarketButton.setVisible(false);
            endTurnButton.setVisible(false);
        }
        PlayerWarehouse myWarehouse = clientModel.getPlayersWarehouses().get(clientModel.getMyIndex());
        PersonalCardBoard myPersonalCardBoard = clientModel.getPlayersCardBoards().get(clientModel.getMyIndex());
        Map<Resource,Integer> myStrongbox = clientModel.getPlayersStrongboxes().get(clientModel.getMyIndex());
        myWarehouseImage11.setImage(GuiView.getResourceImage(myWarehouse.getResource(1,1)));
        myWarehouseImage21.setImage(GuiView.getResourceImage(myWarehouse.getResource(2,1)));
        myWarehouseImage22.setImage(GuiView.getResourceImage(myWarehouse.getResource(2,2)));
        myWarehouseImage31.setImage(GuiView.getResourceImage(myWarehouse.getResource(3,1)));
        myWarehouseImage32.setImage(GuiView.getResourceImage(myWarehouse.getResource(3,2)));
        myWarehouseImage33.setImage(GuiView.getResourceImage(myWarehouse.getResource(3,3)));

        myDevCardImage00.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getCard(0,0)));
        myDevCardImage01.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getCard(0,1)));
        myDevCardImage02.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getCard(0,2)));
        myDevCardImage10.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getCard(1,0)));
        myDevCardImage11.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getCard(1,1)));
        myDevCardImage12.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getCard(1,2)));
        myDevCardImage20.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getCard(2,0)));
        myDevCardImage21.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getCard(2,1)));
        myDevCardImage22.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getCard(2,2)));

        strongboxNumberCoinsLabel.setText("x0");
        strongboxNumberShieldsLabel.setText("x0");
        strongboxNumberServantsLabel.setText("x0");
        strongboxNumberStonesLabel.setText("x0");
        if(myStrongbox.containsKey(Resource.COIN)){
            strongboxNumberCoinsLabel.setText("x"+myStrongbox.get(Resource.COIN));
        }
        if(myStrongbox.containsKey(Resource.SERVANT)){
            strongboxNumberServantsLabel.setText("x"+myStrongbox.get(Resource.SERVANT));
        }
        if(myStrongbox.containsKey(Resource.SHIELD)){
            strongboxNumberShieldsLabel.setText("x"+myStrongbox.get(Resource.SHIELD));
        }
        if(myStrongbox.containsKey(Resource.STONE)){
            strongboxNumberStonesLabel.setText("x"+myStrongbox.get(Resource.STONE));
        }
    }

    @Override
    public void showMessage(String message){
        messageLabel.setText(message);
    }

    @FXML //sto metodo inizializza la scena solo quando viene caricata la prima volta, in automaticao
    public void initialize(){
        BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(new Image(Objects.requireNonNull(InitialSceneController.class.getClassLoader().getResourceAsStream("loader.png")))), CornerRadii.EMPTY, Insets.EMPTY);
        root.setBackground(new Background(backgroundFill));


    }

    @FXML
    public void leaderAction(){
       // initializeLeaderPanel();
        //leaderActionPane.setVisible(true);
        guiView.getSceneController("leaderActionScene").updateScene();
        guiView.setCurrentScene("leaderActionScene");
    }
    @FXML
    public void buyCard(){
        //initializeBuyCardPane();
        //buyCardPane1.setVisible(true);
        guiView.getSceneController("buyCardScene").updateScene();
        guiView.setCurrentScene("buyCardScene");
    }
    @FXML
    public void endTurn(){
        serverHandler.send(new EndTurnEvent());
    }
    @FXML
    public void activateProduction(){
        //initializeProductionPane();
        //productionPane.setVisible(true);
        guiView.getSceneController("activateProductionScene").updateScene();
        guiView.setCurrentScene("activateProductionScene");
    }
    @FXML
    public void useMarket(){
        //marketPane.setVisible(true);
        guiView.getSceneController("useMarketScene").updateScene();
        guiView.setCurrentScene("useMarketScene");
    }
    @FXML
    public void showMarket(){
        //marketPane.setVisible(true);
        guiView.getSceneController("useMarketScene").updateScene();
        guiView.setCurrentScene("useMarketScene");
    }
    @FXML
    public void showDevelopmentCardBoard(){
        guiView.getSceneController("buyCardScene").updateScene();
        guiView.setCurrentScene("buyCardScene");
    }
    @FXML
    public void showOtherPlayerBoard1(){
        if(clientModel.getMyIndex()==0){
            updateOtherBoardPane(1);
        }else{
            updateOtherBoardPane(0);
        }
        otherBoardPane.setVisible(true);
    }
    @FXML
    public void showOtherPlayerBoard2(){
        switch (clientModel.getMyIndex()) {
            case 0, 1 -> updateOtherBoardPane(2);
            case 2 -> updateOtherBoardPane(1);
        }
        otherBoardPane.setVisible(true);
    }
    @FXML
    public void showOtherPlayerBoard3(){
        switch (clientModel.getMyIndex()) {
            case 0, 1, 2 -> updateOtherBoardPane(3);
            case 3 -> updateOtherBoardPane(2);
        }
        otherBoardPane.setVisible(true);
    }

    //sto metodo fa l'update della board dell'altro giocatore, gli devi passare l'indice del giocatore
    //di cui vuoi mostrare la board
    public void updateOtherBoardPane(int playerIndex){
        otherBoardNameLabel.setText(clientModel.getNicknames().get(playerIndex)+"'s Board");
        PlayerWarehouse playerWarehouse = clientModel.getPlayersWarehouses().get(playerIndex);
        PersonalCardBoard playerPersonalCardBoard = clientModel.getPlayersCardBoards().get(playerIndex);
        Map<Resource,Integer> playerStrongbox = clientModel.getPlayersStrongboxes().get(playerIndex);
        otherWarehouseImage11.setImage(GuiView.getResourceImage(playerWarehouse.getResource(1,1)));
        otherWarehouseImage21.setImage(GuiView.getResourceImage(playerWarehouse.getResource(2,1)));
        otherWarehouseImage22.setImage(GuiView.getResourceImage(playerWarehouse.getResource(2,2)));
        otherWarehouseImage31.setImage(GuiView.getResourceImage(playerWarehouse.getResource(3,1)));
        otherWarehouseImage32.setImage(GuiView.getResourceImage(playerWarehouse.getResource(3,2)));
        otherWarehouseImage33.setImage(GuiView.getResourceImage(playerWarehouse.getResource(3,3)));

        otherDevCardImage00.setImage(GuiView.getDevelopmentCardImage(playerPersonalCardBoard.getCard(0,0)));
        otherDevCardImage01.setImage(GuiView.getDevelopmentCardImage(playerPersonalCardBoard.getCard(0,1)));
        otherDevCardImage02.setImage(GuiView.getDevelopmentCardImage(playerPersonalCardBoard.getCard(0,2)));
        otherDevCardImage10.setImage(GuiView.getDevelopmentCardImage(playerPersonalCardBoard.getCard(1,0)));
        otherDevCardImage11.setImage(GuiView.getDevelopmentCardImage(playerPersonalCardBoard.getCard(1,1)));
        otherDevCardImage12.setImage(GuiView.getDevelopmentCardImage(playerPersonalCardBoard.getCard(1,2)));
        otherDevCardImage20.setImage(GuiView.getDevelopmentCardImage(playerPersonalCardBoard.getCard(2,0)));
        otherDevCardImage21.setImage(GuiView.getDevelopmentCardImage(playerPersonalCardBoard.getCard(2,1)));
        otherDevCardImage22.setImage(GuiView.getDevelopmentCardImage(playerPersonalCardBoard.getCard(2,2)));

        otherStrongboxCoinLabel.setText("x0");
        otherStrongboxShieldLabel.setText("x0");
        otherStrongboxServantLabel.setText("x0");
        otherStrongboxStoneLabel.setText("x0");
        if(playerStrongbox.containsKey(Resource.COIN)){
            otherStrongboxCoinLabel.setText("x"+playerStrongbox.get(Resource.COIN));
        }
        if(playerStrongbox.containsKey(Resource.SERVANT)){
            otherStrongboxServantLabel.setText("x"+playerStrongbox.get(Resource.SERVANT));
        }
        if(playerStrongbox.containsKey(Resource.SHIELD)){
            otherStrongboxShieldLabel.setText("x"+playerStrongbox.get(Resource.SHIELD));
        }
        if(playerStrongbox.containsKey(Resource.STONE)){
            otherStrongboxStoneLabel.setText("x"+playerStrongbox.get(Resource.STONE));
        }
    }

    @FXML
    public void exitOtherBoard(){
        otherBoardPane.setVisible(false);
    }
/*
    //----- cose per pane azione leader -----

    private void initializeLeaderPanel(){
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
*/
    /*
    //----- cose per pane compra carta -----
    private void initializeBuyCardPane(){
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
        buyCardPane1.setVisible(true);
    }

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
*/
    //----- cose per pane attiva produzione -----
/*
    private void initializeProductionPane(){
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

        PersonalCardBoard myPersonalCardBoard = clientModel.getPlayersCardBoards().get(clientModel.getMyIndex());
        if(myPersonalCardBoard.getUpperCard(0)!=null){
            cardProductionImage1.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getUpperCard(0)));
        }
        if(myPersonalCardBoard.getUpperCard(1)!=null){
            cardProductionImage2.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getUpperCard(1)));
        }
        if(myPersonalCardBoard.getUpperCard(2)!=null){
            cardProductionImage3.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getUpperCard(2)));
        }
        //cardProductionImage1.setImage(new Image(String.valueOf(GuiView.class.getResource("/Cards/dev1.png")))); //prova, da togliere
        //cardProductionImage2.setImage(new Image(String.valueOf(GuiView.class.getResource("/Cards/dev1.png")))); //prova, da togliere
        //cardProductionImage3.setImage(new Image(String.valueOf(GuiView.class.getResource("/Cards/dev1.png")))); //prova, da togliere

        List<LeaderCard> leaderCards = clientModel.getPlayerLeaderCards(clientModel.getMyNickname());
        if(leaderCards.get(0).isActivated() &&(leaderCards.get(0) instanceof LeaderCardProduction)){
            activeLeaderProductionButton1.setVisible(true);
            activeLeaderProductionButton1.setDisable(false);
            hasLeaderCardProduction=true;
            leaderCardProductionImage1.setImage(GuiView.getLeaderCardImage(leaderCards.get(0)));
        }
        if(leaderCards.get(1).isActivated() &&(leaderCards.get(1) instanceof LeaderCardProduction)){
            activeLeaderProductionButton2.setVisible(true);
            activeLeaderProductionButton2.setDisable(false);
            hasLeaderCardProduction=true;
            leaderCardProductionImage2.setImage(GuiView.getLeaderCardImage(leaderCards.get(1)));
        }
    }

    @FXML
    public void baseProduction(){
        baseProductionPane.setVisible(true);
    }
    @FXML
    public void submitBaseProduction(){
        activateBaseProduction=true;
        //ciò si basa sull'ordine delle risorse sui bottoni, quindi non cambiarlo
        requestedResBP1 = Resource.values()[toggleGroupResourceBP1.getToggles().indexOf(toggleGroupResourceBP1.getSelectedToggle())];
        requestedResBP2 = Resource.values()[toggleGroupResourceBP2.getToggles().indexOf(toggleGroupResourceBP2.getSelectedToggle())];
        producedResBP = Resource.values()[toggleGroupResourceBP3.getToggles().indexOf(toggleGroupResourceBP3.getSelectedToggle())];
        baseProductionPane.setVisible(false);
        baseProductionButton.setDisable(true);
    }

    @FXML
    public void submitProduction(){

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
*/
    //----- cose per l'uso del mercato -----
    /*

    //cose per il pane proprio del mercato
    @FXML
    public void submitMarket(){
        int indexSelectedToggle = toggleGroupMarket.getToggles().indexOf(toggleGroupMarket.getSelectedToggle());
        if(indexSelectedToggle<3){
            rowOrColumn='r';
        }else{
            rowOrColumn='c';
        }
        switch (indexSelectedToggle) {
            case 0, 3 -> marketIndex = 0;
            case 1, 4 -> marketIndex = 1;
            case 2, 5 -> marketIndex = 2;
            case 6 -> marketIndex = 3;
        }
        collectResource();
        marketPane.setVisible(false);
        //System.out.println(rowOrColumn+" "+ marketIndex); //debug
        System.out.println(boughtResources); //debug
        initializeInsertResourcesPane();
    }

    //una volta che l'utente ha scelto riga/colonna allora si prendono le relative risorse, si chiedono all'utente eventuali
    //scelte se tipo ha due carte leader whitemarble attive
    private void collectResource(){
        List<MarbleColor> takenMarbles;
        whiteMarbleChoices=null;
        if(rowOrColumn == 'r')
            takenMarbles = clientModel.getMarket().getRowColors(marketIndex);
        else
            takenMarbles = clientModel.getMarket().getColumnColors(marketIndex);

        List<LeaderCard> leaderCards = clientModel.getPlayerLeaderCards(clientModel.getMyNickname());
        if (leaderCards.get(0).isActivated() && leaderCards.get(0).getWhiteMarbleResource() != null && leaderCards.get(1).isActivated() && leaderCards.get(1).getWhiteMarbleResource() != null) {
            //allora l'utente ha 2 carte leader white marble attive
            whiteMarbleChoices = new ArrayList<>();
            for(MarbleColor marble : takenMarbles){
                if(marble == MarbleColor.WHITE){
                   whiteMarbleChoices.add(askWhiteMarbleChoice());
                }
            }
        }
        boughtResources = clientModel.fromMarblesToResources(takenMarbles, whiteMarbleChoices);
    }

    //questo metodo chiede all'utente di scegliere quale tra le sue 2 carte leader white marble usare
    private int askWhiteMarbleChoice(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("You have two white marble leader cards active, which one do you want to use for this white marble?");
        ButtonType buttonTypeOne = new ButtonType("One");
        ButtonType buttonTypeTwo = new ButtonType("Two");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get()==buttonTypeOne){
            return 1;
        }
        if(result.get()==buttonTypeTwo){
            return 2;
        }else{
            return -1; //dovrebbe essere impossibile
        }
    }

    //cose per il pane di inserire/scartare risorse
    private void initializeInsertResourcesPane(){
        newWarehouse = new PlayerWarehouse();
        newWarehouse.setWarehouse(clientModel.getPlayersWarehouses().get(clientModel.getMyIndex()));
        List<LeaderCard> leaderCards = clientModel.getPlayerLeaderCards(clientModel.getMyNickname());
        fullLeaderSlots1 = leaderCards.get(0).getFullSlotsNumber();
        fullLeaderSlots2 = leaderCards.get(1).getFullSlotsNumber();
        leaderCardResource1 = clientModel.getPlayerLeaderCards(clientModel.getMyNickname()).get(0).getAbilityResource();
        leaderCardResource2 = clientModel.getPlayerLeaderCards(clientModel.getMyNickname()).get(1).getAbilityResource();

        discardedResources = new HashMap<>();
        insertLeaderButton1.setVisible(fullLeaderSlots1 != null);
        insertLeaderButton2.setVisible(fullLeaderSlots2 != null);
        checkFinishedResources();
        if(boughtResources.size()>0){
            insertResourcesPane.setVisible(true);
            //todo:qui devo mostrare le risorse comprate
        }
    }

    @FXML
    public void insertResource(){
        insertResourceButton.setDisable(true);
        discardResourceButton.setDisable(true);
        editWarehouseButton.setDisable(true);
        insertLeaderButton1.setDisable(true);
        insertLeaderButton2.setDisable(true);
        for(Toggle toggle: toggleGroupInsertWarehouse.getToggles()){
            ((Node) toggle).setDisable(false);
        }
        submitInsertResourceButton.setDisable(false);
        insertResourceLabel.setText("Choose the cell where you want to insert the resource.");
    }
    @FXML
    public void submitInsertResource(){
        insertResourceButton.setDisable(false);
        discardResourceButton.setDisable(false);
        editWarehouseButton.setDisable(false);
        insertLeaderButton1.setDisable(false);
        insertLeaderButton2.setDisable(false);
        for(Toggle toggle: toggleGroupInsertWarehouse.getToggles()){
            ((Node) toggle).setDisable(true);
        }
        submitInsertResourceButton.setDisable(true);
        insertResourceLabel.setText("Choose your action.");
        SameTypePair<Integer> selectedCell = getCellFromToggleGroup(toggleGroupInsertWarehouse);
        try {
            newWarehouse.insertResource(boughtResources.get(0), selectedCell.getVal1(), selectedCell.getVal2());
            boughtResources.remove(0);
            checkFinishedResources();
        } catch (InvalidWarehouseInsertionException e) {
            //ignored
        }
        System.out.println(boughtResources +" selected cell "+ selectedCell.getVal1() +" " +selectedCell.getVal2()); //debug
        System.out.println(newWarehouse.getResource(1,1)); //debug
        System.out.println(newWarehouse.getResource(2,1) +" "+ newWarehouse.getResource(2,2));
        System.out.println(newWarehouse.getResource(3,1) +" "+ newWarehouse.getResource(3,2)+" "+ newWarehouse.getResource(3,3));
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
    @FXML
    public void discardResource(){
        discardedResources = Resource.addOneResource(discardedResources,boughtResources.get(0));
        boughtResources.remove(0);
        checkFinishedResources();
        System.out.println(boughtResources +" disc res: "+ discardedResources); //debug
    }

    //controlla se sono finite le risorse comprate, e se si invia l'evento
    private void checkFinishedResources(){
        if(boughtResources.size()==0){
            if(fullLeaderSlots1==null){
                fullLeaderSlots1=0;
            }
            if(fullLeaderSlots2==null){
                fullLeaderSlots2=0;
            }
            serverHandler.send(new UseMarketEvent(rowOrColumn,marketIndex,newWarehouse,discardedResources,fullLeaderSlots1,fullLeaderSlots2,whiteMarbleChoices));
            insertResourcesPane.setVisible(false);
        }
    }

    @FXML
    public void insertLeader1(){
        if(fullLeaderSlots1<2 && leaderCardResource1 == boughtResources.get(0)){
            boughtResources.remove(0);
            fullLeaderSlots1 = fullLeaderSlots1+1;
            checkFinishedResources();
        }
    }

    @FXML
    public void insertLeader2(){
        if(fullLeaderSlots2<2 && leaderCardResource2 == boughtResources.get(0)){
            boughtResources.remove(0);
            fullLeaderSlots2 = fullLeaderSlots2+1;
            checkFinishedResources();
        }
    }

    @FXML
    public void editWarehouse(){
        insertResourcesPane.setVisible(false);
        editWarehousePane.setVisible(true);
        temporaryRemovedResources = new ArrayList<>();
        reinsertResourceButton.setDisable(true);
        reinsertLeaderResourceButton1.setDisable(true);
        reinsertLeaderResourceButton1.setDisable(true);
        exitEditWarehouseButton.setDisable(false);
        reinsertLeaderResourceButton1.setVisible(fullLeaderSlots1 != null);
        reinsertLeaderResourceButton2.setVisible(fullLeaderSlots2 != null);
        removeLeaderResourceButton1.setVisible(fullLeaderSlots1 != null);
        removeLeaderResourceButton2.setVisible(fullLeaderSlots2 != null);
    }

    //cose per il pane di edit warehouse

    @FXML
    public void reinsertResource(){
        SameTypePair<Integer> selectedCell = getCellFromToggleGroup(toggleGroupEditWarehouse);
        try {
            newWarehouse.insertResource(temporaryRemovedResources.get(0), selectedCell.getVal1(), selectedCell.getVal2());
            temporaryRemovedResources.remove(0);
            if(temporaryRemovedResources.size()==0){
                reinsertResourceButton.setDisable(true);
                reinsertLeaderResourceButton1.setDisable(true);
                reinsertLeaderResourceButton2.setDisable(true);
                exitEditWarehouseButton.setDisable(false);
            }
        } catch (InvalidWarehouseInsertionException e) {
            //ignored
        }
    }
    @FXML
    public void temporaryRemoveResource(){
        SameTypePair<Integer> selectedCell = getCellFromToggleGroup(toggleGroupEditWarehouse);
        if (newWarehouse.getResource(selectedCell.getVal1(), selectedCell.getVal2()) != null)
            temporaryRemovedResources.add(newWarehouse.removeResource(selectedCell.getVal1(), selectedCell.getVal2()));

        if(temporaryRemovedResources.size()>0){
            reinsertResourceButton.setDisable(false);
            reinsertLeaderResourceButton1.setDisable(false);
            reinsertLeaderResourceButton2.setDisable(false);
            exitEditWarehouseButton.setDisable(true);
        }
    }
    @FXML
    public void exitEditWarehouse(){
        if(temporaryRemovedResources.size()==0){
            editWarehousePane.setVisible(false);
            insertResourcesPane.setVisible(true);
        }
        System.out.println(newWarehouse.getResource(1,1)); //debug
        System.out.println(newWarehouse.getResource(2,1) +" "+ newWarehouse.getResource(2,2));
        System.out.println(newWarehouse.getResource(3,1) +" "+ newWarehouse.getResource(3,2)+" "+ newWarehouse.getResource(3,3));
    }
    @FXML
    public void reinsertLeaderResource1(){
        if(fullLeaderSlots1<2 && leaderCardResource1 == temporaryRemovedResources.get(0)){
            temporaryRemovedResources.remove(0);
            fullLeaderSlots1 = fullLeaderSlots1+1;
        }
        if(temporaryRemovedResources.size()==0){
            reinsertResourceButton.setDisable(true);
            reinsertLeaderResourceButton1.setDisable(true);
            reinsertLeaderResourceButton2.setDisable(true);
            exitEditWarehouseButton.setDisable(false);
        }
    }
    @FXML
    public void reinsertLeaderResource2(){
        if(fullLeaderSlots2<2 && leaderCardResource2 == temporaryRemovedResources.get(0)){
            temporaryRemovedResources.remove(0);
            fullLeaderSlots2 = fullLeaderSlots2+1;
        }
        if(temporaryRemovedResources.size()==0){
            reinsertResourceButton.setDisable(true);
            reinsertLeaderResourceButton1.setDisable(true);
            reinsertLeaderResourceButton2.setDisable(true);
            exitEditWarehouseButton.setDisable(false);
        }
    }
    @FXML
    public void removeLeaderResource1(){
        if(fullLeaderSlots1>0){
            temporaryRemovedResources.add(leaderCardResource1);
            fullLeaderSlots1=fullLeaderSlots1-1;
        }
        if(temporaryRemovedResources.size()>0){
            reinsertResourceButton.setDisable(false);
            reinsertLeaderResourceButton1.setDisable(false);
            reinsertLeaderResourceButton2.setDisable(false);
            exitEditWarehouseButton.setDisable(true);
        }
    }
    @FXML
    public void removeLeaderResource2(){
        if(fullLeaderSlots2>0){
            temporaryRemovedResources.add(leaderCardResource2);
            fullLeaderSlots2=fullLeaderSlots2-1;
        }
        if(temporaryRemovedResources.size()>0){
            reinsertResourceButton.setDisable(false);
            reinsertLeaderResourceButton1.setDisable(false);
            reinsertLeaderResourceButton2.setDisable(false);
            exitEditWarehouseButton.setDisable(true);
        }
    }
*/


}
