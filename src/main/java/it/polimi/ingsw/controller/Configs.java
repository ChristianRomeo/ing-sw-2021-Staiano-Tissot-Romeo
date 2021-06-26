package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Reads configs files from json, LeaderCards, DevelopmentCards and NetworkConfig
 */
public class Configs {

    private final String server_ip;
    private final int server_port;

    public Configs(String server_ip, int server_port) {
        this.server_ip = server_ip;
        this.server_port = server_port;
    }

    /**
     * Getter of Default ip address
     * @return  string the ip address from file
     * @throws JsonIOException file not found
     */
    public static String getServerIp(Configs config) throws JsonIOException {

        return config.server_ip;
    }

    /**
     * Getter of Default port number
     * @return int the port number from file
     * @throws JsonIOException file not found
     */
    public static int getServerPort(Configs config) throws JsonIOException {

            return config.server_port;

    }


    /**
     * Getter of LeaderCards from file
     * @return list with all the leader cards
     * @throws JsonIOException file not found
     */
    public static List<LeaderCard> getLeaderCards() throws JsonIOException {

        List<LeaderCard> leaderCardList = new ArrayList<>();
        InputStream in = Configs.class.getClassLoader().getResourceAsStream("leadercards.json");
        Gson gson = new Gson();
        JsonArray json = gson.fromJson(new InputStreamReader(Objects.requireNonNull(in)), JsonArray.class);

        for (int i = 0; i < json.size(); ++i)
            switch (gson.fromJson(json.get(i), SonOfLeaderCard.class).getAbility()) {
                case DISCOUNT ->
                        leaderCardList.add(gson.fromJson(json.get(i), LeaderCardDiscount.class));

                case SLOTS ->
                        leaderCardList.add(gson.fromJson(json.get(i), LeaderCardSlots.class));

                case PRODUCTION ->
                        leaderCardList.add(gson.fromJson(json.get(i), LeaderCardProduction.class));

                case WHITEMARBLE ->
                        leaderCardList.add(gson.fromJson(json.get(i), LeaderCardWhiteMarble.class));
            }

        Collections.shuffle(leaderCardList);
        return  leaderCardList;
    }

    /**
     * Getter of development cards
     * @return list with all of the development cards
     * @throws JsonIOException file not found
     */
    public static List<DevelopmentCard> getDevelopmentCards() throws JsonIOException {

        InputStream in = Configs.class.getClassLoader().getResourceAsStream("developmentcards.json");

        JsonArray json = new Gson().fromJson(new InputStreamReader(Objects.requireNonNull(in)), JsonArray.class);
        return new Gson().fromJson(String.valueOf(json), new TypeToken<List<DevelopmentCard>>() { }.getType());
    }

}
