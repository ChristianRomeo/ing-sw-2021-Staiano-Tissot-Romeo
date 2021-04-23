package it.polimi.ingsw.controller;

import java.util.ArrayList;
import java.util.List;

public abstract class ServerObservable {

    private List<ServerEventObserver> observers = new ArrayList<>();

    public void addObserver(ServerEventObserver observer){
        observers.add(observer);
    }

    public void removeObserver(ServerEventObserver observer){
        observers.remove(observer);
    }

    public void notifyAllObservers(ServerEvent event){
        for (ServerEventObserver observer : this.observers) {
            event.notifyHandler(observer);
        }
    }
}
