package com.becoze.nickcode.model.enums;

import co.elastic.clients.elasticsearch.nodes.Ingest;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Problem Submit Enum
 *
 */
public enum ProblemSubmitStatusEnum {

    PENDING("Pending", 0),
    PROCESSING("Processing", 1),
    SUCCEED("Succeed", 2),
    FAILED("Failed",3);

    private final String text;

    private final Integer value;

    ProblemSubmitStatusEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    /**
     * get all values
     *
     * @return
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * get Enum By Value
     *
     * @param value
     * @return
     */
    public static ProblemSubmitStatusEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (ProblemSubmitStatusEnum anEnum : ProblemSubmitStatusEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
