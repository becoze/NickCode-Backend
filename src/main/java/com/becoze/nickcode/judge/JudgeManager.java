package com.becoze.nickcode.judge;

import com.becoze.nickcode.judge.strategy.DefaultJudgeStrategy;
import com.becoze.nickcode.judge.strategy.JavaLanguageJudgeStrategy;
import com.becoze.nickcode.judge.strategy.JudgeContext;
import com.becoze.nickcode.judge.strategy.JudgeStrategy;
import com.becoze.nickcode.judge.codesandbox.model.JudgeInfo;
import com.becoze.nickcode.model.entity.ProblemSubmit;
import org.springframework.stereotype.Service;

@Service
public class JudgeManager {
    JudgeInfo doJudge(JudgeContext judgeContext) {
        ProblemSubmit problemSubmit = judgeContext.getProblemSubmit();
        String language = problemSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
