package com.tencent.bk.sdk.iam.service;

import com.tencent.bk.sdk.iam.dto.SelectionDTO;
import com.tencent.bk.sdk.iam.dto.resource.ResourceTypeDTO;

import java.util.List;

public interface IamResourceService {
    /**
     * 检查资源是否存在
     */
    boolean resourceCheck(String resourceType);
    /**
     * 创建资源
     */
    boolean createResource(List<ResourceTypeDTO> resourceTypeDTO);
    /**
     * 修改资源
     */
    boolean updateResource(ResourceTypeDTO resourceTypeDTO, String resourceId);
    /**
     * 获取系统资源实例视图
     */
    List<SelectionDTO> getSystemInstanceSelector();
    /**
     * 创建系统资源实例视图
     */
    boolean createResourceInstanceSelector(List<SelectionDTO> instanceSelector);
    /**
     * 修改系统资源实例视图
     */
    boolean updateResourceInstanceSelector(String selectionId, SelectionDTO instanceSelector);

}
