package org.openspaces.itest.esb.mule.queue;

import org.mule.api.MuleMessage;
import org.openspaces.itest.esb.mule.AbstractMuleTests;

public class FlowBasedTxSimpleRollBackQueueTests extends AbstractMuleTests {

    public void testSimpleQueueHandling() throws Exception {
        muleClient.dispatch("os-queue://test1", "testme", null);

        MuleMessage message = muleClient.request("os-queue://test3", 5000);
        assertEquals("testmeAppender1Appender2", message.getPayload());
    }

    @Override
    protected String getConfigResources() {
        return "org/openspaces/itest/esb/mule/queue/flow-tx-xa-simple-rollback.xml";
    }
}
