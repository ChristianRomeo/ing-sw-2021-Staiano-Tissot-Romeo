package it.polimi.ingsw.controller.Events;

//sta classe crea gli eventi che poi mandiamo al client
//è una classe ausiliaria per non mettere tutto nel controller

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.DevelopmentCardBoard;
import it.polimi.ingsw.model.PersonalCardBoard;

public class ServerEventCreator{
    Controller controller;

    public ServerEventCreator(Controller controller){
        this.controller=controller;
    }
    public LeaderCardActionEventS2C createLeaderActionEvent(){
        boolean isActive1 = controller.getGame().getCurrentPlayer().getStatusPlayer().getLeaderCard(0).isActivated();
        boolean isActive2 = controller.getGame().getCurrentPlayer().getStatusPlayer().getLeaderCard(1).isActivated();
        boolean isDiscarded1 = controller.getGame().getCurrentPlayer().getStatusPlayer().getLeaderCard(0).isDiscarded();
        boolean isDiscarded2 = controller.getGame().getCurrentPlayer().getStatusPlayer().getLeaderCard(1).isDiscarded();

        return new LeaderCardActionEventS2C(isActive1,isActive2,isDiscarded1,isDiscarded2);
    }

    public BoughtCardEventS2C createBoughtCardEvent(){
        PersonalCardBoard personalCardBoard= controller.getGame().getCurrentPlayer().getStatusPlayer().getPersonalCardBoard();
        DevelopmentCardBoard board = controller.getGame().getBoard().getDevelopmentCardBoard();
        return new BoughtCardEventS2C(personalCardBoard,board);
    }
}
