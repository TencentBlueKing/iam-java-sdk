package com.tencent.bk.sdk.iam.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseData<T> {
    Integer count;
    List<T> results;
}
