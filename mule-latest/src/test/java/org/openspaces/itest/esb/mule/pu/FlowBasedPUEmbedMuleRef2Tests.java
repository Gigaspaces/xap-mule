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

/**
 * Test the ability to run PU with mule imbedded in it.
 *
 * @author yitzhaki
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:/org/openspaces/itest/esb/mule/pu/flow-puembedmuleref2.xml")
//@see http://forums.mulesoft.com/questions/1042/the-mule-v3-7-0-flows-don-t-have-access-to-beans-defined-in-our-other-spring-v4-1-6.html
public class FlowBasedPUEmbedMuleRef2Tests extends PUEmbedMuleRef2Tests {

    @Override
    protected String getConfigFile() {
        return "org/openspaces/itest/esb/mule/pu/flow-puembedmuleref2.xml";
    }
}