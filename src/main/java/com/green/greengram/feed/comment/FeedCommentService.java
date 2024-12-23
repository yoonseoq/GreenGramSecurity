package com.green.greengram.feed.comment;

import com.green.greengram.config.security.AuthenticationFacade;
import com.green.greengram.feed.comment.model.*;
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
    private final AuthenticationFacade authenticationFacade;

    public long postFeedComment(FeedCommentPostReq p){
        p.setUserId(authenticationFacade.getSignedUserId());
        mapper.insFeedComment(p);
        return p.getCommentId();
    }

    public FeedCommentGetRes getFeedComment(FeedCommentGetReq p){
        FeedCommentGetRes res = new FeedCommentGetRes();
        // 피드아이디랑 댓글 리스트

        if(p.getStartIdx() <0) { // sIdx 가 아모고토 없다는 의미
            res.setCommentList(new ArrayList<>()); // 그냥 빈 리스트만 만들어주고 팅김(리턴)
            return res;
        }


        // sIdx
        //일단 페이지 2부터 1~21사이 -> 댓글들 최대 20개 끌고오고 21번째는 isMore 설정
        List<FeedCommentDto> commentList = mapper.selFeedCommentList(p);
        res.setCommentList(commentList); // 댓글 리스트 설정
        res.setMoreComment(commentList.size()==p.getSize()); //아까 세팅한대로 디폴트사이즈 20+1
       // this.size = (size == null ? Constants.getDefault_page_size():size)+1; >> size 를 수동으로 입력한곳에 1을 더함
        if (res.isMoreComment()){ // 댓글 더보기가 되면
            commentList.remove(commentList.size() - 1);
            //21개의 댓글 리스트에서 거기서 1을 빼고 sIdx 부터 댓글 20개 부르기


        }
        return res;

    }
    public int delFeedComment(FeedCommentDelReq p){
        p.setUserId(authenticationFacade.getSignedUserId());
        return mapper.delFeedComment(p);
    }
}
