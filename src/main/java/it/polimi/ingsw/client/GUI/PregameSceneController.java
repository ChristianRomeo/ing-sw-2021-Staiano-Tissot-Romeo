package it.polimi.ingsw.client.GUI;

import javafx.fxml.FXML;

import javafx.scene.control.*;


public class PregameSceneController extends FXMLController{
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
}
