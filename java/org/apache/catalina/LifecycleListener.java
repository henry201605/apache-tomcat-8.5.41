/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.apache.catalina;


/**
 * Interface defining a listener for significant events (including "component
 * start" and "component stop" generated by a component that implements the
 * Lifecycle interface. The listener will be fired after the associated state
 * change has taken place.
 *
 * @author Craig R. McClanahan
 */

/**
 * 要订阅事件的实体类需要实现LifecycleListener
 *
 * 默认情况下，tomcat会内置一些LifecycleListener，配置在server.xml中，除了xml中的LifecycleListener，
 * 还有org.apache.catalina.core.NamingContextListener，而这个LifecycleListener是在StandardServer
 * 的构造器中添加的，各个LifecycleListener的作用在此不再细说。如果我们在tomcat启动、停止的时候增加额外的逻辑，
 * 比如发送邮件通知，则可以从这个地方入手
 */
public interface LifecycleListener {


    /**
     * Acknowledge the occurrence of the specified event.
     *
     * @param event LifecycleEvent that has occurred
     */
    public void lifecycleEvent(LifecycleEvent event);


}
