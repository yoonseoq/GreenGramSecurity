package com.green.greengramver2.feed.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.greengramver2.common.model.Paging;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedCommentGetReq {
    public final int DEFAULT_PAGE_SIZE = 20;
    public final int   FIRST_COMMENT_SIZE = 3;
    /*
     private 붙으면 게터세터 옶으면 아모고토 못함
     (다른 클래스에서 해당 멤버필드를 소환하거나 그  클래스에서 따로설정)
     static 으로 도ㅣ면 모든 객체값 클래스 수준으로 공유여부
     final 이렇게 박제 해 놓은 건 한번 초기화한 이후 바뀔 수 없다
     */

    @Schema(title = "피드 PK", example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private long feedId;
    @JsonIgnore
    private int sIdx;
    @JsonIgnore
    private int size;

    public void setPage(int page) {
        if (page < 1) {
            return; // 그냥 튕겨버림 void 여서 0이나 음수값이면 아예 세팅 ㄴㄴ
        }
        if (page == 1) { // 첫번째 사이즈 설정
            sIdx = 0;
            size = FIRST_COMMENT_SIZE + 1; // +1은 isMore 처리
            return;
        }
        sIdx = (page - 2) * DEFAULT_PAGE_SIZE + FIRST_COMMENT_SIZE;
        size = DEFAULT_PAGE_SIZE + 1; // +1 은 즉 댓글 더 불러오기 역할 isMore
    }
}
