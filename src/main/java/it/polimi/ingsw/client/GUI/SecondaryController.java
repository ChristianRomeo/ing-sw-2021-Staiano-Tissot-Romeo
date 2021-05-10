package it.polimi.ingsw.client.GUI;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SecondaryController {
    @FXML
    public Label label;
    public Button button;

    @FXML
    private void switchToPrimary() throws IOException {
        GuiView.setRoot("primary");
    }

    public void fadeIN(ActionEvent actionEvent) throws IOException {
        label.setOpacity(1);
        GuiView.setRoot("primary");
    }

}