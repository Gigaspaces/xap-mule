/*
 * Copyright 2006-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openspaces.itest.esb.mule.config;

import org.junit.Assert;
import org.junit.Test;
import org.mule.config.spring.SpringRegistry;
import org.openspaces.esb.mule.queue.OpenSpacesQueueConnector;
import org.openspaces.itest.esb.mule.AbstractMuleTests;
import org.springframework.context.ApplicationContext;

/**
 * @author Idan Moyal
 * @since 9.7.0
 */
public class OpenSpacesQueueConnectorConfigTests extends AbstractMuleTests {

    @Override
    protected String getConfigFile() {
        return "org/openspaces/itest/esb/mule/queue/queue-connector-batch-size-config.xml";
    }

    @Test
    public void testBatchSizeConfig() throws Exception {
        ApplicationContext context =
                muleContext.getRegistry().lookupObject(SpringRegistry.SPRING_APPLICATION_CONTEXT);
        OpenSpacesQueueConnector connector = (OpenSpacesQueueConnector) context.getBean("queueConnector");
        Assert.assertNotNull(connector);
        Assert.assertEquals(Integer.valueOf(10), connector.getBatchSize());
    }
}
