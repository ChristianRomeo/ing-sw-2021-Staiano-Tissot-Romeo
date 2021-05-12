package it.polimi.ingsw.client;

public enum Styler {
    ANSI_RED("\u001B[31m"),
    ANSI_GREEN("\u001B[32m"),
    ANSI_YELLOW("\u001B[33m"),
    ANSI_BLUE("\u001B[34m"),
    ANSI_PURPLE("\u001B[35m"),
    ANSI_BOLD("\u001b[1m"),
    ANSI_ITALIC("\u001B[3m"),
    ANSI_UNDERLINED("\u001b[4m"),
    ANSI_REVERSED("\u001b[7m"),
    ANSI_RESET("\u001B[0m"),
    ANSI_LOST("☠️"),
    ANSI_TALK("\uD83D\uDCAC"),
    ANSI_WAIT("💤"),
    ANSI_HELLO("\uD83D\uDC4B"),
    ANSI_INVALID("✋"),
    ANSI_VICTORY("\uD83C\uDFC6"),
    ANSI_STONE("\uD83E\uDEA8"),
    ANSI_SERVANT("\uD83E\uDDCE"),
    ANSI_COIN("\uD83E\uDE99"),
    ANSI_SHIELD("\uD83D\uDEE1"),
    ANSI_PURPLECARD("\uD83D\uDFEA"),
    ANSI_GREENCARD("\uD83D\uDFE9"),
    ANSI_BLUECARD("\uD83D\uDFE6"),
    ANSI_YELLOWCARD("\uD83D\uDFE8"),
    ANSI_TOGIVE("⇤"),
    ANSI_TOHAVE("⇥");

    private final String escape;
    Styler(String escape) {
        this.escape = escape;
    }

    public static String color(char c, String t){
        return switch (c){
            case 'r' -> ANSI_RED;
            case 'g' -> ANSI_GREEN;
            case 'y' -> ANSI_YELLOW;
            case 'b' -> ANSI_BLUE;
            case 'p' -> ANSI_PURPLE;
            default -> ANSI_RESET;
        } + t + ANSI_RESET;
    }

    public static String format(char f, String t){
        return switch (f){
            case 'b' -> ANSI_BOLD;
            case 'i' -> ANSI_ITALIC;
            case 'r' -> ANSI_REVERSED;
            case 'u' -> ANSI_UNDERLINED;
            default -> ANSI_RESET;
        } + t + ANSI_RESET;
    }

    public static String format(char f1, char f2, String t){
        return format(f1, format(f2,t));
    }

    public static String header(String s) {

        String repeat = " ".repeat(Math.max(0, (190 - s.length()) / 2));
        return format('b','r',repeat + s + repeat);

    }

    public static void cls() {

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\033[H\033[2J");

        System.out.println(header(""));
        System.out.println(header("M A S T E R S   O F   R E N A I S S A N C E "));
        System.out.println(header("") + "\n\n\n\n\n\n\n\n\n\n");
    }

    @Override
    public String toString() {
        return escape;
    }

}
