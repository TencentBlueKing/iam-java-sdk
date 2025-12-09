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

package com.tencent.bk.sdk.iam.dto.manager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tencent.bk.sdk.iam.dto.manager.AuthorizationScopes;
import com.tencent.bk.sdk.iam.dto.manager.ManagerScopes;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UpdateManagerDTO {
    /**
     * 分级管理员名称
     */
    String name;
    /**
     * 描述
     */
    String description;
    /**
     * 用户ID
     */
    List<String> members;

    @JsonProperty("authorization_scopes")
    List<AuthorizationScopes> authorizationScopes;

    @JsonProperty("subject_scopes")
    List<ManagerScopes> subjectScopes;
    /**
     * 是否创建同步权限用户组, 默认false
     */
    @JsonProperty("sync_perm")
    Boolean syncPerm;

    /**
     * 	如果sync_perm为true, 可以自定义用户组名称, 默认为空
     */
    @JsonProperty("group_name")
    private String groupName;
    /**
     * 是否禁用--禁用后，不再发起续期通知
     */
    Boolean enabled;
}
