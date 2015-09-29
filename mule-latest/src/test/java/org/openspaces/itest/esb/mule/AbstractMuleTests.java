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
package org.openspaces.itest.esb.mule;

import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.SpaceProxyConfigurer;

/**
 * Superclass for mule functional tests
 *
 * @author Rafi
 */
public abstract class AbstractMuleTests extends FunctionalTestCase {

    protected static final int TIMEOUT = 5000;

	protected GigaSpace gigaSpace;
    protected MuleClient muleClient;

    protected abstract String getConfigFile();

    @Override
    protected void doSetUp() throws Exception {
        super.doSetUp();
        muleClient = new MuleClient(muleContext);
		final String spaceName = "space";
		final String lookupGroups = System.getProperty("user.name");
        gigaSpace = new GigaSpaceConfigurer(new SpaceProxyConfigurer(spaceName).lookupGroups(lookupGroups)).create();
    }
}
