package com.green.greengram.feed.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FeedPicDto {
    //데이터 전달용 값넣는 방법 생성자나 세터 빼는방법은 게터
    // feed_pics 테이블에 튜플 여러개를 insert
    private long feedId;
    private List<String> pics;
}

