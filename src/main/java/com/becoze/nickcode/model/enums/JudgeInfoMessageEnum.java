package com.becoze.nickcode.model.enums;

import com.sun.jna.Memory;
import com.sun.org.apache.xalan.internal.xsltc.cmdline.Compile;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Problem Judge return message
 *
 */
public enum JudgeInfoMessageEnum {

    ACCEPTED("Accepted", "Accepted"),
    WRONG_ANSWER("Wrong Answer", "WrongAnswer"),
    COMPILE_ERROR("Compile Error", "CompileError"),
    MEMORY_LIMIT_EXCEEDED("Memory Limit Exceeded", "MemoryLimitExceeded"),
    TIME_LIMIT_EXCEEDED("Time Limit Exceeded", "TimeLimitExceeded"),
    OUTPUT_LIMIT_EXCEEDED("Output Limit Exceeded", "OutputLimitExceeded"),
    RUNNING("Running", "Running"),
    WAITING ("Waiting in Queue", "Waiting"),
    SECURITY_VIOLATION("Security Violation", "SecurityViolation"),
    RUNTIME_ERROR("Runtime Error", "RuntimeError"),
    SYSTEM_ERROR("System Error", "SystemError");


    private final String text;

    private final String value;

    JudgeInfoMessageEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static JudgeInfoMessageEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (JudgeInfoMessageEnum anEnum : JudgeInfoMessageEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
