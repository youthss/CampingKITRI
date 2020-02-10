
DROP TABLE IF EXISTS riw RESTRICT;
DROP TABLE IF EXISTS bookmark RESTRICT;
DROP TABLE IF EXISTS rev RESTRICT;

-- 리뷰
CREATE TABLE riw (
  riw_id    INTEGER  NOT NULL, -- 리뷰번호
  usr_id    INTEGER  NOT NULL, -- 회원번호
  riw_conts TEXT     NOT NULL, -- 리뷰내용
  grd       VARCHAR(10) NOT NULL, -- 평점
  cdt       DATETIME NOT NULL DEFAULT current_timestamp(), -- 작성일
  riw_reply TEXT     NULL,     -- 답글
  reply_dt  DATE     NULL,     -- 답변일
  rms_id    INTEGER  NULL      -- 숙소번호
);

-- 리뷰
ALTER TABLE riw
  ADD CONSTRAINT PK_riw -- 리뷰 기본키
    PRIMARY KEY (
      riw_id -- 리뷰번호
    );

ALTER TABLE riw
  MODIFY COLUMN riw_id INTEGER NOT NULL AUTO_INCREMENT;


-- 리뷰
ALTER TABLE riw
  ADD CONSTRAINT FK_usr_TO_riw -- 회원 -> 리뷰
    FOREIGN KEY (
      usr_id -- 회원번호
    )
    REFERENCES usr ( -- 회원
      usr_id -- 회원번호
    );

-- 리뷰
ALTER TABLE riw
  ADD CONSTRAINT FK_rms_TO_riw -- 숙소 -> 리뷰
    FOREIGN KEY (
      rms_id -- 숙소번호
    )
    REFERENCES rms ( -- 숙소
      rms_id -- 숙소번호
    );

    
-- 즐겨찾기
CREATE TABLE bookmark (
  usr_id INTEGER  NOT NULL, -- 회원번호
  rms_id INTEGER  NOT NULL, -- 숙소번호
  memo   TEXT     NULL,     -- 찜메모
  cdt    DATETIME NOT NULL DEFAULT current_timestamp() -- 작성일
);

-- 즐겨찾기
ALTER TABLE bookmark
  ADD CONSTRAINT PK_bookmark -- 즐겨찾기 기본키
    PRIMARY KEY (
      usr_id, -- 회원번호
      rms_id  -- 숙소번호
    );

-- 즐겨찾기
ALTER TABLE bookmark
  ADD CONSTRAINT FK_usr_TO_bookmark -- 회원 -> 즐겨찾기
    FOREIGN KEY (
      usr_id -- 회원번호
    )
    REFERENCES usr ( -- 회원
      usr_id -- 회원번호
    );

-- 즐겨찾기
ALTER TABLE bookmark
  ADD CONSTRAINT FK_rms_TO_bookmark -- 숙소 -> 즐겨찾기
    FOREIGN KEY (
      rms_id -- 숙소번호
    )
    REFERENCES rms ( -- 숙소
      rms_id -- 숙소번호
    );
    
-- 예약
CREATE TABLE rev (
  rev_id    INTEGER  NOT NULL, -- 예약번호
  usr_id    INTEGER  NOT NULL, -- 회원번호
  stus_id   INTEGER  NOT NULL, -- 이용 상태 번호
  rms_id    INTEGER  NOT NULL, -- 숙소번호
  cck_in    DATE     NOT NULL, -- 체크인
  cck_out   DATE     NOT NULL, -- 체크아웃
  stby_stus CHAR(2)  NOT NULL, -- 승인/거절/대기 상태
  rev_persn INTEGER  NOT NULL, -- 예약인원
  rev_cdt   DATETIME NOT NULL DEFAULT current_timestamp(), -- 예약생성일
  rev_char  INTEGER  NULL,     -- 가격
  rev_updt  INTEGER  NULL,     -- 변경여부
  rev_reas  TEXT     NULL      -- 변경사유
);

-- 예약
ALTER TABLE rev
  ADD CONSTRAINT PK_rev -- 예약 기본키
    PRIMARY KEY (
      rev_id -- 예약번호
    );

ALTER TABLE rev
  MODIFY COLUMN rev_id INTEGER NOT NULL AUTO_INCREMENT;

-- 예약
ALTER TABLE rev
  ADD CONSTRAINT FK_usr_TO_rev -- 회원 -> 예약
    FOREIGN KEY (
      usr_id -- 회원번호
    )
    REFERENCES usr ( -- 회원
      usr_id -- 회원번호
    );

-- 예약
ALTER TABLE rev
  ADD CONSTRAINT FK_stus_TO_rev -- 이용상태 -> 예약
    FOREIGN KEY (
      stus_id -- 이용 상태 번호
    )
    REFERENCES stus ( -- 이용상태
      stus_id -- 이용 상태 번호
    );

-- 예약
ALTER TABLE rev
  ADD CONSTRAINT FK_rms_TO_rev -- 숙소 -> 예약
    FOREIGN KEY (
      rms_id -- 숙소번호
    )
    REFERENCES rms ( -- 숙소
      rms_id -- 숙소번호
    );