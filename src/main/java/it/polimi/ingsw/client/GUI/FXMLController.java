package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.ClientModel;
import it.polimi.ingsw.client.ServerHandler;

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

    public void updateScene(){

    }

    public void showMessage(String message){

    }

    public void updateWidthConstraints(double doubleValue){
    }

    public  void updateHeightConstraints(double doubleValue){
    }
}
