package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.ClientModel;
import it.polimi.ingsw.client.ServerHandler;

public abstract class FXMLController {
    //un controller di un fxml, non so se sarà utile o no sta classe
    protected ClientModel clientModel;
    protected ServerHandler serverHandler;

    public void setClientModel(ClientModel clientModel){
        this.clientModel=clientModel;
    }

    public void setServerHandler(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    public void updateScene(){

    }

    public void showMessage(String message){

    }
}
