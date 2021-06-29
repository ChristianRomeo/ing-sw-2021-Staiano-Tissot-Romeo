package it.polimi.ingsw.controller.Events;


/**
 * Interface implemented by classes of the controller which will handle events sent by the server
 * (like the virtual view, or in the client)
 */
public interface ServerEventObserver {
    /**
     * LeaderActionEvent from the server
     * sends the event to every client
     * @param event is the event received from the server
     */
    void handleEvent(LeaderCardActionEventS2C event);

    /**
     * BoughtCardEvent from the server
     * sends the event to every client
     * @param event is the event received from the server
     */
    void handleEvent(BoughtCardEventS2C event);

    /**
     * ActivateProductionEvent from the server
     * sends the event to every client
     * @param event is the event received from the server
     */
    void handleEvent(ActivatedProductionEventS2C event);

    /**
     * IncrementPosition from the server
     * sends the event to every client
     * @param event is the event received from the server
     */
    void handleEvent(IncrementPositionEventS2C event);

    /**
     * VaticanReportEvent from the server
     * sends the event to every client
     * @param event is the event received from the server
     */
    void handleEvent(VaticanReportEventS2C event);

    /**
     * UseMarketEvent from the server
     * sends the event to every client
     * @param event is the event received from the server
     */
    void handleEvent(UseMarketEventS2C event);

    /**
     * NewTurnEvent from the server
     * sends the event to every client
     * @param event is the event received from the server
     */
    void handleEvent(NewTurnEventS2C event);

    /**
     * IllegalActionEvent from the server
     * sends the event to the specific player who has done it
     * @param event is the event received from the server
     */
    void handleEvent(IllegalActionEventS2C event);

    /**
     * LeaderActionEvent from the server
     * sends the event to the specific player
     * @param event is the event received from the server
     */
    void handleEvent(GameStarterEventS2C event);

    /**
     * EndGameEvent from the server
     * sends the event to every client
     * @param event is the event received from the server
     */
    void handleEvent(EndGameEventS2C event);

    /**
     * LorenzoTurnEvent from the server
     * sends the event to every client
     * @param event is the event received from the server
     */
    void handleEvent(LorenzoTurnEventS2C event);

    /**
     * EndPreparationEvent from the server
     * sends the event to every client
     * @param event is the event received from the server
     */
    void handleEvent(EndPreparationEventS2C event);

    /**
     * LeaderActionEvent from the server
     * @param event is the event received from the server
     */
    void handleEvent(NewConnectionEventS2C event);

}
