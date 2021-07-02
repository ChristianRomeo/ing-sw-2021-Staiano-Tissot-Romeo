package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.ClientModel;
import it.polimi.ingsw.client.ServerHandler;

/**
 * abstract class from where all the Scene controllers are extended
 */
public abstract class FXMLController {
    protected ClientModel clientModel;
    protected ServerHandler serverHandler;
    protected GuiView guiView;

    /**
     * @param clientModel is the clientModel to be set
     */
    public void setClientModel(ClientModel clientModel){
        this.clientModel=clientModel;
    }

    /**
     * @param serverHandler is the serverHandler to be set
     */
    public void setServerHandler(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    /**
     * @param guiView is the guiView to be set
     */
    public void setGuiView(GuiView guiView){
        this.guiView = guiView;
    }

    /**
     * updated scene, to be overridden by the Scene controllers
     */
    public void updateScene(){

    }

    /**
     * show messages for illegal actions, to be overridden
     * @param message is the message to be shown
     */
    public void showMessage(String message){

    }

}
