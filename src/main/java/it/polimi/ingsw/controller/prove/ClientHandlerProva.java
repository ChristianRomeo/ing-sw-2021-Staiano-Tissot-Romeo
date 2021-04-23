package it.polimi.ingsw.controller.prove;


import it.polimi.ingsw.controller.Server;
import it.polimi.ingsw.model.SameTypePair;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class ClientHandlerProva implements Runnable{
    private final static Logger logger = Logger.getLogger(Server.class.getName());

    private final Socket clientSocket;

    // Constructor
    public ClientHandlerProva(Socket socket)
    {
        this.clientSocket = socket;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */

    /* //run con invio di stringhe
    @Override
    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;
        try {

            // get the outputstream of client
            out = new PrintWriter(
                    clientSocket.getOutputStream(), true);

            // get the inputstream of client
            in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {

                // writing the received message from
                // client
                System.out.printf(
                        " Sent from the client: %s\n",
                        line);
                out.println(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                    clientSocket.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
*/
    //run con  invio oggetti
    @Override
    public void run() {
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try {

            // get the outputstream of client
            out = new ObjectOutputStream(clientSocket.getOutputStream());

            // get the inputstream of client
            in = new ObjectInputStream(clientSocket.getInputStream());

            SameTypePair<Integer> pair = new SameTypePair<>();

            pair = (SameTypePair) in.readObject();

            System.out.println(pair.getVal1() +" "+ pair.getVal2());
            pair.setVal1(2* pair.getVal1());
            pair.setVal2(2* pair.getVal2());

            out.writeObject(pair);
            out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                    clientSocket.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
/*

    public static void send(String messageProva) {

            try {
                    output.writeUTF(messageProva);
                    output.flush();
                    output.reset();

            } catch (IOException e) {
                logger.warning(cSocket +" has disconnected during message sending");
                closeSocket();
            }
        }


    public static void closeSocket() {
        try {
            cSocket.close();
        } catch (IOException ignored) {
        }
    }


}*/