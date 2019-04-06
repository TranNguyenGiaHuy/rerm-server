package com.huytran.rerm.service;

import com.huytran.rerm.bean.core.BeanList;
import com.huytran.rerm.bean.core.BeanResult;
import com.huytran.rerm.config.PropertyConfig;
import com.huytran.rerm.constant.ResultCode;
import com.huytran.rerm.model.Image;
import com.huytran.rerm.model.Room;
import com.huytran.rerm.repository.ImageRepository;
import com.huytran.rerm.repository.RoomRepository;
import com.huytran.rerm.service.core.CoreService;
import com.huytran.rerm.utilities.UtilityFunction;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService extends CoreService<Image, ImageRepository, ImageService.Params> {

    private ImageRepository imageRepository;
    private RoomRepository roomRepository;
    private PropertyConfig propertyConfig;

    ImageService(
            ImageRepository imageRepository,
            RoomRepository roomRepository,
            PropertyConfig propertyConfig
    ) {
        super(imageRepository);
        this.imageRepository = imageRepository;
        this.roomRepository = roomRepository;
        this.propertyConfig = propertyConfig;
    }

    @Override
    public Image createModel() {
        return new Image();
    }

    @Override
    public void parseParams(Image image, Params params) {
        image.setRoom(params.room);
        image.setPath(params.path);
        image.setName(params.name);
    }

    public class Params extends CoreService.AbstractParams {
        Room room;
        String path;
        String name;

        Params(Room room,
               String path,
               String name) {
            this.room = room;
            this.path = path;
            this.name = name;
        }
    }

    public BeanResult create(Long roomId, byte[] image, String name) {
        BeanResult beanResult = new BeanResult();
        if (image == null) {
            beanResult.setCode(ResultCode.RESULT_CODE_INVALID_IMAGE);
            return beanResult;
        }

        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (!optionalRoom.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        // add image here
        String pathString = propertyConfig.getImageFolder() + UtilityFunction.Companion.getUUID();
        Path path = Paths.get(
                pathString
        );
        try {
            Files.write(path, image);
        } catch (IOException e) {
            e.printStackTrace();
            beanResult.setCode(ResultCode.RESULT_CODE_SAVE_IMAGE_FAIL);
            return beanResult;
        }

        return create(
                new Params(
                        optionalRoom.get(),
                        pathString,
                        name
                )
        );
    }

    public byte[] download(Long id) {
        Optional<Image> optionalImage = imageRepository.findById(id);
        if (!optionalImage.isPresent()) {
            return new byte[0];
        }

        try {
            Path path = Paths.get(optionalImage.get().getPath());
            ByteArrayResource byteArrayResource = new ByteArrayResource(Files.readAllBytes(path));
            return byteArrayResource.getByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public BeanResult getAllOfRoom(Long id) {
        BeanResult beanResult = new BeanResult();
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (!optionalRoom.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        Room room = optionalRoom.get();
        // get all file
        List<Image> imageList = room.getImageList();
        beanResult.setBean(
                new BeanList(
                        imageList
                )
        );
        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        return beanResult;
    }
}
