package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.Events.*;

//questo dovrebbe solo mostrare le cose alla cli , in base a che eventi arrivano


public class EventsHandlerCLI implements ServerEventObserver {

    private final ClientModel clientModel;
    private final CliView cliView;            //non so se dobbiamo dividere tra cliview e guiview

    /**
     * constructor
     */
    public EventsHandlerCLI(ClientModel clientModel, CliView cliView){
        this.clientModel =clientModel;
        this.cliView=cliView;
    }

    /**
     * receives an event and handles it
     * @param event is the received event
     */
    @Override
    public void handleEvent(LeaderCardActionEventS2C event) {
        cliView.showMessage(clientModel.getCurrentPlayerNick() + " has activated/discarded a leader card");
        if(clientModel.isCurrentPlayer())
            cliView.showMessage("""

                    Choose an action between
                    ▷ SHOWFT
                    ▷ SHOWLEADERS
                    ▷ SHOWBOARDS
                    ▷ SHOWMYSTATUS
                    ▷ SHOWOTHERSSTATUS
                    ▷ EXIT
                    """);
    }

    /**
     * receives an event and handles it
     * @param event is the received event
     */
    @Override
    public void handleEvent(BoughtCardEventS2C event) {
        cliView.showMessage(clientModel.getCurrentPlayerNick() + " has bought a card");
        if(clientModel.isCurrentPlayer())
            cliView.showMessage("""

                    Choose an action between
                    ▷ LEADERACTION
                    ▷ SHOWFT
                    ▷ SHOWLEADERS
                    ▷ SHOWBOARDS
                    ▷ SHOWMYSTATUS
                    ▷ SHOWOTHERSSTATUS
                    ▷ ENDTURN
                    ▷ EXIT
                    """);
    }

    /**
     * receives an event and handles it
     * @param event is the received event
     */
    @Override
    public void handleEvent(ActivatedProductionEventS2C event) {
        cliView.showMessage(clientModel.getCurrentPlayerNick() + " has used his production");
        if(clientModel.isCurrentPlayer())
            cliView.showMessage("""

                    Choose an action between
                    ▷ LEADERACTION
                    ▷ SHOWFT
                    ▷ SHOWLEADERS
                    ▷ SHOWBOARDS
                    ▷ SHOWMYSTATUS
                    ▷ SHOWOTHERSSTATUS
                    ▷ ENDTURN
                    ▷ EXIT
                    """);
    }

    /**
     * receives an event and handles it
     * @param event is the received event
     */
    @Override
    public void handleEvent(IncrementPositionEventS2C event) {
        cliView.showMessage(event.getPlayerNickname()+ " has proceeded in the faith track");
    }

    /**
     * receives an event and handles it
     * @param event is the received event
     */
    @Override
    public void handleEvent(VaticanReportEventS2C event) {
        cliView.showMessage("A vatican report has been activated");
    }

    /**
     * receives an event and handles it
     * @param event is the received event
     */
    @Override
    public void handleEvent(UseMarketEventS2C event) {
        cliView.showMessage(clientModel.getCurrentPlayerNick() + " has used the market");
        if(clientModel.isCurrentPlayer())
            cliView.showMessage("""

                    Choose an action between
                    ▷ LEADERACTION
                    ▷ SHOWFT
                    ▷ SHOWLEADERS
                    ▷ SHOWBOARDS
                    ▷ SHOWMYSTATUS
                    ▷ SHOWOTHERSSTATUS
                    ▷ ENDTURN
                    ▷ EXIT
                    """);

        //todo riguardare

    }

    /**
     * receives an event and handles it
     * @param event is the received event
     */
    @Override
    public void handleEvent(NewTurnEventS2C event) {
        Color.cls();
        //todo riguardare
        if(event.getNickname().equals(clientModel.getMyNickname())){
            cliView.showMessage(Color.color('g', Color.ANSI_TALK+" It's your turn"));

            if(clientModel.isPregame())
                cliView.showMessage("Write CHOOSE to start choosing");   //in automatico ActionHandler.initialchoice();
            else
            if(clientModel.hasGameStarted())
                cliView.showMessage("""

                        Choose an action between
                        ▷ LEADERACTION
                        ▷ PRODUCTION
                        ▷ BUYCARD
                        ▷ MARKET
                        ▷ SHOWFT
                        ▷ SHOWLEADERS
                        ▷ SHOWBOARDS
                        ▷ SHOWMYSTATUS
                        ▷ SHOWOTHERSSTATUS
                        ▷ ENDTURN
                        ▷ EXIT
                        """);
        }else{
                cliView.showMessage(Color.color('r', Color.ANSI_TALK+" It's "+event.getNickname()+"'s turn"));
                cliView.showMessage("""

                        Choose an action between
                        ▷ SHOWFT
                        ▷ SHOWLEADERS
                        ▷ SHOWBOARDS
                        ▷ SHOWMYSTATUS
                        ▷ SHOWOTHERSSTATUS
                        ▷ EXIT
                        """);
        }
    }

    /**
     * receives an event and handles it
     * @param event is the received event
     */
    @Override
    public void handleEvent(IllegalActionEventS2C event) {
        cliView.showErrorMessage("Illegal action: "+event.getIllegalAction().getDescription());
         cliView.printActions();
    }

    /**
     * receives an event and handles it
     * @param event is the received event
     */
    @Override
    public void handleEvent(GameStarterEventS2C event) {
        cliView.showMessage(Color.ANSI_TALK+" Please wait your turn..."); //da fare meglio
        cliView.showMessage("""

                Choose an action between
                ▷ SHOWFT
                ▷ SHOWLEADERS
                ▷ SHOWBOARDS
                ▷ SHOWMYSTATUS
                ▷ SHOWOTHERSSTATUS
                ▷ EXIT
                """);
    }

    /**
     * receives an event and handles it
     * @param event is the received event
     */
    @Override
    public void handleEvent(EndGameEventS2C event) {
        cliView.showLadderBoard(event);
        cliView.showMessage("Write 'exit' to continue.");
    }

    /**
     * receives an event and handles it
     * @param event is the received event
     */
    @Override
    public void handleEvent(LorenzoTurnEventS2C event) {
        cliView.showLorenzoTurn(event.getActivatedSoloAction());
    }

    /**
     * receives an event and handles it
     * @param event is the received event
     */
    @Override
    public void handleEvent(EndPreparationEventS2C event) {

        Color.cls();
        cliView.showMessage("The game has been set up.\nStarting the match...");

        if(clientModel.getMyNickname().equals(clientModel.getNicknames().get(0))){

            cliView.showMessage(Color.color('g',"It's your turn!"));
            cliView.showMessage("""

                    Choose an action between
                    ▷ LEADERACTION
                    ▷ PRODUCTION
                    ▷ BUYCARD
                    ▷ MARKET
                    ▷ SHOWFT
                    ▷ SHOWLEADERS
                    ▷ SHOWBOARDS
                    ▷ SHOWMYSTATUS
                    ▷ SHOWOTHERSSTATUS
                    ▷ ENDTURN
                    ▷ EXIT
                    """);
        }
        else{
            //Color.cls();
            cliView.showMessage(Color.color('r', Color.ANSI_TALK+" It's "+ clientModel.getNicknames().get(0)+"'s turn."));
            cliView.showMessage("""

                    Choose an action between
                    ▷ SHOWFT
                    ▷ SHOWLEADERS
                    ▷ SHOWBOARDS
                    ▷ SHOWMYSTATUS
                    ▷ SHOWOTHERSSTATUS
                    ▷ EXIT
                    """);
        }
    }

    /**
     * receives an event and handles it
     * @param event is the received event
     */
    @Override
    public void handleEvent(NewConnectionEventS2C event) {

    }
}
