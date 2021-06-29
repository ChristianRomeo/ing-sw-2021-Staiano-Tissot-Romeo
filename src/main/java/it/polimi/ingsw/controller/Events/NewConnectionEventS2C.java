package it.polimi.ingsw.controller.Events;




//questo evento è la risposta del server quando un nuovo giocatore si connette,
//gli dice il suo nickname(che può essere stato modificato rispetto a quello scelto)
//e se è il primo giocatore o meno

/**
 *server to client event triggered when a new player has connected,
 * it communicates the nickname (in fact it could have been edited in respect to the choice of the player)
 * and if it's the first player or not
 */
public class NewConnectionEventS2C extends ServerEvent {

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
