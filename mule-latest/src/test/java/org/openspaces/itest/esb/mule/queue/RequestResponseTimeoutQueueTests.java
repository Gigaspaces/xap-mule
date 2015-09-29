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

package org.openspaces.itest.esb.mule.queue;

import org.junit.Assert;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.openspaces.itest.esb.mule.AbstractMuleTests;
import org.openspaces.itest.utils.TestUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Idan Moyal
 * @since 9.7.0
 */
public class RequestResponseTimeoutQueueTests extends AbstractMuleTests {

    @Override
    protected String getConfigFile() {
        return "org/openspaces/itest/esb/mule/queue/request-response-timeout-queue.xml";
    }

    @Test
    public void testResponseTimeout() throws Exception {

        final CountDownLatch latch = new CountDownLatch(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("Blocking read object from space (written by mule)...");
                Object result = gigaSpace.read(null, 30000);
                logger.info("Object written to space [" + result + "]");
                if (result != null) {
                    latch.countDown();
                }
            }
        }).start();

        try {
            logger.info("Sending mule message...");
            muleClient.send("os-queue://bookingflow.in", "message1", null, 10000);
            Assert.fail("Exception expected");
        } catch (Exception e) {
            logger.info("Caught exception [" + e.getMessage() + "] -> OK");
        }

        logger.info("Waiting for mule response to be written to space...");
        latch.await(10, TimeUnit.SECONDS);

        logger.info("Waiting for response to expire from space...");
        TestUtils.repetitive(new Runnable() {
            @Override
            public void run() {
                Assert.assertEquals("Space should not contain entries after timeout", 0, gigaSpace.count(null));
            }
        }, 5000);

        logger.info("OK");
    }
}
