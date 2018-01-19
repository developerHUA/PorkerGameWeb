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
    private List<Room> onLineRooms = new ArrayList<>(); // 当前在使用的房间
    private List<Room> offLineRooms = new ArrayList<>(); // 已经不再使用的房间
    @Autowired
    UserService userService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Result createRoom(@RequestBody Params<BaseParams> params) {
        User user = userService.loadUserByUserId(params.getParams().getUserId());
        if (user.getToken().equals(params.getParams().getToken())) {
            if (offLineRooms.size() == 0) {
                Room room = new Room();
                if (onLineRooms.size() == 0) {
                    room.setRoomNumber("100");
                } else {
                    room.setRoomNumber(onLineRooms.size() + offLineRooms.size() + 100 + "");
                }
                onLineRooms.add(room);
            } else {
                onLineRooms.add(offLineRooms.get(0));
                offLineRooms.remove(0);
            }
            Room onLineRoom = onLineRooms.get(onLineRooms.size() - 1);
            List<User> users = new ArrayList<>();
            users.add(user);
            onLineRoom.setUsers(users);
            logger.debug(user.getUserId() + " 创建了一个房间：" + onLineRoom.getRoomNumber() +
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
                if (onLineRoom.getRoomNumber().equals(room.getRoomNumber())) {
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

    @RequestMapping(value = "/exit", method = RequestMethod.POST)
    @ResponseBody
    public void exitRoom(@RequestBody Params<Room> params) {

    }



}
