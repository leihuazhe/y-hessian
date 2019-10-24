/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yunji.com.caucho.hessian.io.beans;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class SubUser extends BaseUser implements Serializable {
    private static final long serialVersionUID = 4017613093053853415L;
    private String userName;
    private List<Integer> wage;

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Integer> getWage() {
        return wage;
    }

    public void setWage(List<Integer> wage) {
        this.wage = wage;
    }
}
