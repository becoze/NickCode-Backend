package com.becoze.nickcode.model.dto.problemsubmit;

import lombok.Data;

import java.io.Serializable;

/**
 * Create new problem request
 * Dto (Data Transfer Object)
 */
@Data
public class ProblemSubmitAddRequest implements Serializable {

    /**
     * Programming language
     */
    private String language;

    /**
     * Code submitted by user
     */
    private String code;

    /**
     * Problem id
     */
    private Long problemId;

    private static final long serialVersionUID = 1L;
}