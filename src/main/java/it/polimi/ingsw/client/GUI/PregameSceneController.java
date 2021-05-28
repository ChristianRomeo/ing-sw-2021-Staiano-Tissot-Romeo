package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.controller.Events.InitialChoiceEvent;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.SameTypePair;
import javafx.fxml.FXML;

import javafx.scene.control.*;


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

    @Override
    public void updateScene(){
        if(clientModel.isCurrentPlayer()){
            upperLabel.setText("E' il tuo turno, scegli le carte che vuoi scartare: ");
            firstCardButton.setVisible(true);
            secondCardButton.setVisible(true);
            thirdCardButton.setVisible(true);
            fourthCardButton.setVisible(true);
            //e qui mostro carte, o comunque le rendo visibili
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
        //qui dentro rendo visibile la parte per scegliere le risorse, poi lui fa click di altri bottoni e succedono altre cose
        //poi lo faccio, mo metto cose a caso

        if(clientModel.getMyIndex()>=1){
            resource1=Resource.COIN;
            position1 = new SameTypePair<>(2,1);
            if(clientModel.getMyIndex()==3){
                resource2= Resource.COIN;
                position2 = new SameTypePair<>(2,2);
            }
        }
        serverHandler.send(new InitialChoiceEvent(removedLeaderCard1,removedLeaderCard2,resource1,resource2,position1,position2));
    }
}
