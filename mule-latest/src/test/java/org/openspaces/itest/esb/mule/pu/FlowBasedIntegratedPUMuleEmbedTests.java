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

package org.openspaces.itest.esb.mule.pu;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.openspaces.itest.esb.mule.SimpleMessage;
import org.openspaces.itest.utils.TestUtils;
import org.openspaces.pu.container.integrated.IntegratedProcessingUnitContainer;
import org.openspaces.pu.container.integrated.IntegratedProcessingUnitContainerProvider;

/**
 * Test the ability to run PU with mule imbedded in it.
 *
 * @author yitzhaki
 */
public class FlowBasedIntegratedPUMuleEmbedTests extends TestCase {

    public void testTakeSingleFromSpace() throws Exception {
        IntegratedProcessingUnitContainerProvider provider = new IntegratedProcessingUnitContainerProvider();
        provider.addConfigLocation("org/openspaces/itest/esb/mule/pu/flow-puembedmuleref2.xml");
        IntegratedProcessingUnitContainer container = (IntegratedProcessingUnitContainer) provider.createContainer();

        final GigaSpace gigaSpace = new GigaSpaceConfigurer(new UrlSpaceConfigurer("jini://*/*/space").lookupGroups(System.getProperty("user.name")).space()).gigaSpace();

        final int numberOfMsgs = 22;
        for (int i = 0; i < numberOfMsgs; i++) {
            SimpleMessage message = new SimpleMessage("Hello World " + i, false);
            gigaSpace.write(message);
        }

        final SimpleMessage template = new SimpleMessage(true);
        TestUtils.repetitive(new Runnable() {
            @Override
            public void run() {
                int count = gigaSpace.count(template);
                Assert.assertEquals(numberOfMsgs, count);
            }
        }, 10000);

        container.close();
    }
}