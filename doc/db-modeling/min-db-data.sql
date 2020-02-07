-- riw
insert into riw(usr_id, riw_conts, grd) values(1,'안마의자있어서 너무 좋았어요!! 컴퓨터를 오래봐서 어깨랑 허리랑 너무 아팠는데 발리에 쉬면서 뭉친 피로 다 풀었어요!!!! 디럭스 객실에도 안마의자라니....너무나 감동 ㅜㅜㅜ ...', 4 );
insert into riw(usr_id, riw_conts, grd) values(2,'깔끔하고 너무 편했어요 방이 진짜 따듯했는데 땀날정도였어요ㅋㅋㅋ 창문 열면 괜찮음!', 2 );
insert into riw(usr_id, riw_conts, grd) values(3,'여자 직원 두분 너무 친절하셔서 들어갈때부터 기분서좋았습니다 저렴한가격에 퀄리티 좋은 객실 너무 좋았습니다 지방에서 출장왔는데 서울올때마다 이용하고싶네요', 1 );
insert into riw(usr_id, riw_conts, grd) values(4,'남치니랑 신천에서 자주 노는뎅 갈때마다 테레즈 갑니당~~ 우선 깨끗하구 사장님이 넘넘 친절하세요~과자두 맛나구용 갈때마다 편하게 쓰구가는듯~ 거울방 너무 좋아서 거울방만 찾아영....... 별로에요', 3 );
insert into riw(usr_id, riw_conts, grd) values(5,'후기들 다 알바고 그저 그러겠거니 생각했는데 가보니까 진짜 만족했어요. 숙박업체 이용하고 평점 남기는것도 잘안하는데 정말 만족해서 댓글 답니다 사장님도 친절하셨어요', 3 );
insert into riw(usr_id, riw_conts, grd) values(6,'공주공주풍 한 느낌도 괜찮구 깨끗하고 조용하고 좋아요! 대실시간 엄청길어서 가성비 개굿 프런트직원분도 친절하셔서 기분좋게 갑니당 뿅', 2 );
insert into riw(usr_id, riw_conts, grd) values(7,'알바생이 음식을 자주 리필해서 좋았어요', 4 );
insert into riw(usr_id, riw_conts, grd) values(8,'직원분들이 친절하셔서 불편함없이 잘 지냈습니다', 2 );
insert into riw(usr_id, riw_conts, grd) values(9,'항상 데미안만 이용하고 있어요', 5 );
insert into riw(usr_id, riw_conts, grd) values(10,'공주공주풍 한 느낌도 괜찮구 깨끗하고 조용하고 좋아요! 대실시간 엄청길어서 가성비 개굿 프런트직원분도 친절하셔서 기분좋게 갑니당 뿅', 2 );


-- 예약
insert into rev(usr_id, stus_id, rms_id, cck_in, cck_out, stby_stus, rev_persn, rev_char) 
values(1,3,11,'2019-05-08','2020-05-10', '승인', 2, 60000);
insert into rev(usr_id, stus_id, rms_id, cck_in, cck_out, stby_stus, rev_persn, rev_char) 
values(1,4,12,'2019-05-02','2020-05-10', '거절', 2, 50000);
insert into rev(usr_id, stus_id, rms_id, cck_in, cck_out, stby_stus, rev_persn, rev_char) 
values(1,3,13,'2019-06-08','2020-05-10', '승인', 2, 234000);
insert into rev(usr_id, stus_id, rms_id, cck_in, cck_out, stby_stus, rev_persn, rev_char) 
values(4,2,14,'2019-11-08','2020-05-10', '승인', 1, 55000);
insert into rev(usr_id, stus_id, rms_id, cck_in, cck_out, stby_stus, rev_persn, rev_char) 
values(5,4,15,'2019-12-28','2020-05-10','거절', 3, 53400);
insert into rev(usr_id, stus_id, rms_id, cck_in, cck_out, stby_stus, rev_persn, rev_char) 
values(1,4,11,'2019-01-08','2020-05-10', '승인', 5, 234000);
insert into rev(usr_id, stus_id, rms_id, cck_in, cck_out, stby_stus, rev_persn, rev_char) 
values(2,3,12,'2019-02-08','2020-05-10','승인', 1, 3453000);
insert into rev(usr_id, stus_id, rms_id, cck_in, cck_out, stby_stus, rev_persn, rev_char) 
values(3,4,13,'2019-04-08','2020-05-10','거절', 4, 5235000);
insert into rev(usr_id, stus_id, rms_id, cck_in, cck_out, stby_stus, rev_persn, rev_char) 
values(4,4,14,'2019-06-18','2020-05-10', '거절', 2, 150000);
insert into rev(usr_id, stus_id, rms_id, cck_in, cck_out, stby_stus, rev_persn, rev_char) 
values(5,2,15,'2019-07-11','2020-05-10','승인', 3, 250000);