package com.huytran.rerm.controller;

import com.huytran.rerm.controller.core.CoreController;
import com.huytran.rerm.service.ImageService;
import org.springframework.web.bind.annotation.RestController;

@RestController("/image")
public class ImageController extends CoreController<ImageService, ImageService.Params> {

    private ImageService imageService;

    public ImageController(
            ImageService imageService
    ) {
        super(imageService);
        this.imageService = imageService;
    }

}
