DROP TABLE IF EXISTS rms RESTRICT; 
DROP TABLE IF EXISTS stus RESTRICT;
DROP TABLE IF EXISTS board RESTRICT;
DROP TABLE IF EXISTS rm_photo RESTRICT;
DROP TABLE IF EXISTS rev RESTRICT;
DROP TABLE IF EXISTS riw RESTRICT;
DROP TABLE IF EXISTS usr RESTRICT;
DROP TABLE IF EXISTS auth RESTRICT;
DROP TABLE IF EXISTS rms_amn RESTRICT;
DROP TABLE IF EXISTS host RESTRICT;
DROP TABLE IF EXISTS bookmark RESTRICT;
DROP TABLE IF EXISTS qna RESTRICT;
DROP TABLE IF EXISTS hst_qna RESTRICT;
DROP TABLE IF EXISTS amn RESTRICT;
DROP TABLE IF EXISTS faq RESTRICT;
DROP TABLE IF EXISTS qna_cate RESTRICT;
DROP TABLE IF EXISTS safety RESTRICT;
DROP TABLE IF EXISTS rms_safety RESTRICT;
DROP TABLE IF EXISTS blike RESTRICT;
DROP TABLE IF EXISTS photoboard RESTRICT;

-- 숙소
CREATE TABLE rms (
  rms_id    INTEGER      NOT NULL, -- 숙소번호
  usr_id    INTEGER      NOT NULL, -- 호스트번호
  area      VARCHAR(255) NOT NULL, -- 지역명
  type      VARCHAR(100) NOT NULL, -- 숙소유형
  bed       INTEGER      NOT NULL, -- 침대갯수
  bath      INTEGER      NOT NULL, -- 욕실갯수
  cont      TEXT         NOT NULL, -- 숙소설명
  dets      TEXT         NULL,     -- 숙소세부정보
  reva      TEXT         NULL,     -- 예약가능여부
  come      TEXT         NULL,     -- 숙소가위치한지역
  traf      TEXT         NULL,     -- 교통편
  rm_name   VARCHAR(100) NOT NULL, -- 숙소명
  rm_chge   INTEGER      NOT NULL, -- 숙소가격
  max_ple   INTEGER      NOT NULL, -- 최대 수용 인원
  post_code VARCHAR(5)   NOT NULL, -- 우편번호
  addr      VARCHAR(255) NOT NULL, -- 기본주소
  dtil_addr VARCHAR(255) NOT NULL, -- 상세주소
  lati      VARCHAR(50)  NOT NULL, -- 위도
  longi     VARCHAR(50)  NOT NULL, -- 경도
  cdt       DATETIME     NOT NULL DEFAULT current_timestamp(), -- 등록일
  grd       VARCHAR(5)   NULL,     -- 평균평점
  thum      VARCHAR(255) NULL,     -- 섬네일
  activation  INTEGER    NULL     DEFAULT 0, -- 승인/거절
  reject_memo TEXT       NULL      -- 거절메세지
);

-- 숙소
ALTER TABLE rms
  ADD CONSTRAINT PK_rms -- 숙소 기본키
    PRIMARY KEY (
      rms_id -- 숙소번호
    );

ALTER TABLE rms
  MODIFY COLUMN rms_id INTEGER NOT NULL AUTO_INCREMENT;

-- 회원
CREATE TABLE usr (
  usr_id    INTEGER      NOT NULL, -- 회원번호
  email     VARCHAR(30)  NOT NULL, -- 이메일
  pwd       VARCHAR(255) NOT NULL, -- 비밀번호
  auth_id   INTEGER      NOT NULL, -- 권한번호
  name      VARCHAR(10)  NOT NULL, -- 이름
  cdt       DATETIME     NOT NULL DEFAULT current_timestamp(), -- 가입일
  sns_no    INTEGER      NOT NULL, -- 소셜 로그인
  tel       VARCHAR(15)  NULL,     -- 전화번호
  usr_photo VARCHAR(255) NULL      -- 프로필사진
);

-- 회원
ALTER TABLE usr
  ADD CONSTRAINT PK_usr -- 회원 기본키
    PRIMARY KEY (
      usr_id -- 회원번호
    );

ALTER TABLE usr
  MODIFY COLUMN usr_id INTEGER NOT NULL AUTO_INCREMENT;
  
 ALTER TABLE usr ADD UNIQUE KEY (email);

-- 블로그
CREATE TABLE board (
  board_id   INTEGER      NOT NULL, -- 게시글번호
  usr_id     INTEGER      NOT NULL, -- 회원번호
  rms_id     INTEGER      NOT NULL, -- 숙소번호
  main_photo VARCHAR(255) NOT NULL, -- 섬네일
  title      VARCHAR(50)  NOT NULL, -- 게시글제목
  conts      MEDIUMTEXT   NOT NULL, -- 게시글내용
  cdt        DATETIME     NOT NULL DEFAULT current_timestamp() -- 작성일
);

-- 블로그
ALTER TABLE board
  ADD CONSTRAINT PK_board -- 블로그 기본키
    PRIMARY KEY (
      board_id -- 게시글번호
    );

ALTER TABLE board
  MODIFY COLUMN board_id INTEGER NOT NULL AUTO_INCREMENT;

-- 예약
CREATE TABLE rev (
  rev_id    INTEGER  NOT NULL, -- 예약번호
  usr_id    INTEGER  NOT NULL, -- 회원번호
  stus_id   INTEGER  NOT NULL, -- 이용 상태 번호
  rms_id    INTEGER  NOT NULL, -- 숙소번호
  cck_in    DATE     NOT NULL, -- 체크인
  cck_out   DATE     NOT NULL, -- 체크아웃
  rev_stus  CHAR(3)  NULL,     -- 예약대기상태
  stby_stus CHAR(2)  NOT NULL, -- 승인/거절/대기 상태
  rev_persn INTEGER  NOT NULL, -- 예약인원
  rev_cdt   DATETIME NOT NULL DEFAULT current_timestamp() -- 예약생성일
);

-- 예약
ALTER TABLE rev
  ADD CONSTRAINT PK_rev -- 예약 기본키
    PRIMARY KEY (
      rev_id -- 예약번호
    );

ALTER TABLE rev
  MODIFY COLUMN rev_id INTEGER NOT NULL AUTO_INCREMENT;

-- 문의사항
CREATE TABLE qna (
  qna_id      INTEGER      NOT NULL, -- 문의사항번호
  usr_id      INTEGER      NOT NULL, -- 회원번호
  qna_cate_id INTEGER      NOT NULL, -- 문의구분번호
  parent      INTEGER      NOT NULL, -- 부모게시물번호
  ordr        INTEGER      NOT NULL, -- 순서
  step        INTEGER      NOT NULL, -- 단계
  title       VARCHAR(50)  NOT NULL, -- 문의제목
  content     MEDIUMTEXT   NOT NULL, -- 문의내용
  qna_pwd     VARCHAR(255) NULL,     -- 문의글비밀번호
  cdt         DATETIME     NOT NULL DEFAULT current_timestamp(), -- 작성일
  vw_cnt      INTEGER      NOT NULL DEFAULT 0 -- 조회수
);

-- 문의사항
ALTER TABLE qna
  ADD CONSTRAINT PK_qna -- 문의사항 기본키
    PRIMARY KEY (
      qna_id -- 문의사항번호
    );

ALTER TABLE qna
  MODIFY COLUMN qna_id INTEGER NOT NULL AUTO_INCREMENT;

-- 즐겨찾기
CREATE TABLE bookmark (
  usr_id INTEGER NOT NULL, -- 회원번호
  rms_id INTEGER NOT NULL, -- 숙소번호
  memo   TEXT    NULL      -- 찜메모
);

-- 즐겨찾기
ALTER TABLE bookmark
  ADD CONSTRAINT PK_bookmark -- 즐겨찾기 기본키
    PRIMARY KEY (
      usr_id, -- 회원번호
      rms_id  -- 숙소번호
    );

-- 리뷰
CREATE TABLE riw (
  riw_id    INTEGER  NOT NULL, -- 리뷰번호
  usr_id    INTEGER  NOT NULL, -- 회원번호
  riw_conts TEXT     NOT NULL, -- 리뷰내용
  grd       INT      NOT NULL, -- 평점
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

-- FAQ
CREATE TABLE faq (
  faq_id INTEGER NOT NULL, -- QnA번호
  faq    TEXT    NOT NULL, -- 자주묻는질문
  faq_an TEXT    NOT NULL  -- 자주묻는질문 답변
);

-- FAQ
ALTER TABLE faq
  ADD CONSTRAINT PK_faq -- FAQ 기본키
    PRIMARY KEY (
      faq_id -- QnA번호
    );

ALTER TABLE faq
  MODIFY COLUMN faq_id INTEGER NOT NULL AUTO_INCREMENT;

-- 숙소문의-회원
CREATE TABLE hst_qna (
  hst_qna_id INTEGER  NOT NULL, -- 숙소문의번호-회원
  usr_id     INTEGER  NOT NULL, -- 회원번호
  rms_id     INTEGER  NOT NULL, -- 숙소번호
  conts      TEXT     NOT NULL, -- 내용
  cdt        DATETIME NOT NULL DEFAULT current_timestamp() -- 작성일
);

-- 숙소문의-회원
ALTER TABLE hst_qna
  ADD CONSTRAINT PK_hst_qna -- 숙소문의-회원 기본키
    PRIMARY KEY (
      hst_qna_id -- 숙소문의번호-회원
    );

ALTER TABLE hst_qna
  MODIFY COLUMN hst_qna_id INTEGER NOT NULL AUTO_INCREMENT;

-- 권한
CREATE TABLE auth (
  auth_id INTEGER    NOT NULL, -- 권한번호
  auth    VARCHAR(5) NOT NULL  -- 권한
);

-- 권한
ALTER TABLE auth
  ADD CONSTRAINT PK_auth -- 권한 기본키
    PRIMARY KEY (
      auth_id -- 권한번호
    );

ALTER TABLE auth
  MODIFY COLUMN auth_id INTEGER NOT NULL AUTO_INCREMENT;

-- 호스트
CREATE TABLE host (
  usr_id  INTEGER     NOT NULL, -- 호스트번호
  bank    VARCHAR(10) NOT NULL, -- 은행명
  bank_id VARCHAR(30) NOT NULL  -- 계좌번호
);

-- 호스트
ALTER TABLE host
  ADD CONSTRAINT PK_host -- 호스트 기본키
    PRIMARY KEY (
      usr_id -- 호스트번호
    );

ALTER TABLE host
  MODIFY COLUMN usr_id INTEGER NOT NULL AUTO_INCREMENT;

-- 편의시설
CREATE TABLE amn (
  amn_id INTEGER      NOT NULL, -- 편의 시설번호
  amn    VARCHAR(100) NOT NULL  -- 옵션 이름
);

-- 편의시설
ALTER TABLE amn
  ADD CONSTRAINT PK_amn -- 편의시설 기본키
    PRIMARY KEY (
      amn_id -- 편의 시설번호
    );

ALTER TABLE amn
  MODIFY COLUMN amn_id INTEGER NOT NULL AUTO_INCREMENT;

-- 이용상태
CREATE TABLE stus (
  stus_id INTEGER  NOT NULL, -- 이용 상태 번호
  stus    CHAR(10) NOT NULL  -- 이용 상태
);

-- 이용상태
ALTER TABLE stus
  ADD CONSTRAINT PK_stus -- 이용상태 기본키
    PRIMARY KEY (
      stus_id -- 이용 상태 번호
    );

ALTER TABLE stus
  MODIFY COLUMN stus_id INTEGER NOT NULL AUTO_INCREMENT;

-- 숙소편의시설
CREATE TABLE rms_amn (
  amn_id INTEGER NOT NULL, -- 편의 시설번호
  rms_id INTEGER NOT NULL  -- 숙소번호
);

-- 숙소편의시설
ALTER TABLE rms_amn
  ADD CONSTRAINT PK_rms_amn -- 숙소편의시설 기본키
    PRIMARY KEY (
      amn_id, -- 편의 시설번호
      rms_id  -- 숙소번호
    );

-- 숙소사진
CREATE TABLE rm_photo (
  r_photo_id INTEGER      NOT NULL, -- 숙소사진번호
  rms_id     INTEGER      NOT NULL, -- 숙소번호
  rm_photo   VARCHAR(255) NOT NULL  -- 사진
);

-- 숙소사진
ALTER TABLE rm_photo
  ADD CONSTRAINT PK_rm_photo -- 숙소사진 기본키
    PRIMARY KEY (
      r_photo_id -- 숙소사진번호
    );

ALTER TABLE rm_photo
  MODIFY COLUMN r_photo_id INTEGER NOT NULL AUTO_INCREMENT;

-- 문의구분
CREATE TABLE qna_cate (
  qna_cate_id INTEGER     NOT NULL, -- 문의구분번호
  cate        VARCHAR(20) NOT NULL  -- 문의구분
);

-- 문의구분
ALTER TABLE qna_cate
  ADD CONSTRAINT PK_qna_cate -- 문의구분 기본키
    PRIMARY KEY (
      qna_cate_id -- 문의구분번호
    );

-- 안전시설
CREATE TABLE safety (
  safety_id INTEGER      NOT NULL, -- 안전시설번호
  safety    VARCHAR(255) NOT NULL  -- 안전시설
);

-- 안전시설
ALTER TABLE safety
  ADD CONSTRAINT PK_safety -- 안전시설 기본키
    PRIMARY KEY (
      safety_id -- 안전시설번호
    );

ALTER TABLE safety
  MODIFY COLUMN safety_id INTEGER NOT NULL AUTO_INCREMENT;

-- 숙소안전시설
CREATE TABLE rms_safety (
  safety_id INTEGER NOT NULL, -- 안전시설번호
  rms_id    INTEGER NOT NULL  -- 숙소번호
);

-- 숙소안전시설
ALTER TABLE rms_safety
  ADD CONSTRAINT PK_rms_safety -- 숙소안전시설 기본키
    PRIMARY KEY (
      safety_id, -- 안전시설번호
      rms_id     -- 숙소번호
    );

-- 블로그 좋아요
CREATE TABLE blike (
  blike_no    INTEGER NOT NULL, -- 좋아요번호
  blike_check INTEGER NOT NULL DEFAULT 0, -- 좋아요체크
  board_id    INTEGER NOT NULL, -- 게시글번호
  usr_id      INTEGER NOT NULL  -- 회원번호
);

-- 블로그 좋아요
ALTER TABLE blike
  ADD CONSTRAINT PK_blike -- 블로그 좋아요 기본키
    PRIMARY KEY (
      blike_no -- 좋아요번호
    );

ALTER TABLE blike
  MODIFY COLUMN blike_no INTEGER NOT NULL AUTO_INCREMENT;

-- 블로그사진
CREATE TABLE photoboard (
  pboard_id INTEGER      NOT NULL, -- 블로그사진번호
  board_id  INTEGER      NOT NULL, -- 게시글번호
  photo     VARCHAR(255) NOT NULL  -- 사진
);

-- 블로그사진
ALTER TABLE photoboard
  ADD CONSTRAINT PK_photoboard -- 블로그사진 기본키
    PRIMARY KEY (
      pboard_id -- 블로그사진번호
    );

ALTER TABLE photoboard
  MODIFY COLUMN pboard_id INTEGER NOT NULL AUTO_INCREMENT;

-- 숙소
ALTER TABLE rms
  ADD CONSTRAINT FK_host_TO_rms -- 호스트 -> 숙소
    FOREIGN KEY (
      usr_id -- 호스트번호
    )
    REFERENCES host ( -- 호스트
      usr_id -- 호스트번호
    );

-- 회원
ALTER TABLE usr
  ADD CONSTRAINT FK_auth_TO_usr -- 권한 -> 회원
    FOREIGN KEY (
      auth_id -- 권한번호
    )
    REFERENCES auth ( -- 권한
      auth_id -- 권한번호
    );

-- 블로그
ALTER TABLE board
  ADD CONSTRAINT FK_usr_TO_board -- 회원 -> 블로그
    FOREIGN KEY (
      usr_id -- 회원번호
    )
    REFERENCES usr ( -- 회원
      usr_id -- 회원번호
    );

-- 블로그
ALTER TABLE board
  ADD CONSTRAINT FK_rms_TO_board -- 숙소 -> 블로그
    FOREIGN KEY (
      rms_id -- 숙소번호
    )
    REFERENCES rms ( -- 숙소
      rms_id -- 숙소번호
    );

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

-- 문의사항
ALTER TABLE qna
  ADD CONSTRAINT FK_usr_TO_qna -- 회원 -> 문의사항
    FOREIGN KEY (
      usr_id -- 회원번호
    )
    REFERENCES usr ( -- 회원
      usr_id -- 회원번호
    );

-- 문의사항
ALTER TABLE qna
  ADD CONSTRAINT FK_qna_cate_TO_qna -- 문의구분 -> 문의사항
    FOREIGN KEY (
      qna_cate_id -- 문의구분번호
    )
    REFERENCES qna_cate ( -- 문의구분
      qna_cate_id -- 문의구분번호
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

-- 숙소문의-회원
ALTER TABLE hst_qna
  ADD CONSTRAINT FK_usr_TO_hst_qna -- 회원 -> 숙소문의-회원
    FOREIGN KEY (
      usr_id -- 회원번호
    )
    REFERENCES usr ( -- 회원
      usr_id -- 회원번호
    );

-- 숙소문의-회원
ALTER TABLE hst_qna
  ADD CONSTRAINT FK_rms_TO_hst_qna -- 숙소 -> 숙소문의-회원
    FOREIGN KEY (
      rms_id -- 숙소번호
    )
    REFERENCES rms ( -- 숙소
      rms_id -- 숙소번호
    );

-- 호스트
ALTER TABLE host
  ADD CONSTRAINT FK_usr_TO_host -- 회원 -> 호스트
    FOREIGN KEY (
      usr_id -- 호스트번호
    )
    REFERENCES usr ( -- 회원
      usr_id -- 회원번호
    );

-- 숙소편의시설
ALTER TABLE rms_amn
  ADD CONSTRAINT FK_amn_TO_rms_amn -- 편의시설 -> 숙소편의시설
    FOREIGN KEY (
      amn_id -- 편의 시설번호
    )
    REFERENCES amn ( -- 편의시설
      amn_id -- 편의 시설번호
    );

-- 숙소편의시설
ALTER TABLE rms_amn
  ADD CONSTRAINT FK_rms_TO_rms_amn -- 숙소 -> 숙소편의시설
    FOREIGN KEY (
      rms_id -- 숙소번호
    )
    REFERENCES rms ( -- 숙소
      rms_id -- 숙소번호
    );

-- 숙소사진
ALTER TABLE rm_photo
  ADD CONSTRAINT FK_rms_TO_rm_photo -- 숙소 -> 숙소사진
    FOREIGN KEY (
      rms_id -- 숙소번호
    )
    REFERENCES rms ( -- 숙소
      rms_id -- 숙소번호
    );

-- 숙소안전시설
ALTER TABLE rms_safety
  ADD CONSTRAINT FK_safety_TO_rms_safety -- 안전시설 -> 숙소안전시설
    FOREIGN KEY (
      safety_id -- 안전시설번호
    )
    REFERENCES safety ( -- 안전시설
      safety_id -- 안전시설번호
    );

-- 숙소안전시설
ALTER TABLE rms_safety
  ADD CONSTRAINT FK_rms_TO_rms_safety -- 숙소 -> 숙소안전시설
    FOREIGN KEY (
      rms_id -- 숙소번호
    )
    REFERENCES rms ( -- 숙소
      rms_id -- 숙소번호
    );

-- 블로그 좋아요
ALTER TABLE blike
  ADD CONSTRAINT FK_board_TO_blike -- 블로그 -> 블로그 좋아요
    FOREIGN KEY (
      board_id -- 게시글번호
    )
    REFERENCES board ( -- 블로그
      board_id -- 게시글번호
    );

-- 블로그 좋아요
ALTER TABLE blike
  ADD CONSTRAINT FK_usr_TO_blike -- 회원 -> 블로그 좋아요
    FOREIGN KEY (
      usr_id -- 회원번호
    )
    REFERENCES usr ( -- 회원
      usr_id -- 회원번호
    );

-- 블로그사진
ALTER TABLE photoboard
  ADD CONSTRAINT FK_board_TO_photoboard -- 블로그 -> 블로그사진
    FOREIGN KEY (
      board_id -- 게시글번호
    )
    REFERENCES board ( -- 블로그
      board_id -- 게시글번호
    );