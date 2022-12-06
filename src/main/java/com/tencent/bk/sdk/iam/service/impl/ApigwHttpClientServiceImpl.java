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

package com.tencent.bk.sdk.iam.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tencent.bk.sdk.iam.config.IamConfiguration;
import com.tencent.bk.sdk.iam.constants.HttpHeader;
import com.tencent.bk.sdk.iam.service.HttpClientService;
import com.tencent.bk.sdk.iam.util.JsonUtil;
import com.tencent.bk.sdk.iam.util.http.DefaultApacheHttpClientBuilder;
import com.tencent.bk.sdk.iam.util.http.HttpDeleteWithBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.tencent.bk.sdk.iam.constants.HttpHeader.REQUEST_ID;

@Slf4j
public class ApigwHttpClientServiceImpl implements HttpClientService {

    private CloseableHttpClient httpClient = DefaultApacheHttpClientBuilder.get().build();
    private IamConfiguration iamConfiguration;

    public ApigwHttpClientServiceImpl(IamConfiguration iamConfiguration) {
        this.iamConfiguration = iamConfiguration;
    }

    @Override
    public String doHttpGet(String uri) {
        HttpGet getRequest = new HttpGet(buildUrl(uri));
        buildAuthHeader(getRequest);
        return doExecuteRequest(getRequest);
    }

    @Override
    public String doHttpPost(String uri, Object body) {
        HttpPost postRequest = new HttpPost(buildUrl(uri));
        try {
            String jsonBody = JsonUtil.toJson(body);
            log.debug("Post request, uri:{}, body:{}", uri, jsonBody);
            log.info("iam-sdk doHttpPost jsonBody : {}", jsonBody);
            postRequest.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));
        } catch (JsonProcessingException e) {
            log.warn("Json encode failed!|{}|{}|{}", uri, body, e);
            return null;
        }
        buildAuthHeader(postRequest);
        return doExecuteRequest(postRequest);
    }

    @Override
    public String doHttpPut(String uri, Object body) {
        HttpPut putRequest = new HttpPut(buildUrl(uri));
        try {
            String jsonBody = JsonUtil.toJson(body);
            log.debug("Put request, uri:{}, body:{}", uri, jsonBody);
            putRequest.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));
        } catch (JsonProcessingException e) {
            log.warn("Json encode failed!|{}|{}", uri, body, e);
            return null;
        }
        buildAuthHeader(putRequest);
        return doExecuteRequest(putRequest);
    }

    @Override
    public String doHttpPut(String uri) {
        HttpPut putRequest = new HttpPut(buildUrl(uri));
        buildAuthHeader(putRequest);
        return doExecuteRequest(putRequest);
    }

    @Override
    public String doHttpDelete(String uri) {
        HttpDelete deleteRequest = new HttpDelete(buildUrl(uri));
        buildAuthHeader(deleteRequest);
        return doExecuteRequest(deleteRequest);
    }

    @Override
    public String doHttpDelete(String uri, Object body) {
        HttpDeleteWithBody deleteWithBodyRequest = new HttpDeleteWithBody(buildUrl(uri));
        try {
            String jsonBody = JsonUtil.toJson(body);
            log.debug("delete request, uri:{}, body:{}", uri, jsonBody);
            deleteWithBodyRequest.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));
        } catch (JsonProcessingException e) {
            log.warn("Json encode failed!|{}|{}|{}", uri, body, e);
            return null;
        }
        buildAuthHeader(deleteWithBodyRequest);
        return doExecuteRequest(deleteWithBodyRequest);
    }

    private String doExecuteRequest(HttpRequestBase request) {
        try {
            log.info("doExecuteRequest {}", request);
            CloseableHttpResponse response = httpClient.execute(request);
            log.info("doExecuteRequest|{}", response);
            if (response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                String requestId = "";
                if (response.getFirstHeader(REQUEST_ID) != null) {
                    requestId = response.getFirstHeader(REQUEST_ID).getValue();
                }
                String responseString = response.getEntity() == null ? null : EntityUtils.toString(response.getEntity(), Consts.UTF_8);
                log.info("Http response|{}|{}|{}", statusCode, requestId, responseString);
                return responseString;
            } else {
                log.warn("Http response is null!");
            }
        } catch (IOException e) {
            log.warn("http exception uri:{}, {}", request.getURI(), e);
            e.printStackTrace();
        }
        return null;
    }

    private String buildUrl(String uri) {
        String url = iamConfiguration.getApigwBaseUrl() + uri;
        log.info("ApigwHttpClientServiceImpl url:{}", url);
        return url;
    }

    private void buildAuthHeader(HttpRequestBase httpRequest) {
        Map<String, String> header = new HashMap<>();
        try {
            log.info("buildAuthHeader appcode:{}", iamConfiguration.getAppCode());
            header.put(HttpHeader.APIGW_BK_APP_CODE, iamConfiguration.getAppCode());
            header.put(HttpHeader.APIGW_BK_APP_SECRET, iamConfiguration.getAppSecret());
            String headerStr = JsonUtil.toJson(header);
            httpRequest.setHeader(HttpHeader.AUTHORIZATION_HEAD, headerStr);
        } catch (Exception e) {
            log.warn("buildHeader fail");
        }
    }

}
