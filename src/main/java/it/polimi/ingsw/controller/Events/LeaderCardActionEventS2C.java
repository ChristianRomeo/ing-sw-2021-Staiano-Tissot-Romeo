package it.polimi.ingsw.controller.Events;

//il current player ha attivato/scartato una leader card e dobbiamo comunicarlo a tutti
//inviamo il nuovo status delle leader card di quel player
public class LeaderCardActionEventS2C extends ServerEvent{
    private final boolean isActive1; //la prima carta leader è attiva o no
    private final boolean isActive2;
    private final boolean isDiscarded1;
    private final boolean isDiscarded2;

    public LeaderCardActionEventS2C(boolean isActive1, boolean isActive2, boolean isDiscarded1, boolean isDiscarded2) {
        this.isActive1 = isActive1;
        this.isActive2 = isActive2;
        this.isDiscarded1 = isDiscarded1;
        this.isDiscarded2 = isDiscarded2;
    }

    public boolean isActive1() {
        return isActive1;
    }

    public boolean isActive2() {
        return isActive2;
    }

    public boolean isDiscarded1() {
        return isDiscarded1;
    }

    public boolean isDiscarded2() {
        return isDiscarded2;
    }

    @Override
    public void notifyHandler(ServerEventObserver eventHandler){
        eventHandler.handleEvent(this);
    }
}