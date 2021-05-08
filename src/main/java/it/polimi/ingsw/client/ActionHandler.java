package it.polimi.ingsw.client;


//sta classe riceve il comando inserito dall'utente e vede se è una azione valida e se lo è chiama metodi
//(che volendo possono interagire con l'utente)
public class ActionHandler {
    private final ClientModel clientModel;

    public ActionHandler(ClientModel clientModel){
        this.clientModel=clientModel;
    }

    public void handleAction(String action){
        if(action.contains(" ")){
            action = action.substring(0,action.indexOf(" ")); //ciò prende solo la prima parola inserita
        }
        action = action.toUpperCase();

        if(action.equals("SCEGLI")){//o anche un altro nome al comando
            initialChoice();
        }
        else{
            //if(action.equals("SHOW") ecc
        }

        // mo si fa tipo if(action =="PRODUCTION"), if(action == "ENDTURN") ecc
        //e nel caso è una di queste allora si chiama quel metodo
        //(si dovrà ovviamente anche controllare che quell'azione si può fare)
    }


    public void initialChoice(){
        //metodo che interagisce con l'utente per la scelta iniziale di leader cards e risorse
        if(!clientModel.isCurrentPlayer() || !clientModel.isPregame() ){
            //non è possibile fare questa azione, mostrarlo all'utente
            return;
        }

        //interazione con utente
    }
}
