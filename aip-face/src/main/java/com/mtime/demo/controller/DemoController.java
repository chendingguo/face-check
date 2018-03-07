package com.mtime.demo.controller;

import com.mtime.demo.model.ResultDataModel;
import com.mtime.demo.model.User;
import com.mtime.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getUsers")
    @ResponseBody
    public ResultDataModel<User> getDepartment(
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "asc") String sortOrder,
            @RequestParam(value = "sortName", required = false, defaultValue = "age") String sortName) {
        ResultDataModel<User> resultDataModel = new ResultDataModel<>();
        User user = new User();
        user.setLimit(limit);
        user.setOffset(offset);
        if (!StringUtils.isEmpty(name)) {
            user.setName(name);
        }
        List<User> rows = userService.getUsers(user);

        Comparator<User> comparator = (h1, h2) -> h1.getAge().compareTo(h2.getAge());
        if (sortOrder.equalsIgnoreCase("asc")) {
            rows.sort(comparator);
        } else {
            rows.sort(comparator.reversed());
        }


        resultDataModel.setRows(rows);
        resultDataModel.setTotal(userService.getUserCount(user));

        return resultDataModel;
    }


    @RequestMapping(value = "/batch/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object handleFileUpload(HttpServletRequest request) {

        HashMap<String, Object> filesMap = new HashMap();
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("files[]");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        List<HashMap> fileInfoList=new ArrayList<>();
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();

                    HashMap<String, String> fileMap = new HashMap<String, String>();
                    fileMap.put("thumbnailUrl", file.getOriginalFilename());
                    fileMap.put("url", file.getOriginalFilename());
                    fileMap.put("name", file.getOriginalFilename());
                    fileInfoList.add(fileMap);
                } catch (Exception e) {
                    stream = null;
                    return "You failed to upload " + i + " => "
                            + e.getMessage();

                }
            } else {
                return "You failed to upload " + i
                        + " because the file was empty.";
            }

        }
        filesMap.put("files", fileInfoList);
        return filesMap;

    }


}





