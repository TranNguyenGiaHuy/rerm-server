package com.huytran.rerm.service;

import com.huytran.rerm.bean.BeanGrpcSession;
import com.huytran.rerm.bean.core.BeanResult;
import com.huytran.rerm.constant.ResultCode;
import com.huytran.rerm.model.User;
import com.huytran.rerm.repository.UserRepository;
import com.huytran.rerm.service.core.CoreService;
import com.huytran.rerm.utilities.UtilityFunction;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class UserService extends CoreService<User, UserRepository, UserService.Params> {

    private UserRepository userRepository;
    private GrpcSessionService grpcSessionService;

    UserService(
            UserRepository userRepository,
            GrpcSessionService grpcSessionService
    ) {
        super(userRepository);
        this.userRepository = userRepository;
        this.grpcSessionService = grpcSessionService;
    }

    @Override
    public User createModel() {
        return new User();
    }

    @Override
    public void parseParams(User user, Params params) {
        user.setName(params.name);
        user.setPassword(params.password);
    }

    public static class Params extends CoreService.AbstractParams {
        String name;
        String password;
        String userName;
        Long avatarId;
        String phoneNumber;
        String idCard;
        Long tsCardDated;
        Long tsDateOfBirth;

        public Params(String name,
                      String password,
                      String userName,
                      Long avatarId,
                      String phoneNumber,
                      String idCard,
                      Long tsCardDated,
                      Long tsDateOfBirth) {
            this.name = name;
            this.password = password;
            this.userName = userName;
            this.avatarId = avatarId;
            this.phoneNumber = phoneNumber;
            this.idCard = idCard;
            this.tsCardDated = tsCardDated;
            this.tsDateOfBirth = tsDateOfBirth;
        }

        public Params(String name,
                      String password) {
            this.name = name;
            this.password = password;
            this.userName = "";
            this.avatarId = -1L;
            this.phoneNumber = "";
            this.idCard = "";
            this.tsCardDated = 0L;
            this.tsDateOfBirth = 0L;
        }
    }

    public BeanResult signup(Params params) {
        BeanResult beanResult = new BeanResult();

        BeanResult getSessionResult = grpcSessionService.getSession();
        if (getSessionResult.getCode() == ResultCode.RESULT_CODE_VALID) {
            beanResult.setCode(ResultCode.RESULT_CODE_ALREADY_LOGIN);
            return beanResult;
        }

        Optional<User> originalUser = userRepository.findByName(params.name);
        if (originalUser.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_DUPLICATE);
            return beanResult;
        }

        User user = new User();
        user.setName(params.name);
        user.setPassword(
                UtilityFunction.Companion.encode(params.password)
        );
        userRepository.save(user);

        BeanResult createSessionResult = grpcSessionService.create(
                new GrpcSessionService.Params(
                        user.getId(),
                        UtilityFunction.Companion.generateToken()
                )
        );

        if (createSessionResult.getCode() != ResultCode.RESULT_CODE_VALID) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_LOGIN);
            beanResult.setBean(createSessionResult.getBean());
            return beanResult;
        }

        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        beanResult.setBean(createSessionResult.getBean());
        return beanResult;
    }

    public BeanResult login(Params params) {
        BeanResult beanResult = new BeanResult();

        BeanResult getSessionResult = grpcSessionService.getSession();
        if (getSessionResult.getCode() == ResultCode.RESULT_CODE_VALID) {
            beanResult.setCode(ResultCode.RESULT_CODE_ALREADY_LOGIN);
            return beanResult;
        }

        Optional<User> originalUser = userRepository.findByName(params.name);
        if (!originalUser.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        User user = originalUser.get();
        if (
                !UtilityFunction.Companion.comparePassword(params.password, user.getPassword())
        ) {
            beanResult.setCode(ResultCode.RESULT_CODE_WRONG_PASSWORD);
            return beanResult;
        }

        BeanResult createSessionResult = grpcSessionService.create(
                new GrpcSessionService.Params(
                        user.getId(),
                        UtilityFunction.Companion.generateToken()
                )
        );

        if (createSessionResult.getCode() != ResultCode.RESULT_CODE_VALID) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_LOGIN);
            beanResult.setBean(createSessionResult.getBean());
            return beanResult;
        }

        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        beanResult.setBean(user.createBean());
        return beanResult;
    }

    public BeanResult logout() {
        BeanResult beanResult = new BeanResult();

        grpcSessionService.invalidateSession();

        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        return beanResult;
    }

    public BeanResult get() {
        BeanResult beanResult = new BeanResult();
        BeanResult getGrpcTokkenResult = grpcSessionService.getSession();

        if (getGrpcTokkenResult.getCode() != ResultCode.RESULT_CODE_VALID
                || getGrpcTokkenResult.getBean() == null
                || !(getGrpcTokkenResult.getBean() instanceof BeanGrpcSession)) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        Long userId = ((BeanGrpcSession) getGrpcTokkenResult.getBean()).getUserId();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        beanResult.setBean(optionalUser.get().createBean());
        return beanResult;
    }
}
