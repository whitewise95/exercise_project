package com.sparta_spring.sparta_spring3.domain;

import com.sparta_spring.sparta_spring3.domain.resultType.Timestamped;
import com.sparta_spring.sparta_spring3.dto.RequestBlogDto;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String password;

    @Column
    private String imgUrl;

    @Column(nullable = false)
    private String writer;

    public void update(RequestBlogDto requestBlogDto) {
        this.content = requestBlogDto.getContent();
        this.writer = requestBlogDto.getWriter();
    }

    public void updateWiteImage(RequestBlogDto requestBlogDto) {
        this.imgUrl = requestBlogDto.getImgUrl();
        this.content = requestBlogDto.getContent();
        this.writer = requestBlogDto.getWriter();
    }
}