package com.yc.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.yc.configuration.OSSConfig;
import com.yc.utils.PicUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class FileServiceImpl implements FileService{

    @Autowired
    private OSSConfig ossConfig;

    // 上传文件
//    @Async  // 异步调用
    @Override
    public CompletableFuture<String> upload(MultipartFile file) {
        String bucketName = ossConfig.getBucketName();
        String endpoint = ossConfig.getEndpoint();
        String accessKeyId = ossConfig.getAccessKeyId();
        String accessKeySecret = ossConfig.getAccessKeySecret();

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 原始文件名
        String originalFilename = file.getOriginalFilename();
        //  新文件名 生成唯一的文件名
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));

        PicUrl.addUrl("https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/"+originalFilename);

        String objectName = "product/" +originalFilename;// 上传到OSS的路径
        try{
            ossClient.putObject(bucketName, objectName, file.getInputStream());
        }catch (Exception e){
            log.error("上传文件失败", e);
            throw new RuntimeException("上传文件失败");
        }finally {
            ossClient.shutdown();
        }
        String path = "https://" + bucketName + "." + endpoint + "/" + objectName;
        log.info("上传文件成功，路径为：{}", path);
        return CompletableFuture.completedFuture(path);
    }

    // 批量上传
    @Override
    public List<String> upload(MultipartFile[] files) {
        List<String> paths = new ArrayList<>();
        for (MultipartFile file : files){
            CompletableFuture<String> future = upload(file);
            try{
                // 等待异步任务完成   get() 方法会阻塞当前线程，直到异步任务完成
                paths.add(future.get());
            }catch (Exception e){
                log.error("上传文件失败", e);
                throw new RuntimeException("上传文件失败");
            }
        }
        return paths; // 上传成功的路径
    }
}
