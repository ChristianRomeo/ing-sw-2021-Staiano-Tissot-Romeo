package it.polimi.ingsw.controller;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws FileNotFoundException {
        Client client = new Client();
        client.init();
    }

    /**
     * Client launcher let the player choose the UI
     */
    private void init() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        ServerHandler serverHandler = new ServerHandler();    //come clientHandler però client side
        View view;
        boolean correct;

        do {
            Format.resetScreen();
            System.out.print(Format.style('b', " Choose the interface you want to use [CLI/GUI]: "));
            switch (scanner.nextLine().toUpperCase()){
                case "CLI"->{
                    view = new CliView();
                    serverHandler.setView(view);
                    view.setServerHandler(serverHandler);
                    correct = true;
                    view.launch();
                }
                case "GUI" -> {
                    view = new GuiView();
                    serverHandler.setView(view);
                    view.setServerHandler(serverHandler);
                    correct = true;
                    view.launch();
                }
                default -> {
                    System.out.println(Format.color('r', Format.CANT +"Invalid choice, try again: "));
                    correct = false;
                }
            }
        } while (!correct);
    }
}
