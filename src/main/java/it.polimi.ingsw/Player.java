package it.polimi.ingsw;

public class Player {
    private String nickname;
    private int victoryPoints;
    private StatusPlayer statusPlayer;

    public String getNickname() {
      return nickname;
    }
    public int getVictoryPoints() {
        return victoryPoints;
    }
    public StatusPlayer getStatusPlayer(){
        return statusPlayer;
    }
    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }
    public void calculateAndSetVictoryPoints() {
        int sum = 0;
        int totalNumOfResources = 0;
        int popFavorTileMinNumOfVP = 2; //minimum number of

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
        else if(statusPlayer.getFaithTrackPosition() == 24)
            sum += 20;
        /*
        calculate victory points based on Leader cards.
        Only activated Leader cards points are being added.
        2 is the amount of Leader cards per player.
         */
        for(int i = 0; i < 2; i++) {
            if (statusPlayer.getPlayerLeaderCards()[i].isActivated())
                sum += statusPlayer.getPlayerLeaderCards()[i].getVictoryPoints();
        }
        //calculate victory points based on Development cards
        for(int i = 0; i < statusPlayer.getPersonalCardBoard().getNumberOfCards(); i++) {
            for (int j = 0; j < statusPlayer.getPersonalCardBoard().getNumberOfCards(); i++)
                if (! (statusPlayer.getPersonalCardBoard().getCard(i, j) == null))
                    sum += statusPlayer.getPersonalCardBoard().getCard(i, j).getVictoryPoints();
        }

        //calculate victory points based on Strongbox Resources
        //statusPlayer.getStrongboxResources().forEach((resource, numOfResource) -> totalNumOfResources += numOfResource);
        // int totalNumOfResources = statusPlayer.getStrongboxResources().values().stream().reduce(0, Integer::tot);
        //Collection<Integer> vals = statusPlayer.getStrongboxResources().values();
       // vals.forEach(totalNumOfResources += vals);
        totalNumOfResources = statusPlayer.getStrongboxResources().values().stream().mapToInt(Integer::intValue).sum();
        /*calculate victory points based on Pop Favor Tiles.
        The number of points given are fixed, and specifically the minimum number of Victory Points assigned
        (if any) is 2, while the maximum number is 4.
        */
        if(isBetween(statusPlayer.getFaithTrackPosition(), 5, 8))
            sum += popFavorTileMinNumOfVP;
        else if(isBetween(statusPlayer.getFaithTrackPosition(), 12, 16))
            sum += popFavorTileMinNumOfVP + 1;
        else if(isBetween(statusPlayer.getFaithTrackPosition(), 19, 24))
            sum += popFavorTileMinNumOfVP + 2;


    }

    }
