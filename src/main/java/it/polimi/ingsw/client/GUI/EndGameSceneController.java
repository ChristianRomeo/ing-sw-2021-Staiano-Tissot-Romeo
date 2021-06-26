package it.polimi.ingsw.client.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.List;
import java.util.Map;

public class EndGameSceneController extends FXMLController {

    @FXML
    private Label winOrLoseLabel;
    @FXML
    private Label usernameLabel1;
    @FXML
    private Label usernameLabel2;
    @FXML
    private Label usernameLabel3;
    @FXML
    private Label usernameLabel4;
    @FXML
    private Label victoryPointsLabel1;
    @FXML
    private Label victoryPointsLabel2;
    @FXML
    private Label victoryPointsLabel3;
    @FXML
    private Label victoryPointsLabel4;
    @FXML
    private Label isWinnerLabel1;
    @FXML
    private Label isWinnerLabel2;
    @FXML
    private Label isWinnerLabel3;
    @FXML
    private Label isWinnerLabel4;

    @FXML
    private Button exitLadderBoardButton;

    /**
     * updates the endGameScene
     * it shows the winners and all the players victory points at the end of the match
     */
    @Override
    public void updateScene(){
        List<String> winners = clientModel.getWinners();
        Map<String,Integer> victoryPoints= clientModel.getVictoryPoints();

        isWinnerLabel1.setText("");
        isWinnerLabel2.setText("");
        isWinnerLabel3.setText("");
        isWinnerLabel4.setText("");
        usernameLabel1.setText("");
        usernameLabel2.setText("");
        usernameLabel3.setText("");
        usernameLabel4.setText("");
        victoryPointsLabel1.setText("");
        victoryPointsLabel2.setText("");
        victoryPointsLabel3.setText("");
        victoryPointsLabel4.setText("");

        if(winners.contains(clientModel.getMyNickname())){
            winOrLoseLabel.setText("YOU WON!!!");
        }else{
            winOrLoseLabel.setText("YOU LOST!!!");
        }

        if(clientModel.getNumPlayers()>0){
            usernameLabel1.setText(clientModel.getNicknames().get(0));
            victoryPointsLabel1.setText(" : " + victoryPoints.get(clientModel.getNicknames().get(0))+ "victory points.");
            if(winners.contains(clientModel.getNicknames().get(0))){
                isWinnerLabel1.setText("Is a winner!");
            }
        }
        if(clientModel.getNumPlayers()>1){
            usernameLabel2.setText(clientModel.getNicknames().get(1));
            victoryPointsLabel2.setText(" : " + victoryPoints.get(clientModel.getNicknames().get(1))+ "victory points.");
            if(winners.contains(clientModel.getNicknames().get(1))){
                isWinnerLabel2.setText("Is a winner!");
            }
        }
        if(clientModel.getNumPlayers()>2){
            usernameLabel3.setText(clientModel.getNicknames().get(2));
            victoryPointsLabel3.setText(" : " + victoryPoints.get(clientModel.getNicknames().get(2))+ "victory points.");
            if(winners.contains(clientModel.getNicknames().get(2))){
                isWinnerLabel3.setText("Is a winner!");
            }
        }
        if(clientModel.getNumPlayers()>3){
            usernameLabel4.setText(clientModel.getNicknames().get(3));
            victoryPointsLabel4.setText(" : " + victoryPoints.get(clientModel.getNicknames().get(3))+ "victory points.");
            if(winners.contains(clientModel.getNicknames().get(3))){
                isWinnerLabel4.setText("Is a winner!");
            }
        }

    }

    /**
     * exits from the ladderBoard
     */
    @FXML
    public void exitLadderBoard(){
        System.exit(0);
    }

}
