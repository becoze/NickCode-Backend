package com.becoze.nickcode.judge;

import cn.hutool.json.JSONUtil;
import com.becoze.nickcode.common.ErrorCode;
import com.becoze.nickcode.exception.BusinessException;
import com.becoze.nickcode.judge.codesandbox.CodeSandbox;
import com.becoze.nickcode.judge.codesandbox.CodeSandboxFactory;
import com.becoze.nickcode.judge.codesandbox.CodeSandboxProxy;
import com.becoze.nickcode.judge.codesandbox.model.ExecuteCodeRequest;
import com.becoze.nickcode.judge.codesandbox.model.ExecuteCodeResponse;
import com.becoze.nickcode.judge.strategy.JudgeContext;
import com.becoze.nickcode.model.dto.problem.JudgeCase;
import com.becoze.nickcode.judge.codesandbox.model.JudgeInfo;
import com.becoze.nickcode.model.entity.Problem;
import com.becoze.nickcode.model.entity.ProblemSubmit;
import com.becoze.nickcode.model.enums.ProblemSubmitStatusEnum;
import com.becoze.nickcode.service.ProblemService;
import com.becoze.nickcode.service.ProblemSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {
    @Resource
    private ProblemService problemService;

    @Resource
    private ProblemSubmitService problemSubmitService;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.codeSandboxName:DemoCodeSandbox}")
    private String codeSandboxName;

    @Override
    /**
     * Processes a problem submission for judging
     * This method handles the entire judging workflow from initial submission to processing
     *
     * @param problemSubmitId The ID of the submission to be judged
     * @return ProblemSubmitVO containing the judging result (currently returns null)
     * @throws BusinessException if submission not found, problem doesn't exist, or submission already judged
     */
    public ProblemSubmit doJudge(long problemSubmitId) {
        // Input problemSubmitId, and gather all information of problemSubmit
        ProblemSubmit problemSubmit = problemSubmitService.getById(problemSubmitId);
        if (problemSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "Submission not found");
        }
        Long problemID = problemSubmit.getProblemId();
        Problem problem = problemService.getById(problemID);
        if (problem == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "Problem not exist");
        }
        // Check if submission is already processed
        if (!problemSubmit.getStatus().equals(ProblemSubmitStatusEnum.PENDING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "Submission has been judged");
        }
        // Update submission status to "processing"
        ProblemSubmit problemSubmitUpdate = new ProblemSubmit();
        problemSubmitUpdate.setId(problemSubmitId);
        problemSubmitUpdate.setStatus(ProblemSubmitStatusEnum.PROCESSING.getValue());
        boolean update = problemSubmitService.updateById(problemSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Failed to update submission status");
        }

        // Execute code using specific sandbox by config
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(codeSandboxName);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        // Gather submission language and code information
        String language = problemSubmit.getLanguage();
        String code = problemSubmit.getCode();
        String judgeCaseStr = problem.getJudgeCase();
        // Gather judge case information
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        // Wrap everything up into a request
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();

        // Form "JudgeContext" data set and judge using the "JudgeStrategy"
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setProblem(problem);
        judgeContext.setProblemSubmit(problemSubmit);

        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);

        // Update database and submission status to "succeed"
        problemSubmitUpdate = new ProblemSubmit();
        problemSubmitUpdate.setId(problemSubmitId);
        problemSubmitUpdate.setStatus(ProblemSubmitStatusEnum.SUCCEED.getValue());
        problemSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = problemSubmitService.updateById(problemSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Failed to update submission status");
        }

        return problemSubmitService.getById(problemID);
    }
}
