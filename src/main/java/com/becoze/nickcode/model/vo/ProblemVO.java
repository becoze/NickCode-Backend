package com.becoze.nickcode.model.vo;

import cn.hutool.json.JSONUtil;
import com.becoze.nickcode.model.dto.problem.JudgeConfig;
import com.becoze.nickcode.model.entity.Problem;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Problem
 *
 * @TableName problem
 */
@Data
public class ProblemVO implements Serializable {
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
     * Judge Configuration (JSON Object)
     */
    private JudgeConfig judgeConfig;

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
     * Creator info
     */
    private UserVO userVO;

    /**
     * convert Object to VO
     *
     * @param problemVO
     * @return
     */
    public static Problem voToObj(ProblemVO problemVO) {
        if (problemVO == null) {
            return null;
        }

        Problem problem = new Problem();
        BeanUtils.copyProperties(problemVO, problem);
        List<String> tagList = problemVO.getTags();
        if (tagList != null) {
            problem.setTags(JSONUtil.toJsonStr(tagList));
        }

        JudgeConfig vojudgeConfig = problemVO.getJudgeConfig();
        if (vojudgeConfig != null) {
            problem.setJudgeConfig(JSONUtil.toJsonStr(vojudgeConfig));
        }
        return problem;
    }

    /**
     * convert VO to Object
     *
     * @param problem
     * @return
     */
    public static ProblemVO objToVo(Problem problem) {
        if (problem == null) {
            return null;
        }
        ProblemVO problemVO = new ProblemVO();
        BeanUtils.copyProperties(problem, problemVO);
        List<String> tagList = JSONUtil.toList(problem.getTags(), String.class);
        problemVO.setTags(tagList);
        String judgeConfigStr = problem.getJudgeConfig();
        problemVO.setJudgeConfig(JSONUtil.toBean(judgeConfigStr, JudgeConfig.class));
        return problemVO;
    }

    private static final long serialVersionUID = 1L;
}