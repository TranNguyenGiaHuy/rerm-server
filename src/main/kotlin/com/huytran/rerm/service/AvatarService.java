package com.huytran.rerm.service;

import com.huytran.rerm.bean.BeanAvatar;
import com.huytran.rerm.bean.BeanGrpcSession;
import com.huytran.rerm.bean.core.BeanResult;
import com.huytran.rerm.config.PropertyConfig;
import com.huytran.rerm.constant.ResultCode;
import com.huytran.rerm.model.Avatar;
import com.huytran.rerm.model.User;
import com.huytran.rerm.repository.AvatarRepository;
import com.huytran.rerm.repository.UserRepository;
import com.huytran.rerm.service.core.CoreService;
import com.huytran.rerm.utilities.UtilityFunction;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AvatarService extends CoreService<Avatar, AvatarRepository, AvatarService.Params> {

    private AvatarRepository avatarRepository;
    private UserRepository userRepository;
    private PropertyConfig propertyConfig;
    private GrpcSessionService grpcSessionService;

    AvatarService(
            AvatarRepository avatarRepository,
            UserRepository userRepository,
            PropertyConfig propertyConfig,
            GrpcSessionService grpcSessionService) {
        super(avatarRepository);
        this.avatarRepository = avatarRepository;
        this.userRepository = userRepository;
        this.propertyConfig = propertyConfig;
        this.grpcSessionService = grpcSessionService;
    }

    @Override
    public Avatar createModel() {
        return new Avatar();
    }

    @Override
    public void parseParams(Avatar avatar, Params params) {
        avatar.setUser(params.user);
        avatar.setPath(params.path);
        avatar.setName(params.name);
        avatar.setFileName(params.fileName);
    }

    public class Params extends CoreService.AbstractParams {
        User user;
        String path;
        String name;
        String fileName;

        Params(User user,
               String path,
               String name,
               String fileName) {
            this.user = user;
            this.path = path;
            this.name = name;
            this.fileName = fileName;
        }
    }

    public BeanResult create(Long userId, byte[] image, String name) {
        BeanResult beanResult = new BeanResult();
        if (image == null) {
            beanResult.setCode(ResultCode.RESULT_CODE_INVALID_IMAGE);
            return beanResult;
        }

        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        // add image here
        String fileName = UtilityFunction.Companion.getUUID();
        String pathString = propertyConfig.getImageFolder() + fileName;
        try {
            UtilityFunction.Companion.writeFileToPath(
                    pathString,
                    image
            );
        } catch (IOException e) {
            beanResult.setCode(ResultCode.RESULT_CODE_SAVE_IMAGE_FAIL);
            return beanResult;
        }

        // delete old avatar
        List<Avatar> avatarList = avatarRepository.findByAvailableAndUser(true, optionalUser.get());
        if (!avatarList.isEmpty()) {
            avatarList.forEach(avatar -> {
                avatar.setAvailable(false);
                avatar.setTsLastModified(System.currentTimeMillis());
            });
            avatarRepository.saveAll(avatarList);
        }

        return create(
                new Params(
                        optionalUser.get(),
                        pathString,
                        name,
                        fileName
                )
        );
    }

    public BeanResult download() {
        BeanResult beanResult = new BeanResult();

        BeanResult getSessionResult = grpcSessionService.getSession();
        if (getSessionResult.getCode() != ResultCode.RESULT_CODE_VALID
                || getSessionResult.getBean() == null
                || getSessionResult.getBean() instanceof BeanGrpcSession) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_LOGIN);
            return beanResult;
        }

        Optional<User> originalUser = userRepository.findById(
                ((BeanGrpcSession) getSessionResult.getBean()).getUserId()
        );
        if (!originalUser.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }
        Optional<Avatar> optionalAvatar = originalUser.get().getAvatarList()
                .stream()
                .filter(Avatar::getAvailable)
                .findFirst();

        if (!optionalAvatar.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_INVALID);
            return beanResult;
        }

        Avatar avatar = optionalAvatar.get();
        BeanAvatar avatarBean = (BeanAvatar) avatar.createBean();
        avatarBean.setContent(
                UtilityFunction.Companion.downloadFileFromPath(avatar.getPath())
        );

        beanResult.setBean(avatarBean);
        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        return beanResult;
    }
}
