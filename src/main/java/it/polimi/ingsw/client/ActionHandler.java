package it.polimi.ingsw.client;


import it.polimi.ingsw.MastersOfRenaissance;
import it.polimi.ingsw.controller.Events.*;
import it.polimi.ingsw.model.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Controller client-side of player's actions, dispatch actions to methods (CLI-only)
 */
public class ActionHandler {
    private final ClientModel clientModel;
    private final CliView cliView;
    private final ServerHandler serverHandler;
    private boolean newGame= false;

    /**
     * Instantiated by the cliView
     * @param clientModel   the client storage of model parts
     * @param cliView       the cli
     * @param serverHandler the serverHandler
     */
    public ActionHandler(ClientModel clientModel, CliView cliView, ServerHandler serverHandler){
        this.clientModel=clientModel;
        this.cliView=cliView;
        this.serverHandler = serverHandler;
    }

    /**
     * Main method to handle Player actions
     * @param action the action string
     */
    public void handleAction(String action){

        //ciò prende solo la prima parola inserita
        if(action.contains(" "))
            action = action.substring(0,action.indexOf(" "));


        //todo: bisogna aggiungere le azioni di show
        switch (action.toUpperCase()) {
            case "CHOOSE" -> initialChoice();
            case "LEADERACTION" -> leaderAction();
            case "PRODUCTION" -> activateProduction();
            case "ENDTURN" -> endTurn();
            case "BUYCARD" -> buyDevelopmentCard();
            case "MARKET" -> useMarket();
            case "SHOWFT" -> cliView.showFaithTracks();
            case "SHOWLEADERS" -> cliView.showPlayersLeaderCards();
            case "SHOWABOARDS" -> cliView.showPlayersBoard();
            case "SHOWMYSTATUS" -> cliView.showMyState();
            case "SHOWOTHERSSTATUS" -> cliView.showOthersState();
            case "EXIT" -> exit();
            case "Y" -> getNewGame("y");
            case "N" -> getNewGame("N");
            default -> cliView.showErrorMessage("Invalid choice! Try again: ");
        }

    }

    public void getNewGame(String choice) {

        if (newGame){
            if (choice.equalsIgnoreCase("y")) {
                try {
                    if (MastersOfRenaissance.getSave()==null)
                        MastersOfRenaissance.main("cli ".split(" "));
                    else
                        MastersOfRenaissance.main("cli save ".split(" "));
                } catch (IOException | URISyntaxException e) {
                    System.out.println("Error while restarting the application." +e);
                }
            }else
                System.exit(0);
            newGame=false;
        }else
            cliView.showErrorMessage("Invalid choice! Try again: ");

    }

    public boolean isNewGame() {
        return newGame;
    }

    public void setNewGame(boolean newGame) {
        this.newGame = newGame;
    }

    public void exit(){

        Color.cls();
        if(!clientModel.hasGameEnded())
        {
            cliView.showErrorMessage("You can't do this action now, Please Wait...");
        }



        //cliView.askNewGame();
    }

    public void useMarket() {

        Color.cls();
        if(!clientModel.isCurrentPlayer() || !clientModel.hasGameStarted() ){
            cliView.showErrorMessage("You can't do this action now, Please Wait...");
            return;
        }
        if (clientModel.getDone().get()){
            cliView.showErrorMessage("You've already done an action");
            cliView.showMessage("""

                    Choose an action between
                    ▷ SHOWFT
                    ▷ SHOWLEADERS
                    ▷ SHOWBOARDS
                    ▷ SHOWMYSTATUS
                    ▷ SHOWOTHERSSTATUS
                    ▷ EXIT
                    """);
            return;
        }

        Pair<Character, Integer> marketChoice = cliView.askMarketUse();
        char rowOrColumn = marketChoice.getVal1();
        int index = marketChoice.getVal2();
        List<MarbleColor> takenMarbles;
        List<Resource> boughtResources;
        List<Integer> whiteMarbleChoices = null;

        if(rowOrColumn == 'r')
            takenMarbles = clientModel.getMarket().getRowColors(index);
        else
            takenMarbles = clientModel.getMarket().getColumnColors(index);

        List<LeaderCard> leaderCards = clientModel.getPlayerLeaderCards(clientModel.getMyNickname());
        if (leaderCards.get(0).isActivated() && leaderCards.get(0).getWhiteMarbleResource() != null && leaderCards.get(1).isActivated() && leaderCards.get(1).getWhiteMarbleResource() != null) {
            //allora l'utente ha 2 carte leader white marble attive
            whiteMarbleChoices = new ArrayList<>();
            for(MarbleColor marble : takenMarbles)
                if(marble == MarbleColor.WHITE)
                    whiteMarbleChoices.add(cliView.askWhiteMarbleChoice());


        }
        boughtResources = clientModel.fromMarblesToResources(takenMarbles, whiteMarbleChoices);

        PlayerWarehouse newWarehouse = new PlayerWarehouse();
        newWarehouse.setWarehouse(clientModel.getPlayersWarehouses().get(clientModel.getMyIndex()));
        SameTypePair<Integer> fullLeaderSlots = new SameTypePair<>(leaderCards.get(0).getFullSlotsNumber(),leaderCards.get(1).getFullSlotsNumber());

        Map<Resource,Integer> discardedResources =cliView.insertBoughtResources(newWarehouse,fullLeaderSlots, boughtResources);

        if(fullLeaderSlots.getVal1()==null)
            fullLeaderSlots.setVal1(0);

        if(fullLeaderSlots.getVal2()==null)
            fullLeaderSlots.setVal2(0);

        serverHandler.send(new UseMarketEvent(rowOrColumn,index,newWarehouse,discardedResources,fullLeaderSlots.getVal1(),fullLeaderSlots.getVal2(),whiteMarbleChoices ));

        clientModel.setDone(true);
    }

    /**
     * Guides the player through the initial choice of Leader cards and resources
     */
    public void initialChoice(){
        Color.cls();
        if(!clientModel.isCurrentPlayer() || !clientModel.isPregame() ){
            cliView.showErrorMessage("You can't do this action now, Please Wait...");
            return;
        }

        SameTypePair<Integer> removedLeaderCards = cliView.askChoiceLeaderCards();
        Resource resource1=null;
        SameTypePair<Integer> position1=null;
        Resource resource2=null;
        SameTypePair<Integer> position2=null;

        if(clientModel.getMyIndex()>=1){
            resource1 = cliView.askResource();
            position1= cliView.askWarehouseCell();
        }

        if(clientModel.getMyIndex()==3){//per il secondo e terzo 1 res, per il quarto 2
            resource2 = cliView.askResource();
            position2= cliView.askWarehouseCell();
            while(!checkInitialChoice(resource1,resource2, Objects.requireNonNull(position1),position2)){
                cliView.showMessage("Invalid choice! Try again to choose the second resource and its position. ");
                resource2 = cliView.askResource();
                position2= cliView.askWarehouseCell();
            }
        }

        serverHandler.send(new InitialChoiceEvent(removedLeaderCards.getVal1(), removedLeaderCards.getVal2(), resource1,resource2,position1,position2));
    }

    /**
     * Helper method of initial choice.
     */
    private boolean checkInitialChoice(Resource resource1, Resource resource2, SameTypePair<Integer> pos1, SameTypePair<Integer> pos2){

        if(pos1.getVal1().equals(pos2.getVal1()) && pos1.getVal2().equals(pos2.getVal2()))
            return false;

        if(resource1==resource2 && !pos1.getVal1().equals(pos2.getVal1()))
            return false;

        return resource1 == resource2 || !pos1.getVal1().equals(pos2.getVal1());
    }

    /**
     * Activate/discard leaderCards
     */
    public void leaderAction(){
        Color.cls();
        if(!clientModel.isCurrentPlayer() || !clientModel.hasGameStarted() ){
            cliView.showErrorMessage("You can't do this action now, Please Wait...");
            return;
        }

        List<Integer> answer = cliView.askLeaderCard();
        char activeOrDiscard = answer.get(0)==0 ? 'a': 'd';

        serverHandler.send(new LeaderCardActionEvent(activeOrDiscard,answer.get(1)));
    }

    /**
     * this enables the user to end his turn.
     */
    public void endTurn(){

        Color.cls();
        if(!clientModel.isCurrentPlayer() || !clientModel.hasGameStarted() ){
            cliView.showErrorMessage("You can't do this action now, Please Wait...");
            return;
        }
        serverHandler.send(new EndTurnEvent());
        clientModel.setDone(false);

    }

    /**
     * enables the player to buy a card from the board
     */
    public void buyDevelopmentCard(){

        Color.cls();
        if (clientModel.getDone().get()){
            cliView.showErrorMessage("You've already done an action");
            cliView.showMessage("""

                    Choose an action between
                    ▷ SHOWFT
                    ▷ SHOWLEADERS
                    ▷ SHOWBOARDS
                    ▷ SHOWMYSTATUS
                    ▷ SHOWOTHERSSTATUS
                    ▷ EXIT
                    """);
            return;
        }

        if(!clientModel.isCurrentPlayer() || !clientModel.hasGameStarted() ){
            cliView.showErrorMessage("You can't do this action now, Please Wait...");
            return;
        }

        SameTypePair<Integer> position = cliView.askDevelopmentCard();

        serverHandler.send(new BoughtCardEvent(position.getVal1(),position.getVal2(),cliView.askCardPile()));
        clientModel.setDone(true);
    }

    /**
     * enables the player to activate the production.
     */
    public void activateProduction(){

        Color.cls();
        if (clientModel.getDone().get()){
            cliView.showErrorMessage("You've already done an action");
            cliView.showMessage("""

                    Choose an action between
                    ▷ SHOWFT
                    ▷ SHOWLEADERS
                    ▷ SHOWBOARDS
                    ▷ SHOWMYSTATUS
                    ▷ SHOWOTHERSSTATUS
                    ▷ EXIT
                    """);
            return;
        }
        if(!clientModel.isCurrentPlayer() || !clientModel.hasGameStarted() ){
            cliView.showErrorMessage("You can't do this action now, Please Wait...");
            return;
        }

        List<Integer> cardProductions;
        boolean activateBaseProduction=false;
        Resource requestedResBP1=null;
        Resource requestedResBP2=null;
        Resource producedResBP=null;

        cardProductions = cliView.askCardProductions();
        SameTypeTriple<Resource> baseProductionResources = cliView.askBaseProduction();

        if (baseProductionResources!=null){
            activateBaseProduction=true;
            requestedResBP1 = baseProductionResources.getVal1();
            requestedResBP2 = baseProductionResources.getVal2();
            producedResBP = baseProductionResources.getVal3();
        }

        SameTypePair<Resource> leaderProductionResources = cliView.askLeaderProductions();

        serverHandler.send(new ActivatedProductionEvent(cardProductions,activateBaseProduction,requestedResBP1,requestedResBP2,producedResBP,leaderProductionResources.getVal1(),leaderProductionResources.getVal2()));
        clientModel.setDone(true);

    }

}