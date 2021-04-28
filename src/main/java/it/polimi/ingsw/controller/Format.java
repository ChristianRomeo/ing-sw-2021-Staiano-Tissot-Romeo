package it.polimi.ingsw.controller;
//https://www.lihaoyi.com/post/BuildyourownCommandLinewithANSIescapecodes.html

public enum Format {
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    BOLD("\u001b[1m"),      //BOLD("\ESC[1m);
    ITALIC("\u001B[3m"),    //ESC[3m
    UNDERLINE("\u001b[4m"), //ESC[4m
    REVERSED("\u001b[7m"),  //ESC[7m
    RESET("\u001B[0m"),     //ESC[0m
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
     * @param style The style
     * @param text  The text to be formatted
     * @return The formatted string
     */
    public static String style(char style, String text) {
        Format myStyle = switch (Character.toLowerCase(style)) {
            case 'i' -> ITALIC;
            case 'b' -> BOLD;
            case 'r' -> REVERSED;
            case 'u' -> UNDERLINE;
            default -> RESET;
        };
        return myStyle + text + RESET;
    }

    /**
     * Colored text
     *
     * @param color The color
     * @param text  The text to be colored
     * @return The colored string
     */
    public static String color(char color, String text) {
        Format myColor = switch (Character.toLowerCase(color)) {
            case 'r' -> RED;
            case 'b' -> BLUE;
            case 'g' -> GREEN;
            case 'y' -> YELLOW;
            case 'p' -> PURPLE;
            default -> RESET;
        };
        return myColor + text + RESET;
    }

    /**
     * Heading text
     *
     * @param s The string to be formatted
     * @return The formatted string
     */
    public static String headingText(String s) {
        int max = 167;
        StringBuilder padding = new StringBuilder();
        padding.append(" ".repeat(Math.max(0, (max - s.length()) / 2)));        //check if works

        return BOLD + "" + REVERSED + padding + s + padding + RESET;
    }

    /**
     * Clears the screen and print the default heading
     */
    public static void resetScreen() {
        int height = 50;
        StringBuilder clean = new StringBuilder();
        for (int i = 0; i < height; i = i + 5)
            clean.append("\n\n\n\n\n");

        System.out.print(clean);
        System.out.println(Format.headingText(""));
        System.out.println(Format.headingText("M A S T E R S  O F  R E N A I S S A N C E "));
        System.out.println(Format.headingText("") + "\n\n\n\n");
    }

    /**
     * Clear the server log
     */
    public static void cls() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public String toString() {
        return this.s;
    }

}
