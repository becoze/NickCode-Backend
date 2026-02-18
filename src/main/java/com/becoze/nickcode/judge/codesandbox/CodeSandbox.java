package com.becoze.nickcode.judeg.codesandbox;
import com.becoze.nickcode.judeg.codesandbox.model.ExecuteCodeRequest;
import com.becoze.nickcode.judeg.codesandbox.model.ExecuteCodeResponse;

public interface CodeSandbox {
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
