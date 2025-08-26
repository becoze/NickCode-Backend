package com.becoze.nickcode.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * Problem
 * @TableName problem
 */
@TableName(value ="problem")
@Data
public class Problem implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
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
     * Judge Case (JSON array)
     */
    private String judgeCase;

    /**
     * Judge Configuration (JSON Object)
     */
    private String judgeConfig;

    /**
     * Id of the creator user
     */
    private String creatorId;

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
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}