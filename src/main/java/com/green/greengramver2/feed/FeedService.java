package com.green.greengramver2.feed;

import com.green.greengramver2.common.MyFileUtils;
import com.green.greengramver2.feed.comment.FeedCommentMapper;
import com.green.greengramver2.feed.comment.model.FeedCommentDto;
import com.green.greengramver2.feed.comment.model.FeedCommentGetReq;
import com.green.greengramver2.feed.comment.model.FeedCommentGetRes;
import com.green.greengramver2.feed.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class FeedService {
    private final FeedMapper feedMapper;
    private final MyFileUtils myFileUtils;
    private final FeedCommentMapper feedCommentMapper;
    private final FeedPicsMapper feedPicsMapper;

    @Transactional // 뭐지 알아보자
    public FeedPostRes postFeed(List<MultipartFile> pics, FeedPostReq p) {
        int result = feedMapper.insFeed(p);

        //--------------- 파일등록
        long feedId = p.getFeedId();

        //--------------- 저장폴더 만들기,저장위치/feed/${feedId}/파일들을 저장
        String middlePath = String.format("feed/%d", feedId);
        myFileUtils.makeFolders(middlePath);

        // 랜덤 파일명 저장용 >> feed_pics 테이블에 저장할때 사용
        List<String> picNameList = new ArrayList<>(pics.size());


        for (String pic : picNameList) { //리스트에 있는거 하나하나 전수조사해서 for 문 돌림
            log.info("pic 사진 :{}", pic);
        }
        /*
        pics.size()리턴메소드 쓰면 내부처리가 달라진다.
        안쪽애는 배열로 처리가 됨
        공란으로 해 놓으면 사진배열을 일일이 처리를 해서 일량이 많아짐
        몇개정도인지 기입하면 일처리가 잘 됨
         */
        for (MultipartFile pic : pics) {
            //각 파일의 랜텀파일명 만들기
            String savedPicName = myFileUtils.makeRandomFileName(pic); //각 사진의 랜덤파일명 받아서 savedFileName 에 복사
            picNameList.add(savedPicName); // 파일 이름 리스트에 하나하나 추가함
            String filePath = String.format("%s/%s", middlePath, savedPicName); // 주소/사진이름
            try {
                myFileUtils.transferTo(pic, filePath);// 예외발생시 빨간줄. 걍 트라이캐치
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //반복문이 끝나고 인서트
        //마이바티스에 궈리문에서 데이터를 찍을 용도로 만들었다

        FeedPicDto dto = new FeedPicDto();
        dto.setFeedId(feedId); // 위에서 인서트 하면서 얻은 피드아이디
        dto.setPics(picNameList); // 거기에 있는 사진들 가져오기
        int resultPics = feedPicsMapper.insFeedPics(dto); // 사진들도 따로 저장

        /* FeedPostRes res = new FeedPostRes();
         res.setFeedId(feedId);
         res.setPics(picNameList);
         */

        //프라이빗한 객체를
        return FeedPostRes.builder() // postRes 에는 피드아이디하고 사진이 들어있다
                .feedId(feedId)
                .pics(picNameList)
                .build();
    }

    public List<FeedGetRes> getFeedList(FeedGetReq p) {
        List<FeedGetRes> list = feedMapper.selFeedList(p);
        for (FeedGetRes item : list) {
            // 피드당 사진 리스트
            item.setPics(feedPicsMapper.selFeedPics(item.getFeedId()));

            //피드당 댓글 리스트 (필요한정보. 그 코멘트 페이지, 그리고 어떤 피드인지)
            // 피드당 댓글 4개
            //
            FeedCommentGetReq commentGetReq = new FeedCommentGetReq(item.getFeedId(),0,3);
            List<FeedCommentDto> commentList = feedCommentMapper.selFeedCommentList(commentGetReq);

            // feedCommentMapper 의 쿼리문에 commentGetReq 객체값 입력해서 보냈더니
            // 리스트 보내줌 첫번째 페이지 0,4(최대값) 세팅
            //FeedCommentDto 의 정보가 담긴 리스트를 보내준다
            // 두개 이상일 경우는 리스트가 된다.

            FeedCommentGetRes commentGetRes = new FeedCommentGetRes();
            commentGetRes.setCommentList(commentList);
            commentGetRes.setMoreComment(commentList.size() == commentGetReq.getSize());
            // ??? commentGetRes.setMoreComment(commentList.size() == commentGetReq.getSize());
            // 댓글이 4개가 되면 거기부터 '더 보시겠습니까?' 의 문구란이 뜸

            // 왜 4냐면 FeedCommentGetReq 클래스에
            // this.size = (size == null ? Constants.getDefault_page_size():size)+1; 이렇게 설정이 됨

            if (commentGetRes.isMoreComment()){ //댓글이 더 있을시
                commentList.remove(commentList.size()-1); // 4개일때 하나를 빼는 것
            } // 누르묜 "더보기"라고 쓰인 문구가 사라지고 그 인덱스에 있던 원래댓글부터 지정한 사이즈(21)->20(댓글)+1(isMore)다 뜸
            item.setComment(commentGetRes);
        }

        return list;

    }


}
