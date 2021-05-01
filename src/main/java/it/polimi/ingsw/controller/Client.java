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
    //todo: da modificare
    private void init() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        OldServerHandler oldServerHandler = new OldServerHandler();    //come clientHandler però client side
        View view;
        boolean correct;

        do {
            //reset screen
            System.out.print(" Choose the interface you want to use [CLI/GUI]: ");
            switch (scanner.nextLine().toUpperCase()){
                case "CLI"->{
                    view = new CliView();
                    oldServerHandler.setView(view);
                    view.setServerHandler(oldServerHandler);
                    correct = true;
                    view.launch();
                }
                case "GUI" -> {
                    //view = new GuiView();
                    //oldServerHandler.setView(view);
                    //view.setServerHandler(oldServerHandler);
                    correct = true;
                    //view.launch();
                }
                default -> {
                    System.out.println("Invalid choice, try again: ");
                    correct = false;
                }
            }
        } while (!correct);
    }
}
