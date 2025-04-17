package com.tencent.bk.sdk.iam.util;

/**
 * 线程工具类
 */
public class ThreadUtil {
    /**
     * 租户ID
     */
    private final static ThreadLocal<String> TENANT_ID = new ThreadLocal<>();

    /**
     * 获取租户ID
     */
    public static String getTenantId() {
        return TENANT_ID.get();
    }

    /**
     * 设置租户ID
     */
    public static void setTenantId(String tenantId) {
        TENANT_ID.remove();
        if (tenantId == null) {
            return;
        }
        TENANT_ID.set(tenantId);
    }

    /**
     * 清除租户ID
     */
    public static void clearTenantId() {
        TENANT_ID.remove();
    }
}
