package org.wso2.carbon.apimgt.rest.api.admin.v1;

import org.wso2.carbon.apimgt.rest.api.admin.v1.*;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.*;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import org.wso2.carbon.apimgt.api.APIManagementException;

import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.ErrorDTO;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.VHostDTO;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.VHostListDTO;

import java.util.List;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;


public interface VhostsApiService {
      public Response vhostsGet(MessageContext messageContext) throws APIManagementException;
      public Response vhostsPost(VHostDTO vhostDTO, MessageContext messageContext) throws APIManagementException;
      public Response vhostsVhostIdDelete(String vhostId, MessageContext messageContext) throws APIManagementException;
      public Response vhostsVhostIdPut(String vhostId, VHostDTO vhostDTO, MessageContext messageContext) throws APIManagementException;
}
