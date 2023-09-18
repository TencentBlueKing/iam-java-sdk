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

public class IamUri {

    // 添加iam接入系统
    public static final String ADD_SYSTEM = "/api/v1/model/systems";
    // 修改iam接口系统
    public static final String UPDATE_SYSTEM = "/api/v1/model/systems/%s";
    // 系统通用查询
    public static final String QUERY_SYSTEM = "/api/v1/model/systems/%s/query";

    // 添加资源类型
    public static final String ADD_RESOURCE_TYPE = "/api/v1/model/systems/%s/resource-types";
    // 修改或删除资源类型
    public static final String UPDATE_OR_DELETE_RESOURCE_TYPE = "/api/v1/model/systems/%s/resource-types/%s";

    // 添加action
    public static final String ADD_ACTION = "/api/v1/model/systems/%s/actions";
    // 修改action
    public static final String UPDATE_ACTION = "/api/v1/model/systems/%s/actions/%s";
    // 删除action
    public static final String DELETE_ACTION = "/api/v1/model/systems/%s/actions/%s?check_existence=%s";

    // 添加实例视图
    public static final String ADD_INSTANCE_SELECTIONS = "/api/v1/model/systems/%s/instance-selections";
    // 修改实例视图
    public static final String UPDATE_INSTANCE_SELECTIONS = "/api/v1/model/systems/%s/instance-selections/%s";
    // 删除实例视图
    public static final String DELETE_INSTANCE_SELECTIONS = "/api/v1/model/systems/%s/instance-selections/$s";

    // 添加新建关联
    public static final String ADD_RESOURCE_CREATOR_ACTIONS = "/api/v1/model/systems/%s/configs/resource_creator_actions";
    // 修改新建关联
    public static final String UPDATE_RESOURCE_CREATOR_ACTIONS = "/api/v1/model/systems/%s/configs/resource_creator_actions";

    // 新增操作组
    public static final String ADD_ACTION_GROUP = "/api/v1/model/systems/%s/configs/action_groups";
    // 修改操作组
    public static final String UPDATE_ACTION_GROUP = "/api/v1/model/systems/%s/configs/action_groups";

    public static final String ADD_OR_UPDATE_ACTION_TOPOLOGY = "/api/v1/model/systems/%s/action-topologies/%s";

    public static final String QUERY_SYSTEM_INFO = "/api/v1/model/systems/%s/query";
    public static final String GET_SYSTEM_TOKEN = "/api/v1/model/systems/%s/token";

    public static final String QUERY_POLICY = "/api/v1/policy/query";
    public static final String BATCH_QUERY_POLICY = "/api/v1/policy/query_by_actions";
    public static final String QUERY_POLICY_WITH_RESOURCE = "/api/v1/policy/query_by_ext_resources";
    public static final String USER_GROUP = "/api/v1/open/users/%s/groups?inherit=%s";
    public static final String AUTH_POLICY = "/api/v1/policy/auth";

    public static final String GRANT_OR_REVOKE = "/api/v1/open/authorization/path/";
    public static final String BATCH_GRANT_OR_REVOKE = "/api/v1/open/authorization/batch_path/";

    public static final String MANAGER_ROLE = "/api/v1/open/management/grade_managers/";
    public static final String MANAGER_ROLE_UPDATE = "/api/v1/open/management/grade_managers/%s/";
    public static final String MANAGER_GRADE_GROUP = "/api/v1/open/management/grade_managers/%s/groups/";
    public static final String MANAGER_ROLE_GROUP = "/api/v1/open/management/groups/%s/";
    public static final String MANAGER_GRADE_MEMBER_GET = "/api/v1/open/management/grade_managers/%s/members/";
    public static final String MANAGER_GRADE_MEMBER_CREATE = "/api/v1/open/management/grade_managers/%s/members/";
    public static final String MANAGER_GRADE_MEMBER_DEL = "/api/v1/open/management/grade_managers/%s/members/?members=%s";
    public static final String MANAGER_ROLE_GROUP_MEMBER = "/api/v1/open/management/groups/%s/members/";
    public static final String MANAGER_ROLE_GROUP_MEMBER_DEL = "/api/v1/open/management/groups/%s/members/?type=%s&ids=%s";
    public static final String MANAGER_USER_ROLE = "/api/v1/open/management/users/grade_managers/";
    public static final String MANAGER_ROLE_PERMISSION = "/api/v1/open/management/groups/%s/policies/";
    public static final String USER_MANAGER_LIST_GET = "/api/v1/open/management/users/grade_managers/%s/groups/?user_id=%s";
    public static final String RESOURCE_CREATOR_ACTION = "/api/v1/open/authorization/resource_creator_action/";
    public static final String PERMISSION_URL = "/api/v1/open/application/";

    // v1批量action直接鉴权
    public static final String POLICY_AUTH_BY_ACTIONS = "/api/v1/policy/auth_by_actions";

}
