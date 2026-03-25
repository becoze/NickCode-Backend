package com.becoze.nickcode.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.becoze.nickcode.common.BaseResponse;
import com.becoze.nickcode.common.ErrorCode;
import com.becoze.nickcode.common.ResultUtils;
import com.becoze.nickcode.exception.BusinessException;
import com.becoze.nickcode.model.dto.problemsubmit.ProblemSubmitAddRequest;
import com.becoze.nickcode.model.dto.problemsubmit.ProblemSubmitQueryRequest;
import com.becoze.nickcode.model.entity.ProblemSubmit;
import com.becoze.nickcode.model.entity.User;
import com.becoze.nickcode.model.vo.ProblemSubmitVO;
import com.becoze.nickcode.service.ProblemSubmitService;
import com.becoze.nickcode.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Problem submit
 */
@RestController
//@RequestMapping("/problem_submit")
@Slf4j
@Deprecated
public class ProblemSubmitController {

    @Resource
    private ProblemSubmitService problemSubmitService;

    @Resource
    private UserService userService;

    /**
     * Submit problem
     *
     * @param problemSubmitAddRequest
     * @param request
     * @return submission ID
     */
//    @PostMapping("/")
    public BaseResponse<Long> doProblemSubmit(@RequestBody ProblemSubmitAddRequest problemSubmitAddRequest,
                                              HttpServletRequest request) {
        // Problem ID con not be null
        if (problemSubmitAddRequest == null || problemSubmitAddRequest.getProblemId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        final User loginUser = userService.getLoginUser(request);
        long result = problemSubmitService.doProblemSubmit(problemSubmitAddRequest, loginUser);
        return ResultUtils.success(result);
    }

    /**
     * 分页获取 Submitted-problem 列表，查看所有人的提交记录的公开信息（仅管理员和当前用户，其他非管理员用户只能看见不包含代码答案等公开信息）
     *
     * @param problemSubmitQueryRequest
     * @return
     */
//    @PostMapping("/list/page")
    public BaseResponse<Page<ProblemSubmitVO>> listProblemSubmitByPage(@RequestBody ProblemSubmitQueryRequest problemSubmitQueryRequest,
                                                                       HttpServletRequest request) {
        long current = problemSubmitQueryRequest.getCurrent();
        long size = problemSubmitQueryRequest.getPageSize();
        // Get all submitted problem page data
        Page<ProblemSubmit> problemSubmitPage = problemSubmitService.page(new Page<>(current, size),
                problemSubmitService.getQueryWrapper(problemSubmitQueryRequest));
        // Return filtered info
        final User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(problemSubmitService.getProblemSubmitVOPage(problemSubmitPage, loginUser));
    }

}
