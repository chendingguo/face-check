package com.mtime.demo.controller;

import com.mtime.demo.model.ResultDataModel;
import com.mtime.demo.model.User;
import com.mtime.demo.model.UserInfo;
import com.mtime.demo.service.FaceCheckService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;


@Controller
public class UserController {

    private Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private FaceCheckService faceCheckService;


    @RequestMapping("/uploadImg")
    @ResponseBody
    public String uploadImg(
            @RequestParam(value = "imgData", required = false, defaultValue = "") String imgData) {


        faceCheckService.generateImage(imgData.substring(22));

        return "";
    }

    /**
     * user regitser
     *
     * @param imgData
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public String register(@RequestParam(value = "imgData", required = false, defaultValue = "") String imgData,
                           @RequestParam(value = "userName", required = false, defaultValue = "") String userName) {
        String image = faceCheckService.generateImage(imgData.substring(22));
        if (!StringUtils.isEmpty(image)) {
            return faceCheckService.addUser(userName, image).toString();

        } else {
            return "failed";
        }

    }

    @RequestMapping("/getUsers")
    @ResponseBody
    public ResultDataModel<User> getUsers(
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset
    ) {
        UserInfo userInfo = new UserInfo();
        userInfo.setLimit(limit);
        userInfo.setOffset(offset);

        ResultDataModel resultDataModel = faceCheckService.getUsers(userInfo);
        return resultDataModel;

    }

    @RequestMapping("/deleteUser")
    @ResponseBody
    public String deleteUser(
            @RequestParam(value = "userId", required = false, defaultValue = "") String userId
    ) {

        String result = faceCheckService.deleteUser(userId);
        return result;

    }

    /**
     * 用于计算指定组内用户，与上传图像中人脸的相似度。识别前提为您已经创建了一个人脸库。
     * <p>
     * 典型应用场景：如人脸闸机，考勤签到，安防监控等。
     *
     * @param imgData
     * @return
     */
    @RequestMapping("/identifyUser")
    @ResponseBody
    public String identifyUser(
            @RequestParam(value = "imgData", required = false, defaultValue = "") String imgData) {
        String image = faceCheckService.generateImage(imgData.substring(22));
        String result = "图片捕捉失败";
        if (!StringUtils.isEmpty(image)) {
            result = faceCheckService.identifyUser(image);

        }
        return result;


    }


//    @RequestMapping("/getUserInfo")
//    @ResponseBody
//    public User getUserInfo() {
//        User user = userService.getUserInfo();
//        if(user!=null){
//            System.out.println("user.getName():"+user.getName());
//            logger.info("user.getAge():"+user.getAge());
//        }
//        return user;
//    }
}
