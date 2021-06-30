package it.polimi.ingsw.controller.Events;


/**
 *client to server event triggered when a new player has connected
 */
public class NewConnectionEvent extends  ClientEvent {
    private final String nickname;

    /**
     * constructor
     */
    public NewConnectionEvent(String nickname){
        this.nickname = nickname;
    }

    /**
     * getter of the connected player's nickname
     * @return the connected player's nickname
     */
    public String getNickname(){
        return nickname;
    }

    /**
     * notify the eventHandler (which is the virtual view in this case) to handle this specific NewConnectionEvent
     * @param eventHandler is the handler which will handle this specific NewConnectionEvent
     */
    @Override
    public void notifyHandler(ClientEventHandler eventHandler){
        eventHandler.handleEvent(this);
    }
}
