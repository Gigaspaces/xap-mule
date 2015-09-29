package org.openspaces.itest.esb.mule.queue;

public class FlowBasedSimpleQueueTests extends SimpleQueueTests{

    @Override
    protected String getConfigFile() {
        return "org/openspaces/itest/esb/mule/queue/flow-simple.xml";
    }
}
