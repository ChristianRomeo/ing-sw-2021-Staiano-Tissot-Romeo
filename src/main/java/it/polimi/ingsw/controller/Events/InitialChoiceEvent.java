package it.polimi.ingsw.controller.Events;

public class InitialChoiceEvent extends ClientEvent {

    //todo: qua po vedo che mettere, mancano le risorse
    //ci sarannole scelte iniziali dell'utente
    //mi faccio dare gli indici delle carte leader che vuole RIMUOVERE
    private final int indexLeader1;
    private final int indexLeader2;

    public InitialChoiceEvent(int indexLeader1, int indexLeader2) {
        this.indexLeader1 = indexLeader1;
        this.indexLeader2 = indexLeader2;
    }

    public int getIndexLeader1() {
        return indexLeader1;
    }

    public int getIndexLeader2() {
        return indexLeader2;
    }

    @Override
    public void notifyHandler(ClientEventHandler eventHandler){
        eventHandler.handleEvent(this);
    }
}
