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

package com.tencent.bk.sdk.iam.service;

import com.tencent.bk.sdk.iam.dto.V2QueryPolicyDTO;

import java.util.List;

import com.tencent.bk.sdk.iam.dto.ExpressionWithResourceDTO;
import com.tencent.bk.sdk.iam.dto.UserGroupDTO;
import com.tencent.bk.sdk.iam.dto.action.ActionDTO;
import com.tencent.bk.sdk.iam.dto.action.ActionPolicyDTO;
import com.tencent.bk.sdk.iam.dto.expression.ExpressionDTO;
import com.tencent.bk.sdk.iam.dto.resource.ResourceDTO;

import java.util.Map;

public interface PolicyService {

    /**
     * 根据操作拉取权限表达式
     *
     * @param username     用户名
     * @param action       操作
     * @param resourceList 相关资源列表
     * @return 权限表达式
     */
    ExpressionDTO getPolicyByAction(String username, ActionDTO action,
                                    List<ResourceDTO> resourceList);

    /**
     * 根据操作列表批量拉取权限表达式
     *
     * @param username     用户名
     * @param actionList   操作列表
     * @param resourceList 相关资源列表
     * @return 操作关联的权限表达式列表
     */
    List<ActionPolicyDTO> batchGetPolicyByActionList(String username, List<ActionDTO> actionList,
                                                     List<ResourceDTO> resourceList);

    /**
     * 批量第三方依赖鉴权策略查询
     */
    ExpressionWithResourceDTO batchGetPolicyAndAttribute(String username, ActionDTO actionDTO, ResourceDTO selfResource, List<ResourceDTO> dependencyResource);

    /**
     * 获取用户加入的用户组
     */
    List<UserGroupDTO> getUserGroup(String username, Boolean inherit);

    /**
     * 直接鉴权
     */
    public Boolean verifyPermissions(V2QueryPolicyDTO queryPolicyDTO);

    /**
     *
     */
    public Map<String, Boolean> batchVerifyPermissions(String username, List<ActionDTO> actionList,
                                                       List<ResourceDTO> resourceList);

    /**
     * 根据操作拉取权限表达式
     *
     * @param username     用户名
     * @param action       操作
     * @param resourceList 相关资源列表
     * @param tenantId     租户ID
     * @return 权限表达式
     */
    ExpressionDTO getPolicyByAction(String username, ActionDTO action,
                                    List<ResourceDTO> resourceList, String tenantId);

    /**
     * 根据操作列表批量拉取权限表达式
     *
     * @param username     用户名
     * @param actionList   操作列表
     * @param resourceList 相关资源列表
     * @param tenantId     租户ID
     * @return 操作关联的权限表达式列表
     */
    List<ActionPolicyDTO> batchGetPolicyByActionList(String username, List<ActionDTO> actionList,
                                                     List<ResourceDTO> resourceList, String tenantId);

    /**
     * 批量第三方依赖鉴权策略查询
     */
    ExpressionWithResourceDTO batchGetPolicyAndAttribute(String username, ActionDTO actionDTO, ResourceDTO selfResource, List<ResourceDTO> dependencyResource, String tenantId);

    /**
     * 获取用户加入的用户组
     */
    List<UserGroupDTO> getUserGroup(String username, Boolean inherit, String tenantId);

    /**
     * 直接鉴权
     */
    public Boolean verifyPermissions(V2QueryPolicyDTO queryPolicyDTO, String tenantId);

    public Map<String, Boolean> batchVerifyPermissions(String username, List<ActionDTO> actionList,
                                                       List<ResourceDTO> resourceList, String tenantId);
}
