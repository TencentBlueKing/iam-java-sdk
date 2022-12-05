package com.tencent.bk.sdk.iam.dto.response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tencent.bk.sdk.iam.constants.IamUri;
import com.tencent.bk.sdk.iam.dto.manager.vo.CreateVo;
import com.tencent.bk.sdk.iam.dto.manager.vo.ManagerGroupMemberVo;
import com.tencent.bk.sdk.iam.dto.manager.vo.ManagerRoleGroupVO;
import com.tencent.bk.sdk.iam.util.JsonUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ResponseDTOTest {

    @SneakyThrows
    @Test
    public void responseDtotest() throws IOException {
        String responseStr = "{\"data\":{\"id\":10},\"result\":true,\"code\":0,\"message\":\"OK\"}";
        ResponseDTO<CreateVo> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<CreateVo>>() {
        });
        Assertions.assertEquals(10, responseInfo.getData().getId());
    }

    @Test
    public void stringTest() {
        Integer roleId = 10;
        String url = String.format(IamUri.MANAGER_ROLE_GROUP_UPDATE, roleId.toString());
    }

    @SneakyThrows
    @Test
    public void responsetest() throws IOException {
        String responseStr = "{\"data\":{\"count\":8,\"result\":[{\"id\":20,\"name\":\"iamV3test-041909-qc\",\"description\":\"iamV3test-041909: default group qc\"},{\"id\":19,\"name\":\"iamV3test-041909-pm\",\"description\":\"iamV3test-041909: default group pm\"},{\"id\":18,\"name\":\"iamV3test-041909-tester\",\"description\":\"iamV3test-041909: default group tester\"},{\"id\":17,\"name\":\"iamV3test-041909-maintainer\",\"description\":\"iamV3test-041909: default group maintainer\"},{\"id\":16,\"name\":\"iamV3test-041909-developer\",\"description\":\"iamV3test-041909: default group developer\"},{\"id\":15,\"name\":\"iamV3test-041909-developer\",\"description\":\"iamV3test-041909: default group developer\"},{\"id\":14,\"name\":\"fitz测试用户组12345\",\"description\":\"fitz测试用户组大于141\"},{\"id\":13,\"name\":\"iamV3test-041909-管理员\",\"description\":\"iamV3test-041909: default group 管理员\"}]},\"result\":true,\"code\":0,\"message\":\"OK\"}";
        ResponseDTO<ManagerRoleGroupVO> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<ManagerRoleGroupVO>>() {
        });
    }

    @SneakyThrows
    @Test
    public void responsetest1() throws IOException {
        String responseStr = "{\"data\":{\"count\":1,\"results\":[{\"type\":\"user\",\"id\":\"jiananzhang\",\"name\":\"jiananzhang\",\"expired_at\":1650365974}]},\"result\":true,\"code\":0,\"message\":\"OK\"}";
        ResponseDTO<ManagerGroupMemberVo> responseInfo = JsonUtil.fromJson(responseStr, new TypeReference<ResponseDTO<ManagerGroupMemberVo>>() {});
    }

}
