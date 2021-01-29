/*
 *  Copyright (c) 2021, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.wso2.carbon.apimgt.rest.api.admin.v1.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.wso2.carbon.apimgt.api.APIAdmin;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.model.Label;
import org.wso2.carbon.apimgt.api.model.VHost;
import org.wso2.carbon.apimgt.impl.APIAdminImpl;
import org.wso2.carbon.apimgt.rest.api.admin.v1.VhostsApiService;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.ErrorDTO;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.LabelDTO;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.VHostDTO;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.VHostListDTO;
import org.wso2.carbon.apimgt.rest.api.admin.v1.utils.mappings.LabelMappingUtil;
import org.wso2.carbon.apimgt.rest.api.admin.v1.utils.mappings.VHostMappingUtil;
import org.wso2.carbon.apimgt.rest.api.common.RestApiCommonUtil;
import org.wso2.carbon.apimgt.rest.api.common.RestApiConstants;
import org.wso2.carbon.apimgt.rest.api.util.utils.RestApiUtil;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


public class VhostsApiServiceImpl implements VhostsApiService {

    private static final Log log = LogFactory.getLog(VhostsApiServiceImpl.class);

    /**
     * Retrieve VHosts in the tenant domain of the user
     *
     * @param messageContext message context
     * @return VHosts in the tenant domain of the user
     */
    @Override
    public Response vhostsGet(MessageContext messageContext) {
        try {
            APIAdmin apiAdmin = new APIAdminImpl();
            String tenantDomain = RestApiCommonUtil.getLoggedInUserTenantDomain();
            List<VHost> vHostList = apiAdmin.getAllVhosts(tenantDomain);
            VHostListDTO vHostListDTO = VHostMappingUtil.fromVHostListToVHostListDTO(vHostList);
            return Response.ok().entity(vHostListDTO).build();
        } catch (APIManagementException e) {
            String errorMessage = "Error while retrieving VHosts";
            RestApiUtil.handleInternalServerError(errorMessage, e, log);
        }
        return null;
    }

    /**
     * Add a VHost
     *
     * @param body           request body as VHostDTO
     * @param messageContext message context
     * @return created VHostDTO
     */
    public Response vhostsPost(VHostDTO body, MessageContext messageContext) {
        try {
            APIAdmin apiAdmin = new APIAdminImpl();
            String tenantDomain = RestApiCommonUtil.getLoggedInUserTenantDomain();
            VHost vHost = VHostMappingUtil.fromVHostDTOtoVHost(body);
            VHostDTO vHostDTO = VHostMappingUtil.fromVHostToVHostDTO(apiAdmin.addVhost(tenantDomain, vHost));
            URI location = new URI(RestApiConstants.RESOURCE_PATH_VHost + "/" + vHostDTO.getId());
            return Response.created(location).entity(vHostDTO).build();
        } catch (APIManagementException | URISyntaxException e) {
            String errorMessage = "Error while adding new a VHost to API : " + body.getName() + "-" + e.getMessage();
            RestApiUtil.handleInternalServerError(errorMessage, e, log);
        }
        return null;
    }

    public Response vhostsVhostIdDelete(String vhostId, MessageContext messageContext) {
        try {
            APIAdmin apiAdmin = new APIAdminImpl();
            String tenantDomain = RestApiCommonUtil.getLoggedInUserTenantDomain();
            apiAdmin.deleteVhost(tenantDomain, vhostId);
            return Response.ok().build();
        } catch (APIManagementException e) {
            String errorMessage = "Error while deleting VHost : " + vhostId;
            RestApiUtil.handleInternalServerError(errorMessage, e, log);
        }
        return null;
    }

    public Response vhostsVhostIdPut(String vhostId, VHostDTO body, MessageContext messageContext) {
        try {
            APIAdmin apiAdmin = new APIAdminImpl();
            body.setId(vhostId);
            VHost vHost = VHostMappingUtil.fromVHostDTOtoVHost(body);
            String tenantDomain = RestApiCommonUtil.getLoggedInUserTenantDomain();
            VHostDTO vHostDTO = VHostMappingUtil.fromVHostToVHostDTO(apiAdmin.updateVhost(tenantDomain, vHost));
            URI location = new URI(RestApiConstants.RESOURCE_PATH_VHost + "/" + vHostDTO.getId());
            return Response.ok(location).entity(vHostDTO).build();
        } catch (APIManagementException | URISyntaxException e) {
            String errorMessage = "Error while updating VHost : " + vhostId;
            RestApiUtil.handleInternalServerError(errorMessage, e, log);
        }
        return null;
    }
}
