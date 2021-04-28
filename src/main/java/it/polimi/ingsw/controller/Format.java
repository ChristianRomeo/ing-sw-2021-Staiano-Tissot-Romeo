package it.polimi.ingsw.controller;
//See: https://www.lihaoyi.com/post/BuildyourownCommandLinewithANSIescapecodes.html

import java.text.Normalizer;

public enum Format {
    RED("\u001B[31m"),  //for warnings
    GREEN("\u001B[32m"),//for victory
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    RESET("\u001B[0m"),     //ESC[0m
    BOLD("\u001b[1m"),      //BOLD("\ESC[1m);
    ITALIC("\u001B[3m"),    //ESC[3m
    UNDERLINE("\u001b[4m"), //ESC[4m
    REVERSED("\u001b[7m"),  //ESC[7m
    DEATH("☠️"),
    TALK("\uD83D\uDCAC"),
    SLEEP("💤"),
    HELLO("\uD83D\uDC4B"),
    CANT("✋"),
    VICTORY("\uD83C\uDFC6");

    private final String s;
    Format(String s) {
        this.s=s;
    }

    /**
     * Returns a string formatted as wanted
     *
     * @param style The chosen style
     * @param text  The text to be formatted
     * @return string The formatted text
     */
    public static String style(char style, String text) {
        Format theStyle = switch (Character.toLowerCase(style)) {
            case 'i' -> ITALIC;
            case 'b' -> BOLD;
            case 'r' -> REVERSED;
            case 'u' -> UNDERLINE;
            default -> RESET;
        };
        //the strings needs reset escape code at the end
        return theStyle + text + RESET;
    }

    /**
     * Returns a string colored as wanted
     *
     * @param color The chosen color
     * @param text  The text to be colored
     * @return string The colored text
     */
    public static String color(char color, String text) {
        Format theColor = switch (Character.toLowerCase(color)) {
            case 'r' -> RED;
            case 'b' -> BLUE;
            case 'g' -> GREEN;
            case 'y' -> YELLOW;
            case 'p' -> PURPLE;
            default -> RESET;
        };
        //the strings needs reset escape code at the end
        return theColor + text + RESET;
    }

    /**
     * Header
     *
     * @param s The string to be formatted
     * @return string The formatted string
     */
    public static String headingText(String s) {
        int maxLength = 170;
        
        String repeat = " ".repeat(Math.max(0, (maxLength - s.length()) / 2));
        return BOLD + "" + REVERSED + repeat + s + repeat + RESET;
    }

    //per fare prove
    /*public static void main(String[] args){
        System.out.println(Format.headingText("PROVA"));

        resetScreen();

    }*/

    /**
     * Clears the screen and print the default header
     */
    public static void resetScreen() {

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\033[H\033[2J");
        System.out.flush();

        System.out.println(Format.headingText(""));
        System.out.println(Format.headingText("M A S T E R S   O F   R E N A I S S A N C E "));
        System.out.println(Format.headingText("") + "\n\n\n");
    }

    public String toString() {
        return this.s;
    }

}
