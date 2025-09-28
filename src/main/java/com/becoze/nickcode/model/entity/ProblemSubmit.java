package com.becoze.nickcode.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * Problem Submit
 * @TableName problem_submit
 */
@TableName(value ="problem_submit")
@Data
public class ProblemSubmit implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * Used programming language
     */
    private String language;

    /**
     * Submit user code
     */
    private String code;

    /**
     * Judge result (JSON Object)
     */
    private String judgeInfo;

    /**
     * Judge status (0-pending, 1-processing, 2-success, 3-fail)
     */
    private Integer status;

    /**
     * Problem id
     */
    private Long problemId;

    /**
     * Id of the creator user
     */
    private Long userId;

    /**
     * Creation time
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