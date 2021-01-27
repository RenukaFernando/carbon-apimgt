package org.wso2.carbon.apimgt.rest.api.admin.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;

import javax.xml.bind.annotation.*;
import org.wso2.carbon.apimgt.rest.api.common.annotations.Scope;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;



public class VHostDTO   {
  
    private String id = null;
    private String name = null;
    private String description = null;
    private String url = null;
    private List<String> gatewayEnvironments = new ArrayList<String>();

  /**
   **/
  public VHostDTO id(String id) {
    this.id = id;
    return this;
  }

  
  @ApiModelProperty(example = "ece92bdc-e1e6-325c-b6f4-656208a041e9", value = "")
  @JsonProperty("id")
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  /**
   **/
  public VHostDTO name(String name) {
    this.name = name;
    return this;
  }

  
  @ApiModelProperty(example = "Sea Foods", required = true, value = "")
  @JsonProperty("name")
  @NotNull
 @Size(min=1,max=255)  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   **/
  public VHostDTO description(String description) {
    this.description = description;
    return this;
  }

  
  @ApiModelProperty(example = "Chinese sea foods", value = "")
  @JsonProperty("description")
 @Size(max=1024)  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   **/
  public VHostDTO url(String url) {
    this.url = url;
    return this;
  }

  
  @ApiModelProperty(example = "https://zfoods.com", value = "")
  @JsonProperty("url")
 @Size(max=1024)  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   **/
  public VHostDTO gatewayEnvironments(List<String> gatewayEnvironments) {
    this.gatewayEnvironments = gatewayEnvironments;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("gatewayEnvironments")
  public List<String> getGatewayEnvironments() {
    return gatewayEnvironments;
  }
  public void setGatewayEnvironments(List<String> gatewayEnvironments) {
    this.gatewayEnvironments = gatewayEnvironments;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VHostDTO vhost = (VHostDTO) o;
    return Objects.equals(id, vhost.id) &&
        Objects.equals(name, vhost.name) &&
        Objects.equals(description, vhost.description) &&
        Objects.equals(url, vhost.url) &&
        Objects.equals(gatewayEnvironments, vhost.gatewayEnvironments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, url, gatewayEnvironments);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VHostDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    gatewayEnvironments: ").append(toIndentedString(gatewayEnvironments)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

