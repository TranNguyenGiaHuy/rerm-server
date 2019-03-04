package com.huytran.rerm.service;

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

    UserService(
            UserRepository userRepository
    ) {
        super(userRepository);
        this.userRepository = userRepository;
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

    public class Params extends CoreService.AbstractParams {
        String name;
        String password;
    }

    public BeanResult signup(HttpSession httpSession, Params params) {
        BeanResult beanResult = new BeanResult();

        if (isLogin(httpSession)) {
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

        httpSession.setAttribute("currentUser", user);

        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        beanResult.setBean(user.createBean());
        return beanResult;
    }

    public BeanResult login(HttpSession httpSession, Params params) {
        BeanResult beanResult = new BeanResult();

        if (isLogin(httpSession)) {
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

        httpSession.setAttribute("currentUser", user);

        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        beanResult.setBean(user.createBean());
        return beanResult;
    }

    public BeanResult logout(HttpSession httpSession) {
        BeanResult beanResult = new BeanResult();

        if (!isLogin(httpSession)) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_LOGIN);
            return beanResult;
        }

        httpSession.removeAttribute("currentUser");
        httpSession.invalidate();

        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        return beanResult;
    }

    private Boolean isLogin(HttpSession httpSession) {
        return httpSession.getAttribute("currentUser") != null;
    }

    private User getCurrentUser(HttpSession httpSession) {
        return (User) httpSession.getAttribute("currentUser");
    }
}
