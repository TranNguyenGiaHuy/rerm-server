package com.huytran.rerm.service.core;

import com.huytran.rerm.bean.core.BeanList;
import com.huytran.rerm.bean.core.BeanResult;
import com.huytran.rerm.constant.ResultCode;
import com.huytran.rerm.model.core.ModelCore;
import com.huytran.rerm.repository.UserRepository;
import com.huytran.rerm.repository.core.RepositoryCore;
import kotlin.Suppress;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
public abstract class CoreService<BaseModel extends ModelCore, Repository extends RepositoryCore, Params extends CoreService.AbstractParams> {

    private Repository repository;

    public CoreService(
            Repository repository
    ) {
        this.repository = repository;
    }

    public abstract static class AbstractParams{}

    public abstract BaseModel createModel();
    public abstract void parseParams(BaseModel model, Params params);

    public BeanResult create(Params params) {
        BeanResult  beanResult = new BeanResult();

        BaseModel baseModel = createModel();
        parseParams(baseModel, params);
        repository.save(baseModel);

        beanResult.setBean(baseModel.createBean());
        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        return beanResult;
    }

    public BeanResult update(
            Long id,
            Params params) {
        BeanResult  beanResult = new BeanResult();

        Optional<BaseModel> optionalBaseModel = repository.findByIdAndAvailable(id, true);
        if (!optionalBaseModel.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }
        BaseModel baseModel = optionalBaseModel.get();
        parseParams(baseModel, params);
        repository.save(baseModel);

        beanResult.setBean(baseModel.createBean());
        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        return beanResult;
    }

    public BeanResult delete(Long id) {
        BeanResult  beanResult = new BeanResult();

        Optional<BaseModel> optionalBaseModel = repository.findByIdAndAvailable(id, true);
        if (!optionalBaseModel.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }
        BaseModel baseModel = optionalBaseModel.get();
        baseModel.setAvailable(false);
        repository.save(baseModel);

        beanResult.setBean(baseModel.createBean());
        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        return beanResult;
    }

    public BeanResult get(Long id) {
        BeanResult  beanResult = new BeanResult();

        Optional<BaseModel> optionalBaseModel = repository.findByIdAndAvailable(id, true);
        if (!optionalBaseModel.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        beanResult.setBean(optionalBaseModel.get().createBean());
        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        return beanResult;
    }

    public BeanResult getAll() {
        BeanResult  beanResult = new BeanResult();

        Iterable<BaseModel> baseModelIterable = repository.findByAvailable(true);

        beanResult.setBean(new BeanList(baseModelIterable));
        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        return beanResult;
    }
}
