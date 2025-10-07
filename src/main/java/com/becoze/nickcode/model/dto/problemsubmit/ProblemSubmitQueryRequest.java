package com.becoze.nickcode.model.dto.problemsubmit;

import com.becoze.nickcode.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Create new submitted-problem request
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProblemSubmitQueryRequest extends PageRequest implements Serializable {

    /**
     * Programming language
     */
    private String language;

    /**
     * Code submitted by user
     */
    private Integer status;

    /**
     * Problem id
     */
    private Long problemId;

    /**
     * User id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}