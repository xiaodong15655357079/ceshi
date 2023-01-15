package com.liheng.controller;


import io.minio.MinioClient;
import io.minio.PutObjectArgs;

import io.minio.errors.*;
import org.apache.http.entity.ContentType;

import org.simpleframework.xml.Transient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Transient
@RestController
@RequestMapping("minio")
public class MinioController {



    @Autowired
    private MinioClient client;


    @GetMapping("/upload")
    public String MinIOUpload(@RequestParam String  name) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        File file=new File("C:\\Users\\Hasee\\"+name+".mp4");


        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        //2. 文件上传  参数校验 和分片上传
        PutObjectArgs putObjectArgs = PutObjectArgs.builder().object(name + ".mp4")
                .bucket("xiaodongtest")
                .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                .contentType(multipartFile.getContentType())
                .build();
        client.putObject(putObjectArgs);
        System.out.println("http://192.168.136.160:9000/xiaodongtest/"+name+".mp4");
            return "成功";

    }



}
