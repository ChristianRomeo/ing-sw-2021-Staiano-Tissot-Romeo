package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.controller.Events.EndTurnEvent;
import it.polimi.ingsw.model.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GameSceneController extends FXMLController {

    @FXML
    private AnchorPane root;
    //---- cose per la schermata principale---
    @FXML
    private ImageView myFaithTrackPositionImage;
    @FXML
    private ImageView blackCrossPositionImage;
    @FXML
    private ImageView soloActionImage;
    @FXML
    private Label soloActionLabel;
    @FXML
    private Label turnLabel;
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
    private ImageView myLeaderCardImage1;
    @FXML
    private ImageView myLeaderCardImage2;
    @FXML
    private ImageView myLeaderSlotImage11;
    @FXML
    private ImageView myLeaderSlotImage12;
    @FXML
    private ImageView myLeaderSlotImage21;
    @FXML
    private ImageView myLeaderSlotImage22;
    @FXML
    private ImageView myPopeTileImage1;
    @FXML
    private ImageView myPopeTileImage2;
    @FXML
    private ImageView myPopeTileImage3;


    @FXML
    private AnchorPane otherBoardPane;
    @FXML
    private Button exitOtherBoardButton;
    @FXML
    private Label otherBoardNameLabel;
    @FXML
    private ImageView otherFaithPositionImage;
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
    @FXML
    private ImageView otherLeaderCardImage1;
    @FXML
    private ImageView otherLeaderCardImage2;
    @FXML
    private ImageView otherLeaderSlotImage11;
    @FXML
    private ImageView otherLeaderSlotImage12;
    @FXML
    private ImageView otherLeaderSlotImage21;
    @FXML
    private ImageView otherLeaderSlotImage22;
    @FXML
    private ImageView otherPopeTileImage1;
    @FXML
    private ImageView otherPopeTileImage2;
    @FXML
    private ImageView otherPopeTileImage3;

    @FXML
    private AnchorPane messagePane;
    @FXML
    private Label messagePaneLabel;
    @FXML
    private Button exitMessagePaneButton;


    //---- cose per la schermata principale---
    /**
     * updates the gameScene
     * it will show actions based on the number of the players
     * (e.g. the current player will see Buy Card, Leader Action...while other players won't)
     * and then it calls methods which will update Warehouse, PersonalCardBoard, Strongbox, LeaderCards, PopeTiles
     */
    @Override
    public void updateScene(){

        showOtherPlayerBoardButton1.setVisible(false);
        showOtherPlayerBoardButton2.setVisible(false);
        showOtherPlayerBoardButton3.setVisible(false);
        if(clientModel.getNumPlayers()>1){
            showOtherPlayerBoardButton1.setVisible(true);
            blackCrossPositionImage.setImage(null);
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
            turnLabel.setText("It's your turn!");
            turnLabel.setTextFill(Color.LIGHTGREEN);
        }else{
            leaderActionButton.setVisible(false);
            buyCardButton.setVisible(false);
            activateProductionButton.setVisible(false);
            useMarketButton.setVisible(false);
            endTurnButton.setVisible(false);
            turnLabel.setText("It's his turn: "+ clientModel.getCurrentPlayerNick());
            turnLabel.setTextFill(Color.web("#ff704d"));
        }

        setFaithTrackPosition(clientModel.getMyIndex(),false);
        if(clientModel.getNumPlayers()==1){
            soloActionLabel.setVisible(true);
            setFaithTrackPosition(0,true);
            soloActionImage.setImage(GuiView.getSoloActionImage(clientModel.getLastSoloActionUsed()));
        }
        updateMyWarehouse();
        updateMyPersonalCardBoard();
        updateMyStrongbox();
        updateMyLeaderCards();
        updateMyPopeTiles();
    }

    /**
     * updates the warehouse to show the current resources
     */
    private void updateMyWarehouse(){
        PlayerWarehouse myWarehouse = clientModel.getPlayersWarehouses().get(clientModel.getMyIndex());

        myWarehouseImage11.setImage(GuiView.getResourceImage(myWarehouse.getResource(1,1)));
        myWarehouseImage21.setImage(GuiView.getResourceImage(myWarehouse.getResource(2,1)));
        myWarehouseImage22.setImage(GuiView.getResourceImage(myWarehouse.getResource(2,2)));
        myWarehouseImage31.setImage(GuiView.getResourceImage(myWarehouse.getResource(3,1)));
        myWarehouseImage32.setImage(GuiView.getResourceImage(myWarehouse.getResource(3,2)));
        myWarehouseImage33.setImage(GuiView.getResourceImage(myWarehouse.getResource(3,3)));
    }

    /**
     * updates the personal card board to show the current Development cards
     */
    private void updateMyPersonalCardBoard(){
        PersonalCardBoard myPersonalCardBoard = clientModel.getPlayersCardBoards().get(clientModel.getMyIndex());

        myDevCardImage00.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getCard(0,0)));
        myDevCardImage01.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getCard(0,1)));
        myDevCardImage02.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getCard(0,2)));
        myDevCardImage10.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getCard(1,0)));
        myDevCardImage11.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getCard(1,1)));
        myDevCardImage12.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getCard(1,2)));
        myDevCardImage20.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getCard(2,0)));
        myDevCardImage21.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getCard(2,1)));
        myDevCardImage22.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getCard(2,2)));

    }

    /**
     * updates the strongbox to show the current resources
     */
    private void updateMyStrongbox(){
        Map<Resource,Integer> myStrongbox = clientModel.getPlayersStrongboxes().get(clientModel.getMyIndex());

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

    /**
     * updates the leader cards checking if they're activated or discarded
     */
    private void updateMyLeaderCards(){
        DropShadow d= new DropShadow();
        d.setRadius(80);

        d.setColor(Color.web("#7e0608"));
        List<LeaderCard> myLeaderCards = clientModel.getPlayerLeaderCards(clientModel.getMyNickname());
        if(myLeaderCards.get(0).isDiscarded()){
            myLeaderCardImage1.setImage(new Image(String.valueOf(GuiView.class.getResource("/Cards/backleader.png"))));
        }else{
            myLeaderCardImage1.setImage(GuiView.getLeaderCardImage(myLeaderCards.get(0)));
            if (myLeaderCards.get(0).isActivated())
            myLeaderCardImage1.setEffect(d);
        }
        if(myLeaderCards.get(1).isDiscarded()){
            myLeaderCardImage2.setImage(new Image(String.valueOf(GuiView.class.getResource("/Cards/backleader.png"))));
        }else{
            myLeaderCardImage2.setImage(GuiView.getLeaderCardImage(myLeaderCards.get(1)));
            if (myLeaderCards.get(1).isActivated())
                myLeaderCardImage2.setEffect(d);

        }
        myLeaderSlotImage11.setImage(null);
        myLeaderSlotImage12.setImage(null);
        myLeaderSlotImage21.setImage(null);
        myLeaderSlotImage22.setImage(null);
        if(myLeaderCards.get(0).getFullSlotsNumber()!=null){
            if(myLeaderCards.get(0).getFullSlotsNumber()>0){
                myLeaderSlotImage11.setImage(GuiView.getResourceImage(myLeaderCards.get(0).getAbilityResource()));
            }
            if(myLeaderCards.get(0).getFullSlotsNumber()>1){
                myLeaderSlotImage12.setImage(GuiView.getResourceImage(myLeaderCards.get(0).getAbilityResource()));
            }
        }
        if(myLeaderCards.get(1).getFullSlotsNumber()!=null){
            if(myLeaderCards.get(1).getFullSlotsNumber()>0){
                myLeaderSlotImage21.setImage(GuiView.getResourceImage(myLeaderCards.get(1).getAbilityResource()));
            }
            if(myLeaderCards.get(1).getFullSlotsNumber()>1){
                myLeaderSlotImage22.setImage(GuiView.getResourceImage(myLeaderCards.get(1).getAbilityResource()));
            }
        }
    }

    /**
     * updates the Pope tiles to show their current statuses
     */
    private void updateMyPopeTiles(){
        SameTypeTriple<PopeFavorTileStatus> myPopeTiles = clientModel.getPlayersPopeTiles().get(clientModel.getMyIndex());
        myPopeTileImage1.setImage(GuiView.getPopeTileImage(myPopeTiles.get(1),1));
        myPopeTileImage2.setImage(GuiView.getPopeTileImage(myPopeTiles.get(2),2));
        myPopeTileImage3.setImage(GuiView.getPopeTileImage(myPopeTiles.get(3),3));
    }

    /**
     * updates the Faith track position
     * @param playerIndex is the index of the player whom Faith track position will be updated
     * @param setBlackCross is used to check whether the player is in Solo mode or not (used for the black cross)
     */
    //uso sto metodo sia per il mio faith track che per l'other board, sia per la black cross
    private void setFaithTrackPosition(int playerIndex, boolean setBlackCross){
        //int myPosition = clientModel.getPlayersFTPositions().get(clientModel.getMyIndex());
        int position;
        if(!setBlackCross){
            position = clientModel.getPlayersFTPositions().get(playerIndex);
        }else{
            position = clientModel.getBlackCrossPosition();
        }
        int myX=0, myY=0;
        int otherX= 0, otherY=0;
        switch (position) {
            case 0 -> {
                myX = 237;
                myY = 220;
                otherX=261;
                otherY=257;
            }
            case 1 -> {
                myX = 281;
                myY = 220;
                otherX=295;
                otherY=257;
            }
            case 2 -> {
                myX = 325;
                myY = 220;
                otherX=326;
                otherY=257;
            }
            case 3 -> {
                myX = 325;
                myY = 178;
                otherX=326;
                otherY=221;
            }
            case 4 -> {
                myX = 325;
                myY = 126;
                otherX=326;
                otherY=189;
            }
            case 5 -> {
                myX = 365;
                myY = 126;
                otherX=365;
                otherY=189;
            }
            case 6 -> {
                myX = 406;
                myY = 126;
                otherX=400;
                otherY=189;
            }
            case 7 -> {
                myX = 458;
                myY = 126;
                otherX=435;
                otherY=189;
            }
            case 8 -> {
                myX = 493;
                myY = 126;
                otherX=475;
                otherY=189;
            }
            case 9 -> {
                myX = 539;
                myY = 126;
                otherX=495;
                otherY=189;
            }
            case 10 -> {
                myX = 539;
                myY = 168;
                otherX=495;
                otherY=221;
            }
            case 11 -> {
                myX = 539;
                myY = 221;
                otherX=495;
                otherY=258;
            }
            case 12 -> {
                myX = 588;
                myY = 221;
                otherX=536;
                otherY=258;
            }
            case 13 -> {
                myX = 626;
                myY = 221;
                otherX=565;
                otherY=258;
            }
            case 14 -> {
                myX = 665;
                myY = 221;
                otherX=599;
                otherY=258;
            }
            case 15 -> {
                myX = 710;
                myY = 221;
                otherX=639;
                otherY=258;
            }
            case 16 -> {
                myX = 759;
                myY = 221;
                otherX=667;
                otherY=258;
            }
            case 17 -> {
                myX = 759;
                myY = 169;
                otherX=667;
                otherY=221;
            }
            case 18 -> {
                myX = 759;
                myY = 127;
                otherX=667;
                otherY=189;
            }
            case 19 -> {
                myX = 794;
                myY = 127;
                otherX=701;
                otherY=189;
            }
            case 20 -> {
                myX = 840;
                myY = 127;
                otherX=732;
                otherY=189;
            }
            case 21 -> {
                myX = 881;
                myY = 127;
                otherX=767;
                otherY=189;
            }
            case 22 -> {
                myX = 920;
                myY = 127;
                otherX=799;
                otherY=189;
            }
            case 23 -> {
                myX = 965;
                myY = 127;
                otherX=838;
                otherY=189;
            }
            case 24 -> {
                myX = 1010;
                myY = 127;
                otherX=867;
                otherY=189;
            }
        }
        if(!setBlackCross){
            if(playerIndex== clientModel.getMyIndex()){
                myFaithTrackPositionImage.setLayoutX(myX);
                myFaithTrackPositionImage.setLayoutY(myY);
            }else{
                otherFaithPositionImage.setLayoutX(otherX);
                otherFaithPositionImage.setLayoutY(otherY);
            }
        }
        else{
            blackCrossPositionImage.setLayoutX(myX);
            blackCrossPositionImage.setLayoutY(myY);
        }
    }

    @Override
    public void showMessage(String message){
        switch (message) {
            case "CannotActivateProduction" -> messagePaneLabel.setText("You can't activate the production this way!");
            case "CannotBuyCard" -> messagePaneLabel.setText("You can't buy this card or you can't put it in that position!");
            case "IllegalMarketUse" -> messagePaneLabel.setText("You can't use the market this way!");
            case "IllegalLeaderAction" -> messagePaneLabel.setText("You can't do this leader action!");
            case "AlreadyDoneAction" -> messagePaneLabel.setText("You have already made a main action! You can't do another!");
            case "EndTurnWithoutAction" -> messagePaneLabel.setText("You have to do a main action before you end your turn!");
            case "IllegalAction" -> messagePaneLabel.setText("Illegal Action!");
            default -> messagePaneLabel.setText("Strange Message");
        }

        messagePane.setVisible(true);
    }

    @FXML
    public void exitMessagePane(){
        messagePane.setVisible(false);
    }

    @FXML //sto metodo inizializza la scena solo quando viene caricata la prima volta, in automaticao
    public void initialize(){
        BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(new Image(Objects.requireNonNull(InitialSceneController.class.getClassLoader().getResourceAsStream("loader.png")))), CornerRadii.EMPTY, Insets.EMPTY);
        root.setBackground(new Background(backgroundFill));


    }

    /**
     * calls updateScene() for the LeaderActionSceneController
     * and sets the current scene to leaderActionScene.fxml
     */
    @FXML
    public void leaderAction(){

        guiView.getSceneController("leaderActionScene").updateScene();
        guiView.setCurrentScene("leaderActionScene");
    }

    /**
     * calls updateScene() for the BuyCardSceneController
     * and sets the current scene to buyCardScene.fxml
     */
    @FXML
    public void buyCard(){

        guiView.getSceneController("buyCardScene").updateScene();
        guiView.setCurrentScene("buyCardScene");
    }
    @FXML
    public void endTurn(){
        serverHandler.send(new EndTurnEvent());
    }

    /**
     * calls updateScene() for the ActivateProductionSceneController
     * and sets the current scene to activateProductionScene.fxml
     */
    @FXML
    public void activateProduction(){

        guiView.getSceneController("activateProductionScene").updateScene();
        guiView.setCurrentScene("activateProductionScene");
    }

    /**
     * calls updateScene() for the UseMarketSceneController
     * and sets the current scene to useMarketScene.fxml
     */
    @FXML
    public void useMarket(){

        guiView.getSceneController("useMarketScene").updateScene();
        guiView.setCurrentScene("useMarketScene");
    }

    /**
     * calls updateScene() for the UseMarketSceneController
     * and sets the current scene to useMarketScene.fxml
     */
    @FXML
    public void showMarket(){

        guiView.getSceneController("useMarketScene").updateScene();
        guiView.setCurrentScene("useMarketScene");
    }

    /**
     * calls updateScene() for the BuyCardSceneController
     * and sets the current scene to buyCardScene.fxml
     */
    @FXML
    public void showDevelopmentCardBoard(){
        guiView.getSceneController("buyCardScene").updateScene();
        guiView.setCurrentScene("buyCardScene");
    }

    /**
     * shows the first player's board
     */
    @FXML
    public void showOtherPlayerBoard1(){
        if(clientModel.getMyIndex()==0){
            updateOtherBoardPane(1);
        }else{
            updateOtherBoardPane(0);
        }
        otherBoardPane.setVisible(true);
    }

    /**
     * shows the second player's board
     */
    @FXML
    public void showOtherPlayerBoard2(){
        switch (clientModel.getMyIndex()) {
            case 0, 1 -> updateOtherBoardPane(2);
            case 2 -> updateOtherBoardPane(1);
        }
        otherBoardPane.setVisible(true);
    }

    /**
     * shows the third player's board
     */
    @FXML
    public void showOtherPlayerBoard3(){
        switch (clientModel.getMyIndex()) {
            case 0, 1, 2 -> updateOtherBoardPane(3);
            case 3 -> updateOtherBoardPane(2);
        }
        otherBoardPane.setVisible(true);
    }

    /**
     * updates the board of a specific player
     * @param playerIndex is the index of the player whose board will be updated
     */
    //sto metodo fa l'update della board dell'altro giocatore, gli devi passare l'indice del giocatore
    //di cui vuoi mostrare la board
    public void updateOtherBoardPane(int playerIndex){
        otherBoardNameLabel.setText(clientModel.getNicknames().get(playerIndex)+"'s Board");

        setFaithTrackPosition(playerIndex,false);

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

        List<LeaderCard> otherLeaderCards = clientModel.getPlayerLeaderCards(clientModel.getNicknames().get(playerIndex));
        if(!otherLeaderCards.get(0).isActivated()){
            otherLeaderCardImage1.setImage(new Image(String.valueOf(GuiView.class.getResource("/Cards/backleader.png"))));
        }else{
            otherLeaderCardImage1.setImage(GuiView.getLeaderCardImage(otherLeaderCards.get(0)));
        }
        if(!otherLeaderCards.get(1).isActivated()){
            otherLeaderCardImage2.setImage(new Image(String.valueOf(GuiView.class.getResource("/Cards/backleader.png"))));
        }else{
            otherLeaderCardImage2.setImage(GuiView.getLeaderCardImage(otherLeaderCards.get(1)));
        }
        otherLeaderSlotImage11.setImage(null);
        otherLeaderSlotImage12.setImage(null);
        otherLeaderSlotImage21.setImage(null);
        otherLeaderSlotImage22.setImage(null);
        if(otherLeaderCards.get(0).getFullSlotsNumber()!=null){
            if(otherLeaderCards.get(0).getFullSlotsNumber()>0){
                otherLeaderSlotImage11.setImage(GuiView.getResourceImage(otherLeaderCards.get(0).getAbilityResource()));
            }
            if(otherLeaderCards.get(0).getFullSlotsNumber()>1){
                otherLeaderSlotImage12.setImage(GuiView.getResourceImage(otherLeaderCards.get(0).getAbilityResource()));
            }
        }
        if(otherLeaderCards.get(1).getFullSlotsNumber()!=null){
            if(otherLeaderCards.get(1).getFullSlotsNumber()>0){
                otherLeaderSlotImage21.setImage(GuiView.getResourceImage(otherLeaderCards.get(1).getAbilityResource()));
            }
            if(otherLeaderCards.get(1).getFullSlotsNumber()>1){
                otherLeaderSlotImage22.setImage(GuiView.getResourceImage(otherLeaderCards.get(1).getAbilityResource()));
            }
        }

        SameTypeTriple<PopeFavorTileStatus> otherPopeTiles = clientModel.getPlayersPopeTiles().get(playerIndex);
        otherPopeTileImage1.setImage(GuiView.getPopeTileImage(otherPopeTiles.get(1),1));
        otherPopeTileImage2.setImage(GuiView.getPopeTileImage(otherPopeTiles.get(2),2));
        otherPopeTileImage3.setImage(GuiView.getPopeTileImage(otherPopeTiles.get(3),3));
    }
    /**
     * close the opened board of the other player
     */
    @FXML
    public void exitOtherBoard(){
        otherBoardPane.setVisible(false);
    }



}
