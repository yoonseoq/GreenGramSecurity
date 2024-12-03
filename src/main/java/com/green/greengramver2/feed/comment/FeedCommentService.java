package com.green.greengramver2.feed.comment;

import com.green.greengramver2.feed.comment.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedCommentService {
    private final FeedCommentMapper mapper;

    public long postFeedComment(FeedCommentPostReq p){
        mapper.insFeedComment(p);
        return p.getCommentId();
    }

    public FeedCommentGetRes getFeedComment(FeedCommentGetReq p){
        FeedCommentGetRes res = new FeedCommentGetRes();
        if(p.getPage() < 2) {
            res.setCommentList(new ArrayList<>());
            return res;
        }


        // sIdx
        //일단 페이지 2부터 댓글들 최대 20개 끌고오고 21번째는 isMore 설정
        List<FeedCommentDto> commentList = mapper.selFeedCommentList(p);
        res.setCommentList(commentList); // 댓글 리스트 설정
        res.setMoreComment(commentList.size()==p.getSize());
        if (res.isMoreComment()){
            commentList.remove(commentList.size() - 1);

        }
        return res;

    }
    public int delFeedComment(FeedCommentDelReq p){
        return mapper.delFeedComment(p);
    }
}
