CREATE TABLE `user` (
                        `user_id`	BIGINT	NOT NULL,
                        `uid`	VARCHAR(30)	NOT NULL,
                        `upw`	VARCHAR(100)	NOT NULL,
                        `nick_name`	VARCHAR(30)	NULL,
                        `pic`	VARCHAR(50)	NULL	COMMENT '프로필 사진',
                        `created_at`	DATETIME	NOT NULL	DEFAULT CURRENT_TIMESTAMP(),
                        `updated_at`	DATETIME	NULL
);

CREATE TABLE `feed_pic` (
                            `pic`	bigint	NOT NULL,
                            `feed_id`	bigint	NOT NULL,
                            `created_at`	datetime	NOT NULL	DEFAULT current_timestamp()
);

CREATE TABLE `feed_comment` (
                                `feed_comment_id`	bigint	NOT NULL,
                                `feed_id`	bigint	NOT NULL,
                                `user_id`	BIGINT	NOT NULL,
                                `comment`	varchar(150)	NOT NULL,
                                `created_at`	datetime	NOT NULL	DEFAULT current_timestamp(),
                                `updated_at`	datetime	NULL
);

CREATE TABLE `user_follow` (
                               `from_user_id`	BIGINT	NOT NULL,
                               `user_id2`	BIGINT	NOT NULL,
                               `created_at`	datetime	NOT NULL	DEFAULT current_timestamp()
);

CREATE TABLE `feed_like` (
                             `user_id`	BIGINT	NOT NULL,
                             `feed_id`	bigint	NOT NULL
);

CREATE TABLE `feed` (
                        `feed_id`	bigint	NOT NULL,
                        `user_id`	BIGINT	NOT NULL,
                        `contents`	varchar(1000)	NULL,
                        `location`	varchar(30)	NULL,
                        `created_at`	datetime	NOT NULL	DEFAULT current_timestamp(),
                        `updated_at`	datetime	NULL
);

ALTER TABLE `user` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
                                                         `user_id`
    );

ALTER TABLE `feed_pic` ADD CONSTRAINT `PK_FEED_PIC` PRIMARY KEY (
                                                                 `pic`,
                                                                 `feed_id`
    );

ALTER TABLE `feed_comment` ADD CONSTRAINT `PK_FEED_COMMENT` PRIMARY KEY (
                                                                         `feed_comment_id`
    );

ALTER TABLE `user_follow` ADD CONSTRAINT `PK_USER_FOLLOW` PRIMARY KEY (
                                                                       `from_user_id`
    );

ALTER TABLE `feed_like` ADD CONSTRAINT `PK_FEED_LIKE` PRIMARY KEY (
                                                                   `user_id`,
                                                                   `feed_id`
    );

ALTER TABLE `feed` ADD CONSTRAINT `PK_FEED` PRIMARY KEY (
                                                         `feed_id`
    );

ALTER TABLE `feed_pic` ADD CONSTRAINT `FK_feed_TO_feed_pic_1` FOREIGN KEY (
                                                                           `feed_id`
    )
    REFERENCES `feed` (
                       `feed_id`
        );

ALTER TABLE `user_follow` ADD CONSTRAINT `FK_user_TO_user_follow_1` FOREIGN KEY (
                                                                                 `from_user_id`
    )
    REFERENCES `user` (
                       `user_id`
        );

ALTER TABLE `feed_like` ADD CONSTRAINT `FK_user_TO_feed_like_1` FOREIGN KEY (
                                                                             `user_id`
    )
    REFERENCES `user` (
                       `user_id`
        );

ALTER TABLE `feed_like` ADD CONSTRAINT `FK_feed_TO_feed_like_1` FOREIGN KEY (
                                                                             `feed_id`
    )
    REFERENCES `feed` (
                       `feed_id`
        );
