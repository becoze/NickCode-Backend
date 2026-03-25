package com.becoze.nickcode.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.becoze.nickcode.model.dto.problem.JudgeCase;
import com.becoze.nickcode.model.dto.problem.JudgeConfig;
import com.becoze.nickcode.judge.codesandbox.model.JudgeInfo;
import com.becoze.nickcode.model.entity.Problem;
import com.becoze.nickcode.model.enums.JudgeInfoMessageEnum;

import java.util.List;
import java.util.Optional;

/**
 * DefaultJudgeStrategy implements the JudgeStrategy interface to provide a default judging mechanism.
 * It evaluates user submissions by checking output correctness, execution time, and memory usage.
 */
public class JavaLanguageJudgeStrategy implements JudgeStrategy {
    /**
     * Performs the judging of a user's submission.
     *
     * @param judgeContext Contains all necessary information for judging including user results,
     *                     input/output lists, test cases, and problem details
     * @return JudgeInfo containing the judging result with appropriate status message
     */
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        // Extract user result information
        JudgeInfo userResult = judgeContext.getJudgeInfo();
        Long userTimeUsed = Optional.ofNullable(userResult.getTime()).orElse(0L);
        Long userMemoryUsed = Optional.ofNullable(userResult.getMemory()).orElse(0L);
        // Extract test case and input/output data
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        Problem problem = judgeContext.getProblem();
        // Initialize judge response with default accepted status
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setMemory(userMemoryUsed);
        judgeInfoResponse.setTime(userTimeUsed);

        // Check if the number of outputs matches the number of inputs
        if (outputList.size() != inputList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        // Check if each output matches the expected output
        for (int i = 0; i < judgeCaseList.size(); i++) {
            // JudgeCase judgeCase = judgeCaseList.get(i);  if (!judgeCase.getOutput().equals(outputList.get(i))) {}
            if (!outputList.get(i).equals(judgeCaseList.get(i).getOutput())) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfoResponse;
            }
        }

        // Check if the execution time and memory exceeds the limit
        String judgeConfigStr = problem.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        Long timeLimit = judgeConfig.getTimeLimit();
        Long memoryLimit = judgeConfig.getMemoryLimit();
        if (userTimeUsed > timeLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        if (userMemoryUsed > memoryLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }

        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfoResponse;
    }
}
