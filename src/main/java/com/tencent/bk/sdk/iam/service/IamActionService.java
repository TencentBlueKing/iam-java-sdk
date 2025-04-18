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

import com.tencent.bk.sdk.iam.dto.action.ActionDTO;
import com.tencent.bk.sdk.iam.dto.action.ActionGroupDTO;
import com.tencent.bk.sdk.iam.dto.action.ActionUpdateDTO;
import com.tencent.bk.sdk.iam.dto.resource.ResourceCreatorActionsDTO;

import java.util.List;

public interface IamActionService {
    // 获取系统注册的所有的action
    ResourceCreatorActionsDTO getResourceCreatorAction();

    ActionDTO getAction(String actionId);

    Boolean createAction(List<ActionDTO> actionDTO);

    Boolean updateAction(String actionId, ActionUpdateDTO actionDTO);

    /**
     * @param actionId
     * @param checkExistence 默认会检查 id 存在, 不存在将导致删除失败, 设置 false 不检查 id 是否存在, 直接走删除
     * @return
     */
    Boolean deleteAction(String actionId, Boolean checkExistence);

    // 操作组
    ActionGroupDTO getActionGroup(String groupName);

    Boolean createActionGroup(List<ActionGroupDTO> actionGroupDTO);

    Boolean updateActionGroup(List<ActionGroupDTO> actionGroupDTO);


    // 新建关联操作
    Boolean createResourceCreatorAction(ResourceCreatorActionsDTO resourceCreatorActionsDTO);

    // 修改关联操作
    Boolean updateResourceCreatorAction(ResourceCreatorActionsDTO resourceCreatorActionsDTO);

    /**
     * 获取系统注册的所有的action
     *
     * @param tenantId 租户ID
     * @return 资源创建者操作DTO
     */
    ResourceCreatorActionsDTO getResourceCreatorAction(String tenantId);

    /**
     * 获取操作
     *
     * @param actionId 操作ID
     * @param tenantId 租户ID
     * @return 操作DTO
     */
    ActionDTO getAction(String actionId, String tenantId);

    /**
     * 创建操作
     *
     * @param actionDTO 操作DTO列表
     * @param tenantId  租户ID
     * @return 是否创建成功
     */
    Boolean createAction(List<ActionDTO> actionDTO, String tenantId);

    /**
     * 更新操作
     *
     * @param actionId  操作ID
     * @param actionDTO 操作更新DTO
     * @param tenantId  租户ID
     * @return 是否更新成功
     */
    Boolean updateAction(String actionId, ActionUpdateDTO actionDTO, String tenantId);

    /**
     * 删除操作
     *
     * @param actionId       操作ID
     * @param checkExistence 是否检查存在性
     * @param tenantId       租户ID
     * @return 是否删除成功
     */
    Boolean deleteAction(String actionId, Boolean checkExistence, String tenantId);

    /**
     * 获取操作组
     *
     * @param groupName 组名
     * @param tenantId  租户ID
     * @return 操作组DTO
     */
    ActionGroupDTO getActionGroup(String groupName, String tenantId);

    /**
     * 创建操作组
     *
     * @param actionGroupDTO 操作组DTO列表
     * @param tenantId       租户ID
     * @return 是否创建成功
     */
    Boolean createActionGroup(List<ActionGroupDTO> actionGroupDTO, String tenantId);

    /**
     * 更新操作组
     *
     * @param actionGroupDTO 操作组DTO列表
     * @param tenantId       租户ID
     * @return 是否更新成功
     */
    Boolean updateActionGroup(List<ActionGroupDTO> actionGroupDTO, String tenantId);

    /**
     * 新建关联操作
     *
     * @param resourceCreatorActionsDTO 资源创建者操作DTO
     * @param tenantId                  租户ID
     * @return 是否创建成功
     */
    Boolean createResourceCreatorAction(ResourceCreatorActionsDTO resourceCreatorActionsDTO, String tenantId);

    /**
     * 修改关联操作
     *
     * @param resourceCreatorActionsDTO 资源创建者操作DTO
     * @param tenantId                  租户ID
     * @return 是否更新成功
     */
    Boolean updateResourceCreatorAction(ResourceCreatorActionsDTO resourceCreatorActionsDTO, String tenantId);
}
