package com.becoze.nickcode.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.becoze.nickcode.common.ErrorCode;
import com.becoze.nickcode.exception.BusinessException;
import com.becoze.nickcode.mapper.ProblemSubmitMapper;
import com.becoze.nickcode.model.dto.problemsubmit.ProblemSubmitAddRequest;
import com.becoze.nickcode.model.entity.Problem;
import com.becoze.nickcode.model.entity.ProblemSubmit;
import com.becoze.nickcode.model.entity.User;
import com.becoze.nickcode.model.enums.ProblemSubmitLanguageEnum;
import com.becoze.nickcode.model.enums.ProblemSubmitStatusEnum;
import com.becoze.nickcode.service.ProblemService;
import com.becoze.nickcode.service.ProblemSubmitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}




