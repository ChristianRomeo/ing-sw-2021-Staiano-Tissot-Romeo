package it.polimi.ingsw.controller.Events;


import java.io.Serializable;

//questo evento è la risposta del server quando un nuovo giocatore si connette,
//gli dice il suo nickname(che può essere stato modificato rispetto a quello scelto)
//e se è il primo giocatore o meno
public class NewConnectionEventS2C extends ServerEvent implements Serializable {

    private final String nickname;
    private final boolean isFirstPlayer;

    public NewConnectionEventS2C(String nickname, boolean isFirstPlayer) {
        this.nickname = nickname;
        this.isFirstPlayer = isFirstPlayer;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isFirstPlayer() {
        return isFirstPlayer;
    }

    @Override
    public void notifyHandler(ServerEventObserver eventHandler){
        eventHandler.handleEvent(this);
    }
}
