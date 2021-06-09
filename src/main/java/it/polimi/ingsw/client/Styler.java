package it.polimi.ingsw.client;

import java.io.PrintWriter;

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
    ANSI_HELLO("\uD83D\uDC4B"),
    ANSI_INVALID("✋"),
    ANSI_VICTORY("\uD83C\uDFC6"),
    ANSI_STONE("\uD83D\uDDFF"),
    ANSI_SERVANT("\uD83D\uDE4D"),
    ANSI_COIN("\uD83D\uDCB2"),
    ANSI_SHIELD("\uD83D\uDEE1"),
    ANSI_PURPLECARD("\uD83D\uDFEA"),
    ANSI_GREENCARD("\uD83D\uDFE9"),
    ANSI_BLUECARD("\uD83D\uDFE6"),
    ANSI_YELLOWCARD("\uD83D\uDFE8");

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

    public static void header(){
        System.out.println(color('y', """
                                
                                
                ███╗   ███╗ █████╗ ███████╗████████╗███████╗██████╗ ███████╗     ██████╗ ███████╗
                ████╗ ████║██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝    ██╔═══██╗██╔════╝
                ██╔████╔██║███████║███████╗   ██║   █████╗  ██████╔╝███████╗    ██║   ██║█████╗ \s
                ██║╚██╔╝██║██╔══██║╚════██║   ██║   ██╔══╝  ██╔══██╗╚════██║    ██║   ██║██╔══╝ \s
                ██║ ╚═╝ ██║██║  ██║███████║   ██║   ███████╗██║  ██║███████║    ╚██████╔╝██║    \s
                ╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝     ╚═════╝ ╚═╝    \s
                                                                                                \s
                ██████╗ ███████╗███╗   ██╗ █████╗ ██╗███████╗███████╗ █████╗ ███╗   ██╗ ██████╗███████╗
                ██╔══██╗██╔════╝████╗  ██║██╔══██╗██║██╔════╝██╔════╝██╔══██╗████╗  ██║██╔════╝██╔════╝
                ██████╔╝█████╗  ██╔██╗ ██║███████║██║███████╗███████╗███████║██╔██╗ ██║██║     █████╗ \s
                ██╔══██╗██╔══╝  ██║╚██╗██║██╔══██║██║╚════██║╚════██║██╔══██║██║╚██╗██║██║     ██╔══╝ \s
                ██║  ██║███████╗██║ ╚████║██║  ██║██║███████║███████║██║  ██║██║ ╚████║╚██████╗███████╗
                ╚═╝  ╚═╝╚══════╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝╚══════╝
                Powered by GC24:    E. Staiano,     T. Tissot,       C. Romeo
                                
                # Press Enter to begin... #
                                                                                                      \s"""));
    }

    public static void cls() {
        new PrintWriter(System.out,true).println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\033[H\033[2J");
        System.out.println(color('g', """
                ┌┬┐┌─┐┌─┐┌┬┐┌─┐┬─┐┌─┐  ┌─┐┌─┐  ┬─┐┌─┐┌┐┌┌─┐┬┌─┐┌─┐┌─┐┌┐┌┌─┐┌─┐
                │││├─┤└─┐ │ ├┤ ├┬┘└─┐  │ │├┤   ├┬┘├┤ │││├─┤│└─┐└─┐├─┤││││  ├┤\s
                ┴ ┴┴ ┴└─┘ ┴ └─┘┴└─└─┘  └─┘└    ┴└─└─┘┘└┘┴ ┴┴└─┘└─┘┴ ┴┘└┘└─┘└─┘
                \s"""));
    }

    @Override
    public String toString() {
        return escape;
    }
}
