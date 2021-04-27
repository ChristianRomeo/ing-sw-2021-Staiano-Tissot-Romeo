package it.polimi.ingsw.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class InputValidator {

    /**
     * Tests if the input is a correct number of players
     * @param num  The entered text
     * @return  The null value if the string is not a correct value, otherwise its integer value
     */
    public Integer isNumPlayers(String num) {
        int numPlayers = 0;
        try {
            numPlayers = Integer.parseInt(num);
        } catch (NumberFormatException ignored) {}

        return (numPlayers ==1 || numPlayers ==2 || numPlayers ==3 || numPlayers ==4)? numPlayers : null;
    }

    /**
     * Tests if the input is a valid card position
     * @param num  The entered text
     * @return  The null value if the string is not a correct value, otherwise a map with row and column
     */
    public TreeSet<Integer> isLeaderCard(String num) {
        int first=5, last=5;
        try {
            first = Integer.parseInt(String.valueOf(num.charAt(0)));
            last = Integer.parseInt(String.valueOf(num.charAt(1)));
        } catch (NumberFormatException ignored) {}
        TreeSet<Integer> s1 = new TreeSet<>();
        s1.add(first);
        s1.add(last);
        return (first < 5 || last < 5 && first!=last)? s1 : null;
    }

    /**
     * Tests if the input is a valid card position
     * @param num  The entered text
     * @return  The null value if the string is not a correct value, otherwise a map with row and column
     */
    public Map<Integer,Integer> isNumCard(String num) {
        int row=9, col=9;
        try {
            row = Integer.parseInt(String.valueOf(num.charAt(0)));
            col = Integer.parseInt(String.valueOf(num.charAt(1)));
        } catch (NumberFormatException ignored) {}
        return (row < 3 || col < 4)? new HashMap<>(row,col) : null;
    }

    /**
     * Tests if the input is a nickname
     * @param nickname  The entered string
     * @return  True if the nickname is valid, false otherwise
     */
    public boolean isNickname(String nickname) {
        String expression = "^[\\p{Alnum}\\s._-]+$";
        return nickname.matches(expression);
    }

}
