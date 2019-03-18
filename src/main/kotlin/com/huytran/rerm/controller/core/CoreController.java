//package com.huytran.rerm.controller.core;
//
//import com.huytran.rerm.bean.core.BeanResult;
//import com.huytran.rerm.service.core.CoreService;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import javax.validation.Valid;
//
//@SuppressWarnings("unchecked")
//@Validated
//public abstract class CoreController<Service extends CoreService, Params extends CoreService.AbstractParams> {
//
//    private Service service;
//
//    public CoreController(
//            Service service
//    ) {
//        this.service = service;
//    }
//
//    @PostMapping("/get")
//    public BeanResult get(@Valid Params params) {
//        return service.create(params);
//    }
//
//    @PostMapping("/getAll")
//    public BeanResult getAll() {
//        return service.getAll();
//    }
//
//    @PostMapping("/create")
//    public BeanResult create(@Valid Params params) {
//        return service.create(params);
//    }
//
//    @PostMapping("/update")
//    public BeanResult update(
//            Long id,
//            @Valid Params params) {
//        return service.update(id, params);
//    }
//
//    @PostMapping("/delete")
//    public BeanResult delete(Long id) {
//        return service.delete(id);
//    }
//
//}
