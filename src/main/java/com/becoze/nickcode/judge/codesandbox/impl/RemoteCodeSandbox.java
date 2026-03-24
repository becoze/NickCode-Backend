package com.becoze.nickcode.judge.codesandbox.impl;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.becoze.nickcode.common.ErrorCode;
import com.becoze.nickcode.exception.BusinessException;
import com.becoze.nickcode.judge.codesandbox.CodeSandbox;
import com.becoze.nickcode.judge.codesandbox.model.ExecuteCodeRequest;
import com.becoze.nickcode.judge.codesandbox.model.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;

public class RemoteCodeSandbox implements CodeSandbox {
    // TODO Change to your own pass KEY!!!
    private static final String AUTH_REQUEST_KEY = "test";
    private static final String AUTH_REQUEST_HEADER = "auth";
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        String url = "http://localhost:8104/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_KEY)
                .body(json)
                .execute()
                .body();
        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "CodeSandbox executeCode error, message = " + responseStr);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
