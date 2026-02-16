package com.becoze.nickcode.judeg.codesandbox.impl;
import com.becoze.nickcode.judeg.codesandbox.CodeSandbox;
import com.becoze.nickcode.judeg.codesandbox.model.ExecuteCodeRequest;
import com.becoze.nickcode.judeg.codesandbox.model.ExecuteCodeResponse;

public class DemoCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("DemoCodeSandbox");
        return null;
    }
}
