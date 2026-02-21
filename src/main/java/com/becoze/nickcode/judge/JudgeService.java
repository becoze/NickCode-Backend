package com.becoze.nickcode.judge;

import com.becoze.nickcode.model.entity.ProblemSubmit;
import com.becoze.nickcode.model.vo.ProblemSubmitVO;

public interface JudgeService {

    /**
     *
     * @param problemId
     * @return
     */
    ProblemSubmit doJudge(long problemId);
}
