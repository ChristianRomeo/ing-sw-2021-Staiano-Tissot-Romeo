package it.polimi.ingsw.model;

public class IllegalAction {
    Player player;
    String description;

    public IllegalAction(Player player, String description) {
        this.player = player;
        this.description = description;
    }

    public Player getPlayer() {
        return player;
    }

    public String getDescription() {
        return description;
    }
}
