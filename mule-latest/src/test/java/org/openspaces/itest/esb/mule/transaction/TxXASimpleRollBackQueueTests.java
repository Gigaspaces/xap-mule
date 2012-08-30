package org.openspaces.itest.esb.mule.transaction;


/**
 *
 * Running os-queue within JTA transaction.
 * The transaction rollback at the first invocation and commit successfully in the second invocation.
 *
 * @author yitzhaki
 */
//public class TxXASimpleRollBackQueueTests extends AbstractMuleTests {
//
//    @Test
//	public void testSimpleQueueHandling() throws Exception {
//        muleClient.dispatch("os-queue://test1", "testme", null);
//
//        MuleMessage message = muleClient.request("os-queue://test3", 5000);
//        assertNotNull(message);
//        assertEquals("testmeAppender1Appender2", message.getPayload());
//    }
//
//    @Override
//    protected String getConfigResources() {
//        return "org/openspaces/itest/esb/mule/transaction/tx-xa-simple-roolback.xml";
//    }
//}