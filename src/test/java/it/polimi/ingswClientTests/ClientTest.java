package it.polimi.ingswClientTests;

import it.polimi.ingsw.client.CliView;
import it.polimi.ingsw.client.Styler;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.VirtualView;
import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class ClientTest {


    @Test
    public void printMarketTest() throws Exception {
        CliView cliView = new CliView();
        Market market = new Market();
        cliView.showMessage(Styler.color('b',"#\t1\t\t2\t\t3\t\t4"),false);
        for (int i = 0; i< Market.MAXROWS; ++i){
            System.out.print(i+1 + "\t");
            for (int j=0;j<Market.MAXCOLUMNS;++j){
                if(market.getColor(i,j)== MarbleColor.RED){
                    System.out.print("RED\t\t");
                }else{
                    System.out.print(market.getColor(i,j).toString() + "\t");
                }

            }
            System.out.print("\n");
        }
    }

    @Test
    public void showWarehouseTest() throws Exception {
        CliView cliView = new CliView();
        PlayerWarehouse playerWarehouse = new PlayerWarehouse();

        //playerWarehouse.insertResource(Resource.COIN,1,0);
        //playerWarehouse.insertResource(Resource.SERVANT,2,1);
        playerWarehouse.insertResource(Resource.SERVANT,2,2);
        playerWarehouse.insertResource(Resource.SHIELD,3,1);
        //playerWarehouse.insertResource(Resource.SHIELD,3,2);
        //playerWarehouse.insertResource(Resource.SHIELD,3,3);

        cliView.showWarehouse(playerWarehouse);
    }

    @Test
    public void showStrongboxTest() throws Exception {
        CliView cliView = new CliView();
        Map<Resource,Integer> strongbox = new HashMap<>();
        strongbox.put(Resource.COIN,3);
        strongbox.put(Resource.SHIELD,37);
        strongbox.put(Resource.STONE,0);
        strongbox.put(Resource.SERVANT,0);

        cliView.showStrongbox(strongbox);
    }
}
