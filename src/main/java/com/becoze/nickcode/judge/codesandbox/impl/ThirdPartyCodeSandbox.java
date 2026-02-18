package com.becoze.nickcode.judge.codesandbox.impl;
import com.becoze.nickcode.judge.codesandbox.CodeSandbox;
import com.becoze.nickcode.judge.codesandbox.model.ExecuteCodeRequest;
import com.becoze.nickcode.judge.codesandbox.model.ExecuteCodeResponse;

public class ThirdPartyCodeSandbox implements CodeSandbox {
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("ThirdPartyCodeSandbox");
        return null;
    }
}
