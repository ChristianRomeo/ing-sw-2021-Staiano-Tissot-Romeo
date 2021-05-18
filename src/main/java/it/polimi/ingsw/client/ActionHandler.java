package it.polimi.ingsw.client;


import it.polimi.ingsw.controller.Events.*;
import it.polimi.ingsw.model.*;

import java.util.*;

/**
 * Controller client-side of player's actions, dispatch actions to methods (CLI-only)
 */
public class ActionHandler {
    private final ClientModel clientModel;
    private final CliView cliView;
    private final ServerHandler serverHandler;
    private final Scanner scanner;

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
        this.scanner = new Scanner(System.in);
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
            case "SCEGLI" -> initialChoice();   //o anche un altro nome al comando (se non vi piace questo)
            case "AZIONELEADER" -> leaderAction();
            case "PRODUZIONE" -> activateProduction();
            case "FINETURNO" -> endTurn();
            case "COMPRACARTA" -> buyDevelopmentCard();
            case "MERCATO" -> useMarket();
            case "MOSTRAFT" -> cliView.showFaithTrack();
            case "MOSTRALEADERS" -> cliView.showPlayersLeaderCards();
            case "MOSTRABOARDS" -> cliView.showPlayersBoard();
            default -> cliView.showErrorMessage("Invalid choice! Try again: ");
        }

        //commento vecchio:
        // mo si fa tipo if(action =="PRODUCTION"), if(action == "ENDTURN") ecc
        //e nel caso è una di queste allora si chiama quel metodo
        //(si dovrà ovviamente anche controllare che quell'azione si può fare)
    }



    public void useMarket() {
        if(!clientModel.isCurrentPlayer() || !clientModel.hasGameStarted() ){
            cliView.showErrorMessage("You can't do this action now, Please Wait...");
            return;
        }
        Pair<Character, Integer> marketChoice = cliView.askMarketUse();
        char rowOrColumn = marketChoice.getVal1();
        int index = marketChoice.getVal2();
        List<MarbleColor> takenMarbles;
        List<Resource> boughtResources;
        List<Integer> whiteMarbleChoices = null;
        if(rowOrColumn == 'r'){
            takenMarbles = clientModel.getMarket().getRowColors(index);
        }else{
            takenMarbles = clientModel.getMarket().getColumnColors(index);
        }
        List<LeaderCard> leaderCards = clientModel.getPlayerLeaderCards(clientModel.getMyNickname());
        if (leaderCards.get(0).isActivated() && leaderCards.get(0).getWhiteMarbleResource() != null && leaderCards.get(1).isActivated() && leaderCards.get(1).getWhiteMarbleResource() != null) {
            //allora l'utente ha 2 carte leader white marble attive
            whiteMarbleChoices = new ArrayList<>();
            for(MarbleColor marble : takenMarbles){
                if(marble == MarbleColor.WHITE){
                    whiteMarbleChoices.add(cliView.askWhiteMarbleChoice());
                }
            }
        }
        boughtResources = clientModel.fromMarblesToResources(takenMarbles, whiteMarbleChoices);

        PlayerWarehouse newWarehouse = new PlayerWarehouse();
        newWarehouse.setWarehouse(clientModel.getPlayersWarehouses().get(clientModel.getMyIndex()));
        SameTypePair<Integer> fullLeaderSlots = new SameTypePair<>(leaderCards.get(0).getFullSlotsNumber(),leaderCards.get(1).getFullSlotsNumber());
        Map<Resource,Integer> discardedResources = new HashMap<>();
        cliView.insertBoughtResources(newWarehouse,fullLeaderSlots, boughtResources, discardedResources);

        serverHandler.send(new UseMarketEvent(rowOrColumn,index,newWarehouse,discardedResources,fullLeaderSlots.getVal1(),fullLeaderSlots.getVal2(),whiteMarbleChoices ));
    }



    /**
     * Guides the player throught the initial choice of leaderCards and resources
     *///todo: metodo da finire
    public void initialChoice(){

        if(!clientModel.isCurrentPlayer() || !clientModel.isPregame() ){
            cliView.showErrorMessage("You can't do this action now, Please Wait...");
            return;
        }


        //ovviamente ste cose dovremo chiederle a utente
        //qua si prendono gli indici delle leader card  che si vogliono scartare
        int removedLeader1=0;
        int removedLeader2=1;
        Resource resource1=null;
        SameTypePair<Integer> position1=null;
        Resource resource2=null;
        SameTypePair<Integer> position2=null;
        if(clientModel.getMyIndex()>=1){
            resource1 = Resource.COIN;
            position1= new SameTypePair<>();
            position1.setVal1(3);
            position1.setVal2(1);
        }
        if(clientModel.getMyIndex()==3){//per il secondo e terzo 1 res, per il quarto 2
            resource2 = Resource.COIN;
            position2= new SameTypePair<>();
            position2.setVal1(3);
            position2.setVal2(2);
        }

        serverHandler.send(new InitialChoiceEvent(removedLeader1,removedLeader2,resource1,resource2,position1,position2));
        cliView.showMessage("Choice saved",false); //per debug
    }

    /**
     * Activate/discard leaderCards
     */
    public void leaderAction(){

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
        if(!clientModel.isCurrentPlayer() || !clientModel.hasGameStarted() ){
            cliView.showErrorMessage("You can't do this action now, Please Wait...");
            return;
        }
        serverHandler.send(new EndTurnEvent());
    }

    /**
     * enables the player to buy a card from the board
     */
    public void buyDevelopmentCard(){
        if(!clientModel.isCurrentPlayer() || !clientModel.hasGameStarted() ){
            cliView.showErrorMessage("You can't do this action now, Please Wait...");
            return;
        }
        SameTypePair<Integer> position = cliView.askDevelopmentCard();

        serverHandler.send(new BoughtCardEvent(position.getVal1(),position.getVal2(),cliView.askCardPile()));
    }

    /**
     * enables the player to activate the production.
     */
    public void activateProduction(){
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
    }
}
