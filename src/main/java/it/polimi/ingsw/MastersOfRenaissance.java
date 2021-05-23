package it.polimi.ingsw;

import it.polimi.ingsw.client.CliView;
import it.polimi.ingsw.client.GUI.GuiView;
import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.Styler;
import it.polimi.ingsw.controller.Server;
import it.polimi.ingsw.controller.View;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The entrypoint of the MOR game.
 *
 * @see it.polimi.ingsw.client
 * @see it.polimi.ingsw.controller
 */
public class MastersOfRenaissance {
    /**
     * Initializes and launches the MOR game.
     * To run a GUI client, do not specify any command line argument or specify "gui".
     * To run a CLI client, specify "cli" as the first command line argument.
     * To run the server, specify "server" as the first command line argument.
     *
     * @param args the cmd arguments
     */
    public static void main(String[] args) throws FileNotFoundException {

        switch (args[0].toUpperCase()) {
            case "CLI" -> launchCli();
            case "SERVER" -> launchServer();
            default -> launchGui();
        }
    }

    private static void launchCli() throws FileNotFoundException {
        View view = new CliView();
        view.setConnectionHandler(new ServerHandler(view));
        view.launcher();
        Styler.cls();
    }

    private static void launchGui() {
        View view = new GuiView();
        //view.setConnectionHandler(new ServerHandler(view));
        //view.launch();
    }

    private static void launchServer() {
        try{
            Server.main(null);
        } catch (IOException e){
            System.out.println("Error initializing server "+ e);
            System.exit(1);
        }
    }

}
