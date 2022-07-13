package com.sparta_spring.sparta_spring3.dto;

import lombok.*;

@Getter
@Setter
public class FileUploadResponse {

    String fileName;
    String url;

    public FileUploadResponse(String fileName, String url) {
        this.fileName = fileName;
        this.url = url;
    }

    public FileUploadResponse(String url) {
        this.url = url;
    }
}
