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

package com.tencent.bk.sdk.iam.service.v2.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tencent.bk.sdk.iam.config.IamConfiguration;
import com.tencent.bk.sdk.iam.constants.ManagerScopesEnum;
import com.tencent.bk.sdk.iam.constants.V2IamUri;
import com.tencent.bk.sdk.iam.dto.CallbackApplicationDTO;
import com.tencent.bk.sdk.iam.dto.GradeManagerApplicationCreateDTO;
import com.tencent.bk.sdk.iam.dto.GradeManagerApplicationUpdateDTO;
import com.tencent.bk.sdk.iam.dto.PageInfoDTO;
import com.tencent.bk.sdk.iam.dto.V2PageInfoDTO;
import com.tencent.bk.sdk.iam.dto.action.GroupAction;
import com.tencent.bk.sdk.iam.dto.application.ApplicationDTO;
import com.tencent.bk.sdk.iam.dto.application.ApplicationVO;
import com.tencent.bk.sdk.iam.dto.manager.AuthorizationScopes;
import com.tencent.bk.sdk.iam.dto.manager.GroupMemberVerifyInfo;
import com.tencent.bk.sdk.iam.dto.manager.ManagerRoleGroup;
import com.tencent.bk.sdk.iam.dto.manager.RoleGroupMemberInfo;
import com.tencent.bk.sdk.iam.dto.manager.dto.CreateManagerDTO;
import com.tencent.bk.sdk.iam.dto.manager.dto.CreateSubsetManagerDTO;
import com.tencent.bk.sdk.iam.dto.manager.dto.GroupMemberRenewApplicationDTO;
import com.tencent.bk.sdk.iam.dto.manager.dto.ManagerMemberGroupDTO;
import com.tencent.bk.sdk.iam.dto.manager.dto.ManagerRoleGroupDTO;
import com.tencent.bk.sdk.iam.dto.manager.dto.SearchGroupDTO;
import com.tencent.bk.sdk.iam.dto.manager.dto.SearchTemplatesDTO;
import com.tencent.bk.sdk.iam.dto.manager.dto.UpdateManagerDTO;
import com.tencent.bk.sdk.iam.dto.manager.dto.UpdateSubsetManagerDTO;
import com.tencent.bk.sdk.iam.dto.manager.vo.CreateVo;
import com.tencent.bk.sdk.iam.dto.manager.vo.ManagerGroupMemberVo;
import com.tencent.bk.sdk.iam.dto.manager.vo.SubjectTemplateVO;
import com.tencent.bk.sdk.iam.dto.manager.vo.V2ManagerRoleGroupVO;
import com.tencent.bk.sdk.iam.dto.response.CallbackApplicationResponese;
import com.tencent.bk.sdk.iam.dto.response.GradeManagerApplicationResponse;
import com.tencent.bk.sdk.iam.dto.response.GroupPermissionDetailResponseDTO;
import com.tencent.bk.sdk.iam.dto.response.ManagerDetailResponse;
import com.tencent.bk.sdk.iam.dto.response.MemberGroupDetailsResponse;
import com.tencent.bk.sdk.iam.dto.response.ResponseDTO;
import com.tencent.bk.sdk.iam.exception.IamException;
import com.tencent.bk.sdk.iam.service.impl.ApigwHttpClientServiceImpl;
import com.tencent.bk.sdk.iam.service.v2.V2ManagerService;
import com.tencent.bk.sdk.iam.util.AuthRequestContext;
import com.tencent.bk.sdk.iam.util.HttpUtils;
import com.tencent.bk.sdk.iam.util.JsonUtil;
import com.tencent.bk.sdk.iam.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public class V2ManagerServiceImpl implements V2ManagerService {

    private final ApigwHttpClientServiceImpl apigwHttpClientService;
    private final IamConfiguration iamConfiguration;

    public V2ManagerServiceImpl(ApigwHttpClientServiceImpl apigwHttpClientService, IamConfiguration iamConfiguration) {
        this.apigwHttpClientService = apigwHttpClientService;
        this.iamConfiguration = iamConfiguration;
    }

    @Override
    public Integer batchCreateRoleGroupV2(Integer gradeManagerId, ManagerRoleGroupDTO managerRoleGroupDTO) {
        try {
            AuthRequestContext.setRequestName("V2_MANAGER_GRADE_GROUP_CREATE");
            String url = String.format(V2IamUri.V2_MANAGER_GRADE_GROUP_CREATE, iamConfiguration.getSystemId(), gradeManagerId);
            String responseStr = apigwHttpClientService.doHttpPost(url, managerRoleGroupDTO);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("create v2 manager role group response|{}", responseStr);
                ResponseDTO<List<Integer>> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<List<Integer>>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData().get(0);
                }
            } else {
                log.warn("create v2 manager role group got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("create v2 manager role group failed", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void updateRoleGroupV2(Integer groupId, ManagerRoleGroup managerRoleGroup) {
        try {
            AuthRequestContext.setRequestName("V2_MANAGER_ROLE_GROUP_UPDATE");
            String responseStr = apigwHttpClientService.doHttpPut(String.format(V2IamUri.V2_MANAGER_ROLE_GROUP_UPDATE, iamConfiguration.getSystemId(), groupId), managerRoleGroup);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("update V2 manager role group response|{}", responseStr);
                ResponseDTO<Object> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                }
            } else {
                log.warn("update V2 manager role group got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("update V2 manager role group failed", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteRoleGroupV2(Integer groupId) {
        try {
            AuthRequestContext.setRequestName("V2_MANAGER_ROLE_GROUP_DELETE");
            String responseStr = apigwHttpClientService.doHttpDelete(String.format(V2IamUri.V2_MANAGER_ROLE_GROUP_DELETE, iamConfiguration.getSystemId(), groupId));
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("delete V2 manager role group response|{}", responseStr);
                ResponseDTO<Object> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                }
            } else {
                log.warn("delete V2 manager role group got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("delete V2 manager role group failed", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void grantRoleGroupV2(Integer groupId, AuthorizationScopes authorizationScopes) {
        AuthRequestContext.setRequestName("V2_MANAGER_ROLE_GROUP_GRANT");
        String url = String.format(V2IamUri.V2_MANAGER_ROLE_GROUP_GRANT, iamConfiguration.getSystemId(), groupId);
        try {
            String responseStr = apigwHttpClientService.doHttpPost(url, authorizationScopes);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("grant V2 role group response|{}", responseStr);
                ResponseDTO<Object> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                }
            } else {
                log.warn("grant V2 role group got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("grant V2 role group failed", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createRoleGroupMemberV2(Integer groupId, ManagerMemberGroupDTO managerMemberGroupDTO) {
        AuthRequestContext.setRequestName("V2_MANAGER_ROLE_GROUP_MEMBER_CREATE");
        String url = String.format(V2IamUri.V2_MANAGER_ROLE_GROUP_MEMBER_CREATE, iamConfiguration.getSystemId(), groupId);
        try {
            String responseStr = apigwHttpClientService.doHttpPost(url, managerMemberGroupDTO);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("create V2 role group member response|{}", responseStr);
                ResponseDTO<Object> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                }
            } else {
                log.warn("create V2 role group member got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("create V2 role group member failed", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteRoleGroupMemberV2(Integer groupId, String type, String members) {
        AuthRequestContext.setRequestName("V2_MANAGER_ROLE_GROUP_MEMBER_DEL");
        String url = String.format(V2IamUri.V2_MANAGER_ROLE_GROUP_MEMBER_DEL, iamConfiguration.getSystemId(), groupId, type, members);
        try {
            String responseStr = apigwHttpClientService.doHttpDelete(url);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("delete V2 role group member response|{}", responseStr);
                System.out.println(responseStr);
                ResponseDTO<Object> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                }
            } else {
                log.warn("delete V2 role group member got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("delete V2 role group member failed", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public ManagerGroupMemberVo getRoleGroupMemberV2(Integer groupId, V2PageInfoDTO pageInfoDTO) {
        AuthRequestContext.setRequestName("V2_MANAGER_ROLE_GROUP_MEMBER_GET");
        String url = v2BuildURLPage(String.format(V2IamUri.V2_MANAGER_ROLE_GROUP_MEMBER_GET, iamConfiguration.getSystemId(), groupId), pageInfoDTO);
        try {
            String responseStr = apigwHttpClientService.doHttpGet(url);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("get V2 role group member response|{}", responseStr);
                ResponseDTO<ManagerGroupMemberVo> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<ManagerGroupMemberVo>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData();
                }
            } else {
                log.warn("get V2 role group member got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("get V2 role group member failed", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<RoleGroupMemberInfo> listRoleGroupTemplates(Integer groupId, V2PageInfoDTO pageInfoDTO) {
        AuthRequestContext.setRequestName("V2_MANAGER_ROLE_GROUP_TEMPLATES_LIST");
        String url = v2BuildURLPage(String.format(V2IamUri.V2_MANAGER_ROLE_GROUP_TEMPLATES_LIST, iamConfiguration.getSystemId(), groupId), pageInfoDTO);
        try {
            String responseStr = apigwHttpClientService.doHttpGet(url);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("list role group templates response|{}", responseStr);
                ResponseDTO<List<RoleGroupMemberInfo>> responseInfo = JsonUtil.fromJson(
                    responseStr,
                    new TypeReference<ResponseDTO<List<RoleGroupMemberInfo>>>() {
                    });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData();
                }
            } else {
                log.warn("list role group templates got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("list role group templates failed", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void renewalRoleGroupMemberV2(Integer groupId, ManagerMemberGroupDTO managerMemberGroupDTO) {
        try {
            AuthRequestContext.setRequestName("V2_MANAGER_ROLE_GROUP_MEMBER_RENEWAL");
            String responseStr = apigwHttpClientService.doHttpPut(String.format(V2IamUri.V2_MANAGER_ROLE_GROUP_MEMBER_RENEWAL, iamConfiguration.getSystemId(), groupId), managerMemberGroupDTO);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("renewal v2 manager role group response|{}", responseStr);
                ResponseDTO<Object> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                }
            } else {
                log.warn("renewal v2 manager role group got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("renewal v2 manager role group failed", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void renewalRoleGroupMemberApplication(GroupMemberRenewApplicationDTO groupMemberRenewApplicationDTO) {
        try {
            AuthRequestContext.setRequestName("V2_GROUPS_RENEW_APPLICATIONS");
            String url = String.format(V2IamUri.V2_GROUPS_RENEW_APPLICATIONS, iamConfiguration.getSystemId());
            String responseStr = apigwHttpClientService.doHttpPost(url, groupMemberRenewApplicationDTO);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("renewal role group member application response|{}", responseStr);
                ResponseDTO<Object> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                }
            } else {
                log.warn("renewal role group member application got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("renewal role group member application failed", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public MemberGroupDetailsResponse listMemberGroupsDetails(
        String gradeManagerId,
        ManagerScopesEnum memberType,
        String memberId,
        String groupIds
    ) {
        try {
            AuthRequestContext.setRequestName("V2_MEMBER_GROUPS_DETAILS_LIST");
            String url = String.format(V2IamUri.V2_MEMBER_GROUP_DETAILS_GET,
                iamConfiguration.getSystemId(),
                gradeManagerId,
                ManagerScopesEnum.getType(memberType),
                memberId,
                groupIds
            );
            String responseStr = apigwHttpClientService.doHttpGet(url);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("list member groups details response|{}", responseStr);
                ResponseDTO<MemberGroupDetailsResponse> responseInfo =
                    JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<MemberGroupDetailsResponse>>() {
                    });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData();
                }
            } else {
                log.warn("list member groups details got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("list member groups details failed", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<GroupAction> getRoleGroupActionV2(Integer groupId) {
        AuthRequestContext.setRequestName("V2_MANAGER_ROLE_GROUP_ACTION_GET");
        String url = String.format(V2IamUri.V2_MANAGER_ROLE_GROUP_ACTION_GET, iamConfiguration.getSystemId(), groupId);
        try {
            String responseStr = apigwHttpClientService.doHttpGet(url);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("get V2 role group action response|{}", responseStr);
                ResponseDTO<List<GroupAction>> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<List<GroupAction>>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData();
                }
            } else {
                log.warn("get V2 role group action got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("get V2 role group action failed", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<GroupPermissionDetailResponseDTO> getGroupPermissionDetail(Integer groupId) {
        return getGroupPermissionDetail(groupId, iamConfiguration.getSystemId());
    }

    @Override
    public List<GroupPermissionDetailResponseDTO> getGroupPermissionDetail(Integer groupId, String systemId) {
        AuthRequestContext.setRequestName("V2_MANAGER_ROLE_GROUP_PERMISSION_DETAIL_GET");
        String url = String.format(V2IamUri.V2_MANAGER_ROLE_GROUP_PERMISSION_DETAIL_GET, systemId, groupId);
        try {
            String responseStr = apigwHttpClientService.doHttpGet(url);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("get Group Permission Detail response|{}", responseStr);
                ResponseDTO<List<GroupPermissionDetailResponseDTO>> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<List<GroupPermissionDetailResponseDTO>>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData();
                }
            } else {
                log.warn("get Group Permission Detail got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("get Group Permission Detail failed", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Map<Integer, GroupMemberVerifyInfo> verifyGroupValidMember(String userId, String groupIds) {
        try {
            AuthRequestContext.setRequestName("V2_VERIFY_GROUP_VALID_MEMBER");
            String url = String.format(V2IamUri.V2_VERIFY_GROUP_VALID_MEMBER, iamConfiguration.getSystemId(), userId, groupIds);
            String responseStr = apigwHttpClientService.doHttpGet(url);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("verify group valid member response|{}", responseStr);
                ResponseDTO<Map<Integer, GroupMemberVerifyInfo>> responseInfo =
                    JsonUtil.fromJson(responseStr,
                        new TypeReference<ResponseDTO<Map<Integer, GroupMemberVerifyInfo>>>() {
                        });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData();
                }
            } else {
                log.warn("verify group valid member got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("verify group valid member failed", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Map<Integer, GroupMemberVerifyInfo> verifyGroupValidDepartment(String departmentId, String groupIds) {
        try {
            AuthRequestContext.setRequestName("V2_VERIFY_GROUP_VALID_DEPARTMENT");
            String url = String.format(V2IamUri.V2_VERIFY_GROUP_VALID_DEPARTMENT, iamConfiguration.getSystemId(), departmentId, groupIds);
            String responseStr = apigwHttpClientService.doHttpGet(url);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("verify group valid department response|{}", responseStr);
                ResponseDTO<Map<Integer, GroupMemberVerifyInfo>> responseInfo =
                    JsonUtil.fromJson(responseStr,
                        new TypeReference<ResponseDTO<Map<Integer, GroupMemberVerifyInfo>>>() {
                        });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData();
                }
            } else {
                log.warn("verify group valid department got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("verify group valid department failed", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Integer createSubsetManager(String gradeManagerId, CreateSubsetManagerDTO createSubsetManagerDTO) throws IOException {
        try {
            AuthRequestContext.setRequestName("V2_SUBSET_GRADE_MANAGER_CREATE");
            String url = String.format(V2IamUri.V2_SUBSET_GRADE_MANAGER_CREATE, iamConfiguration.getSystemId(), gradeManagerId);
            String responseStr = apigwHttpClientService.doHttpPost(url, createSubsetManagerDTO);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("create subset manager response|{}", responseStr);
                ResponseDTO<CreateVo> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<CreateVo>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData().getId();
                }
            } else {
                log.warn("create subset manager got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("create subset manager failed", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Integer batchCreateSubsetRoleGroup(Integer subsetManagerId, ManagerRoleGroupDTO managerRoleGroupDTO) {
        try {
            AuthRequestContext.setRequestName("V2_SUBSET_GRADE_MANAGER_GROUP_CREATE");
            String url = String.format(V2IamUri.V2_SUBSET_GRADE_MANAGER_GROUP_CREATE, iamConfiguration.getSystemId(), subsetManagerId);
            String responseStr = apigwHttpClientService.doHttpPost(url, managerRoleGroupDTO);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("create subset manager role group response|{}", responseStr);
                ResponseDTO<List<Integer>> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<List<Integer>>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData().get(0);
                }
            } else {
                log.warn("create subset manager role group got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("create subset manager role group failed", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public V2ManagerRoleGroupVO getSubsetManagerRoleGroup(Integer subsetManagerId, V2PageInfoDTO pageInfoDTO) {
        try {
            AuthRequestContext.setRequestName("V2_SUBSET_GRADE_MANAGER_GROUP_GET");
            String responseStr = apigwHttpClientService.doHttpGet(v2BuildURLPage(
                String.format(V2IamUri.V2_SUBSET_GRADE_MANAGER_GROUP_GET, iamConfiguration.getSystemId(),
                    subsetManagerId.toString()), pageInfoDTO));
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("get subeset manager role group response|{}", responseStr);
                ResponseDTO<V2ManagerRoleGroupVO> responseInfo =
                    JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<V2ManagerRoleGroupVO>>() {
                    });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData();
                }
            } else {
                log.warn("get subeset manager role group got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("get subeset manager role group failed", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ManagerDetailResponse getGradeManagerDetail(String gradeManagerId) {
        try {
            AuthRequestContext.setRequestName("V2_MANAGER_ROLE_DETAIL_GET");
            String responseStr = apigwHttpClientService.doHttpGet(
                String.format(V2IamUri.V2_MANAGER_ROLE_DETAIL_GET, iamConfiguration.getSystemId(), gradeManagerId));
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("get subeset manager role group response|{}", responseStr);
                ResponseDTO<ManagerDetailResponse> responseInfo =
                    JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<ManagerDetailResponse>>() {
                    });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData();
                }
            } else {
                log.warn("get grade manager detail got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("get grade manager detail failed", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public V2ManagerRoleGroupVO getGradeManagerRoleGroupV2(String gradeManagerId, SearchGroupDTO searchGroupDTO, V2PageInfoDTO pageInfoDTO) {
        try {
            AuthRequestContext.setRequestName("V2_MANAGER_GRADE_GROUP_GET");
            String url = v2BuildURLPage(String.format(V2IamUri.V2_MANAGER_GRADE_GROUP_GET, iamConfiguration.getSystemId(), gradeManagerId), pageInfoDTO);
            if (searchGroupDTO != null) {
                String s = HttpUtils.joinParams(searchGroupDTO);
                if (StringUtils.isNotBlank(s))
                    url = url.concat("&".concat(s));
            }
            String responseStr = apigwHttpClientService.doHttpGet(url);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("get subeset manager role group response|{}", responseStr);
                ResponseDTO<V2ManagerRoleGroupVO> responseInfo =
                    JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<V2ManagerRoleGroupVO>>() {
                    });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData();
                }
            } else {
                log.warn("get subeset manager role group got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("get subeset manager role group failed", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public SubjectTemplateVO getGradeManagerRoleTemplate(String gradeManagerId, SearchTemplatesDTO searchTemplatesDTO, V2PageInfoDTO pageInfoDTO) {
        try {
            AuthRequestContext.setRequestName("V2_MANAGER_GRADE_TEMPLATE_GET");
            String url = v2BuildURLPage(String.format(V2IamUri.V2_MANAGER_GRADE_TEMPLATE_GET, iamConfiguration.getSystemId(), gradeManagerId), pageInfoDTO);
            if (searchTemplatesDTO != null) {
                String s = HttpUtils.joinParams(searchTemplatesDTO);
                if (StringUtils.isNotBlank(s))
                    url = url.concat("&".concat(s));
            }
            String responseStr = apigwHttpClientService.doHttpGet(url);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("get grade manager role template response|{}", responseStr);
                ResponseDTO<SubjectTemplateVO> responseInfo =
                    JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<SubjectTemplateVO>>() {
                    });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData();
                }
            } else {
                log.warn("get grade manager role template response got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("get grade manager role template response failed", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public GradeManagerApplicationResponse createGradeManagerApplication(GradeManagerApplicationCreateDTO gradeManagerApplicationCreateDTO) {
        try {
            AuthRequestContext.setRequestName("V2_GRADE_MANAGER_APPLICATION_CREATE");
            log.info("iam-sdk gradeManagerApplicationCreateDTO : {}", gradeManagerApplicationCreateDTO);
            String url = String.format(V2IamUri.V2_GRADE_MANAGER_APPLICATION_CREATE, iamConfiguration.getSystemId());
            String responseStr = apigwHttpClientService.doHttpPost(url, gradeManagerApplicationCreateDTO);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("create grade manager application response|{}", responseStr);
                ResponseDTO<GradeManagerApplicationResponse> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<GradeManagerApplicationResponse>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData();
                }
            } else {
                log.warn("create grade manager application got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("create grade manager application failed", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public GradeManagerApplicationResponse updateGradeManagerApplication(String gradeManagerId, GradeManagerApplicationUpdateDTO gradeManagerApplicationUpdateDTO) {
        try {
            AuthRequestContext.setRequestName("V2_GRADE_MANAGER_APPLICATION_UPDATE");
            String url = String.format(V2IamUri.V2_GRADE_MANAGER_APPLICATION_UPDATE, iamConfiguration.getSystemId(), gradeManagerId);
            String responseStr = apigwHttpClientService.doHttpPost(url, gradeManagerApplicationUpdateDTO);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("update grade manager application response|{}", responseStr);
                ResponseDTO<GradeManagerApplicationResponse> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<GradeManagerApplicationResponse>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData();
                }
            } else {
                log.warn("update grade manager application got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("update grade manager application failed", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Integer createManagerV2(CreateManagerDTO createManagerDTO) throws IOException {
        try {
            AuthRequestContext.setRequestName("V2_MANAGER_ROLE_CREATE");
            String url = String.format(V2IamUri.V2_MANAGER_ROLE_CREATE, iamConfiguration.getSystemId());
            String responseStr = apigwHttpClientService.doHttpPost(url, createManagerDTO);
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
            throw iamException;
        } catch (Exception e) {
            log.error("create manager failed", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void updateManagerV2(String gradeManagerId, UpdateManagerDTO updateManagerDTO) {
        try {
            AuthRequestContext.setRequestName("V2_MANAGER_ROLE_UPDATE");
            String responseStr = apigwHttpClientService.doHttpPut(String.format(V2IamUri.V2_MANAGER_ROLE_UPDATE, iamConfiguration.getSystemId(), gradeManagerId), updateManagerDTO);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("update manager response|{}", responseStr);
                ResponseDTO<Object> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                }
            } else {
                log.warn("update manager got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("update manager failed", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteManagerV2(String gradeManagerId) {
        try {
            AuthRequestContext.setRequestName("V2_MANAGER_ROLE_DELETE");
            String responseStr = apigwHttpClientService.doHttpDelete(String.format(V2IamUri.V2_MANAGER_ROLE_DELETE, iamConfiguration.getSystemId(), gradeManagerId));
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("delete V2 grade manager response|{}", responseStr);
                ResponseDTO<Object> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                }
            } else {
                log.warn("delete V2 grade manager got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("delete V2 grade manager failed", e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public CallbackApplicationResponese handleCallbackApplication(String callbackId, CallbackApplicationDTO callbackApplicationDTO) {
        try {
            AuthRequestContext.setRequestName("V2_CALLBACK_APPLICATION_HANDLE");
            String url = String.format(V2IamUri.V2_CALLBACK_APPLICATION_HANDLE, iamConfiguration.getSystemId(), callbackId);
            String responseStr = apigwHttpClientService.doHttpPost(url, callbackApplicationDTO);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("handle callback application response|{}", responseStr);
                ResponseDTO<CallbackApplicationResponese> responseInfo =
                    JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<CallbackApplicationResponese>>() {
                    });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData();
                }
            } else {
                log.warn("handle callback application got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("handle callback application failed", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Boolean cancelCallbackApplication(String callbackId) {
        try {
            AuthRequestContext.setRequestName("V2_CALLBACK_APPLICATION_CANCEL");
            String responseStr = apigwHttpClientService.doHttpPut(String.format(V2IamUri.V2_CALLBACK_APPLICATION_CANCEL, iamConfiguration.getSystemId(), callbackId));
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("cancelCallbackApplication|{}", responseStr);
                ResponseDTO<Object> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                }
                if (StringUtils.isNotBlank(responseInfo.getMessage()) && responseInfo.getMessage().equals("OK"))
                    return true;
            } else {
                log.warn("cancelCallbackApplication got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("cancelCallbackApplication error", e);
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public ApplicationVO createRoleGroupApplicationV2(ApplicationDTO applicationDTO) {
        try {
            AuthRequestContext.setRequestName("V2_MANAGER_ROLE_GROUP_APPLICATIONS_CREATE");
            String url = String.format(V2IamUri.V2_MANAGER_ROLE_GROUP_APPLICATIONS_CREATE, iamConfiguration.getSystemId());
            String responseStr = apigwHttpClientService.doHttpPost(url, applicationDTO);
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("create v2  role group application response|{}", responseStr);
                ResponseDTO<ApplicationVO> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<ApplicationVO>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData();
                }
            } else {
                log.warn("create v2  role group application got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("create v2  role group application failed", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ManagerDetailResponse getSubsetManagerDetail(String subsetManagerId) {
        try {
            AuthRequestContext.setRequestName("V2_SUBSET_GRADE_MANAGER_DETAIL_GET");
            String responseStr = apigwHttpClientService.doHttpGet(
                String.format(V2IamUri.V2_SUBSET_GRADE_MANAGER_DETAIL_GET, iamConfiguration.getSystemId(), subsetManagerId));
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("get subeset manager detail response|{}", responseStr);
                ResponseDTO<ManagerDetailResponse> responseInfo =
                    JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<ManagerDetailResponse>>() {
                    });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                    return responseInfo.getData();
                }
            } else {
                log.warn("get subeset manager detail got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("get subeset manager detail failed", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void updateSubsetManager(String subsetManagerId, UpdateSubsetManagerDTO updateSubsetManagerDTO) {
        try {
            AuthRequestContext.setRequestName("V2_SUBSET_GRADE_MANAGER_UPDATE");
            String url = String.format(V2IamUri.V2_SUBSET_GRADE_MANAGER_UPDATE, iamConfiguration.getSystemId(), subsetManagerId);
            String responseStr = apigwHttpClientService.doHttpPost(url, updateSubsetManagerDTO);
            if (StringUtils.isNotBlank(responseStr)) {
                System.out.println(responseStr);
                log.debug("update subset manager response|{}", responseStr);
                ResponseDTO<Object> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                }
            } else {
                log.warn("update subset manager got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("update subset manager failed", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteSubsetManager(String subsetManagerId) {
        try {
            AuthRequestContext.setRequestName("V2_SUBSET_GRADE_MANAGER_DELETE");
            String responseStr = apigwHttpClientService.doHttpDelete(String.format(V2IamUri.V2_SUBSET_GRADE_MANAGER_DELETE, iamConfiguration.getSystemId(), subsetManagerId));
            if (StringUtils.isNotBlank(responseStr)) {
                log.debug("delete V2 subset manager response|{}", responseStr);
                System.out.println("delete V2 subset manager response|" + responseStr);
                ResponseDTO<Object> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<Object>>() {
                });
                if (responseInfo != null) {
                    ResponseUtil.checkResponse(responseInfo);
                }
            } else {
                log.warn("delete V2 subset manager got empty response!");
            }
        } catch (IamException iamException) {
            throw iamException;
        } catch (Exception e) {
            log.error("delete V2 subset manager failed", e);
            throw new RuntimeException(e);
        }
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
