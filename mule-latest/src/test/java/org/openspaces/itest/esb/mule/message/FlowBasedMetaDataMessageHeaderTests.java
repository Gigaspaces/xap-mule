package org.openspaces.itest.esb.mule.message;

public class FlowBasedMetaDataMessageHeaderTests extends MetaDataMessageHeaderTests {
    
    @Override
    protected String getConfigFile() {
        return "org/openspaces/itest/esb/mule/message/flow-metadatamessageheader.xml";
    }
}
