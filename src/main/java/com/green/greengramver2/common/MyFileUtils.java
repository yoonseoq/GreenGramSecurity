package com.green.greengramver2.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
@Slf4j
@Component
public class MyFileUtils {
    private final String uploadPath;

    /*
    @Value("${file.directory}")은
    yaml 파일에 있는 file.directory 속성에 저장된 값을 생성자 호출할 때 값을 넣어준다.
     */
    public MyFileUtils(@Value("${file.directory}") String uploadPath) {
        // file.directory = directory: D:/download/yoonstagram

        log.info("MyFileUtils - 생성자: {}", uploadPath);
        this.uploadPath = uploadPath;
    }

    // path = "ddd/aaa"                  /user/pk or feed/pk
    // D:/2024-02/download/greengram_ver1/ddd/aaa
    //디렉토리 생성

 // 어떤구간에 파일을 이동시키고 싶어요
    //폴더가 없으묜 에러가 터져서 그래서 미리 폴더를 만듦
    public String makeFolders(String path) {
        File file = new File(uploadPath, path);
        //생성자 호출됨
        //파라미터가 두개값이 들어간다? 두개를 합친다?
        // 중간에 자동으로 /이게 들어간다 (uploadPath / path)
        // 만들고 보면 확장자가 없음 즉 파일의 경로를 만들어줌

        if (!file.exists()){
            //       if (!file.exists()) 거짓일때 실
            file.mkdirs();// 존재한다면
        }     // mkdir mkdirs 차이
        return file.getAbsolutePath();
        // getAbsolutePath() 절대주소를 가져온다는 의미인가?
    }
    //파일명에서 확장자 추출
    public String getExt(String fileName) {
        int lastIdx = fileName.lastIndexOf(".");
        return fileName.substring(lastIdx);
    }

    //랜덤파일명 생성
    public String makeRandomFileName() {
        return UUID.randomUUID().toString();
    }

    //랜덤파일명 + 확장자 생성하여 리턴
    public String makeRandomFileName(String originalFileName) {
        return makeRandomFileName() + getExt(originalFileName);
    }

    public String makeRandomFileName(MultipartFile file) {
        return makeRandomFileName(file.getOriginalFilename());
    }

    //파일을 원하는 경로에 저장 //
    public void transferTo(MultipartFile mf, String path) throws IOException {
        File file = new File(uploadPath, path);
        mf.transferTo(file);
        //윈도우에 다른 이름으로 저장. uploadPath + path 파일 근데  \D\download\캡쳐\hsdkshdaljsal.jpg
        // D:\download\캡쳐\hsdkshdaljsal.jpg
         //          폴더명  여기부터 파일명
    }

}


