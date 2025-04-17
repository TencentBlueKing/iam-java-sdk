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
import com.tencent.bk.sdk.iam.service.decorator.IamService;

import java.util.List;

public interface IamResourceService extends IamService {
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

}
