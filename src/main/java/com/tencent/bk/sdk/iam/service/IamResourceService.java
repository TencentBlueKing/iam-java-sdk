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

import com.tencent.bk.sdk.iam.dto.SelectionDTO;
import com.tencent.bk.sdk.iam.dto.resource.ResourceTypeDTO;

import java.util.List;

public interface IamResourceService {
    /**
     * 检查资源是否存在
     */
    boolean resourceCheck(String resourceType);

    /**
     * 创建资源
     */
    boolean createResource(List<ResourceTypeDTO> resourceTypeDTO);

    /**
     * 修改资源
     */
    boolean updateResource(ResourceTypeDTO resourceTypeDTO, String resourceId);

    /**
     * 获取系统资源实例视图
     */
    List<SelectionDTO> getSystemInstanceSelector();

    /**
     * 创建系统资源实例视图
     */
    boolean createResourceInstanceSelector(List<SelectionDTO> instanceSelector);

    /**
     * 修改系统资源实例视图
     */
    boolean updateResourceInstanceSelector(String selectionId, SelectionDTO instanceSelector);

    /**
     * 检查资源是否存在(带租户)
     */
    boolean resourceCheck(String resourceType, String tenantId);

    /**
     * 创建资源(带租户)
     */
    boolean createResource(List<ResourceTypeDTO> resourceTypeDTO, String tenantId);

    /**
     * 修改资源(带租户)
     */
    boolean updateResource(ResourceTypeDTO resourceTypeDTO, String resourceId, String tenantId);

    /**
     * 获取系统资源实例视图(带租户)
     */
    List<SelectionDTO> getSystemInstanceSelector(String tenantId);

    /**
     * 创建系统资源实例视图(带租户)
     */
    boolean createResourceInstanceSelector(List<SelectionDTO> instanceSelector, String tenantId);

    /**
     * 修改系统资源实例视图(带租户)
     */
    boolean updateResourceInstanceSelector(String selectionId, SelectionDTO instanceSelector, String tenantId);


}
