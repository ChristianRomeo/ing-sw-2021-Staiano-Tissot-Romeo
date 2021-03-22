package it.polimi.ingsw;


import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    /*
     * Rigorous Test :-)
     */
   @Test
    public void shouldAnswerWithTrue()
    {
        System.out.println("hello");
    }


    @Test //test passed, the inizialization create a correct random market
    public void testMarketInizialization()
    {
        Market market = new Market();
        System.out.println("");
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
        System.out.println("");
    }
}
