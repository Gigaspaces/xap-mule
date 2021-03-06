package org.openspaces.itest.esb.mule.queue;

import org.junit.Assert;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.openspaces.itest.esb.mule.AbstractMuleTests;

/**
 * Running os-queue within OpenSpace local transaction.
 * The transaction rollback at the first invocation and commit successfully in the second invocation.
 *
 * @author yitzhaki
 */
public class TxSimpleRollBackQueueTests extends AbstractMuleTests {

    @Override
    protected String getConfigFile() {
        return "org/openspaces/itest/esb/mule/queue/tx-simple-roolback.xml";
    }

    @Test
    public void testSimpleQueueHandling() throws Exception {
        muleClient.dispatch("os-queue://test1", "testme", null);

        MuleMessage message = muleClient.request("os-queue://test3", 5000);
        Assert.assertEquals("testmeAppender1Appender2", message.getPayload());
    }
}