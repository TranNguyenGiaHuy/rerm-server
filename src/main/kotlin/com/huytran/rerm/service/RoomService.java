package com.huytran.rerm.service;

import com.huytran.rerm.model.Room;
import com.huytran.rerm.repository.RoomRepository;
import com.huytran.rerm.service.core.CoreService;
import org.springframework.stereotype.Service;

@Service
public class RoomService extends CoreService<Room, RoomRepository, RoomService.Params> {

    private RoomRepository roomRepository;

    RoomService(
            RoomRepository roomRepository
    ) {
        super(roomRepository);
        this.roomRepository = roomRepository;
    }

    @Override
    public Room createModel() {
        return new Room();
    }

    @Override
    public void parseParams(Room room, Params params) {
        room.setSquare(params.square);
        room.setAddress(params.address);
        room.setPrice(params.price);
        room.setType(params.type);
        room.setNumberOfFloor(params.numberOfFloor);
        room.setHasFurniture(params.hasFurniture);
        room.setMaxMember(params.maxMember);
        room.setCookingAllowance(params.cookingAllowance);
        room.setHomeType(params.homeType);
        room.setPrepaid(params.prepaid);
        room.setDescription(params.description);
    }

    public class Params extends CoreService.AbstractParams {
        Float square;
        String address;
        Long price;
        Integer type;
        Integer numberOfFloor;
        Boolean hasFurniture;
        Integer maxMember;
        Boolean cookingAllowance;
        Integer homeType;
        Long prepaid;
        String description;
    }

}
