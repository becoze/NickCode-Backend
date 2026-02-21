package com.becoze.nickcode.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * Problem
 *
 * @TableName problem
 */
@TableName(value = "problem")
@Data
public class Problem implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
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
    private String tags;

    /**
     * Problem answer
     */
    private String answer;

    /**
     * Total submitted
     */
    private Integer submitNum;

    /**
     * Total accepted
     */
    private Integer acceptedNum;

    /**
     * Number of likes / thumb up
     */
    private Integer thumbNum;

    /**
     * Number of favour / collection
     */
    private Integer favourNum;

    /**
     * Judge Case (JSON array)
     */
    private String judgeCase;

    /**
     * Judge Configuration (JSON Object)
     */
    private String judgeConfig;

    /**
     * ID of the user
     */
    private Long userId;

    /**
     * Creation Time
     */
    private Date createTime;

    /**
     * Update Time
     */
    private Date updateTime;

    /**
     * Deleted Status
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}