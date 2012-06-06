package org.openspaces.itest.esb.mule.queue;

import org.mule.api.MuleMessage;
import org.mule.api.config.MuleProperties;
import org.mule.api.transport.PropertyScope;
import org.openspaces.itest.esb.mule.AbstractMuleTests;

/**
 * @author anna
 * @since 8.0.4
 */
public class RequestResponseQueueTests extends AbstractMuleTests {

    public void testSimpleQueueHandling() throws Exception {

    	// message 1 
        MuleMessage result1 = muleClient.send("os-queue://bookingflow.in", "message1", null, 5000);
        
        assertEquals("Val1", result1.getProperty("Header1", PropertyScope.OUTBOUND));
        assertEquals("Val2", result1.getProperty("Header2", PropertyScope.OUTBOUND));
        assertEquals("Val3", result1.getProperty("Header3", PropertyScope.OUTBOUND));
        
        assertEquals("os-queue://external.queue.in", result1.getOutboundProperty(MuleProperties.MULE_ENDPOINT_PROPERTY));
        assertEquals("message1BookingServiceInternalBookingServiceExternalBookingService", result1.getPayload());
        
        
        // message 2
        MuleMessage result2 = muleClient.send("os-queue://internal.queue.in", "message2", null, 5000);
        
        assertNull(result2.getProperty("Header1", PropertyScope.OUTBOUND));
        assertEquals("Val2", result2.getProperty("Header2", PropertyScope.OUTBOUND));
        assertEquals("Val3", result2.getProperty("Header3", PropertyScope.OUTBOUND));
       
        assertEquals("os-queue://external.queue.in", result2.getOutboundProperty(MuleProperties.MULE_ENDPOINT_PROPERTY));
        assertEquals("message2InternalBookingServiceExternalBookingService", result2.getPayload());
    
        
        // message 3
        MuleMessage result3 = muleClient.send("os-queue://external.queue.in", "message3", null, 5000);
        assertNull(result3.getProperty("Header1", PropertyScope.OUTBOUND));
        assertNull(result3.getProperty("Header2", PropertyScope.OUTBOUND));
        assertEquals("Val3", result3.getProperty("Header3", PropertyScope.OUTBOUND));
        
        assertEquals("os-queue://external.queue.in", result3.getOutboundProperty(MuleProperties.MULE_ENDPOINT_PROPERTY));
        assertEquals("message3ExternalBookingService", result3.getPayload());

       
    }

    @Override
    protected String getConfigResources() {
        return "org/openspaces/itest/esb/mule/queue/request-response-queue.xml";
    }
}
