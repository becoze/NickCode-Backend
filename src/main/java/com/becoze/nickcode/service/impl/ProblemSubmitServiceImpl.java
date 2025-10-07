package com.becoze.nickcode.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.becoze.nickcode.common.ErrorCode;
import com.becoze.nickcode.constant.CommonConstant;
import com.becoze.nickcode.exception.BusinessException;
import com.becoze.nickcode.mapper.ProblemSubmitMapper;
import com.becoze.nickcode.model.dto.problemsubmit.ProblemSubmitAddRequest;
import com.becoze.nickcode.model.dto.problemsubmit.ProblemSubmitQueryRequest;
import com.becoze.nickcode.model.entity.Problem;
import com.becoze.nickcode.model.entity.ProblemSubmit;
import com.becoze.nickcode.model.entity.User;
import com.becoze.nickcode.model.enums.ProblemSubmitLanguageEnum;
import com.becoze.nickcode.model.enums.ProblemSubmitStatusEnum;
import com.becoze.nickcode.model.vo.ProblemSubmitVO;
import com.becoze.nickcode.service.ProblemService;
import com.becoze.nickcode.service.ProblemSubmitService;
import com.becoze.nickcode.service.UserService;
import com.becoze.nickcode.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author liyua
* @description 针对表【problem_submit(Problem_Submit)】的数据库操作Service实现
* @createDate 2025-08-20 19:49:31
*/
@Service
public class ProblemSubmitServiceImpl extends ServiceImpl<ProblemSubmitMapper, ProblemSubmit>
    implements ProblemSubmitService{
    @Resource
    private ProblemService problemService;

    @Resource
    private UserService userService;

    /**
     * Submit problem
     *
     * @param problemSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public long doProblemSubmit(ProblemSubmitAddRequest problemSubmitAddRequest, User loginUser) {
        // Validate the programming language selected by the user.
        // 1. get submitted language from the request
        // 2. try match with ProblemSubmitLanguageEnum
        // 3. throw exception if no match
        String language = problemSubmitAddRequest.getLanguage();
        ProblemSubmitLanguageEnum languageEnum = ProblemSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
           throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Language not supported / error");
        }

        long problemId = problemSubmitAddRequest.getProblemId();

        // check if exist and fetch the object
        Problem problem = problemService.getById(problemId);
        if (problem == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // Fetch user id
        long userId = loginUser.getId();
        ProblemSubmit problemSubmit = new ProblemSubmit();
        problemSubmit.setUserId(userId);
        problemSubmit.setProblemId(problemId);
        problemSubmit.setCode(problemSubmitAddRequest.getCode());
        problemSubmit.setLanguage(language);
        // Set init status to PENDING
        problemSubmit.setStatus(ProblemSubmitStatusEnum.PENDING.getValue());
        problemSubmit.setJudgeInfo("{}");
        boolean save = this.save(problemSubmit);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Problem Submission error");
        }
        return problemSubmit.getId();
    }

    /**
     * Verify query wrapper, wrap query using objects from user front-end
     *
     * @param problemSubmitQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<ProblemSubmit> getQueryWrapper(ProblemSubmitQueryRequest problemSubmitQueryRequest) {
        QueryWrapper<ProblemSubmit> queryWrapper = new QueryWrapper<>();
        if (problemSubmitQueryRequest == null) {
            return queryWrapper;
        }
        String language = problemSubmitQueryRequest.getLanguage();
        Integer status = problemSubmitQueryRequest.getStatus();
        Long problemId = problemSubmitQueryRequest.getProblemId();
        Long userId = problemSubmitQueryRequest.getUserId();
        String sortField = problemSubmitQueryRequest.getSortField();
        String sortOrder = problemSubmitQueryRequest.getSortOrder();

        // wrap the submitted query
        queryWrapper.eq(StringUtils.isNotEmpty(language), "language", language);
        queryWrapper.eq(ProblemSubmitStatusEnum.getEnumByValue(status) != null, "status", status);
        queryWrapper.eq(ObjectUtils.isNotEmpty(problemId), "problemId", problemId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * Get single problemSubmit data
     *
     * @param problemSubmit
     * @param loginUser import user info with "User" class not "HttpServletRequest" request class.
     * @return
     */
    @Override
    public ProblemSubmitVO getProblemSubmitVO(ProblemSubmit problemSubmit, User loginUser) {
        ProblemSubmitVO problemSubmitVO = ProblemSubmitVO.objToVo(problemSubmit);
        // 本人和管理员能看见自己（提交 userID 和登录用户id不同）提交代码答案
        long userId = loginUser.getId();
        // Do not show answer code to non admin && user who not the problem creator/owner
        if (userId != problemSubmit.getUserId() && !userService.isAdmin(loginUser)) {
            problemSubmitVO.setCode(null);
        }
        return problemSubmitVO;
    }

    /**
     * Get multiple problemSubmit data as a page
     */
    @Override
    public Page<ProblemSubmitVO> getProblemSubmitVOPage(Page<ProblemSubmit> problemSubmitPage, User loginUser) {
        List<ProblemSubmit> problemSubmitList = problemSubmitPage.getRecords();
        Page<ProblemSubmitVO> problemSubmitVOPage = new Page<>(problemSubmitPage.getCurrent(), problemSubmitPage.getSize(), problemSubmitPage.getTotal());
        if (CollUtil.isEmpty(problemSubmitList)) {
            return problemSubmitVOPage;
        }
        List<ProblemSubmitVO> problemSubmitVOList = problemSubmitList.stream()
                .map(problemSubmit -> getProblemSubmitVO(problemSubmit, loginUser))
                .collect(Collectors.toList());
        problemSubmitVOPage.setRecords(problemSubmitVOList);
        return problemSubmitVOPage;
    }
}




