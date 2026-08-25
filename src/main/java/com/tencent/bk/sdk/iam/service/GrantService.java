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

import com.tencent.bk.sdk.iam.dto.grant.BatchGrantPolicyDTO;
import com.tencent.bk.sdk.iam.dto.grant.GrantPolicyDTO;
import com.tencent.bk.sdk.iam.dto.grant.GrantResourceDTO;

import java.util.List;

public interface GrantService {
    /**
     * 给用户授予权限
     */
    GrantPolicyDTO grantPermission(String userId, String action, List<GrantResourceDTO> resources);

    /**
     * 收回用户权限
     */
    GrantPolicyDTO revokePermission(String userId, String action, List<GrantResourceDTO> resources);

    /**
     * 给用户批量授予权限
     */
    List<BatchGrantPolicyDTO> batchRevokePermission(String userId, List<String> actions, List<GrantResourceDTO> resources);

    /**
     * 批量收回用户权限
     */
    List<BatchGrantPolicyDTO> batchGrantPermission(String userId, List<String> actions, List<GrantResourceDTO> resources);

    /**
     * 给用户授予权限(带租户ID)
     */
    GrantPolicyDTO grantPermission(String userId, String action, List<GrantResourceDTO> resources, String tenantId);

    /**
     * 收回用户权限(带租户ID)
     */
    GrantPolicyDTO revokePermission(String userId, String action, List<GrantResourceDTO> resources, String tenantId);

    /**
     * 给用户批量授予权限(带租户ID)
     */
    List<BatchGrantPolicyDTO> batchRevokePermission(String userId, List<String> actions, List<GrantResourceDTO> resources, String tenantId);

    /**
     * 批量收回用户权限(带租户ID)
     */
    List<BatchGrantPolicyDTO> batchGrantPermission(String userId, List<String> actions, List<GrantResourceDTO> resources, String tenantId);
}
