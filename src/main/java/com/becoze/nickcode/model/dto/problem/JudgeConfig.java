package com.becoze.nickcode.model.dto.problem;

import lombok.Data;

/**
 * Judge Config
 */
@Data
public class JudgeConfig {
    /**
     * Time Limit (ms)
     */
    private Long timeLimit;

    /**
     * Memory Limit (KB)
     */
    private Long memoryLimit;

    /**
     * Stack Limit (KB)
     */
    private Long stackLimit;

}
