package com.becoze.nickcode.model.vo;

import cn.hutool.json.JSONUtil;
import com.becoze.nickcode.judge.codesandbox.model.JudgeInfo;
import com.becoze.nickcode.model.entity.ProblemSubmit;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Problem Submit
 * VO (View Object)
 *
 * @TableName problem
 */
@Data
public class ProblemSubmitVO implements Serializable {
    /**
     * ID
     */
    private Long id;

    /**
     * Programming language
     */
    private String language;

    /**
     * Code submitted by user
     */
    private String code;

    /**
     * Judge result (JSON Object)
     */
    private JudgeInfo judgeInfo;

    /**
     * Judge status (0-pending, 1-processing, 2-success, 3-fail)
     */
    private Integer status;

    /**
     * Problem id
     */
    private Long problemId;

    /**
     * ID of the creator user
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
     * The User/owner who submit the problem
     */
    private UserVO userVO;

    /**
     * Problem
     */
    private ProblemVO problemVO;

    /**
     * convert Object to VO
     *
     * @param problemSubmitVO
     * @return
     */
    public static ProblemSubmit voToObj(ProblemSubmitVO problemSubmitVO) {
        if (problemSubmitVO == null) {
            return null;
        }

        ProblemSubmit problemSubmit = new ProblemSubmit();
        BeanUtils.copyProperties(problemSubmitVO, problemSubmit);
        JudgeInfo judgeInfoObj = problemSubmitVO.getJudgeInfo();
        if (judgeInfoObj != null) {
            problemSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfoObj));
        }
        return problemSubmit;
    }

    /**
     * convert VO to Object
     *
     * @param problemSubmit
     * @return
     */
    public static ProblemSubmitVO objToVo(ProblemSubmit problemSubmit) {
        if (problemSubmit == null) {
            return null;
        }
        ProblemSubmitVO problemSubmitVO = new ProblemSubmitVO();
        BeanUtils.copyProperties(problemSubmit, problemSubmitVO);
        String judgeInfoStr = problemSubmit.getJudgeInfo();
        problemSubmitVO.setJudgeInfo(JSONUtil.toBean(judgeInfoStr, JudgeInfo.class));
        return problemSubmitVO;
    }

    private static final long serialVersionUID = 1L;
}