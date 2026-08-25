/*
 * TencentBlueKing is pleased to support the open source community by making
 * 蓝鲸智云-权限中心Java SDK(iam-java-sdk) available.
 * Copyright (C) 2017-2021 THL A29 Limited, a Tencent company. All rights reserved.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://opensource.org/licenses/MIT
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.tencent.bk.sdk.iam.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tencent.bk.sdk.iam.config.IamConfiguration;
import com.tencent.bk.sdk.iam.constants.IamUri;
import com.tencent.bk.sdk.iam.dto.response.ResponseDTO;
import com.tencent.bk.sdk.iam.dto.system.SystemDTO;
import com.tencent.bk.sdk.iam.dto.system.SystemFieldDTO;
import com.tencent.bk.sdk.iam.exception.IamException;
import com.tencent.bk.sdk.iam.service.SystemService;
import com.tencent.bk.sdk.iam.util.AuthRequestContext;
import com.tencent.bk.sdk.iam.util.JsonUtil;
import com.tencent.bk.sdk.iam.util.ResponseUtil;
import com.tencent.bk.sdk.iam.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class SystemServiceImpl implements SystemService {

    private ApigwHttpClientServiceImpl apigwHttpClientService;
    private IamConfiguration iamConfiguration;

    public SystemServiceImpl(ApigwHttpClientServiceImpl apigwHttpClientService, IamConfiguration iamConfiguration) {
        this.apigwHttpClientService = apigwHttpClientService;
        this.iamConfiguration = iamConfiguration;
    }

    @Override
    public Boolean systemCheck(String systemId) {
        try {
            AuthRequestContext.setRequestName("QUERY_SYSTEM");
            String url = String.format(IamUri.QUERY_SYSTEM, systemId);
            String responseStr = apigwHttpClientService.doHttpGet(url);
            if (StringUtils.isNotBlank(responseStr)) {
                log.info("systemCheck response|{}", responseStr);
                ResponseDTO<Object> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                if (responseInfo != null) {
                    if (responseInfo.getCode() != 0) {
                        log.warn("systemCheck {} not exist", systemId);
                        return false;
                    } else {
                        return true;
                    }
                }
            } else {
                log.warn("systemCheck got empty response!");
            }
        } catch (IamException iamException) {
            log.error("systemCheck failed|{}|{}", iamException.getErrorCode(), iamException.getErrorMsg());
            throw iamException;
        } catch (Exception e) {
            log.error("systemCheck failed|{}", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public SystemFieldDTO getSystemFieldsInfo(String systemId) {
        try {
            AuthRequestContext.setRequestName("QUERY_SYSTEM");
            String url = String.format(IamUri.QUERY_SYSTEM, systemId);
            String responseStr = apigwHttpClientService.doHttpGet(url);
            if (StringUtils.isNotBlank(responseStr)) {
                log.info("getSystemFields response|{}", responseStr);
                ResponseDTO<SystemFieldDTO> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<SystemFieldDTO>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData();
                }
            } else {
                log.warn("getSystemFieldsr got empty response!");
            }
        } catch (IamException iamException) {
            log.error("getSystemFields failed|{}|{}", iamException.getErrorCode(), iamException.getErrorMsg());
            throw iamException;
        } catch (Exception e) {
            log.error("getSystemFields failed|{}", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Boolean createSystem(SystemDTO systemInfo) {
        try {
            AuthRequestContext.setRequestName("ADD_SYSTEM");
            String responseStr = apigwHttpClientService.doHttpPost(IamUri.ADD_SYSTEM, systemInfo);
            if (StringUtils.isNotBlank(responseStr)) {
                log.info("create system response|{}", responseStr);
                ResponseDTO<SystemFieldDTO> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<SystemFieldDTO>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return true;
                }
            } else {
                log.warn("create system got empty response!");
            }
        } catch (IamException iamException) {
            log.error("create system|{}|{}|{}", systemInfo, iamException.getErrorCode(), iamException.getErrorMsg());
            throw iamException;
        } catch (Exception e) {
            log.error("create system|{}|{}", systemInfo, e);
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Boolean systemCheck(String systemId, String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.systemCheck(systemId);
    }

    @Override
    public SystemFieldDTO getSystemFieldsInfo(String systemId, String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.getSystemFieldsInfo(systemId);
    }

    @Override
    public Boolean createSystem(SystemDTO systemInfo, String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.createSystem(systemInfo);
    }
}
