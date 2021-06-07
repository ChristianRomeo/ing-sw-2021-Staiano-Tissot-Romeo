package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.ClientModel;
import it.polimi.ingsw.controller.Events.*;
import javafx.application.Platform;

//per ora li tengo separati gli events handler di gui e cli, se no è un casino, però sarebbe meglio
//tenere la parte in cui si settano le cose nel client model in una sola classe, quindi potrei fare
//un events handler apposito

//questo dovrebbe solo mostrare le cose alla gui , in base a che eventi arrivano

/**
 * Handles Server incoming Events and dispatch actions, it uses VISITOR PATTERN.
 * This handler is used for the gui.
 *
 */
public class EventsHandlerGUI implements ServerEventObserver {

    private final ClientModel clientModel;
    private final GuiView guiView;

    public EventsHandlerGUI(ClientModel clientModel, GuiView guiView){
        this.clientModel =clientModel;
        this.guiView=guiView;
    }

    @Override
    public void handleEvent(LeaderCardActionEventS2C event) {
        Platform.runLater(() -> {
            guiView.getSceneController("gameScene").updateScene();
            guiView.getCurrentSceneController().showMessage(clientModel.getCurrentPlayerNick() + " has activated/discarded a leader card");
        });
    }

    @Override
    public void handleEvent(BoughtCardEventS2C event) {
        Platform.runLater(() -> {
            guiView.getSceneController("gameScene").updateScene();
            guiView.getCurrentSceneController().showMessage(clientModel.getCurrentPlayerNick() + " has bought a card");
        });
    }

    @Override
    public void handleEvent(ActivatedProductionEventS2C event) {
        Platform.runLater(() -> {
            guiView.getSceneController("gameScene").updateScene();
            guiView.getCurrentSceneController().showMessage(clientModel.getCurrentPlayerNick() + " has used his production");
        });
    }

    @Override
    public void handleEvent(IncrementPositionEventS2C event) {
        Platform.runLater(() -> {
            guiView.getSceneController("gameScene").updateScene();
            guiView.getCurrentSceneController().showMessage(event.getPlayerNickname()+ " has proceeded in the faith track");
        });
    }

    @Override
    public void handleEvent(VaticanReportEventS2C event) {
        Platform.runLater(() -> {
            guiView.getSceneController("gameScene").updateScene();
            guiView.getCurrentSceneController().showMessage("A vatican report has been activated");
        });
    }

    @Override
    public void handleEvent(UseMarketEventS2C event) {
        Platform.runLater(() -> {
            guiView.getSceneController("gameScene").updateScene();
            guiView.getCurrentSceneController().showMessage(clientModel.getCurrentPlayerNick() + " has used the market");
        });
    }

    @Override
    public void handleEvent(NewTurnEventS2C event) {
        Platform.runLater(() -> {
            if(clientModel.isPregame()){
                guiView.getCurrentSceneController().updateScene();
            }
            if(clientModel.hasGameStarted()){
                guiView.getSceneController("gameScene").updateScene();
                if(clientModel.isCurrentPlayer()){
                    guiView.getCurrentSceneController().showMessage("E' il tuo turno! ");
                }else{
                    guiView.getCurrentSceneController().showMessage("E' il turno di: "+ clientModel.getCurrentPlayerNick());
                }
            }
        });
    }

    @Override
    public void handleEvent(IllegalActionEventS2C event) {
        Platform.runLater(() -> guiView.getCurrentSceneController().showMessage("Illegal action: "+event.getIllegalAction().getDescription()));
    }

    @Override
    public void handleEvent(GameStarterEventS2C event) {

        Platform.runLater(() -> guiView.setCurrentScene("pregameScene"));

    }

    @Override
    public void handleEvent(EndGameEventS2C event) {
        Platform.runLater(() -> {
            guiView.getSceneController("endGameScene").updateScene();
            guiView.setCurrentScene("endGameScene");
        });
    }

    @Override
    public void handleEvent(LorenzoTurnEventS2C event) {
        Platform.runLater(() -> {
            guiView.getSceneController("gameScene").updateScene();
        });
    }

    @Override
    public void handleEvent(EndPreparationEventS2C event) {
        Platform.runLater(() -> {
            guiView.setCurrentScene("gameScene");
            guiView.getCurrentSceneController().updateScene();
            if(clientModel.isCurrentPlayer()){
                guiView.getCurrentSceneController().showMessage("E' il tuo turno! ");
            }else{
                guiView.getCurrentSceneController().showMessage("E' il turno di: "+ clientModel.getCurrentPlayerNick());
            }
        });
    }

    @Override
    public void handleEvent(NewConnectionEventS2C event) {

    }
}
