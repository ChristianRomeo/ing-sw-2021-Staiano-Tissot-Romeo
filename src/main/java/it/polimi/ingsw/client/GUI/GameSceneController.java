package it.polimi.ingsw.client.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class GameSceneController extends FXMLController {

    @FXML
    private Label messageLabel;
    @FXML
    private Button leaderActionButton;

    @Override
    public void updateScene(){
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
}
