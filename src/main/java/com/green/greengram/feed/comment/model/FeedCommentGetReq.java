package com.green.greengram.feed.comment.model;

import com.green.greengram.common.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

import java.beans.ConstructorProperties;


@Getter
@Schema(title = "피드 댓글 리스트 요청")
public class FeedCommentGetReq {
    //public final int DEFAULT_PAGE_SIZE = 20; Constant 에서 값을 가져옴
    private final static int   FIRST_COMMENT_SIZE = 3;
    /*
     private 붙으면 게터세터 옶으면 아모고토 못함
     (다른 클래스에서 해당 멤버필드를 소환하거나 그  클래스에서 따로설정)
     static 으로 도ㅣ면 모든 객체값 클래스 수준으로 공유여부
     final 이렇게 박제 해 놓은 건 한번 초기화한 이후 바뀔 수 없다
     */
    @Positive // 피드아이디는 양수여야함
    @Schema(title = "피드 PK",description = "피드 PK",example = "1", name = "feed_id"
            ,requiredMode = Schema.RequiredMode.REQUIRED)
    private long feedId;

    @PositiveOrZero
    @Schema(title = "튜플시작 index",description = "댓글, Element 갯수를 보내주면 된다",name = "start_idx"
            , example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
    // private int page; 페이지는 필요없어짐
    private int startIdx;

    @Min(value = 20, message = "사이즈는 20이상이어야 합니다" )
    @Schema(title = "페이지당 아이템 수", description = "default = 20", example = "20")
    private int size;


    @ConstructorProperties({"feed_id","start_idx","size"})
    public FeedCommentGetReq(long feedId, int startIdx, Integer size) {
        this.feedId = feedId;
        this.startIdx = startIdx;
        this.size = (size == null ? Constants.getDefault_page_size():size)+1;
    }
}
