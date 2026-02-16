package com.becoze.nickcode.judeg.codesandbox.impl;
import com.becoze.nickcode.judeg.codesandbox.CodeSandbox;
import com.becoze.nickcode.judeg.codesandbox.model.ExecuteCodeRequest;
import com.becoze.nickcode.judeg.codesandbox.model.ExecuteCodeResponse;

public class ThirdPartyCodeSandbox implements CodeSandbox {
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("ThirdPartyCodeSandbox");
        return null;
    }
}
