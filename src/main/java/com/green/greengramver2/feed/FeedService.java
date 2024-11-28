package com.green.greengramver2.feed;

import com.green.greengramver2.common.MyFileUtils;
import com.green.greengramver2.feed.model.FeedPostReq;
import com.green.greengramver2.feed.model.FeedPostRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class FeedService {
    private final  FeedMapper mapper;
    private final  FeedPicsMapper picsMapper;
    private final MyFileUtils myFileUtils;

    public FeedPostRes postFeed(List<MultipartFile> pics, FeedPostReq p){
        int result = mapper.insFeed(p);

        //--------------- 파일등록
        long feedId = p.getFeedId();

        //--------------- 저장폴더 만들기
        String middlePath = String.format("feed/%d",feedId);
        myFileUtils.makeFolders(middlePath);

        for (MultipartFile pic : pics) {
            //각 파일의 랜텀파일명 만들기
            String savedPicName = myFileUtils.makeRandomFileName(pic);
            String filePath = String.format("feed/%s/%s", middlePath, savedPicName);
            try {
                myFileUtils.transferTo(pic,filePath);// 예외발생시 빨간줄. 걍 트라이캐치
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
