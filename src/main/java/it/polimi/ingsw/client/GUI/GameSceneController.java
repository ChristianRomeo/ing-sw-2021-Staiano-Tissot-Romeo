package it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.controller.Events.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelExceptions.InvalidWarehouseInsertionException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.*;

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
    @FXML
    private Button useMarketButton;

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

    //----- cose per pane compra al mercato -----
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


    //---- cose per la schermata principale---

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
    @FXML
    public void useMarket(){
        marketPane.setVisible(true);
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
        //ciò si basa sull'ordine delle risorse sui bottoni, quindi non cambiarlo
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

    //----- cose per l'uso del mercato -----

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
        if(boughtResources.size()==0){
            serverHandler.send(new UseMarketEvent(rowOrColumn,marketIndex,newWarehouse,discardedResources,fullLeaderSlots1,fullLeaderSlots2,whiteMarbleChoices ));
            insertResourcesPane.setVisible(false);
        }else{
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



}
