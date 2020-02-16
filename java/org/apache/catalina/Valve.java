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

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;

/**
 * <p>A <b>Valve</b> is a request processing component associated with a
 * particular Container.  A series of Valves are generally associated with
 * each other into a Pipeline.  The detailed contract for a Valve is included
 * in the description of the <code>invoke()</code> method below.</p>
 *
 * <b>HISTORICAL NOTE</b>:  The "Valve" name was assigned to this concept
 * because a valve is what you use in a real world pipeline to control and/or
 * modify flows through it.
 *
 * @author Craig R. McClanahan
 * @author Gunnar Rjnning
 * @author Peter Donald
 */
//Valve 是阀门组件，穿插在 Container 容器中，可以把它理解成请求拦截器，在 tomcat 接收到网络请求与触发 Servlet 之间执行

/**
 * tomcat为我们提供了一系列的Valve
 * - AccessLogValve，记录请求日志，默认会开启
 * - RemoteAddrValve，可以做访问控制，比如限制IP黑白名单
 * - RemoteIpValve，主要用于处理 X-Forwarded-For 请求头，用来识别通过HTTP代理或负载均衡方式连接到Web服务器的客户端最原始的IP地址的HTTP请求头字段
 * ————————————————
 * 版权声明：本文为CSDN博主「黄小厮」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/Dwade_mia/article/details/79244157
 */

/**
 * StandardEgineValve：StandardEngine 中唯一（basic）的阀门，主要用于从request 中选择其host映射的StandardHost容器
 *
 * AccessLogValve：StandardHost 中第一个（first）阀门，主要用于Pipeline执行结束后记录日志信息
 *
 * ErrorReportValve：StandardHost 中第二个（next）阀门，主要用于获取request中的错误信息，并将错误信息封装到response中返回给用户展示
 *
 * StandardHostValve：StandardHost 中最后（basic）一个阀门，主要用于从request 中选择其context映射的 StandardHost 容器 和获取 request 中的 Session
 *
 * AuthenticatorValve：StandContext 中第一个（first）阀门，主要用于用户权限的验证以及对response 设置header部分属性
 *
 * StandardContextValve：StandContext 中最后一个（basic）阀门，主要用于从request 中选择其 wrapper 映射的 StandardWrapper 容器以及控制禁止直接对  /META-INF/ 和 /WEB-INF/ 目录下资源的直接访问
 *
 * StandardWrapperValve：StandardWrapper 中唯一（basic）的阀门，主要用于调用StandardWrapper 的 loadServlet 方法获取 Servlet 实例、调用 ApplicationFilterFactory的createFilterChain 方法创建 request 过滤器链、记录请求次数、请求时间、请求最大时间、最小时间等
 * ————————————————
 * 版权声明：本文为CSDN博主「dragon@oo」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/ywlmsm1224811/article/details/91384813
 */
public interface Valve {


    //-------------------------------------------------------------- Properties

    /**
     * @return the next Valve in the pipeline containing this Valve, if any.
     */
    public Valve getNext();


    /**
     * Set the next Valve in the pipeline containing this Valve.
     *
     * @param valve The new next valve, or <code>null</code> if none
     */
    public void setNext(Valve valve);


    //---------------------------------------------------------- Public Methods


    /**
     * Execute a periodic task, such as reloading, etc. This method will be
     * invoked inside the classloading context of this container. Unexpected
     * throwables will be caught and logged.
     */
    public void backgroundProcess();


    /**
     * <p>Perform request processing as required by this Valve.</p>
     *
     * <p>An individual Valve <b>MAY</b> perform the following actions, in
     * the specified order:</p>
     * <ul>
     * <li>Examine and/or modify the properties of the specified Request and
     *     Response.
     * <li>Examine the properties of the specified Request, completely generate
     *     the corresponding Response, and return control to the caller.
     * <li>Examine the properties of the specified Request and Response, wrap
     *     either or both of these objects to supplement their functionality,
     *     and pass them on.
     * <li>If the corresponding Response was not generated (and control was not
     *     returned, call the next Valve in the pipeline (if there is one) by
     *     executing <code>getNext().invoke()</code>.
     * <li>Examine, but not modify, the properties of the resulting Response
     *     (which was created by a subsequently invoked Valve or Container).
     * </ul>
     *
     * <p>A Valve <b>MUST NOT</b> do any of the following things:</p>
     * <ul>
     * <li>Change request properties that have already been used to direct
     *     the flow of processing control for this request (for instance,
     *     trying to change the virtual host to which a Request should be
     *     sent from a pipeline attached to a Host or Context in the
     *     standard implementation).
     * <li>Create a completed Response <strong>AND</strong> pass this
     *     Request and Response on to the next Valve in the pipeline.
     * <li>Consume bytes from the input stream associated with the Request,
     *     unless it is completely generating the response, or wrapping the
     *     request before passing it on.
     * <li>Modify the HTTP headers included with the Response after the
     *     <code>getNext().invoke()</code> method has returned.
     * <li>Perform any actions on the output stream associated with the
     *     specified Response after the <code>getNext().invoke()</code> method has
     *     returned.
     * </ul>
     *
     * @param request The servlet request to be processed
     * @param response The servlet response to be created
     *
     * @exception IOException if an input/output error occurs, or is thrown
     *  by a subsequently invoked Valve, Filter, or Servlet
     * @exception ServletException if a servlet error occurs, or is thrown
     *  by a subsequently invoked Valve, Filter, or Servlet
     */
    public void invoke(Request request, Response response)
        throws IOException, ServletException;


    public boolean isAsyncSupported();
}
