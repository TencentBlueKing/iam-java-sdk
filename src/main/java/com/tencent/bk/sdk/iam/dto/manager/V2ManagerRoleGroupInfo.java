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

package com.tencent.bk.sdk.iam.dto.manager;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class V2ManagerRoleGroupInfo {
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 是否是只读用户组
     */
    private Boolean readonly;
    /**
     * 用户组成员user数量
     */
    @JsonProperty("user_count")
    private Integer userCount;
    /**
     * 用户组成员department数量
     */
    @JsonProperty("department_count")
    private Integer departmentCount;
}
