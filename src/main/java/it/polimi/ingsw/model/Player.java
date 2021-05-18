package it.polimi.ingsw.model;

/**
 * @author tommy
 */
public class Player {
    /**
     * The player's leader cards number, constant and common to all players
     */
    private static final int LEADER_CARDS_OWNED = 2;
    /**
     * The name chosen by the physical player
     */
    private final String nickname;
    private int victoryPoints;
    private final StatusPlayer statusPlayer;
    private boolean isWinner;
    private boolean isConnected;

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public String getNickname() {
        return nickname;
    }

    /**
     *
     * @return victory points of the player
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * Constructor
     * @param nickname il nickname
     */
    public Player(String nickname){
        //costruttore creato a caso solo per fare testing
        statusPlayer = new StatusPlayer();
        isWinner=false;
        this.nickname=nickname;
    }

    /**
     *getter of StatusPlayer
     */
    public StatusPlayer getStatusPlayer(){
        return statusPlayer;
    }

    /**
     * @param x number to check
     * @param lower lower bound of the range to check
     * @param upper upper bound of the range to check
     * Checks if the number x is between the range [lower,upper]
     */
    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    /**
     * Calculate VP
     */
    public void calculateAndSetVictoryPoints() {
        int sum = 0, popFavorTileMinNumOfVP = 2;//minimum number of victory points given, if any
        double totalNumOfResources;

        //calculate victory points based on faith track position.
        if (isBetween(statusPlayer.getFaithTrackPosition(), 3, 5))
            sum += 1;
        else if (isBetween(statusPlayer.getFaithTrackPosition(), 6, 8))
            sum += 2;
        else if (isBetween(statusPlayer.getFaithTrackPosition(), 9, 11))
            sum += 4;
        else if (isBetween(statusPlayer.getFaithTrackPosition(), 12, 14))
            sum += 6;
        else if (isBetween(statusPlayer.getFaithTrackPosition(), 15, 17))
            sum += 9;
        else if (isBetween(statusPlayer.getFaithTrackPosition(), 18, 20))
            sum += 12;
        else if (isBetween(statusPlayer.getFaithTrackPosition(), 21, 23))
            sum += 16;
        else if (statusPlayer.getFaithTrackPosition() == 24)
            sum += 20;
        /*
        calculate victory points based on Leader cards.
        Only activated Leader cards points are being added.
        2 is the amount of Leader cards per player.
         */
        for (int i = 0; i < statusPlayer.getPlayerLeaderCards().size(); ++i)
            if (statusPlayer.getLeaderCard(i).isActivated())
                sum += statusPlayer.getLeaderCard(i).getVictoryPoints();

        //calculate victory points based on Development cards
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j)
                if (statusPlayer.getPersonalCardBoard().getCard(i, j) != null)
                    sum += statusPlayer.getPersonalCardBoard().getCard(i, j).getVictoryPoints();

        /*increase sum based on Pop Favor Tiles.
        The number of points given are fixed, and specifically the minimum number of Victory Points assigned
        (if any) is 2 (see PopFavorTileMinNumOfVP), while the maximum number is 4.*/
       for(int i = 1; i <= 3; ++i) {
            if (statusPlayer.getPopeFavorTile(i)==PopeFavorTileStatus.ACTIVE)
                sum += popFavorTileMinNumOfVP;
            ++popFavorTileMinNumOfVP;
        }
        //calculate victory points based on number of owned resources
        totalNumOfResources = statusPlayer.getResourcesNumber();
        sum += Math.floor(totalNumOfResources / 5);

        //final result: Victory Points
        victoryPoints = sum;
    }

    public void setIsWinner(){
        this.isWinner=true;
    }

    public boolean isWinner() { return isWinner; }

}
