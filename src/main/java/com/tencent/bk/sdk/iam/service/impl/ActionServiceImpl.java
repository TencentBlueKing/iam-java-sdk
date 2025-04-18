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
import com.tencent.bk.sdk.iam.dto.action.ActionDTO;
import com.tencent.bk.sdk.iam.dto.action.ActionGroupDTO;
import com.tencent.bk.sdk.iam.dto.action.ActionUpdateDTO;
import com.tencent.bk.sdk.iam.dto.resource.ResourceCreatorActionsDTO;
import com.tencent.bk.sdk.iam.dto.response.ResponseDTO;
import com.tencent.bk.sdk.iam.dto.system.SystemFieldDTO;
import com.tencent.bk.sdk.iam.service.IamActionService;
import com.tencent.bk.sdk.iam.service.SystemService;
import com.tencent.bk.sdk.iam.util.AuthRequestContext;
import com.tencent.bk.sdk.iam.util.JsonUtil;
import com.tencent.bk.sdk.iam.util.ResponseUtil;
import com.tencent.bk.sdk.iam.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ActionServiceImpl implements IamActionService {

    private IamConfiguration iamConfiguration;
    private ApigwHttpClientServiceImpl apigwHttpClientService;
    private SystemService systemService;

    public ActionServiceImpl(
        IamConfiguration iamConfiguration,
        ApigwHttpClientServiceImpl apigwHttpClientService,
        SystemService systemService
    ) {
        this.iamConfiguration = iamConfiguration;
        this.apigwHttpClientService = apigwHttpClientService;
        this.systemService = systemService;
    }

    @Override
    public ActionDTO getAction(String actionId) {
        SystemFieldDTO systemFieldDTO = systemService.getSystemFieldsInfo(iamConfiguration.getSystemId());
        List<ActionDTO> actions = systemFieldDTO.getActions();
        if (actions.isEmpty()) {
            log.warn("action get empty");
            return null;
        } else {
            for (ActionDTO action: actions) {
                if (action.getId().equals(actionId)) {
                    return action;
                }
            }
        }
        return null;
    }

    @Override
    public Boolean createAction(List<ActionDTO> actionDTO) {
        AuthRequestContext.setRequestName("ADD_ACTION");
        String url = String.format(IamUri.ADD_ACTION, iamConfiguration.getSystemId());
        try {
            String result = apigwHttpClientService.doHttpPost(url, actionDTO);
            if (!result.isEmpty()) {
                ResponseDTO<Object> response = JsonUtil.fromJson(result, new TypeReference<ResponseDTO<Object>>() {
                });
                ResponseUtil.checkResponse(response);
                return true;
            } else {
                log.warn("create action result is empty {}|{}", actionDTO, result);
            }
        } catch (Exception e) {
            log.warn("create action fail {}", actionDTO, e);
        }
        return false;
    }

    @Override
    public Boolean updateAction(String actionId, ActionUpdateDTO actionUpdateDTO) {
        AuthRequestContext.setRequestName("UPDATE_ACTION");
        String url = String.format(IamUri.UPDATE_ACTION, iamConfiguration.getSystemId(), actionId);
        try {
            String result = apigwHttpClientService.doHttpPut(url, actionUpdateDTO);
            if (!result.isEmpty()) {
                ResponseDTO<Object> response = JsonUtil.fromJson(result, new TypeReference<ResponseDTO<Object>>() {
                });
                ResponseUtil.checkResponse(response);
                return true;
            } else {
                log.warn("update action result is empty {}|{}", actionUpdateDTO, result);
            }
        } catch (Exception e) {
            log.warn("update action fail {}", actionUpdateDTO, e);
        }
        return false;
    }

    @Override
    public Boolean deleteAction(String actionId, Boolean checkExistence) {
        AuthRequestContext.setRequestName("DELETE_ACTION");
        String url;
        if (checkExistence == null) {
            url = String.format(IamUri.DELETE_ACTION, iamConfiguration.getSystemId(), actionId, false);
        } else {
            url = String.format(IamUri.DELETE_ACTION, iamConfiguration.getSystemId(), actionId, checkExistence);
        }
        try {
            String result = apigwHttpClientService.doHttpDelete(url);
            if (!result.isEmpty()) {
                ResponseDTO<Object> response = JsonUtil.fromJson(result, new TypeReference<ResponseDTO<Object>>() {
                });
                ResponseUtil.checkResponse(response);
                return true;
            } else {
                log.warn("delete action result is empty {}|{}", actionId, result);
            }
        } catch (Exception e) {
            log.warn("delete action fail {}", actionId, e);
        }
        return false;
    }

    @Override
    public ActionGroupDTO getActionGroup(String groupName) {
        SystemFieldDTO systemFieldDTO = systemService.getSystemFieldsInfo(iamConfiguration.getSystemId());
        List<ActionGroupDTO> actionGroups = systemFieldDTO.getActionGroup();
        if (actionGroups.isEmpty()) {
            return null;
        } else {
            for (ActionGroupDTO actionGroup: actionGroups) {
                if (actionGroup.getName().equals(groupName)) {
                    return actionGroup;
                }
            }
        }
        return null;
    }

    @Override
    public Boolean createActionGroup(List<ActionGroupDTO> actionGroupDTO) {
        AuthRequestContext.setRequestName("ADD_ACTION_GROUP");
        String url = String.format(IamUri.ADD_ACTION_GROUP, iamConfiguration.getSystemId());
        try {
            String result = apigwHttpClientService.doHttpPost(url, actionGroupDTO);
            if (!result.isEmpty()) {
                ResponseDTO<Object> response = JsonUtil.fromJson(result, new TypeReference<ResponseDTO<Object>>() {
                });
                ResponseUtil.checkResponse(response);
                return true;
            } else {
                log.warn("create action group result is empty {}|{}", actionGroupDTO, result);
            }
        } catch (Exception e) {
            log.warn("create action group fail {}", actionGroupDTO, e);
        }
        return false;
    }

    @Override
    public Boolean updateActionGroup(List<ActionGroupDTO> actionGroupDTO) {
        AuthRequestContext.setRequestName("UPDATE_ACTION_GROUP");
        String url = String.format(IamUri.UPDATE_ACTION_GROUP, iamConfiguration.getSystemId());
        try {
            String result = apigwHttpClientService.doHttpPut(url, actionGroupDTO);
            if (!result.isEmpty()) {
                ResponseDTO<Object> response = JsonUtil.fromJson(result, new TypeReference<ResponseDTO<Object>>() {
                });
                ResponseUtil.checkResponse(response);
                return true;
            } else {
                log.warn("update action group result is empty {}|{}", actionGroupDTO, result);
            }
        } catch (Exception e) {
            log.warn("update action group fail {}", actionGroupDTO, e);
        }
        return false;
    }

    @Override
    public ResourceCreatorActionsDTO getResourceCreatorAction() {
        SystemFieldDTO systemFieldDTO = systemService.getSystemFieldsInfo(iamConfiguration.getSystemId());
        ResourceCreatorActionsDTO actionGroups = systemFieldDTO.getResourceCreatorActions();
        return actionGroups;
    }

    @Override
    public Boolean createResourceCreatorAction(ResourceCreatorActionsDTO resourceCreatorActionsDTO) {
        AuthRequestContext.setRequestName("ADD_RESOURCE_CREATOR_ACTIONS");
        String url = String.format(IamUri.ADD_RESOURCE_CREATOR_ACTIONS, iamConfiguration.getSystemId());
        try {
            String result = apigwHttpClientService.doHttpPost(url, resourceCreatorActionsDTO);
            if (!result.isEmpty()) {
                ResponseDTO<Object> response = JsonUtil.fromJson(result, new TypeReference<ResponseDTO<Object>>() {
                });
                ResponseUtil.checkResponse(response);
                return true;
            } else {
                log.warn("create resourceCreatorActions result is empty {}|{}", resourceCreatorActionsDTO, result);
            }
        } catch (Exception e) {
            log.warn("create resourceCreatorActions fail {}", resourceCreatorActionsDTO, e);
        }
        return false;
    }

    @Override
    public Boolean updateResourceCreatorAction(ResourceCreatorActionsDTO resourceCreatorActionsDTO) {
        AuthRequestContext.setRequestName("UPDATE_RESOURCE_CREATOR_ACTIONS");
        String url = String.format(IamUri.UPDATE_RESOURCE_CREATOR_ACTIONS, iamConfiguration.getSystemId());
        try {
            String result = apigwHttpClientService.doHttpPut(url, resourceCreatorActionsDTO);
            if (!result.isEmpty()) {
                ResponseDTO<Object> response = JsonUtil.fromJson(result, new TypeReference<ResponseDTO<Object>>() {
                });
                ResponseUtil.checkResponse(response);
                return true;
            } else {
                log.warn("update resourceCreatorActions result is empty {}|{}", resourceCreatorActionsDTO, result);
            }
        } catch (Exception e) {
            log.warn("update resourceCreatorActions fail {}", resourceCreatorActionsDTO, e);
        }
        return false;
    }

    @Override
    public ResourceCreatorActionsDTO getResourceCreatorAction(String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.getResourceCreatorAction();
    }

    @Override
    public ActionDTO getAction(String actionId, String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.getAction(actionId);
    }

    @Override
    public Boolean createAction(List<ActionDTO> actionDTO, String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.createAction(actionDTO);
    }

    @Override
    public Boolean updateAction(String actionId, ActionUpdateDTO actionDTO, String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.updateAction(actionId, actionDTO);
    }

    @Override
    public Boolean deleteAction(String actionId, Boolean checkExistence, String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.deleteAction(actionId, checkExistence);
    }

    @Override
    public ActionGroupDTO getActionGroup(String groupName, String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.getActionGroup(groupName);
    }

    @Override
    public Boolean createActionGroup(List<ActionGroupDTO> actionGroupDTO, String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.createActionGroup(actionGroupDTO);
    }

    @Override
    public Boolean updateActionGroup(List<ActionGroupDTO> actionGroupDTO, String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.updateActionGroup(actionGroupDTO);
    }

    @Override
    public Boolean createResourceCreatorAction(ResourceCreatorActionsDTO resourceCreatorActionsDTO, String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.createResourceCreatorAction(resourceCreatorActionsDTO);
    }

    @Override
    public Boolean updateResourceCreatorAction(ResourceCreatorActionsDTO resourceCreatorActionsDTO, String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.updateResourceCreatorAction(resourceCreatorActionsDTO);
    }
}
