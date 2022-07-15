package com.sparta_spring.sparta_spring3.controller;

import com.sparta_spring.sparta_spring3.domain.User;
import com.sparta_spring.sparta_spring3.service.CommonService;
import com.sparta_spring.sparta_spring3.util.S3Uploader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static com.sparta_spring.sparta_spring3.domain.resultType.FileUploadType.BOARD;
import static com.sparta_spring.sparta_spring3.security.FormLoginSuccessHandler.AUTH_HEADER;

@Controller
public class CommonController {

    private final CommonService commonService;
    private final S3Uploader s3Uploader;

    public CommonController(CommonService commonService, S3Uploader s3Uploader) {
        this.commonService = commonService;
        this.s3Uploader = s3Uploader;
    }

    @ResponseBody
    @RequestMapping("/common/fileUpload")
    public String fileUpload(@RequestParam("file") MultipartFile file) {
        return s3Uploader.upload(file, BOARD.getPath());
    }

    @ResponseBody
    @RequestMapping("/test")
    public String test(final HttpServletResponse response, User user) {
        if (Optional.ofNullable(response.getHeader(AUTH_HEADER)).isPresent()) {
            return "로그인되었습니다.";
        } else {
            return "로그인되었습니다.";
        }
    }

}
