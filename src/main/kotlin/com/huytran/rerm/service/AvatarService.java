package com.huytran.rerm.service;

import com.huytran.rerm.bean.core.BeanList;
import com.huytran.rerm.bean.core.BeanResult;
import com.huytran.rerm.config.PropertyConfig;
import com.huytran.rerm.constant.ResultCode;
import com.huytran.rerm.model.Avatar;
import com.huytran.rerm.model.Image;
import com.huytran.rerm.model.Room;
import com.huytran.rerm.model.User;
import com.huytran.rerm.repository.AvatarRepository;
import com.huytran.rerm.repository.ImageRepository;
import com.huytran.rerm.repository.RoomRepository;
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

    AvatarService(
            AvatarRepository avatarRepository,
            UserRepository userRepository,
            PropertyConfig propertyConfig
    ) {
        super(avatarRepository);
        this.avatarRepository = avatarRepository;
        this.userRepository = userRepository;
        this.propertyConfig = propertyConfig;
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
    }

    public class Params extends CoreService.AbstractParams {
        User user;
        String path;
        String name;

        Params(User user,
               String path,
               String name) {
            this.user = user;
            this.path = path;
            this.name = name;
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
        String pathString = propertyConfig.getImageFolder() + UtilityFunction.Companion.getUUID();
        try {
            UtilityFunction.Companion.writeFileToPath(
                    pathString,
                    image
            );
        } catch (IOException e) {
            beanResult.setCode(ResultCode.RESULT_CODE_SAVE_IMAGE_FAIL);
            return beanResult;
        }

        return create(
                new Params(
                        optionalUser.get(),
                        pathString,
                        name
                )
        );
    }

    public byte[] download(Long id) {
        Optional<Avatar> optionalAvatar = avatarRepository.findById(id);
        return optionalAvatar.map(image ->
                UtilityFunction.Companion.downloadFileFromPath(image.getPath())
        )
                .orElseGet(() ->
                        new byte[0]
                );

    }
}
