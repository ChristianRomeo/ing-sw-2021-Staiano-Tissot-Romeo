package it.polimi.ingsw.controller;

import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Client client = new Client();
        client.launch();
    }

    /**
     * ClientLauncher launcher. Asks the preferred UI and launches it.
     */
    private void launch() {
        Scanner scanner = new Scanner(System.in);
        ServerHandler serverHandler = new ServerHandler();    //come clientHandler però dall'altro lato
        //View view;  //la vera e propria view

        Format.resetScreen();
        boolean incorrect;
        do {
            System.out.print(Format.style('b', " Choose the interface you want to use [CLI/GUI]: "));
            String preferredInterface = scanner.nextLine();
            switch (scanner.nextLine()){
                case "CLI"->{
                    //view = new CliView();
                    //serverHandler.setView(view);
                    //view.setServerHandler(serverHandler);
                    incorrect = false;
                    //view.launch();
                }
                case "GUI" -> {
                    //view = new GuiView();
                    //serverHandler.setView(view);
                    //view.setServerHandler(serverHandler);
                    incorrect = false;
                    //view.launch();
                }
                default -> {
                    Format.resetScreen();
                    System.out.println(Format.color('r', "  > Invalid choice. Try again."));
                    incorrect = true;
                }
            }
        } while (incorrect);
    }
}
