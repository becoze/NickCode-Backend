package com.becoze.nickcode.judge.strategy;

import com.becoze.nickcode.judge.codesandbox.model.JudgeInfo;

public interface JudgeStrategy {
    JudgeInfo doJudge(JudgeContext judgeContext);
}
