package com.becoze.nickcode.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.becoze.nickcode.annotation.AuthCheck;
import com.becoze.nickcode.common.BaseResponse;
import com.becoze.nickcode.common.DeleteRequest;
import com.becoze.nickcode.common.ErrorCode;
import com.becoze.nickcode.common.ResultUtils;
import com.becoze.nickcode.constant.UserConstant;
import com.becoze.nickcode.exception.BusinessException;
import com.becoze.nickcode.exception.ThrowUtils;
import com.becoze.nickcode.model.dto.problem.*;
import com.becoze.nickcode.model.entity.Problem;
import com.becoze.nickcode.model.entity.User;
import com.becoze.nickcode.model.vo.ProblemVO;
import com.becoze.nickcode.service.ProblemService;
import com.becoze.nickcode.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Problem API
 */
@RestController
@RequestMapping("/problem")
@Slf4j
public class ProblemController {

    @Resource
    private ProblemService problemService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建
     *
     * @param problemAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addProblem(@RequestBody ProblemAddRequest problemAddRequest, HttpServletRequest request) {
        if (problemAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Problem problem = new Problem();
        BeanUtils.copyProperties(problemAddRequest, problem);
        List<String> tags = problemAddRequest.getTags();
        if (tags != null) {
            problem.setTags(JSONUtil.toJsonStr(tags));
        }
        // Convert judgeCase and judgeConfig into JSON strings and store them into the problem entity.
        List<JudgeCase> judgeCase = problemAddRequest.getJudgeCase();
        if (judgeCase != null) {
            problem.setJudgeCase(JSONUtil.toJsonStr(judgeCase));
        }
        JudgeConfig judgeConfig = problemAddRequest.getJudgeConfig();
        if (judgeConfig != null) {
            problem.setJudgeConfig(JSONUtil.toJsonStr(judgeConfig));
        }
        problemService.validProblem(problem, true);
        User loginUser = userService.getLoginUser(request);
        problem.setUserId(loginUser.getId());
        problem.setFavourNum(0);
        problem.setThumbNum(0);
        boolean result = problemService.save(problem);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newProblemId = problem.getId();
        return ResultUtils.success(newProblemId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteProblem(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        Problem oldProblem = problemService.getById(id);
        ThrowUtils.throwIf(oldProblem == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldProblem.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = problemService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param problemUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateProblem(@RequestBody ProblemUpdateRequest problemUpdateRequest) {
        if (problemUpdateRequest == null || problemUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Problem problem = new Problem();
        BeanUtils.copyProperties(problemUpdateRequest, problem);
        List<String> tags = problemUpdateRequest.getTags();
        if (tags != null) {
            problem.setTags(JSONUtil.toJsonStr(tags));
        }
        // Convert judgeCase and judgeConfig into JSON strings and store them into the problem entity.
        List<JudgeCase> judgeCase = problemUpdateRequest.getJudgeCase();
        if (judgeCase != null) {
            problem.setJudgeCase(JSONUtil.toJsonStr(judgeCase));
        }
        JudgeConfig judgeConfig = problemUpdateRequest.getJudgeConfig();
        if (judgeConfig != null) {
            problem.setJudgeConfig(JSONUtil.toJsonStr(judgeConfig));
        }
        // 参数校验
        problemService.validProblem(problem, false);
        long id = problemUpdateRequest.getId();
        // 判断是否存在
        Problem oldProblem = problemService.getById(id);
        ThrowUtils.throwIf(oldProblem == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = problemService.updateById(problem);
        return ResultUtils.success(result);
    }
    /**
     * 根据 id 获取原始数据
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<Problem> getProblemById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Problem problem = problemService.getById(id);
        if (problem == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        User loginuser = userService.getLoginUser(request);
        if (!problem.getUserId().equals(loginuser.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        return ResultUtils.success(problem);
    }

    /**
     * 根据 id 获取脱敏数据
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<ProblemVO> getProblemVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Problem problem = problemService.getById(id);
        if (problem == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(problemService.getProblemVO(problem, request));
    }

    /**
     * 分页获取 problem 列表（仅管理员）
     *
     * @param problemQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<Problem>> listProblemByPage(@RequestBody ProblemQueryRequest problemQueryRequest) {
        long current = problemQueryRequest.getCurrent();
        long size = problemQueryRequest.getPageSize();
        Page<Problem> problemPage = problemService.page(new Page<>(current, size),
                problemService.getQueryWrapper(problemQueryRequest));
        return ResultUtils.success(problemPage);
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param problemQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<ProblemVO>> listProblemVOByPage(@RequestBody ProblemQueryRequest problemQueryRequest,
                                                             HttpServletRequest request) {
        long current = problemQueryRequest.getCurrent();
        long size = problemQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Problem> problemPage = problemService.page(new Page<>(current, size),
                problemService.getQueryWrapper(problemQueryRequest));
        return ResultUtils.success(problemService.getProblemVOPage(problemPage, request));
    }

    /**
     * 分页获取当前用户创建的资源列表
     *
     * @param problemQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<ProblemVO>> listMyProblemVOByPage(@RequestBody ProblemQueryRequest problemQueryRequest,
                                                               HttpServletRequest request) {
        if (problemQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        problemQueryRequest.setUserId(loginUser.getId());
        long current = problemQueryRequest.getCurrent();
        long size = problemQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Problem> problemPage = problemService.page(new Page<>(current, size),
                problemService.getQueryWrapper(problemQueryRequest));
        return ResultUtils.success(problemService.getProblemVOPage(problemPage, request));
    }

    // endregion

    /**
     * 编辑（用户）
     *
     * @param problemEditRequest
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editProblem(@RequestBody ProblemEditRequest problemEditRequest, HttpServletRequest request) {
        if (problemEditRequest == null || problemEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Problem problem = new Problem();
        BeanUtils.copyProperties(problemEditRequest, problem);
        List<String> tags = problemEditRequest.getTags();
        if (tags != null) {
            problem.setTags(JSONUtil.toJsonStr(tags));
        }
        // Convert judgeCase and judgeConfig into JSON strings and store them into the problem entity.
        List<JudgeCase> judgeCase = problemEditRequest.getJudgeCase();
        if (judgeCase != null) {
            problem.setJudgeCase(JSONUtil.toJsonStr(judgeCase));
        }
        JudgeConfig judgeConfig = problemEditRequest.getJudgeConfig();
        if (judgeConfig != null) {
            problem.setJudgeConfig(JSONUtil.toJsonStr(judgeConfig));
        }
        // 参数校验
        problemService.validProblem(problem, false);
        User loginUser = userService.getLoginUser(request);
        long id = problemEditRequest.getId();
        // 判断是否存在
        Problem oldProblem = problemService.getById(id);
        ThrowUtils.throwIf(oldProblem == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldProblem.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = problemService.updateById(problem);
        return ResultUtils.success(result);
    }

}
