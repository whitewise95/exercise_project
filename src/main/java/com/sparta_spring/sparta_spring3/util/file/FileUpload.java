package com.sparta_spring.sparta_spring3.util.file;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

import static com.sparta_spring.sparta_spring3.domain.resultType.FileUploadType.UBUNTU_BASE_PATH;

@Component
public class FileUpload {

    /* multipartFile을 file로 변환 하는 작업 */
    public File multipartFileToFileWithUpload(MultipartFile uploadFile) {
        File file = null;
        FileOutputStream fos = null;
        try {
            file = imageUploadToSever(uploadFile);
            file.createNewFile();
            fos = new FileOutputStream(file);
            fos.write(uploadFile.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("S3Uploader 121 에러" + e.getMessage());
        } finally {
            close(fos);
        }
        return file;
    }

    /* 우분투 환경와 window환경에서 이미지업로드 */
    public File imageUploadToSever(MultipartFile uploadFile) {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.startsWith("windows")) {
            return new File(new File("").getAbsolutePath() + "\\" + uploadFile.getOriginalFilename());
        }

        File file = new File(UBUNTU_BASE_PATH.getPath());
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(UBUNTU_BASE_PATH.getPath() + "/" + uploadFile.getOriginalFilename());
    }

    public File imageUploadToSever(String imagePath, String imageName) {
        File file = null;

        String os = System.getProperty("os.name").toLowerCase();
        BufferedImage image = null;
        try {
            image = ImageIO.read(new URL(imagePath));
            if (os.startsWith("windows")) {
                file = new File(new File("").getAbsolutePath() + "\\" + imageName);
            } else {
                file = new File(UBUNTU_BASE_PATH.getPath());
                if (!file.exists()) {
                    file.mkdirs();
                }
                file = new File(UBUNTU_BASE_PATH.getPath() + "/" + imageName);
            }

            ImageIO.write(image, imageName.substring(imageName.lastIndexOf(".") + 1), file);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return file;
    }

    /* close 예외처리 */
    public void close(FileOutputStream fos) {
        if (fos == null)
            return;
        try {
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException("S3Uploader 137 에러" + e.getMessage());
        }
    }
}
