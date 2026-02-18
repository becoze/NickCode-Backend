package com.becoze.nickcode.judge.codesandbox;
import com.becoze.nickcode.judge.codesandbox.model.ExecuteCodeRequest;
import com.becoze.nickcode.judge.codesandbox.model.ExecuteCodeResponse;

public interface CodeSandbox {
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
