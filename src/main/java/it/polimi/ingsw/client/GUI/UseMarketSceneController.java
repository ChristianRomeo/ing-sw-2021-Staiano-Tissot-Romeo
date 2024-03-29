package it.polimi.ingsw.client.GUI;


import it.polimi.ingsw.controller.Events.UseMarketEvent;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelExceptions.InvalidWarehouseInsertionException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.*;

/**
 * controller which handles the useMarketScene
 */
public class UseMarketSceneController extends FXMLController{

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
    private ImageView marbleImage00;
    @FXML
    private ImageView marbleImage01;
    @FXML
    private ImageView marbleImage02;
    @FXML
    private ImageView marbleImage03;
    @FXML
    private ImageView marbleImage10;
    @FXML
    private ImageView marbleImage11;
    @FXML
    private ImageView marbleImage12;
    @FXML
    private ImageView marbleImage13;
    @FXML
    private ImageView marbleImage20;
    @FXML
    private ImageView marbleImage21;
    @FXML
    private ImageView marbleImage22;
    @FXML
    private ImageView marbleImage23;
    @FXML
    private ImageView slideMarbleImage;

    @FXML
    private ToggleGroup toggleGroupMarket;
    @FXML
    private Button submitMarketButton;
    @FXML
    private Button exitButton;

    //pane per inserire/scartare risorse
    @FXML
    private ImageView boughtResourceImage1;
    @FXML
    private ImageView boughtResourceImage2;
    @FXML
    private ImageView boughtResourceImage3;
    @FXML
    private ImageView boughtResourceImage4;

    @FXML
    private ImageView warehouseResourceInsertImage11;
    @FXML
    private ImageView warehouseResourceInsertImage21;
    @FXML
    private ImageView warehouseResourceInsertImage22;
    @FXML
    private ImageView warehouseResourceInsertImage31;
    @FXML
    private ImageView warehouseResourceInsertImage32;
    @FXML
    private ImageView warehouseResourceInsertImage33;

    @FXML
    private ImageView leaderCardInsertImage1;
    @FXML
    private ImageView leaderCardInsertImage2;
    @FXML
    private ImageView leader1Slot1InsertImage;
    @FXML
    private ImageView leader1Slot2InsertImage;
    @FXML
    private ImageView leader2Slot1InsertImage;
    @FXML
    private ImageView leader2Slot2InsertImage;

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
    private ImageView temporaryRemovedResourceImage1;
    @FXML
    private ImageView temporaryRemovedResourceImage2;
    @FXML
    private ImageView temporaryRemovedResourceImage3;
    @FXML
    private ImageView temporaryRemovedResourceImage4;

    @FXML
    private ImageView warehouseResourceEditImage11;
    @FXML
    private ImageView warehouseResourceEditImage21;
    @FXML
    private ImageView warehouseResourceEditImage22;
    @FXML
    private ImageView warehouseResourceEditImage31;
    @FXML
    private ImageView warehouseResourceEditImage32;
    @FXML
    private ImageView warehouseResourceEditImage33;

    @FXML
    private ImageView leaderCardEditImage1;
    @FXML
    private ImageView leaderCardEditImage2;
    @FXML
    private ImageView leader1Slot1EditImage;
    @FXML
    private ImageView leader1Slot2EditImage;
    @FXML
    private ImageView leader2Slot1EditImage;
    @FXML
    private ImageView leader2Slot2EditImage;

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
    @FXML
    private AnchorPane root;


    /**
     * updates the useMarketScene with the current market
     */
    @Override
    public void updateScene(){
        submitMarketButton.setDisable(!clientModel.isCurrentPlayer());

        Market market = clientModel.getMarket();
        marbleImage00.setImage(GuiView.getMarbleImage(market.getColor(0,0)));
        marbleImage01.setImage(GuiView.getMarbleImage(market.getColor(0,1)));
        marbleImage02.setImage(GuiView.getMarbleImage(market.getColor(0,2)));
        marbleImage03.setImage(GuiView.getMarbleImage(market.getColor(0,3)));
        marbleImage10.setImage(GuiView.getMarbleImage(market.getColor(1,0)));
        marbleImage11.setImage(GuiView.getMarbleImage(market.getColor(1,1)));
        marbleImage12.setImage(GuiView.getMarbleImage(market.getColor(1,2)));
        marbleImage13.setImage(GuiView.getMarbleImage(market.getColor(1,3)));
        marbleImage20.setImage(GuiView.getMarbleImage(market.getColor(2,0)));
        marbleImage21.setImage(GuiView.getMarbleImage(market.getColor(2,1)));
        marbleImage22.setImage(GuiView.getMarbleImage(market.getColor(2,2)));
        marbleImage23.setImage(GuiView.getMarbleImage(market.getColor(2,3)));
        slideMarbleImage.setImage(GuiView.getMarbleImage(market.getSlideMarble()));
    }

    /**
     * choices in the market
     */
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
        initializeInsertResourcesPane();
    }

    /**
     * exits from the useMarketScene and go back to gameScene
     */
    @FXML
    public void exit(){
        guiView.getSceneController("gameScene").updateScene();
        guiView.setCurrentScene("gameScene");
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

    /**
     * asks the player to choose which of his 2 white marble Leader cards to use
     */
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

    /**
     * insertion/deletion of resources
     */
    private void initializeInsertResourcesPane(){
        newWarehouse = new PlayerWarehouse();
        newWarehouse.setWarehouse(clientModel.getPlayersWarehouses().get(clientModel.getMyIndex()));
        List<LeaderCard> leaderCards = clientModel.getPlayerLeaderCards(clientModel.getMyNickname());
        fullLeaderSlots1 = leaderCards.get(0).getFullSlotsNumber();
        fullLeaderSlots2 = leaderCards.get(1).getFullSlotsNumber();
        leaderCardResource1 = leaderCards.get(0).getAbilityResource();
        leaderCardResource2 = leaderCards.get(1).getAbilityResource();

        discardedResources = new HashMap<>();
        insertLeaderButton1.setVisible(fullLeaderSlots1 != null);
        insertLeaderButton2.setVisible(fullLeaderSlots2 != null);
        if(fullLeaderSlots1!=null){
            leaderCardInsertImage1.setImage(GuiView.getLeaderCardImage(leaderCards.get(0)));
        }else{
            leaderCardInsertImage1.setImage(null);
        }
        if(fullLeaderSlots2!=null){
            leaderCardInsertImage2.setImage(GuiView.getLeaderCardImage(leaderCards.get(1)));
        }else{
            leaderCardInsertImage2.setImage(null);
        }
        checkFinishedResources();
        if(boughtResources.size()>0){
            insertResourcesPane.setVisible(true);
            updateInsertResourcePane();
        }
    }

    //fa l'update del pane per inserire risorse, cosi mostra le giuste immagini nel warehouse e nelle cose da
    //inserire
    private void updateInsertResourcePane(){
        boughtResourceImage1.setImage(null);
        boughtResourceImage2.setImage(null);
        boughtResourceImage3.setImage(null);
        boughtResourceImage4.setImage(null);
        if(boughtResources.size()>0){
            boughtResourceImage1.setImage(GuiView.getResourceImage(boughtResources.get(0)));
        }
        if(boughtResources.size()>1){
            boughtResourceImage2.setImage(GuiView.getResourceImage(boughtResources.get(1)));
        }
        if(boughtResources.size()>2){
            boughtResourceImage3.setImage(GuiView.getResourceImage(boughtResources.get(2)));
        }
        if(boughtResources.size()>3){
            boughtResourceImage4.setImage(GuiView.getResourceImage(boughtResources.get(3)));
        }
        //if the warehouse doesn't have a resource in that position, it returns null, so it will be setImage(null), so it won't set an image. Top.
        warehouseResourceInsertImage11.setImage(GuiView.getResourceImage(newWarehouse.getResource(1,1)));
        warehouseResourceInsertImage21.setImage(GuiView.getResourceImage(newWarehouse.getResource(2,1)));
        warehouseResourceInsertImage22.setImage(GuiView.getResourceImage(newWarehouse.getResource(2,2)));
        warehouseResourceInsertImage31.setImage(GuiView.getResourceImage(newWarehouse.getResource(3,1)));
        warehouseResourceInsertImage32.setImage(GuiView.getResourceImage(newWarehouse.getResource(3,2)));
        warehouseResourceInsertImage33.setImage(GuiView.getResourceImage(newWarehouse.getResource(3,3)));

        leader1Slot1InsertImage.setImage(null);
        leader1Slot2InsertImage.setImage(null);
        if(fullLeaderSlots1!=null){
            if(fullLeaderSlots1>=1){
                leader1Slot1InsertImage.setImage(GuiView.getResourceImage(leaderCardResource1));
            }
            if(fullLeaderSlots1>=2){
                leader1Slot2InsertImage.setImage(GuiView.getResourceImage(leaderCardResource1));
            }
        }
        leader2Slot1InsertImage.setImage(null);
        leader2Slot2InsertImage.setImage(null);
        if(fullLeaderSlots2!=null){
            if(fullLeaderSlots2>=1){
                leader2Slot1InsertImage.setImage(GuiView.getResourceImage(leaderCardResource2));
            }
            if(fullLeaderSlots2>=2){
                leader2Slot2InsertImage.setImage(GuiView.getResourceImage(leaderCardResource2));
            }
        }
    }

    /**
     * lets the player choose where to put resources
     */
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

    /**
     * submit the chosen resources
     */
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
            updateInsertResourcePane();
        } catch (InvalidWarehouseInsertionException ignored) {
        }
    }

    /**
     * @param toggleGroup is the group of toggles from which the player will choose a warehouse cell
     * @return the selected warehouse cell from the selected toggle
     */
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

    /**
     * lets the player which resources to be discarded
     */
    @FXML
    public void discardResource(){
        discardedResources = Resource.addOneResource(discardedResources,boughtResources.get(0));
        boughtResources.remove(0);
        updateInsertResourcePane();
        checkFinishedResources();
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
            guiView.getSceneController("gameScene").updateScene();
            guiView.setCurrentScene("gameScene");
        }
    }

    @FXML
    public void insertLeader1(){
        if(fullLeaderSlots1<2 && leaderCardResource1 == boughtResources.get(0)){
            boughtResources.remove(0);
            fullLeaderSlots1 = fullLeaderSlots1+1;
            updateInsertResourcePane();
            checkFinishedResources();
        }
    }

    @FXML
    public void insertLeader2(){
        if(fullLeaderSlots2<2 && leaderCardResource2 == boughtResources.get(0)){
            boughtResources.remove(0);
            fullLeaderSlots2 = fullLeaderSlots2+1;
            updateInsertResourcePane();
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
        List<LeaderCard> leaderCards = clientModel.getPlayerLeaderCards(clientModel.getMyNickname());
        if(fullLeaderSlots1!=null){
            leaderCardEditImage1.setImage(GuiView.getLeaderCardImage(leaderCards.get(0)));
        }else{
            leaderCardEditImage1.setImage(null);
        }
        if(fullLeaderSlots2!=null){
            leaderCardEditImage2.setImage(GuiView.getLeaderCardImage(leaderCards.get(1)));
        }else{
            leaderCardEditImage2.setImage(null);
        }

        updateEditWarehousePane();
    }

    //----cose per il pane di edit warehouse----

    //fa l'update del pane per edit warehouse, cosi mostra le giuste immagini nel warehouse e nelle cose da
    //inserire
    private void updateEditWarehousePane(){
        temporaryRemovedResourceImage1.setImage(null);
        temporaryRemovedResourceImage2.setImage(null);
        temporaryRemovedResourceImage3.setImage(null);
        temporaryRemovedResourceImage4.setImage(null);
        if(temporaryRemovedResources.size()>0){
            temporaryRemovedResourceImage1.setImage(GuiView.getResourceImage(temporaryRemovedResources.get(0)));
        }
        if(temporaryRemovedResources.size()>1){
            temporaryRemovedResourceImage2.setImage(GuiView.getResourceImage(temporaryRemovedResources.get(1)));
        }
        if(temporaryRemovedResources.size()>2){
            temporaryRemovedResourceImage3.setImage(GuiView.getResourceImage(temporaryRemovedResources.get(2)));
        }
        if(temporaryRemovedResources.size()>3){
            temporaryRemovedResourceImage4.setImage(GuiView.getResourceImage(temporaryRemovedResources.get(3)));
        }

        warehouseResourceEditImage11.setImage(GuiView.getResourceImage(newWarehouse.getResource(1,1)));
        warehouseResourceEditImage21.setImage(GuiView.getResourceImage(newWarehouse.getResource(2,1)));
        warehouseResourceEditImage22.setImage(GuiView.getResourceImage(newWarehouse.getResource(2,2)));
        warehouseResourceEditImage31.setImage(GuiView.getResourceImage(newWarehouse.getResource(3,1)));
        warehouseResourceEditImage32.setImage(GuiView.getResourceImage(newWarehouse.getResource(3,2)));
        warehouseResourceEditImage33.setImage(GuiView.getResourceImage(newWarehouse.getResource(3,3)));

        leader1Slot1EditImage.setImage(null);
        leader1Slot2EditImage.setImage(null);
        if(fullLeaderSlots1!=null){
            if(fullLeaderSlots1>=1){
                leader1Slot1EditImage.setImage(GuiView.getResourceImage(leaderCardResource1));
            }
            if(fullLeaderSlots1>=2){
                leader1Slot2EditImage.setImage(GuiView.getResourceImage(leaderCardResource1));
            }
        }
        leader2Slot1EditImage.setImage(null);
        leader2Slot2EditImage.setImage(null);
        if(fullLeaderSlots2!=null){
            if(fullLeaderSlots2>=1){
                leader2Slot1EditImage.setImage(GuiView.getResourceImage(leaderCardResource2));
            }
            if(fullLeaderSlots2>=2){
                leader2Slot2EditImage.setImage(GuiView.getResourceImage(leaderCardResource2));
            }
        }
    }

    /**
     * reinsert resources after they have been temporarily removed
     */
    @FXML
    public void reinsertResource(){
        SameTypePair<Integer> selectedCell = getCellFromToggleGroup(toggleGroupEditWarehouse);
        try {
            newWarehouse.insertResource(temporaryRemovedResources.get(0), selectedCell.getVal1(), selectedCell.getVal2());
            temporaryRemovedResources.remove(0);
            updateEditWarehousePane();
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
    /**
     * allows to temporary remove a resource in order to rearrange the warehouse
     */
    @FXML
    public void temporaryRemoveResource(){
        SameTypePair<Integer> selectedCell = getCellFromToggleGroup(toggleGroupEditWarehouse);
        if (newWarehouse.getResource(selectedCell.getVal1(), selectedCell.getVal2()) != null)
            temporaryRemovedResources.add(newWarehouse.removeResource(selectedCell.getVal1(), selectedCell.getVal2()));

        updateEditWarehousePane();
        if(temporaryRemovedResources.size()>0){
            reinsertResourceButton.setDisable(false);
            reinsertLeaderResourceButton1.setDisable(false);
            reinsertLeaderResourceButton2.setDisable(false);
            exitEditWarehouseButton.setDisable(true);
        }
    }

    /**
     * exits from the editWarehousePane
     */
    @FXML
    public void exitEditWarehouse(){
        if(temporaryRemovedResources.size()==0){
            editWarehousePane.setVisible(false);
            updateInsertResourcePane();
            insertResourcesPane.setVisible(true);
        }
    }
    @FXML
    public void reinsertLeaderResource1(){
        if(fullLeaderSlots1<2 && leaderCardResource1 == temporaryRemovedResources.get(0)){
            temporaryRemovedResources.remove(0);
            fullLeaderSlots1 = fullLeaderSlots1+1;
            updateEditWarehousePane();
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
            updateEditWarehousePane();
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
            updateEditWarehousePane();
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
            updateEditWarehousePane();
        }
        if(temporaryRemovedResources.size()>0){
            reinsertResourceButton.setDisable(false);
            reinsertLeaderResourceButton1.setDisable(false);
            reinsertLeaderResourceButton2.setDisable(false);
            exitEditWarehouseButton.setDisable(true);
        }
    }

}
