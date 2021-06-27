package it.polimi.ingsw.model;

/**
 * @author tommy
 */
public class Player {
    /**
     * The name chosen by the physical player
     */
    private final String nickname;
    private int victoryPoints;
    private final StatusPlayer statusPlayer;
    private boolean isWinner;

    /**
     * @return the player's nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
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
     * Calculate victory points, an overall sum is calculated:
     * Firstly it calculates victory points obtained with the Faith Track position.
     * Then, it calculates victory points obtained with Leader cards
     * (only activated Leader cards points are being added).
     * Then, it calculates victory points obtained with Development Cards
     * Then, it considers the Pop favor tiles
     * (the number of points given are fixed, and specifically the minimum number of victory points assigned
     * (if any) is 2 (see PopFavorTileMinNumOfVP), while the maximum number is 4.)
     * Last but not least, it calculates victory points based on the number of owned resources
     * (every 5 resources rounded down equals 1 victory point)
     *Finally, it calculates the overall sum.
     */
    public void calculateAndSetVictoryPoints() {
        int sum = 0, popFavorTileMinNumOfVP = 2;//minimum number of victory points given, if any
        double totalNumOfResources;

        //starts calculating VP based on Faith Track position
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
        //starts calculating VP based on Leader cards

        for (int i = 0; i < statusPlayer.getPlayerLeaderCards().size(); ++i)
            if (statusPlayer.getLeaderCard(i).isActivated())
                sum += statusPlayer.getLeaderCard(i).getVictoryPoints();

        //starts calculating VP based on Development cards
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j)
                if (statusPlayer.getPersonalCardBoard().getCard(i, j) != null)
                    sum += statusPlayer.getPersonalCardBoard().getCard(i, j).getVictoryPoints();

        //starts calculating VP based on Pope Favor tiles
       for(int i = 1; i <= 3; ++i) {
            if (statusPlayer.getPopeFavorTile(i)==PopeFavorTileStatus.ACTIVE)
                sum += popFavorTileMinNumOfVP;
            ++popFavorTileMinNumOfVP;
        }
        //starts calculating VP based on the number of owned resources
        totalNumOfResources = statusPlayer.getResourcesNumber();
        sum += Math.floor(totalNumOfResources / 5);

        //final result: Victory Points
        victoryPoints = sum;
    }

    /**
     * sets the boolean variable isWinner to true.
     */
    public void setIsWinner(){
        this.isWinner=true;
    }

    /**
     * @return true if the player is a winner, false otherwise
     */
    public boolean isWinner() { return isWinner; }

}
