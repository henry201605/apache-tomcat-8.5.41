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
 * The list of valid states for components that implement {@link Lifecycle}.
 * See {@link Lifecycle} for the state transition diagram.
 */
public enum LifecycleState {
    // LifecycleBase实例化完成时的状态
    NEW(false, null),

    // 容器正在初始化的状态，在INITIALIZED之前
    INITIALIZING(false, Lifecycle.BEFORE_INIT_EVENT),

    // 初始化完成的状态
    INITIALIZED(false, Lifecycle.AFTER_INIT_EVENT),

    // 启动前
    STARTING_PREP(false, Lifecycle.BEFORE_START_EVENT),

    // 启动过程中的状态
    STARTING(true, Lifecycle.START_EVENT),

    // 启动完成
    STARTED(true, Lifecycle.AFTER_START_EVENT),

    // 停止前的状态
    STOPPING_PREP(true, Lifecycle.BEFORE_STOP_EVENT),

    // 停止过程中
    STOPPING(false, Lifecycle.STOP_EVENT),

    // 停止完成
    STOPPED(false, Lifecycle.AFTER_STOP_EVENT),

    // 销毁中
    DESTROYING(false, Lifecycle.BEFORE_DESTROY_EVENT),

    // 完成销毁
    DESTROYED(false, Lifecycle.AFTER_DESTROY_EVENT),

    // 启动、停止过程中出现异常
    FAILED(false, null);

    private final boolean available;
    private final String lifecycleEvent;

    private LifecycleState(boolean available, String lifecycleEvent) {
        this.available = available;
        this.lifecycleEvent = lifecycleEvent;
    }

    /**
     * May the public methods other than property getters/setters and lifecycle
     * methods be called for a component in this state? It returns
     * <code>true</code> for any component in any of the following states:
     * <ul>
     * <li>{@link #STARTING}</li>
     * <li>{@link #STARTED}</li>
     * <li>{@link #STOPPING_PREP}</li>
     * </ul>
     *
     * @return <code>true</code> if the component is available for use,
     *         otherwise <code>false</code>
     */
    public boolean isAvailable() {
        return available;
    }

    public String getLifecycleEvent() {
        return lifecycleEvent;
    }
}
