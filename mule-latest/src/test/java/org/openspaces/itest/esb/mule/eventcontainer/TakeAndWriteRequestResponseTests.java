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

package org.openspaces.itest.esb.mule.eventcontainer;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;
import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
import org.openspaces.itest.esb.mule.AbstractMuleTests;
import org.openspaces.itest.esb.mule.Message;

import com.gigaspaces.annotation.pojo.SpaceId;

/**
 * Tests mule connector, receive and process single object at a time.
 *
 * @author yitzhaki
 */
public class TakeAndWriteRequestResponseTests extends AbstractMuleTests {

    @Override
    protected String getConfigFile() {
        return "org/openspaces/itest/esb/mule/eventcontainer/takeandwriterequest-response.xml";
    }

    @Test
    public void testTakeSingleFromSpace() throws Exception {
        

        int numberOfMsgs = 10;
        for (int i = 0; i < numberOfMsgs; i++) {
            SyncMessage message = new SyncMessage("Hello World " + i, 0);
            gigaSpace.write(message);
        }
        //blocking wait until the mule writes back the messages to the space after reading them.
        for (int i = 0; i < numberOfMsgs; i++) {
            SyncMessage template = new SyncMessage("Hello World " + i, 1);
            SyncMessage message = gigaSpace.take(template, TIMEOUT);
            Assert.assertNotNull(message);
        }
        Assert.assertEquals(0, gigaSpace.count(new SyncMessage()));
    }
    
    public static class SyncMessage implements Message,Serializable
    {
        private static final long serialVersionUID = 1L;
        
        private String message;
        private String id;
        private boolean read;
        private Integer stage;

        public SyncMessage() {
        }

       

        public SyncMessage(String message, Integer stage) {
            this.message = message;
            this.stage = stage;
        }

        public boolean isRead() {
            return read;
        }

        @SpaceId(autoGenerate = true)
        public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public void setRead(boolean read) {
            this.read = read;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getStage() {
            return stage;
        }

        public void setStage(Integer stage) {
            this.stage = stage;
        }



       
        @Override
        public String toString() {
            return "SyncMessage [message=" + message + ", stage=" + stage +"]";
        }

    }
    
    public static class MessageReader implements Callable {


        public Object onCall(MuleEventContext eventContext) throws Exception {
            Object payload = eventContext.getMessage().getPayload();
            System.out.println(Thread.currentThread().getName() +" onCall=" + payload);
            Object result = read(payload);
            return result;
        }

        /**
         * Sets to true the obj read attribute.
         */
        private Object read(Object obj) {
            if (obj instanceof Object[]) {
                for (Object o : ((Object[]) obj)) {
                    SyncMessage m = (SyncMessage) o;
                    m.setStage(m.getStage()+1);
                }
            } else {
                SyncMessage m = (SyncMessage) obj;
                m.setStage(m.getStage()+1);
            }
            return obj;
        }

    }
}