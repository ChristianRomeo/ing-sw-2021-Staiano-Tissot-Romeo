package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.CliView;
import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.GUI.GuiView;
import it.polimi.ingsw.client.Styler;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws FileNotFoundException {
        new Client().init();
    }

    /**
     * Client launcher let the player choose the UI
     */
    //todo: da modificare
    private void init() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        View view;
        boolean correct;

        do {
            Styler.cls();
            System.out.print(" Choose the interface you want to use [CLI/GUI]: ");
            switch (scanner.nextLine().toUpperCase()){
                case "CLI"->{
                    view = new CliView();
                    view.setConnectionHandler(new ServerHandler(view));
                    correct = true;
                    view.launcher();
                }
                case "GUI" -> {
                    view = new GuiView();
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
