package dal.asd.dpl.TradingTest;

import  dal.asd.dpl.Trading.AiAcceptReject;

import org.junit.Assert;
import org.junit.Test;


public class AiAcceptRejectTest {

    AiAcceptReject tar = new AiAcceptReject();

    @Test
    public void tradeTest(){
        int a = tar.trade();
        Assert.assertEquals(-1,a);
    }

}
