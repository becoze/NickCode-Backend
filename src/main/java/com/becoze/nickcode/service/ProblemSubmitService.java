package com.becoze.nickcode.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.becoze.nickcode.model.dto.problemsubmit.ProblemSubmitAddRequest;
import com.becoze.nickcode.model.dto.problemsubmit.ProblemSubmitQueryRequest;
import com.becoze.nickcode.model.entity.ProblemSubmit;
import com.becoze.nickcode.model.entity.User;
import com.becoze.nickcode.model.vo.ProblemSubmitVO;

/**
* @author liyua
* @description 针对表【problem_submit(Problem_Submit)】的数据库操作Service
* @createDate 2025-08-20 19:49:31
*/
public interface ProblemSubmitService extends IService<ProblemSubmit> {
    /**
     * Submit Problem
     *
     * @param problemSubmitAddRequest submit problem info
     * @param loginUser
     * @return
     */
    long doProblemSubmit(ProblemSubmitAddRequest problemSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param problemSubmitQueryRequest
     * @return
     */
    QueryWrapper<ProblemSubmit> getQueryWrapper(ProblemSubmitQueryRequest problemSubmitQueryRequest);

    /**
     * 获取problem封装
     *
     * @param problemSubmit
     * @param loginUser
     * @return
     */
    ProblemSubmitVO getProblemSubmitVO(ProblemSubmit problemSubmit, User loginUser);

    /**
     * 分页获取problem封装
     *
     * @param problemSubmitPage
     * @param loginUser
     * @return
     */
    Page<ProblemSubmitVO> getProblemSubmitVOPage(Page<ProblemSubmit> problemSubmitPage, User loginUser);
}
