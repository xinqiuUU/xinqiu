package com.yc.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/*
    文件上传   业务层接口
 */
public interface FileService {
    //处理单个文件上传并返回一个异步结果
    //因为是异步的，所以返回值是CompletableFuture
    public CompletableFuture<String> upload(MultipartFile file);
    public List<String> upload(MultipartFile[] files);
}
