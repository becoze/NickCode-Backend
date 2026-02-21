package com.becoze.nickcode.judge.codesandbox;

import com.becoze.nickcode.judge.codesandbox.model.ExecuteCodeRequest;
import com.becoze.nickcode.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeSandboxProxy implements CodeSandbox{
    private final CodeSandbox codeSandbox;

    public CodeSandboxProxy(CodeSandbox codeSandbox) {
        this.codeSandbox = codeSandbox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("Code sandbox request: " + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        log.info("Code sandbox response: " + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}

