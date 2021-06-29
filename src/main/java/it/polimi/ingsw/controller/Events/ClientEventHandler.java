package it.polimi.ingsw.controller.Events;

/**
 * Interface implemented by classes of the controller that will handle events sent by the client
 */

public interface ClientEventHandler {

    /**
     * BoughCardEvent from the client
     * checks if an action has already been done:
     * if not, it sends the event to the controller which will then edit the model;
     * if yes, it adds a new IllegalAction to communicate that an action has already been done
     * @param event is the event received from the client
     */
    void handleEvent(BoughtCardEvent event);

    /**
     *LeaderCardActionEvent from the client
     * action could be discard ("d") or activate "a")
     * it sends the event to the controller, which will then edit the model
     * if yes, it adds a new IllegalAction to communicate that an action has already been done
     * @param event is the event received from the client
     */
    void handleEvent(LeaderCardActionEvent event);

    /**
     * ActivateProductionEvent from the client
     * checks if an action has already been done:
     * if not, it sends the event to the controller which will then edit the model;
     * if yes, it adds a new IllegalAction to communicate that an action has already been done
     * @param event is the event received from the client
     */
    void handleEvent(ActivatedProductionEvent event);

    /**
     * UseMarketEvent from the client
     * checks if an action has already been done:
     * if not, it sends the event to the controller which will then edit the model;
     * if yes, it adds a new IllegalAction to communicate that an action has already been done
     * @param event is the event received from the client
     */
    void handleEvent(UseMarketEvent event);

    /**
     * boughCardEvent from the client
     * checks if an action has already been done:
     * if not, it adds a new IllegalAction to communicate that an action has already been done;
     * if yes, it sends the event to the controller which will then edit the model;
     * @param event is the event received from the client
     */
    void handleEvent(EndTurnEvent event);

    /**
     * NewConnectionEvent from the client
     * @param event is the event received from the client
     */
    void handleEvent(NewConnectionEvent event);

    /**
     * NumPlayerEvent from the client, sent to the controller which will then edit the model
     * @param event is the event received from the client
     */
    void handleEvent(NumPlayerEvent event);

    /**
     * InitialChoiceEvent from the client
     * sends the event to the controller which will then edit the model
     * @param event is the event received from the client
     */
    void handleEvent(InitialChoiceEvent event);


}
