package com.yc.web.controllers;

import com.yc.bean.model.JsonModel;
import com.yc.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/*
 * 上传图片到 oss 云存储
 */
@RestController
@RequestMapping("/comment")
@Slf4j
public class UpPicture {

    @Autowired
    private FileService fileService;

    @PostMapping("/regFile")
    public JsonModel regFile(@RequestParam Map<String, MultipartFile> files) {
       try{
           for (Map.Entry<String, MultipartFile> entry : files.entrySet()) {
               String key = entry.getKey(); // 键 即是 文件名
               MultipartFile file = entry.getValue(); // 值
               log.info("文件名: " + key);
               fileService.upload(file);
           }
       }catch (Exception e){
           e.printStackTrace();
       }
       JsonModel jm = new JsonModel();
       jm.setCode(1);
       return jm;
    }
}
