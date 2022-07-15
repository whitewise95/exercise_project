package com.sparta_spring.sparta_spring3.dto;

import com.sparta_spring.sparta_spring3.domain.Board;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RequestBlogDto {
    private String content;
    private String writer;
    private String password;
    private String imgUrl;

    public RequestBlogDto(String content, String writer, String password, String imgUrl) {
        this.content = content;
        this.writer = writer;
        this.password = password;
        this.imgUrl = imgUrl;
    }

    public RequestBlogDto(Board content) {
        this.content = content.getContent();
        this.writer = content.getUser().getId() + "";
        this.password = content.getPassword();
        this.imgUrl = content.getImgUrl();
    }
}
