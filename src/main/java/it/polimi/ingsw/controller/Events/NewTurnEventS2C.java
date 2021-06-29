package it.polimi.ingsw.controller.Events;

/**
 *server to client event triggered when a new turn happens
 */
public class NewTurnEventS2C extends ServerEvent{
    private final String nickname; //it's his turn

    public NewTurnEventS2C(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public void notifyHandler(ServerEventObserver eventHandler){
        eventHandler.handleEvent(this);
    }
}
