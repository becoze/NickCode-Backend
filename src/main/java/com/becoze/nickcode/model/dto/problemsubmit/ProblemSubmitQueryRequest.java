package com.becoze.nickcode.model.dto.problemsubmit;

import lombok.Data;

import java.io.Serializable;

/**
 * Create new problem request
 *
 */
@Data
public class ProblemSubmitAddRequest implements Serializable {

    /**
     * Used programming language
     */
    private String language;

    /**
     * Submit user code
     */
    private String code;

    /**
     * Problem id
     */
    private Long problemId;

    private static final long serialVersionUID = 1L;
}