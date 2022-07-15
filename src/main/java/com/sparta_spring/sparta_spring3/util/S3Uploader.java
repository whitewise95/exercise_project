package com.sparta_spring.sparta_spring3.util;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.*;
import com.amazonaws.services.s3.model.*;
import com.sparta_spring.sparta_spring3.dto.FileUploadResponse;
import com.sparta_spring.sparta_spring3.util.file.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;
    private final AmazonS3 amazonS3;
    private final FileUpload fileUpload;
    private final FileUtils fileUtils;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket; // S3 버킷 이름

    /* MultipartFile와 path만 가지고 이미지 업로드 */
    public String upload(MultipartFile uploadFile, String path) {
        String imgName = path + fileUtils.makeFileName(uploadFile);
        File imageFile = fileUpload.multipartFileToFileWithUpload(uploadFile);
        String uploadImageUrl = putS3(imageFile, imgName);
        removeNewFile(imageFile);
        return new FileUploadResponse(uploadImageUrl).getUrl();
    }

    /* imagePath만 가지고 이미지 업로드 */
    public FileUploadResponse upload(String imagePath, String path) {
        String imgName = fileUtils.makeFileName();
        File imageFile = fileUpload.imageUploadToSever(path, imgName);
        String uploadImageUrl = putS3(imageFile, imagePath + imgName);
        removeNewFile(imageFile);
        return new FileUploadResponse(uploadImageUrl);
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile == null) {
            return;
        }

        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
            return;
        }
        log.info("파일 삭제 실패");
    }

    /* S3에서 이미지 찾기 */
    public S3Object selectImage(String path, String imageName) {
        S3Object s3Object = null;
        try {
            s3Object = amazonS3.getObject(new GetObjectRequest(bucket, path + imageName.substring(imageName.lastIndexOf("/") + 1)));
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return s3Object;
    }

    /* 이미지 삭제 */
    public void fileDelete(String imageNameWithPath) {
        try {
            amazonS3.deleteObject(bucket, imageNameWithPath);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        }
    }
}
