package com.becoze.nickcode.model.dto.problem;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 编辑请求
 */
@Data
public class ProblemEditRequest implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * Problem title
     */
    private String title;

    /**
     * Question content
     */
    private String content;

    /**
     * Problem tags (JSON array)
     */
    private List<String> tags;

    /**
     * Problem answer
     */
    private String answer;

    /**
     * Judge Case (JSON array)
     */
    private List<JudgeCase> judgeCase;

    /**
     * Judge Configuration (JSON Object)
     */
    private JudgeConfig judgeConfig;

    private static final long serialVersionUID = 1L;
}