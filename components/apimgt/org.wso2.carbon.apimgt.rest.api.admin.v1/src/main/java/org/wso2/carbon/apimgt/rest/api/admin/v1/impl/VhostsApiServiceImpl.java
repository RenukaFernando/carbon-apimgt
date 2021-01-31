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
import org.wso2.carbon.apimgt.api.model.VHost;
import org.wso2.carbon.apimgt.impl.APIAdminImpl;
import org.wso2.carbon.apimgt.rest.api.admin.v1.VhostsApiService;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.VHostDTO;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.VHostListDTO;
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
     * @return response with VHosts in the tenant domain of the user
     * @throws APIManagementException if failed to get VHosts in the user's domain
     */
    @Override
    public Response vhostsGet(MessageContext messageContext) throws APIManagementException {
        APIAdmin apiAdmin = new APIAdminImpl();
        String tenantDomain = RestApiCommonUtil.getLoggedInUserTenantDomain();
        List<VHost> vHostList = apiAdmin.getAllVhosts(tenantDomain);
        VHostListDTO vHostListDTO = VHostMappingUtil.fromVHostListToVHostListDTO(vHostList);
        return Response.ok().entity(vHostListDTO).build();
    }

    /**
     * Add a VHost
     *
     * @param body           request body as VHostDTO
     * @param messageContext message context
     * @return response for created VHostDTO
     * @throws APIManagementException if failed to add the VHost
     */
    public Response vhostsPost(VHostDTO body, MessageContext messageContext) throws APIManagementException {
        try {
            APIAdmin apiAdmin = new APIAdminImpl();
            String tenantDomain = RestApiCommonUtil.getLoggedInUserTenantDomain();
            VHost vHost = VHostMappingUtil.fromVHostDTOtoVHost(body);
            VHostDTO vHostDTO = VHostMappingUtil.fromVHostToVHostDTO(apiAdmin.addVhost(tenantDomain, vHost));
            URI location = new URI(RestApiConstants.RESOURCE_PATH_VHost + "/" + vHostDTO.getId());
            return Response.created(location).entity(vHostDTO).build();
        } catch (URISyntaxException e) {
            String errorMessage = "Error while adding new a VHost to API : " + body.getName() + "-" + e.getMessage();
            RestApiUtil.handleInternalServerError(errorMessage, e, log);
        }
        return null;
    }

    /**
     * Delete a VHost
     *
     * @param vhostId        Id of the VHost
     * @param messageContext message context
     * @return response of VHost deletion
     * @throws APIManagementException if failed to delete the VHost
     */
    public Response vhostsVhostIdDelete(String vhostId, MessageContext messageContext) throws APIManagementException {
        APIAdmin apiAdmin = new APIAdminImpl();
        String tenantDomain = RestApiCommonUtil.getLoggedInUserTenantDomain();
        apiAdmin.deleteVhost(tenantDomain, vhostId);
        return Response.ok().build();
    }

    /**
     * Update a Vhost
     * @param vhostId id of the VHost
     * @param body request body of the VHost
     * @param messageContext message context
     * @return response of updated VHost
     * @throws APIManagementException if failed to update VHost
     */
    public Response vhostsVhostIdPut(String vhostId, VHostDTO body, MessageContext messageContext) throws APIManagementException {
        try {
            APIAdmin apiAdmin = new APIAdminImpl();
            body.setId(vhostId);
            VHost vHost = VHostMappingUtil.fromVHostDTOtoVHost(body);
            String tenantDomain = RestApiCommonUtil.getLoggedInUserTenantDomain();

            VHost currentVhost = apiAdmin.getVhost(tenantDomain, vHost.getUuid());
            vHost.setId(currentVhost.getId());
            if (!currentVhost.getName().equals(vHost.getName())) {
                RestApiUtil.handleBadRequest("Virtual Host name can not be changed", log);
            }
            if (!currentVhost.getUrl().equals(vHost.getUrl())) {
                RestApiUtil.handleBadRequest("Virtual Host URL can not be changed", log);
            }
            if (currentVhost.getGatewayEnvironments().size() > vHost.getGatewayEnvironments().size() ||
                    !vHost.getGatewayEnvironments().containsAll(currentVhost.getGatewayEnvironments())) {
                RestApiUtil.handleBadRequest("Assigned gateway environments of Virtual Host can not be removed", log);
            }

            VHostDTO vHostDTO = VHostMappingUtil.fromVHostToVHostDTO(apiAdmin.updateVhost(tenantDomain, vHost));
            URI location = new URI(RestApiConstants.RESOURCE_PATH_VHost + "/" + vHostDTO.getId());
            return Response.ok(location).entity(vHostDTO).build();
        } catch (URISyntaxException e) {
            String errorMessage = "Error while updating VHost : " + vhostId;
            RestApiUtil.handleInternalServerError(errorMessage, e, log);
        }
        return null;
    }
}
