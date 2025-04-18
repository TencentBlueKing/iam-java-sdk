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
import com.tencent.bk.sdk.iam.dto.system.TokenDTO;
import com.tencent.bk.sdk.iam.service.HttpClientService;
import com.tencent.bk.sdk.iam.service.TokenService;
import com.tencent.bk.sdk.iam.util.AuthRequestContext;
import com.tencent.bk.sdk.iam.util.JsonUtil;

import com.tencent.bk.sdk.iam.util.ThreadUtil;
import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenServiceImpl implements TokenService {

    private final IamConfiguration iamConfiguration;
    private final HttpClientService httpClientService;

    private String token = null;

    public TokenServiceImpl(IamConfiguration iamConfiguration, HttpClientService httpClientService) {
        this.iamConfiguration = iamConfiguration;
        this.httpClientService = httpClientService;
    }

    private void initToken() {
        try {
            AuthRequestContext.setRequestName("GET_SYSTEM_TOKEN");
            String getTokenResponse = httpClientService.doHttpGet(String.format(IamUri.GET_SYSTEM_TOKEN, iamConfiguration.getSystemId()));
            if (StringUtils.isNotBlank(getTokenResponse)) {
                log.debug("Get token response|{}", getTokenResponse);
                ResponseDTO<TokenDTO> tokenResponse = JsonUtil.fromJson(getTokenResponse, new TypeReference<ResponseDTO<TokenDTO>>() {
                });
                if (tokenResponse != null) {
                    log.debug("Get token from iam|{}|{}|{}", tokenResponse.getCode(), tokenResponse.getMessage(),
                            tokenResponse.getData().getToken());
                    if (tokenResponse.getCode() == 0) {
                        token = tokenResponse.getData().getToken();
                    }
                }
            }
            if (token == null) {
                throw new RuntimeException("Get token failed!");
            }
        } catch (Exception e) {
            log.error("Get iam system token failed!", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getToken() {
        if (token == null) {
            initToken();
        }
        return token;
    }

    @Override
    public String getToken(String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return getToken();
    }
}
