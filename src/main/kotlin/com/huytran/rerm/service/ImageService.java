package com.huytran.rerm.service;

import com.huytran.rerm.model.Image;
import com.huytran.rerm.model.Room;
import com.huytran.rerm.repository.ImageRepository;
import com.huytran.rerm.repository.RoomRepository;
import com.huytran.rerm.service.core.CoreService;
import org.springframework.stereotype.Service;

@Service
public class ImageService extends CoreService<Image, ImageRepository, ImageService.Params> {

    private ImageRepository imageRepository;

    ImageService(
            ImageRepository imageRepository
    ) {
        super(imageRepository);
        this.imageRepository = imageRepository;
    }

    @Override
    public Image createModel() {
        return new Image();
    }

    @Override
    public void parseParams(Image image, Params params) {
        image.setRoomId(params.roomId);
    }

    public class Params extends CoreService.AbstractParams {
        Long roomId;
    }

}
