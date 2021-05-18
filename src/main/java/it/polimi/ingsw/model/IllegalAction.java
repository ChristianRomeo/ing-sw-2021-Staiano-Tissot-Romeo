package it.polimi.ingsw.model;

import java.io.Serializable;

public class IllegalAction implements Serializable {
    private final String playerNickname;
    private final String description;

    public IllegalAction(String playerNickname, String description) {
        this.playerNickname = playerNickname;
        this.description = description;
    }

    public IllegalAction(Player player, String description){
        this.playerNickname=player.getNickname();
        this.description=description;
    }

    public String getPlayerNickname() {
        return playerNickname;
    }

    public String getDescription() {
        return description;
    }


}
