package com.mtime.demo.service;

import com.baidu.aip.face.AipFace;
import com.mtime.demo.model.ResultDataModel;
import com.mtime.demo.model.UserInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class FaceCheckService {
    @Autowired
    ClientManager clientManager;

    @Value("${image.path}")
    private String imagePath;






    public JSONObject matchFace() {
        AipFace client=clientManager.getAipFaceClient( );

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("ext_fields", "qualities");
        options.put("image_liveness", ",faceliveness");
        options.put("types", "7,13");

        //参数为本地图片路径列表
        String path1 = imagePath + "sample1.jpg";
        String path2 = imagePath + "sample2.jpg";
        ArrayList<String> images = new ArrayList<String>();
        images.add(path1);
        images.add(path2);
        JSONObject res =client.match(images, options);
        System.out.println(res.toString(2));
        return res;
    }

    public JSONObject addUser(String userName, String image) {
        // 传入可选参数调用接口
        AipFace client=clientManager.getAipFaceClient( );
        HashMap<String, String> options = new HashMap<>();
        options.put("action_type", "replace");
        int idx = image.lastIndexOf("/");
        String uid = image.substring(idx + 1, image.length()).replace(".jpg","");
        String userInfo = userName;
        String groupId = "group1";

        JSONObject res =client.addUser(uid, userInfo, groupId, image, options);
        System.out.println(res.toString(2));




        return res;

    }

    public ResultDataModel<UserInfo> getUsers(UserInfo userInfo){
        AipFace client=clientManager.getAipFaceClient( );
        ResultDataModel resultDataModel=new ResultDataModel();

        String groupId = "group1";
        HashMap<String, String> options= new HashMap<String, String>();
        options.put("start", "0");
        options.put("end", "50");
        // 组内用户列表查询
        JSONObject res =client.getGroupUsers(groupId, options);

        long total= res.getLong("result_num");
        List<UserInfo> uiList=new ArrayList<>();
        JSONArray array=res.getJSONArray("result");
        for(int i=0;i<array.length();i++){
            UserInfo ui=new UserInfo();
            JSONObject obj=array.getJSONObject(i);
            String userId=obj.getString("uid");
            String info=obj.getString("user_info");
            ui.setId(userId);
            ui.setName(info);
            String images="/images/"+userId+".jpg";
            ui.setImage(images);
            uiList.add(ui);

        }
        resultDataModel.setRows(uiList);
        resultDataModel.setTotal(total);
        System.out.println(res.toString(2));

        return  resultDataModel;
    }

    public String deleteUser(String userId){
        AipFace client=clientManager.getAipFaceClient( );
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<>();
        options.put("group_id", "group1");

        // 人脸删除
        JSONObject res =client.deleteUser(userId, options);
        return res.toString(2);
    }


    /**
     * @param imgStr base64编码字符串
     * @return
     * @Description: 将base64编码字符串转换为图片
     * @Author:
     * @CreateTime:
     */
    public String generateImage(String imgStr) {
        if (imgStr == null) return "";
        String photoPath = imagePath + System.currentTimeMillis() + ".jpg";
        BASE64Decoder decoder = new BASE64Decoder();
        try {
// 解密
            byte[] b = decoder.decodeBuffer(imgStr);
// 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(photoPath);
            out.write(b);
            out.flush();
            out.close();
            return photoPath;
        } catch (Exception e) {
            return "";
        }
    }


}
