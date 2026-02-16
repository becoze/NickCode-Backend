package com.becoze.nickcode.judeg.codesandbox.model;

import com.becoze.nickcode.model.dto.problem.JudgeConfig;
import com.becoze.nickcode.model.dto.problemsubmit.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeResponse {

    private List<String> outputList;

    /**
     * message regarding this API, different from JudgeInfo - String message
     */
    private String message;

    private Integer status;

    /**
     * contain Long time, Long memory, String message
     */
    private JudgeInfo judgeInfo;
}
