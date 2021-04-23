package it.polimi.ingsw.controller.Events;

public class NewConnectionEvent extends  ClientEvent{
    private final String nickname;

    public NewConnectionEvent(String nickname){
        this.nickname = nickname;
    }

    public String getNickname(){
        return nickname;
    }

    @Override
    public void notifyHandler(ClientEventHandler eventHandler){
        eventHandler.handleEvent(this);
    }
}
