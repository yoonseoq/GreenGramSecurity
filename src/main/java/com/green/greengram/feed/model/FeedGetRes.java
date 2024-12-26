package com.green.greengram.feed.model;

import com.green.greengram.feed.comment.model.FeedCommentGetRes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@Schema(title = "피드 정보")
@NoArgsConstructor
@ToString
public class FeedGetRes {
    private long feedId;
    private String contents;
    private String location;
    private String createdAt;
    private long writerUserId;
    private String writerNm;
    private String writerPic;
    private int isLike;

    private List<String> pics ;
    private FeedCommentGetRes comment;
    //댓글관련정보 레퍼런스 변수 FeedCommentGetRes 의 주소값이 들어간다 즉 객체화함

    public FeedGetRes(FeedPicCommentDto dto) {
        this.feedId = dto.getFeedId();
        this.contents = dto.getContents();
        this.location = dto.getLocation();
        this.createdAt = dto.getCreatedAt();
        this.writerUserId = dto.getWriterUserId();
        this.writerNm = dto.getWriterNm();
        this.writerPic = dto.getWriterPic();
        this.isLike = dto.getIsLike();
        this.pics = dto.getPics();
        this.comment = new FeedCommentGetRes();
        // 이제 무슨작업해야하나? moreComment?
        // TODO: 댓글 moreComment, list 컨버트
        if (dto.getCommentList().size()==4){
            comment.setMoreComment(true);
        };
    }
}
