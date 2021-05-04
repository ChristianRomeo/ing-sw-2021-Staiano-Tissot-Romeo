package it.polimi.ingsw.client;


//sta classe riceve il comando inserito dall'utente e vede se è una azione valida e se lo è chiama metodi
//(che volendo possono interagire con l'utente)
public class ActionHandler {

    public void handleAction(String action){
        if(action.contains(" ")){
            action = action.substring(0,action.indexOf(" ")); //ciò prende solo la prima parola inserita
        }
        action = action.toUpperCase();

        // mo si fa tipo if(action =="PRODUCTION"), if(action == "ENDTURN") ecc
        //e nel caso è una di queste allora si chiama quel metodo
        //(si dovrà ovviamente anche controllare che quell'azione si può fare)
    }
}
