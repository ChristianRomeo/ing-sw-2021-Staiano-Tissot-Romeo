package it.polimi.ingsw.controller;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Configs {

    private String server_ip;
    private int server_port;


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

}
