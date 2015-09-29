package org.openspaces.itest.esb.mule.queue;

import org.junit.Assert;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.openspaces.itest.esb.mule.AbstractMuleTests;

/**
 * @author kimchy
 */
public class ClusteredSimpleQueueTests extends AbstractMuleTests {

    @Override
    protected String getConfigFile() {
        return "org/openspaces/itest/esb/mule/queue/clustered-simple.xml";
    }

    @Test
    public void testClusteredSimpleQueueHandling() throws Exception {
        muleClient.dispatch("os-queue://test1", "testme", null);

        MuleMessage message = muleClient.request("os-queue://test3", 500000);
        Assert.assertEquals("testmeAppender1Appender2", message.getPayload());
    }
}
