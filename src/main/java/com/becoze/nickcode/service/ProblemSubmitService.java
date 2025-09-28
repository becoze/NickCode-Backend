package com.becoze.nickcode.service;

import com.becoze.nickcode.model.dto.problemsubmit.ProblemSubmitAddRequest;
import com.becoze.nickcode.model.entity.ProblemSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.becoze.nickcode.model.entity.User;

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

}
