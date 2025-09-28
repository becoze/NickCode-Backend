package com.becoze.nickcode.model.dto.problem;

import com.becoze.nickcode.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 查询请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProblemQueryRequest extends PageRequest implements Serializable {

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
     * Id of the user user
     */
    private Long userId;


    private static final long serialVersionUID = 1L;
}