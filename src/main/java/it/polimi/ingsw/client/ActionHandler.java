package it.polimi.ingsw.client;


import it.polimi.ingsw.controller.Events.EndTurnEvent;
import it.polimi.ingsw.controller.Events.InitialChoiceEvent;
import it.polimi.ingsw.controller.Events.LeaderCardActionEvent;
import it.polimi.ingsw.model.*;

import java.util.Scanner;

//sta classe riceve il comando inserito dall'utente e vede se è una azione valida e se lo è chiama metodi
//(che volendo possono interagire con l'utente)
//ovviamente è solo per la cli
public class ActionHandler {
    private final ClientModel clientModel;
    private final CliView cliView;
    private final ConnectionHandler connectionHandler;
    private final Scanner scanner;

    public ActionHandler(ClientModel clientModel, CliView cliView,ConnectionHandler connectionHandler){
        this.clientModel=clientModel;
        this.cliView=cliView;
        this.connectionHandler=connectionHandler;
        this.scanner = new Scanner(System.in);
    }

    public void handleAction(String action){
        if(action.contains(" ")){
            action = action.substring(0,action.indexOf(" ")); //ciò prende solo la prima parola inserita
        }
        action = action.toUpperCase();


        switch (action){//bisogna aggiungere le altre azioni
            case "SCEGLI": //o anche un altro nome al comando (se non vi piace questo)
                initialChoice();
                break;
            case "AZIONELEADER":
                leaderAction();
                break;
            case "PRODUZIONE":
                activateProduction();
                break;
            case "FINETURNO":
                endTurn();
                break;
            default:
                cliView.showMessage("comando non valido");
        }

        //commento vecchio:
        // mo si fa tipo if(action =="PRODUCTION"), if(action == "ENDTURN") ecc
        //e nel caso è una di queste allora si chiama quel metodo
        //(si dovrà ovviamente anche controllare che quell'azione si può fare)
    }


    public void initialChoice(){
        //metodo che interagisce con l'utente per la scelta iniziale di leader cards e risorse
        if(!clientModel.isCurrentPlayer() || !clientModel.isPregame() ){
            cliView.showMessage("Non puoi fare questa azione adesso");
            return;
        }
        //todo: metodo da finire

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

        connectionHandler.send(new InitialChoiceEvent(removedLeader1,removedLeader2,resource1,resource2,position1,position2));
        cliView.showMessage("Scelta inviata"); //per debug
    }

    //metodo per scartare/attivare carte leader
    public void leaderAction(){
        if(!clientModel.isCurrentPlayer() || !clientModel.isGameStarted() ){
            cliView.showMessage("Non puoi fare questa azione adesso");
            return;
        }

        // todo: qui mostro le leader cards che ha il giocatore

        cliView.showMessage("Vuoi attivare o scartare una carta? A/S");
        String string = scanner.nextLine();
        while (string.length()!=1 || (string.charAt(0)!='A'&& string.charAt(0)!='S')){
            cliView.showMessage("Scelta non valida! Riprova: ");
            string = scanner.nextLine();
        }
        char activeOrDiscard = string.charAt(0)=='A' ? 'a' : 'd';
        int cardIndex = cliView.askLeaderCard();

        connectionHandler.send(new LeaderCardActionEvent(activeOrDiscard,cardIndex));
    }

    //metodo per attivare produzione
    public void activateProduction(){
        if(!clientModel.isCurrentPlayer() || !clientModel.isGameStarted() ){
            cliView.showMessage("Non puoi fare questa azione adesso");
            return;
        }



    }

    //metodo per terminare il proprio turno
    public void endTurn(){
        if(!clientModel.isCurrentPlayer() || !clientModel.isGameStarted() ){
            cliView.showMessage("Non puoi fare questa azione adesso");
            return;
        }
        connectionHandler.send(new EndTurnEvent());
    }


}
