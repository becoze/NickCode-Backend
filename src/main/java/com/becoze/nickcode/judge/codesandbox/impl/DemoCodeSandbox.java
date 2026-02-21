package com.becoze.nickcode.judge.codesandbox.impl;
import com.becoze.nickcode.judge.codesandbox.CodeSandbox;
import com.becoze.nickcode.judge.codesandbox.model.ExecuteCodeRequest;
import com.becoze.nickcode.judge.codesandbox.model.ExecuteCodeResponse;
import com.becoze.nickcode.judge.codesandbox.model.JudgeInfo;
import com.becoze.nickcode.model.enums.JudgeInfoMessageEnum;
import com.becoze.nickcode.model.enums.ProblemSubmitStatusEnum;

import java.util.List;

public class DemoCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("Success");
        executeCodeResponse.setStatus(ProblemSubmitStatusEnum.SUCCEED.getValue());

        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
