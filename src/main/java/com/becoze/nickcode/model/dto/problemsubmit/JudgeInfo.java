package com.becoze.nickcode.model.dto.problemsubmit;

import lombok.Data;

/**
 * Judge result info
 */
@Data
public class JudgeInfo {
    /**
     * Return message
     */
    private String message;

    /**
     * Memory used (KB)
     */
    private Long memory;

    /**
     * Time used (s)
     */
    private Long time;

}
