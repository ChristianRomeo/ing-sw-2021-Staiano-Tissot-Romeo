package it.polimi.ingsw.controller.Events;


/**
 * Interface implemented by classes of the controller which will handle events sent by the server
 * (like the virtual view, or in the client)
 */
public interface ServerEventObserver {
    void handleEvent(LeaderCardActionEventS2C event);

    void handleEvent(BoughtCardEventS2C event);

    void handleEvent(ActivatedProductionEventS2C event);

    void handleEvent(IncrementPositionEventS2C event);

    void handleEvent(VaticanReportEventS2C event);

    void handleEvent(UseMarketEventS2C event);

    void handleEvent(NewTurnEventS2C event);

    void handleEvent(IllegalActionEventS2C event);

    void handleEvent(GameStarterEventS2C event);

    void handleEvent(EndGameEventS2C event);

    void handleEvent(LorenzoTurnEventS2C event);

    void handleEvent(EndPreparationEventS2C event);

    void handleEvent(NewConnectionEventS2C event);

}
