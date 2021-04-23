package it.polimi.ingsw.controller.Events;

// Interface implemented by classes of the controller that will handle events sent by the server

public interface ServerEventObserver {
    void handleEvent(LeaderCardActionEventS2C event);

    void handleEvent(BoughtCardEventS2C event);
}
