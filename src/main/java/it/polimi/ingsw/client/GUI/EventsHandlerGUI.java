package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.ClientModel;
import it.polimi.ingsw.controller.Events.*;
import javafx.application.Platform;

/**
 * Handles Server incoming Events and dispatch actions, it uses VISITOR PATTERN.
 * This handler is used for the GUI.
 *
 */
public class EventsHandlerGUI implements ServerEventObserver {

    private final ClientModel clientModel;
    private final GuiView guiView;

    /**
     * constructor
     */
    public EventsHandlerGUI(ClientModel clientModel, GuiView guiView){
        this.clientModel =clientModel;
        this.guiView=guiView;
    }

    /**
     * receives an event and updates the scene based on the event type
     * @param event is the received event
     */
    @Override
    public void handleEvent(LeaderCardActionEventS2C event) {
        Platform.runLater(() -> {
            guiView.getSceneController("gameScene").updateScene();
           // guiView.getCurrentSceneController().showMessage(clientModel.getCurrentPlayerNick() + " has activated/discarded a leader card");
        });
    }

    /**
     * receives an event and updates the scene based on the event type
     * @param event is the received event
     */
    @Override
    public void handleEvent(BoughtCardEventS2C event) {
        Platform.runLater(() -> {
            guiView.getSceneController("gameScene").updateScene();
           // guiView.getCurrentSceneController().showMessage(clientModel.getCurrentPlayerNick() + " has bought a card");
        });
    }

    /**
     * receives an event and updates the scene based on the event type
     * @param event is the received event
     */
    @Override
    public void handleEvent(ActivatedProductionEventS2C event) {
        Platform.runLater(() -> {
            guiView.getSceneController("gameScene").updateScene();
           // guiView.getCurrentSceneController().showMessage(clientModel.getCurrentPlayerNick() + " has used his production");
        });
    }

    /**
     * receives an event and updates the scene based on the event type
     * @param event is the received event
     */
    @Override
    public void handleEvent(IncrementPositionEventS2C event) {
        Platform.runLater(() -> {
            guiView.getSceneController("gameScene").updateScene();
           // guiView.getCurrentSceneController().showMessage(event.getPlayerNickname()+ " has proceeded in the faith track");
        });
    }

    /**
     * receives an event and updates the scene based on the event type
     * @param event is the received event
     */
    @Override
    public void handleEvent(VaticanReportEventS2C event) {
        Platform.runLater(() -> {
            guiView.getSceneController("gameScene").updateScene();
           // guiView.getCurrentSceneController().showMessage("A vatican report has been activated");
        });
    }

    /**
     * receives an event and updates the scene based on the event type
     * @param event is the received event
     */
    @Override
    public void handleEvent(UseMarketEventS2C event) {
        Platform.runLater(() -> {
            guiView.getSceneController("gameScene").updateScene();
           // guiView.getCurrentSceneController().showMessage(clientModel.getCurrentPlayerNick() + " has used the market");
        });
    }

    /**
     * receives an event and updates the scene based on the event type
     * @param event is the received event
     */
    @Override
    public void handleEvent(NewTurnEventS2C event) {
        Platform.runLater(() -> {
            if(clientModel.isPregame()){
                guiView.getCurrentSceneController().updateScene();
            }
            if(clientModel.hasGameStarted()){
                guiView.getSceneController("gameScene").updateScene();
            }
        });
    }

    /**
     * receives an event and updates the scene based on the event type
     * @param event is the received event
     */
    @Override
    public void handleEvent(IllegalActionEventS2C event) {
        Platform.runLater(() -> guiView.getCurrentSceneController().showMessage(event.getIllegalAction().getDescription()));
    }

    /**
     * receives an event and updates the scene based on the event type
     * @param event is the received event
     */
    @Override
    public void handleEvent(GameStarterEventS2C event) {

        Platform.runLater(() -> guiView.setCurrentScene("pregameScene"));

    }

    /**
     * receives an event and updates the scene based on the event type
     * @param event is the received event
     */
    @Override
    public void handleEvent(EndGameEventS2C event) {
        Platform.runLater(() -> {
            guiView.getSceneController("endGameScene").updateScene();
            guiView.setCurrentScene("endGameScene");
        });
    }

    /**
     * receives an event and updates the scene based on the event type
     * @param event is the received event
     */
    @Override
    public void handleEvent(LorenzoTurnEventS2C event) {
        Platform.runLater(() -> guiView.getSceneController("gameScene").updateScene());
    }

    /**
     * receives an event and updates the scene based on the event type
     * @param event is the received event
     */
    @Override
    public void handleEvent(EndPreparationEventS2C event) {
        Platform.runLater(() -> {
            guiView.setCurrentScene("gameScene");
            guiView.getCurrentSceneController().updateScene();
        });
    }

    /**
     * receives an event and updates the scene based on the event type
     * @param event is the received event
     */
    @Override
    public void handleEvent(NewConnectionEventS2C event) {

    }
}
