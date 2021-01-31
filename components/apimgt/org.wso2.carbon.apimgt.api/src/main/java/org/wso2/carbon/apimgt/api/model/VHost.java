package org.wso2.carbon.apimgt.api.model;

import java.util.List;

/**
 * This class represent the VHost
 */
public class VHost {
    private Integer id;
    private String uuid;
    private String name;
    private String description;
    private String url;
    private List<String> gatewayEnvironments;

    public VHost() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getGatewayEnvironments() {
        return gatewayEnvironments;
    }

    public void setGatewayEnvironments(List<String> gatewayEnvironments) {
        this.gatewayEnvironments = gatewayEnvironments;
    }
}
