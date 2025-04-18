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

import com.fasterxml.jackson.core.type.TypeReference;
import com.tencent.bk.sdk.iam.config.IamConfiguration;
import com.tencent.bk.sdk.iam.constants.GrantType;
import com.tencent.bk.sdk.iam.constants.IamUri;
import com.tencent.bk.sdk.iam.dto.grant.BatchGrantDTO;
import com.tencent.bk.sdk.iam.dto.grant.BatchGrantPolicyDTO;
import com.tencent.bk.sdk.iam.dto.SubjectDTO;
import com.tencent.bk.sdk.iam.dto.grant.GrantPolicyDTO;
import com.tencent.bk.sdk.iam.dto.grant.GrantResourceDTO;
import com.tencent.bk.sdk.iam.dto.grant.SimpleGrantDTO;
import com.tencent.bk.sdk.iam.dto.response.ResponseDTO;
import com.tencent.bk.sdk.iam.service.GrantService;
import com.tencent.bk.sdk.iam.service.HttpClientService;
import com.tencent.bk.sdk.iam.util.AuthRequestContext;
import com.tencent.bk.sdk.iam.util.JsonUtil;
import com.tencent.bk.sdk.iam.util.ResponseUtil;
import com.tencent.bk.sdk.iam.util.ThreadUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GrantServiceImpl implements GrantService {

	private HttpClientService httpClientService;

	private IamConfiguration iamConfiguration;

	public GrantServiceImpl(HttpClientService httpClientService, IamConfiguration iamConfiguration) {
		this.httpClientService = httpClientService;
		this.iamConfiguration = iamConfiguration;
	}

	@Override
	public GrantPolicyDTO grantPermission(String userId, String action, List<GrantResourceDTO> resources) {
		return grantAndRemove(userId, action, GrantType.GRANT, resources);
	}

	@Override
	public GrantPolicyDTO revokePermission(String userId, String action, List<GrantResourceDTO> resources) {
		return grantAndRemove(userId, action, GrantType.REVOKE, resources);
	}

	@Override
	public List<BatchGrantPolicyDTO> batchRevokePermission(String userId, List<String> actions, List<GrantResourceDTO> resources) {
		return batchGrantAndRemove(userId, actions, GrantType.GRANT, resources);
	}

	@Override
	public List<BatchGrantPolicyDTO> batchGrantPermission(String userId, List<String> actions, List<GrantResourceDTO> resources) {
		return batchGrantAndRemove(userId, actions, GrantType.REVOKE, resources);
	}

	@Override
	public GrantPolicyDTO grantPermission(String userId, String action, List<GrantResourceDTO> resources, String tenantId) {
		ThreadUtil.setTenantId(tenantId);
		return grantPermission(userId, action, resources);
	}

	@Override
	public GrantPolicyDTO revokePermission(String userId, String action, List<GrantResourceDTO> resources, String tenantId) {
		ThreadUtil.setTenantId(tenantId);
		return revokePermission(userId, action, resources);
	}

	@Override
	public List<BatchGrantPolicyDTO> batchRevokePermission(String userId, List<String> actions, List<GrantResourceDTO> resources, String tenantId) {
		ThreadUtil.setTenantId(tenantId);
		return batchRevokePermission(userId, actions, resources);
	}

	@Override
	public List<BatchGrantPolicyDTO> batchGrantPermission(String userId, List<String> actions, List<GrantResourceDTO> resources, String tenantId) {
		ThreadUtil.setTenantId(tenantId);
		return batchGrantPermission(userId, actions, resources);
	}


	@SneakyThrows
	private GrantPolicyDTO grantAndRemove(String userId, String action, GrantType grantType, List<GrantResourceDTO> resourceList) {
		AuthRequestContext.setRequestName("GRANT_OR_REVOKE");
		SimpleGrantDTO grantBaseDto = SimpleGrantDTO.builder().asynchronous(false).operate(grantType.getType()).
			system(iamConfiguration.getSystemId()).action(GrantActionDTO.builder().id(action).build())
			.subject(SubjectDTO.builder().id(userId).type("user").build())
			.resources(resourceList)
			.build();
		String grantResponse = httpClientService.doHttpPost(IamUri.GRANT_OR_REVOKE, grantBaseDto);
		log.info("grant action response|{}", grantResponse);
		if (StringUtils.isNotBlank(grantResponse)) {
			if (log.isDebugEnabled()) {
				log.debug("grant action response|{}", grantResponse);
			}
			ResponseDTO<GrantPolicyDTO> responseInfo;
			try {
				responseInfo = JsonUtil.fromJson(grantResponse,
					new TypeReference<ResponseDTO<GrantPolicyDTO>>() {
					});
			} catch (IOException e) {
				log.error("Error while grant action response!|{}|{}|{}|{}|{}", userId, action, resourceList,
					grantResponse, e);
				throw e;
			}
			if (responseInfo != null) {
				ResponseUtil.checkResponse(responseInfo);
				return responseInfo.getData();
			}
		} else {
			log.warn("grant action got empty response!");
		}
		return null;
	}

	private List<BatchGrantPolicyDTO> batchGrantAndRemove(
		String userId,
		List<String> actions,
		GrantType grantType,
		List<GrantResourceDTO> resourceList
	) {
		AuthRequestContext.setRequestName("BATCH_GRANT_OR_REVOKE");
		List<GrantActionDTO> batchActions = new ArrayList<>();
		actions.forEach( it ->
			batchActions.add(GrantActionDTO.builder().id(it).build())
		);
		BatchGrantDTO grantBaseDto = BatchGrantDTO.builder().asynchronous(false).operate(grantType).
			system(iamConfiguration.getSystemId()).actions(batchActions)
			.subject(SubjectDTO.builder().id(userId).type("user").build())
			.resources(resourceList)
			.build();
		String grantResponse = httpClientService.doHttpPost(IamUri.BATCH_GRANT_OR_REVOKE, grantBaseDto);
		log.info("grant action response|{}", grantResponse);
		if (StringUtils.isNotBlank(grantResponse)) {
			if (log.isDebugEnabled()) {
				log.debug("batch grant action response|{}", grantResponse);
			}
			ResponseDTO<List<BatchGrantPolicyDTO>> responseInfo;
			try {
				responseInfo = JsonUtil.fromJson(grantResponse,
					new TypeReference<ResponseDTO<List<BatchGrantPolicyDTO>>>() {
					});
			} catch (IOException e) {
				log.error("Error while grant action response!|{}|{}|{}|{}", userId, actions, resourceList,
					grantResponse, e);
				return null;
			}
			if (responseInfo != null) {
				ResponseUtil.checkResponse(responseInfo);
				return responseInfo.getData();
			}
		} else {
			log.warn("batch grant action got empty response!");
		}
		return null;
	}
}
