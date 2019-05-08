package com.huytran.rerm.service;

import com.huytran.rerm.bean.BeanImage;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
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
        String fileName;

        Params(Room room,
               String path,
               String name,
               String fileName) {
            this.room = room;
            this.path = path;
            this.name = name;
            this.fileName = fileName;
        }
    }

    public static class ImageParams {
        byte[] content;
        String name;

        public ImageParams(byte[] content, String name) {
            this.content = content;
            this.name = name;
        }
    }

    public BeanResult create(Long roomId, ImageParams imageParams) {
        BeanResult beanResult = new BeanResult();
        if (imageParams.content == null) {
            beanResult.setCode(ResultCode.RESULT_CODE_INVALID_IMAGE);
            return beanResult;
        }

        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (!optionalRoom.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        // add image here
        String fileName = UtilityFunction.Companion.getUUID();
        String pathString = propertyConfig.getImageFolder() + fileName;
        try {
            UtilityFunction.Companion.writeFileToPath(
                    pathString,
                    imageParams.content
            );
        } catch (IOException e) {
            beanResult.setCode(ResultCode.RESULT_CODE_SAVE_IMAGE_FAIL);
            return beanResult;
        }

        return create(
                new Params(
                        optionalRoom.get(),
                        pathString,
                        imageParams.name,
                        fileName
                )
        );
    }

    public BeanResult create(Long roomId, List<ImageParams> imageParamsList) {
        BeanResult beanResult = new BeanResult();

        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (!optionalRoom.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        List<Image> uploadedImage = new ArrayList<>();
        imageParamsList.forEach(imageParams -> {

            // add image here
            String fileName = UtilityFunction.Companion.getUUID();
            String pathString = propertyConfig.getImageFolder() + fileName;
            try {
                UtilityFunction.Companion.writeFileToPath(
                        pathString,
                        imageParams.content
                );
            } catch (IOException e) {
                return;
            }

            Image image = new Image();
            parseParams(
                    image,
                    new Params(
                            optionalRoom.get(),
                            pathString,
                            imageParams.name,
                            fileName
                    )
            );
            uploadedImage.add(image);
        });

        imageRepository.saveAll(uploadedImage);
        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        return beanResult;
    }

    public BeanResult download(Long id) {
        BeanResult beanResult = new BeanResult();

        Optional<Image> optionalImage = imageRepository.findById(id);

        if (!optionalImage.isPresent()) {
            beanResult.setCode(ResultCode.RESULT_CODE_NOT_FOUND);
            return beanResult;
        }

        Image image = optionalImage.get();
        BeanImage beanImage = (BeanImage) image.createBean();

        beanImage.setContent(
                UtilityFunction.Companion.downloadFileFromPath(image.getPath())
        );

        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        beanResult.setBean(beanImage);
        return beanResult;
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
        List<Image> imageList = imageRepository.findByRoomId(room.getId());
        beanResult.setBean(
                new BeanList(
                        imageList
                )
        );
        beanResult.setCode(ResultCode.RESULT_CODE_VALID);
        return beanResult;
    }
}
