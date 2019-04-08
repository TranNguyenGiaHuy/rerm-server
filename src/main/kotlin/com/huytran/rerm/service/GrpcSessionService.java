package com.huytran.rerm.service;

import com.huytran.rerm.bean.BeanGrpcSession;
import com.huytran.rerm.bean.core.BeanResult;
import com.huytran.rerm.constant.ResultCode;
import com.huytran.rerm.interceptor.SecurityInterceptor;
import com.huytran.rerm.model.GrpcSession;
import com.huytran.rerm.model.Image;
import com.huytran.rerm.model.User;
import com.huytran.rerm.repository.GrpcSessionRepository;
import com.huytran.rerm.repository.ImageRepository;
import com.huytran.rerm.service.core.CoreService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GrpcSessionService extends CoreService<GrpcSession, GrpcSessionRepository, GrpcSessionService.Params> {

    private GrpcSessionRepository grpcSessionRepository;

    GrpcSessionService(
            GrpcSessionRepository grpcSessionRepository
    ) {
        super(grpcSessionRepository);
        this.grpcSessionRepository = grpcSessionRepository;
    }

    @Override
    public GrpcSession createModel() {
        return new GrpcSession();
    }

    @Override
    public void parseParams(GrpcSession image, Params params) {
        image.setUser(params.user);
        image.setToken(params.token);
    }

    public static class Params extends CoreService.AbstractParams {
        User user;
        String token;

        Params(User user, String token) {
            this.user =  user;
            this.token =  token;
        }
    }

    public BeanResult getSession() {
        BeanResult beanResult = new BeanResult();

        String token = SecurityInterceptor.Companion.getUSER_IDENTITY().get();
        if (token.isEmpty()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_LOGIN);
            return beanResult;
        }

        Optional<GrpcSession> grpcSessionOptional = grpcSessionRepository.findByToken(token);
        if (!grpcSessionOptional.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_LOGIN);
            return beanResult;
        }

        beanResult.setBean(
                grpcSessionOptional.get().createBean()
        );
        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        return beanResult;
    }

    public BeanResult invalidateSession() {
        BeanResult beanResult = new BeanResult();

        String token = SecurityInterceptor.Companion.getUSER_IDENTITY().get();
        if (token.isEmpty()) {
            beanResult.setCode(ResultCode.RESULT_CODE_VALID);
            return beanResult;
        }

        Optional<GrpcSession> grpcSessionOptional = grpcSessionRepository.findByToken(token);
        if (!grpcSessionOptional.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_VALID);
            return beanResult;
        }

        grpcSessionRepository.delete(grpcSessionOptional.get());
        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        return beanResult;
    }

    public BeanResult checkLogin() {
        BeanResult beanResult = new BeanResult();

        String token = SecurityInterceptor.Companion.getUSER_IDENTITY().get();
        if (token.isEmpty()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_LOGIN);
            return beanResult;
        }

        Optional<GrpcSession> grpcSessionOptional = grpcSessionRepository.findByToken(token);
        if (!grpcSessionOptional.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_LOGIN);
            return beanResult;
        }

        User user = grpcSessionOptional.get().getUser();
        if (user == null) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_LOGIN);
            return beanResult;
        }

        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        return beanResult;
    }

}
