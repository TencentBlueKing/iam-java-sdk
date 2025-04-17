package com.tencent.bk.sdk.iam.service.decorator;

import com.tencent.bk.sdk.iam.util.ThreadUtil;

/**
 * 装饰器
 */
public class IamDecorator {
    public static <T extends IamService> T decorate(T origin, String tenantId) {
        ThreadUtil.setTenantId(tenantId);
        return origin;
    }
}
