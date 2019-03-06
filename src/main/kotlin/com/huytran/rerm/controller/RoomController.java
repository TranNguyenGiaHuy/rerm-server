package com.huytran.rerm.controller;

import com.huytran.rerm.controller.core.CoreController;
import com.huytran.rerm.service.RoomService;
import org.springframework.web.bind.annotation.RestController;

@RestController("/room")
public class RoomController extends CoreController<RoomService, RoomService.Params> {

    private RoomService roomService;

    public RoomController(
            RoomService roomService
    ) {
        super(roomService);
        this.roomService = roomService;
    }

}
