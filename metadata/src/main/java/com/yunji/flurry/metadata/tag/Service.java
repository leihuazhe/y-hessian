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
package com.yunji.flurry.metadata.tag;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @author Denim.leihz 2019-07-08 8:21 PM
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Service {

    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ServiceMeta {
        public int timeout;
        public String serviceVersion;
        public String groupId;
        public String artifactId;
        public String version;

        public String getServiceVersion() {
            return serviceVersion;
        }

        public void setServiceVersion(String serviceVersion) {
            this.serviceVersion = serviceVersion;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getArtifactId() {
            return artifactId;
        }

        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }
    }

    @XmlAttribute
    public String namespace;
    @XmlAttribute
    public String name;
    public String label;
    public String doc;

    public ServiceMeta meta;

    @XmlElementWrapper(name = "methods")
    @XmlElement(name = "method")
    public List<Method> methods = new ArrayList<>();

    @XmlElementWrapper(name = "structs")
    @XmlElement(name = "struct")
    public List<Struct> structDefinitions = new ArrayList<>();

    @XmlElementWrapper(name = "enums")
    @XmlElement(name = "tenum")
    public List<TEnum> enumDefinitions = new ArrayList<>();

    @XmlElementWrapper(name = "annotations")
    @XmlElement(name = "annotation")
    public List<Annotation> annotations = new ArrayList<>();

    private transient boolean initialized = false;
    private transient Map<String, Struct> structByName = new java.util.HashMap<String, Struct>();

    public synchronized void postInit() {
        if (initialized) return;
        for (Struct it : structDefinitions) {
            structByName.put(it.namespace + "." + it.name, it);
        }
        initialized = true;
    }

    public Struct lookupStruct(String qualifiedName) {
        postInit();
        return structByName.get(qualifiedName);
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public ServiceMeta getMeta() {
        return meta;
    }

    public void setMeta(ServiceMeta meta) {
        this.meta = meta;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }

    public List<Struct> getStructDefinitions() {
        return structDefinitions;
    }

    public void setStructDefinitions(List<Struct> structDefinitions) {
        this.structDefinitions = structDefinitions;
    }

    public List<TEnum> getEnumDefinitions() {
        return enumDefinitions;
    }

    public void setEnumDefinitions(List<TEnum> enumDefinitions) {
        this.enumDefinitions = enumDefinitions;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
    }

}
