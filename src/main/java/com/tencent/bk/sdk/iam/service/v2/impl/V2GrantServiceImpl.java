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

package com.tencent.bk.sdk.iam.service.v2.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tencent.bk.sdk.iam.config.IamConfiguration;
import com.tencent.bk.sdk.iam.constants.V2IamUri;
import com.tencent.bk.sdk.iam.dto.grant.ManagerRoleGroupGrantDTO;
import com.tencent.bk.sdk.iam.dto.response.ResponseDTO;
import com.tencent.bk.sdk.iam.exception.IamException;
import com.tencent.bk.sdk.iam.service.impl.ApigwHttpClientServiceImpl;
import com.tencent.bk.sdk.iam.service.v2.V2GrantService;
import com.tencent.bk.sdk.iam.util.AuthRequestContext;
import com.tencent.bk.sdk.iam.util.JsonUtil;
import com.tencent.bk.sdk.iam.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class V2GrantServiceImpl implements V2GrantService {
    private final ApigwHttpClientServiceImpl apigwHttpClientService;
    private final IamConfiguration iamConfiguration;

    public V2GrantServiceImpl(ApigwHttpClientServiceImpl apigwHttpClientService, IamConfiguration iamConfiguration) {
        this.apigwHttpClientService = apigwHttpClientService;
        this.iamConfiguration = iamConfiguration;
    }

    @Override
    public void v2GrantRoleGroup(Integer groupId, ManagerRoleGroupGrantDTO managerRoleGroupGrantDTO) {
        AuthRequestContext.setRequestName("V2_MANAGER_ROLE_GROUP_GRANT");
        String url = String.format(V2IamUri.V2_MANAGER_ROLE_GROUP_GRANT, iamConfiguration.getSystemId(), groupId);
        try {
            String responseStr = apigwHttpClientService.doHttpPost(url, managerRoleGroupGrantDTO);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("grant V2 role group response|{}", responseStr);
                ResponseDTO<Object> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                }
            } else {
                log.warn("grant V2 role group got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("grant V2 role group failed", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void revokeRoleGroupPolicies(Integer groupId, ManagerRoleGroupGrantDTO managerRoleGroupGrantDTO) {
        AuthRequestContext.setRequestName("V2_MANAGER_ROLE_GROUP_GRANT_REVOKE");
        String url = String.format(V2IamUri.V2_MANAGER_ROLE_GROUP_GRANT_REVOKE, iamConfiguration.getSystemId(), groupId);
        try {
            String responseStr = apigwHttpClientService.doHttpDelete(url, managerRoleGroupGrantDTO);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("revoke role group policies response|{}", responseStr);
                ResponseDTO<Object> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                }
            } else {
                log.warn("revoke role group policies got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("revoke role group policies failed", e);
            throw new RuntimeException(e);
        }
    }
}
