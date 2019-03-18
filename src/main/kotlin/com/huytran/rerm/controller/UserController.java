//package com.huytran.rerm.controller;
//
//import com.huytran.rerm.bean.core.BeanResult;
//import com.huytran.rerm.controller.core.CoreController;
//import com.huytran.rerm.service.UserService;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpSession;
//import javax.validation.Valid;
//
//@RestController("/user")
//public class UserController extends CoreController<UserService, UserService.Params> {
//
//    private UserService userService;
//
//    public UserController(
//            UserService userService
//    ) {
//        super(userService);
//        this.userService = userService;
//    }
//
//    public BeanResult login(
//            HttpSession httpSession,
//            @Valid UserService.Params params) {
//        return userService.login(httpSession, params);
//    }
//
//    public BeanResult signup(
//            HttpSession httpSession,
//            @Valid UserService.Params params) {
//        return userService.signup(httpSession, params);
//    }
//
//}
