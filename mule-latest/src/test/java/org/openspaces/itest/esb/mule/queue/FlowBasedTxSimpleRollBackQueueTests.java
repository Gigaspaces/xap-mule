package org.openspaces.itest.esb.mule.queue;

import org.junit.Assert;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.openspaces.itest.esb.mule.AbstractMuleTests;

public class FlowBasedTxSimpleRollBackQueueTests extends AbstractMuleTests {

    @Override
    protected String getConfigFile() {
        return "org/openspaces/itest/esb/mule/queue/flow-tx-xa-simple-rollback.xml";
    }

    @Test
    public void testSimpleQueueHandling() throws Exception {
        muleClient.dispatch("os-queue://test1", "testme", null);

        MuleMessage message = muleClient.request("os-queue://test3", 5000);
        Assert.assertEquals("testmeAppender1Appender2", message.getPayload());
    }
}
