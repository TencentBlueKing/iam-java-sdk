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

public class HttpHeader {
    public static final String BK_LANGUAGE = "Blueking-Language";
    public static final String REQUEST_ID = "X-Request-Id";
    public static final String BK_TENANT_ID = "X-Bk-Tenant-Id";

    public static final String AUTH = "Authorization";
    public static final String AUTH_SIGN = "X-Bk-IAM-Signature";

    public static final String BK_APP_CODE = "X-Bk-App-Code";
    public static final String BK_APP_SECRET = "X-Bk-App-Secret";
    public static final String BK_IAM_VERSION = "X-Bk-IAM-Version";

    public static final String APIGW_BK_APP_CODE = "bk_app_code";
    public static final String APIGW_BK_APP_SECRET = "bk_app_secret";
    public static final String AUTHORIZATION_HEAD = "x-bkapi-authorization";
}
