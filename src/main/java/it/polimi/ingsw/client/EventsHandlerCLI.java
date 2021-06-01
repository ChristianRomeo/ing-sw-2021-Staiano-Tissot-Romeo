package it.polimi.ingsw.client;

import it.polimi.ingsw.client.GUI.GuiView;
import it.polimi.ingsw.controller.Events.*;

//questo dovrebbe solo mostrare le cose alla cli , in base a che eventi arrivano


public class EventsHandlerCLI implements ServerEventObserver {

    private final ClientModel clientModel;
    private final CliView cliView;            //non so se dobbiamo dividere tra cliview e guiview


    public EventsHandlerCLI(ClientModel clientModel, CliView cliView){
        this.clientModel =clientModel;
        this.cliView=cliView;
    }
    @Override
    public void handleEvent(LeaderCardActionEventS2C event) {

    }

    @Override
    public void handleEvent(BoughtCardEventS2C event) {

    }

    @Override
    public void handleEvent(ActivatedProductionEventS2C event) {

    }

    @Override
    public void handleEvent(IncrementPositionEventS2C event) {

    }

    @Override
    public void handleEvent(VaticanReportEventS2C event) {

    }

    @Override
    public void handleEvent(UseMarketEventS2C event) {

    }

    @Override
    public void handleEvent(NewTurnEventS2C event) {
        Styler.cls();
        //todo riguardare
        if(event.getNickname().equals(clientModel.getMyNickname())){
            cliView.showMessage(Styler.color('g',Styler.ANSI_TALK+"It's your turn"));

            if(clientModel.isPregame())
                cliView.showMessage("scrivi SCEGLI per iniziare la scelta");   //in automatico ActionHandler.initialchoice();
            else
            if(clientModel.hasGameStarted())
                cliView.showMessage("Scegli l'azione tra AZIONELEADER PRODUZIONE FINETURNO COMPRACARTA MERCATO MOSTRAFT MOSTRALEADERS MOSTRABOARDS EXIT :");

        }else{
            cliView.showMessage(Styler.ANSI_TALK+"It's "+event.getNickname()+"'s turn");
            cliView.showMessage("You can chose between MOSTRAFT MOSTRALEADERS MOSTRABOARDS EXIT :");
        }
    }

    @Override
    public void handleEvent(IllegalActionEventS2C event) {
        Styler.cls();
        cliView.showErrorMessage("Illegal action: "+event.getIllegalAction().getDescription());
        //show choices
    }

    @Override
    public void handleEvent(GameStarterEventS2C event) {
        cliView.showMessage(Styler.ANSI_TALK+"Please wait your turn..."); //da fare meglio
    }

    @Override
    public void handleEvent(EndGameEventS2C event) {

    }

    @Override
    public void handleEvent(LorenzoTurnEventS2C event) {

    }

    @Override
    public void handleEvent(EndPreparationEventS2C event) {

        Styler.cls();
        cliView.showMessage("The game has been set up.\nStarting the match...");

        if(clientModel.getMyNickname().equals(clientModel.getNicknames().get(0))){

            cliView.showMessage(Styler.color('g',"It's your turn!"));
            cliView.showMessage("Scegli l'azione tra AZIONELEADER PRODUZIONE FINETURNO COMPRACARTA MERCATO MOSTRAFT MOSTRALEADERS MOSTRABOARDS EXIT :");
        }
        else{
            //Styler.cls();
            cliView.showMessage(Styler.ANSI_TALK+"It's "+ clientModel.getNicknames().get(0)+"'s turn.");
            cliView.showMessage("You can chose between MOSTRAFT MOSTRALEADERS MOSTRABOARDS EXIT :");
        }
    }

    @Override
    public void handleEvent(NewConnectionEventS2C event) {

    }
}
