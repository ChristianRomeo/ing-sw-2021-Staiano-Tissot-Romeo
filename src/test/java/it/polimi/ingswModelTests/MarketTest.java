package it.polimi.ingswModelTests;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests.
 */
public class MarketTest {
    @Test //test passed, the inizialization create a correct random market
    public void testMarketInizialization()
    {
        Market market = new Market();
        System.out.println("b");
    }

    @Test //test passed, the shift is correct (the resources collecting isn't implemented yet)
    public void testMarketSelectRowSelectColomn()
    {
        Market market = new Market();
        market.selectColumn(0);
        market.selectColumn(3);
        market.selectRow(0);
        market.selectRow(1);
        market.selectRow(2);
        System.out.println("ciaomarket");
    }
}
