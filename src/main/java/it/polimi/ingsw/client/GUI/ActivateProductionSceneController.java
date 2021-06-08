package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.controller.Events.ActivatedProductionEvent;
import it.polimi.ingsw.model.LeaderCard;
import it.polimi.ingsw.model.LeaderCardProduction;
import it.polimi.ingsw.model.PersonalCardBoard;
import it.polimi.ingsw.model.Resource;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ActivateProductionSceneController extends FXMLController{

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

    public void updateScene(){
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
        cardProductionImage1.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getUpperCard(0)));
        cardProductionImage2.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getUpperCard(1)));
        cardProductionImage3.setImage(GuiView.getDevelopmentCardImage(myPersonalCardBoard.getUpperCard(2)));

        List<LeaderCard> leaderCards = clientModel.getPlayerLeaderCards(clientModel.getMyNickname());
        leaderCardProductionImage1.setImage(null);
        leaderCardProductionImage2.setImage(null);
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
        //productionPane.setVisible(false);
        if(hasLeaderCardProduction){
            leaderProductionPane.setVisible(true);
        }else{
            serverHandler.send(new ActivatedProductionEvent(cardProductions,activateBaseProduction,requestedResBP1,requestedResBP2,producedResBP,null,null));
            guiView.getSceneController("gameScene").updateScene();
            guiView.setCurrentScene("gameScene");
        }
    }
    @FXML
    public void activeLeaderProduction1(){
        leaderProductionResource1 = Resource.values()[toggleGroupLeaderResource.getToggles().indexOf(toggleGroupLeaderResource.getSelectedToggle())];
        activeLeaderProductionButton1.setDisable(true);
        DropShadow d= new DropShadow();
        d.setRadius(80);

        d.setColor(Color.web("#7e0608"));
        leaderCardProductionImage1.setEffect(d);
        leaderCardProductionImage1.setDisable(true);
    }
    @FXML
    public void activeLeaderProduction2(){
        leaderProductionResource2 = Resource.values()[toggleGroupLeaderResource.getToggles().indexOf(toggleGroupLeaderResource.getSelectedToggle())];
        activeLeaderProductionButton2.setDisable(true);
        DropShadow d= new DropShadow();
        d.setRadius(80);

        d.setColor(Color.web("#7e0608"));
        leaderCardProductionImage2.setEffect(d);
        leaderCardProductionImage2.setDisable(true);
    }
    @FXML
    public void submitLeaderProduction(){
        serverHandler.send(new ActivatedProductionEvent(cardProductions,activateBaseProduction,requestedResBP1,requestedResBP2,producedResBP,leaderProductionResource1,leaderProductionResource2));
        leaderProductionPane.setVisible(false);
        guiView.getSceneController("gameScene").updateScene();
        guiView.setCurrentScene("gameScene");
    }

    public void closeBaseProd() {
        baseProductionPane.setVisible(false);
        //baseProductionPane.setDisable(true);
    }
    public void closeLeadProd() {
        leaderProductionPane.setVisible(false);
        //baseProductionPane.setDisable(true);
    }

    @FXML
    public void exit(){
        guiView.getSceneController("gameScene").updateScene();
        guiView.setCurrentScene("gameScene");
    }
}
