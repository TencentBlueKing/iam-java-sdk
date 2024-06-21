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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CallbackMethodEnum {
    /**
     * 查询某个资源类型可用于配置权限的属性列
     */
    LIST_ATTRIBUTE("list_attr"),
    /**
     * 获取一个资源类型某个属性的值列表
     */
    LIST_ATTRIBUTE_VALUE("list_attr_value"),
    /**
     * 根据过滤条件查询实例
     */
    LIST_INSTANCE("list_instance"),
    /**
     * 查询资源授权管理记录
     */
    LIST_RESOURCE_AUTHORIZATION("list_resource_authorization"),
    /**
     * 批量获取资源实例详情
     */
    FETCH_INSTANCE_INFO("fetch_instance_info"),
    /**
     * 根据策略表达式查询资源实例
     */
    LIST_INSTANCE_BY_POLICY("list_instance_by_policy"),
    /**
     * 根据过滤条件和搜索关键字查询实例
     */
    SEARCH_INSTANCE("search_instance"),
    /**
     * 根据过滤条件搜索实例
     */
    FETCH_INSTANCE_LIST("fetch_instance_list"),
    /**
     * 获取资源类型 schema 定义
     */
    FETCH_RESOURCE_TYPE_SCHEMA("fetch_resource_type_schema");

    @JsonValue
    private String method;

    @JsonCreator
    private CallbackMethodEnum parseMethod(String method) {
        if (StringUtils.isBlank(method)) {
            return null;
        }
        for (CallbackMethodEnum callbackMethod : values()) {
            if (callbackMethod.getMethod().equals(method)) {
                return callbackMethod;
            }
        }
        return null;
    }
}
