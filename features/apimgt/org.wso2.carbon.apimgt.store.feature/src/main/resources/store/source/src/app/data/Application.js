/**
 * Copyright (c) 2017, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

"use strict";
import APIClientFactory from "./APIClientFactory";

export default class Application {
    constructor(name, description, throttlingTier, kwargs) {
        this.id = kwargs ? kwargs.applicationId : null;
        this.client = new APIClientFactory().getAPIClient(Utils.getEnvironment().label).client;
        this.keys = new Map();
        this.tokens = new Map();
        for (let key in kwargs) {
            if (kwargs.hasOwnProperty(key)) {
                if (key === "keys") {
                    this._setKeys(kwargs[key]);
                    continue;
                }
                this[key] = kwargs[key];
            }
        }
    }

    /***
     * Set this.keys object by iterating the keys array received from REST API
     * @param keys {Array} An array of keys object containing either PRODUCTION or/and SANDBOX key information
     * @private
     */
    _setKeys(keys) {
        for (let key_obj of keys) {
            this.keys.set(key_obj.keyType, key_obj);
        }
    }

    /***
     * Get keys of the current instance of an application
     * @param key_type {string} Key type either `Production` or `SandBox`
     * @returns {promise} Set the fetched CS/CK into current instance and return keys array as Promise object
     */
    getKeys(key_type) {
        let promise_keys = this.client.then((client) => {
            return client.apis["Application (Individual)"].get_applications__applicationId__keys({applicationId: this.id});
        });
        return promise_keys.then(keys_response => {
            this._setKeys(keys_response.obj);
            return this.keys;
        });
    }

    /***
     * Generate token for this application instance
     * @returns {promise} Set the generated token into current instance and return tokenObject received as Promise object
     */
    generateToken(type) {
        let promise_token = this.client.then((client) => {
            let keys = this.keys.get(type);
            let request_content = {
                consumerKey: keys.consumerKey,
                consumerSecret: keys.consumerSecret,
                validityPeriod: 3600,
                scopes: ""
            };
            let payload = {applicationId: this.id, body: request_content};
            return client.apis["Application (Individual)"].post_applications__applicationId__generate_token(payload);
        });
        return promise_token.then(token_response => {
            let token = token_response.obj;
            this.tokens.set(type, token);
            return token;
        });
    }

    /***
     * Generate Consumer Secret and Consumer Key for this application instance
     * @param key_type {string} Key type either `Production` or `SandBox`
     * @returns {promise} Set the generated token into current instance and return tokenObject received as Promise object
     */
    generateKeys(key_type) {
        let promised_keys = this.client.then((client) => {
            let request_content =
                {
                    keyType: key_type, /* TODO: need to support dynamic key types ~tmkb*/
                    grantTypesToBeSupported: ["password", "client_credentials"], /* TODO: need to give UI checkboxes to get these information ~tmkb*/
                    callbackUrl: ""
                };
            let payload = {applicationId: this.id, body: request_content};
            return client.apis["Application (Individual)"].post_applications__applicationId__generate_keys(payload);
        });
        return promised_keys.then(keys_response => {
            this.keys.set(key_type, keys_response.obj);
            return this.keys.get(key_type);
        });
    }

    static get(id) {
        let client = new APIClientFactory().getAPIClient(Utils.getEnvironment().label).client;
        let promise_get = client.then(
            (client) => {
                return client.apis["Application (Individual)"].get_applications__applicationId_({applicationId: id});
            });
        return promise_get.then(response => {
            let app_json = response.obj;
            return new Application(app_json.name, app_json.description, app_json.throttlingTier, app_json);
        });
    }
}

Application.KEY_TYPES = {
    PRODUCTION: "PRODUCTION",
    SANDBOX: "SANDBOX"
};