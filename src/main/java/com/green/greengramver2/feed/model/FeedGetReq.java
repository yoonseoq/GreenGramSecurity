package com.green.greengramver2.feed.model;

import com.green.greengramver2.common.model.Paging;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.BindParam;

import java.beans.ConstructorProperties;

@Getter
@Slf4j
@ToString(callSuper = true)
// toString 이 친구의역할은 FeedGetReq 여기에 오버라이딩 하게 해준다 부모가 가지고 있는 toString 을 쓰게 해줌
public class FeedGetReq extends Paging {
    @Schema(title = "로그인유저 PK",name = "signed_user_id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private long signedUserId;
    //로그인한 사용자 pk를 가져온다. 본인이 좋아요 눌렀는지 안눌렀는지 피드를 구분하기 위해서
    //퀴리스트링: url 에다 바디의값을 넣어주는거, get 방식과 delete 방식때 사용,
    // post,put 을 쓸때는 body

   // @ConstructorProperties({"page","size","signed_user_id"})
    public FeedGetReq(Integer page, Integer size,@BindParam("signed_user_id") long signedUserId) {
        //@BindParam 의 역할은 req 와 res 의 멤버필드를 매칭시켜주는것
        // 스웨거에서도 이 키값을
        super(page,size);// 부모에 있는 생성자 호출 완료
        this.signedUserId = signedUserId;
        log.info("page: {}, size: {}, signedUserId: {}", page, size, signedUserId);
    }
}
