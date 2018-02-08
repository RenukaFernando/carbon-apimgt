package org.wso2.carbon.apimgt.core.internal;
/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.apimgt.core.configuration.models.APIMConfigurations;
import org.wso2.carbon.apimgt.core.configuration.models.EnvironmentConfigurations;
import org.wso2.carbon.config.ConfigurationException;
import org.wso2.carbon.config.provider.ConfigProvider;
import org.wso2.carbon.secvault.SecureVault;
import org.yaml.snakeyaml.error.YAMLException;

import java.util.Map;

/**
 * Class used to hold the APIM configuration
 */
public class ServiceReferenceHolder {
    private static final Logger log = LoggerFactory.getLogger(ServiceReferenceHolder.class);
    private static ServiceReferenceHolder instance = new ServiceReferenceHolder();
    private ConfigProvider configProvider;
    private SecureVault secureVault;

    private ServiceReferenceHolder() {}

    public static ServiceReferenceHolder getInstance() {
        return instance;
    }

    /**
     * Sets the configProvider instance
     *
     * @param configProvider configProvider instance to set
     */
    public void setConfigProvider(ConfigProvider configProvider) {
        this.configProvider = configProvider;
    }

    /**
     * Gives the APIMConfigurations explicitly set in the deployment yaml or the default configurations
     *
     * @return APIMConfigurations
     */
    public APIMConfigurations getAPIMConfiguration() {
        try {
            if (configProvider != null) {
                return configProvider.getConfigurationObject(APIMConfigurations.class);
            } else {
                throw new YAMLException("Configuration provider is null");
            }
        } catch (ConfigurationException e) {
            throw new YAMLException("error getting config : org.wso2.carbon.apimgt.core.internal.APIMConfiguration", e);
        }
    }

    /**
     * Gives the EnvironmentConfigurations explicitly set in the deployment yaml or the default configurations
     *
     * @return EnvironmentConfigurations
     */
    public EnvironmentConfigurations getEnvironmentConfigurations() {
        try {
            if (configProvider != null) {
                return configProvider.getConfigurationObject(EnvironmentConfigurations.class);
            } else {
                throw new YAMLException("Configuration provider is null");
            }
        } catch (ConfigurationException e) {
            throw new YAMLException("error getting config : org.wso2.carbon.apimgt.core.internal.EnvironmentConfigurations", e);
        }
    }

    /**
    * This method is to get configuration map of a given namespace
    *
    * @param namespace namespace defined in deployment.yaml
    * @return resource path to scope mapping
    */
    public Map<String, String> getRestAPIConfigurationMap(String namespace) {
        try {
            if (configProvider != null) {
                return (Map<String, String>) configProvider.getConfigurationObject(namespace);
            } else {
                throw new YAMLException("Configuration provider is null");
            }
        } catch (ConfigurationException e) {
            throw new YAMLException("Error while reading the configurations map of namespace : " +
                    "org.wso2.carbon.apimgt.core.internal.APIMConfiguration", e);
        }
    }

    /**
     * Gives the secure vault instance if already set
     *
     * @return secureVault instance
     */
    public SecureVault getSecureVault() {
        return secureVault;
    }

    /**
     * Sets the secure vault instance
     *
     * @param secureVault secureVault instance to set
     */
    public void setSecureVault(SecureVault secureVault) {
        this.secureVault = secureVault;
    }
}
