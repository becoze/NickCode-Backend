package com.becoze.nickcode.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.becoze.nickcode.model.dto.problem.ProblemQueryRequest;
import com.becoze.nickcode.model.entity.Problem;
import com.becoze.nickcode.model.entity.Problem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.becoze.nickcode.model.vo.ProblemVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author liyua
* @description 针对表【problem(Problem)】的数据库操作Service
* @createDate 2025-08-20 19:47:05
*/
public interface ProblemService extends IService<Problem> {
    /**
     * 校验
     *
     * @param problem
     * @param add
     */
    void validProblem(Problem problem, boolean add);

    /**
     * 获取查询条件
     *
     * @param problemQueryRequest
     * @return
     */
    QueryWrapper<Problem> getQueryWrapper(ProblemQueryRequest problemQueryRequest);

    /**
     * 获取problem封装
     *
     * @param problem
     * @param request
     * @return
     */
    ProblemVO getProblemVO(Problem problem, HttpServletRequest request);

    /**
     * 分页获取problem封装
     *
     * @param problemPage
     * @param request
     * @return
     */
    Page<ProblemVO> getProblemVOPage(Page<Problem> problemPage, HttpServletRequest request);
}
