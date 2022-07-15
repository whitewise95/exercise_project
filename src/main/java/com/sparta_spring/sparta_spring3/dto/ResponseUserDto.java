package com.sparta_spring.sparta_spring3.dto;

import lombok.*;

@Getter
@NoArgsConstructor
public class ResponseUserDto {
    private String content;
    private String writer;
    private String password;
    private String imgUrl;
}
