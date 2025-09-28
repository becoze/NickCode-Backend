package com.becoze.nickcode.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.becoze.nickcode.common.ErrorCode;
import com.becoze.nickcode.constant.CommonConstant;
import com.becoze.nickcode.exception.BusinessException;
import com.becoze.nickcode.exception.ThrowUtils;
import com.becoze.nickcode.mapper.ProblemMapper;
import com.becoze.nickcode.model.dto.problem.ProblemQueryRequest;
import com.becoze.nickcode.model.entity.Problem;
import com.becoze.nickcode.model.entity.User;
import com.becoze.nickcode.model.vo.ProblemVO;
import com.becoze.nickcode.model.vo.UserVO;
import com.becoze.nickcode.service.ProblemService;
import com.becoze.nickcode.service.UserService;
import com.becoze.nickcode.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author liyua
* @description 针对表【problem(Problem)】的数据库操作Service实现
* @createDate 2025-08-20 19:47:05
*/
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem>
    implements ProblemService{
    @Resource
    private UserService userService;

    /**
     * Validate Problem, by checking its size and is-complete
     * @param problem
     * @param add
     */
    @Override
    public void validProblem(Problem problem, boolean add) {
        if (problem == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // Variables to be checked
        String title = problem.getTitle();
        String content = problem.getContent();
        String tags = problem.getTags();
        String answer = problem.getAnswer();
        String judgeCase = problem.getJudgeCase();
        String judgeConfig = problem.getJudgeConfig();

        // Title, Content and Tags can not be null when created
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(title, content, tags), ErrorCode.PARAMS_ERROR);
        }
        // Length and size check
        if (StringUtils.isNotBlank(title) && title.length() > 80) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Title exceed size limit");
        }
        if (StringUtils.isNotBlank(content) && content.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Content exceed size limit");
        }
        if (StringUtils.isNotBlank(answer) && answer.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Answer exceed size limit");
        }
        if (StringUtils.isNotBlank(judgeCase) && judgeCase.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Judge case exceed size limit");
        }
        if (StringUtils.isNotBlank(judgeConfig) && judgeConfig.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Judge config exceed size limit");
        }
    }

    /**
     * Verify query wrapper, wrap query using objects from user front-end
     *
     * @param problemQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Problem> getQueryWrapper(ProblemQueryRequest problemQueryRequest) {
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        if (problemQueryRequest == null) {
            return queryWrapper;
        }

        Long id = problemQueryRequest.getId();
        String title = problemQueryRequest.getTitle();
        String content = problemQueryRequest.getContent();
        List<String> tags = problemQueryRequest.getTags();
        String answer = problemQueryRequest.getAnswer();
        Long userId = problemQueryRequest.getUserId();
        String sortField = problemQueryRequest.getSortField();
        String sortOrder = problemQueryRequest.getSortOrder();

        // wrap the submitted query
        queryWrapper.like(StringUtils.isNotBlank(title), "title", title);
        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        queryWrapper.like(StringUtils.isNotBlank(answer), "answer", answer);
        if (CollUtil.isNotEmpty(tags)) {
            for (String tag : tags) {
                queryWrapper.like("tags", "\"" + tag + "\"");
            }
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * Fetch Problem data and build Problem query VO
     * @param problem
     * @param request
     * @return
     */
    @Override
    public ProblemVO getProblemVO(Problem problem, HttpServletRequest request) {
        ProblemVO problemVO = ProblemVO.objToVo(problem);
        // Find associated user information that not come together by its own
        Long userId = problem.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        problemVO.setUserVO(userVO);
        return problemVO;
    }

    @Override
    public Page<ProblemVO> getProblemVOPage(Page<Problem> problemPage, HttpServletRequest request) {
        List<Problem> problemList = problemPage.getRecords();
        Page<ProblemVO> problemVOPage = new Page<>(problemPage.getCurrent(), problemPage.getSize(), problemPage.getTotal());
        if (CollUtil.isEmpty(problemList)) {
            return problemVOPage;
        }
        // Find associated user information
        Set<Long> userIdSet = problemList.stream().map(Problem::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));

        // Fetch data
        List<ProblemVO> problemVOList = problemList.stream().map(problem -> {
            ProblemVO problemVO = ProblemVO.objToVo(problem);
            Long userId = problem.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            problemVO.setUserVO(userService.getUserVO(user));
            return problemVO;
        }).collect(Collectors.toList());
        problemVOPage.setRecords(problemVOList);
        return problemVOPage;
    }
}




