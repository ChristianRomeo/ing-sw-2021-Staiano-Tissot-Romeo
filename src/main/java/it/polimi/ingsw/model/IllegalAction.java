package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * LeaderCardDiscount class contains the illegal actions of the players
 */
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
    /**
     * @return the nickname of the Player who did an illegal action
     */
    public String getPlayerNickname() {
        return playerNickname;
    }

    /**
     * @return the description of the illegal action
     */
    public String getDescription() {
        return description;
    }


}
