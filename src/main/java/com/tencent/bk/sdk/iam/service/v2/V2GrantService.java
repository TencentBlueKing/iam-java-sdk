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

package com.tencent.bk.sdk.iam.service.v2;

import com.tencent.bk.sdk.iam.dto.grant.ManagerRoleGroupGrantDTO;

public interface V2GrantService {
    /**
     * 用户组授权
     *
     * @param groupId
     * @param managerRoleGroupGrantDTO
     * @return
     */
    public void v2GrantRoleGroup(Integer groupId, ManagerRoleGroupGrantDTO managerRoleGroupGrantDTO);

    /**
     * 用户组授权回收
     *
     * @param groupId
     * @param managerRoleGroupGrantDTO
     * @return
     */
    public void revokeRoleGroupPolicies(Integer groupId, ManagerRoleGroupGrantDTO managerRoleGroupGrantDTO);
}
