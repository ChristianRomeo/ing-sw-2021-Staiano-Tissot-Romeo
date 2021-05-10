package it.polimi.ingsw.client.GUI;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PrimaryController {

    @FXML
    Label label;
    public Button button;


    @FXML
    private void switchToSecondary() throws IOException {
        GuiView.setRoot("secondary");
    }

    public void fadeOUT(ActionEvent actionEvent) throws IOException {
        label.setOpacity(0);
        GuiView.setRoot("secondary");
    }
}

