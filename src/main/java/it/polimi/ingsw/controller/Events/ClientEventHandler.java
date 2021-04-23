package it.polimi.ingsw.controller.Events;

 // Interface implemented by classes of the controller that will handle events sent by the client

public interface ClientEventHandler {

    void handleEvent(BoughtCardEvent event);

    void handleEvent(LeaderCardActionEvent event);

    void handleEvent(ActivatedProductionEvent event);

    void handleEvent(UseMarketEvent event);

    void handleEvent(EndTurnEvent event);

    void handleEvent(NewConnectionEvent event);


}
