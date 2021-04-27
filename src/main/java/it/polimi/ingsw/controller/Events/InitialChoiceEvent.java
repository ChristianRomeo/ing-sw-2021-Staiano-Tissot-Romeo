package it.polimi.ingsw.controller.Events;

public class InitialChoiceEvent extends ClientEvent {

    //todo: qua po vedo che mettere
    //ci sarannole scelte iniziali dell'utente



    @Override
    public void notifyHandler(ClientEventHandler eventHandler){
        eventHandler.handleEvent(this);
    }
}
