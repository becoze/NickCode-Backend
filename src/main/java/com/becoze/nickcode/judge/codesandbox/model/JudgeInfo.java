package com.becoze.nickcode.judge.codesandbox.model;

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
