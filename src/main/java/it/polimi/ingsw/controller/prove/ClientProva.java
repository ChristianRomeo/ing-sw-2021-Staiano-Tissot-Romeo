package it.polimi.ingsw.controller.prove;

import com.sun.tools.javac.Main;
import it.polimi.ingsw.model.SameTypePair;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

// Client class
public class ClientProva {

/* //Client che invia stringhe
    // driver code
    public static void main(String[] args)
    {
        // establish a connection by providing host and port
        // number
        try (Socket socket = new Socket("localhost", 9876)) {

            // writing to server
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            // reading from server
            BufferedReader in
                    = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));

            // object of scanner class
            Scanner sc = new Scanner(System.in);
            String line = null;

            while (!"exit".equalsIgnoreCase(line)) {

                // reading from user
                line = sc.nextLine();

                // sending the user input to server
                out.println(line);
                out.flush();

                // displaying server reply
                System.out.println("Server replied "
                        + in.readLine());
            }

            // closing the scanner object
            sc.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

    //client che invia oggetti
    public static void main(String[] args)
    {
        // establish a connection by providing host and port
        // number
        try {

            Socket socket = new Socket("localhost", 9876);
            // get the outputstream of client
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            // get the inputstream of client
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            SameTypePair<Integer> pair = new SameTypePair<>();
            pair.setVal1(5);
            pair.setVal2(10);
            out.writeObject(pair);
            out.flush();

            pair = (SameTypePair) in.readObject();

            System.out.println(pair.getVal1() +" "+ pair.getVal2());
            out.close();
            in.close();
            socket.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
