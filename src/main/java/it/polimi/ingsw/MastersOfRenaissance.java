package it.polimi.ingsw;

import com.google.gson.Gson;
import it.polimi.ingsw.client.CliView;
import it.polimi.ingsw.client.GUI.GuiView;
import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.controller.Configs;
import it.polimi.ingsw.controller.Server;
import it.polimi.ingsw.controller.View;
import java.io.*;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * The entrypoint of the MOR game.
 *
 * @see it.polimi.ingsw.client
 * @see it.polimi.ingsw.controller
 */
public class MastersOfRenaissance {
     public static FileInputStream save=null;
     private static Configs config=null;
    /**
     * Initializes and launches the MOR game.
     * To run a GUI client, do not specify any command line argument or specify "gui".
     * To run a CLI client, specify "cli" as the first command line argument.
     * To run the server, specify "server" as the first command line argument.
     *
     * @param args the cmd arguments
     */
    public static void main(String[] args) throws IOException, URISyntaxException {

        if (args.length == 0)
            launchGui(Configs.class.getClassLoader().getResourceAsStream("configs.json"));
        else
            if (args.length==2){
                if(args[1] != null && !args[1].trim().isEmpty()) {    //il file deve essere nello stesso percorso per evitare slash - backslash problem
                    if (save == null)
                        save = new FileInputStream(Objects.requireNonNull(args[1]));
                    switch (args[0].toUpperCase()) {
                        case "CLI" -> launchCli(save);
                        case "SERVER" -> launchServer(save);
                        default -> launchGui(save);
                    }
                }
            }
            else
                switch (args[0].toUpperCase()) {
                    case "CLI" -> launchCli(Configs.class.getClassLoader().getResourceAsStream("configs.json"));
                    case "SERVER" -> launchServer(Configs.class.getClassLoader().getResourceAsStream("configs.json"));
                    default -> launchGui(Configs.class.getClassLoader().getResourceAsStream("configs.json"));
                }

    }

    private static void launchCli(InputStream in){
        View view = new CliView();
        if(config==null)
            config = new Gson().fromJson(new InputStreamReader(in), Configs.class);
        view.setConnectionHandler(new ServerHandler(view,config));
        view.launcher();
    }

    private static void launchGui(InputStream in) {

        System.out.println("Initializing GUI... ");
        Configs config = new Gson().fromJson(new InputStreamReader(in), Configs.class);
        GuiView.main(config);

    }

    private static void launchServer(InputStream in) {
        try{
            Configs config = new Gson().fromJson(new InputStreamReader(in), Configs.class);
            Server.main(config);
        } catch (IOException e){
            System.out.println("Error initializing server "+ e);
            System.exit(1);
        }
    }

}
