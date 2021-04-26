package it.polimi.ingsw.controller.prove;

import it.polimi.ingsw.controller.Events.BoughtCardEvent;
import it.polimi.ingsw.controller.Events.IllegalActionEventS2C;
import it.polimi.ingsw.controller.Events.NewConnectionEvent;

import java.io.*;
import java.net.*;
import java.util.*;

// Client class
public class ClientProva2 {


    //client che invia oggetti
    public static void main(String[] args)
    {
        // establish a connection by providing host and port
        // number
        try {

            Socket socket = new Socket("localhost", 9876); // o 9838
            // get the outputstream of client
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            // get the inputstream of client
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            BoughtCardEvent event = new BoughtCardEvent(0,0,0);

            Scanner sc = new Scanner(System.in);
            String line = "";

            out.writeObject("Nicolino");
            out.writeObject(1);

            while(!line.equals("quit")){
                line = sc.nextLine();
                out.writeObject(event);
                out.flush();
                IllegalActionEventS2C illegalActionEvent = (IllegalActionEventS2C) in.readObject();

                System.out.println(illegalActionEvent.getIllegalAction().getDescription()+illegalActionEvent.getIllegalAction().getPlayerNickname());

            }
            out.close();
            in.close();
            socket.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
