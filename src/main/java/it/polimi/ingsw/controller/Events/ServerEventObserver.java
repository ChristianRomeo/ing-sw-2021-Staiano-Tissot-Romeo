package it.polimi.ingsw.controller.Events;

// Interface implemented by classes of the controller that will handle events sent by the server
//(like the virtual view)

import it.polimi.ingsw.controller.ActivatedProductionEventS2C;
import it.polimi.ingsw.controller.IncrementPositionEventS2C;

public interface ServerEventObserver {
    void handleEvent(LeaderCardActionEventS2C event);

    void handleEvent(BoughtCardEventS2C event);

    void handleEvent(ActivatedProductionEventS2C event);

    void handleEvent(IncrementPositionEventS2C event);
}
