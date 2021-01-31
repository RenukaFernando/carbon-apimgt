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

package org.wso2.carbon.apimgt.rest.api.admin.v1.utils.mappings;

import org.wso2.carbon.apimgt.api.model.VHost;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.VHostDTO;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.VHostListDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manage VHost mapping to VHostDTO
 */
public class VHostMappingUtil {
    /**
     * Convert list of VHost to VHostListDTO
     *
     * @param vHostList List of VHosts
     * @return VhostListDTO containing VHost list
     */
    public static VHostListDTO fromVHostListToVHostListDTO(List<VHost> vHostList) {
        VHostListDTO vHostListDTO = new VHostListDTO();
        vHostListDTO.setCount(vHostList.size());

        List<VHostDTO> vHostDTOList = new ArrayList<>(vHostList.size());
        for (VHost vHost : vHostList) {
            vHostDTOList.add(fromVHostToVHostDTO(vHost));
        }
        vHostListDTO.setList(vHostDTOList);
        return vHostListDTO;
    }

    /**
     * Convert VHost to VHostDTO
     *
     * @param vHost VHost
     * @return VHostDTO
     */
    public static VHostDTO fromVHostToVHostDTO(VHost vHost) {
        VHostDTO vHostDTO = new VHostDTO();
        vHostDTO.setId(vHost.getUuid());
        vHostDTO.setName(vHost.getName());
        vHostDTO.setUrl(vHost.getUrl());
        vHostDTO.setDescription(vHost.getDescription());
        vHostDTO.setGatewayEnvironments(vHost.getGatewayEnvironments());
        return vHostDTO;
    }

    /**
     * Convert VHostDTO to VHost
     *
     * @param vhostDTO VHostDTO
     * @return VHost
     */
    public static VHost fromVHostDTOtoVHost(VHostDTO vhostDTO) {
        VHost vHost = new VHost();
        vHost.setUuid(vhostDTO.getId());
        vHost.setName(vhostDTO.getName());
        vHost.setUrl(vhostDTO.getUrl());
        vHost.setDescription(vhostDTO.getDescription());
        vHost.setGatewayEnvironments(vhostDTO.getGatewayEnvironments());
        return vHost;
    }
}
