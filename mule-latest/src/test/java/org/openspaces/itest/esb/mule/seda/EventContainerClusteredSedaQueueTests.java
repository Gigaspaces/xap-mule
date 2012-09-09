/*******************************************************************************
 * 
 * Copyright (c) 2012 GigaSpaces Technologies Ltd. All rights reserved
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *  
 ******************************************************************************/
package org.openspaces.itest.esb.mule.seda;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openspaces.itest.esb.mule.AbstractMuleTests;

import com.gigaspaces.annotation.pojo.SpaceId;

/**
 * Verifies OpenSpaces Mule wrapper object isn't written to space with a wrong routing value (ProtectiveMode).
 * (This happened when the routing property for this object was Mule's correlation id).
 * 
 * In order to test such a scenario, a 2,0 cluster is created where a polling container is set to poll
 * objects only from one of the partitions.
 * 
 * The polling container is an in bound for the first of two SEDA queues over the space.
 * 
 * @author idanmo
 * @since 9.1
 *
 */
public class EventContainerClusteredSedaQueueTests extends AbstractMuleTests {
	
	@Override
	protected String getConfigResources() {
		return "org/openspaces/itest/esb/mule/seda/event-container-clustered-queue.xml";
	}

	@Test
	public void test() throws Exception {
		final int objectsCount = 10;
		for (int i = 0; i < objectsCount; i++)			
			gigaSpace.write(new Data(null, false, false));
				
		repetitiveAssert(new Runnable() {
			@Override
			public void run() {
				assertEquals(objectsCount / 2, gigaSpace.count(new Data(null, true, true)));
			}
		}, 5, TimeUnit.SECONDS);		
	}
	
	public static class Verifier {
		public Data verify(Data data) {
			data.setVerified(true);
			return data;
		}
	}

	public static class Processor {
		public Data processData(Data data) {
			data.setProcessed(true);
			return data;
		}
	}
	
	public static class Data implements Serializable {		
		private static final long serialVersionUID = 1L;
		
		private String id;
		private boolean processed;
		private boolean verified;
		
		public Data()
		{
		}
		
		public Data(String id, boolean verified, boolean processed) {
			this.id = id;
			this.processed = processed;
			this.verified = verified;
		}

		@SpaceId(autoGenerate = true)
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public boolean isProcessed() {
			return processed;
		}

		public void setProcessed(boolean processed) {
			this.processed = processed;
		}

		public boolean isVerified() {
			return verified;
		}

		public void setVerified(boolean verified) {
			this.verified = verified;
		}
		
		@Override
		public String toString() {
			return "Data [id=" + id + ", verified=" + verified + ", processed=" + processed + "]";
		}
		
	}
	
}
