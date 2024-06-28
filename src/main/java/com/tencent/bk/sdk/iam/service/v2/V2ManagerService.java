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

package com.tencent.bk.sdk.iam.service.v2;

import com.tencent.bk.sdk.iam.constants.ManagerScopesEnum;
import com.tencent.bk.sdk.iam.dto.CallbackApplicationDTO;
import com.tencent.bk.sdk.iam.dto.GradeManagerApplicationCreateDTO;
import com.tencent.bk.sdk.iam.dto.GradeManagerApplicationUpdateDTO;
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
import com.tencent.bk.sdk.iam.dto.manager.vo.ManagerGroupMemberVo;
import com.tencent.bk.sdk.iam.dto.manager.vo.SubjectTemplateVO;
import com.tencent.bk.sdk.iam.dto.manager.vo.V2ManagerRoleGroupVO;
import com.tencent.bk.sdk.iam.dto.response.CallbackApplicationResponese;
import com.tencent.bk.sdk.iam.dto.response.GradeManagerApplicationResponse;
import com.tencent.bk.sdk.iam.dto.response.GroupPermissionDetailResponseDTO;
import com.tencent.bk.sdk.iam.dto.response.ManagerDetailResponse;
import com.tencent.bk.sdk.iam.dto.response.MemberGroupDetailsResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

// v2类接口使用于RBAC权限;对于ABAC权限，使用v1接口
public interface V2ManagerService {
    //----------------二级管理员相关接口----------------

    /**
     * 创建二级管理员
     */
    public Integer createSubsetManager(String gradeManagerId, CreateSubsetManagerDTO createSubsetManagerDTO) throws IOException;

    /**
     * 二级管理员下批量创建用户组
     */
    public Integer batchCreateSubsetRoleGroup(Integer subsetManagerId, ManagerRoleGroupDTO managerRoleGroupDTO);

    /**
     * 查询二级管理员下用户组列表
     */
    public V2ManagerRoleGroupVO getSubsetManagerRoleGroup(Integer subsetManagerId, V2PageInfoDTO pageInfoDTO);

    /**
     * 查询二级管理员详情
     *
     * @param subsetManagerId 二级管理员ID
     */
    public ManagerDetailResponse getSubsetManagerDetail(String subsetManagerId);

    /**
     * 更新二级管理员
     *
     * @param subsetManagerId        二级管理员ID
     * @param updateSubsetManagerDTO
     */
    public void updateSubsetManager(String subsetManagerId, UpdateSubsetManagerDTO updateSubsetManagerDTO);

    /**
     * 删除二级管理员
     *
     * @param subsetManagerId 二级管理员ID
     */
    void deleteSubsetManager(String subsetManagerId);

    //----------------分级管理员相关接口----------------

    /**
     * 查询分级管理员详情
     */
    public ManagerDetailResponse getGradeManagerDetail(String gradeManagerId);

    /**
     * v2版本
     * 查询分级管理员下用户组列表
     */
    public V2ManagerRoleGroupVO getGradeManagerRoleGroupV2(String gradeManagerId, SearchGroupDTO searchGroupDTO, V2PageInfoDTO pageInfoDTO);

    /**
     * 查询分级管理员下人员模板列表
     */
    public SubjectTemplateVO getGradeManagerRoleTemplate(String gradeManagerId, SearchTemplatesDTO searchTemplatesDTO, V2PageInfoDTO pageInfoDTO);

    /**
     * 创建分级管理员申请
     */
    public GradeManagerApplicationResponse createGradeManagerApplication(GradeManagerApplicationCreateDTO gradeManagerApplicationCreateDTO);

    /**
     * 修改分级管理员申请
     */
    public GradeManagerApplicationResponse updateGradeManagerApplication(String gradeManagerId,
                                                                         GradeManagerApplicationUpdateDTO gradeManagerApplicationUpdateDTO);

    /**
     * 直接创建分级管理员
     **/
    public Integer createManagerV2(CreateManagerDTO createManagerDTO) throws IOException;

    /**
     * 直接更新分级管理员
     *
     * @return
     */
    public void updateManagerV2(String gradeManagerId, UpdateManagerDTO updateManagerDTO);

    /**
     * 删除分级管理员
     *
     * @param gradeManagerId 分级管理员ID
     */
    void deleteManagerV2(String gradeManagerId);

    //----------------审批单相关接口----------------

    /**
     * 处理申请单审批状态通知回调
     */
    public CallbackApplicationResponese handleCallbackApplication(String callbackId, CallbackApplicationDTO callbackApplicationDTO);

    /**
     * 取消申请单审批
     */
    public Boolean cancelCallbackApplication(String callbackId);

    //----------------用户组相关接口-----------------

    /**
     * 创建用户组申请单据
     */
    public ApplicationVO createRoleGroupApplicationV2(ApplicationDTO applicationDTO);

    /**
     * 批量创建用户组
     */
    public Integer batchCreateRoleGroupV2(Integer gradeManagerId, ManagerRoleGroupDTO managerRoleGroupDTO);

    /**
     * 更新用户组
     */
    public void updateRoleGroupV2(Integer groupId, ManagerRoleGroup managerRoleGroup);

    /**
     * 删除用户组
     */
    public void deleteRoleGroupV2(Integer groupId);

    /**
     * 用户组授权
     */
    public void grantRoleGroupV2(Integer groupId, AuthorizationScopes authorizationScopes);

    /**
     * 用户组添加成员
     */
    public void createRoleGroupMemberV2(Integer groupId, ManagerMemberGroupDTO managerMemberGroupDTO);

    /**
     * 用户组删除成员
     */
    public void deleteRoleGroupMemberV2(Integer groupId, String type, String members);

    /**
     * 用户组成员列表
     */
    public ManagerGroupMemberVo getRoleGroupMemberV2(Integer groupId, V2PageInfoDTO pageInfoDTO);

    /**
     * 用户组下模板列表
     */
    public List<RoleGroupMemberInfo> listRoleGroupTemplates(Integer groupId, V2PageInfoDTO pageInfoDTO);

    /**
     * 用户组成员续期(不需要审批版本)
     */
    public void renewalRoleGroupMemberV2(Integer groupId, ManagerMemberGroupDTO managerMemberGroupDTO);

    /**
     * 用户组成员续期(需要审批版本)
     */
    public void renewalRoleGroupMemberApplication(GroupMemberRenewApplicationDTO groupMemberRenewApplicationDTO);

    /**
     * 批量查询用户/组织/人员模板加入的用户组详情
     */
    public MemberGroupDetailsResponse listMemberGroupsDetails(String gradeManagerId, ManagerScopesEnum memberType, String memberId, String groupIds);

    /**
     * 查询用户组有权限的Action列表
     */
    public List<GroupAction> getRoleGroupActionV2(Integer groupId);

    /**
     * 查询用户组权限详情
     */
    public List<GroupPermissionDetailResponseDTO> getGroupPermissionDetail(Integer groupId);

    /**
     * 查询用户组权限详情,需传递systemId
     */
    public List<GroupPermissionDetailResponseDTO> getGroupPermissionDetail(Integer groupId, String systemId);

    /**
     * 校验用户是否某个用户组的有效成员
     *
     * @param groupIds 1,2,3,4  多个用“,”分割
     */
    public Map<Integer, GroupMemberVerifyInfo> verifyGroupValidMember(String userId, String groupIds);

    /**
     * 校验部门是否某个用户组的有效成员
     */
    public Map<Integer, GroupMemberVerifyInfo> verifyGroupValidDepartment(String departmentId, String groupIds);
}
