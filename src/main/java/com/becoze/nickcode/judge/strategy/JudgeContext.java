package com.becoze.nickcode.judge.strategy;

import com.becoze.nickcode.model.dto.problem.JudgeCase;
import com.becoze.nickcode.judge.codesandbox.model.JudgeInfo;
import com.becoze.nickcode.model.entity.Problem;
import com.becoze.nickcode.model.entity.ProblemSubmit;
import lombok.Data;

import java.util.List;

/**
 * Maintain references to a judge strategy,
 * Store configuration data and state that needs to be shared across different components.
 */
@Data
public class JudgeContext {
    private JudgeInfo judgeInfo;
    private List<String> inputList;
    private List<String> outputList;
    private List<JudgeCase> judgeCaseList;
    private Problem problem;
    private ProblemSubmit problemSubmit;
}
