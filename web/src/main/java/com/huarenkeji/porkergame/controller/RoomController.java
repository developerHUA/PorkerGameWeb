package com.huarenkeji.porkergame.controller;

import com.huarenkeji.porkergame.base.BaseParams;
import com.huarenkeji.porkergame.base.Params;
import com.huarenkeji.porkergame.base.Result;
import com.huarenkeji.porkergame.bean.Room;
import com.huarenkeji.porkergame.bean.User;
import com.huarenkeji.porkergame.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/room")
public class RoomController {
    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
    private static List<Room> onLineRooms = new ArrayList<>(); // 当前在使用的房间
    private static List<Room> offLineRooms = new ArrayList<>(); // 已经不再使用的房间
    @Autowired
    UserService userService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Result createRoom(@RequestBody Params<Room> params) {
        User user = userService.loadUserByUserId(params.getParams().getUserId());
        Room room = params.getParams();
        if (user.getToken().equals(params.getParams().getToken())) {
            if (offLineRooms.size() == 0) {
                if (onLineRooms.size() == 0) {
                    room.setRoomNumber(100);
                } else {
                    room.setRoomNumber(onLineRooms.size() + offLineRooms.size() + 100);
                }
                onLineRooms.add(room);
            } else {
                room.setRoomNumber(offLineRooms.get(0).getRoomNumber());
                onLineRooms.add(room);
                offLineRooms.remove(0);
            }
            Room onLineRoom = onLineRooms.get(onLineRooms.size() - 1);
            List<User> users = new ArrayList<>();
            users.add(user);
            onLineRoom.setUsers(users);
            logger.debug(user.getNickname() + " 创建了一个房间：" + onLineRoom.getRoomNumber() +
                    "当前房间数：" + onLineRooms.size());
            return Result.getSuccessResult(onLineRoom);
        } else {
            return Result.getInValidTokenResult();
        }

    }


    @RequestMapping(value = "/join", method = RequestMethod.POST)
    @ResponseBody
    public Result joinRoom(@RequestBody Params<Room> params) {
        Room room = params.getParams();
        User user = userService.loadUserByUserId(params.getParams().getUserId());
        if (user.getToken().equals(params.getParams().getToken())) {
            for (Room onLineRoom : onLineRooms) {
                if (onLineRoom.getRoomNumber() == room.getRoomNumber()) {
                    onLineRoom.getUsers().add(user);
                    logger.debug(user.getUserId() + " 加入房间：" + onLineRoom.getRoomNumber());
                    return Result.getSuccessResult(onLineRoom);
                }
            }
        } else {
            return Result.getInValidTokenResult();
        }
        return Result.getNoSearchResult();
    }

    @RequestMapping(value = "/exitRoom", method = RequestMethod.POST)
    @ResponseBody
    public Result exitRoom(@RequestBody Params<Room> params) {

        int userId = params.getParams().getUserId();

        User user = userService.loadUserByUserId(userId);
        if (user.getToken().equals(params.getParams().getToken())) {
            int roomNumber = params.getParams().getRoomNumber();
            for (int j = 0; j < onLineRooms.size(); j++) {
                if (onLineRooms.get(j).getRoomNumber() == roomNumber) {

                    logger.debug("当前房间剩余用户" + onLineRooms.get(j).getUsers().size());
                    for (int i = 0; i < onLineRooms.get(j).getUsers().size(); i++) {
                        if (onLineRooms.get(j).getUsers().get(i).getUserId() == userId) {
                            onLineRooms.get(j).getUsers().remove(i);
                            break;
                        }
                    }
                    logger.debug("当前房间剩余用户" + onLineRooms.get(j).getUsers().size());

                    if (onLineRooms.get(j).getUsers().size() == 0) {
                        offLineRooms.add(onLineRooms.get(j));
                        onLineRooms.remove(j);
                    }

                    return Result.getSuccessResult();
                }
            }


        } else {
            return Result.getInValidTokenResult();
        }

        return Result.getNoRoomResult();
    }


    public static Room getRoom(int roomNumber) {

        for (Room room : onLineRooms) {
            if(roomNumber == room.getRoomNumber()) {
                return room;
            }
        }

        return null;

    }


}
