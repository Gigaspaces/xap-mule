package org.openspaces.itest.esb.mule.queue;

import org.junit.Assert;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.openspaces.itest.esb.mule.AbstractMuleTests;

/**
 * Testing runing os-queue within trnasaction within local OpenSapce transaction.
 * The transaction commit succesufly.
 *
 * @author yitzhaki
 */
public class TxSimpleQueueTests extends AbstractMuleTests {


    @Override
    protected String getConfigFile() {
        return "org/openspaces/itest/esb/mule/queue/tx-simple.xml";
    }

    @Test
    public void testSimpleQueueHandling() throws Exception {
        muleClient.dispatch("os-queue://test1", "testme", null);

        MuleMessage message = muleClient.request("os-queue://test3", 5000);
        System.out.println(gigaSpace.read(null));
        Assert.assertEquals("testmeAppender1Appender2", message.getPayload());
    }
}