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
import com.tencent.bk.sdk.iam.dto.SelectionDTO;
import com.tencent.bk.sdk.iam.dto.resource.ResourceTypeDTO;
import com.tencent.bk.sdk.iam.dto.response.ResponseDTO;
import com.tencent.bk.sdk.iam.dto.system.SystemFieldDTO;
import com.tencent.bk.sdk.iam.exception.IamException;
import com.tencent.bk.sdk.iam.service.IamResourceService;
import com.tencent.bk.sdk.iam.service.SystemService;
import com.tencent.bk.sdk.iam.util.AuthRequestContext;
import com.tencent.bk.sdk.iam.util.JsonUtil;
import com.tencent.bk.sdk.iam.util.ResponseUtil;
import com.tencent.bk.sdk.iam.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Slf4j
public class ResourceServiceImpl implements IamResourceService {
    private IamConfiguration iamConfiguration;
    private ApigwHttpClientServiceImpl apigwHttpClientService;
    private SystemService systemService;

    public ResourceServiceImpl(
        IamConfiguration iamConfiguration,
        ApigwHttpClientServiceImpl apigwHttpClientService,
        SystemService systemService
    ) {
        this.iamConfiguration = iamConfiguration;
        this.apigwHttpClientService = apigwHttpClientService;
        this.systemService = systemService;
    }

    @Override
    public boolean resourceCheck(String resourceType) {
        SystemFieldDTO systemFieldDTO = systemService.getSystemFieldsInfo(iamConfiguration.getSystemId());
        List<ResourceTypeDTO> resources = systemFieldDTO.getResourceType();
        // 系统下注册资源为空
        if (resources.isEmpty()) {
            return false;
        } else {
            // 资源已存在
            for (ResourceTypeDTO resource: resources) {
                if (resource.getId().equals(resourceType)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<SelectionDTO> getSystemInstanceSelector() {
        SystemFieldDTO systemFieldDTO = systemService.getSystemFieldsInfo(iamConfiguration.getSystemId());
        return systemFieldDTO.getInstanceSelections();
    }

    @Override
    public boolean createResource(List<ResourceTypeDTO> resourceTypeDTO) {
        try {
            AuthRequestContext.setRequestName("ADD_RESOURCE_TYPE");
            String url = String.format(IamUri.ADD_RESOURCE_TYPE, iamConfiguration.getSystemId());
            String responseStr = apigwHttpClientService.doHttpPost(url, resourceTypeDTO);
            if (StringUtils.isNotBlank(responseStr)) {
                ResponseDTO<Object> result = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                ResponseUtil.checkResponse(result);
                log.info("create resource success. {}", resourceTypeDTO);
                return true;
            } else {
                log.info("create Resource is empty");
            }
        } catch (IamException iamException) {
            log.warn("create resource fail. {}|{}", resourceTypeDTO, iamException);
        } catch (Exception e) {
            log.warn("create resource fail. {}|{}", resourceTypeDTO, e);
        }
        return false;
    }

    @Override
    public boolean updateResource(ResourceTypeDTO resourceTypeDTO, String resourceId) {
        try {
            AuthRequestContext.setRequestName("UPDATE_OR_DELETE_RESOURCE_TYPE");
            String url = String.format(IamUri.UPDATE_OR_DELETE_RESOURCE_TYPE, iamConfiguration.getSystemId(), resourceId);
            String responseStr = apigwHttpClientService.doHttpPut(url, resourceTypeDTO);
            if (StringUtils.isNotBlank(responseStr)) {
                ResponseDTO<Object> result = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                ResponseUtil.checkResponse(result);
                log.info("update resource success. {}", resourceTypeDTO);
                return true;
            } else {
                log.info("update Resource is empty");
            }
        } catch (IamException iamException) {
            log.warn("update resource fail. {}|{}", resourceTypeDTO, iamException);
        } catch (Exception e) {
            log.warn("update resource fail. {}|{}", resourceTypeDTO, e);
        }
        return false;
    }

    @Override
    public boolean createResourceInstanceSelector(List<SelectionDTO> instanceSelector) {
        try {
            AuthRequestContext.setRequestName("ADD_INSTANCE_SELECTIONS");
            String url = String.format(IamUri.ADD_INSTANCE_SELECTIONS, iamConfiguration.getSystemId());
            String responseStr = apigwHttpClientService.doHttpPost(url, instanceSelector);
            if (StringUtils.isNotBlank(responseStr)) {
                ResponseDTO<Object> result = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                ResponseUtil.checkResponse(result);
                log.info("create resource instance success. {}", instanceSelector);
                return true;
            } else {
                log.info("create resource instance is empty");
            }
        } catch (IamException iamException) {
            log.warn("create resource instance fail. {}|{}", instanceSelector, iamException);
        } catch (Exception e) {
            log.warn("create resource instance fail. {}|{}", instanceSelector, e);
        }
        return false;
    }

    @Override
    public boolean updateResourceInstanceSelector(String selectionId, SelectionDTO instanceSelector) {
        try {
            AuthRequestContext.setRequestName("UPDATE_INSTANCE_SELECTIONS");
            String url = String.format(IamUri.UPDATE_INSTANCE_SELECTIONS, iamConfiguration.getSystemId(), selectionId);
            String responseStr = apigwHttpClientService.doHttpPut(url, instanceSelector);
            if (StringUtils.isNotBlank(responseStr)) {
                ResponseDTO<Object> result = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                ResponseUtil.checkResponse(result);
                log.info("update resource InstanceSelector success. {}", instanceSelector);
                return true;
            } else {
                log.info("update resource InstanceSelector is empty");
            }
        } catch (IamException iamException) {
            log.warn("update resource InstanceSelector fail. {}|{}", instanceSelector, iamException);
        } catch (Exception e) {
            log.warn("update resource InstanceSelector fail. {}|{}", instanceSelector, e);
        }
        return false;
    }

    @Override
    public boolean resourceCheck(String resourceType, String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.resourceCheck(resourceType);
    }

    @Override
    public boolean createResource(List<ResourceTypeDTO> resourceTypeDTO, String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.createResource(resourceTypeDTO);
    }

    @Override
    public boolean updateResource(ResourceTypeDTO resourceTypeDTO, String resourceId, String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.updateResource(resourceTypeDTO, resourceId);
    }

    @Override
    public List<SelectionDTO> getSystemInstanceSelector(String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.getSystemInstanceSelector();
    }

    @Override
    public boolean createResourceInstanceSelector(List<SelectionDTO> instanceSelector, String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.createResourceInstanceSelector(instanceSelector);
    }

    @Override
    public boolean updateResourceInstanceSelector(String selectionId, SelectionDTO instanceSelector, String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.updateResourceInstanceSelector(selectionId, instanceSelector);
    }
}
