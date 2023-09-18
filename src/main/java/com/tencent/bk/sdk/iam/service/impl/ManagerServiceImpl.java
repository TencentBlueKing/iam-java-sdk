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
import com.tencent.bk.sdk.iam.constants.IamUri;
import com.tencent.bk.sdk.iam.dto.CreateRelationDTO;
import com.tencent.bk.sdk.iam.dto.PageInfoDTO;
import com.tencent.bk.sdk.iam.dto.PermissionUrlDTO;
import com.tencent.bk.sdk.iam.dto.V2PageInfoDTO;
import com.tencent.bk.sdk.iam.dto.manager.AuthorizationScopes;
import com.tencent.bk.sdk.iam.dto.manager.ManagerRoleGroup;
import com.tencent.bk.sdk.iam.dto.manager.ManagerRoleGroupInfo;
import com.tencent.bk.sdk.iam.dto.manager.dto.CreateManagerDTO;
import com.tencent.bk.sdk.iam.dto.manager.dto.ManagerMemberGroupDTO;
import com.tencent.bk.sdk.iam.dto.manager.dto.ManagerRoleGroupDTO;
import com.tencent.bk.sdk.iam.dto.manager.dto.ManagerRoleMemberDTO;
import com.tencent.bk.sdk.iam.dto.manager.vo.CreateVo;
import com.tencent.bk.sdk.iam.dto.manager.vo.ManagerGroupMemberVo;
import com.tencent.bk.sdk.iam.dto.manager.vo.ManagerRoleGroupVO;
import com.tencent.bk.sdk.iam.dto.response.ResponseDTO;
import com.tencent.bk.sdk.iam.exception.IamException;
import com.tencent.bk.sdk.iam.service.ManagerService;
import com.tencent.bk.sdk.iam.util.JsonUtil;
import com.tencent.bk.sdk.iam.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Slf4j
public class ManagerServiceImpl implements ManagerService {

    private ApigwHttpClientServiceImpl apigwHttpClientService;
    private IamConfiguration iamConfiguration;

    public ManagerServiceImpl(ApigwHttpClientServiceImpl apigwHttpClientService, IamConfiguration iamConfiguration) {
        this.apigwHttpClientService = apigwHttpClientService;
        this.iamConfiguration = iamConfiguration;
    }

    @Override
    public Integer createManager(CreateManagerDTO createManagerDTO) {
        try {
            String responseStr = apigwHttpClientService.doHttpPost(IamUri.MANAGER_ROLE, createManagerDTO);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("create manager response|{}", responseStr);
                ResponseDTO<CreateVo> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<CreateVo>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData().getId();
                }
            } else {
                log.warn("create manager got empty response!");
            }
        } catch (IamException iamException) {
            log.error("create manager failed|{}|{}", iamException.getErrorCode(), iamException.getErrorMsg());
            throw iamException;
        } catch (Exception e) {
            log.error("create manager failed|{}", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ManagerRoleGroupVO getGradeManagerRoleGroup(Integer projectId, PageInfoDTO pageInfoDTO) {
        try {
            String responseStr = apigwHttpClientService.doHttpGet(buildURLPage(String.format(IamUri.MANAGER_GRADE_GROUP, projectId.toString()), pageInfoDTO));
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("get manager role response|{}", responseStr);
                ResponseDTO<ManagerRoleGroupVO> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<ManagerRoleGroupVO>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData();
                }
            } else {
                log.warn("get manager role got empty response!");
            }
        } catch (IamException iamException) {
            log.error("get manager role failed|{}|{}", iamException.getErrorCode(), iamException.getErrorMsg());
            throw iamException;
        } catch (Exception e) {
            log.error("get manager role failed|{}", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Integer batchCreateRoleGroup(Integer projectId, ManagerRoleGroupDTO managerRoleGroupDTO) {
        try {
            String url = String.format(IamUri.MANAGER_GRADE_GROUP, projectId);
            String responseStr = apigwHttpClientService.doHttpPost(url, managerRoleGroupDTO);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("create manager role group response|{}", responseStr);
                ResponseDTO<List<Integer>> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<List<Integer>>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData().get(0);
                }
            } else {
                log.warn("create manager role group got empty response!");
            }
        } catch (IamException iamException) {
            log.error("create manager role group failed|{}|{}", iamException.getErrorCode(), iamException.getErrorMsg());
            throw iamException;
        } catch (Exception e) {
            log.error("create manager role group failed|{}", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void updateRoleGroup(Integer roleId, ManagerRoleGroup managerRoleGroup) {
        try {
            String responseStr = apigwHttpClientService.doHttpPut(String.format(IamUri.MANAGER_ROLE_GROUP, roleId.toString()), managerRoleGroup);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("update manager role group response|{}", responseStr);
                ResponseDTO<Object> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                }
            } else {
                log.warn("update manager role group got empty response!");
            }
        } catch (IamException iamException) {
            log.error("update manager role group failed|{}|{}", iamException.getErrorCode(), iamException.getErrorMsg());
            throw iamException;
        } catch (Exception e) {
            log.error("update manager role group failed|{}", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteRoleGroup(Integer roleId) {
        try {
            String responseStr = apigwHttpClientService.doHttpDelete(String.format(IamUri.MANAGER_ROLE_GROUP, roleId.toString()));
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("delete manager role group response|{}", responseStr);
                ResponseDTO<Object> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                }
            } else {
                log.warn("delete manager role group got empty response!");
            }
        } catch (IamException iamException) {
            log.error("delete manager role group failed|{}|{}", iamException.getErrorCode(), iamException.getErrorMsg());
            throw iamException;
        } catch (Exception e) {
            log.error("delete manager role group failed|{}", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getGradeManagerRoleMember(Integer projectId, PageInfoDTO pageInfoDTO) {
        String url = buildURLPage(String.format(IamUri.MANAGER_GRADE_MEMBER_GET, projectId.toString()), pageInfoDTO);
        try {
            String responseStr = apigwHttpClientService.doHttpGet(url);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("get manager role member response|{}", responseStr);
                ResponseDTO<List<String>> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<List<String>>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData();
                }
            } else {
                log.warn("get manager role member got empty response!");
            }
        } catch (IamException iamException) {
            log.error("get manager role member failed|{}|{}", iamException.getErrorCode(), iamException.getErrorMsg());
            throw iamException;
        } catch (Exception e) {
            log.error("get manager role member failed|{}", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void batchCreateGradeManagerRoleMember(ManagerRoleMemberDTO members, Integer roleId) {
        String url = String.format(IamUri.MANAGER_GRADE_MEMBER_CREATE, roleId);
        try {
            String responseStr = apigwHttpClientService.doHttpPost(url, members);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("create manager role member response|{}", responseStr);
                ResponseDTO<Object> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                }
            } else {
                log.warn("create manager role member got empty response!");
            }
        } catch (IamException iamException) {
            log.error("create manager role member failed|{}|{}", iamException.getErrorCode(), iamException.getErrorMsg());
            throw iamException;
        } catch (Exception e) {
            log.error("create manager role member failed|{}", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteGradeManagerRoleMember(String members, Integer projectId) {
        String url = String.format(IamUri.MANAGER_GRADE_MEMBER_DEL, projectId.toString(), members);
        try {
            String responseStr = apigwHttpClientService.doHttpDelete(url);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("delete manager role member response|{}", responseStr);
                ResponseDTO<Object> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                }
            } else {
                log.warn("delete manager role member got empty response!");
            }
        } catch (IamException iamException) {
            log.error("delete manager role member failed|{}|{}", iamException.getErrorCode(), iamException.getErrorMsg());
            throw iamException;
        } catch (Exception e) {
            log.error("delete manager role member failed|{}", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public ManagerGroupMemberVo getRoleGroupMember(Integer roleId, PageInfoDTO pageInfoDTO) {
        String url = buildURLPage(String.format(IamUri.MANAGER_ROLE_GROUP_MEMBER, roleId.toString()), pageInfoDTO);
        try {
            String responseStr = apigwHttpClientService.doHttpGet(url);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("get role group member response|{}", responseStr);
                ResponseDTO<ManagerGroupMemberVo> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<ManagerGroupMemberVo>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData();
                }
            } else {
                log.warn("get role group member got empty response!");
            }
        } catch (IamException iamException) {
            log.error("get role group member failed|{}|{}", iamException.getErrorCode(), iamException.getErrorMsg());
            throw iamException;
        } catch (Exception e) {
            log.error("get role group member failed|{}", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void createRoleGroupMember(Integer roleId, ManagerMemberGroupDTO managerMemberGroupDTO) {
        String url = String.format(IamUri.MANAGER_ROLE_GROUP_MEMBER, roleId);
        try {
            String responseStr = apigwHttpClientService.doHttpPost(url, managerMemberGroupDTO);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("create role group member response|{}", responseStr);
                ResponseDTO<Object> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                }
            } else {
                log.warn("create role group member got empty response!");
            }
        } catch (IamException iamException) {
            log.error("create role group member failed|{}|{}", iamException.getErrorCode(), iamException.getErrorMsg());
            throw iamException;
        } catch (Exception e) {
            log.error("create role group member failed|{}", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteRoleGroupMember(Integer roleId, String type, String members) {
        String url = String.format(IamUri.MANAGER_ROLE_GROUP_MEMBER_DEL, roleId.toString(), type, members);
        try {
            String responseStr = apigwHttpClientService.doHttpDelete(url);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("delete role group member response|{}", responseStr);
                ResponseDTO<Object> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                }
            } else {
                log.warn("delete role group member got empty response!");
            }
        } catch (IamException iamException) {
            log.error("delete role group member failed|{}|{}", iamException.getErrorCode(), iamException.getErrorMsg());
            throw iamException;
        } catch (Exception e) {
            log.error("delete role group member failed|{}", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CreateVo> getUserRole(String userId, PageInfoDTO pageInfoDTO) {
        String url = buildURLPage(IamUri.MANAGER_USER_ROLE, pageInfoDTO);
        url = url + "&system=" + iamConfiguration.getSystemId() + "&user_id=" + userId;
        try {
            String responseStr = apigwHttpClientService.doHttpGet(url);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("get user role response|{}", responseStr);
                ResponseDTO<List<CreateVo>> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<List<CreateVo>>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData();
                }
            } else {
                log.warn("get user role got empty response!");
            }
        } catch (IamException iamException) {
            log.error("get user role failed|{}|{}", iamException.getErrorCode(), iamException.getErrorMsg());
            throw iamException;
        } catch (Exception e) {
            log.error("get user role failed|{}", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Boolean createRolePermission(Integer roleId, AuthorizationScopes permission) {
        String url = String.format(IamUri.MANAGER_ROLE_PERMISSION, roleId.toString());
        try {
            String responseStr = apigwHttpClientService.doHttpPost(url, permission);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("create role permission response|{}", responseStr);
                ResponseDTO<Object> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                }
            } else {
                log.warn("create role permission got empty response!");
            }
        } catch (IamException iamException) {
            log.error("create role permission failed|{}|{}", iamException.getErrorCode(), iamException.getErrorMsg());
            throw iamException;
        } catch (Exception e) {
            log.error("create role permission failed|{}", e);
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public List<ManagerRoleGroupInfo> getUserGroup(Integer projectId, String userId) {
        String url = String.format(IamUri.USER_MANAGER_LIST_GET, projectId.toString(), userId);
        try {
            String responseStr = apigwHttpClientService.doHttpGet(url);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("getUserGroup response|{}", responseStr);
                ResponseDTO<List<ManagerRoleGroupInfo>> responseInfo = JsonUtil.fromJson(responseStr,
                    new TypeReference<ResponseDTO<List<ManagerRoleGroupInfo>>>() {
                    });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData();
                }
            } else {
                log.warn("getUserGroup response got empty response!");
            }
        } catch (IamException iamException) {
            log.error("getUserGroup response failed|{}|{}", iamException.getErrorCode(), iamException.getErrorMsg());
            throw iamException;
        } catch (Exception e) {
            log.error("getUserGroup response failed|{}", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void createResourceRelation(CreateRelationDTO createRelationDTO) {
        try {
            String responseStr = apigwHttpClientService.doHttpPost(IamUri.RESOURCE_CREATOR_ACTION, createRelationDTO);
            if (StringUtils.isNotBlank(responseStr)) {
                log.info("CreateResourceRelation response|{}", responseStr);
                ResponseDTO<Object> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return;
                }
            } else {
                log.warn("create manager got empty response!");
            }
        } catch (IamException iamException) {
            log.error("create manager failed|{}|{}", iamException.getErrorCode(), iamException.getErrorMsg());
            throw iamException;
        } catch (Exception e) {
            log.error("create manager failed|{}", e);
            throw new RuntimeException(e);
        }
        return;
    }

    @Override
    public String getPermissionUrl(PermissionUrlDTO permissionUrlDTO) {
        try {
            log.info("getPermissionUrl request|{}", permissionUrlDTO);
            String responseStr = apigwHttpClientService.doHttpPost(IamUri.PERMISSION_URL, permissionUrlDTO);
            if (StringUtils.isNotBlank(responseStr)) {
                log.info("getPermissionUrl response|{}", responseStr);
                ResponseDTO<Object> responseInfo = JsonUtil.fromJson(responseStr,
                    new TypeReference<ResponseDTO<Object>>() {
                    });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    String url = responseInfo.getData().toString();
                    String urlStr = url.substring(url.indexOf("=") + 1, url.length() - 1);
                    return urlStr;
                }
            } else {
                log.warn("getPermissionUrl response got empty response!");
            }
        } catch (IamException iamException) {
            log.error("getPermissionUrl failed|{}|{}", iamException.getErrorCode(), iamException.getErrorMsg());
            throw iamException;
        } catch (Exception e) {
            log.error("getPermissionUrl failed|{}", e);
            throw new RuntimeException(e);
        }
        return "";
    }

    private String buildURLPage(String iamURL, PageInfoDTO pageInfoDTO) {
        StringBuffer url = new StringBuffer();
        url.append(iamURL);
        if (pageInfoDTO != null) {
            url.append("?limit=");
            url.append(pageInfoDTO.getLimit().toString());
            url.append("&offset=");
            url.append(pageInfoDTO.getOffset().toString());
        }
        return url.toString();
    }

    private String v2BuildURLPage(String iamURL, V2PageInfoDTO pageInfoDTO) {
        StringBuffer url = new StringBuffer();
        url.append(iamURL);
        if (pageInfoDTO != null) {
            url.append("?page_size=");
            url.append(pageInfoDTO.getPageSize().toString());
            url.append("&page=");
            url.append(pageInfoDTO.getPage().toString());
        }
        return url.toString();
    }
}
