package it.polimi.ingsw.controller;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws FileNotFoundException {
        Client client = new Client();
        client.launch();
    }

    /**
     * ClientLauncher launcher. Asks the preferred UI and launches it.
     */
    private void launch() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        ServerHandler serverHandler = new ServerHandler();    //come clientHandler però dall'altro lato
        View view;

        Format.resetScreen();
        boolean correct;
        do {
            System.out.print(Format.style('b', " Choose the interface you want to use [CLI/GUI]: "));
            switch (scanner.nextLine()){
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
                    Format.resetScreen();
                    System.out.println(Format.color('r', "  > Invalid choice. Try again."));
                    correct = false;
                }
            }
        } while (!correct);
    }
}
