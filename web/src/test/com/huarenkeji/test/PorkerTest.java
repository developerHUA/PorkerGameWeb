package com.huarenkeji.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.huarenkeji.porkergame.bean.DDZPorker;
import com.huarenkeji.porkergame.config.SocketConfig;

import java.util.List;


public class PorkerTest {


    public static void main(String[] args) {


        String json = "{\"params\":[{\"isClick\":true,\"porkerSize\":8,\"porkerId\":10,\"porkerType\":2},{\"isClick\":true,\"porkerSize\":8,\"porkerId\":10,\"porkerType\":4}],\"type\":2,\"uid\":9}";

        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("params");
        System.out.println(jsonArray.toJSONString());
        String arrayJson = JSONObject.toJSONString(jsonArray, SerializerFeature.WriteClassName);//将array数组转换成字符串
        System.out.println(arrayJson);
        List<DDZPorker> playPorker = JSON.parseArray(jsonArray.toJSONString(), DDZPorker.class);
        System.out.println(playPorker);
    }

}
