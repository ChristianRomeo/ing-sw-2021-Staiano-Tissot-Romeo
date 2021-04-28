package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Reads configs file from json
 */
public class Configs {

    private final String server_ip;
    private final int server_port;

    public Configs(String server_ip, int server_port) {     //va bene?
        this.server_ip = server_ip;
        this.server_port = server_port;
    }

    public static  String getServerIp() throws FileNotFoundException {
        String CONFIGPATH = "src/main/resources/Configs.json";

        BufferedReader bufferedReader = new BufferedReader(new FileReader(CONFIGPATH));
        Configs configs = new Gson().fromJson(bufferedReader, Configs.class);
        return configs.server_ip;
    }

    public static  int getServerPort() throws FileNotFoundException {
        String CONFIGPATH = "src/main/resources/Configs.json";

        BufferedReader bufferedReader = new BufferedReader(new FileReader(CONFIGPATH));
        Configs configs = new Gson().fromJson(bufferedReader, Configs.class);
        return configs.server_port;
    }

    public static List<LeaderCard> getLeaderCards() throws FileNotFoundException {
        String LEADERPATH = "src/main/resources/Leaders.json";

        List<LeaderCard> leaderCardList = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(LEADERPATH));
        Gson gson = new Gson();
        JsonArray json = gson.fromJson(bufferedReader, JsonArray.class);

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
        //leaderCardList.forEach(System.out::println);
    }

    public static List<DevelopmentCard> getDevelopmentCards() throws FileNotFoundException {
        String CARDPATH = "src/main/resources/Cards.json";

        BufferedReader bufferedReader = new BufferedReader(new FileReader(CARDPATH));
        JsonArray json = new Gson().fromJson(bufferedReader, JsonArray.class);
        return new Gson().fromJson(String.valueOf(json), new TypeToken<List<DevelopmentCard>>() { }.getType());
    }

    public static boolean isServerAlive(){
        return true;
    }
}
