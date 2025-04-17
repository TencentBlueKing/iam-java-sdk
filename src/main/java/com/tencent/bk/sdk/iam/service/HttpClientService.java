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

import com.tencent.bk.sdk.iam.service.decorator.IamService;

public interface HttpClientService extends IamService {

    /**
     * 发起 HTTP GET 请求
     *
     * @param uri 请求路径
     * @return 响应包体
     */
    String doHttpGet(String uri);

    /**
     * 发起 HTTP POST 请求
     *
     * @param uri  请求路径
     * @param body 请求体
     * @return 响应包体
     */
    String doHttpPost(String uri, Object body);

    /**
     * 发起 HTTP PUT 请求
     *
     * @param uri  请求路径
     * @param body 请求体
     * @return 响应包体
     */
    String doHttpPut(String uri, Object body);

    /**
     * 发起 HTTP PUT 请求
     *
     * @param uri  请求路径
     * @return 响应包体
     */
    String doHttpPut(String uri);

    /**
     * 发起 HTTP DELETE 请求
     *
     * @param uri 请求路径
     * @return 响应包体
     */
    String doHttpDelete(String uri);

    /**
     * 发起 HTTP DELETE 请求
     *
     * @param uri  请求路径
     * @param body 请求体
     * @return 响应包体
     */
    String doHttpDelete(String uri, Object body);
}
