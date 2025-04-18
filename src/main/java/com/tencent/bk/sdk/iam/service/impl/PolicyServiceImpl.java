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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.tencent.bk.sdk.iam.config.IamConfiguration;
import com.tencent.bk.sdk.iam.constants.IamUri;
import com.tencent.bk.sdk.iam.dto.ExpressionWithResourceDTO;
import com.tencent.bk.sdk.iam.dto.InstanceDTO;
import com.tencent.bk.sdk.iam.dto.SubjectDTO;
import com.tencent.bk.sdk.iam.dto.UserGroupDTO;
import com.tencent.bk.sdk.iam.dto.V2QueryPolicyDTO;
import com.tencent.bk.sdk.iam.dto.action.ActionDTO;
import com.tencent.bk.sdk.iam.dto.action.ActionPolicyDTO;
import com.tencent.bk.sdk.iam.dto.expression.ExpressionDTO;
import com.tencent.bk.sdk.iam.dto.request.BatchQueryPolicyRequestDTO;
import com.tencent.bk.sdk.iam.dto.request.QueryPolicyRequestDTO;
import com.tencent.bk.sdk.iam.dto.request.QueryPolicyWithDependencyResourceRequestDTO;
import com.tencent.bk.sdk.iam.dto.resource.DependencyResourceInfoDTO;
import com.tencent.bk.sdk.iam.dto.resource.ResourceDTO;
import com.tencent.bk.sdk.iam.dto.response.QueryPolicyWithDependencyResourceResponseDTO;
import com.tencent.bk.sdk.iam.dto.response.ResponseDTO;
import com.tencent.bk.sdk.iam.exception.IamException;
import com.tencent.bk.sdk.iam.service.HttpClientService;
import com.tencent.bk.sdk.iam.service.PolicyService;
import com.tencent.bk.sdk.iam.util.AuthCacheUtil;
import com.tencent.bk.sdk.iam.util.AuthRequestContext;
import com.tencent.bk.sdk.iam.util.JsonUtil;
import com.tencent.bk.sdk.iam.util.ResponseUtil;
import com.tencent.bk.sdk.iam.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class PolicyServiceImpl implements PolicyService {

    private final IamConfiguration iamConfiguration;
    private final HttpClientService httpClientService;

    public PolicyServiceImpl(IamConfiguration iamConfiguration, HttpClientService httpClientService) {
        this.iamConfiguration = iamConfiguration;
        this.httpClientService = httpClientService;
    }

    @Override
    public ExpressionDTO getPolicyByAction(String username, ActionDTO action, List<ResourceDTO> resourceList) {
        if (resourceList == null) {
            resourceList = Collections.emptyList();
        }
        QueryPolicyRequestDTO queryPolicyRequest =
                QueryPolicyRequestDTO.builder().subject(SubjectDTO.builder().id(username).type("user").build())
                        .action(action).resourceList(resourceList).system(iamConfiguration.getSystemId()).build();
        if (log.isDebugEnabled()) {
            log.debug("Get policy by action request|{}|{}|{}|{}", username, action, resourceList, queryPolicyRequest);
        }
        String policyResponse = httpClientService.doHttpPost(getPolicyByActionUrl(), queryPolicyRequest);
        if (StringUtils.isNotBlank(policyResponse)) {
            if (log.isDebugEnabled()) {
                log.debug("Get policy by action response|{}", policyResponse);
            }
            ResponseDTO<ExpressionDTO> responseInfo;
            try {
                responseInfo = JsonUtil.fromJson(policyResponse,
                        new TypeReference<ResponseDTO<ExpressionDTO>>() {
                        });
            } catch (IOException e) {
                log.error("Error while parse policy response!|{}|{}|{}|{}", username, action, resourceList,
                        policyResponse, e);
                return null;
            }
            if (responseInfo != null) {
                ResponseUtil.checkResponse(responseInfo);
                return responseInfo.getData();
            }
        } else {
            log.warn("Get policy by action got empty response!");
        }
        return null;
    }

    @Override
    public List<ActionPolicyDTO> batchGetPolicyByActionList(String username, List<ActionDTO> actionList,
                                                            List<ResourceDTO> resourceList) {
        BatchQueryPolicyRequestDTO batchQueryPolicyRequest = BatchQueryPolicyRequestDTO.builder().subject(SubjectDTO.builder().id(username).type("user").build())
                .actionList(actionList).resourceList(resourceList).system(iamConfiguration.getSystemId()).build();
        if (log.isDebugEnabled()) {
            log.debug("Batch get policy by action list request|{}|{}|{}|{}", username, actionList, resourceList, batchQueryPolicyRequest);
        }
        String actionPolicyResponse = httpClientService.doHttpPost(getBatchGetPolicyByActionListUrl(), batchQueryPolicyRequest);
        if (StringUtils.isNotBlank(actionPolicyResponse)) {
            if (log.isDebugEnabled()) {
                log.debug("Batch get policy by action list response|{}", actionPolicyResponse);
            }
            ResponseDTO<List<ActionPolicyDTO>> responseInfo;
            try {
                responseInfo = JsonUtil.fromJson(actionPolicyResponse,
                        new TypeReference<ResponseDTO<List<ActionPolicyDTO>>>() {
                        });
            } catch (IOException e) {
                log.error("Error while parse action policy response!|{}|{}|{}|{}", username, actionList, resourceList,
                        actionPolicyResponse, e);
                return null;
            }
            if (responseInfo != null) {
                ResponseUtil.checkResponse(responseInfo);
                return responseInfo.getData();
            }
        } else {
            log.warn("Batch get policy by action list got empty response!");
        }
        return null;
    }

    @Override
    public ExpressionWithResourceDTO batchGetPolicyAndAttribute(String username, ActionDTO action, ResourceDTO selfResource, List<ResourceDTO> dependencyResource) {
        QueryPolicyWithDependencyResourceRequestDTO queryPolicyWithDependencyResourceRequest = new QueryPolicyWithDependencyResourceRequestDTO();
        queryPolicyWithDependencyResourceRequest.setSubject(SubjectDTO.builder().id(username).type("user").build());
        queryPolicyWithDependencyResourceRequest.setAction(action);
        if (selfResource != null) {
            queryPolicyWithDependencyResourceRequest.setResourceList(Collections.singletonList(selfResource));
        } else {
            queryPolicyWithDependencyResourceRequest.setResourceList(Collections.emptyList());
        }
        queryPolicyWithDependencyResourceRequest.setSystem(iamConfiguration.getSystemId());
        queryPolicyWithDependencyResourceRequest.setDependencyResource(dependencyResource);
        if (log.isDebugEnabled()) {
            log.debug("Batch get policy and attr request|{}|{}|{}|{}|{}", username, action, selfResource,
                    dependencyResource, queryPolicyWithDependencyResourceRequest);
        }

        String policyAndResourceResponse = httpClientService.doHttpPost(IamUri.QUERY_POLICY_WITH_RESOURCE,
                queryPolicyWithDependencyResourceRequest);
        if (StringUtils.isNotBlank(policyAndResourceResponse)) {
            if (log.isDebugEnabled()) {
                log.debug("Batch get policy and attr response|{}", policyAndResourceResponse);
            }
            ResponseDTO<QueryPolicyWithDependencyResourceResponseDTO> responseInfo;
            try {
                responseInfo = JsonUtil.fromJson(policyAndResourceResponse, new TypeReference<ResponseDTO<QueryPolicyWithDependencyResourceResponseDTO>>() {
                });
            } catch (IOException e) {
                log.error("Error while parse action policy response!|{}|{}|{}|{}|{}", username, action, selfResource,
                        dependencyResource, policyAndResourceResponse, e);
                return null;
            }
            if (responseInfo != null) {
                ResponseUtil.checkResponse(responseInfo);
                QueryPolicyWithDependencyResourceResponseDTO policyWithDependencyResource = responseInfo.getData();

                ExpressionWithResourceDTO expressionWithResource = new ExpressionWithResourceDTO();
                expressionWithResource.setExpression(policyWithDependencyResource.getExpression());

                List<DependencyResourceInfoDTO> dependencyResourceList = policyWithDependencyResource.getDependencyResourceList();

                Map<String, Map<String, List<InstanceDTO>>> instanceMap =
                        new ConcurrentHashMap<>(dependencyResourceList.size());

                for (DependencyResourceInfoDTO dependencyResourceInfo : dependencyResourceList) {
                    if (StringUtils.isBlank(dependencyResourceInfo.getSystem())) {
                        log.error("Dependency resource should has system!");
                        continue;
                    }
                    if (dependencyResourceInfo.getInstanceList() == null) {
                        log.error("Null dependency resource instance list!");
                        continue;
                    }
                    Map<String, List<InstanceDTO>> typeResourceMap =
                            instanceMap.computeIfAbsent(dependencyResourceInfo.getSystem(),
                                    k -> new ConcurrentHashMap<>(dependencyResourceInfo.getInstanceList().size()));
                    typeResourceMap.put(dependencyResourceInfo.getType(),
                            dependencyResourceInfo.getInstanceList());
                }
                expressionWithResource.setInstanceMap(instanceMap);

                return expressionWithResource;
            }
        }
        return null;
    }

    @Override
    public List<UserGroupDTO> getUserGroup(String username, Boolean inherit) {
        String responseStr = httpClientService.doHttpGet(String.format(IamUri.USER_GROUP, username, inherit.toString()));

        if (StringUtils.isNotBlank(responseStr)) {
            log.debug("get manager role response|{}", responseStr);
            ResponseDTO<List<UserGroupDTO>> responseInfo = null;
            try {
                responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<List<UserGroupDTO>>>() {});
            } catch (IOException e) {
                log.error("get manager role response failed", e);
            }
            if (responseInfo != null) {
                ResponseUtil.checkResponse(responseInfo);
                return responseInfo.getData();
            }
        } else {
            log.warn("get user group got empty response!");
            return null;
        }

        return null;
    }

    @Override
    public Boolean verifyPermissions(V2QueryPolicyDTO queryPolicyDTO) {
        try {
            String cacheKey = getPermissionCacheKey(queryPolicyDTO);
            return AuthCacheUtil.cachePermission(cacheKey, () -> verifyPermissionsFromIam(queryPolicyDTO));
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("verify permissions response failed", e);
            throw new RuntimeException(e);
        }
    }

    public Boolean verifyPermissionsFromIam(V2QueryPolicyDTO queryPolicyDTO) {
        try {
            String responseStr = httpClientService.doHttpPost(getVerifyPermissionsUrl(), queryPolicyDTO);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("verify permissions response|{}", responseStr);
                ResponseDTO<Map<String, Boolean>> responseInfo =
                        JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Map<String, Boolean>>>() {});
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData().get("allowed");
                }
            } else {
                log.warn("verify permissions got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Map<String, Boolean> batchVerifyPermissions(String username, List<ActionDTO> actionList, List<ResourceDTO> resourceList) {
        BatchQueryPolicyRequestDTO batchQueryPolicyRequest = BatchQueryPolicyRequestDTO.builder().subject(SubjectDTO.builder().id(username).type("user").build())
                .actionList(actionList).resourceList(resourceList).system(iamConfiguration.getSystemId()).build();
        if (log.isDebugEnabled()) {
            log.debug("Batch verify policy by action list request|{}|{}|{}|{}", username, actionList, resourceList, batchQueryPolicyRequest);
        }
        try {
            String actionPolicyResponse = httpClientService.doHttpPost(getBatchVerifyPermissionsUrl(), batchQueryPolicyRequest);
            if (StringUtils.isNotBlank(actionPolicyResponse)) {
                if (log.isDebugEnabled()) {
                    log.debug("Batch verify policy by action list response|{}", actionPolicyResponse);
                }
                ResponseDTO<Map<String, Boolean>> responseInfo;
                try {
                    responseInfo = JsonUtil.fromJson(actionPolicyResponse,
                            new TypeReference<ResponseDTO<Map<String, Boolean>>>() {
                            });
                } catch (IOException e) {
                    log.error("Error while parse action policy response!|{}|{}|{}|{}", username, actionList, resourceList,
                            actionPolicyResponse, e);
                    return null;
                }
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData();
                }
            } else {
                log.warn("Batch verify policy by action list got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("batch verify permissions response failed", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ExpressionDTO getPolicyByAction(String username, ActionDTO action, List<ResourceDTO> resourceList, String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.getPolicyByAction(username, action, resourceList);
    }

    @Override
    public List<ActionPolicyDTO> batchGetPolicyByActionList(String username, List<ActionDTO> actionList, List<ResourceDTO> resourceList, String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.batchGetPolicyByActionList(username, actionList, resourceList);
    }

    @Override
    public ExpressionWithResourceDTO batchGetPolicyAndAttribute(String username, ActionDTO actionDTO, ResourceDTO selfResource, List<ResourceDTO> dependencyResource, String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.batchGetPolicyAndAttribute(username, actionDTO, selfResource, dependencyResource);
    }

    @Override
    public List<UserGroupDTO> getUserGroup(String username, Boolean inherit, String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.getUserGroup(username, inherit);
    }

    @Override
    public Boolean verifyPermissions(V2QueryPolicyDTO queryPolicyDTO, String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.verifyPermissions(queryPolicyDTO);
    }

    @Override
    public Map<String, Boolean> batchVerifyPermissions(String username, List<ActionDTO> actionList, List<ResourceDTO> resourceList, String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return this.batchVerifyPermissions(username, actionList, resourceList);
    }

    public String getPolicyByActionUrl() {
        AuthRequestContext.setRequestName("QUERY_POLICY");
        return IamUri.QUERY_POLICY;
    }

    public String getBatchGetPolicyByActionListUrl() {
        AuthRequestContext.setRequestName("BATCH_QUERY_POLICY");
        return IamUri.BATCH_QUERY_POLICY;
    }

    public String getVerifyPermissionsUrl() {
        AuthRequestContext.setRequestName("AUTH_POLICY");
        return IamUri.AUTH_POLICY;
    }

    public String getBatchVerifyPermissionsUrl() {
        AuthRequestContext.setRequestName("POLICY_AUTH_BY_ACTIONS");
        return IamUri.POLICY_AUTH_BY_ACTIONS;
    }

    private String getPermissionCacheKey(V2QueryPolicyDTO queryPolicyDTO) throws JsonProcessingException {
        return DigestUtils.md5Hex(JsonUtil.toJson(queryPolicyDTO));
    }
}
