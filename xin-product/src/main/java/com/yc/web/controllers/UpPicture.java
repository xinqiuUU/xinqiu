package com.yc.web.controllers;

import com.yc.bean.model.JsonModel;
import com.yc.service.FileService;
import com.yc.utils.PicUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/*
 * 上传图片到 oss 云存储
 */
@RestController
@RequestMapping("/product/admin")
@Slf4j
public class UpPicture {

    @Autowired
    private FileService fileService;

    @PostMapping("/regFile")
    public JsonModel regFile(@RequestParam Map<String, MultipartFile> files) {
        /**
         * 用PicUrl存图片前应该先把它清空
         */
        PicUrl.clearUrls();
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
