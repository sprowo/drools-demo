package com.prowo.drools.dynamic2;

import org.kie.api.io.Resource;

/**
 * Created by linan on 2017/8/15.
 */
public class ResourceWrapper {
    private Resource resource;

    private String targetResourceName;

    public ResourceWrapper(Resource resource, String targetResourceName) {
        this.resource = resource;
        this.targetResourceName = targetResourceName;
    }

    public Resource getResource() {
        return resource;
    }

    public String getTargetResourceName() {
        return targetResourceName;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public void setTargetResourceName(String targetResourceName) {
        this.targetResourceName = targetResourceName;
    }
}
