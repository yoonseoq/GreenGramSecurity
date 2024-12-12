package com.green.greengramver3.feed;

import com.green.greengramver3.common.MyFileUtils;
import com.green.greengramver3.feed.comment.FeedCommentMapper;
import com.green.greengramver3.feed.comment.model.FeedCommentDto;
import com.green.greengramver3.feed.comment.model.FeedCommentGetReq;
import com.green.greengramver3.feed.comment.model.FeedCommentGetRes;
import com.green.greengramver3.feed.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedService {
    private final FeedMapper feedMapper;
    private final FeedPicMapper feedPicMapper;
    private final FeedCommentMapper feedCommentMapper;
    private final MyFileUtils myFileUtils;

    @Transactional //여러개의 작업이 수행될때 데이터의 일관성을 보장하기 위해 사용됨
    public FeedPostRes postFeed(List<MultipartFile> pics, FeedPostReq p) {
        int result = feedMapper.insFeed(p); // 피드정보 가져옴

        long feedId = p.getFeedId(); // 피드아이디 가져와서 변수 만들기

        String middlePath = String.format("feed/%d/", feedId);
        myFileUtils.makeFolders(middlePath); // 해당파일 경로로 폴더만듦

        List<String> picNameList = new ArrayList<>(pics.size()); //사진개수 크기만큼 List 가 나온다
        for (MultipartFile pic : pics) {
            //pics 리스트에 있는 사진들 돌기
            String savedPicName = myFileUtils.makeRandomFileName(pic); //multipartFile type 으로

            /*
             public String makeRandomFileName(MultipartFile file) {
               return makeRandomFileName(file.getOriginalFilename());
            }
             public String makeRandomFileName(String originalFileName) {
                return makeRandomFileName() + getExt(originalFileName);
            }
              public String makeRandomFileName() {
                return UUID.randomUUID().toString();
            }
             */

            picNameList.add(savedPicName); // 리스트에 사진 이름 추가
            String filePath = String.format("%s/%s", middlePath, savedPicName);
            // 파일 경로 설정하기
            try {
                myFileUtils.transferTo(pic, filePath); // 해당사진을 지정한 폴더경로로 보내줌
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FeedPicDto feedPicDto = new FeedPicDto();
        /*
        public class FeedPicDto {
            private long feedId;
            private List<String> pics;
        }
         */
        feedPicDto.setFeedId(feedId); //피드 아이디 세팅
        feedPicDto.setPics(picNameList); //피드 사진 리스트 세팅
        int resultPics = feedPicMapper.insFeedPic(feedPicDto);

        return FeedPostRes.builder()
                .feedId(feedId)
                .pics(picNameList)
                .build();
    }

    public List<FeedGetRes> getFeedList(FeedGetReq p) {

        List<FeedGetRes> list = feedMapper.selFeedList(p);
        for (FeedGetRes item : list) {

            item.setPics(feedPicMapper.selFeedPicList(item.getFeedId())); // 피드당 사진 리스트

            // 피드당 댓글
            FeedCommentGetReq commentGetReq = new FeedCommentGetReq(item.getFeedId(), 0, 3);
            //피드아이디에서 특정범위만큼의 댓글 가져오기
            List<FeedCommentDto> commentList = feedCommentMapper.selFeedCommentList(commentGetReq);

            FeedCommentGetRes commentGetRes = new FeedCommentGetRes();
            commentGetRes.setCommentList(commentList);
            commentGetRes.setMoreComment(commentList.size() == commentGetReq.getSize());

            if (commentGetRes.isMoreComment()) {
                commentList.remove(commentList.size() - 1);
            }
            item.setComment(commentGetRes);
        }
        return list;

    }

    public List<FeedGetRes> getFeedList3(FeedGetReq p) {
        //피드 리스트
        List<FeedGetRes> list = feedMapper.selFeedList(p);

        //feed_id를 골라내야 한다.

        List<Long> feedIds4 = list.stream().map(FeedGetRes::getFeedId).collect(Collectors.toList());
        List<Long> feedIds5 = list.stream().map(item -> ((FeedGetRes) item).getFeedId()).toList();
        List<Long> feedIds6 = list.stream().map(item -> {
            return ((FeedGetRes) item).getFeedId();
        }).toList();
        // 대충 이런것들도 있다

        List<Long> feedIds = new ArrayList<>(list.size());
        for (FeedGetRes item : list) {
            feedIds.add(item.getFeedId());
        }
        log.info("feedIds: {}", feedIds);

        List<FeedPicSel> feedPicList = feedPicMapper.selPicListByFeedIds(feedIds);
        // 위에 모아놓은 피드 아이디들로 피드 사진들 불러옴
        log.info("feedPicList: {}", feedPicList);

        Map<Long, List<String>> picHashMap = new HashMap<>();
        //hashMap은 뭐하는 놈이지?
        for (FeedPicSel item : feedPicList) {
            long feedId = item.getFeedId();
            if (!picHashMap.containsKey(feedId)) {
                picHashMap.put(feedId, new ArrayList<>());
            }
            List<String> pics = picHashMap.get(feedId);
            pics.add(item.getPic());
        }

        List<FeedCommentDto> feedCommentList = feedCommentMapper.selCommentListByFeedId(feedIds);
        log.info("feedCommentList: {}", feedCommentList);
        Map<Long, FeedCommentGetRes> commentHashMap = new HashMap<>();
        for (FeedCommentDto item : feedCommentList) {
            long feedId = item.getFeedId();
            if (!commentHashMap.containsKey(feedId)) {
                FeedCommentGetRes commentGetRes = new FeedCommentGetRes();
                commentGetRes.setCommentList(new ArrayList<>(4)); // 처음댓글은 4개까지
                commentHashMap.put(feedId, commentGetRes);
            }
            FeedCommentGetRes commentGetRes = commentHashMap.get(feedId);
            commentGetRes.getCommentList().add(item);
        }

        for (FeedGetRes res : list) {
            res.setPics(picHashMap.get(res.getFeedId()));
            FeedCommentGetRes feedCommentGetRes = commentHashMap.get(res.getFeedId());

            if (feedCommentGetRes == null) {
                feedCommentGetRes = new FeedCommentGetRes();
                feedCommentGetRes.setCommentList(new ArrayList<>());
            } else if (feedCommentGetRes.getCommentList().size() == 4) {
                feedCommentGetRes.setMoreComment(true);
                feedCommentGetRes.getCommentList().remove(feedCommentGetRes.getCommentList().size() - 1);
            }
            res.setComment(feedCommentGetRes);
        }
        log.info("list: {}", list);
        return list;

    }
    @Transactional
    public int deleteFeed(FeedDeleteReq p) {
        //피드 댓 좋야요 사진 삭제
        int affectedRowsEtc = feedMapper.delFeedLikeAndFeedCommentAndFeedPic(p);
        log.info("deleteFeed > affectedRows: {}", affectedRowsEtc);

        int affectedRowsFeed = feedMapper.delFeed(p);
        log.info("deleteFeed > affectedRowsFeed: {}", affectedRowsFeed);

         String deletePath = String.format("%s/feed/%d",myFileUtils.getUploadPath(), p.getFeedId());
         myFileUtils.deleteFolder(deletePath, true);


         return 1;
    }
}

/*해쉬뱁은 키와 밸류 관리하느ㅏㄴ ㄴ놈임


 */



