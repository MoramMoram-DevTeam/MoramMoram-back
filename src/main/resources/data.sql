INSERT INTO authority (authority_name) VALUES ('ROLE_USER');
INSERT INTO authority (authority_name) VALUES ('ROLE_ADMIN');
INSERT INTO authority (authority_name) VALUES ('ROLE_OFFICE');

INSERT INTO user (id,email,name,pw,pnum,uimg,seller,report,marketing) VALUES (1,'1000playch@naver.com','admin','$2a$10$D99zvd9eSCquwrkA5ss7L.GiYshRHu2x.MEgvTbk80SpnGahGNKse','010-1234-1234',null,true,0,true);
INSERT INTO user (id,email,name,pw,pnum,uimg,seller,report,marketing) VALUES (2,'6candoit@naver.com','육캔두잇','$2a$10$D99zvd9eSCquwrkA5ss7L.GiYshRHu2x.MEgvTbk80SpnGahGNKse','010-6666-6666',null,true,0,true);
INSERT INTO user (id,email,name,pw,pnum,uimg,seller,report,marketing,office_add,market_add) VALUES (3,'6candoit@gmail.com','office','$2a$10$D99zvd9eSCquwrkA5ss7L.GiYshRHu2x.MEgvTbk80SpnGahGNKse','010-5678-5678',null,true,0,true,"dk.jpg","aa.png");

INSERT INTO flea_market (id, category, start, m_img, market_name, m_note, office_id, open, place, end, deadline, views) VALUES (1,'공예품','2022-10-25','https://morambucket.s3.ap-northeast-2.amazonaws.com/markets/2022-11-25/main1.png','자양사랑 플리마켓','서울 광진구 자양동에서 진행하는 자양사랑 플리마켓입니다~',3,false,'영등포','2022-11-25','2022-11-21', 0);
INSERT INTO flea_market (id, category, start, m_img, market_name, m_note, office_id, open, place, end, deadline, views) VALUES (2,'먹거리','2022-12-01','https://morambucket.s3.ap-northeast-2.amazonaws.com/markets/2022-11-25/main2.png','별빛달빛 플리마켓','별빛달빛 마켓입니다^^',3,false,'구로','2022-12-05','2022-11-28', 10);
INSERT INTO flea_market (id, category, start, m_img, market_name, m_note, office_id, open, place, end, deadline, views) VALUES (3,'인테리어','2022-12-21','https://morambucket.s3.ap-northeast-2.amazonaws.com/markets/2022-11-25/Rectangle+169-1.png','도리미 플리마켓','도레미 아니고 도리미입니다!',3,false,'신도림','2022-12-25','2022-12-17', 2);
INSERT INTO flea_market (id, category, start, m_img, market_name, m_note, office_id, open, place, end, deadline, views) VALUES (4,'문구/팬시','2022-12-23','https://morambucket.s3.ap-northeast-2.amazonaws.com/markets/2022-11-25/Rectangle+169-2.png','잠실만 플리마켓','잠실에서 열리는 잠실만~~ 플리~~마켓~~',3,false,'잠실','2022-12-28','2022-12-20', 4);
INSERT INTO flea_market (id, category, start, m_img, market_name, m_note, office_id, open, place, end, deadline, views) VALUES (5,'수공예품','2022-11-13','https://morambucket.s3.ap-northeast-2.amazonaws.com/markets/2022-11-25/Rectangle+169-3.png','강자만 남는 플리마켓','약한 자는 가라. 강자만 남는 강남의 강자만 마켓입니다.',3,false,'강남','2022-11-16','2022-11-10', 8);
INSERT INTO flea_market (id, category, start, m_img, market_name, m_note, office_id, open, place, end, deadline, views) VALUES (6,'의류/잡화','2022-11-23','https://morambucket.s3.ap-northeast-2.amazonaws.com/markets/2022-11-25/Rectangle+169-4.png','거꾸로해도 플리마켓','플리마켓을 거꾸로 하면 플리마켓. 틀렸다구요? 하지만 빨랐죠.',3,false,'역삼','2022-11-25','2022-11-20', 17);
INSERT INTO flea_market (id, category, start, m_img, market_name, m_note, office_id, open, place, end, deadline, views) VALUES (7,'의류/잡화','2022-11-20','https://morambucket.s3.ap-northeast-2.amazonaws.com/markets/2022-11-25/Rectangle+169-5.png','홍시대박 플리마켓','홍대에서 시작한 그 플리마켓, 홍시마켓이 가을을 맞이하여 돌아왔습니다.',3,false,'홍대','2022-11-23','2022-11-17', 1);
INSERT INTO flea_market (id, category, start, m_img, market_name, m_note, office_id, open, place, end, deadline, views) VALUES (8,'인테리어','2022-11-19','https://morambucket.s3.ap-northeast-2.amazonaws.com/markets/2022-11-25/Rectangle+169-6.png','어청담 플리마켓','어?여기는...? 어청담...?',3,false,'청담','2022-11-27','2022-11-16', 0);
INSERT INTO flea_market (id, category, start, m_img, market_name, m_note, office_id, open, place, end, deadline, views) VALUES (9,'수공예품','2022-12-02','https://morambucket.s3.ap-northeast-2.amazonaws.com/markets/2022-11-25/Rectangle+169-7.png','어디연남 플리마켓','어디연곳없나? 어디연남?',3,false,'연남','2022-12-05','2022-11-30', 0);
INSERT INTO flea_market (id, category, start, m_img, market_name, m_note, office_id, open, place, end, deadline, views) VALUES (10,'먹거리','2022-12-04','https://morambucket.s3.ap-northeast-2.amazonaws.com/markets/2022-11-25/Rectangle+169-8.png','마음이 공덕공덕 플리마켓','공덕만 가면 내 마음이 공덕.공덕. 뛰어버려.',3,false,'공덕','2022-12-08','2022-12-01', 0);
INSERT INTO flea_market (id, category, start, m_img, market_name, m_note, office_id, open, place, end, deadline, views) VALUES (11,'의류/잡화','2022-11-23','https://morambucket.s3.ap-northeast-2.amazonaws.com/markets/2022-11-25/Rectangle+169-9.png','한강 플리마켓','한강에 가면~ 치킨도 있고~ 플리마켓도 있고~',3,false,'영등포','2022-11-25','2022-11-21', 0);
INSERT INTO flea_market (id, category, start, m_img, market_name, m_note, office_id, open, place, end, deadline, views) VALUES (12,'먹거리','2022-12-01','https://morambucket.s3.ap-northeast-2.amazonaws.com/markets/2022-11-25/Rectangle+248.png','구롱구롱 플리마켓','구로가 울면? 눈물이 구롱구롱. 푸하핫',3,false,'구로','2022-12-05','2022-11-28', 10);


INSERT INTO user_authority (id,authority_name) VALUES (1,'ROLE_ADMIN');
INSERT INTO user_authority (id,authority_name) VALUES (1,'ROLE_USER');
INSERT INTO user_authority (id,authority_name) VALUES (1,'ROLE_OFFICE');
INSERT INTO user_authority (id,authority_name) VALUES (2,'ROLE_USER');
INSERT INTO user_authority (id,authority_name) VALUES (3,'ROLE_OFFICE');

INSERT INTO question_board (question_board_id,user_id,name,title,note,status) VALUES (1,1,'김민주','대여 업체 선정 기준','다들 어디서 빌리시나요??','status');
INSERT INTO question_board (question_board_id,user_id,name,title,note,status) VALUES (2,1,'조유리','11월 서울 플리마켓 다 어디 참여하시나요?','이번이 두 번째 마켓인데 나가는 김에 다른 분들하고 친해지고 싶어요ㅎㅎ','status');
INSERT INTO question_board (question_board_id,user_id,name,title,note,status) VALUES (3,1,'김정윤','어린이 손님들 간식','저는 어린이 손님이 많이 오는 편인데 간단히 사탕 같은 간식 두면 좋아할까요..? 부모님이 싫어하실까봐 둘지 말지 고민이네요...','status');
INSERT INTO question_board (question_board_id,user_id,name,title,note,status) VALUES (4,1,'홍예은','음악 틀어도 되나요?','너무 심심한데 음악 틀어두면 주변에 계신 다른 셀러분들이 불편하시겠죠? 너무 심심한데!','status');
INSERT INTO question_board (question_board_id,user_id,name,title,note,status) VALUES (5,1,'김지혜','질문있습니다1!','다들 본업이신가요?! 아님 부업이신가요?!!','status');


INSERT INTO tip_board (tip_board_id, user_id, name, title, note, view_cnt, created_at) VALUES (1, 1, '임연수', '나의 두 번째 직업, 플리마켓 셀러.', '저는 평범한 사무직으로 일하던 5년차 회사원이었습니다. \n계기는 우연히 참가했던 플리마켓에서 고등학교 시절 동창을 만나게 된 것이었는데요. 손재주가 좋았던 것은 기억하는데 마켓에서 셀러와 손님으로 만날 줄은 몰랐죠. \n이야기를 들어보니 저같은 회사원인데 취미생활의 연장으로 플리마켓에 참여하고 있더라구요. 이를 보니 저도 한 번 도전해보고 싶다는 생각이 들어 간단한 수공예품으로 시작했고, 지금은 도자기에 빠져 자기류를 위주로 판매하고 있네요ㅎㅎ \n여러분도 할 수 있습니다! 지금 바로 도전하세요~!', 78, '2022-11-22');
INSERT INTO tip_board (tip_board_id, user_id, name, title, note, view_cnt, created_at) VALUES (2, 1, '김정윤', '물건 잘 파는 팁', '가족 단위 손님, 외국인 손님을 잘 공략해보세요. 가족 단위 손님 중에 아이가 있으면 아이를, 또는 여성분들 위주로 대화하면 한층 대화도 잘 이어지고 구매율도 오르더라구요!', 17, '2022-10-28');
INSERT INTO tip_board (tip_board_id, user_id, name, title, note, view_cnt, created_at) VALUES (3, 1, '오유진', '대여업체 선정 시 유의점', '1.사장님이 연락을 잘 받는가 \n 2.실물 사진 따로 받아보기. \n제가 이걸 모를 때는 일단 받았는데 당일에 보니 상태가 영 별로더라구요. 그 뒤로는 미리 꼭 체크합니다.', 5, '2022-10-19');
INSERT INTO tip_board (tip_board_id, user_id, name, title, note, view_cnt, created_at) VALUES (4, 1, '신지원', '첫 플리마켓을 실패했던 이야기', '안녕하세요. 지금은 최소 월 1회 플리마켓에 참여하고 있습니다~ 처음 플리마켓 하시는 분들이 보여 저의 옛날 이야기를 써볼까 해요!', 19, '2022-11-01');
INSERT INTO tip_board (tip_board_id, user_id, name, title, note, view_cnt, created_at) VALUES (5, 1, '최민하', '플리마켓 선택할 때 이것만은 알아보자!', '1. 플리마켓 유동인구 \n 2.인근 편의시설 \n 3.행사일의 날씨', 11, '2022-10-23');
INSERT INTO tip_board (tip_board_id, user_id, name, title, note, view_cnt, created_at) VALUES (6, 1, '김정윤', '무엇을 팔아야 할지에 대한 고민과 나만의 해답찾기', '내가 좋아하는 것을 먼저 만들자!', 11, '2022-10-23');
INSERT INTO tip_board (tip_board_id, user_id, name, title, note, view_cnt, created_at) VALUES (7, 1, '전문가', '플리마켓을 처음 열어보고 느낀 점', '생각보다 호객행위에 겁 먹지 않아도 돼요~\n처음에는 말 꺼내기 어려울 수 있는데 핸드폰을 최대한 자제하고 기다리면 점점 모여들더라구요', 11, '2022-10-23');

