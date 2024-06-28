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

package com.tencent.bk.sdk.iam.constants;

public class V2IamUri {
    public static final String V2_MANAGER_GRADE_GROUP_CREATE = "/api/v2/open/management/systems/%s/grade_managers/%s/groups/";
    public static final String V2_MANAGER_ROLE_GROUP_UPDATE = "/api/v2/open/management/systems/%s/groups/%s/";
    public static final String V2_MANAGER_ROLE_GROUP_DELETE = "/api/v2/open/management/systems/%s/groups/%s/";
    public static final String V2_MANAGER_ROLE_GROUP_GRANT = "/api/v2/open/management/systems/%s/groups/%s/policies/";

    public static final String V2_MANAGER_ROLE_GROUP_ACTION_DEL = "/api/v2/open/management/systems/%s/groups/%s/actions/-/policies/";
    public static final String V2_MANAGER_ROLE_GROUP_ACTION_GET = "/api/v2/open/management/systems/%s/groups/%s/policies/-/actions/";
    public static final String V2_MANAGER_ROLE_GROUP_PERMISSION_DETAIL_GET = "/api/v2/open/management/systems/%s/groups/%s/policies/";
    public static final String V2_MANAGER_ROLE_GROUP_APPLICATIONS_CREATE = "/api/v2/open/management/systems/%s/groups/-/applications/";
    public static final String V2_MANAGER_ROLE_GROUP_BELONG = "/api/v2/open/management/systems/%s/users/%s/groups/belong/?groups=%s&inherit=%s";
    public static final String V2_MANAGER_ROLE_GROUP_MEMBER_CREATE = "/api/v2/open/management/systems/%s/groups/%s/members/";
    public static final String V2_MANAGER_ROLE_GROUP_MEMBER_GET = "/api/v2/open/management/systems/%s/groups/%s/members/";
    public static final String V2_MANAGER_ROLE_GROUP_TEMPLATES_LIST = "/api/v2/open/management/systems/%s/groups/%s/subject_templates/";
    public static final String V2_MANAGER_ROLE_GROUP_MEMBER_DEL = "/api/v2/open/management/systems/%s/groups/%s/members/?type=%s&ids=%s";
    public static final String V2_MANAGER_ROLE_GROUP_MEMBER_RENEWAL = "/api/v2/open/management/systems/%s/groups/%s/members/-/expired_at/";
    public static final String V2_AUTH_POLICY = "/api/v2/policy/systems/%s/auth/";
    public static final String V2_QUERY_POLICY = "/api/v2/policy/systems/%s/query/";
    public static final String V2_GRADE_MANAGER_APPLICATION_CREATE = "/api/v2/open/management/systems/%s/grade_managers/-/applications/";
    public static final String V2_GRADE_MANAGER_APPLICATION_UPDATE = "/api/v2/open/management/systems/%s/grade_managers/%s/applications/";
    public static final String V2_CALLBACK_APPLICATION_HANDLE = "/api/v2/open/management/systems/%s/applications/%s/approve/";
    public static final String V2_CALLBACK_APPLICATION_CANCEL = "/api/v2/open/management/systems/%s/applications/%s/cancel/";

    public static final String V2_SUBSET_GRADE_MANAGER_GROUP_CREATE = "/api/v2/open/management/systems/%s/subset_managers/%s/groups/";
    public static final String V2_VERIFY_GROUP_VALID_MEMBER = "/api/v2/open/management/systems/%s/users/%s/groups/belong/?group_ids=%s";
    public static final String V2_VERIFY_GROUP_VALID_DEPARTMENT = "/api/v2/open/management/systems/%s/departments/%s/groups/belong/?group_ids=%s";
    public static final String V2_SUBSET_GRADE_MANAGER_GROUP_GET = "/api/v2/open/management/systems/%s/subset_managers/%s/groups/";
    public static final String V2_MANAGER_GRADE_GROUP_GET = "/api/v2/open/management/systems/%s/grade_managers/%s/groups/";
    public static final String V2_MANAGER_GRADE_TEMPLATE_GET = "/api/v2/open/management/systems/%s/grade_managers/%s/subject_templates/";
    public static final String V2_MEMBER_GROUP_DETAILS_GET = "/api/v2/open/management/systems/%s/grade_managers/%s/group_member_types/%s/members/%s/groups/-/details?group_ids=%s";

    // 创建分级管理员
    public static final String V2_MANAGER_ROLE_CREATE = "/api/v2/open/management/systems/%s/grade_managers/";
    // 更新分级管理员
    public static final String V2_MANAGER_ROLE_UPDATE = "/api/v2/open/management/systems/%s/grade_managers/%s/";
    // 获取分级管理员详情
    public static final String V2_MANAGER_ROLE_DETAIL_GET = "/api/v2/open/management/systems/%s/grade_managers/%s";
    // 删除分级管理员
    public static final String V2_MANAGER_ROLE_DELETE = "/api/v2/open/management/systems/%s/grade_managers/%s";

    // 创建二级管理员
    public static final String V2_SUBSET_GRADE_MANAGER_CREATE = "/api/v2/open/management/systems/%s/grade_managers/%s/subset_managers/";
    // 获取二级管理员详情
    public static final String V2_SUBSET_GRADE_MANAGER_DETAIL_GET = "/api/v2/open/management/systems/%s/subset_managers/%s";
    // 更新二级管理员
    public static final String V2_SUBSET_GRADE_MANAGER_UPDATE = "/api/v2/open/management/systems/%s/subset_managers/%s";
    // 删除二级管理员
    public static final String V2_SUBSET_GRADE_MANAGER_DELETE = "/api/v2/open/management/systems/%s/subset_managers/%s/";
    // 用户组续期（需要审批版）
    public static final String V2_GROUPS_RENEW_APPLICATIONS = "/api/v2/open/management/systems/%s/groups/-/renew/applications/";

    // v2批量action直接鉴权
    public static final String V2_POLICY_QUERY_BY_ACTIONS = "/api/v2/policy/systems/%s/auth_by_actions/";

    // v2批量操作策略查询
    public static final String V2_BATCH_QUERY_POLICY = "/api/v2/policy/systems/%s/query_by_actions/";
}
